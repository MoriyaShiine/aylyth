package moriyashiine.aylyth.common.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ThornFlechetteEffect(StatusEffectInstance statusEffectInstance, float chance) {
    public static final Codec<ThornFlechetteEffect> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    StatusEffectInstance.CODEC.fieldOf("effect").forGetter(ThornFlechetteEffect::statusEffectInstance),
                    Codec.FLOAT.fieldOf("chance").forGetter(ThornFlechetteEffect::chance)
            ).apply(instance, ThornFlechetteEffect::new)
    );
    public static final PacketCodec<? super RegistryByteBuf, ThornFlechetteEffect> PACKET_CODEC = PacketCodec.tuple(
            StatusEffectInstance.PACKET_CODEC, ThornFlechetteEffect::statusEffectInstance,
            PacketCodecs.FLOAT, ThornFlechetteEffect::chance,
            ThornFlechetteEffect::new
    );

    public void apply(LivingEntity target) {
        if (target.getRandom().nextFloat() < chance) {
            target.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
        }
    }
}
