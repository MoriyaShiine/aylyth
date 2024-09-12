package moriyashiine.aylyth.common.other.customregistry;

import moriyashiine.aylyth.common.advancement.renderdata.AdvancementRendererDataType;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;

// TODO regroup
public class CustomRegistries {
    public static void init() {}
    public static final Registry<AdvancementRendererDataType<?>> ADVANCEMENT_RENDERER_DATA_TYPE = FabricRegistryBuilder.createSimple(CustomRegistryKeys.REGISTRY_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();
}
