package moriyashiine.aylyth.common.data.world;

import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.carver.ConfiguredCarver;

public interface AylythConfiguredCarvers {

    RegistryKey<ConfiguredCarver<?>> CAVES = bind("caves");
    RegistryKey<ConfiguredCarver<?>> CANYONS = bind("canyons");

    private static RegistryKey<ConfiguredCarver<?>> bind(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_CARVER, AylythUtil.id(name));
    }
}
