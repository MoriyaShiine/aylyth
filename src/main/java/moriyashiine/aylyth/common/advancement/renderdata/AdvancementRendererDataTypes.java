package moriyashiine.aylyth.common.advancement.renderdata;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.AylythRegistries;
import net.minecraft.registry.Registry;

public interface AdvancementRendererDataTypes {

    AdvancementRendererDataType<TextureRendererData> TEXTURE_RENDERER_DATA = register("texture", () -> TextureRendererData.CODEC);

    private static <T extends AdvancementRendererDataType<?>> T register(String name, T type) {
        return Registry.register(AylythRegistries.ADVANCEMENT_RENDERER_DATA_TYPE, Aylyth.id(name), type);
    }

    // Load static initializer
    static void register() {}
}
