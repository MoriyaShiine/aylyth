package moriyashiine.aylyth.datagen.worldgen.terrain;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class AylythMaterialRules extends MaterialRules {

    public static MaterialRule block(Block block) {
        return block(block.getDefaultState());
    }

    public static MaterialRule surfaceNoiseBlock(Block block, double min, double max) {
        return condition(noiseThreshold(AylythNoiseTypes.SURFACE.registryKey, min, max), block(block.getDefaultState()));
    }

    public static MaterialRule podzol(AylythNoiseTypes.NoiseRegistryPair noise, double min, double max) {
        return condition(noiseThreshold(noise.registryKey, min, max), block(Blocks.PODZOL));
    }

    public static MaterialRule uplandsTerracotta() {
        return MaterialRules.sequence(AylythMaterialRules.surfaceNoiseBlock(Blocks.DEEPSLATE, -2, -0.6), AylythMaterialRules.surfaceNoiseBlock(Blocks.BROWN_TERRACOTTA, -0.6, -0.15), AylythMaterialRules.surfaceNoiseBlock(Blocks.YELLOW_TERRACOTTA, -0.15, 0), AylythMaterialRules.surfaceNoiseBlock(Blocks.ORANGE_TERRACOTTA, 0, 0.3), AylythMaterialRules.surfaceNoiseBlock(Blocks.RED_TERRACOTTA, 0.3, 0.6), AylythMaterialRules.surfaceNoiseBlock(Blocks.TERRACOTTA, 0.6, 0.8), AylythMaterialRules.surfaceNoiseBlock(Blocks.DEEPSLATE, 0.8, 2.0));
    }
}
