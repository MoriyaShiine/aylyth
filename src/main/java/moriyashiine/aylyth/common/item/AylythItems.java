package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.data.AylythJukeboxSongs;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.item.components.ThornFlechetteEffect;
import moriyashiine.aylyth.common.item.types.AylythianHeartItem;
import moriyashiine.aylyth.common.item.types.AylythianSmithingTemplateUpgradeItem;
import moriyashiine.aylyth.common.item.types.CoricSeedItem;
import moriyashiine.aylyth.common.item.types.DaggerItem;
import moriyashiine.aylyth.common.item.types.DebugWandItem;
import moriyashiine.aylyth.common.item.types.NephriteFlaskItem;
import moriyashiine.aylyth.common.item.types.PomegranateItem;
import moriyashiine.aylyth.common.item.types.ShuckedYmpeFruitItem;
import moriyashiine.aylyth.common.item.types.ThornFlechetteItem;
import moriyashiine.aylyth.common.item.types.YmpeCuirassItem;
import moriyashiine.aylyth.common.item.types.YmpeDaggerItem;
import moriyashiine.aylyth.common.item.types.YmpeEffigyItem;
import moriyashiine.aylyth.common.item.types.YmpeFlambergeItem;
import moriyashiine.aylyth.common.item.types.YmpeGlaiveItem;
import moriyashiine.aylyth.common.item.types.YmpeLanceItem;
import moriyashiine.aylyth.common.item.types.YmpeScytheItem;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BoatItem;
import net.minecraft.item.HangingSignItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SignItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TallBlockItem;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Rarity;
import net.minecraft.util.Unit;
import net.minecraft.util.math.Direction;

import java.util.function.Function;

public interface AylythItems {
    Item DEBUG_WAND = register("debug_wand", DebugWandItem::new, settings());

    Item SAPSTONE =  registerBlockItem("sapstone", AylythBlocks.SAPSTONE);
    Item AMBER_SAPSTONE =  registerBlockItem("amber_sapstone", AylythBlocks.AMBER_SAPSTONE);
    Item LIGNITE_SAPSTONE =  registerBlockItem("lignite_sapstone", AylythBlocks.LIGNITE_SAPSTONE);
    Item OPALESCENT_SAPSTONE =  registerBlockItem("opalescent_sapstone", AylythBlocks.OPALESCENT_SAPSTONE);

    Item GREEN_AYLYTHIAN_OAK_SAPLING = registerBlockItem("green_aylythian_oak_sapling", AylythBlocks.GREEN_AYLYTHIAN_OAK_SAPLING);
    Item GREEN_AYLYTHIAN_OAK_LEAVES = registerBlockItem("green_aylythian_oak_leaves", AylythBlocks.GREEN_AYLYTHIAN_OAK_LEAVES);
    Item DARK_OAK_BRANCH = registerBlockItem("dark_oak_branch", AylythBlocks.DARK_OAK_BRANCH);
    Item BARE_DARK_OAK_BRANCH = registerBlockItem("bare_dark_oak_branch", AylythBlocks.BARE_DARK_OAK_BRANCH);
    
    Item ORANGE_AYLYTHIAN_OAK_SAPLING = registerBlockItem("orange_aylythian_oak_sapling", AylythBlocks.ORANGE_AYLYTHIAN_OAK_SAPLING);
    Item ORANGE_AYLYTHIAN_OAK_LEAVES = registerBlockItem("orange_aylythian_oak_leaves", AylythBlocks.ORANGE_AYLYTHIAN_OAK_LEAVES);
    Item ORANGE_AYLYTHIAN_OAK_BRANCH = registerBlockItem("orange_aylythian_oak_branch", AylythBlocks.ORANGE_AYLYTHIAN_OAK_BRANCH);
    
    Item RED_AYLYTHIAN_OAK_SAPLING = registerBlockItem("red_aylythian_oak_sapling", AylythBlocks.RED_AYLYTHIAN_OAK_SAPLING);
    Item RED_AYLYTHIAN_OAK_LEAVES = registerBlockItem("red_aylythian_oak_leaves", AylythBlocks.RED_AYLYTHIAN_OAK_LEAVES);
    Item RED_AYLYTHIAN_OAK_BRANCH = registerBlockItem("red_aylythian_oak_branch", AylythBlocks.RED_AYLYTHIAN_OAK_BRANCH);

