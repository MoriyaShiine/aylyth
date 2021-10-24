package moriyashiine.aylyth.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModBiomes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

@Environment(EnvType.CLIENT)
public class AylythDimensionRenderer {
	public static int goalFogStrength = 0;
	private static float currentFogStrength;
	
	public static void renderFog() {
		RenderSystem.setShaderFogStart(0F);
		RenderSystem.setShaderFogEnd(currentFogStrength);
		if (goalFogStrength < currentFogStrength) {
			currentFogStrength -= 0.1F;
		}
		else if (goalFogStrength > currentFogStrength) {
			currentFogStrength += 0.1F;
		}
	}
	
	public static void determineConditions(ClientWorld world, Biome biome) {
		if (world.getDimension().getSkyProperties().getNamespace().equals(Aylyth.MOD_ID)) {
			Identifier biomeId = world.getRegistryManager().get(Registry.BIOME_KEY).getId(biome);
			if (biomeId == ModBiomes.CLEARING_ID) {
				goalFogStrength = 40;
			}
			else if (biomeId == ModBiomes.OVERGROWN_CLEARING_ID) {
				goalFogStrength = 24;
			}
			else {
				goalFogStrength = 16;
			}
		}
		else {
			goalFogStrength = 0;
		}
	}
}
