package moriyashiine.aylyth.common.statuseffect;

import moriyashiine.aylyth.common.AylythUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.UUID;

public class WyrdedStatusEffect extends StatusEffect {

    public WyrdedStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x695237);
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "55683eb4-97eb-41a0-8b6a-3ffaf5dfb21b", 1.0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        var instance = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        var modifier = getAttributeModifiers().get(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (!AylythUtil.isNearSeep(entity, 5)) {
            if (instance != null && instance.hasModifier(modifier)) {
                instance.removeModifier(modifier);
            }
        } else {
            entity.damage(DamageSource.MAGIC, 0.5f + ((float)amplifier * 0.5f));
            if (instance != null && !instance.hasModifier(modifier)) {
                instance.addPersistentModifier(new EntityAttributeModifier(modifier.getId(), this::getTranslationKey, -(0.45 + (Math.sqrt(amplifier) / 10)), EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        // NO-OP
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
