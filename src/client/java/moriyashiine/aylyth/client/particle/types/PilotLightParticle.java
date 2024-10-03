package moriyashiine.aylyth.client.particle.types;

import moriyashiine.aylyth.client.particle.AylythParticleTextureSheets;
import net.minecraft.client.particle.AbstractSlowingParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class PilotLightParticle extends AbstractSlowingParticle {
	protected boolean fadeIn = true;
	protected float targetScale;
	
	protected PilotLightParticle(ClientWorld clientWorld, double x, double y, double z, float r, float g, float b) {
		super(clientWorld, x, y, z, 0, Math.max(0.01F, clientWorld.random.nextFloat() / 50), 0);
		alpha = 0;
		red = r;
		green = g;
		blue = b;
		this.targetScale = 0.5F;
		maxAge = 40 + clientWorld.random.nextInt(40);
	}
	
	public ParticleTextureSheet getType() {
		return AylythParticleTextureSheets.GLOWING;
	}
	
	public void move(double dx, double dy, double dz) {
		this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
		this.repositionFromBoundingBox();
	}
	
	@Override
	public void tick() {
		super.tick();
		float lifeRatio = (float) this.age / (float) this.maxAge;
		if (lifeRatio >= 0.5F) {
			scale(0.95F);
		}
		else {
			this.alpha = lifeRatio * 2;
		}
		if (lifeRatio >= 1) {
			markDead();
		}
	}
	
	public int getBrightness(float tint) {
		float f = ((float) this.age + tint) / (float) this.maxAge;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		int i = super.getBrightness(tint);
		int j = i & 255;
		int k = i >> 16 & 255;
		j += (int) (f * 15.0F * 16.0F);
		if (j > 240) {
			j = 240;
		}
		return j | k << 16;
	}

	public static class AmbientFactory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;
		
		public AmbientFactory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ) {
			PilotLightParticle particle = new PilotLightParticle(clientWorld, x, y, z, MathHelper.nextFloat(clientWorld.random, 0.8F, 1F), MathHelper.nextFloat(clientWorld.random, 0.5F, 0.6F), MathHelper.nextFloat(clientWorld.random, 0.05F, 0.1F));
			particle.targetScale = MathHelper.nextFloat(clientWorld.random, 0.5F, 0.75F);
			particle.scale(particle.targetScale);
			particle.velocityX = velX;
			particle.velocityY = velY;
			particle.velocityZ = velZ;
			particle.setSpriteForAge(this.spriteProvider);
			return particle;
		}
	}

	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;
		
		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velX, double velY, double velZ) {
			PilotLightParticle particle = new PilotLightParticle(clientWorld, x, y, z, MathHelper.nextFloat(clientWorld.random, 0.8F, 1F), MathHelper.nextFloat(clientWorld.random, 0.5F, 0.6F), MathHelper.nextFloat(clientWorld.random, 0.05F, 0.1F));
			particle.targetScale = MathHelper.nextFloat(clientWorld.random, 1F, 1.5F);
			particle.scale(particle.targetScale);
			particle.red = (float) velX;
			particle.green = (float) velY;
			particle.blue = (float) velZ;
			particle.setSpriteForAge(this.spriteProvider);
			return particle;
		}
	}
}