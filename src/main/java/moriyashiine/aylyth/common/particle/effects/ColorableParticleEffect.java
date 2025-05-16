package moriyashiine.aylyth.common.particle.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.particle.AylythParticleTypes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.ColorHelper;
import org.joml.Vector3f;

public record ColorableParticleEffect(ParticleType<?> particleType, int color) implements ParticleEffect {
    public static final MapCodec<ColorableParticleEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Registries.PARTICLE_TYPE.getCodec().fieldOf("particle_type").forGetter(ColorableParticleEffect::particleType),
                    Codecs.RGB.fieldOf("color").forGetter(ColorableParticleEffect::color)
            ).apply(instance, ColorableParticleEffect::new)
    );
    public static final PacketCodec<? super RegistryByteBuf, ColorableParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.registryCodec(Registries.PARTICLE_TYPE.getCodec()), ColorableParticleEffect::particleType,
            PacketCodecs.INTEGER, ColorableParticleEffect::color,
            ColorableParticleEffect::new
    );

    public static final ColorableParticleEffect SOUL_EMBER = new ColorableParticleEffect(AylythParticleTypes.SOUL_EMBER, 0x60F5FA);

    @Override
    public ParticleType<?> getType() {
        return particleType;
    }

    @Override
    public String toString() {
        return Registries.PARTICLE_TYPE.getId(this.getType()) + "[" + Integer.toString(color, 16) + "]";
    }
}
