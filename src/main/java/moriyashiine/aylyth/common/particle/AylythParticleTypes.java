package moriyashiine.aylyth.common.particle;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.particle.effects.ColorableParticleEffect;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythParticleTypes {

	SimpleParticleType PILOT_LIGHT = register("pilot_light", FabricParticleTypes.simple(true));
	SimpleParticleType AMBIENT_PILOT_LIGHT = register("ambient_pilot_light", FabricParticleTypes.simple(true));
	SimpleParticleType HIND_SMOKE = register("hind_smoke", FabricParticleTypes.simple(true));
	SimpleParticleType VAMPIRIC_DRIP = register("vampiric_drip", FabricParticleTypes.simple(true));
	SimpleParticleType VAMPIRIC_LAND = register("vampiric_land", FabricParticleTypes.simple(true));
	SimpleParticleType BLIGHT_DRIP = register("blight_drip", FabricParticleTypes.simple(true));
	SimpleParticleType BLIGHT_LAND = register("blight_land", FabricParticleTypes.simple(true));
	ParticleType<ColorableParticleEffect> SOUL_EMBER = register("soul_ember", FabricParticleTypes.complex(true, ColorableParticleEffect.CODEC, ColorableParticleEffect.PACKET_CODEC));

	private static <E extends ParticleEffect, P extends ParticleType<E>> P register(String id, P particleType) {
		return Registry.register(Registries.PARTICLE_TYPE, Aylyth.id(id), particleType);
	}

	// Load static initializer
	static void register() {}
}
