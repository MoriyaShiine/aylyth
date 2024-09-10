package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythParticleTypes {

	DefaultParticleType PILOT_LIGHT = register("pilot_light", FabricParticleTypes.simple(true));
	DefaultParticleType AMBIENT_PILOT_LIGHT = register("ambient_pilot_light", FabricParticleTypes.simple(true));
	DefaultParticleType HIND_SMOKE = register("hind_smoke", FabricParticleTypes.simple(true));
	DefaultParticleType VAMPIRIC_DRIP = register("vampiric_drip", FabricParticleTypes.simple(true));
	DefaultParticleType VAMPIRIC_LAND = register("vampiric_land", FabricParticleTypes.simple(true));
	DefaultParticleType BLIGHT_DRIP = register("blight_drip", FabricParticleTypes.simple(true));
	DefaultParticleType BLIGHT_LAND = register("blight_land", FabricParticleTypes.simple(true));

	private static <E extends ParticleEffect, P extends ParticleType<E>> P register(String id, P particleType) {
		return Registry.register(Registries.PARTICLE_TYPE, AylythUtil.id(id), particleType);
	}

	// Load static initializer
	static void register() {}
}
