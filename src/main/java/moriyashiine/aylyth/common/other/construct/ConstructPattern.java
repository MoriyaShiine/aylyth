package moriyashiine.aylyth.common.other.construct;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class ConstructPattern {
    private final BlockMatcher[][][] pattern;
    private final BlockPos relativeSummonerBlock;

    public ConstructPattern(BlockMatcher[][][] pattern, BlockPos relativeSummonerBlock) {
        this.pattern = pattern;
        this.relativeSummonerBlock = relativeSummonerBlock;
    }

    public boolean test(World world, BlockPos clickedPos, Direction directionToCheck) {
        BlockPos startPos = getOffsetPos(clickedPos, relativeSummonerBlock.multiply(-1), directionToCheck);
        for (BlockPos mutablePos : BlockPos.iterate(0, 0, 0, pattern[0][0].length-1, pattern.length-1, pattern[0].length-1)) {
            BlockPos pos = getOffsetPos(startPos, mutablePos, directionToCheck);
            BlockMatcher predicate = pattern[mutablePos.getY()][mutablePos.getZ()][mutablePos.getX()];
            if (predicate.tryMatch(new CachedBlockPosition(world, pos, false)) == MatchResult.MISMATCH) {
                return false;
            }
        }
        return true;
    }

    public BlockPos getRelativeSummonBlock() {
        return relativeSummonerBlock;
    }

    public List<BlockPos> collectIfMatching(World world, BlockPos clickedPos, Direction directionToCheck) {
        List<BlockPos> matches = new ObjectArrayList<>();
        BlockPos startPos = getOffsetPos(clickedPos, relativeSummonerBlock.multiply(-1), directionToCheck);
        for (BlockPos mutablePos : BlockPos.iterate(0, 0, 0, pattern[0][0].length-1, pattern.length-1, pattern[0].length-1)) {
            BlockPos pos = getOffsetPos(startPos, mutablePos, directionToCheck);
            BlockMatcher predicate = pattern[mutablePos.getY()][mutablePos.getZ()][mutablePos.getX()];
            MatchResult matchResult = predicate.tryMatch(new CachedBlockPosition(world, pos, false));
            switch (matchResult) {
                case IGNORE -> {}
                case MISMATCH -> {
                    return List.of();
                }
                case MATCH -> matches.add(pos);
            }
        }
        if (!matches.isEmpty()) matches.sort(Collections.reverseOrder());
        return matches;
    }

    public BlockPos getOffsetPos(BlockPos pos, BlockPos offset, Direction directionToCheck) {
        return pos.mutableCopy()
                .offset(directionToCheck.rotateYClockwise(), offset.getX())
                .offset(directionToCheck, offset.getZ())
                .offset(Direction.UP, offset.getY());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Char2ObjectMap<BlockMatcher> charPredicateMap = Util.make(new Char2ObjectOpenHashMap<>(), map -> {
            map.put(' ', cachedBlockPosition -> MatchResult.IGNORE);
        });
        private final List<String[]> layers = new ObjectArrayList<>();
        private int width;
        private int depth;
        // This should be a unique character in the pattern that represents the block the player clicks.
        private char summoner = '\0';

        private Builder() {}

        /**
         * Built from bottom-to-top.
         * @param pattern The pattern to match in world
         * @return this builder
         */
        public Builder layer(String... pattern) {
            checkPatternExists(pattern);
            if (depth != 0) {
                if (pattern.length != depth) {
                    throw new IllegalArgumentException("The depth (length of array) of the given pattern does not match the previous levels");
                }
            } else {
                depth = pattern.length;
                width = pattern[0].length();
            }

            for (String line : pattern) {
                if (line.length() != width) {
                    throw new IllegalArgumentException("The width (length of string) of the given pattern does not match expected width: " + width);
                }
                for (char element : line.toCharArray()) {
                    charPredicateMap.putIfAbsent(element, null);
                }
            }

            layers.add(pattern);

            return this;
        }

        public Builder whereSummonerIs(char c) {
            if (summoner != '\0') {
                throw new IllegalStateException("Summoner already defined.");
            }
            this.summoner = c;
            return this;
        }

        public Builder where(char c, Predicate<CachedBlockPosition> predicate) {
            if (charPredicateMap.containsKey(c) && charPredicateMap.get(c) != null) {
                throw new IllegalArgumentException("Predicate already defined for: " + c);
            }

            charPredicateMap.put(c, cachedBlockPosition -> predicate.test(cachedBlockPosition) ? MatchResult.MATCH : MatchResult.MISMATCH);

            return this;
        }

        public Builder where(char c, char summoner, Predicate<CachedBlockPosition> predicate) {
            if (charPredicateMap.containsKey(c) && charPredicateMap.get(c) != null) {
                throw new IllegalArgumentException("Predicate already defined for: " + c);
            }
            if (charPredicateMap.containsKey(summoner) && charPredicateMap.get(summoner) != null) {
                throw new IllegalArgumentException("Predicate already defined for: " + summoner);
            }
            if (this.summoner != '\0') {
                throw new IllegalStateException("Summoner already defined.");
            }


            this.summoner = summoner;
            charPredicateMap.put(c, cachedBlockPosition -> predicate.test(cachedBlockPosition) ? MatchResult.MATCH : MatchResult.MISMATCH);
            charPredicateMap.put(summoner, cachedBlockPosition -> predicate.test(cachedBlockPosition) ? MatchResult.MATCH : MatchResult.MISMATCH);

            return this;
        }

        @SuppressWarnings("unchecked")
        public ConstructPattern build() {
            if (summoner == '\0') {
                throw new IllegalStateException("Summoner character was left undefined!");
            }

            BlockMatcher[][][] predicates = new BlockMatcher[layers.size()][depth][width];
            BlockPos relativeSummonerBlock = null;
            for (int y = 0; y < layers.size(); y++) {
                String[] layer = layers.get(y);
                for (int z = 0; z < layer.length; z++) {
                    String line = layer[z];
                    for (int x = 0; x < line.length(); x++) {
                        char c = line.charAt(x);
                        BlockMatcher predicate = charPredicateMap.get(c);
                        if (predicate == null) {
                            throw new IllegalStateException("Predicate not defined for: " + c);
                        }

                        predicates[y][z][x] = predicate;

                        if (c == summoner) {
                            if (relativeSummonerBlock == null) {
                                relativeSummonerBlock = new BlockPos(x, y, z);
                            } else {
                                throw new IllegalStateException("Should only have one summoner position defined");
                            }
                        }
                    }
                }
            }

            return new ConstructPattern(predicates, relativeSummonerBlock);
        }

        private void checkPatternExists(String[] pattern) {
            if (pattern.length == 0 || pattern[0].isEmpty()) {
                throw new IllegalArgumentException("Pattern must be present!");
            }
        }
    }

    public interface BlockMatcher {
        MatchResult tryMatch(CachedBlockPosition cachedBlockPosition);
    }

    public enum MatchResult {
        MATCH,
        MISMATCH,
        IGNORE
    }
}
