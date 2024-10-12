package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.advancement.renderdata.AdvancementRendererDataType;
import moriyashiine.aylyth.common.loot.display.LootDisplay;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

// TODO move to advancement package?
public interface AylythRegistryKeys {

    RegistryKey<Registry<AdvancementRendererDataType<?>>> ADVANCEMENT_RENDERER_DATA = RegistryKey.ofRegistry(Aylyth.id("advancement_renderer_data"));
    RegistryKey<Registry<LootDisplay.Type<?>>> LOOT_TABLE_DISPLAY_TYPE = RegistryKey.ofRegistry(Aylyth.id("loot_display_type"));

    RegistryKey<Registry<LootDisplay>> LOOT_TABLE_DISPLAY = RegistryKey.ofRegistry(Aylyth.id("loot_display"));
}
