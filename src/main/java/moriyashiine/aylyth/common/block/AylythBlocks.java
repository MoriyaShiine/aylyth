package moriyashiine.aylyth.common.block;

import com.google.common.base.Function;
import com.google.common.base.Suppliers;
import com.terraformersmc.terraform.sign.api.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.api.block.TerraformWallSignBlock;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.types.*;
import moriyashiine.aylyth.common.data.AylythLootTables;
import moriyashiine.aylyth.common.data.world.feature.AylythConfiguredFeatures;
import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

import static net.minecraft.block.AbstractBlock.Settings.copy;

public interface AylythBlocks {
	BlockSetType YMPE_BLOCK_SET_TYPE = new BlockSetTypeBuilder().register(Aylyth.id("ympe"));
	BlockSetType POMEGRANATE_BLOCK_SET_TYPE = new BlockSetTypeBuilder().register(Aylyth.id("pomegranate"));
	BlockSetType WRITHEWOOD_BLOCK_SET_TYPE = new BlockSetTypeBuilder().register(Aylyth.id("writhewood"));
	WoodType YMPE_WOOD_TYPE = new WoodTypeBuilder().register(Aylyth.id("ympe"), YMPE_BLOCK_SET_TYPE);
	WoodType POMEGRANATE_WOOD_TYPE = new WoodTypeBuilder().register(Aylyth.id("pomegranate"), POMEGRANATE_BLOCK_SET_TYPE);
	WoodType WRITHEWOOD_WOOD_TYPE = new WoodTypeBuilder().register(Aylyth.id("writhewood"), WRITHEWOOD_BLOCK_SET_TYPE);

	Block SAPSTONE = register("sapstone", copy(Blocks.STONE).mapColor(MapColor.DIRT_BROWN));
	Block AMBER_SAPSTONE = register("amber_sapstone", copy(Blocks.STONE).mapColor(MapColor.ORANGE));
	Block LIGNITE_SAPSTONE = register("lignite_sapstone", copy(Blocks.STONE).mapColor(MapColor.TERRACOTTA_GRAY));
	Block OPALESCENT_SAPSTONE = register("opalescent_sapstone", copy(Blocks.STONE).mapColor(MapColor.PINK));

