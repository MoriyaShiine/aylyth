package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.common.block.NysianGrapeVineBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VineBlock.class)
public class VineBlockMixin {
	@SuppressWarnings("ConstantConditions")
	@Inject(method = "shouldHaveSide", at = @At("HEAD"), cancellable = true)
	private void shouldNysianVineHaveSide(BlockView world, BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> cir) {
		if ((VineBlock) (Object) this instanceof NysianGrapeVineBlock && side == Direction.UP) {
			cir.setReturnValue(false);
		}
	}
	
	@Inject(method = "shouldConnectTo", at = @At("HEAD"), cancellable = true)
	private static void shouldNysianVineConnectTo(BlockView world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		if (world.getBlockState(pos).getBlock() instanceof NysianGrapeVineBlock && direction == Direction.UP) {
			cir.setReturnValue(false);
		}
	}
}
