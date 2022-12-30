package moriyashiine.aylyth.common.world.generator.trunkplacer;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import moriyashiine.aylyth.common.block.SeepBlock;
import moriyashiine.aylyth.common.registry.ModFeatures;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class GirasolTrunkPlacer extends TrunkPlacer {
    public static final Codec<GirasolTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).and(BlockState.CODEC.fieldOf("seep_block").forGetter(girasolTrunkPlacer -> girasolTrunkPlacer.seepBlock)).apply(instance, GirasolTrunkPlacer::new));

    private final BlockState seepBlock;

    public GirasolTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight, BlockState seepBlock) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
        this.seepBlock = seepBlock;
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModFeatures.GIRASOL_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        ImmutableList.Builder<FoliagePlacer.TreeNode> builder = ImmutableList.builder();

        if (!hasSpace(world, startPos)) return builder.build();

        for (int y = 0; y < height; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    if ((Math.abs(x) == 2 && Math.abs(z) == 2) || (y > 2 && (Math.abs(x) == 2 || Math.abs(z) == 2))) continue;
                    var pos = startPos.add(x, y, z);
                    if (y > 2 && (Math.abs(x) == 1 || Math.abs(z) == 1) && random.nextInt(60) < 35) {
                        if (seepBlock.getBlock() instanceof SeepBlock seep) {
                            if (world.testBlockState(pos.down(), state -> state.isOf(seep))) {
                                var state = seep.getDefaultState().with(SeepBlock.CONNECTION, SeepBlock.Connection.DOWN);
                                replacer.accept(pos, state);
                                var downState = seep.getDefaultState().with(SeepBlock.CONNECTION, SeepBlock.Connection.UP);
                                replacer.accept(pos.down(), downState);
                            } else {
                                var state = seep.getDefaultState().with(SeepBlock.CONNECTION, SeepBlock.Connection.NONE);
                                replacer.accept(pos, state);
                            }
                        } else {
                            replacer.accept(pos, seepBlock);
                        }
                    } else {
                        replacer.accept(pos, config.trunkProvider.getBlockState(random, pos));
                    }
                }
            }
        }

        IntSet angles = new IntArraySet();
        var topCenter = startPos.up(height);
        for (int i = 0; i < 16; i++) {
            var topCenterOffset = topCenter.add(random.nextBetween(-1, 1), 0, random.nextBetween(-1, 1));
            int randAngle;
            int iterations = 0;
            do {
                randAngle = random.nextInt(360);
                iterations++;
            } while (isNear(angles, randAngle, 15) || iterations < 5);
            angles.add(randAngle);
            BlockPos pos = null;
            for (double h = 0; h < 6; h += 0.5) {
                var xoffSet = h * Math.cos(Math.toRadians(randAngle));
                var zOffset = h * Math.sin(Math.toRadians(randAngle));
                var yOffset = getYValue(h, random);
                pos = topCenterOffset.add(xoffSet, yOffset, zOffset);

                if (!canReplaceOrIsLog(world, pos)) break;

                replacer.accept(pos, config.trunkProvider.getBlockState(random, pos));
            }
            builder.add(new FoliagePlacer.TreeNode(pos.down(), 1, true));
        }

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                var pos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, topCenter).add(x, 0, z);
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

    private boolean hasSpace(TestableWorld world, BlockPos centerPos) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (Math.abs(x) == 2 && Math.abs(z) == 2) continue;
                if (!canReplace(world, centerPos.add(x, 0, z))) {
                    return false;
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
