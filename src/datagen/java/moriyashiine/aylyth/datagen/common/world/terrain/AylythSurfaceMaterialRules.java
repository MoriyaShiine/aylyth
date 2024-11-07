package moriyashiine.aylyth.datagen.common.world.terrain;

import moriyashiine.aylyth.common.data.world.AylythBiomes;
import moriyashiine.aylyth.common.data.world.terrain.AylythNoiseParams;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler.NoiseParameters;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.MaterialRules.MaterialRule;

import static net.minecraft.world.gen.surfacebuilder.MaterialRules.*;

final class AylythSurfaceMaterialRules {
    private AylythSurfaceMaterialRules() {}

    private static final MaterialCondition ON_FLOOR = STONE_DEPTH_FLOOR;
    private static final MaterialCondition UNDER_FLOOR = STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH;
    private static final MaterialCondition ABOVE_WATER_LEVEL = water(0, 0);
    private static final MaterialCondition AT_OR_ABOVE_WATER_LEVEL = water(-1, 0);
    private static final MaterialCondition BELOW_WATER_LEVEL = waterWithStoneDepth(-6, -1);

    public static MaterialRule build() {
        var bedrock = condition(verticalGradient("aylyth:bedrock_layer", YOffset.BOTTOM, YOffset.aboveBottom(5)), block(Blocks.BEDROCK));

        var grassIfNotWater = condition(ABOVE_WATER_LEVEL, block(Blocks.GRASS_BLOCK));
        var onSurface = condition(
                ON_FLOOR,
                condition(
                        AT_OR_ABOVE_WATER_LEVEL,
                        sequence(deepwood(), copseAndOverwrownClearing(), copse(), grassIfNotWater, block(Blocks.DIRT))
                )
        );
        var dirtUnderFloor = condition(
                BELOW_WATER_LEVEL,
                condition(
                        STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                        sequence(
                                //
                                condition(stoneDepth(-1, true, VerticalSurfaceType.FLOOR), condition(ABOVE_WATER_LEVEL, block(Blocks.ROOTED_DIRT))),
                                condition(stoneDepth(-2, true, VerticalSurfaceType.FLOOR), condition(ABOVE_WATER_LEVEL, block(Blocks.ROOTED_DIRT))),
                                block(Blocks.DIRT)
                        )
                )
        );
        var abovePreliminarySurface = condition(surface(), sequence(uplands(), mire(), onSurface, dirtUnderFloor));

        return sequence(bedrock, bowels(), abovePreliminarySurface);
    }

    private static MaterialRule bowels() {
        return condition(
                biome(AylythBiomes.BOWELS),
                sequence(
                        condition(ON_FLOOR, condition(ABOVE_WATER_LEVEL, noiseBlock(AylythNoiseParams.BOWELS_SOUL_SAND_PATCHES, Blocks.SOUL_SAND, 0.6, Double.MAX_VALUE))),
                        condition(BELOW_WATER_LEVEL, condition(UNDER_FLOOR, block(Blocks.SOUL_SOIL)))
                )
        );
    }

    private static MaterialRule uplands() {
        return condition(
                biome(AylythBiomes.UPLANDS),
                condition(
                        BELOW_WATER_LEVEL,
                        sequence(
                                surfaceNoiseBlock(Blocks.DEEPSLATE, -2, -0.6),
                                surfaceNoiseBlock(Blocks.BROWN_TERRACOTTA, -0.6, -0.15),
                                surfaceNoiseBlock(Blocks.YELLOW_TERRACOTTA, -0.15, 0),
                                surfaceNoiseBlock(Blocks.ORANGE_TERRACOTTA, 0, 0.3),
                                surfaceNoiseBlock(Blocks.RED_TERRACOTTA, 0.3, 0.6),
                                surfaceNoiseBlock(Blocks.TERRACOTTA, 0.6, 0.8),
                                surfaceNoiseBlock(Blocks.DEEPSLATE, 0.8, 2.0)
                        )
                )
        );
    }

    private static MaterialRule mire() {
        return condition(
                biome(AylythBiomes.MIRE),
                sequence(
                        condition(
                                not(aboveYWithStoneDepth(YOffset.fixed(48), 0)),
                                sequence(surfaceNoiseBlock(Blocks.SOUL_SOIL, 0.70, Double.MAX_VALUE), block(Blocks.MUD))
                        ),
                        condition(ON_FLOOR, condition(not(ABOVE_WATER_LEVEL), block(Blocks.MUD)))
                )
        );
    }

    private static MaterialRule deepwood() {
        return condition(
                biome(AylythBiomes.DEEPWOOD, AylythBiomes.CONIFEROUS_DEEPWOOD),
                sequence(
                        // Common dark podzol patches
                        condition(noiseThreshold(AylythNoiseParams.DEEPWOOD_DARK_PODZOL_PATCHES, 0.3, Double.MAX_VALUE), block(Blocks.MUD)), // TODO replace with AylythBlocks.DARK_PODZOL
                        // Thick podzol veins
                        condition(noiseThreshold(AylythNoiseParams.DEEPWOOD_PODZOL_VEINS, -0.05, 0.05), block(Blocks.PODZOL))
                )
        );
    }

    private static MaterialRule copseAndOverwrownClearing() {
        return condition(
                biome(AylythBiomes.COPSE, AylythBiomes.OVERGROWN_CLEARING),
                // Rare podzol patches
                condition(noiseThreshold(AylythNoiseParams.PODZOL_PATCHES, 0.95, Double.MAX_VALUE), block(Blocks.PODZOL))
        );
    }

    private static MaterialRule copse() {
        return condition(
                biome(AylythBiomes.COPSE, AylythBiomes.CONIFEROUS_COPSE),
                // Thin dark podzol veins
                condition(noiseThreshold(AylythNoiseParams.COPSE_DARK_PODZOL_VEINS, -0.03, 0.03), block(Blocks.MUD)) // TODO replace with AylythBlocks.DARK_PODZOL
        );
    }

    private static MaterialRule block(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }

    private static MaterialRule noiseBlock(RegistryKey<NoiseParameters> noise, Block block, double min, double max) {
        return condition(noiseThreshold(noise, min, max), block(block));
    }

    private static MaterialRule surfaceNoiseBlock(Block block, double min, double max) {
        return noiseBlock(AylythNoiseParams.SURFACE, block, min, max);
    }
}
