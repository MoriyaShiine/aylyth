package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.item.attack.ApplyEffects;
import moriyashiine.aylyth.common.item.attack.Heal;
import moriyashiine.aylyth.common.item.attack.ModifyAbsorption;
import moriyashiine.aylyth.common.item.attack.ModifyDamage;
import moriyashiine.aylyth.common.item.attack.Operation;
import moriyashiine.aylyth.common.item.attack.SpawnParticlesAround;
import moriyashiine.aylyth.common.item.components.AttackEffects;
import moriyashiine.aylyth.common.loot.condition.ArmorLootCondition;
import moriyashiine.aylyth.common.loot.condition.CriticalHitLootCondition;
import moriyashiine.aylyth.common.particle.AylythParticleTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

import java.util.List;
import java.util.Optional;

public interface AylythAttackEffectComponents {
    ModifyAbsorption SWORD_ABSORPTION = new ModifyAbsorption(0.5f, Operation.MULTIPLY, LootContext.EntityTarget.ATTACKER);
    ModifyDamage PICKAXE_DAMAGE = new ModifyDamage(1.2f, Operation.MULTIPLY);
    LootCondition PICKAXE_CONDITION = ArmorLootCondition.builder().atLeast(10).build();

    Heal VAMPIRIC_HEALING = new Heal(ConstantLootNumberProvider.create(0.5f), Operation.MULTIPLY, LootContext.EntityTarget.ATTACKER);
    SpawnParticlesAround VAMPIRIC_PARTICLES = new SpawnParticlesAround(32, List.of(AylythParticleTypes.VAMPIRIC_DRIP), LootContext.EntityTarget.ATTACKER);
    LootCondition VAMPIRIC_CONDITION = RandomChanceLootCondition.builder(0.2f).and(CriticalHitLootCondition.builder()).build();
    AttackEffects VAMPIRIC_AXE = vampiric().build();
    AttackEffects VAMPIRIC_SWORD = vampiric()
            .addEffect(SWORD_ABSORPTION)
            .build();
    AttackEffects VAMPIRIC_PICKAXE = vampiric()
            .addEffect(PICKAXE_DAMAGE, PICKAXE_CONDITION)
            .build();
    AttackEffects VAMPIRIC_HOE = vampiric()
            .addEffect(new ApplyEffects(List.of(
                    new ApplyEffects.EffectConfig(
                            new StatusEffectInstance(AylythStatusEffects.CRIMSON_CURSE, 20 * 10, 0),
                            1.0f,
                            Optional.empty()
                    )),
                    LootContext.EntityTarget.THIS
            ))
            .build();
    ApplyEffects BLIGHTED_EFFECT = new ApplyEffects(List.of(
            new ApplyEffects.EffectConfig(
                    new StatusEffectInstance(AylythStatusEffects.BLIGHT, 20 * 4, 0),
                    1.0f,
                    Optional.of(
                            new ApplyEffects.AmplifierIncrease(0.85f, 1)
                    )
            )
    ), LootContext.EntityTarget.THIS);
    SpawnParticlesAround BLIGHTED_PARTICLES = new SpawnParticlesAround(32, List.of(AylythParticleTypes.BLIGHT_DRIP), LootContext.EntityTarget.THIS);
    LootCondition BLIGHTED_CONDITION = RandomChanceLootCondition.builder(0.25f).and(CriticalHitLootCondition.builder()).build();
    AttackEffects BLIGHTED_AXE = blighted()
            .build();
    AttackEffects BLIGHTED_SWORD = blighted()
            .addEffect(SWORD_ABSORPTION)
            .build();
    AttackEffects BLIGHTED_PICKAXE = blighted()
            .addEffect(PICKAXE_DAMAGE, PICKAXE_CONDITION)
            .build();
    AttackEffects BLIGHTED_HOE = blighted()
            .addEffect(new ApplyEffects(List.of(
                    new ApplyEffects.EffectConfig(
                            new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 2, 0),
                            1.0f,
                            Optional.empty()
                    )),
                    LootContext.EntityTarget.THIS
            ))
            .build();

    static AttackEffects.Builder vampiric() {
        return AttackEffects.builder()
                .addEffect(VAMPIRIC_HEALING)
                .addEffect(VAMPIRIC_PARTICLES)
                .condition(VAMPIRIC_CONDITION);
    }

    static AttackEffects.Builder blighted() {
        return AttackEffects.builder()
                .addEffect(BLIGHTED_EFFECT)
                .addEffect(BLIGHTED_PARTICLES)
                .condition(BLIGHTED_CONDITION);
    }
}
