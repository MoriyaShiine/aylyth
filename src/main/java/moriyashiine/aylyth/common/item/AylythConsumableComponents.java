package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.item.consume.CompositeHealthBased;
import moriyashiine.aylyth.common.item.consume.RestoreVitalHealth;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

import java.util.List;

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

    ConsumableComponent NEPHRITE_FLASK = ConsumableComponents.drink()
            .consumeEffect(new CompositeHealthBased(
                    List.of(
                            new RestoreVitalHealth(4)
                    ),
                    List.of(
                            new ApplyEffectsConsumeEffect(
                                    List.of(
                                            new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1)
                                    )
                            )
                    )
            ))
            .build();
    ConsumableComponent DARK_NEPHRITE_FLASK = ConsumableComponents.drink()
            .consumeEffect(new CompositeHealthBased(
                    List.of(
                            new RestoreVitalHealth(4)
                    ),
                    List.of(
                            new ApplyEffectsConsumeEffect(
                                    List.of(
                                            new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1)
                                    )
                            )
                    )
            ))
            .build();
}
