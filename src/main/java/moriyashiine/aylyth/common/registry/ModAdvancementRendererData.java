package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.advancement.AdvancementRendererDataType;
import moriyashiine.aylyth.common.advancement.renderdata.TextureRendererData;
import moriyashiine.aylyth.common.registry.custom.CustomRegistries;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModAdvancementRendererData {
    public static final AdvancementRendererDataType<TextureRendererData> TEXTURE_RENDERER_DATA = register("texture", () -> TextureRendererData.CODEC);

    private static <T extends AdvancementRendererDataType<?>> T register(String id, T type) {
        return Registry.register(CustomRegistries.ADVANCEMENT_RENDERER_DATA_TYPE, AylythUtil.id(id), type);
    }

    public static void init() {}
}
