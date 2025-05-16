package moriyashiine.aylyth.common.entity.statuseffects;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class WyrdedStatusEffect extends StatusEffect {
    private static final Identifier WYRDED_MOVEMENT = Aylyth.id("wyrded_movement_debuff");

    public WyrdedStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x695237);
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (entity.getWorld() instanceof ServerWorld serverWorld) {
            EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
            if (instance == null) {
                return false;
            }
            instance.removeModifier(WYRDED_MOVEMENT);
            double distance = AylythUtil.distanceToSeep(serverWorld, entity, 5);
            if (distance != -1) {
                entity.damage(world, entity.getDamageSources().magic(), 2f + ((float)amplifier * 2));
                instance.addTemporaryModifier(
                        new EntityAttributeModifier(
                                WYRDED_MOVEMENT,
                                -(0.45 + (Math.sqrt(amplifier) / 10) + ((5.0 - distance) / 12.5)),
                                EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
        }
        return super.applyUpdateEffect(world, entity, amplifier);
    }

    @Override
    public void onRemoved(AttributeContainer attributeContainer) {
        EntityAttributeInstance instance = attributeContainer.getCustomInstance(EntityAttributes.MOVEMENT_SPEED);
        if (instance != null) {
            instance.removeModifier(WYRDED_MOVEMENT);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
