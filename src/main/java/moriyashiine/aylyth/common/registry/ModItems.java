package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.item.*;
import moriyashiine.aylyth.common.registry.util.ItemWoodSuite;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(Aylyth.MOD_ID, Aylyth.MOD_ID), () -> new ItemStack(ModItems.YMPE_DAGGER));

	public static final Item DEBUG_WAND = new DebugWandItem(new FabricItemSettings());

	public static final Item FRUIT_BEARING_YMPE_LOG = new BlockItem(ModBlocks.FRUIT_BEARING_YMPE_LOG, settings());
	public static final Item YMPE_LEAVES = new BlockItem(ModBlocks.YMPE_LEAVES, settings());
	public static final ItemWoodSuite YMPE_ITEMS = ItemWoodSuite.of(new Identifier(Aylyth.MOD_ID, "ympe"), ModBlocks.YMPE_BLOCKS, new ItemWoodSuite.GroupedSettings(GROUP), Registry.ITEM, () -> ModBoatTypes.YMPE_BOAT_TYPE, () -> ModBoatTypes.YMPE_CHEST_BOAT_TYPE);

	public static final Item POMEGRANATE_LEAVES = new BlockItem(ModBlocks.POMEGRANATE_LEAVES, settings());
	public static final ItemWoodSuite POMEGRANATE_ITEMS = ItemWoodSuite.of(new Identifier(Aylyth.MOD_ID, "pomegranate"), ModBlocks.POMEGRANATE_BLOCKS, new ItemWoodSuite.GroupedSettings(GROUP), Registry.ITEM, () -> ModBoatTypes.POMEGRANATE_BOAT_TYPE, () -> ModBoatTypes.POMEGRANATE_CHEST_BOAT_TYPE);

	public static final Item WRITHEWOOD_LEAVES = new BlockItem(ModBlocks.WRITHEWOOD_LEAVES, settings());
	public static final ItemWoodSuite WRITHEWOOD_ITEMS = ItemWoodSuite.of(new Identifier(Aylyth.MOD_ID, "writhewood"), ModBlocks.WRITHEWOOD_BLOCKS, new ItemWoodSuite.GroupedSettings(GROUP), Registry.ITEM, () -> ModBoatTypes.WRITHEWOOD_BOAT_TYPE, () -> ModBoatTypes.WRITHEWOOD_CHEST_BOAT_TYPE);

	public static final Item AYLYTH_BUSH = new BlockItem(ModBlocks.AYLYTH_BUSH, settings());
	public static final Item ANTLER_SHOOTS = new BlockItem(ModBlocks.ANTLER_SHOOTS, settings());
	public static final Item GRIPWEED = new BlockItem(ModBlocks.GRIPWEED, settings());
	public static final Item CORIC_SEED = new CoricSeedItem(settings());

	public static final Item NYSIAN_GRAPE_VINE = new BlockItem(ModBlocks.NYSIAN_GRAPE_VINE, settings());
	public static final Item MARIGOLD = new BlockItem(ModBlocks.MARIGOLD, settings());
	public static final Item OAK_STREWN_LEAVES = new BlockItem(ModBlocks.OAK_STREWN_LEAVES, settings());
	public static final Item YMPE_STREWN_LEAVES = new BlockItem(ModBlocks.YMPE_STREWN_LEAVES, settings());
	public static final Item JACK_O_LANTERN_MUSHROOM = new WallStandingBlockItem(ModBlocks.JACK_O_LANTERN_MUSHROOM, ModBlocks.SHELF_JACK_O_LANTERN_MUSHROOM, settings());
	public static final Item GHOSTCAP_MUSHROOM_SPORES = new BlockItem(ModBlocks.GHOSTCAP_MUSHROOM, settings());

	public static final Item OAK_SEEP = new BlockItem(ModBlocks.OAK_SEEP, settings());
	public static final Item SPRUCE_SEEP = new BlockItem(ModBlocks.SPRUCE_SEEP, settings());
	public static final Item DARK_OAK_SEEP = new BlockItem(ModBlocks.DARK_OAK_SEEP, settings());
	public static final Item YMPE_SEEP = new BlockItem(ModBlocks.YMPE_SEEP, settings());

	public static final Item YMPE_DAGGER = new YmpeDaggerItem(ToolMaterials.NETHERITE, 1, -2, settings());
	public static final Item YMPE_LANCE = new YmpeLanceItem(312, settings());

	public static final Item YMPE_FRUIT = new Item(settings().food(ModFoodComponents.YMPE_FRUIT));
	public static final Item SHUCKED_YMPE_FRUIT = new ShuckedYmpeFruitItem(settings().maxCount(1));

	public static final Item NYSIAN_GRAPES = new Item(settings().food(ModFoodComponents.NYSIAN_GRAPES));
	public static final Item GHOSTCAP_MUSHROOM = new Item(settings().food(ModFoodComponents.GHOSTCAPS));
	public static final Item POMEGRANATE = new PomegranateItem(settings().food(ModFoodComponents.POMEGRANATE));

	public static final Item AYLYTHIAN_HEART = new AylythianHeartItem(settings());
	public static final Item WRONGMEAT = new Item(settings());

	public static final Item SOUL_HEARTH = new BlockItem(ModBlocks.SOUL_HEARTH, settings());
	public static final Item VITAL_THURIBLE = new BlockItem(ModBlocks.VITAL_THURIBLE, settings());
	public static final Item DARK_WOODS_TILES = new BlockItem(ModBlocks.DARK_WOODS_TILES, settings());

	public static final Item PILOT_LIGHT_SPAWN_EGG = new SpawnEggItem(ModEntityTypes.PILOT_LIGHT, 0xFFD972, 0x9FD9F6, settings());
	public static final Item AYLYTHIAN_SPAWN_EGG = new SpawnEggItem(ModEntityTypes.AYLYTHIAN, 0x6A4831, 0xE58E03, settings());
	public static final Item ELDER_AYLYTHIAN_SPAWN_EGG = new SpawnEggItem(ModEntityTypes.ELDER_AYLYTHIAN, 0x513425, 0xFFDC9B, settings());
	public static final Item SCION_SPAWN_EGG = new SpawnEggItem(ModEntityTypes.SCION, 0x463428, 0xE58E03, settings());

	public static final Item SOULTRAP_EFFIGY_ITEM =  new SoultrapEffigyItem((settings()).fireproof().rarity(Rarity.RARE).maxCount(1));
	public static final Item GLAIVE =  new GlaiveItem(4, -3.1F, (settings()).fireproof().rarity(Rarity.UNCOMMON).maxCount(1));

	public static final Item SOULMOULD_ITEM = new SoulmouldItem((settings()).fireproof().rarity(Rarity.UNCOMMON).maxCount(16));





	private static Item.Settings settings() {
		return new FabricItemSettings().group(GROUP);
	}

	public static void init() {
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "debug_wand"), DEBUG_WAND);
		
		YMPE_ITEMS.register();
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "fruit_bearing_ympe_log"), FRUIT_BEARING_YMPE_LOG);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_leaves"), YMPE_LEAVES);
		POMEGRANATE_ITEMS.register();
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "pomegranate_leaves"), POMEGRANATE_LEAVES);
		WRITHEWOOD_ITEMS.register();
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "writhewood_leaves"), WRITHEWOOD_LEAVES);
		
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "aylyth_bush"), AYLYTH_BUSH);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "antler_shoots"), ANTLER_SHOOTS);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "gripweed"), GRIPWEED);

		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "nysian_grape_vine"), NYSIAN_GRAPE_VINE);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "marigolds"), MARIGOLD);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "oak_strewn_leaves"), OAK_STREWN_LEAVES);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_strewn_leaves"), YMPE_STREWN_LEAVES);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "jack_o_lantern_mushroom"), JACK_O_LANTERN_MUSHROOM);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ghostcap_mushroom_spores"), GHOSTCAP_MUSHROOM_SPORES);

		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "oak_seep"), OAK_SEEP);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "spruce_seep"), SPRUCE_SEEP);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "dark_oak_seep"), DARK_OAK_SEEP);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_seep"), YMPE_SEEP);

		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_dagger"), YMPE_DAGGER);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_fruit"), YMPE_FRUIT);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "shucked_ympe_fruit"), SHUCKED_YMPE_FRUIT);

		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ympe_lance"), YMPE_LANCE);

		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "nysian_grapes"), NYSIAN_GRAPES);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "ghostcap_mushroom"), GHOSTCAP_MUSHROOM);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "pomegranate"), POMEGRANATE);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "coric_seed"), CORIC_SEED);

		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "aylythian_heart"), AYLYTHIAN_HEART);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "wrongmeat"), WRONGMEAT);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "soul_hearth"), SOUL_HEARTH);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "vital_thurible"), VITAL_THURIBLE);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "dark_woods_tiles"), DARK_WOODS_TILES);

		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "pilot_light_spawn_egg"), PILOT_LIGHT_SPAWN_EGG);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "aylythian_spawn_egg"), AYLYTHIAN_SPAWN_EGG);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "scion_spawn_egg"), SCION_SPAWN_EGG);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "elder_aylythian_spawn_egg"), ELDER_AYLYTHIAN_SPAWN_EGG);

		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "soultrap_effigy"), SOULTRAP_EFFIGY_ITEM);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "glaive"), GLAIVE);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "soulmould"), SOULMOULD_ITEM);

		FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
		fuelRegistry.add(YMPE_ITEMS.fence, 300);
		fuelRegistry.add(YMPE_ITEMS.fenceGate, 300);
		fuelRegistry.add(POMEGRANATE_ITEMS.fence, 300);
		fuelRegistry.add(POMEGRANATE_ITEMS.fenceGate, 300);
		CompostingChanceRegistry compostRegistry = CompostingChanceRegistry.INSTANCE;
		compostRegistry.add(YMPE_LEAVES, 0.3f);
		compostRegistry.add(YMPE_ITEMS.sapling, 0.3f);
		compostRegistry.add(POMEGRANATE_LEAVES, 0.3f);
		compostRegistry.add(POMEGRANATE_ITEMS.sapling, 0.3f);
		compostRegistry.add(OAK_STREWN_LEAVES, 0.3f);
		compostRegistry.add(YMPE_STREWN_LEAVES, 0.3f);
		compostRegistry.add(JACK_O_LANTERN_MUSHROOM, 0.3f);
		compostRegistry.add(GHOSTCAP_MUSHROOM, 0.3f);
		compostRegistry.add(POMEGRANATE, 0.3f);
	}
}
