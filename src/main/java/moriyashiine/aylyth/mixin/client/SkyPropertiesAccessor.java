package moriyashiine.aylyth.mixin.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import moriyashiine.aylyth.client.render.AylythDimensionRenderer;
import moriyashiine.aylyth.common.registry.ModDimensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SkyProperties.class)
@Environment(EnvType.CLIENT)
public interface SkyPropertiesAccessor {
    @Accessor(value = "BY_IDENTIFIER")
    static Object2ObjectMap<Identifier, SkyProperties> getByIdentifier() {
        throw new UnsupportedOperationException();
    }
}
