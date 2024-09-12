package moriyashiine.aylyth.common.data.tag;

import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface AylythItemTags {

    TagKey<Item> YMPE_LOGS = bind("ympe_logs");
    TagKey<Item> POMEGRANATE_LOGS = bind("pomegranate_logs");
    TagKey<Item> WRITHEWOOD_LOGS = bind("writhewood_logs");
    TagKey<Item> SEEPS = bind("seeps");
    TagKey<Item> CARVED_NEPHRITE = bind("carved_nephrite");
    TagKey<Item> WOODY_GROWTHS = bind("woody_growths");
    TagKey<Item> CHTHONIA_WOOD = bind("chthonia_wood");
    TagKey<Item> DECREASES_BRANCHES = bind("decreases_branches");
    TagKey<Item> PLEDGE_ITEMS = bind("pledge_items");
    TagKey<Item> NEPHRITE_TOOL_MATERIALS = bind("nephrite_tool_materials");
    TagKey<Item> HEART_HARVESTERS = bind("heart_harvesters");
    TagKey<Item> YMPE_WEAPONS = bind("ympe_weapons");
    TagKey<Item> VAMPIRIC_WEAPON = bind("vampiric_weapons");
    TagKey<Item> BLIGHTED_WEAPON = bind("blighted_weapons");
    TagKey<Item> NEPHRITE_FLASKS = bind("nephrite_flasks");
    TagKey<Item> BOSS_HEARTS = bind("boss_hearts");

    private static TagKey<Item> bind(String name) {
        return TagKey.of(RegistryKeys.ITEM, AylythUtil.id(name));
    }
}
