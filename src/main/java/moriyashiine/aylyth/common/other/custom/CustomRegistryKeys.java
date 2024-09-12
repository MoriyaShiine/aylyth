package moriyashiine.aylyth.common.other.custom;

import moriyashiine.aylyth.common.advancement.AdvancementRendererDataType;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

// TODO regroup
public class CustomRegistryKeys {
    public static final RegistryKey<Registry<AdvancementRendererDataType<?>>> REGISTRY_KEY = RegistryKey.ofRegistry(AylythUtil.id("advancement_renderer_data"));
}
