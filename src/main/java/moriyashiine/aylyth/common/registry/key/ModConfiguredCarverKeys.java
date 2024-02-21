package moriyashiine.aylyth.common.registry.key;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.carver.ConfiguredCarver;

public class ModConfiguredCarverKeys {
    public static final RegistryKey<ConfiguredCarver<?>> CAVES = RegistryKey.of(RegistryKeys.CONFIGURED_CARVER, AylythUtil.id("caves"));
    public static final RegistryKey<ConfiguredCarver<?>> CANYONS = RegistryKey.of(RegistryKeys.CONFIGURED_CARVER, AylythUtil.id("canyons"));
}
