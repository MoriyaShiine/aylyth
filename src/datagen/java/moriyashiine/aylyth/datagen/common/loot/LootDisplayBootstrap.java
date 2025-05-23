package moriyashiine.aylyth.datagen.common.loot;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.loot.display.LootDisplay;
import moriyashiine.aylyth.common.loot.display.DaggerLootDisplay;
import moriyashiine.aylyth.common.registry.AylythRegistryKeys;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class LootDisplayBootstrap {
    private LootDisplayBootstrap() {}

    public static void bootstrap(Registerable<LootDisplay> registerable) {
        register(registerable, "creeper_head_from_creeper", EntityType.CREEPER, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.CREEPER_HEAD);
        register(registerable, "player_head_from_player", EntityType.PLAYER, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.PLAYER_HEAD);
        register(registerable, "skeleton_skull_from_skeleton", EntityType.SKELETON, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.SKELETON_SKULL);
        register(registerable, "skeleton_skull_from_stray", EntityType.STRAY, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.SKELETON_SKULL);
        register(registerable, "wither_skeleton_skull_from_wither_skeleton", EntityType.WITHER_SKELETON, 0.025f, AylythItemTags.FLESH_HARVESTERS, Items.WITHER_SKELETON_SKULL);
        register(registerable, "zombie_head_from_husk", EntityType.HUSK, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.ZOMBIE_HEAD);
        register(registerable, "zombie_head_from_zombie", EntityType.ZOMBIE, 0.2f, AylythItemTags.FLESH_HARVESTERS, Items.ZOMBIE_HEAD);

        register(registerable, "wrongmeat_from_aylythian", AylythEntityTypes.AYLYTHIAN, 0.15f, AylythItemTags.FLESH_HARVESTERS, AylythItems.WRONGMEAT);
        register(registerable, "wrongmeat_from_elder_aylythian", AylythEntityTypes.ELDER_AYLYTHIAN, 0.2f, AylythItemTags.FLESH_HARVESTERS, AylythItems.WRONGMEAT);
        register(registerable, "wrongmeat_from_scion", AylythEntityTypes.SCION, 0.15f, AylythItemTags.FLESH_HARVESTERS, AylythItems.WRONGMEAT);
        register(registerable, "wrongmeat_from_wreathed_hind", AylythEntityTypes.WREATHED_HIND_ENTITY, 0.2f, AylythItemTags.FLESH_HARVESTERS, AylythItems.WRONGMEAT);
    }
    
    private static void register(Registerable<LootDisplay> registerable, String id, EntityType<?> entity, float chance, TagKey<Item> weaponsTag, ItemConvertible output) {
        registerable.register(key(id), DaggerLootDisplay.create(entity, chance, registerable.getRegistryLookup(RegistryKeys.ITEM).getOrThrow(weaponsTag), output));
    }

    private static RegistryKey<LootDisplay> key(String id) {
        return RegistryKey.of(AylythRegistryKeys.LOOT_TABLE_DISPLAY, Aylyth.id(id));
    }
}
