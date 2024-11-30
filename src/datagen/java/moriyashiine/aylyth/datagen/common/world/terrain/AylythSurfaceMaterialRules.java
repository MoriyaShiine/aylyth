package moriyashiine.aylyth.datagen.common.world.terrain;

import moriyashiine.aylyth.common.block.AylythBlocks;
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
    private static final MaterialCondition BELOW_SHALLOW_WATER = waterWithStoneDepth(-6, -1);

    public static MaterialRule build() {
        var bedrock = condition(verticalGradient("aylyth:bedrock_layer", YOffset.BOTTOM, YOffset.aboveBottom(5)), block(Blocks.BEDROCK));

        var mudUnderwater = condition(not(ABOVE_WATER_LEVEL), block(Blocks.MUD));
        var grassAboveWater = condition(ABOVE_WATER_LEVEL, block(Blocks.GRASS_BLOCK));

        var floor = condition(
                ON_FLOOR,
                sequence(
                        mudUnderwater,
                        condition(
                                AT_OR_ABOVE_WATER_LEVEL,
                                sequence(deepwood(), copseAndOverwrownClearing(), copse(), grassAboveWater, block(Blocks.DIRT))
                        )
                )
        );
        var underFloor = condition(
                BELOW_SHALLOW_WATER,
                condition(
                        UNDER_FLOOR,
                        sequence(
                                // TODO StoneDepthMaterialCondition::secondaryDepthRange doesn't work but is more preferable
                                condition(stoneDepth(-1, true, VerticalSurfaceType.FLOOR), condition(ABOVE_WATER_LEVEL, block(Blocks.ROOTED_DIRT))),
                                condition(stoneDepth(-2, true, VerticalSurfaceType.FLOOR), condition(ABOVE_WATER_LEVEL, block(Blocks.ROOTED_DIRT))),
                                block(Blocks.DIRT)
                        )
                )
        );
        var abovePreliminarySurface = condition(surface(), sequence(uplands(), mire(), floor, underFloor));

        return sequence(bedrock, bowels(), abovePreliminarySurface);
    }

    private static MaterialRule bowels() {
        return condition(
                biome(AylythBiomes.BOWELS),
                sequence(
                        // Uncommon patches
                        condition(ON_FLOOR, condition(ABOVE_WATER_LEVEL, noiseBlock(AylythNoiseParams.BOWELS_SOUL_SAND_PATCHES, Blocks.SOUL_SAND, 0.6, Double.MAX_VALUE))),
                        condition(BELOW_SHALLOW_WATER, condition(UNDER_FLOOR, block(Blocks.SOUL_SOIL)))
                )
        );
    }

    private static MaterialRule uplands() {
        return condition(
                biome(AylythBiomes.UPLANDS),
                condition(
                        BELOW_SHALLOW_WATER,
                        sequence(
                                // Common patches
                                noiseBlock(AylythNoiseParams.UPLANDS_LIGNITE_SAPSTONE_PATCHES, AylythBlocks.LIGNITE_SAPSTONE, 0.4, Double.MAX_VALUE),
                                // Opalescent inside amber, amber inside regular
                                noiseBlock(AylythNoiseParams.UPLANDS_SAPSTONE_RINGS, AylythBlocks.OPALESCENT_SAPSTONE, 0.4, 2),
                                noiseBlock(AylythNoiseParams.UPLANDS_SAPSTONE_RINGS, AylythBlocks.AMBER_SAPSTONE, 0.15, 0.4),
                                noiseBlock(AylythNoiseParams.UPLANDS_SAPSTONE_RINGS, AylythBlocks.SAPSTONE, -0.15, 0.15),
                                noiseBlock(AylythNoiseParams.UPLANDS_SAPSTONE_RINGS, AylythBlocks.AMBER_SAPSTONE, -0.4, -0.15),
                                noiseBlock(AylythNoiseParams.UPLANDS_SAPSTONE_RINGS, AylythBlocks.OPALESCENT_SAPSTONE, -2, -0.4)
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