    Item BROWN_AYLYTHIAN_OAK_SAPLING = registerBlockItem("brown_aylythian_oak_sapling", AylythBlocks.BROWN_AYLYTHIAN_OAK_SAPLING);
    Item BROWN_AYLYTHIAN_OAK_LEAVES = registerBlockItem("brown_aylythian_oak_leaves", AylythBlocks.BROWN_AYLYTHIAN_OAK_LEAVES);
    Item BROWN_AYLYTHIAN_OAK_BRANCH = registerBlockItem("brown_aylythian_oak_branch", AylythBlocks.BROWN_AYLYTHIAN_OAK_BRANCH);
    
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
    Item YMPE_DOOR = registerBlockItem("ympe_door", settings -> new TallBlockItem(AylythBlocks.YMPE_DOOR, settings), settings());
    Item YMPE_SIGN = registerBlockItem("ympe_sign", settings -> new SignItem(AylythBlocks.YMPE_SIGN, AylythBlocks.YMPE_WALL_SIGN, settings), settings().maxCount(16));
    Item YMPE_BOAT = register("ympe_boat", settings -> new BoatItem(AylythEntityTypes.YMPE_BOAT, settings), settings().maxCount(1));
    Item YMPE_CHEST_BOAT = register("ympe_chest_boat", settings -> new BoatItem(AylythEntityTypes.YMPE_CHEST_BOAT, settings), settings().maxCount(1));
    Item YMPE_HANGING_SIGN = registerBlockItem("ympe_hanging_sign", settings -> new HangingSignItem(AylythBlocks.YMPE_HANGING_SIGN, AylythBlocks.YMPE_WALL_HANGING_SIGN, settings), settings());
    Item YMPE_LEAVES = registerBlockItem("ympe_leaves", AylythBlocks.YMPE_LEAVES);
    Item FRUIT_BEARING_YMPE_LOG = registerBlockItem("fruit_bearing_ympe_log", AylythBlocks.FRUIT_BEARING_YMPE_LOG);
    Item YMPE_BRANCH = registerBlockItem("ympe_branch", AylythBlocks.YMPE_BRANCH);
    Item BARE_YMPE_BRANCH = registerBlockItem("bare_ympe_branch", AylythBlocks.BARE_YMPE_BRANCH);

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
    Item POMEGRANATE_DOOR = registerBlockItem("pomegranate_door", settings -> new TallBlockItem(AylythBlocks.POMEGRANATE_DOOR, settings), settings());
    Item POMEGRANATE_SIGN = registerBlockItem("pomegranate_sign", settings -> new SignItem(AylythBlocks.POMEGRANATE_SIGN, AylythBlocks.POMEGRANATE_WALL_SIGN, settings), settings().maxCount(16));
    Item POMEGRANATE_BOAT = register("pomegranate_boat", settings -> new BoatItem(AylythEntityTypes.POMEGRANATE_BOAT, settings), settings().maxCount(1));
    Item POMEGRANATE_CHEST_BOAT = register("pomegranate_chest_boat", settings -> new BoatItem(AylythEntityTypes.POMEGRANATE_CHEST_BOAT, settings), settings().maxCount(1));
    Item POMEGRANATE_HANGING_SIGN = registerBlockItem("pomegranate_hanging_sign", settings -> new HangingSignItem(AylythBlocks.POMEGRANATE_HANGING_SIGN, AylythBlocks.POMEGRANATE_WALL_HANGING_SIGN, settings), settings());
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
    Item WRITHEWOOD_DOOR = registerBlockItem("writhewood_door", settings -> new TallBlockItem(AylythBlocks.WRITHEWOOD_DOOR, settings), settings());
    Item WRITHEWOOD_SIGN = registerBlockItem("writhewood_sign", settings -> new SignItem(AylythBlocks.WRITHEWOOD_SIGN, AylythBlocks.WRITHEWOOD_WALL_SIGN, settings), settings().maxCount(16));
    Item WRITHEWOOD_BOAT = register("writhewood_boat", settings -> new BoatItem(AylythEntityTypes.WRITHEWOOD_BOAT, settings), settings().maxCount(1));
    Item WRITHEWOOD_CHEST_BOAT = register("writhewood_chest_boat", settings -> new BoatItem(AylythEntityTypes.WRITHEWOOD_CHEST_BOAT, settings), settings().maxCount(1));
    Item WRITHEWOOD_HANGING_SIGN = registerBlockItem("writhewood_hanging_sign", settings -> new HangingSignItem(AylythBlocks.WRITHEWOOD_HANGING_SIGN, AylythBlocks.WRITHEWOOD_WALL_HANGING_SIGN, settings), settings());
    Item WRITHEWOOD_LEAVES = registerBlockItem("writhewood_leaves", AylythBlocks.WRITHEWOOD_LEAVES);
    Item WRITHEWOOD_BRANCH = registerBlockItem("writhewood_branch", AylythBlocks.WRITHEWOOD_BRANCH);
    Item BARE_WRITHEWOOD_BRANCH = registerBlockItem("bare_writhewood_branch", AylythBlocks.BARE_WRITHEWOOD_BRANCH);

