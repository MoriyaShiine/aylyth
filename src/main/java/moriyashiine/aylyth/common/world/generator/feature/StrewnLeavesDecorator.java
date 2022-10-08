package moriyashiine.aylyth.common.world.generator.feature;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.block.StrewnLeavesBlock;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModFeatures;
import moriyashiine.aylyth.common.registry.ModVegetationFeatures;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class StrewnLeavesDecorator extends TreeDecorator {
    private static final StrewnLeavesDecorator INSTANCE = new StrewnLeavesDecorator();
    public static final Codec<StrewnLeavesDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModFeatures.STREWN_LEAVES;
    }

    @Override
    public void generate(Generator generator) {
        for (BlockPos pos : generator.getLeavesPositions()) {
            pos = generator.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos);
            if (generator.getRandom().nextFloat() > 0.75) {
                generator.replace(pos, ModBlocks.OAK_STREWN_LEAVES.getDefaultState());
            }
        }
    }
}
