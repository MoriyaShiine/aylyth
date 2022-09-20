package moriyashiine.aylyth.datagen;

import net.minecraft.block.Block;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class AylythMaterialRules extends MaterialRules {

    public static MaterialRules.MaterialRule surfaceNoiseBlock(Block block, double min, double max) {
        return condition(noiseThreshold(AylythNoiseTypes.SURFACE.registryKey, min, max), block(block.getDefaultState()));
    }
}