    Item SEEPING_WOOD = registerBlockItem("seeping_wood", AylythBlocks.SEEPING_WOOD);
    Item GIRASOL_SEED = register("girasol_sapling", settings -> new BlockItem(AylythBlocks.GIRASOL_SAPLING, settings), settings());

    Item CHTHONIA_WOOD = registerBlockItem("chthonia_wood", AylythBlocks.CHTHONIA_WOOD);
    Item NEPHRITIC_CHTHONIA_WOOD = registerBlockItem("nephritic_chthonia_wood", AylythBlocks.NEPHRITIC_CHTHONIA_WOOD);

    Item DARK_PODZOL = registerBlockItem("dark_podzol", AylythBlocks.DARK_PODZOL);

    Item AYLYTH_BUSH = registerBlockItem("aylyth_bush", AylythBlocks.AYLYTH_BUSH);
    Item ANTLER_SHOOTS = registerBlockItem("antler_shoots", AylythBlocks.ANTLER_SHOOTS);
    Item GRIPWEED = registerBlockItem("gripweed", AylythBlocks.GRIPWEED);
    Item NYSIAN_GRAPE_VINE = registerBlockItem("nysian_grape_vine", AylythBlocks.NYSIAN_GRAPE_VINE);
    Item MARIGOLD = registerBlockItem("marigolds", AylythBlocks.MARIGOLD);
    Item OAK_STREWN_LEAVES = registerBlockItem("oak_strewn_leaves", AylythBlocks.OAK_STREWN_LEAVES);
    Item YMPE_STREWN_LEAVES = registerBlockItem("ympe_strewn_leaves", AylythBlocks.YMPE_STREWN_LEAVES);
    Item OAK_LEAF_PILE = registerBlockItem("oak_leaf_pile", AylythBlocks.OAK_LEAF_PILE);
    Item YMPE_LEAF_PILE = registerBlockItem("ympe_leaf_pile", AylythBlocks.YMPE_LEAF_PILE);
    Item JACK_O_LANTERN_MUSHROOM = registerBlockItem("jack_o_lantern_mushroom", settings -> new VerticallyAttachableBlockItem(AylythBlocks.JACK_O_LANTERN_MUSHROOM, AylythBlocks.SHELF_JACK_O_LANTERN_MUSHROOM, Direction.DOWN, settings), settings());
    Item GHOSTCAP_MUSHROOM_SPORES = register("ghostcap_mushroom_spores", settings -> new BlockItem(AylythBlocks.GHOSTCAP_MUSHROOM, settings), settings());

    Item JACK_O_LANTERN_MUSHROOM_STEM = registerBlockItem("jack_o_lantern_mushroom_stem", AylythBlocks.JACK_O_LANTERN_MUSHROOM_STEM);
    Item JACK_O_LANTERN_MUSHROOM_BLOCK = registerBlockItem("jack_o_lantern_mushroom_block", AylythBlocks.JACK_O_LANTERN_MUSHROOM_BLOCK);

    Item SMALL_WOODY_GROWTH = registerBlockItem("small_woody_growth", AylythBlocks.SMALL_WOODY_GROWTH);
    Item LARGE_WOODY_GROWTH = registerBlockItem("large_woody_growth", AylythBlocks.LARGE_WOODY_GROWTH);
    Item WOODY_GROWTH_CACHE = registerBlockItem("woody_growth_cache", AylythBlocks.WOODY_GROWTH_CACHE, settings().component(AylythDataComponentTypes.YELLOW_TINTED, Unit.INSTANCE));

