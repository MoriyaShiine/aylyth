package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.advancement.renderdata.AdvancementRendererDataType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

// TODO move to advancement package?
public interface AylythRegistryKeys {

    RegistryKey<Registry<AdvancementRendererDataType<?>>> REGISTRY_KEY = RegistryKey.ofRegistry(Aylyth.id("advancement_renderer_data"));
}
