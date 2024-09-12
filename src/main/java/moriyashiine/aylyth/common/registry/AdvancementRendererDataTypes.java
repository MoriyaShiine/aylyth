package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.advancement.AdvancementRendererDataType;
import moriyashiine.aylyth.common.advancement.renderdata.TextureRendererData;
import moriyashiine.aylyth.common.registry.custom.CustomRegistries;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.Registry;

public interface AdvancementRendererDataTypes {

    AdvancementRendererDataType<TextureRendererData> TEXTURE_RENDERER_DATA = register("texture", () -> TextureRendererData.CODEC);

    private static <T extends AdvancementRendererDataType<?>> T register(String name, T type) {
        return Registry.register(CustomRegistries.ADVANCEMENT_RENDERER_DATA_TYPE, AylythUtil.id(name), type);
    }

    // Load static initializer
    public static void register() {}
}
