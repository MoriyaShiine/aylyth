package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.item.attack.AttackEffect;
import moriyashiine.aylyth.common.loot.display.LootDisplay;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public interface AylythRegistryKeys {

    RegistryKey<Registry<LootDisplay.Type<?>>> LOOT_TABLE_DISPLAY_TYPE = RegistryKey.ofRegistry(Aylyth.id("loot_display_type"));
    RegistryKey<Registry<AttackEffect.Type<?>>> ATTACK_EFFECT_TYPE = RegistryKey.ofRegistry(Aylyth.id("attack_effect_type"));

    RegistryKey<Registry<LootDisplay>> LOOT_TABLE_DISPLAY = RegistryKey.ofRegistry(Aylyth.id("loot_display"));
}
