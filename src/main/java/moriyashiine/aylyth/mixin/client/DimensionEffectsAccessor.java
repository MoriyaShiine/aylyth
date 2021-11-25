package moriyashiine.aylyth.mixin.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DimensionEffects.class)
@Environment(EnvType.CLIENT)
public interface DimensionEffectsAccessor {
	@Accessor(value = "BY_IDENTIFIER")
	static Object2ObjectMap<Identifier, DimensionEffects> aylyth_getByIdentifier() {
		throw new UnsupportedOperationException();
	}
}
