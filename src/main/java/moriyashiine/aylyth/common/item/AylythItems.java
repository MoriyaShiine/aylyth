package moriyashiine.aylyth.common.item;

import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.item.types.*;
import moriyashiine.aylyth.common.item.components.ThornFlechetteEffect;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

import java.util.List;

public interface AylythItems {

    List<Item> TEMPT_MAIN_ITEM_GROUP_ITEMS = new ObjectArrayList<>(); // *private*
    RegistryKey<ItemGroup> MAIN_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Aylyth.id(Aylyth.MOD_ID));

    Item DEBUG_WAND = register("debug_wand", new DebugWandItem(settings()));

	Item YMPE_STRIPPED_LOG = registerBlockItem("stripped_ympe_log", AylythBlocks.YMPE_STRIPPED_LOG);
    Item YMPE_STRIPPED_WOOD = registerBlockItem("stripped_ympe_wood", AylythBlocks.YMPE_STRIPPED_WOOD);
    Item YMPE_LOG = registerBlockItem("ympe_log", AylythBlocks.YMPE_LOG);
    Item YMPE_WOOD = registerBlockItem("ympe_wood", AylythBlocks.YMPE_WOOD);
    Item YMPE_SAPLING = registerBlockItem("ympe_sapling", AylythBlocks.YMPE_SAPLING);
    Item YMPE_PLANKS = registerBlockItem("ympe_planks", AylythBlocks.YMPE_PLANKS);
    Item YMPE_STAIRS = registerBlockItem("ympe_stairs", AylythBlocks.YMPE_STAIRS);
    Item YMPE_SLAB = registerBlockItem("ympe_slab", AylythBlocks.YMPE_SLAB);
    Item YMPE_FENCE = registerBlockItem("ympe_fence", AylythBlocks.YMPE_FENCE);
    Item YMPE_FENCE_GATE = registerBlockItem("ympe_fence_gate", AylythBlocks.YMPE_FENCE_GATE);
    Item YMPE_PRESSURE_PLATE = registerBlockItem("ympe_pressure_plate", AylythBlocks.YMPE_PRESSURE_PLATE);
    Item YMPE_BUTTON = registerBlockItem("ympe_button", AylythBlocks.YMPE_BUTTON);
    Item YMPE_TRAPDOOR = registerBlockItem("ympe_trapdoor", AylythBlocks.YMPE_TRAPDOOR);
    Item YMPE_DOOR = register("ympe_door", new TallBlockItem(AylythBlocks.YMPE_DOOR, settings()));
    Item YMPE_SIGN = register("ympe_sign", new SignItem(settings().maxCount(16), AylythBlocks.YMPE_SIGN, AylythBlocks.YMPE_WALL_SIGN));
    Item YMPE_BOAT = register("ympe_boat", new TerraformBoatItem(AylythBoatTypes.YMPE, false, settings().maxCount(1)));
    Item YMPE_CHEST_BOAT = register("ympe_chest_boat", new TerraformBoatItem(AylythBoatTypes.YMPE, true, settings().maxCount(1)));
    Item YMPE_HANGING_SIGN = register("ympe_hanging_sign", new HangingSignItem(AylythBlocks.YMPE_HANGING_SIGN, AylythBlocks.YMPE_WALL_HANGING_SIGN, settings()));
    Item YMPE_LEAVES = registerBlockItem("ympe_leaves", AylythBlocks.YMPE_LEAVES);
    Item FRUIT_BEARING_YMPE_LOG = registerBlockItem("fruit_bearing_ympe_log", AylythBlocks.FRUIT_BEARING_YMPE_LOG);

    Item POMEGRANATE_STRIPPED_LOG = registerBlockItem("stripped_pomegranate_log", AylythBlocks.POMEGRANATE_STRIPPED_LOG);
    Item POMEGRANATE_STRIPPED_WOOD = registerBlockItem("stripped_pomegranate_wood", AylythBlocks.POMEGRANATE_STRIPPED_WOOD);
    Item POMEGRANATE_LOG = registerBlockItem("pomegranate_log", AylythBlocks.POMEGRANATE_LOG);
    Item POMEGRANATE_WOOD = registerBlockItem("pomegranate_wood", AylythBlocks.POMEGRANATE_WOOD);
    Item POMEGRANATE_SAPLING = registerBlockItem("pomegranate_sapling", AylythBlocks.POMEGRANATE_SAPLING);
    Item POMEGRANATE_PLANKS = registerBlockItem("pomegranate_planks", AylythBlocks.POMEGRANATE_PLANKS);
    Item POMEGRANATE_STAIRS = registerBlockItem("pomegranate_stairs", AylythBlocks.POMEGRANATE_STAIRS);
    Item POMEGRANATE_SLAB = registerBlockItem("pomegranate_slab", AylythBlocks.POMEGRANATE_SLAB);
    Item POMEGRANATE_FENCE = registerBlockItem("pomegranate_fence", AylythBlocks.POMEGRANATE_FENCE);
    Item POMEGRANATE_FENCE_GATE = registerBlockItem("pomegranate_fence_gate", AylythBlocks.POMEGRANATE_FENCE_GATE);
    Item POMEGRANATE_PRESSURE_PLATE = registerBlockItem("pomegranate_pressure_plate", AylythBlocks.POMEGRANATE_PRESSURE_PLATE);
    Item POMEGRANATE_BUTTON = registerBlockItem("pomegranate_button", AylythBlocks.POMEGRANATE_BUTTON);
    Item POMEGRANATE_TRAPDOOR = registerBlockItem("pomegranate_trapdoor", AylythBlocks.POMEGRANATE_TRAPDOOR);
    Item POMEGRANATE_DOOR = register("pomegranate_door", new TallBlockItem(AylythBlocks.POMEGRANATE_DOOR, settings()));
    Item POMEGRANATE_SIGN = register("pomegranate_sign", new SignItem(settings().maxCount(16), AylythBlocks.POMEGRANATE_SIGN, AylythBlocks.POMEGRANATE_WALL_SIGN));
    Item POMEGRANATE_BOAT = register("pomegranate_boat", new TerraformBoatItem(AylythBoatTypes.POMEGRANATE, false, settings().maxCount(1)));
    Item POMEGRANATE_CHEST_BOAT = register("pomegranate_chest_boat", new TerraformBoatItem(AylythBoatTypes.YMPE, true, settings().maxCount(1)));
    Item POMEGRANATE_HANGING_SIGN = register("pomegranate_hanging_sign", new HangingSignItem(AylythBlocks.POMEGRANATE_HANGING_SIGN, AylythBlocks.POMEGRANATE_WALL_HANGING_SIGN, settings()));
    Item POMEGRANATE_LEAVES = registerBlockItem("pomegranate_leaves", AylythBlocks.POMEGRANATE_LEAVES);

    Item WRITHEWOOD_STRIPPED_LOG = registerBlockItem("stripped_writhewood_log", AylythBlocks.WRITHEWOOD_STRIPPED_LOG);
    Item WRITHEWOOD_STRIPPED_WOOD = registerBlockItem("stripped_writhewood_wood", AylythBlocks.WRITHEWOOD_STRIPPED_WOOD);
    Item WRITHEWOOD_LOG = registerBlockItem("writhewood_log", AylythBlocks.WRITHEWOOD_LOG);
    Item WRITHEWOOD_WOOD = registerBlockItem("writhewood_wood", AylythBlocks.WRITHEWOOD_WOOD);
    Item WRITHEWOOD_SAPLING = registerBlockItem("writhewood_sapling", AylythBlocks.WRITHEWOOD_SAPLING);
    Item WRITHEWOOD_PLANKS = registerBlockItem("writhewood_planks", AylythBlocks.WRITHEWOOD_PLANKS);
    Item WRITHEWOOD_STAIRS = registerBlockItem("writhewood_stairs", AylythBlocks.WRITHEWOOD_STAIRS);
    Item WRITHEWOOD_SLAB = registerBlockItem("writhewood_slab", AylythBlocks.WRITHEWOOD_SLAB);
    Item WRITHEWOOD_FENCE = registerBlockItem("writhewood_fence", AylythBlocks.WRITHEWOOD_FENCE);
    Item WRITHEWOOD_FENCE_GATE = registerBlockItem("writhewood_fence_gate", AylythBlocks.WRITHEWOOD_FENCE_GATE);
    Item WRITHEWOOD_PRESSURE_PLATE = registerBlockItem("writhewood_pressure_plate", AylythBlocks.WRITHEWOOD_PRESSURE_PLATE);
    Item WRITHEWOOD_BUTTON = registerBlockItem("writhewood_button", AylythBlocks.WRITHEWOOD_BUTTON);
    Item WRITHEWOOD_TRAPDOOR = registerBlockItem("writhewood_trapdoor", AylythBlocks.WRITHEWOOD_TRAPDOOR);
    Item WRITHEWOOD_DOOR = register("writhewood_door", new TallBlockItem(AylythBlocks.WRITHEWOOD_DOOR, settings()));
    Item WRITHEWOOD_SIGN = register("writhewood_sign", new SignItem(settings().maxCount(16), AylythBlocks.WRITHEWOOD_SIGN, AylythBlocks.WRITHEWOOD_WALL_SIGN));
    Item WRITHEWOOD_BOAT = register("writhewood_boat", new TerraformBoatItem(AylythBoatTypes.WRITHEWOOD, false, settings().maxCount(1)));
    Item WRITHEWOOD_CHEST_BOAT = register("writhewood_chest_boat", new TerraformBoatItem(AylythBoatTypes.YMPE, true, settings().maxCount(1)));
    Item WRITHEWOOD_HANGING_SIGN = register("writhewood_hanging_sign", new HangingSignItem(AylythBlocks.WRITHEWOOD_HANGING_SIGN, AylythBlocks.WRITHEWOOD_WALL_HANGING_SIGN, settings()));
    Item WRITHEWOOD_LEAVES = registerBlockItem("writhewood_leaves", AylythBlocks.WRITHEWOOD_LEAVES);

    Item SEEPING_WOOD = registerBlockItem("seeping_wood", AylythBlocks.SEEPING_WOOD);
    Item GIRASOL_SEED = register("girasol_seed", new AliasedBlockItem(AylythBlocks.GIRASOL_SAPLING, settings()));

    Item CHTHONIA_WOOD = registerBlockItem("chthonia_wood", AylythBlocks.CHTHONIA_WOOD);
    Item NEPHRITIC_CHTHONIA_WOOD = registerBlockItem("nephritic_chthonia_wood", AylythBlocks.NEPHRITIC_CHTHONIA_WOOD);

    Item AYLYTH_BUSH = registerBlockItem("aylyth_bush", AylythBlocks.AYLYTH_BUSH);
    Item ANTLER_SHOOTS = registerBlockItem("antler_shoots", AylythBlocks.ANTLER_SHOOTS);
    Item GRIPWEED = registerBlockItem("gripweed", AylythBlocks.GRIPWEED);
    Item NYSIAN_GRAPE_VINE = registerBlockItem("nysian_grape_vine", AylythBlocks.NYSIAN_GRAPE_VINE);
    Item MARIGOLD = registerBlockItem("marigolds", AylythBlocks.MARIGOLD);
    Item OAK_STREWN_LEAVES = registerBlockItem("oak_strewn_leaves", AylythBlocks.OAK_STREWN_LEAVES);
    Item YMPE_STREWN_LEAVES = registerBlockItem("ympe_strewn_leaves", AylythBlocks.YMPE_STREWN_LEAVES);
    Item JACK_O_LANTERN_MUSHROOM = register("jack_o_lantern_mushroom", new VerticallyAttachableBlockItem(AylythBlocks.JACK_O_LANTERN_MUSHROOM, AylythBlocks.SHELF_JACK_O_LANTERN_MUSHROOM, settings(), Direction.DOWN));
    Item GHOSTCAP_MUSHROOM_SPORES = registerBlockItem("ghostcap_mushroom_spores", AylythBlocks.GHOSTCAP_MUSHROOM);
    Item SMALL_WOODY_GROWTH = registerBlockItem("small_woody_growth", AylythBlocks.SMALL_WOODY_GROWTH);
    Item LARGE_WOODY_GROWTH = registerBlockItem("large_woody_growth", AylythBlocks.LARGE_WOODY_GROWTH);
    Item WOODY_GROWTH_CACHE = registerBlockItem("woody_growth_cache", AylythBlocks.WOODY_GROWTH_CACHE);

    Item OAK_SEEP = registerBlockItem("oak_seep", AylythBlocks.OAK_SEEP);
    Item SPRUCE_SEEP = registerBlockItem("spruce_seep", AylythBlocks.SPRUCE_SEEP);
    Item DARK_OAK_SEEP = registerBlockItem("dark_oak_seep", AylythBlocks.DARK_OAK_SEEP);
    Item YMPE_SEEP = registerBlockItem("ympe_seep", AylythBlocks.YMPE_SEEP);
    Item SEEPING_WOOD_SEEP = registerBlockItem("seeping_wood_seep", AylythBlocks.SEEPING_WOOD_SEEP);
    Item DARK_WOODS_TILES = registerBlockItem("dark_woods_tiles", AylythBlocks.DARK_WOODS_TILES);
	//Item MYSTERIOUS_SKETCH = registerSimple("mysterious_sketch");
    Item BARK = registerSimple("bark");

    Item LANCEOLATE_DAGGER = register("lanceolate_dagger", new DaggerItem(AylythToolMaterials.NEPHRITE, 1, -2, -0.5f, settings()));
    Item YMPE_DAGGER = register("ympe_dagger", new YmpeDaggerItem(AylythToolMaterials.NEPHRITE, 2, -2, -0.5f, settings()));
    Item YMPE_GLAIVE = register("ympe_glaive", new YmpeGlaiveItem(AylythToolMaterials.NEPHRITE, 5, -2.8F, (settings()).fireproof().rarity(Rarity.UNCOMMON).maxCount(1)));
    Item YMPE_LANCE = register("ympe_lance", new YmpeLanceItem(settings().maxCount(1).maxDamage(312)));
    Item YMPE_FLAMBERGE = register("ympe_flamberge", new YmpeFlambergeItem(AylythToolMaterials.NEPHRITE, 5, -3.1F, (settings()).fireproof().rarity(Rarity.UNCOMMON).maxCount(1)));
    Item YMPE_SCYTHE = register("ympe_scythe", new YmpeScytheItem(AylythToolMaterials.NEPHRITE, 4, -2.7F, (settings()).fireproof().rarity(Rarity.UNCOMMON).maxCount(1)));

    Item AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE = register("aylythian_upgrade_smithing_template", new AylythianSmithingTemplateUpgradeItem());
    Item CORIC_SEED = register("coric_seed", new CoricSeedItem(settings()));
    Item ESSTLINE = registerSimple("esstline");
    Item NEPHRITE = registerSimple("nephrite");
    Item BLIGHTED_THORNS = registerSimple("blighted_thorns");

    Item ESSTLINE_BLOCK = registerBlockItem("esstline_block", AylythBlocks.ESSTLINE_BLOCK);
    Item NEPHRITE_BLOCK = registerBlockItem("nephrite_block", AylythBlocks.NEPHRITE_BLOCK);
    Item CARVED_SMOOTH_NEPHRITE = registerBlockItem("carved_smooth_nephrite", AylythBlocks.CARVED_SMOOTH_NEPHRITE);
    Item CARVED_ANTLERED_NEPHRITE = registerBlockItem("carved_antlered_nephrite", AylythBlocks.CARVED_ANTLERED_NEPHRITE);
    Item CARVED_NEPHRITE_PILLAR = registerBlockItem("carved_nephrite_pillar", AylythBlocks.CARVED_NEPHRITE_PILLAR);
    Item CARVED_NEPHRITE_TILES = registerBlockItem("carved_nephrite_tiles", AylythBlocks.CARVED_NEPHRITE_TILES);
    Item CARVED_WOODY_NEPHRITE = registerBlockItem("carved_woody_nephrite", AylythBlocks.CARVED_WOODY_NEPHRITE);

    Item NEPHRITE_SWORD = register("nephrite_sword", new SwordItem(AylythToolMaterials.NEPHRITE, 4, -2.4f, settings()));
    Item NEPHRITE_SHOVEL = register("nephrite_shovel", new ShovelItem(AylythToolMaterials.NEPHRITE, 1.5f, -3.0f, settings()));
    Item NEPHRITE_PICKAXE = register("nephrite_pickaxe", new PickaxeItem(AylythToolMaterials.NEPHRITE, 1, -2.8f, settings()));
    Item NEPHRITE_AXE = register("nephrite_axe", new AxeItem(AylythToolMaterials.NEPHRITE, 5, -3.0f, settings()));
    Item NEPHRITE_HOE = register("nephrite_hoe", new HoeItem(AylythToolMaterials.NEPHRITE, -3, 0f, settings()));

    Item VAMPIRIC_SWORD = register("vampiric_sword", new SwordItem(AylythToolMaterials.NEPHRITE_SPECIAL, 4, -2.4f, settings()));
    Item VAMPIRIC_PICKAXE = register("vampiric_pick", new PickaxeItem(AylythToolMaterials.NEPHRITE_SPECIAL, 1, -2.8f, settings()));
    Item VAMPIRIC_AXE = register("vampiric_axe", new AxeItem(AylythToolMaterials.NEPHRITE_SPECIAL, 5, -3.0f, settings()));
    Item VAMPIRIC_HOE = register("vampiric_sickle", new HoeItem(AylythToolMaterials.NEPHRITE_SPECIAL, -3, 0f, settings()));

    Item BLIGHTED_SWORD = register("blighted_sword", new SwordItem(AylythToolMaterials.NEPHRITE_SPECIAL, 4, -2.4f, settings()));
    Item BLIGHTED_PICKAXE = register("blighted_pick", new PickaxeItem(AylythToolMaterials.NEPHRITE_SPECIAL, 1, -2.8f, settings()));
    Item BLIGHTED_AXE = register("blighted_axe", new AxeItem(AylythToolMaterials.NEPHRITE_SPECIAL, 5, -3.0f, settings()));
    Item BLIGHTED_HOE = register("blighted_sickle", new HoeItem(AylythToolMaterials.NEPHRITE_SPECIAL, -3, 0f, settings()));

    Item THORN_FLECHETTE = register("thorn_flechette", new ThornFlechetteItem(settings()));
    Item BLIGHTED_THORN_FLECHETTE = register("blighted_thorn_flechette", new ThornFlechetteItem(settings(), new ThornFlechetteEffect(new StatusEffectInstance(AylythStatusEffects.BLIGHT, 100), 0.5f)));

    Item YMPE_CUIRASS = register("ympe_cuirass", new YmpeCuirassItem(settings().maxCount(1)));
    Item YMPE_EFFIGY = register("ympe_effigy", new YmpeEffigyItem((settings()).fireproof().rarity(Rarity.RARE).maxCount(1)));
    Item NEPHRITE_FLASK = register("nephrite_flask", new NephriteFlaskItem(settings().maxCount(1)));
    Item DARK_NEPHRITE_FLASK = register("dark_nephrite_flask", new NephriteFlaskItem(settings().maxCount(1)));

    Item YMPE_MUSH = registerFood("ympe_mush", AylythFoods.YMPE_MUSH);
    Item YMPE_FRUIT = registerFood("ympe_fruit", AylythFoods.YMPE_FRUIT);
    Item SHUCKED_YMPE_FRUIT = register("shucked_ympe_fruit", new ShuckedYmpeFruitItem(settings().maxCount(1)));

    Item NYSIAN_GRAPES = registerFood("nysian_grapes", AylythFoods.NYSIAN_GRAPES);
    Item GHOSTCAP_MUSHROOM = registerFood("ghostcap_mushroom", AylythFoods.GHOSTCAPS);
    Item POMEGRANATE = register("pomegranate", new PomegranateItem(settings().food(AylythFoods.POMEGRANATE)));

    Item WRONGMEAT = registerFood("wrongmeat", AylythFoods.WRONGMEAT);
    Item AYLYTHIAN_HEART = register("aylythian_heart", new AylythianHeartItem(settings()));
    Item NEPHRITE_HEART = registerSimple("nephrite_heart");
    Item YHONDYTH_HEART = registerSimple("yhondyth_heart");

    Item SOUL_HEARTH = registerBlockItem("soul_hearth", AylythBlocks.SOUL_HEARTH);
    Item VITAL_THURIBLE = registerBlockItem("vital_thurible", AylythBlocks.VITAL_THURIBLE);
    Item BLACK_WELL = registerBlockItem("black_well", AylythBlocks.BLACK_WELL);

    Item PILOT_LIGHT_SPAWN_EGG = registerSpawnEgg("pilot_light_spawn_egg", AylythEntityTypes.PILOT_LIGHT, 0xFFD972, 0x9FD9F6);
    Item AYLYTHIAN_SPAWN_EGG = registerSpawnEgg("aylythian_spawn_egg", AylythEntityTypes.AYLYTHIAN, 0x6A4831, 0xE58E03);
    Item ELDER_AYLYTHIAN_SPAWN_EGG = registerSpawnEgg("elder_aylythian_spawn_egg", AylythEntityTypes.ELDER_AYLYTHIAN, 0x513425, 0xFFDC9B);
    Item FAUNAYLYTHIAN_SPAWN_EGG = registerSpawnEgg("faunaylythian_spawn_egg", AylythEntityTypes.FAUNAYLYTHIAN, 0x6A4831, 0xE1AC20);
    Item WREATHED_HIND_SPAWN_EGG = registerSpawnEgg("wreathed_hind_spawn_egg", AylythEntityTypes.WREATHED_HIND_ENTITY, 0x5C4F42, 0xE1B886);
    Item SCION_SPAWN_EGG = registerSpawnEgg("scion_spawn_egg", AylythEntityTypes.SCION, 0x463428, 0xE58E03);
    Item YMPEMOULD_SPAWN_EGG = registerSpawnEgg("ympemould_spawn_egg", AylythEntityTypes.SOULMOULD, 0x42423E, 0xE58E03);
    Item BONEFLY_SPAWN_EGG = registerSpawnEgg("bonefly_spawn_egg", AylythEntityTypes.BONEFLY, 0xE2E2D6, 0x3A2E2B);
    Item TULPA_SPAWN_EGG = registerSpawnEgg("tulpa_spawn_egg", AylythEntityTypes.TULPA, 0xE2E2D6, 0x73868F);

    Item POMEGRANATE_CASSETTE = register("pomegranate_cassette", new MusicDiscItem(14, AylythSoundEvents.POMEGRANATE_MUSIC_DISC.value(), settings().maxCount(1).rarity(Rarity.RARE), 118));

    private static Item.Settings settings() {
		return new FabricItemSettings();
	}

	private static <I extends Item> I register(String name, I item) {
		TEMPT_MAIN_ITEM_GROUP_ITEMS.add(item);
		return Registry.register(Registries.ITEM, Aylyth.id(name), item);
	}

	private static Item registerSimple(String name) {
		return register(name, new Item(settings()));
	}

	private static Item registerFood(String name, FoodComponent food) {
		return register(name, new Item(settings().food(food)));
	}

	private static BlockItem registerBlockItem(String name, Block block) {
		return register(name, new BlockItem(block, settings()));
	}

	private static SpawnEggItem registerSpawnEgg(String name, EntityType<? extends MobEntity> entityType, int primaryColor, int secondaryColor) {
		return register(name, new SpawnEggItem(entityType, primaryColor, secondaryColor, settings()));
	}

	static void register() {
        Registry.register(Registries.ITEM_GROUP, MAIN_ITEM_GROUP, FabricItemGroup.builder()
                .icon(AylythItems.YMPE_DAGGER::getDefaultStack)
                .displayName(Text.translatable("itemGroup.aylyth.main"))
                .entries((displayContext, entries) -> {
                    for (var item : TEMPT_MAIN_ITEM_GROUP_ITEMS) {
                        entries.add(item);
                    }
                    TEMPT_MAIN_ITEM_GROUP_ITEMS.clear();
                })
                .build()
        );
    }
}
