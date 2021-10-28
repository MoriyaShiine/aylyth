package moriyashiine.aylyth.mixin.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SkyProperties.class)
@Environment(EnvType.CLIENT)
public interface SkyPropertiesAccessor {
	@Accessor(value = "BY_IDENTIFIER")
	static Object2ObjectMap<Identifier, SkyProperties> aylyth_getByIdentifier() {
		throw new UnsupportedOperationException();
	}
}
