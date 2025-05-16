package moriyashiine.aylyth.client.particle;

import moriyashiine.aylyth.common.particle.AylythParticleTypes;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.BlockLeakParticle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.ColorHelper;

public class ParticleFactories {
    public static ParticleFactory<SimpleParticleType> createVampiricDrip(FabricSpriteProvider spriteProvider) {
        return (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
            HoneyLike particle = new HoneyLike(world, x, y, z, Fluids.EMPTY, AylythParticleTypes.VAMPIRIC_LAND);
            particle.setGravityStrength(0.01f);
            particle.setColor(ColorHelper.getRed(0x79181D) / 255f, ColorHelper.getGreen(0x79181D) / 255f, ColorHelper.getBlue(0x79181D) / 255f);
            particle.setSprite(spriteProvider);
            return particle;
        };
    }

    public static ParticleFactory<SimpleParticleType> createVampiricLand(FabricSpriteProvider spriteProvider) {
        return (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
            SpriteBillboardParticle particle = BlockLeakParticle.createLandingHoney(parameters, world, x, y, z, velocityX, velocityY, velocityZ);
            particle.setColor(ColorHelper.getRed(0x79181D) / 255f, ColorHelper.getGreen(0x79181D) / 255f, ColorHelper.getBlue(0x79181D) / 255f);
            particle.setSprite(spriteProvider);
            return particle;
        };
    }

    public static ParticleFactory<SimpleParticleType> createBlightDrip(FabricSpriteProvider spriteProvider) {
        return (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
            HoneyLike particle = new HoneyLike(world, x, y, z, Fluids.EMPTY, AylythParticleTypes.BLIGHT_LAND);
            particle.setGravityStrength(0.01f);
            particle.setColor(ColorHelper.getRed(0x38352A) / 255f, ColorHelper.getGreen(0x38352A) / 255f, ColorHelper.getBlue(0x38352A) / 255f);
            particle.setSprite(spriteProvider);
            return particle;
        };
    }

    public static ParticleFactory<SimpleParticleType> createBlightLand(FabricSpriteProvider spriteProvider) {
        return (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
            SpriteBillboardParticle particle = BlockLeakParticle.createLandingHoney(parameters, world, x, y, z, velocityX, velocityY, velocityZ);
            particle.setColor(ColorHelper.getRed(0x38352A) / 255f, ColorHelper.getGreen(0x38352A) / 255f, ColorHelper.getBlue(0x38352A) / 255f);
            particle.setSprite(spriteProvider);
            return particle;
        };
    }

    public static class HoneyLike extends BlockLeakParticle.FallingHoney {
        public HoneyLike(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, ParticleEffect particleEffect) {
            super(clientWorld, d, e, f, fluid, particleEffect);
        }

        public void setGravityStrength(float strength) {
            this.gravityStrength = strength;
        }
    }
}
