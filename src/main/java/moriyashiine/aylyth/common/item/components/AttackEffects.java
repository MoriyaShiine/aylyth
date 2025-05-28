package moriyashiine.aylyth.common.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.item.attack.AttackEffect;
import moriyashiine.aylyth.common.loot.AylythLootContextParameters;
import moriyashiine.aylyth.common.loot.AylythLootContextTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;
import java.util.Optional;

public record AttackEffects(List<AttackEffectConfig> attackEffects, Optional<LootCondition> condition) {
    public static final Codec<AttackEffects> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    AttackEffectConfig.CODEC.listOf().fieldOf("effects").forGetter(AttackEffects::attackEffects),
                    LootCondition.CODEC.optionalFieldOf("condition").forGetter(AttackEffects::condition)
            ).apply(instance, AttackEffects::new)
    );
    public static final PacketCodec<RegistryByteBuf, AttackEffects> PACKET_CODEC = PacketCodec.tuple(
            AttackEffectConfig.PACKET_CODEC.collect(PacketCodecs.toList()), AttackEffects::attackEffects,
            attackEffectConfigs -> new AttackEffects(attackEffectConfigs, Optional.empty())
    );

    public float apply(ServerWorld world, LivingEntity target, DamageSource source, float damageAmount) {
        LootWorldContext.Builder worldContextBuilder = new LootWorldContext.Builder(world)
                .add(LootContextParameters.THIS_ENTITY, target)
                .add(LootContextParameters.ORIGIN, target.getPos())
                .add(LootContextParameters.DAMAGE_SOURCE, source)
                .addOptional(LootContextParameters.ATTACKING_ENTITY, source.getAttacker())
                .addOptional(LootContextParameters.DIRECT_ATTACKING_ENTITY, source.getSource());

        if (target.getLastAttacker() instanceof PlayerEntity playerEntity) {
            worldContextBuilder = worldContextBuilder
                    .addOptional(LootContextParameters.LAST_DAMAGE_PLAYER, playerEntity).luck(playerEntity.getLuck());
        }

        if (source.getAttacker() instanceof LivingEntity attacker) {
            double baseAttack = attacker.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
            if (damageAmount >= baseAttack) {
                worldContextBuilder = worldContextBuilder.addOptional(AylythLootContextParameters.CRITICAL, Unit.INSTANCE);
            }
        }

        LootContext context = new LootContext.Builder(worldContextBuilder.build(AylythLootContextTypes.ATTACK))
                .build(Optional.empty());

        if (condition.isEmpty() || condition.get().test(context)) {
            MutableFloat modifiedDamage = new MutableFloat(damageAmount);
            for (AttackEffectConfig config : attackEffects) {
                if (config.condition.isEmpty() || config.condition.get().test(context)) {
                    modifiedDamage.setValue(config.attackEffect.modifyDamage(context, modifiedDamage.getValue()));
                }
            }
            return modifiedDamage.getValue();
        }
        return damageAmount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public record AttackEffectConfig(AttackEffect attackEffect, Optional<LootCondition> condition) {
        public static final Codec<AttackEffectConfig> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        AttackEffect.CODEC.fieldOf("effect").forGetter(AttackEffectConfig::attackEffect),
                        LootCondition.CODEC.optionalFieldOf("condition").forGetter(AttackEffectConfig::condition)
                ).apply(instance, AttackEffectConfig::new)
        );
        public static final PacketCodec<RegistryByteBuf, AttackEffectConfig> PACKET_CODEC = PacketCodec.tuple(
                AttackEffect.PACKET_CODEC, AttackEffectConfig::attackEffect,
                effect -> new AttackEffectConfig(effect, Optional.empty())
        );
    }

    public static class Builder {
        private final List<AttackEffectConfig> attackEffects;
        private LootCondition condition;

        Builder() {
            this.attackEffects = new ObjectArrayList<>();
        }

        public Builder addEffect(AttackEffect effect) {
            attackEffects.add(new AttackEffectConfig(effect, Optional.empty()));
            return this;
        }

        public Builder addEffect(AttackEffect effect, LootCondition condition) {
            attackEffects.add(new AttackEffectConfig(effect, Optional.of(condition)));
            return this;
        }

        public Builder addEffect(AttackEffect effect, LootCondition.Builder conditionBuilder) {
            return addEffect(effect, conditionBuilder.build());
        }

        public Builder condition(LootCondition condition) {
            this.condition = condition;
            return this;
        }

        public Builder condition(LootCondition.Builder conditionBuilder) {
            return condition(conditionBuilder.build());
        }

        public Builder probability(float probability) {
            return condition(new RandomChanceLootCondition(ConstantLootNumberProvider.create(probability)));
        }

        public AttackEffects build() {
            return new AttackEffects(new ObjectArrayList<>(this.attackEffects), Optional.ofNullable(condition));
        }
    }
}
