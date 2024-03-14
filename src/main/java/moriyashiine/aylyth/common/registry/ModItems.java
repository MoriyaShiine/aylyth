package moriyashiine.aylyth.common.registry;

import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItem;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.item.*;
import moriyashiine.aylyth.common.item.component.ThornFlechetteEffect;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<>();

	public static final RegistryKey<ItemGroup> GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, AylythUtil.id(Aylyth.MOD_ID));

	public static final ItemGroup.Builder GROUP_BUILDER = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.YMPE_DAGGER))
			.displayName(Text.translatable("itemGroup.aylyth.main"));

	public static final Item DEBUG_WAND = register("debug_wand", new DebugWandItem(new FabricItemSettings()));

	public static final Item YMPE_STRIPPED_LOG = register("stripped_ympe_log", new BlockItem(ModBlocks.YMPE_STRIPPED_LOG, settings()));
	public static final Item YMPE_STRIPPED_WOOD = register("stripped_ympe_wood", new BlockItem(ModBlocks.YMPE_STRIPPED_WOOD, settings()));
	public static final Item YMPE_LOG = register("ympe_log", new BlockItem(ModBlocks.YMPE_LOG, settings()));
	public static final Item YMPE_WOOD = register("ympe_wood", new BlockItem(ModBlocks.YMPE_WOOD, settings()));
	public static final Item YMPE_SAPLING = register("ympe_sapling", new BlockItem(ModBlocks.YMPE_SAPLING, settings()));
	public static final Item YMPE_PLANKS = register("ympe_planks", new BlockItem(ModBlocks.YMPE_PLANKS, settings()));
	public static final Item YMPE_STAIRS = register("ympe_stairs", new BlockItem(ModBlocks.YMPE_STAIRS, settings()));
	public static final Item YMPE_SLAB = register("ympe_slab", new BlockItem(ModBlocks.YMPE_SLAB, settings()));
	public static final Item YMPE_FENCE = register("ympe_fence", new BlockItem(ModBlocks.YMPE_FENCE, settings()));
	public static final Item YMPE_FENCE_GATE = register("ympe_fence_gate", new BlockItem(ModBlocks.YMPE_FENCE_GATE, settings()));
	public static final Item YMPE_PRESSURE_PLATE = register("ympe_pressure_plate", new BlockItem(ModBlocks.YMPE_PRESSURE_PLATE, settings()));
	public static final Item YMPE_BUTTON = register("ympe_button", new BlockItem(ModBlocks.YMPE_BUTTON, settings()));
	public static final Item YMPE_TRAPDOOR = register("ympe_trapdoor", new BlockItem(ModBlocks.YMPE_TRAPDOOR, settings()));
	public static final Item YMPE_DOOR = register("ympe_door", new TallBlockItem(ModBlocks.YMPE_DOOR, settings()));
	public static final Item YMPE_SIGN = register("ympe_sign", new SignItem(settings().maxCount(16), ModBlocks.YMPE_SIGN, ModBlocks.YMPE_WALL_SIGN));
	public static final Item YMPE_BOAT = register("ympe_boat", new TerraformBoatItem(ModBoatTypes.YMPE_BOAT_TYPE, false, settings().maxCount(1)));
	public static final Item YMPE_CHEST_BOAT = register("ympe_chest_boat", new TerraformBoatItem(ModBoatTypes.YMPE_BOAT_TYPE, true, settings().maxCount(1)));
	public static final Item YMPE_HANGING_SIGN = register("ympe_hanging_sign", new HangingSignItem(ModBlocks.YMPE_HANGING_SIGN, ModBlocks.YMPE_WALL_HANGING_SIGN, settings()));
	public static final Item YMPE_LEAVES = register("ympe_leaves", new BlockItem(ModBlocks.YMPE_LEAVES, settings()));
	public static final Item FRUIT_BEARING_YMPE_LOG = register("fruit_bearing_ympe_log", new BlockItem(ModBlocks.FRUIT_BEARING_YMPE_LOG, settings()));

	public static final Item POMEGRANATE_STRIPPED_LOG = register("stripped_pomegranate_log", new BlockItem(ModBlocks.POMEGRANATE_STRIPPED_LOG, settings()));
	public static final Item POMEGRANATE_STRIPPED_WOOD = register("stripped_pomegranate_wood", new BlockItem(ModBlocks.POMEGRANATE_STRIPPED_WOOD, settings()));
	public static final Item POMEGRANATE_LOG = register("pomegranate_log", new BlockItem(ModBlocks.POMEGRANATE_LOG, settings()));
	public static final Item POMEGRANATE_WOOD = register("pomegranate_wood", new BlockItem(ModBlocks.POMEGRANATE_WOOD, settings()));
	public static final Item POMEGRANATE_SAPLING = register("pomegranate_sapling", new BlockItem(ModBlocks.POMEGRANATE_SAPLING, settings()));
	public static final Item POMEGRANATE_PLANKS = register("pomegranate_planks", new BlockItem(ModBlocks.POMEGRANATE_PLANKS, settings()));
	public static final Item POMEGRANATE_STAIRS = register("pomegranate_stairs", new BlockItem(ModBlocks.POMEGRANATE_STAIRS, settings()));
	public static final Item POMEGRANATE_SLAB = register("pomegranate_slab", new BlockItem(ModBlocks.POMEGRANATE_SLAB, settings()));
	public static final Item POMEGRANATE_FENCE = register("pomegranate_fence", new BlockItem(ModBlocks.POMEGRANATE_FENCE, settings()));
	public static final Item POMEGRANATE_FENCE_GATE = register("pomegranate_fence_gate", new BlockItem(ModBlocks.POMEGRANATE_FENCE_GATE, settings()));
	public static final Item POMEGRANATE_PRESSURE_PLATE = register("pomegranate_pressure_plate", new BlockItem(ModBlocks.POMEGRANATE_PRESSURE_PLATE, settings()));
	public static final Item POMEGRANATE_BUTTON = register("pomegranate_button", new BlockItem(ModBlocks.POMEGRANATE_BUTTON, settings()));
	public static final Item POMEGRANATE_TRAPDOOR = register("pomegranate_trapdoor", new BlockItem(ModBlocks.POMEGRANATE_TRAPDOOR, settings()));
	public static final Item POMEGRANATE_DOOR = register("pomegranate_door", new TallBlockItem(ModBlocks.POMEGRANATE_DOOR, settings()));
	public static final Item POMEGRANATE_SIGN = register("pomegranate_sign", new SignItem(settings().maxCount(16), ModBlocks.POMEGRANATE_SIGN, ModBlocks.POMEGRANATE_WALL_SIGN));
	public static final Item POMEGRANATE_BOAT = register("pomegranate_boat", new TerraformBoatItem(ModBoatTypes.POMEGRANATE_BOAT_TYPE, false, settings().maxCount(1)));
	public static final Item POMEGRANATE_CHEST_BOAT = register("pomegranate_chest_boat", new TerraformBoatItem(ModBoatTypes.YMPE_BOAT_TYPE, true, settings().maxCount(1)));
	public static final Item POMEGRANATE_HANGING_SIGN = register("pomegranate_hanging_sign", new HangingSignItem(ModBlocks.POMEGRANATE_HANGING_SIGN, ModBlocks.POMEGRANATE_WALL_HANGING_SIGN, settings()));
	public static final Item POMEGRANATE_LEAVES = register("pomegranate_leaves", new BlockItem(ModBlocks.POMEGRANATE_LEAVES, settings()));

	public static final Item WRITHEWOOD_STRIPPED_LOG = register("stripped_writhewood_log", new BlockItem(ModBlocks.WRITHEWOOD_STRIPPED_LOG, settings()));
	public static final Item WRITHEWOOD_STRIPPED_WOOD = register("stripped_writhewood_wood", new BlockItem(ModBlocks.WRITHEWOOD_STRIPPED_WOOD, settings()));
	public static final Item WRITHEWOOD_LOG = register("writhewood_log", new BlockItem(ModBlocks.WRITHEWOOD_LOG, settings()));
	public static final Item WRITHEWOOD_WOOD = register("writhewood_wood", new BlockItem(ModBlocks.WRITHEWOOD_WOOD, settings()));
	public static final Item WRITHEWOOD_SAPLING = register("writhewood_sapling", new BlockItem(ModBlocks.WRITHEWOOD_SAPLING, settings()));
	public static final Item WRITHEWOOD_PLANKS = register("writhewood_planks", new BlockItem(ModBlocks.WRITHEWOOD_PLANKS, settings()));
	public static final Item WRITHEWOOD_STAIRS = register("writhewood_stairs", new BlockItem(ModBlocks.WRITHEWOOD_STAIRS, settings()));
	public static final Item WRITHEWOOD_SLAB = register("writhewood_slab", new BlockItem(ModBlocks.WRITHEWOOD_SLAB, settings()));
	public static final Item WRITHEWOOD_FENCE = register("writhewood_fence", new BlockItem(ModBlocks.WRITHEWOOD_FENCE, settings()));
	public static final Item WRITHEWOOD_FENCE_GATE = register("writhewood_fence_gate", new BlockItem(ModBlocks.WRITHEWOOD_FENCE_GATE, settings()));
	public static final Item WRITHEWOOD_PRESSURE_PLATE = register("writhewood_pressure_plate", new BlockItem(ModBlocks.WRITHEWOOD_PRESSURE_PLATE, settings()));
	public static final Item WRITHEWOOD_BUTTON = register("writhewood_button", new BlockItem(ModBlocks.WRITHEWOOD_BUTTON, settings()));
	public static final Item WRITHEWOOD_TRAPDOOR = register("writhewood_trapdoor", new BlockItem(ModBlocks.WRITHEWOOD_TRAPDOOR, settings()));
	public static final Item WRITHEWOOD_DOOR = register("writhewood_door", new TallBlockItem(ModBlocks.WRITHEWOOD_DOOR, settings()));
	public static final Item WRITHEWOOD_SIGN = register("writhewood_sign", new SignItem(settings().maxCount(16), ModBlocks.WRITHEWOOD_SIGN, ModBlocks.WRITHEWOOD_WALL_SIGN));
	public static final Item WRITHEWOOD_BOAT = register("writhewood_boat", new TerraformBoatItem(ModBoatTypes.WRITHEWOOD_BOAT_TYPE, false, settings().maxCount(1)));
	public static final Item WRITHEWOOD_CHEST_BOAT = register("writhewood_chest_boat", new TerraformBoatItem(ModBoatTypes.YMPE_BOAT_TYPE, true, settings().maxCount(1)));
	public static final Item WRITHEWOOD_HANGING_SIGN = register("writhewood_hanging_sign", new HangingSignItem(ModBlocks.WRITHEWOOD_HANGING_SIGN, ModBlocks.WRITHEWOOD_WALL_HANGING_SIGN, settings()));
	public static final Item WRITHEWOOD_LEAVES = register("writhewood_leaves", new BlockItem(ModBlocks.WRITHEWOOD_LEAVES, settings()));

	public static final Item SEEPING_WOOD = register("seeping_wood", new BlockItem(ModBlocks.SEEPING_WOOD, settings()));
	public static final Item GIRASOL_SEED = register("girasol_seed", new AliasedBlockItem(ModBlocks.GIRASOL_SAPLING, settings()));

	public static final Item AYLYTH_BUSH = register("aylyth_bush", new BlockItem(ModBlocks.AYLYTH_BUSH, settings()));
	public static final Item ANTLER_SHOOTS = register("antler_shoots", new BlockItem(ModBlocks.ANTLER_SHOOTS, settings()));
	public static final Item GRIPWEED = register("gripweed", new BlockItem(ModBlocks.GRIPWEED, settings()));
	public static final Item NYSIAN_GRAPE_VINE = register("nysian_grape_vine", new BlockItem(ModBlocks.NYSIAN_GRAPE_VINE, settings()));
	public static final Item MARIGOLD = register("marigolds", new BlockItem(ModBlocks.MARIGOLD, settings()));
	public static final Item OAK_STREWN_LEAVES = register("oak_strewn_leaves", new BlockItem(ModBlocks.OAK_STREWN_LEAVES, settings()));
	public static final Item YMPE_STREWN_LEAVES = register("ympe_strewn_leaves", new BlockItem(ModBlocks.YMPE_STREWN_LEAVES, settings()));
	public static final Item JACK_O_LANTERN_MUSHROOM = register("jack_o_lantern_mushroom", new VerticallyAttachableBlockItem(ModBlocks.JACK_O_LANTERN_MUSHROOM, ModBlocks.SHELF_JACK_O_LANTERN_MUSHROOM, settings(), Direction.DOWN));
	public static final Item GHOSTCAP_MUSHROOM_SPORES = register("ghostcap_mushroom_spores", new BlockItem(ModBlocks.GHOSTCAP_MUSHROOM, settings()));
	public static final Item SMALL_WOODY_GROWTH = register("small_woody_growth", new BlockItem(ModBlocks.SMALL_WOODY_GROWTH, settings()));
	public static final Item LARGE_WOODY_GROWTH = register("large_woody_growth", new BlockItem(ModBlocks.LARGE_WOODY_GROWTH, settings()));
	public static final Item WOODY_GROWTH_CACHE = register("woody_growth_cache", new BlockItem(ModBlocks.WOODY_GROWTH_CACHE, settings()));

	public static final Item OAK_SEEP = register("oak_seep", new BlockItem(ModBlocks.OAK_SEEP, settings()));
	public static final Item SPRUCE_SEEP = register("spruce_seep", new BlockItem(ModBlocks.SPRUCE_SEEP, settings()));
	public static final Item DARK_OAK_SEEP = register("dark_oak_seep", new BlockItem(ModBlocks.DARK_OAK_SEEP, settings()));
	public static final Item YMPE_SEEP = register("ympe_seep", new BlockItem(ModBlocks.YMPE_SEEP, settings()));
	public static final Item SEEPING_WOOD_SEEP = register("seeping_wood_seep", new BlockItem(ModBlocks.SEEPING_WOOD_SEEP, settings()));
	public static final Item DARK_WOODS_TILES = register("dark_woods_tiles", new BlockItem(ModBlocks.DARK_WOODS_TILES, settings()));
