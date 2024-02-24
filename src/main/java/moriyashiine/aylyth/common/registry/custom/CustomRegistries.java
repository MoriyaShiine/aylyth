package moriyashiine.aylyth.common.registry.custom;

import moriyashiine.aylyth.common.advancement.AdvancementRendererDataType;
import moriyashiine.aylyth.common.registry.custom.key.CustomRegistryKeys;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;

public class CustomRegistries {
    public static void init() {}
    public static final Registry<AdvancementRendererDataType<?>> ADVANCEMENT_RENDERER_DATA_TYPE = FabricRegistryBuilder.createSimple(CustomRegistryKeys.REGISTRY_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();
}
