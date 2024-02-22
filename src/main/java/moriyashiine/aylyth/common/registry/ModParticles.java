package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModParticles {
	public static final DefaultParticleType PILOT_LIGHT = register("pilot_light", FabricParticleTypes.simple(true));
	public static final DefaultParticleType AMBIENT_PILOT_LIGHT = register("ambient_pilot_light", FabricParticleTypes.simple(true));
	public static final DefaultParticleType HIND_SMOKE = register("hind_smoke", FabricParticleTypes.simple(true));
	public static final DefaultParticleType VAMPIRIC_DRIP = register("vampiric_drip", FabricParticleTypes.simple(true));
	public static final DefaultParticleType VAMPIRIC_LAND = register("vampiric_land", FabricParticleTypes.simple(true));
	public static final DefaultParticleType BLIGHT_DRIP = register("blight_drip", FabricParticleTypes.simple(true));
	public static final DefaultParticleType BLIGHT_LAND = register("blight_land", FabricParticleTypes.simple(true));

	private static <P extends ParticleType<T>, T extends ParticleEffect> P register(String id, P particleType) {
		Registry.register(Registries.PARTICLE_TYPE, AylythUtil.id(id), particleType);
		return particleType;
	}

	public static void init() {}
}