    Item OAK_SEEP = registerBlockItem("oak_seep", AylythBlocks.OAK_SEEP);
    Item SPRUCE_SEEP = registerBlockItem("spruce_seep", AylythBlocks.SPRUCE_SEEP);
    Item DARK_OAK_SEEP = registerBlockItem("dark_oak_seep", AylythBlocks.DARK_OAK_SEEP);
    Item YMPE_SEEP = registerBlockItem("ympe_seep", AylythBlocks.YMPE_SEEP);
    Item SEEPING_WOOD_SEEP = registerBlockItem("seeping_wood_seep", AylythBlocks.SEEPING_WOOD_SEEP);
    Item DARK_WOODS_TILES = registerBlockItem("dark_woods_tiles", AylythBlocks.DARK_WOODS_TILES);
    Item BARK = registerSimple("bark");

    Item LANCEOLATE_DAGGER = register("lanceolate_dagger", settings -> new DaggerItem(AylythToolMaterials.NEPHRITE, 1, -2, -0.5f, settings), settings().maxCount(1));
    Item YMPE_DAGGER = register("ympe_dagger", settings -> new YmpeDaggerItem(AylythToolMaterials.NEPHRITE, 2, -2, -0.5f, settings), settings().maxCount(1));
    Item YMPE_GLAIVE = register("ympe_glaive", settings -> new YmpeGlaiveItem(AylythToolMaterials.NEPHRITE, 5, -2.8F, settings), settings().useCooldown(1.75f).fireproof().rarity(Rarity.UNCOMMON).maxCount(1));
    Item YMPE_LANCE = register("ympe_lance", YmpeLanceItem::new, settings().attributeModifiers(YmpeLanceItem.createAttributeModifiers()).repairable(AylythItemTags.NEPHRITE_TOOL_MATERIALS).maxCount(1).maxDamage(312));
    Item YMPE_FLAMBERGE = register("ympe_flamberge", settings -> new YmpeFlambergeItem(AylythToolMaterials.NEPHRITE, 5, -3.1F, settings), settings().fireproof().rarity(Rarity.UNCOMMON).maxCount(1));
    Item YMPE_SCYTHE = register("ympe_scythe", settings -> new YmpeScytheItem(AylythToolMaterials.NEPHRITE, 4, -2.7F, settings), settings().fireproof().rarity(Rarity.UNCOMMON).maxCount(1));

    Item AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE = register("aylythian_upgrade_smithing_template", AylythianSmithingTemplateUpgradeItem::new, settings());
    Item CORIC_SEED = register("coric_seed", CoricSeedItem::new, settings());
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

    Item NEPHRITE_SWORD = register("nephrite_sword", settings -> new SwordItem(AylythToolMaterials.NEPHRITE, 4, -2.4f, settings), settings());
    Item NEPHRITE_SHOVEL = register("nephrite_shovel", settings -> new ShovelItem(AylythToolMaterials.NEPHRITE, 1.5f, -3.0f, settings), settings());
    Item NEPHRITE_PICKAXE = register("nephrite_pickaxe", settings -> new PickaxeItem(AylythToolMaterials.NEPHRITE, 1, -2.8f, settings), settings());
    Item NEPHRITE_AXE = register("nephrite_axe", settings -> new AxeItem(AylythToolMaterials.NEPHRITE, 5, -3.0f, settings), settings());
    Item NEPHRITE_HOE = register("nephrite_hoe", settings -> new HoeItem(AylythToolMaterials.NEPHRITE, -3, 0f, settings), settings());