	Block GREEN_AYLYTHIAN_OAK_SAPLING = register("green_aylythian_oak_sapling", settings -> new SaplingBlock(SaplingGenerators.GREEN_AYLYTHIAN_OAK, settings), copy(Blocks.OAK_SAPLING).mapColor(MapColor.GREEN));
	Block POTTED_GREEN_AYLYTHIAN_OAK_SAPLING = register("potted_green_aylythian_oak_sapling", settings -> new FlowerPotBlock(GREEN_AYLYTHIAN_OAK_SAPLING, settings), Blocks.createFlowerPotSettings());
	Block GREEN_AYLYTHIAN_OAK_LEAVES = register("green_aylythian_oak_leaves", LeavesBlock::new, Blocks.createLeavesSettings(BlockSoundGroup.GRASS));
	Block DARK_OAK_BRANCH = register("dark_oak_branch", BranchBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY));
	Block BARE_DARK_OAK_BRANCH = register("bare_dark_oak_branch", BranchBlock::new, copy(DARK_OAK_BRANCH).mapColor(MapColor.BROWN));
	
	Block ORANGE_AYLYTHIAN_OAK_SAPLING = register("orange_aylythian_oak_sapling", settings -> new SaplingBlock(SaplingGenerators.ORANGE_AYLYTHIAN_OAK, settings), copy(Blocks.OAK_SAPLING).mapColor(MapColor.ORANGE));
	Block POTTED_ORANGE_AYLYTHIAN_OAK_SAPLING = register("potted_orange_aylythian_oak_sapling", settings -> new FlowerPotBlock(ORANGE_AYLYTHIAN_OAK_SAPLING, settings), Blocks.createFlowerPotSettings());
	Block ORANGE_AYLYTHIAN_OAK_LEAVES = register("orange_aylythian_oak_leaves", LeavesBlock::new, copy(Blocks.OAK_LEAVES).mapColor(MapColor.ORANGE));
	Block ORANGE_AYLYTHIAN_OAK_BRANCH = register("orange_aylythian_oak_branch", BranchBlock::new, copy(DARK_OAK_BRANCH).mapColor(MapColor.ORANGE));

	Block RED_AYLYTHIAN_OAK_SAPLING = register("red_aylythian_oak_sapling", settings -> new SaplingBlock(SaplingGenerators.RED_AYLYTHIAN_OAK, settings), copy(Blocks.OAK_SAPLING).mapColor(MapColor.RED));
	Block POTTED_RED_AYLYTHIAN_OAK_SAPLING = register("potted_red_aylythian_oak_sapling", settings -> new FlowerPotBlock(RED_AYLYTHIAN_OAK_SAPLING, settings), Blocks.createFlowerPotSettings());
	Block RED_AYLYTHIAN_OAK_LEAVES = register("red_aylythian_oak_leaves", LeavesBlock::new, copy(Blocks.OAK_LEAVES).mapColor(MapColor.RED));
	Block RED_AYLYTHIAN_OAK_BRANCH = register("red_aylythian_oak_branch", BranchBlock::new, copy(DARK_OAK_BRANCH).mapColor(MapColor.RED));
	
	Block BROWN_AYLYTHIAN_OAK_SAPLING = register("brown_aylythian_oak_sapling", settings -> new SaplingBlock(SaplingGenerators.BROWN_AYLYTHIAN_OAK, settings), copy(Blocks.OAK_SAPLING).mapColor(MapColor.BROWN));
	Block POTTED_BROWN_AYLYTHIAN_OAK_SAPLING = register("potted_brown_aylythian_oak_sapling", settings -> new FlowerPotBlock(BROWN_AYLYTHIAN_OAK_SAPLING, settings), Blocks.createFlowerPotSettings());
	Block BROWN_AYLYTHIAN_OAK_LEAVES = register("brown_aylythian_oak_leaves", LeavesBlock::new, copy(Blocks.OAK_LEAVES).mapColor(MapColor.BROWN));
	Block BROWN_AYLYTHIAN_OAK_BRANCH = register("brown_aylythian_oak_branch", BranchBlock::new, copy(DARK_OAK_BRANCH).mapColor(MapColor.BROWN));

	Block YMPE_STRIPPED_LOG = register("stripped_ympe_log", PillarBlock::new, copy(Blocks.STRIPPED_OAK_LOG));
	Block YMPE_STRIPPED_WOOD = register("stripped_ympe_wood", PillarBlock::new, copy(Blocks.STRIPPED_OAK_WOOD));
	Block YMPE_LOG = register("ympe_log", PillarBlock::new, copy(Blocks.OAK_LOG));
	Block YMPE_WOOD = register("ympe_wood", PillarBlock::new, copy(Blocks.OAK_WOOD));
	Block YMPE_SAPLING = register("ympe_sapling", settings -> new YmpeSaplingBlock(SaplingGenerators.YMPE, AylythConfiguredFeatures.TWISTED_YMPE_GROWTH, settings), copy(Blocks.OAK_SAPLING));
	Block POTTED_YMPE_SAPLING = register("potted_ympe_sapling", settings -> new FlowerPotBlock(YMPE_SAPLING, settings), Blocks.createFlowerPotSettings());
	Block YMPE_PLANKS = register("ympe_planks", copy(Blocks.OAK_PLANKS));
	Block YMPE_STAIRS = register("ympe_stairs", settings -> new StairsBlock(YMPE_PLANKS.getDefaultState(), settings), copy(Blocks.OAK_STAIRS));
	Block YMPE_SLAB = register("ympe_slab", SlabBlock::new, copy(Blocks.OAK_SLAB));
	Block YMPE_FENCE = register("ympe_fence", FenceBlock::new, copy(Blocks.OAK_FENCE));
	Block YMPE_FENCE_GATE = register("ympe_fence_gate", settings -> new FenceGateBlock(YMPE_WOOD_TYPE, settings), copy(Blocks.OAK_FENCE_GATE));
	Block YMPE_PRESSURE_PLATE = register("ympe_pressure_plate", settings -> new PressurePlateBlock(YMPE_BLOCK_SET_TYPE, settings), copy(Blocks.OAK_PRESSURE_PLATE));
	Block YMPE_BUTTON = register("ympe_button", settings -> new ButtonBlock(YMPE_BLOCK_SET_TYPE, 30, settings), copy(Blocks.OAK_BUTTON));
	Block YMPE_TRAPDOOR = register("ympe_trapdoor", settings -> new TrapdoorBlock(YMPE_BLOCK_SET_TYPE, settings), copy(Blocks.OAK_TRAPDOOR));
	Block YMPE_DOOR = register("ympe_door", settings -> new DoorBlock(YMPE_BLOCK_SET_TYPE, settings), copy(Blocks.OAK_DOOR));
	Block YMPE_SIGN = register("ympe_sign", settings -> new TerraformSignBlock(Aylyth.id("entity/signs/ympe"), YMPE_WOOD_TYPE, settings), copy(Blocks.OAK_SIGN));
	Block YMPE_WALL_SIGN = register("ympe_wall_sign", settings -> new TerraformWallSignBlock(Aylyth.id("entity/signs/ympe"), YMPE_WOOD_TYPE, settings), copy(Blocks.OAK_WALL_SIGN).lootTable(YMPE_SIGN.getLootTableKey()));
	Block YMPE_HANGING_SIGN = register("ympe_hanging_sign", settings -> new TerraformHangingSignBlock(Aylyth.id("entity/signs/hanging/ympe"), Aylyth.id("textures/gui/hanging_signs/ympe"), YMPE_WOOD_TYPE, settings), copy(Blocks.OAK_HANGING_SIGN));
	Block YMPE_WALL_HANGING_SIGN = register("ympe_wall_hanging_sign", settings -> new TerraformWallHangingSignBlock(Aylyth.id("entity/signs/hanging/ympe"), Aylyth.id("textures/gui/hanging_signs/ympe"), YMPE_WOOD_TYPE, settings), copy(Blocks.OAK_HANGING_SIGN).lootTable(YMPE_HANGING_SIGN.getLootTableKey()));
	Block YMPE_LEAVES = register("ympe_leaves", LeavesBlock::new, copy(Blocks.OAK_LEAVES).mapColor(MapColor.ORANGE));
	Block FRUIT_BEARING_YMPE_LOG = register("fruit_bearing_ympe_log", settings -> new GrowingHarvestablePillarBlock(AylythLootTables.FRUIT_BEARING_YMPE_LOG, settings), copy(AylythBlocks.YMPE_LOG));
	Block YMPE_BRANCH = register("ympe_branch", BranchBlock::new, copy(BROWN_AYLYTHIAN_OAK_BRANCH).mapColor(MapColor.ORANGE));
	Block BARE_YMPE_BRANCH = register("bare_ympe_branch", BranchBlock::new, copy(BROWN_AYLYTHIAN_OAK_BRANCH).mapColor(MapColor.BROWN));

	Block POMEGRANATE_STRIPPED_LOG = register("stripped_pomegranate_log", PillarBlock::new, copy(Blocks.STRIPPED_OAK_LOG));
	Block POMEGRANATE_STRIPPED_WOOD = register("stripped_pomegranate_wood", PillarBlock::new, copy(Blocks.STRIPPED_OAK_WOOD));
	Block POMEGRANATE_LOG = register("pomegranate_log", PillarBlock::new, copy(Blocks.OAK_LOG));
	Block POMEGRANATE_WOOD = register("pomegranate_wood", PillarBlock::new, copy(Blocks.OAK_WOOD));
	Block POMEGRANATE_SAPLING = register("pomegranate_sapling", settings -> new SaplingBlock(SaplingGenerators.POMEGRANATE, settings), copy(Blocks.OAK_SAPLING));
	Block POTTED_POMEGRANATE_SAPLING = register("potted_pomegranate_sapling", settings -> new FlowerPotBlock(POMEGRANATE_SAPLING, settings), Blocks.createFlowerPotSettings());
	Block POMEGRANATE_PLANKS = register("pomegranate_planks", copy(Blocks.OAK_PLANKS));
	Block POMEGRANATE_STAIRS = register("pomegranate_stairs", settings -> new StairsBlock(POMEGRANATE_PLANKS.getDefaultState(), settings), copy(Blocks.OAK_STAIRS));
	Block POMEGRANATE_SLAB = register("pomegranate_slab", SlabBlock::new, copy(Blocks.OAK_SLAB));
	Block POMEGRANATE_FENCE = register("pomegranate_fence", FenceBlock::new, copy(Blocks.OAK_FENCE));
	Block POMEGRANATE_FENCE_GATE = register("pomegranate_fence_gate", settings -> new FenceGateBlock(POMEGRANATE_WOOD_TYPE, settings), copy(Blocks.OAK_FENCE_GATE));
	Block POMEGRANATE_PRESSURE_PLATE = register("pomegranate_pressure_plate", settings -> new PressurePlateBlock(POMEGRANATE_BLOCK_SET_TYPE, settings), copy(Blocks.OAK_PRESSURE_PLATE));
	Block POMEGRANATE_BUTTON = register("pomegranate_button", settings -> new ButtonBlock(POMEGRANATE_BLOCK_SET_TYPE, 30, settings), copy(Blocks.OAK_BUTTON));
	Block POMEGRANATE_TRAPDOOR = register("pomegranate_trapdoor", settings -> new TrapdoorBlock(POMEGRANATE_BLOCK_SET_TYPE, settings), copy(Blocks.OAK_TRAPDOOR));
	Block POMEGRANATE_DOOR = register("pomegranate_door", settings -> new DoorBlock(POMEGRANATE_BLOCK_SET_TYPE, settings), copy(Blocks.OAK_DOOR));
	Block POMEGRANATE_SIGN = register("pomegranate_sign", settings -> new TerraformSignBlock(Aylyth.id("entity/signs/pomegranate"), POMEGRANATE_WOOD_TYPE, settings), copy(Blocks.OAK_SIGN));
	Block POMEGRANATE_WALL_SIGN = register("pomegranate_wall_sign", settings -> new TerraformWallSignBlock(Aylyth.id("entity/signs/pomegranate"), POMEGRANATE_WOOD_TYPE, settings), copy(Blocks.OAK_WALL_SIGN).lootTable(POMEGRANATE_SIGN.getLootTableKey()));
	Block POMEGRANATE_HANGING_SIGN = register("pomegranate_hanging_sign", settings -> new TerraformHangingSignBlock(Aylyth.id("entity/signs/hanging/pomegranate"), Aylyth.id("textures/gui/hanging_signs/pomegranate"), POMEGRANATE_WOOD_TYPE, settings), copy(Blocks.OAK_HANGING_SIGN));
	Block POMEGRANATE_WALL_HANGING_SIGN = register("pomegranate_wall_hanging_sign", settings -> new TerraformWallHangingSignBlock(Aylyth.id("entity/signs/hanging/pomegranate"), Aylyth.id("textures/gui/hanging_signs/pomegranate"), POMEGRANATE_WOOD_TYPE, settings), copy(Blocks.OAK_HANGING_SIGN).lootTable(POMEGRANATE_HANGING_SIGN.getLootTableKey()));
	Block POMEGRANATE_LEAVES = register("pomegranate_leaves", PomegranateLeavesBlock::new, copy(Blocks.OAK_LEAVES));

	Block WRITHEWOOD_STRIPPED_LOG = register("stripped_writhewood_log", PillarBlock::new, copy(Blocks.STRIPPED_OAK_LOG));
	Block WRITHEWOOD_STRIPPED_WOOD = register("stripped_writhewood_wood", PillarBlock::new, copy(Blocks.STRIPPED_OAK_WOOD));
	Block WRITHEWOOD_LOG = register("writhewood_log", PillarBlock::new, copy(Blocks.OAK_LOG));
	Block WRITHEWOOD_WOOD = register("writhewood_wood", PillarBlock::new, copy(Blocks.OAK_WOOD));
	Block WRITHEWOOD_SAPLING = register("writhewood_sapling", settings -> new WaterloggableSaplingBlock(SaplingGenerators.WRITHEWOOD, settings), copy(Blocks.OAK_SAPLING));
	Block POTTED_WRITHEWOOD_SAPLING = register("potted_writhewood_sapling", settings -> new FlowerPotBlock(WRITHEWOOD_SAPLING, settings), Blocks.createFlowerPotSettings());
	Block WRITHEWOOD_PLANKS = register("writhewood_planks", copy(Blocks.OAK_PLANKS));
	Block WRITHEWOOD_STAIRS = register("writhewood_stairs", settings -> new StairsBlock(WRITHEWOOD_PLANKS.getDefaultState(), settings), copy(Blocks.OAK_STAIRS));
	Block WRITHEWOOD_SLAB = register("writhewood_slab", SlabBlock::new, copy(Blocks.OAK_SLAB));
	Block WRITHEWOOD_FENCE = register("writhewood_fence", FenceBlock::new, copy(Blocks.OAK_FENCE));
	Block WRITHEWOOD_FENCE_GATE = register("writhewood_fence_gate", settings -> new FenceGateBlock(WRITHEWOOD_WOOD_TYPE, settings), copy(Blocks.OAK_FENCE_GATE));
	Block WRITHEWOOD_PRESSURE_PLATE = register("writhewood_pressure_plate", settings -> new PressurePlateBlock(WRITHEWOOD_BLOCK_SET_TYPE, settings), copy(Blocks.OAK_PRESSURE_PLATE));
	Block WRITHEWOOD_BUTTON = register("writhewood_button", settings -> new ButtonBlock(WRITHEWOOD_BLOCK_SET_TYPE, 30, settings), copy(Blocks.OAK_BUTTON));
	Block WRITHEWOOD_TRAPDOOR = register("writhewood_trapdoor", settings -> new TrapdoorBlock(WRITHEWOOD_BLOCK_SET_TYPE, settings), copy(Blocks.OAK_TRAPDOOR));
	Block WRITHEWOOD_DOOR = register("writhewood_door", settings -> new DoorBlock(WRITHEWOOD_BLOCK_SET_TYPE, settings), copy(Blocks.OAK_DOOR));
	Block WRITHEWOOD_SIGN = register("writhewood_sign", settings -> new TerraformSignBlock(Aylyth.id("entity/signs/writhewood"), WRITHEWOOD_WOOD_TYPE, settings), copy(Blocks.OAK_SIGN));
	Block WRITHEWOOD_WALL_SIGN = register("writhewood_wall_sign", settings -> new TerraformWallSignBlock(Aylyth.id("entity/signs/writhewood"), WRITHEWOOD_WOOD_TYPE, settings), copy(Blocks.OAK_WALL_SIGN).lootTable(WRITHEWOOD_SIGN.getLootTableKey()));
	Block WRITHEWOOD_HANGING_SIGN = register("writhewood_hanging_sign", settings -> new TerraformHangingSignBlock(Aylyth.id("entity/signs/hanging/writhewood"), Aylyth.id("textures/gui/hanging_signs/writhewood"), WRITHEWOOD_WOOD_TYPE, settings), copy(Blocks.OAK_HANGING_SIGN));
	Block WRITHEWOOD_WALL_HANGING_SIGN = register("writhewood_wall_hanging_sign", settings -> new TerraformWallHangingSignBlock(Aylyth.id("entity/signs/hanging/writhewood"), Aylyth.id("textures/gui/hanging_signs/writhewood"), WRITHEWOOD_WOOD_TYPE, settings), copy(Blocks.OAK_HANGING_SIGN).lootTable(WRITHEWOOD_HANGING_SIGN.getLootTableKey()));
	Block WRITHEWOOD_LEAVES = register("writhewood_leaves", LeavesBlock::new, copy(Blocks.OAK_LEAVES).mapColor(MapColor.ORANGE));
	Block WRITHEWOOD_BRANCH = register("writhewood_branch", BranchBlock::new, copy(BROWN_AYLYTHIAN_OAK_BRANCH).mapColor(MapColor.ORANGE));
	Block BARE_WRITHEWOOD_BRANCH = register("bare_writhewood_branch", BranchBlock::new, copy(BROWN_AYLYTHIAN_OAK_BRANCH).mapColor(MapColor.OAK_TAN));

	Block SEEPING_WOOD = register("seeping_wood", PillarBlock::new, copy(Blocks.OAK_WOOD));
	Block GIRASOL_SAPLING = register("girasol_sapling", settings -> new GirasolSaplingBlock(SaplingGenerators.GIRASOL, settings), copy(Blocks.OAK_SAPLING));
	Block POTTED_GIRASOL_SAPLING = register("potted_girasol_sapling", settings -> new FlowerPotBlock(GIRASOL_SAPLING, settings), Blocks.createFlowerPotSettings());

	Block CHTHONIA_WOOD = register("chthonia_wood", PillarBlock::new, copy(Blocks.OAK_WOOD).mapColor(MapColor.PALE_GREEN));
	Block NEPHRITIC_CHTHONIA_WOOD = register("nephritic_chthonia_wood", settings -> new OneTimeHarvestablePillarBlock(AylythLootTables.NEPHRITIC_CHTHONIA_WOOD, state -> AylythBlocks.CHTHONIA_WOOD.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)), settings), copy(Blocks.OAK_WOOD).mapColor(MapColor.PALE_GREEN));

	Block DARK_PODZOL = register("dark_podzol", SnowyBlock::new, copy(Blocks.PODZOL).mapColor(MapColor.TERRACOTTA_BLACK));

	Block AYLYTHIAN_BUSH = register("aylythian_bush", AylythianBushBlock::new, copy(Blocks.SHORT_GRASS));
	Block ANTLER_SHOOTS = register("antler_shoots", AntlerShootsBlock::new, copy(Blocks.SHORT_GRASS).offset(AbstractBlock.OffsetType.XZ));
	Block GRIPWEED = register("gripweed", GripweedBlock::new, copy(Blocks.SHORT_GRASS).offset(AbstractBlock.OffsetType.XZ));
	
	Block NYSIAN_GRAPE_VINE = register("nysian_grape_vine", NysianGrapeVineBlock::new, copy(Blocks.VINE));

	Block MARIGOLD = register("marigolds", settings -> new FlowerBlock(AylythStatusEffects.MORTECHIS, 9, settings), copy(Blocks.DANDELION));
	Block POTTED_MARIGOLD = register("potted_marigolds", settings -> new FlowerPotBlock(MARIGOLD, settings), Blocks.createFlowerPotSettings());
	Block JACK_O_LANTERN_MUSHROOM = register("jack_o_lantern_mushroom", settings -> new JackolanternMushroomBlock(Suppliers.memoize(() -> Registries.BLOCK.get(Aylyth.id("shelf_jack_o_lantern_mushroom"))), settings), AbstractBlock.Settings.create().notSolid().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.WART_BLOCK).noCollision().nonOpaque().ticksRandomly().luminance(state -> state.get(JackolanternShelfMushroomBlock.GLOWING) ? state.get(StagedMushroomPlantBlock.STAGE)+4 : 0));
	Block SHELF_JACK_O_LANTERN_MUSHROOM = register("shelf_jack_o_lantern_mushroom", settings -> new JackolanternShelfMushroomBlock(() -> JACK_O_LANTERN_MUSHROOM, settings), AbstractBlock.Settings.create().notSolid().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.WART_BLOCK).noCollision().nonOpaque().ticksRandomly().luminance(state -> state.get(JackolanternShelfMushroomBlock.GLOWING) ? 5 : 0));
	Block GHOSTCAP_MUSHROOM = register("ghostcap_mushroom", SpreadingPlantBlock::new, AbstractBlock.Settings.create().notSolid().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.GLOW_LICHEN).noCollision().nonOpaque().ticksRandomly());

	Block JACK_O_LANTERN_MUSHROOM_STEM = register("jack_o_lantern_mushroom_stem", MushroomBlock::new, copy(Blocks.MUSHROOM_STEM).mapColor(MapColor.TERRACOTTA_ORANGE));
	Block JACK_O_LANTERN_MUSHROOM_BLOCK = register("jack_o_lantern_mushroom_block", MushroomBlock::new, copy(Blocks.BROWN_MUSHROOM_BLOCK).mapColor(state -> !state.get(MushroomBlock.UP) ? MapColor.EMERALD_GREEN : MapColor.ORANGE).luminance(value -> 15));

	Block OAK_STREWN_LEAVES = register("oak_strewn_leaves", StrewnLeavesBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).notSolid().pistonBehavior(PistonBehavior.DESTROY).replaceable().sounds(AylythSoundEvents.STREWN_LEAVES));
	Block YMPE_STREWN_LEAVES = register("ympe_strewn_leaves", StrewnLeavesBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).notSolid().pistonBehavior(PistonBehavior.DESTROY).replaceable().sounds(AylythSoundEvents.STREWN_LEAVES));
	Block OAK_LEAF_PILE = register("oak_leaf_pile", LeafPileBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).notSolid().pistonBehavior(PistonBehavior.DESTROY).replaceable().sounds(AylythSoundEvents.LEAF_PILE));
	Block YMPE_LEAF_PILE = register("ympe_leaf_pile", LeafPileBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).notSolid().pistonBehavior(PistonBehavior.DESTROY).replaceable().sounds(AylythSoundEvents.LEAF_PILE));

	Block SMALL_WOODY_GROWTH = register("small_woody_growth", SmallWoodyGrowthBlock::new, AbstractBlock.Settings.create().burnable().strength(2.0f).sounds(BlockSoundGroup.WOOD));
	Block LARGE_WOODY_GROWTH = register("large_woody_growth", LargeWoodyGrowthBlock::new, AbstractBlock.Settings.create().burnable().strength(2.0f).sounds(BlockSoundGroup.WOOD));
	Block WOODY_GROWTH_CACHE = register("woody_growth_cache", WoodyGrowthCacheBlock::new, AbstractBlock.Settings.create().burnable().strength(2.0f).sounds(BlockSoundGroup.WOOD));

	Block OAK_SEEP = register("oak_seep", SeepBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).burnable().strength(2).sounds(BlockSoundGroup.WOOD));
	Block SPRUCE_SEEP = register("spruce_seep", SeepBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).burnable().strength(2).sounds(BlockSoundGroup.WOOD));
	Block DARK_OAK_SEEP = register("dark_oak_seep", SeepBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.BROWN).burnable().strength(2).sounds(BlockSoundGroup.WOOD));
	Block YMPE_SEEP = register("ympe_seep", SeepBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).burnable().strength(2).sounds(BlockSoundGroup.WOOD)); // TODO: Change to the map color of ympe, same with seeping wood
	Block SEEPING_WOOD_SEEP = register("seeping_wood_seep", SeepBlock::new, AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).burnable().strength(2).sounds(BlockSoundGroup.WOOD));

	Block DARK_WOODS_TILES = register("dark_woods_tiles", copy(Blocks.DARK_OAK_PLANKS));
	Block ESSTLINE_BLOCK = register("esstline_block", copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.BLACK));
	Block NEPHRITE_BLOCK = register("nephrite_block", copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN));

	Block CARVED_SMOOTH_NEPHRITE = register("carved_smooth_nephrite", copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN));
	Block CARVED_ANTLERED_NEPHRITE = register("carved_antlered_nephrite", copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN));
	Block CARVED_NEPHRITE_PILLAR = register("carved_nephrite_pillar", PillarBlock::new, copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN));
	Block CARVED_NEPHRITE_TILES = register("carved_nephrite_tiles", copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN));
	Block CARVED_WOODY_NEPHRITE = register("carved_woody_nephrite", copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN));

	Block SOUL_HEARTH = register("soul_hearth", SoulHearthBlock::new, copy(Blocks.DEEPSLATE).nonOpaque().requiresTool().strength(3.5F).luminance((state) -> state.get(SoulHearthBlock.CHARGES) > 0 ? 13 : 0));
	Block VITAL_THURIBLE = register("vital_thurible", VitalThuribleBlock::new, copy(Blocks.DEEPSLATE).nonOpaque().requiresTool().strength(3.5F).luminance((state) -> state.get(VitalThuribleBlock.ACTIVE) ? 13 : 0));
	Block BLACK_WELL = register("black_well", BlackWellBlock::new, copy(Blocks.DEEPSLATE));

	private static Block register(String name, AbstractBlock.Settings settings) {
		return register(name, Block::new, settings);
	}
	
	private static Block register(String name, Function<AbstractBlock.Settings, Block> function, AbstractBlock.Settings settings) {
		return Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, Aylyth.id(name)), function, settings);
	}

	// Load static initializer
	static void register() {}
}
