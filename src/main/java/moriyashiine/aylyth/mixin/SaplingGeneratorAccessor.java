package moriyashiine.aylyth.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SaplingGenerator.class)
public interface SaplingGeneratorAccessor {

    @Invoker
    static boolean invokeCanGenerateLargeTree(BlockState state, BlockView world, BlockPos pos, int x, int z) {
        throw new AssertionError("Implemented via mixin");
    }
}
