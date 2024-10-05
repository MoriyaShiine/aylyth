package moriyashiine.aylyth.common.particle.effects;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import moriyashiine.aylyth.common.particle.AylythParticleTypes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.AbstractDustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.ColorHelper;
import org.joml.Vector3f;

public class ColorableParticleEffect implements ParticleEffect {
    public static final ColorableParticleEffect SOUL_EMBER = new ColorableParticleEffect(AylythParticleTypes.SOUL_EMBER, 0x60F5FA);

    private final ParticleType<ColorableParticleEffect> particleType;
    private final Vector3f vectorColor;

    public ColorableParticleEffect(ParticleType<ColorableParticleEffect> particleType, int color) {
        this(particleType, new Vector3f(ColorHelper.Argb.getRed(color) / 255f, ColorHelper.Argb.getGreen(color) / 255f, ColorHelper.Argb.getBlue(color) / 255f));
    }

    public ColorableParticleEffect(ParticleType<ColorableParticleEffect> particleType, Vector3f color) {
        this.particleType = particleType;
        this.vectorColor = color;
    }

    public Vector3f getColor() {
        return vectorColor;
    }

    @Override
    public ParticleType<?> getType() {
        return particleType;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVector3f(this.vectorColor);
    }

    @Override
    public String asString() {
        return Registries.PARTICLE_TYPE.getId(this.getType()) + "[" + vectorColor + "]";
    }

    public static class Factory implements ParticleEffect.Factory<ColorableParticleEffect> {
        @Override
        public ColorableParticleEffect read(ParticleType<ColorableParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            return new ColorableParticleEffect(type, AbstractDustParticleEffect.readColor(reader));
        }

        @Override
        public ColorableParticleEffect read(ParticleType<ColorableParticleEffect> type, PacketByteBuf buf) {
            return new ColorableParticleEffect(type, buf.readVector3f());
        }
    }
}
