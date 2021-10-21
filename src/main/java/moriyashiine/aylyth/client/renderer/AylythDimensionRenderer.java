package moriyashiine.aylyth.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.biome.Biome;

@Environment(EnvType.CLIENT)
public class AylythDimensionRenderer {
	public static int aylythDepth = 0;
	
	public static void renderFog() {
		RenderSystem.setShaderFogStart(0F);
		RenderSystem.setShaderFogEnd(64F - aylythDepth * 16);
	}
	
	public static void determineConditions(ClientWorld world, Biome biome) {
		if (world.getDimension().getSkyProperties().getNamespace().equals(Aylyth.MOD_ID)) {
			aylythDepth = 3; //todo set depth based on biome
		}
		else {
			aylythDepth = 0;
		}
	}
}