    Item VAMPIRIC_SWORD = register("vampiric_sword", settings -> new SwordItem(AylythToolMaterials.NEPHRITE_SPECIAL, 4, -2.4f, settings), settings().component(AylythDataComponentTypes.ATTACK_EFFECTS, AylythAttackEffectComponents.VAMPIRIC_SWORD));
    Item VAMPIRIC_PICKAXE = register("vampiric_pickaxe", settings -> new PickaxeItem(AylythToolMaterials.NEPHRITE_SPECIAL, 1, -2.8f, settings), settings().component(AylythDataComponentTypes.ATTACK_EFFECTS, AylythAttackEffectComponents.VAMPIRIC_PICKAXE));
    Item VAMPIRIC_AXE = register("vampiric_axe", settings -> new AxeItem(AylythToolMaterials.NEPHRITE_SPECIAL, 5, -3.0f, settings), settings().component(AylythDataComponentTypes.ATTACK_EFFECTS, AylythAttackEffectComponents.VAMPIRIC_AXE));
    Item VAMPIRIC_HOE = register("vampiric_hoe", settings -> new HoeItem(AylythToolMaterials.NEPHRITE_SPECIAL, -3, 0f, settings), settings().component(AylythDataComponentTypes.ATTACK_EFFECTS, AylythAttackEffectComponents.VAMPIRIC_HOE));

    Item BLIGHTED_SWORD = register("blighted_sword", settings -> new SwordItem(AylythToolMaterials.NEPHRITE_SPECIAL, 4, -2.4f, settings), settings().component(AylythDataComponentTypes.ATTACK_EFFECTS, AylythAttackEffectComponents.BLIGHTED_SWORD));
    Item BLIGHTED_PICKAXE = register("blighted_pickaxe", settings -> new PickaxeItem(AylythToolMaterials.NEPHRITE_SPECIAL, 1, -2.8f, settings), settings().component(AylythDataComponentTypes.ATTACK_EFFECTS, AylythAttackEffectComponents.BLIGHTED_PICKAXE));
    Item BLIGHTED_AXE = register("blighted_axe", settings -> new AxeItem(AylythToolMaterials.NEPHRITE_SPECIAL, 5, -3.0f, settings), settings().component(AylythDataComponentTypes.ATTACK_EFFECTS, AylythAttackEffectComponents.BLIGHTED_AXE));
    Item BLIGHTED_HOE = register("blighted_hoe", settings -> new HoeItem(AylythToolMaterials.NEPHRITE_SPECIAL, -3, 0f, settings), settings().component(AylythDataComponentTypes.ATTACK_EFFECTS, AylythAttackEffectComponents.BLIGHTED_HOE));

    Item THORN_FLECHETTE = register("thorn_flechette", ThornFlechetteItem::new, settings());
    Item BLIGHTED_THORN_FLECHETTE = register("blighted_thorn_flechette", ThornFlechetteItem::new, settings().component(AylythDataComponentTypes.THORN_FLECHETTE_EFFECT, new ThornFlechetteEffect(new StatusEffectInstance(AylythStatusEffects.BLIGHT, 100), 0.5f)));

    Item YMPE_CUIRASS = register("ympe_cuirass", YmpeCuirassItem::new, settings().maxCount(1));
    Item YMPE_EFFIGY = register("ympe_effigy", YmpeEffigyItem::new, (settings()).fireproof().rarity(Rarity.RARE).maxCount(1));
    Item NEPHRITE_FLASK = register("nephrite_flask", NephriteFlaskItem::new, settings().maxCount(1).component(DataComponentTypes.CONSUMABLE, AylythConsumableComponents.NEPHRITE_FLASK).component(AylythDataComponentTypes.MAX_FLASK_CHARGES, 6));
    Item DARK_NEPHRITE_FLASK = register("dark_nephrite_flask", NephriteFlaskItem::new, settings().maxCount(1).component(DataComponentTypes.CONSUMABLE, AylythConsumableComponents.DARK_NEPHRITE_FLASK).component(AylythDataComponentTypes.MAX_FLASK_CHARGES, 6));

    Item YMPE_MUSH = registerFood("ympe_mush", AylythFoodComponents.YMPE_MUSH, AylythConsumableComponents.YMPE_MUSH);
    Item YMPE_FRUIT = registerFood("ympe_fruit", AylythFoodComponents.YMPE_FRUIT, AylythConsumableComponents.YMPE_FRUIT);
    Item SHUCKED_YMPE_FRUIT = register("shucked_ympe_fruit", ShuckedYmpeFruitItem::new, settings().maxCount(1));

