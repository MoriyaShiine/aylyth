package moriyashiine.aylyth.common.world.gen.trunkplacer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import moriyashiine.aylyth.common.block.types.LargeWoodyGrowthBlock;
import moriyashiine.aylyth.common.block.types.SeepBlock;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.world.gen.AylythTrunkPlacerTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class GirasolTrunkPlacer extends TrunkPlacer {
    public static final Codec<GirasolTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
            fillTrunkPlacerFields(instance)
                    .and(BlockState.CODEC.fieldOf("seep_block").forGetter(girasolTrunkPlacer -> girasolTrunkPlacer.seepBlock))
                    .and(Codec.intRange(-1, 6).fieldOf("woody_growth_range").forGetter(girasolTrunkPlacer -> girasolTrunkPlacer.woodyGrowthRange))
                    .apply(instance, GirasolTrunkPlacer::new));

    private final BlockState seepBlock;
    private final int woodyGrowthRange;

    public GirasolTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, BlockState seepBlock, int woodyGrowthRange) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.seepBlock = seepBlock;
        this.woodyGrowthRange = woodyGrowthRange;
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return AylythTrunkPlacerTypes.GIRASOL;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        ImmutableList.Builder<FoliagePlacer.TreeNode> builder = ImmutableList.builder();

        if (!hasSpace(world, startPos, height)) return builder.build();

        replacer.accept(startPos, config.trunkProvider.get(random, startPos));

        for (int y = 0; y < height; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    if ((Math.abs(x) == 2 && Math.abs(z) == 2) || (y > 2 && (Math.abs(x) == 2 || Math.abs(z) == 2))) continue;
                    BlockPos pos = startPos.add(x, y, z);
                    if (((y > 2 && (Math.abs(x) == 1 || Math.abs(z) == 1)) || (Math.abs(x) == 2 ^ Math.abs(z) == 2)) && random.nextInt(60) < 25) {
                        if (seepBlock.getBlock() instanceof SeepBlock seep) {
                            if (world.testBlockState(pos.down(), state -> state.isOf(seep) && state.get(SeepBlock.CONNECTION).equals(SeepBlock.Connection.NONE))) {
                                BlockState state = seep.getDefaultState().with(SeepBlock.CONNECTION, SeepBlock.Connection.DOWN);
                                replacer.accept(pos, state);
                                BlockState downState = seep.getDefaultState().with(SeepBlock.CONNECTION, SeepBlock.Connection.UP);
                                replacer.accept(pos.down(), downState);
                            } else {
                                BlockState state = seep.getDefaultState().with(SeepBlock.CONNECTION, SeepBlock.Connection.NONE);
                                replacer.accept(pos, state);
                            }
                        } else {
                            replacer.accept(pos, seepBlock);
                        }
                    } else {
                        replacer.accept(pos, config.trunkProvider.get(random, pos));
                        if (random.nextInt(5) == 0) {
                            if (x == -1) {

                            } else if (z == -1) {

                            } else if (x == 1) {

                            } else if (z == 1) {

                            }
                        }
                    }
                }
            }
        }

        IntSet angles = new IntArraySet();
        BlockPos topCenter = startPos.up(height);
        for (int i = 0; i < 16; i++) {
            BlockPos topCenterOffset = topCenter.add(random.nextBetween(-1, 1), 0, random.nextBetween(-1, 1));
            int randAngle;
            int iterations = 0;
            do {
                randAngle = random.nextInt(360);
                iterations++;
            } while (isNear(angles, randAngle, 15) && iterations < 5);
            angles.add(randAngle);
            double length = 7 + ((random.nextInt(random.nextInt(10) + 1)) * 0.1);
            for (double h = 0; h < length; h += 0.1) {
                double xoffSet = h * Math.cos(Math.toRadians(randAngle));
                double zOffset = h * Math.sin(Math.toRadians(randAngle));
                int yOffset = getYValue(h, random);
                BlockPos pos = topCenterOffset.add((int) xoffSet, yOffset, (int) zOffset);
                BlockState blockState = config.trunkProvider.get(random, pos);

                if ((canReplace(world, pos) || TreeFeature.isAirOrLeaves(world, pos)) && pos.getY() >= startPos.getY()) {
                    replacer.accept(pos, blockState);
                    int xDiff = topCenter.getX() - pos.getX();
                    int zDiff = topCenter.getZ() - pos.getZ();
                    if (canReplace(world, pos.up()) && Math.sqrt((xDiff * xDiff + zDiff * zDiff)) <= woodyGrowthRange && random.nextInt(10) == 0) {
                        if (canReplace(world, pos.up()) && random.nextInt(3) == 0) {
                            replacer.accept(pos.up(), AylythBlocks.SMALL_WOODY_GROWTH.getDefaultState());
                        } else {
                            if (canReplace(world, pos.up()) && canReplace(world, pos.up(2))) {
                                replacer.accept(pos.up(), AylythBlocks.LARGE_WOODY_GROWTH.getDefaultState().with(LargeWoodyGrowthBlock.HALF, DoubleBlockHalf.LOWER));
                                replacer.accept(pos.up(2), AylythBlocks.LARGE_WOODY_GROWTH.getDefaultState().with(LargeWoodyGrowthBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    } else if (random.nextInt(5) == 0) {
                        BlockPos randomOffsetPos = pos.offset(Direction.random(random));
                        if (canReplace(world, randomOffsetPos)) {
                            builder.add(new FoliagePlacer.TreeNode(randomOffsetPos, 1, true));
                        }
                    }
                }
            }
        }

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos pos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, topCenter).add(x, 0, z);
                if (world.testBlockState(pos.down(), AbstractBlock.AbstractBlockState::isAir)) {
                    pos = pos.down();
                }
                if (canReplace(world, pos) && random.nextInt(10) < 6) {
                    builder.add(new FoliagePlacer.TreeNode(pos, 1, true));
                }
            }
        }

        return builder.build();
    }

    private boolean hasSpace(TestableWorld world, BlockPos centerPos, int height) {
        for (int y = 0; y <= height; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    if (Math.abs(x) == 2 && Math.abs(z) == 2) continue;
                    if (!canReplace(world, centerPos.add(x, y, z))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isNear(IntSet angles, int angle, int bound) {
        for (int i = -bound; i < bound; i++) {
            if (angles.contains(angle+i)) {
                return true;
            }
        }
        return false;
    }

    private int getYValue(double val, Random random) {
        double modifier = 0.5;
        double modifiedVal = val * modifier;
        double b = -1;
        double v = 3;
        return (int) Math.round(b * (modifiedVal * modifiedVal * modifiedVal) + v * (modifiedVal * modifiedVal));
    }
}
