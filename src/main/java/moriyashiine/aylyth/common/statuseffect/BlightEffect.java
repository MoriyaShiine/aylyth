package moriyashiine.aylyth.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class BlightEffect extends StatusEffect {
    public BlightEffect() {
        super(StatusEffectCategory.HARMFUL, 0xFFAABB);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity.age % (20 - Math.max(1f * amplifier, 3) * 5 )  == 0)
            entity.damage(entity.getWorld().modDamageSources().blight(null), 1);

        super.applyUpdateEffect(entity, amplifier);
    }
}
