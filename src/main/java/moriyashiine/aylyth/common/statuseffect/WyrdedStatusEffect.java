package moriyashiine.aylyth.common.statuseffect;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.UUID;

public class WyrdedStatusEffect extends StatusEffect {

    private static final UUID MODIFIER_UUID = UUID.fromString("55683eb4-97eb-41a0-8b6a-3ffaf5dfb21b");

    public WyrdedStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x695237);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        double distance = AylythUtil.distanceIfNearSeep(entity, 5);
        if (distance == -1) {
            if (instance != null) {
                instance.tryRemoveModifier(MODIFIER_UUID);
            }
        } else {
            entity.damage(DamageSource.MAGIC, 2f + ((float)amplifier * 2));
            if (instance != null && (instance.tryRemoveModifier(MODIFIER_UUID) || instance.getModifier(MODIFIER_UUID) == null)) {
                instance.addTemporaryModifier(new EntityAttributeModifier(MODIFIER_UUID, this::getTranslationKey, -(0.45 + (Math.sqrt(amplifier) / 10) + ((5.0 - distance) / 12.5)), EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        // NO-OP
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).tryRemoveModifier(MODIFIER_UUID);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
