package moriyashiine.aylyth.datagen.common.world.terrain;

import moriyashiine.aylyth.common.data.world.AylythBiomes;
import moriyashiine.aylyth.common.data.world.terrain.AylythNoiseParams;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler.NoiseParameters;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.MaterialRules.MaterialRule;

import static net.minecraft.world.gen.surfacebuilder.MaterialRules.*;

final class AylythSurfaceMaterialRules {
    private AylythSurfaceMaterialRules() {}

    public static MaterialRule build() {
        var bedrock = condition(verticalGradient("aylyth:bedrock_layer", YOffset.BOTTOM, YOffset.aboveBottom(5)), block(Blocks.BEDROCK));

        var grassIfNotWater = condition(water(0, 0), block(Blocks.GRASS_BLOCK));
        var onSurface = condition(
                STONE_DEPTH_FLOOR,
                condition(
                        water(-1, 0),
                        sequence(deepwood(), copseAndOverwrownClearing(), grassIfNotWater, block(Blocks.DIRT))
                )
        );
        var dirtUnderFloor = condition(
                not(biome(AylythBiomes.UPLANDS)),
                condition(
                        waterWithStoneDepth(-6, -1),
                        condition(
                                STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                block(Blocks.DIRT)
                        )
                )
        );

        var abovePreliminarySurface = condition(surface(), sequence(onSurface, dirtUnderFloor));

        return sequence(bedrock, bowels(), uplands(), mire(), abovePreliminarySurface);
    }

    private static MaterialRule bowels() {
        return condition(
                biome(AylythBiomes.BOWELS),
                sequence(
                        condition(
                                STONE_DEPTH_FLOOR,
                                condition(
                                        water(0, 0),
                                        noiseBlock(AylythNoiseParams.BOWELS_SOUL_SAND, Blocks.SOUL_SAND, 0.6, Double.MAX_VALUE)
                                )
                        ),
                        condition(
                                waterWithStoneDepth(-6, -1),
                                condition(
                                        STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH,
                                        block(Blocks.SOUL_SOIL)
                                )
                        )
                )
        );
    }

    private static MaterialRule uplands() {
        return condition(
                biome(AylythBiomes.UPLANDS),
                condition(
                        surface(),
                        condition(
                                waterWithStoneDepth(-6, -1),
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
                )
        );
    }

    private static MaterialRule mire() {
        return condition(
                biome(AylythBiomes.MIRE),
                condition(
                        surface(),
                        sequence(
                                condition(
                                        not(aboveYWithStoneDepth(YOffset.fixed(48), 0)),
                                        sequence(surfaceNoiseBlock(Blocks.SOUL_SOIL, 0.70, Double.MAX_VALUE), block(Blocks.MUD))
                                ),
                                condition(
                                        STONE_DEPTH_FLOOR,
                                        condition(not(water(0, 0)),block(Blocks.MUD))
                                )
                        )
                )
        );
    }

    private static MaterialRule deepwood() {
        return condition(
                biome(AylythBiomes.DEEPWOOD, AylythBiomes.CONIFEROUS_DEEPWOOD),
                condition(noiseThreshold(AylythNoiseParams.PODZOL_COMMON, 0.3, Double.MAX_VALUE), block(Blocks.PODZOL))
        );
    }

    private static MaterialRule copseAndOverwrownClearing() {
        return condition(
                biome(AylythBiomes.COPSE, AylythBiomes.OVERGROWN_CLEARING),
                condition(noiseThreshold(AylythNoiseParams.PODZOL_RARE, 0.95, Double.MAX_VALUE), block(Blocks.PODZOL))
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
