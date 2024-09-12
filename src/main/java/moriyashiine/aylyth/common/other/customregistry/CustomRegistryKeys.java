package moriyashiine.aylyth.common.other.customregistry;

import moriyashiine.aylyth.common.advancement.renderdata.AdvancementRendererDataType;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

// TODO regroup
public class CustomRegistryKeys {
    public static final RegistryKey<Registry<AdvancementRendererDataType<?>>> REGISTRY_KEY = RegistryKey.ofRegistry(AylythUtil.id("advancement_renderer_data"));
}
