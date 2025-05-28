package moriyashiine.aylyth.common.item.attack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.item.AttackEffectTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.world.ServerWorld;

import java.util.List;
import java.util.Optional;

public record ApplyEffects(List<EffectConfig> effects, LootContext.EntityTarget target) implements AttackEffect {
    public static final MapCodec<ApplyEffects> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    EffectConfig.CODEC.listOf().fieldOf("effects").forGetter(ApplyEffects::effects),
                    LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(ApplyEffects::target)
            ).apply(instance, ApplyEffects::new)
    );
    public static final PacketCodec<RegistryByteBuf, ApplyEffects> PACKET_CODEC = PacketCodec.tuple(
            EffectConfig.PACKET_CODEC.collect(PacketCodecs.toList()), ApplyEffects::effects,
            PacketCodecs.codec(LootContext.EntityTarget.CODEC), ApplyEffects::target,
            ApplyEffects::new
    );

    @Override
    public Type<? extends AttackEffect> getType() {
        return AttackEffectTypes.APPLY_EFFECTS;
    }

    @Override
    public float modifyDamage(LootContext lootContext, float damageAmount) {
        ServerWorld world = lootContext.getWorld();
        Entity targetEntity = lootContext.get(target.getParameter());
        Entity attacker = lootContext.get(LootContextParameters.ATTACKING_ENTITY);
        if (targetEntity instanceof LivingEntity livingTarget) {
            for (EffectConfig config : effects) {
                if (world.random.nextFloat() < config.probability) {
                    continue;
                }
                if (!livingTarget.hasStatusEffect(config.effect.getEffectType())) {
                    livingTarget.addStatusEffect(new StatusEffectInstance(config.effect), attacker);
                } else {
                    if (config.amplifierIncrease.isEmpty()) {
                        continue;
                    }
                    AmplifierIncrease amplifierIncrease = config.amplifierIncrease.get();
                    StatusEffectInstance existing = livingTarget.getStatusEffect(config.effect.getEffectType());
                    if (existing.getAmplifier() < amplifierIncrease.maxAmplifier && world.random.nextFloat() <= amplifierIncrease.probability) {
                        livingTarget.removeStatusEffect(config.effect.getEffectType());
                        livingTarget.addStatusEffect(new StatusEffectInstance(
                                config.effect.getEffectType(),
                                config.effect.getDuration(),
                                existing.getAmplifier() + 1,
                                config.effect.isAmbient(),
                                config.effect.shouldShowParticles(),
                                config.effect.shouldShowIcon()
                        ));
                    }
                }
            }
        }
        return damageAmount;
    }

    public record EffectConfig(StatusEffectInstance effect, float probability, Optional<AmplifierIncrease> amplifierIncrease) {
        public static final Codec<EffectConfig> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        StatusEffectInstance.CODEC.fieldOf("effect").forGetter(EffectConfig::effect),
                        Codec.FLOAT.fieldOf("probability").forGetter(EffectConfig::probability),
                        AmplifierIncrease.CODEC.optionalFieldOf("amplifier_increment").forGetter(EffectConfig::amplifierIncrease)
                ).apply(instance, EffectConfig::new)
        );
        public static final PacketCodec<RegistryByteBuf, EffectConfig> PACKET_CODEC = PacketCodec.tuple(
                StatusEffectInstance.PACKET_CODEC, EffectConfig::effect,
                PacketCodecs.FLOAT, EffectConfig::probability,
                PacketCodecs.optional(AmplifierIncrease.PACKET_CODEC), EffectConfig::amplifierIncrease,
                EffectConfig::new
        );
    }

    public record AmplifierIncrease(float probability, int maxAmplifier) {
        public static final Codec<AmplifierIncrease> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.FLOAT.fieldOf("probability").forGetter(AmplifierIncrease::probability),
                        Codec.INT.fieldOf("max_amplifier").forGetter(AmplifierIncrease::maxAmplifier)
                ).apply(instance, AmplifierIncrease::new)
        );
        public static final PacketCodec<RegistryByteBuf, AmplifierIncrease> PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.FLOAT, AmplifierIncrease::probability,
                PacketCodecs.INTEGER, AmplifierIncrease::maxAmplifier,
                AmplifierIncrease::new
        );
    }
}
