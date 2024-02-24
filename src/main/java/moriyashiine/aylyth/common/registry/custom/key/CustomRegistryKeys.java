package moriyashiine.aylyth.common.registry.custom.key;

import moriyashiine.aylyth.common.advancement.AdvancementRendererDataType;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class CustomRegistryKeys {
    public static final RegistryKey<Registry<AdvancementRendererDataType<?>>> REGISTRY_KEY = RegistryKey.ofRegistry(AylythUtil.id("advancement_renderer_data"));
}
