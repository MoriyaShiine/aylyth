package moriyashiine.aylyth.common.entity.statuseffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class BlightEffect extends StatusEffect {
    public BlightEffect() {
        super(StatusEffectCategory.HARMFUL, 0x38352A);
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        entity.damage(world, world.aylythDamageSources().blight(null), 1);
        return super.applyUpdateEffect(world, entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % (20 - Math.max(1f * amplifier, 3) * 5 ) == 0;
    }
}
