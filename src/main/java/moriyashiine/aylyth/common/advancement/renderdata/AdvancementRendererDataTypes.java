package moriyashiine.aylyth.common.advancement.renderdata;

import moriyashiine.aylyth.common.other.customregistry.CustomRegistries;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.registry.Registry;

public interface AdvancementRendererDataTypes {

    AdvancementRendererDataType<TextureRendererData> TEXTURE_RENDERER_DATA = register("texture", () -> TextureRendererData.CODEC);

    private static <T extends AdvancementRendererDataType<?>> T register(String name, T type) {
        return Registry.register(CustomRegistries.ADVANCEMENT_RENDERER_DATA_TYPE, AylythUtil.id(name), type);
    }

    // Load static initializer
    static void register() {}
}
