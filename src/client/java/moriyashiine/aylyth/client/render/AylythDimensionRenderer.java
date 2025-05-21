package moriyashiine.aylyth.client.render;

import moriyashiine.aylyth.common.data.tag.AylythBiomeTags;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import org.joml.Vector4f;

public class AylythDimensionRenderer {
	public static final DimensionEffects DIMENSION_EFFECTS = new AylythDimensionEffects();
	public static int goalFogStrength = 0;
	private static float currentFogStrength;

	public static Fog applyFog(Camera camera, FogShape shape, Vector4f color) {
		float fogStrength = currentFogStrength;
		if (camera.getSubmersionType() == CameraSubmersionType.WATER) {
			ClientPlayerEntity player = MinecraftClient.getInstance().player;
			ClientWorld world = MinecraftClient.getInstance().world;
			if (world.getBiome(player.getBlockPos()).isIn(BiomeTags.HAS_CLOSER_WATER_FOG)) {
				fogStrength *= 0.75f;
			}
		}
		if (goalFogStrength < currentFogStrength) {
			currentFogStrength -= 0.1F;
		} else if (goalFogStrength > currentFogStrength) {
			currentFogStrength += 0.1F;
		}
		return new Fog(0F, fogStrength, shape, color.x, color.y, color.z, color.w);
	}

	public static void determineConditions(RegistryEntry<Biome> biome) {
		if (biome.isIn(AylythBiomeTags.HAS_WEAK_FOG)) {
			goalFogStrength = 40;
		} else if (biome.isIn(AylythBiomeTags.HAS_AVERAGE_FOG)) {
			goalFogStrength = 24;
		} else if (biome.isIn(AylythBiomeTags.HAS_STRONG_FOG)) {
			goalFogStrength = 16;
		} else {
			goalFogStrength = 0;
		}
	}

	private static class AylythDimensionEffects extends DimensionEffects {
		public AylythDimensionEffects() {
			super(-70, false, SkyType.NONE, false, true);
		}

		@Override
		public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
			return color;
		}

		@Override
		public boolean useThickFog(int camX, int camY) {
			return false;
		}
	}
}
