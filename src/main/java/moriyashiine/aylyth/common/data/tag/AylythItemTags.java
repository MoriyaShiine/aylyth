package moriyashiine.aylyth.common.data.tag;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public interface AylythItemTags {

    TagKey<Item> YMPE_LOGS = bind("ympe_logs");
    TagKey<Item> POMEGRANATE_LOGS = bind("pomegranate_logs");
    TagKey<Item> WRITHEWOOD_LOGS = bind("writhewood_logs");
    TagKey<Item> SEEPS = bind("seeps");
    TagKey<Item> CARVED_NEPHRITE = bind("carved_nephrite");
    TagKey<Item> WOODY_GROWTHS = bind("woody_growths");
    TagKey<Item> CHTHONIA_WOOD = bind("chthonia_wood");
    TagKey<Item> DECREASES_BRANCHES = bind("decreases_branches");
    TagKey<Item> DECREASES_BRANCHES_1_IN_4 = bind("decreases_branches_1_in_4");
    TagKey<Item> PLEDGE_ITEMS = bind("pledge_items");
    TagKey<Item> NEPHRITE_TOOL_MATERIALS = bind("nephrite_tool_materials");
    TagKey<Item> HEART_HARVESTERS = bind("heart_harvesters");
    TagKey<Item> YMPE_FRUIT_HARVESTERS = bind("ympe_fruit_harvesters");
    TagKey<Item> STRIPS_OFF_BARK = bind("strips_off_bark");
    TagKey<Item> YMPE_WEAPONS = bind("ympe_weapons");
    TagKey<Item> VAMPIRIC_WEAPONS = bind("vampiric_weapons");
    TagKey<Item> BLIGHTED_WEAPONS = bind("blighted_weapons");
    TagKey<Item> NEPHRITE_FLASKS = bind("nephrite_flasks");
    TagKey<Item> BOSS_HEARTS = bind("boss_hearts");

    TagKey<Item> STORAGE_BLOCKS_ESSTLINE = bind("storage_blocks/esstline");
    TagKey<Item> STORAGE_BLOCKS_NEPHRITE = bind("storage_blocks/nephrite");

    TagKey<Item> DAGGERS = common("daggers");

    private static TagKey<Item> bind(String name) {
        return TagKey.of(RegistryKeys.ITEM, Aylyth.id(name));
    }

    private static TagKey<Item> common(String name) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier("c", name));
    }
}
