package moriyashiine.aylyth.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModBiomes;
import moriyashiine.aylyth.common.world.dimension.AylythBiomeSource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.Optional;

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
		} else {
			aylythDepth = 0;
		}
	}
}
