package moriyashiine.aylyth.client.particle.types;

import moriyashiine.aylyth.common.particle.effects.ColorableParticleEffect;
import net.minecraft.client.particle.AbstractSlowingParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import org.joml.Vector3f;

public class SoulEmberParticle extends AbstractSlowingParticle {
    protected final SpriteProvider spriteProvider;

    protected SoulEmberParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
        this.maxAge = 9 * 4;
    }

    @Override
    public void tick() {
        super.tick();
        if (!dead) {
            this.setSprite(this.spriteProvider.getSprite(age, maxAge));
        }
    }

    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<ColorableParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(ColorableParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            SoulEmberParticle particle = new SoulEmberParticle(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
            Vector3f color = parameters.getColor();
            particle.setColor(color.x(), color.y(), color.z());
            particle.setAlpha(0.9f);
            particle.setSprite(spriteProvider);
            return particle;
        }
    }
}
