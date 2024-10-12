package moriyashiine.aylyth.datagen.common.loot;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.loot.display.LootDisplay;
import moriyashiine.aylyth.common.loot.display.DaggerLootDisplay;
import moriyashiine.aylyth.common.registry.AylythRegistryKeys;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;

public final class LootDisplayBootstrap {
    private LootDisplayBootstrap() {}

    public static void bootstrap(Registerable<LootDisplay> registerable) {
        registerable.register(key("creeper_head_from_creeper"), DaggerLootDisplay.create(EntityType.CREEPER, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.CREEPER_HEAD));
        registerable.register(key("player_head_from_player"), DaggerLootDisplay.create(EntityType.PLAYER, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.PLAYER_HEAD));
        registerable.register(key("skeleton_skull_from_skeleton"), DaggerLootDisplay.create(EntityType.SKELETON, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.SKELETON_SKULL));
        registerable.register(key("skeleton_skull_from_stray"), DaggerLootDisplay.create(EntityType.STRAY, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.SKELETON_SKULL));
        registerable.register(key("wither_skeleton_skull_from_wither_skeleton"), DaggerLootDisplay.create(EntityType.WITHER_SKELETON, 0.025f, AylythItemTags.FLESH_HARVESTERS, Items.WITHER_SKELETON_SKULL));
        registerable.register(key("zombie_head_from_husk"), DaggerLootDisplay.create(EntityType.HUSK, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.ZOMBIE_HEAD));
        registerable.register(key("zombie_head_from_zombie"), DaggerLootDisplay.create(EntityType.ZOMBIE, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.ZOMBIE_HEAD));

        registerable.register(key("wrongmeat_from_aylythian"), DaggerLootDisplay.create(AylythEntityTypes.AYLYTHIAN, 0.15f, AylythItemTags.FLESH_HARVESTERS, AylythItems.WRONGMEAT));
        registerable.register(key("wrongmeat_from_elder_aylythian"), DaggerLootDisplay.create(AylythEntityTypes.ELDER_AYLYTHIAN, 0.2f, AylythItemTags.FLESH_HARVESTERS, AylythItems.WRONGMEAT));
        registerable.register(key("wrongmeat_from_scion"), DaggerLootDisplay.create(AylythEntityTypes.SCION, 0.15f, AylythItemTags.FLESH_HARVESTERS, AylythItems.WRONGMEAT));
        registerable.register(key("wrongmeat_from_wreathed_hind"), DaggerLootDisplay.create(AylythEntityTypes.WREATHED_HIND_ENTITY, 0.2f, AylythItemTags.FLESH_HARVESTERS, AylythItems.WRONGMEAT));
    }

    private static RegistryKey<LootDisplay> key(String id) {
        return RegistryKey.of(AylythRegistryKeys.LOOT_TABLE_DISPLAY, Aylyth.id(id));
    }
}
