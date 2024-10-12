package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.advancement.renderdata.AdvancementRendererDataType;
import moriyashiine.aylyth.common.loot.display.LootDisplay;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;

// TODO move to advancement package?
public interface AylythRegistries {

    Registry<AdvancementRendererDataType<?>> ADVANCEMENT_RENDERER_DATA_TYPE = FabricRegistryBuilder.createSimple(AylythRegistryKeys.ADVANCEMENT_RENDERER_DATA).attribute(RegistryAttribute.SYNCED).buildAndRegister();
    Registry<LootDisplay.Type<?>> LOOT_TABLE_DISPLAY_TYPE = FabricRegistryBuilder.createSimple(AylythRegistryKeys.LOOT_TABLE_DISPLAY_TYPE).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    // Load static initializer
    static void register() {}
}
