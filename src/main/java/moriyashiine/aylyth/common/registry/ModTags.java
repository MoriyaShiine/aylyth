package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModTags {
	public static final TagKey<EntityType<?>> GRIPWEED_IMMUNE = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Aylyth.MOD_ID, "gripweed_immune"));
	public static final TagKey<EntityType<?>> SHUCK_BLACKLIST = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(Aylyth.MOD_ID, "shuck_blacklist"));

	public static final TagKey<Block> YMPE_LOGS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_logs"));
	public static final TagKey<Block> POMEGRANATE_LOGS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "pomegranate_logs"));
	public static final TagKey<Block> WRITHEWOOD_LOGS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "writhewood_logs"));
	public static final TagKey<Block> SEEPS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "seeps"));
	public static final TagKey<Block> JACK_O_LANTERN_GENERATE_ON = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "jack_o_lantern_generate_on"));
	public static final TagKey<Block> GHOSTCAP_REPLACEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "ghostcap_replaceable"));
	public static final TagKey<Block> SCION_REPELLENT = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "scion_repellent"));
	public static final TagKey<Block> WOODY_GROWTHS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "woody_growths"));
	public static final TagKey<Block> WOODY_GROWTHS_GENERATE_ON = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "woody_growths_generate_on"));

	public static final TagKey<Item> YMPE_FOODS = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_foods"));
	public static final TagKey<Item> YMPE_LOGS_ITEM = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_logs"));
	public static final TagKey<Item> POMEGRANATE_LOGS_ITEM = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "pomegranate_logs"));
	public static final TagKey<Item> WRITHEWOOD_LOGS_ITEM = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "writhewood_logs"));
	public static final TagKey<Item> WOODY_GROWTHS_ITEM = TagKey.of(RegistryKeys.ITEM, new Identifier(Aylyth.MOD_ID, "woody_growths"));

	public static final TagKey<Biome> GENERATES_SEEP = TagKey.of(RegistryKeys.BIOME, new Identifier(Aylyth.MOD_ID, "generates_seep"));
}