    Item NYSIAN_GRAPES = registerFood("nysian_grapes", AylythFoodComponents.NYSIAN_GRAPES);
    Item GHOSTCAP_MUSHROOM = registerFood("ghostcap_mushroom", AylythFoodComponents.GHOSTCAPS, AylythConsumableComponents.GHOSTCAPS);
    Item POMEGRANATE = register("pomegranate", PomegranateItem::new, settings().food(AylythFoodComponents.POMEGRANATE, AylythConsumableComponents.POMEGRANATE));

    Item WRONGMEAT = registerFood("wrongmeat", AylythFoodComponents.WRONGMEAT, AylythConsumableComponents.WRONGMEAT);
    Item AYLYTHIAN_HEART = register("aylythian_heart", AylythianHeartItem::new, settings());
    Item NEPHRITE_HEART = registerSimple("nephrite_heart");
    Item YHONDYTH_HEART = registerSimple("yhondyth_heart");

    Item SOUL_HEARTH = registerBlockItem("soul_hearth", AylythBlocks.SOUL_HEARTH);
    Item VITAL_THURIBLE = registerBlockItem("vital_thurible", AylythBlocks.VITAL_THURIBLE);
    Item BLACK_WELL = registerBlockItem("black_well", AylythBlocks.BLACK_WELL);

    Item PILOT_LIGHT_SPAWN_EGG = registerSpawnEgg("pilot_light_spawn_egg", AylythEntityTypes.PILOT_LIGHT);
    Item AYLYTHIAN_SPAWN_EGG = registerSpawnEgg("aylythian_spawn_egg", AylythEntityTypes.AYLYTHIAN);
    Item ELDER_AYLYTHIAN_SPAWN_EGG = registerSpawnEgg("elder_aylythian_spawn_egg", AylythEntityTypes.ELDER_AYLYTHIAN);
    Item FAUNAYLYTHIAN_SPAWN_EGG = registerSpawnEgg("faunaylythian_spawn_egg", AylythEntityTypes.FAUNAYLYTHIAN);
    Item WREATHED_HIND_SPAWN_EGG = registerSpawnEgg("wreathed_hind_spawn_egg", AylythEntityTypes.WREATHED_HIND_ENTITY);
    Item SCION_SPAWN_EGG = registerSpawnEgg("scion_spawn_egg", AylythEntityTypes.SCION);
    Item YMPEMOULD_SPAWN_EGG = registerSpawnEgg("ympemould_spawn_egg", AylythEntityTypes.YMPEMOULD);
    Item BONEFLY_SPAWN_EGG = registerSpawnEgg("bonefly_spawn_egg", AylythEntityTypes.BONEFLY);
    Item TULPA_SPAWN_EGG = registerSpawnEgg("tulpa_spawn_egg", AylythEntityTypes.TULPA);

    Item POMEGRANATE_CASSETTE = register("pomegranate_cassette", settings().maxCount(1).rarity(Rarity.RARE).jukeboxPlayable(AylythJukeboxSongs.POMEGRANATE_MUSIC_DISC));

    private static Item.Settings settings() {
		return new Item.Settings();
	}

    private static Item register(String name, Function<Item.Settings, Item> function, Item.Settings settings) {
        return Items.register(RegistryKey.of(RegistryKeys.ITEM, Aylyth.id(name)), function, settings);
    }
    
    private static Item register(String name, Item.Settings settings) {
        return register(name, Item::new, settings);
    }

	private static Item registerSimple(String name) {
		return register(name, settings());
	}

	private static Item registerFood(String name, FoodComponent food) {
		return register(name, settings().food(food));
	}

    private static Item registerFood(String name, FoodComponent food, ConsumableComponent consumable) {
        return register(name, settings().food(food, consumable));
    }

    private static Item registerBlockItem(String name, Function<Item.Settings, Item> function, Item.Settings settings) {
        return register(name, function, settings.useBlockPrefixedTranslationKey());
    }

    private static Item registerBlockItem(String name, Block block, Item.Settings defaultSettings) {
        return registerBlockItem(name, settings -> new BlockItem(block, settings), defaultSettings);
    }

    private static Item registerBlockItem(String name, Block block) {
        return registerBlockItem(name, block, settings());
    }

	private static Item registerSpawnEgg(String name, EntityType<? extends MobEntity> entityType) {
		return register(name, settings -> new SpawnEggItem(entityType, settings), settings());
	}

	static void register() {}
}
