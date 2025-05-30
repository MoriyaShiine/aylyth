package moriyashiine.aylyth.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Invoker
    Vec3d invokeApplyMovementInput(Vec3d movementInput, float slipperiness);

    @Accessor
    Map<RegistryEntry<StatusEffect>, StatusEffectInstance>  getActiveStatusEffects();
}
