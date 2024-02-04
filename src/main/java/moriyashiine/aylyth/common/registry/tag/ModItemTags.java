package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> YMPE_FOODS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_foods"));
    public static final TagKey<Item> YMPE_LOGS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_logs"));
    public static final TagKey<Item> POMEGRANATE_LOGS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "pomegranate_logs"));
    public static final TagKey<Item> WRITHEWOOD_LOGS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "writhewood_logs"));
    public static final TagKey<Item> WOODY_GROWTHS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "woody_growths"));
    public static final TagKey<Item> PLEDGE_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "pledge_items"));
    public static final TagKey<Item> SEEPS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "seeps"));
    public static final TagKey<Item> NEPHRITE_REPAIR_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "repairs_nephrite"));
    public static final TagKey<Item> CARVED_NEPHRITE = create("carved_nephrite");

    private static final TagKey<Item> create(String id) {
        return TagKey.of(RegistryKeys.ITEM, AylythUtil.id(id));
    }
}
