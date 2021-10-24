package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.common.registry.ModComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "drop", at = @At("HEAD"), cancellable = true)
	private void shuckLogic(DamageSource source, CallbackInfo ci) {
		if ((LivingEntity) (Object) this instanceof MobEntity mob && ModComponents.PREVENT_DROPS.get(mob).getPreventsDrops()) {
			ci.cancel();
		}
	}
}
