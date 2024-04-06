package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModItemTags {
    public static final TagKey<Item> YMPE_LOGS = create("ympe_logs");
    public static final TagKey<Item> POMEGRANATE_LOGS = create("pomegranate_logs");
    public static final TagKey<Item> WRITHEWOOD_LOGS = create("writhewood_logs");
    public static final TagKey<Item> SEEPS = create("seeps");
    public static final TagKey<Item> CARVED_NEPHRITE = create("carved_nephrite");
    public static final TagKey<Item> WOODY_GROWTHS = create("woody_growths");
    public static final TagKey<Item> CHTHONIA_WOOD = create("chthonia_wood");
    public static final TagKey<Item> DECREASES_BRANCHES = create("decreases_branches");
    public static final TagKey<Item> PLEDGE_ITEMS = create("pledge_items");
    public static final TagKey<Item> NEPHRITE_TOOL_MATERIALS = create("nephrite_tool_materials");
    public static final TagKey<Item> HEART_HARVESTERS = create("heart_harvesters");
    public static final TagKey<Item> YMPE_WEAPONS = create("ympe_weapons");
    public static final TagKey<Item> VAMPIRIC_WEAPON = create("vampiric_weapons");
    public static final TagKey<Item> BLIGHTED_WEAPON = create("blighted_weapons");
    public static final TagKey<Item> NEPHRITE_FLASKS = create("nephrite_flasks");
    public static final TagKey<Item> BOSS_HEARTS = create("boss_hearts");

    private static TagKey<Item> create(String tag) {
        return TagKey.of(RegistryKeys.ITEM, AylythUtil.id(tag));
    }
}
