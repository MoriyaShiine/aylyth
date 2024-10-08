package moriyashiine.aylyth.common.world.gen.treedecorators;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.block.AylythBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

// TODO: Implement
public class StrewnLeavesDecorator extends TreeDecorator {
    private static final StrewnLeavesDecorator INSTANCE = new StrewnLeavesDecorator();
    public static final Codec<StrewnLeavesDecorator> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    protected TreeDecoratorType<?> getType() {
        return null;
    }

    @Override
    public void generate(Generator generator) {
        for (BlockPos pos : generator.getLeavesPositions()) {
            pos = generator.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos);
            if (generator.getRandom().nextFloat() > 0.75) {
                generator.replace(pos, AylythBlocks.OAK_STREWN_LEAVES.getDefaultState());
            }
        }
    }
}
