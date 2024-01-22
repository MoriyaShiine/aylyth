package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> YMPE_FOODS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_foods"));
    public static final TagKey<Item> YMPE_LOGS_ITEM = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_logs"));
    public static final TagKey<Item> POMEGRANATE_LOGS_ITEM = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "pomegranate_logs"));
    public static final TagKey<Item> WRITHEWOOD_LOGS_ITEM = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "writhewood_logs"));
    public static final TagKey<Item> WOODY_GROWTHS_ITEM = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "woody_growths"));
    public static final TagKey<Item> PLEDGE_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "pledge_items"));
    public static final TagKey<Item> SEEP_ITEMS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "seeps"));
}
