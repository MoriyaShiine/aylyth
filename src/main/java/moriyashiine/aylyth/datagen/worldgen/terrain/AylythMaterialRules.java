package moriyashiine.aylyth.datagen.worldgen.terrain;

import moriyashiine.aylyth.common.registry.ModBiomeKeys;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

import static moriyashiine.aylyth.datagen.worldgen.terrain.AylythNoiseTypes.PODZOL_COMMON;
import static moriyashiine.aylyth.datagen.worldgen.terrain.AylythNoiseTypes.PODZOL_RARE;

public class AylythMaterialRules extends MaterialRules {

    static MaterialRules.MaterialRule materialRules() {
        var dirt = block(Blocks.DIRT);
        var grass = block(Blocks.GRASS_BLOCK);
        var onReplaceWithGrass = condition(water(0, 0), grass);
        var commonPodzol = podzol(PODZOL_COMMON, 0.3, Double.MAX_VALUE);
        var rarePodzol = podzol(PODZOL_RARE, 0.95, Double.MAX_VALUE);
        var podzolDecoCommon = sequence(condition(biome(ModBiomeKeys.DEEPWOOD_ID, ModBiomeKeys.CONIFEROUS_DEEPWOOD_ID), commonPodzol));
        var podzolDecoRare = condition(biome(ModBiomeKeys.COPSE_ID, ModBiomeKeys.OVERGROWN_CLEARING_ID), rarePodzol);
        var onSurface = condition(
                stoneDepth(0, false, 0, VerticalSurfaceType.FLOOR),
                condition(water(-1, 0),
                        sequence(podzolDecoCommon, podzolDecoRare, onReplaceWithGrass, dirt)
                )
        );
        var onUnderSurface = condition(not(biome(ModBiomeKeys.UPLANDS_ID)), condition(waterWithStoneDepth(-6, -1), condition(stoneDepth(0, true, 0, VerticalSurfaceType.FLOOR), dirt)));
        var aboveBasicSurface = condition(surface(), sequence(onSurface, onUnderSurface));
        var bedrock = condition(verticalGradient("aylyth:bedrock_layer", YOffset.BOTTOM, YOffset.aboveBottom(5)), block(Blocks.BEDROCK));
        var uplands = condition(biome(ModBiomeKeys.UPLANDS_ID), condition(surface(), condition(waterWithStoneDepth(-6, -1), uplandsTerracotta())));
        return sequence(bedrock, bowels(), uplands, mire(), aboveBasicSurface);
    }

    static MaterialRule uplandsTerracotta() {
        return sequence(surfaceNoiseBlock(Blocks.DEEPSLATE, -2, -0.6), surfaceNoiseBlock(Blocks.BROWN_TERRACOTTA, -0.6, -0.15), surfaceNoiseBlock(Blocks.YELLOW_TERRACOTTA, -0.15, 0), surfaceNoiseBlock(Blocks.ORANGE_TERRACOTTA, 0, 0.3), surfaceNoiseBlock(Blocks.RED_TERRACOTTA, 0.3, 0.6), surfaceNoiseBlock(Blocks.TERRACOTTA, 0.6, 0.8), surfaceNoiseBlock(Blocks.DEEPSLATE, 0.8, 2.0));
    }

    static MaterialRule mire() {
        return condition(biome(ModBiomeKeys.MIRE_ID), sequence(mireAroundLand(), mireUnderwaterMud()));
    }

    static MaterialRule mireAroundLand() {
        return condition(surface(), condition(not(aboveYWithStoneDepth(YOffset.fixed(48), 0)), sequence(surfaceNoiseBlock(Blocks.SOUL_SOIL, 0.70, Double.MAX_VALUE), block(Blocks.MUD))));
    }

    static MaterialRule mireUnderwaterMud() {
        return condition(surface(), condition(stoneDepth(0, false, 0, VerticalSurfaceType.FLOOR), condition(not(water(0, 0)), block(Blocks.MUD))));
    }

    static MaterialRule bowels() {
        return condition(biome(ModBiomeKeys.BOWELS_ID), sequence(condition(stoneDepth(0, false, 0, VerticalSurfaceType.FLOOR), condition(water(0, 0), noiseBlock(AylythNoiseTypes.BOWELS_SOUL_SAND, Blocks.SOUL_SAND, 0.6, Double.MAX_VALUE))), condition(waterWithStoneDepth(-6, -1), condition(stoneDepth(0, true, 0, VerticalSurfaceType.FLOOR), block(Blocks.SOUL_SOIL)))));
    }

    static MaterialRule podzol(AylythNoiseTypes.NoiseRegistryPair noise, double min, double max) {
        return condition(noiseThreshold(noise.registryKey, min, max), block(Blocks.PODZOL));
    }

    static MaterialRule noiseBlock(AylythNoiseTypes.NoiseRegistryPair noise, Block block, double min, double max) {
        return condition(noiseThreshold(noise.registryKey, min, max), block(block));
    }

    static MaterialRule surfaceNoiseBlock(Block block, double min, double max) {
        return condition(noiseThreshold(AylythNoiseTypes.SURFACE.registryKey, min, max), block(block));
    }

    static MaterialRule block(Block block) {
        return block(block.getDefaultState());
    }
}