//	public static final Item MYSTERIOUS_SKETCH = register("mysterious_sketch", new Item(new FabricItemSettings()));
	public static final Item BARK = register("bark", new Item(new FabricItemSettings()));

	public static final Item YMPE_DAGGER = register("ympe_dagger", new YmpeDaggerItem(ModToolMaterials.NEPHRITE, 2, -2, -0.5f, settings()));
	public static final Item YMPE_GLAIVE = register("ympe_glaive", new YmpeGlaiveItem(ModToolMaterials.NEPHRITE, 5, -2.8F, (settings()).fireproof().rarity(Rarity.UNCOMMON).maxCount(1)));
	public static final Item YMPE_LANCE = register("ympe_lance", new YmpeLanceItem(settings().maxCount(1).maxDamage(312)));
	public static final Item YMPE_FLAMBERGE = register("ympe_flamberge", new YmpeFlambergeItem(ModToolMaterials.NEPHRITE, 5, -3.1F, (settings()).fireproof().rarity(Rarity.UNCOMMON).maxCount(1)));
	public static final Item YMPE_SCYTHE = register("ympe_scythe", new YmpeScytheItem(ModToolMaterials.NEPHRITE, 4, -2.7F, (settings()).fireproof().rarity(Rarity.UNCOMMON).maxCount(1)));

	public static final Item AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE = register("aylythian_upgrade_smithing_template", new AylythianSmithingTemplateUpgradeItem());
	public static final Item CORIC_SEED = register("coric_seed", new CoricSeedItem(settings()));
	public static final Item ESSTLINE = register("esstline", new Item(new FabricItemSettings()));
	public static final Item NEPHRITE = register("nephrite", new Item(new FabricItemSettings()));
	public static final Item BLIGHTED_THORNS = register("blighted_thorns", new Item(new FabricItemSettings()));

	public static final Item ESSTLINE_BLOCK = register("esstline_block", new BlockItem(ModBlocks.ESSTLINE_BLOCK, settings()));
	public static final Item NEPHRITE_BLOCK = register("nephrite_block", new BlockItem(ModBlocks.NEPHRITE_BLOCK, settings()));
	public static final Item CARVED_SMOOTH_NEPHRITE = register("carved_smooth_nephrite", new BlockItem(ModBlocks.CARVED_SMOOTH_NEPHRITE, settings()));
	public static final Item CARVED_ANTLERED_NEPHRITE = register("carved_antlered_nephrite", new BlockItem(ModBlocks.CARVED_ANTLERED_NEPHRITE, settings()));
	public static final Item CARVED_NEPHRITE_PILLAR = register("carved_nephrite_pillar", new BlockItem(ModBlocks.CARVED_NEPHRITE_PILLAR, settings()));
	public static final Item CARVED_NEPHRITE_TILES = register("carved_nephrite_tiles", new BlockItem(ModBlocks.CARVED_NEPHRITE_TILES, settings()));
	public static final Item CARVED_WOODY_NEPHRITE = register("carved_woody_nephrite", new BlockItem(ModBlocks.CARVED_WOODY_NEPHRITE, settings()));

	public static final Item NEPHRITE_SWORD = register("nephrite_sword", new SwordItem(ModToolMaterials.NEPHRITE, 4, -2.4f, settings()));
	public static final Item NEPHRITE_SHOVEL = register("nephrite_shovel", new ShovelItem(ModToolMaterials.NEPHRITE, 1.5f, -3.0f, settings()));
	public static final Item NEPHRITE_PICKAXE = register("nephrite_pickaxe", new PickaxeItem(ModToolMaterials.NEPHRITE, 1, -2.8f, settings()));
	public static final Item NEPHRITE_AXE = register("nephrite_axe", new AxeItem(ModToolMaterials.NEPHRITE, 5, -3.0f, settings()));
	public static final Item NEPHRITE_HOE = register("nephrite_hoe", new HoeItem(ModToolMaterials.NEPHRITE, -3, 0f, settings()));

	public static final Item VAMPIRIC_SWORD = register("vampiric_sword", new SwordItem(ModToolMaterials.NEPHRITE_SPECIAL, 4, -2.4f, settings()));
	public static final Item VAMPIRIC_PICKAXE = register("vampiric_pick", new PickaxeItem(ModToolMaterials.NEPHRITE_SPECIAL, 1, -2.8f, settings()));
	public static final Item VAMPIRIC_AXE = register("vampiric_axe", new AxeItem(ModToolMaterials.NEPHRITE_SPECIAL, 5, -3.0f, settings()));
	public static final Item VAMPIRIC_HOE = register("vampiric_sickle", new HoeItem(ModToolMaterials.NEPHRITE_SPECIAL, -3, 0f, settings()));

	public static final Item BLIGHTED_SWORD = register("blighted_sword", new SwordItem(ModToolMaterials.NEPHRITE_SPECIAL, 4, -2.4f, settings()));
	public static final Item BLIGHTED_PICKAXE = register("blighted_pick", new PickaxeItem(ModToolMaterials.NEPHRITE_SPECIAL, 1, -2.8f, settings()));
	public static final Item BLIGHTED_AXE = register("blighted_axe", new AxeItem(ModToolMaterials.NEPHRITE_SPECIAL, 5, -3.0f, settings()));
	public static final Item BLIGHTED_HOE = register("blighted_sickle", new HoeItem(ModToolMaterials.NEPHRITE_SPECIAL, -3, 0f, settings()));

	public static final Item THORN_FLECHETTE = register("thorn_flechette", new ThornFlechetteItem(settings()));
	public static final Item BLIGHTED_THORN_FLECHETTE = register("blighted_thorn_flechette", new ThornFlechetteItem(settings(), new ThornFlechetteEffect(new StatusEffectInstance(ModStatusEffects.BLIGHT, 100), 0.5f)));

	public static final Item YMPE_CUIRASS = register("ympe_cuirass", new YmpeCuirassItem(settings().maxCount(1)));
	public static final Item YMPE_EFFIGY = register("ympe_effigy", new YmpeEffigyItem((settings()).fireproof().rarity(Rarity.RARE).maxCount(1)));
	public static final Item NEPHRITE_FLASK = register("nephrite_flask", new NephriteFlaskItem(settings().maxCount(1)));
	public static final Item DARK_NEPHRITE_FLASK = register("dark_nephrite_flask", new NephriteFlaskItem(settings().maxCount(1)));

	public static final Item YMPE_FRUIT = register("ympe_fruit", new Item(settings().food(ModFoodComponents.YMPE_FRUIT)));
	public static final Item SHUCKED_YMPE_FRUIT = register("shucked_ympe_fruit", new ShuckedYmpeFruitItem(settings().maxCount(1)));

	public static final Item NYSIAN_GRAPES = register("nysian_grapes", new Item(settings().food(ModFoodComponents.NYSIAN_GRAPES)));
	public static final Item GHOSTCAP_MUSHROOM = register("ghostcap_mushroom", new Item(settings().food(ModFoodComponents.GHOSTCAPS)));
	public static final Item POMEGRANATE = register("pomegranate", new PomegranateItem(settings().food(ModFoodComponents.POMEGRANATE)));

	public static final Item AYLYTHIAN_HEART = register("aylythian_heart", new AylythianHeartItem(settings()));
	public static final Item WRONGMEAT = register("wrongmeat", new Item(settings().food(ModFoodComponents.WRONGMEAT)));

	public static final Item SOUL_HEARTH = register("soul_hearth", new BlockItem(ModBlocks.SOUL_HEARTH, settings()));
	public static final Item VITAL_THURIBLE = register("vital_thurible", new BlockItem(ModBlocks.VITAL_THURIBLE, settings()));

	public static final Item PILOT_LIGHT_SPAWN_EGG = register("pilot_light_spawn_egg", new SpawnEggItem(ModEntityTypes.PILOT_LIGHT, 0xFFD972, 0x9FD9F6, settings()));
	public static final Item AYLYTHIAN_SPAWN_EGG = register("aylythian_spawn_egg", new SpawnEggItem(ModEntityTypes.AYLYTHIAN, 0x6A4831, 0xE58E03, settings()));
	public static final Item ELDER_AYLYTHIAN_SPAWN_EGG = register("elder_aylythian_spawn_egg", new SpawnEggItem(ModEntityTypes.ELDER_AYLYTHIAN, 0x513425, 0xFFDC9B, settings()));
	public static final Item FAUNAYLYTHIAN_SPAWN_EGG = register("faunaylythian_spawn_egg", new SpawnEggItem(ModEntityTypes.FAUNAYLYTHIAN, 0x6A4831, 0xE1AC20, settings()));
	public static final Item WREATHED_HIND_SPAWN_EGG = register("wreathed_hind_spawn_egg", new SpawnEggItem(ModEntityTypes.WREATHED_HIND_ENTITY, 0x5C4F42, 0xE1B886, settings()));
	public static final Item SCION_SPAWN_EGG = register("scion_spawn_egg", new SpawnEggItem(ModEntityTypes.SCION, 0x463428, 0xE58E03, settings()));
	public static final Item YMPEMOULD_SPAWN_EGG = register("ympemould_spawn_egg", new SpawnEggItem(ModEntityTypes.SOULMOULD, 0x42423E, 0xE58E03, settings()));
	public static final Item BONEFLY_SPAWN_EGG = register("bonefly_spawn_egg", new SpawnEggItem(ModEntityTypes.BONEFLY, 0xE2E2D6, 0x3A2E2B, settings()));
	public static final Item TULPA_SPAWN_EGG = register("tulpa_spawn_egg", new SpawnEggItem(ModEntityTypes.TULPA, 0xE2E2D6, 0x73868F, settings()));

	public static final Item POMEGRANATE_CASSETTE = register("pomegranate_cassette", new MusicDiscItem(14, ModSoundEvents.POMEGRANATE_MUSIC_DISC.value(), settings().maxCount(1).rarity(Rarity.RARE), 118));


	private static <T extends Item> T register(String name, T item) {
		Registry.register(Registries.ITEM, new Identifier(Aylyth.MOD_ID, name), item);
		ITEMS.add(item);
		return item;
	}


	private static Item.Settings settings() {
		return new FabricItemSettings();
	}

	public static void init() {
		GROUP_BUILDER.entries((context, entries) -> {
			ITEMS.forEach(entries::add);
		});

		Registry.register(Registries.ITEM_GROUP, GROUP, GROUP_BUILDER.build());

		FuelRegistry fuelRegistry = FuelRegistry.INSTANCE;
		fuelRegistry.add(YMPE_FENCE, 300);
		fuelRegistry.add(YMPE_FENCE_GATE, 300);
		fuelRegistry.add(POMEGRANATE_FENCE, 300);
		fuelRegistry.add(POMEGRANATE_FENCE_GATE, 300);
		fuelRegistry.add(WRITHEWOOD_FENCE, 300);
		fuelRegistry.add(WRITHEWOOD_FENCE_GATE, 300);
		fuelRegistry.add(BARK, 100);
		CompostingChanceRegistry compostRegistry = CompostingChanceRegistry.INSTANCE;
		compostRegistry.add(YMPE_LEAVES, 0.3f);
		compostRegistry.add(YMPE_SAPLING, 0.3f);
		compostRegistry.add(POMEGRANATE_LEAVES, 0.3f);
		compostRegistry.add(POMEGRANATE_SAPLING, 0.3f);
		compostRegistry.add(WRITHEWOOD_LEAVES, 0.3f);
		compostRegistry.add(WRITHEWOOD_SAPLING, 0.3f);
		compostRegistry.add(OAK_STREWN_LEAVES, 0.3f);
		compostRegistry.add(YMPE_STREWN_LEAVES, 0.3f);
		compostRegistry.add(JACK_O_LANTERN_MUSHROOM, 0.3f);
		compostRegistry.add(GHOSTCAP_MUSHROOM, 0.3f);
		compostRegistry.add(POMEGRANATE, 0.3f);
	}
}
