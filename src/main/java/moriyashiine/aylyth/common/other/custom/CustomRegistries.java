package moriyashiine.aylyth.common.other.custom;

import moriyashiine.aylyth.common.advancement.AdvancementRendererDataType;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;

// TODO regroup
public class CustomRegistries {
    public static void init() {}
    public static final Registry<AdvancementRendererDataType<?>> ADVANCEMENT_RENDERER_DATA_TYPE = FabricRegistryBuilder.createSimple(CustomRegistryKeys.REGISTRY_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();
}
