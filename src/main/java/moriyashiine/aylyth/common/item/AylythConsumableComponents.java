package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

public interface AylythConsumableComponents {
    ConsumableComponent YMPE_MUSH = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(AylythStatusEffects.WYRDED, 6000, 0), 0.5f))
            .build();
    ConsumableComponent GHOSTCAPS = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(AylythStatusEffects.CIMMERIAN, 1800, 0), 0.125f))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 300, 0), 0.875f))
            .build();
    ConsumableComponent POMEGRANATE = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(AylythStatusEffects.CIMMERIAN, 1800, 0), 0.5f))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(AylythStatusEffects.WYRDED, 6000, 0), 0.5f))
            .build();
    ConsumableComponent WRONGMEAT = ConsumableComponents.food()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(AylythStatusEffects.CIMMERIAN, 1800, 0), 0.5f))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(AylythStatusEffects.WYRDED, 6000, 0), 0.5f))
            .build();
}
