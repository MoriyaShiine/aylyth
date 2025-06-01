package moriyashiine.aylyth.common.registry;

import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.item.attack.AttackEffect;
import moriyashiine.aylyth.common.loot.display.LootDisplay;
import moriyashiine.aylyth.common.loot.modifier.LootTableModifier;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;

public interface AylythRegistries {
    Registry<MapCodec<? extends LootTableModifier>> LOOT_TABLE_MODIFIER_TYPE = FabricRegistryBuilder.createSimple(AylythRegistryKeys.LOOT_TABLE_MODIFIER_TYPE)
            .buildAndRegister();
    Registry<LootDisplay.Type<?>> LOOT_TABLE_DISPLAY_TYPE = FabricRegistryBuilder.createSimple(AylythRegistryKeys.LOOT_TABLE_DISPLAY_TYPE)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();
    Registry<AttackEffect.Type<?>> ATTACK_EFFECT_TYPE = FabricRegistryBuilder.createSimple(AylythRegistryKeys.ATTACK_EFFECT_TYPE)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    // Load static initializer
    static void register() {}
}
