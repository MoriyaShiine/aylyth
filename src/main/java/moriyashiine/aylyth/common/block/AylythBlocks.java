package moriyashiine.aylyth.common.block;

import com.google.common.base.Suppliers;
import com.terraformersmc.terraform.sign.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.types.*;
import moriyashiine.aylyth.common.data.world.feature.AylythConfiguredFeatures;
import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;

public interface AylythBlocks {

	BlockSetType YMPE_BLOCK_SET_TYPE = new BlockSetTypeBuilder().register(Aylyth.id("ympe"));
	BlockSetType POMEGRANATE_BLOCK_SET_TYPE = new BlockSetTypeBuilder().register(Aylyth.id("pomegranate"));
	BlockSetType WRITHEWOOD_BLOCK_SET_TYPE = new BlockSetTypeBuilder().register(Aylyth.id("writhewood"));
	WoodType YMPE_WOOD_TYPE = new WoodTypeBuilder().register(Aylyth.id("ympe"), YMPE_BLOCK_SET_TYPE);
	WoodType POMEGRANATE_WOOD_TYPE = new WoodTypeBuilder().register(Aylyth.id("pomegranate"), POMEGRANATE_BLOCK_SET_TYPE);
	WoodType WRITHEWOOD_WOOD_TYPE = new WoodTypeBuilder().register(Aylyth.id("writhewood"), WRITHEWOOD_BLOCK_SET_TYPE);

	Block SAPSTONE = register("sapstone", new Block(copyOf(Blocks.STONE).mapColor(MapColor.OAK_TAN)));
	Block AMBER_SAPSTONE = register("amber_sapstone", new Block(copyOf(Blocks.STONE).mapColor(MapColor.ORANGE)));
	Block LIGNITE_SAPSTONE = register("lignite_sapstone", new Block(copyOf(Blocks.STONE).mapColor(MapColor.TERRACOTTA_GRAY)));
	Block OPALESCENT_SAPSTONE = register("opalescent_sapstone", new Block(copyOf(Blocks.STONE).mapColor(MapColor.DULL_PINK)));

	Block YMPE_STRIPPED_LOG = register("stripped_ympe_log", new PillarBlock(copyOf(Blocks.STRIPPED_OAK_LOG)));
	Block YMPE_STRIPPED_WOOD = register("stripped_ympe_wood", new PillarBlock(copyOf(Blocks.STRIPPED_OAK_WOOD)));
	Block YMPE_LOG = register("ympe_log", new PillarBlock(copyOf(Blocks.OAK_LOG)));
	Block YMPE_WOOD = register("ympe_wood", new PillarBlock(copyOf(Blocks.OAK_WOOD)));
	Block YMPE_SAPLING = register("ympe_sapling", new YmpeSaplingBlock(simpleLargeSaplingGenerator(AylythConfiguredFeatures.YMPE_TREE, AylythConfiguredFeatures.BIG_YMPE_TREE), AylythConfiguredFeatures.TWISTED_YMPE_GROWTH, copyOf(Blocks.OAK_SAPLING)));
	Block YMPE_POTTED_SAPLING = register("potted_ympe_sapling", new FlowerPotBlock(YMPE_SAPLING, copyOf(Blocks.POTTED_OAK_SAPLING)));
	Block YMPE_PLANKS = register("ympe_planks", new Block(copyOf(Blocks.OAK_PLANKS)));
	Block YMPE_STAIRS = register("ympe_stairs", new StairsBlock(YMPE_PLANKS.getDefaultState(), copyOf(Blocks.OAK_STAIRS)));
	Block YMPE_SLAB = register("ympe_slab", new SlabBlock(copyOf(Blocks.OAK_SLAB)));
	Block YMPE_FENCE = register("ympe_fence", new FenceBlock(copyOf(Blocks.OAK_FENCE)));
	Block YMPE_FENCE_GATE = register("ympe_fence_gate", new FenceGateBlock(copyOf(Blocks.OAK_FENCE_GATE), YMPE_WOOD_TYPE));
	Block YMPE_PRESSURE_PLATE = register("ympe_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, copyOf(Blocks.OAK_PRESSURE_PLATE), YMPE_BLOCK_SET_TYPE));
	Block YMPE_BUTTON = register("ympe_button", new ButtonBlock(copyOf(Blocks.OAK_BUTTON), YMPE_BLOCK_SET_TYPE, 30, true));
	Block YMPE_TRAPDOOR = register("ympe_trapdoor", new TrapdoorBlock(copyOf(Blocks.OAK_TRAPDOOR), YMPE_BLOCK_SET_TYPE));
	Block YMPE_DOOR = register("ympe_door", new DoorBlock(copyOf(Blocks.OAK_DOOR), YMPE_BLOCK_SET_TYPE));
	TerraformSignBlock YMPE_SIGN = register("ympe_sign", new TerraformSignBlock(Aylyth.id("entity/signs/ympe"), copyOf(Blocks.OAK_SIGN)));
	Block YMPE_WALL_SIGN = register("ympe_wall_sign", new TerraformWallSignBlock(Aylyth.id("entity/signs/ympe"), copyOf(Blocks.OAK_WALL_SIGN).dropsLike(YMPE_SIGN)));
	TerraformHangingSignBlock YMPE_HANGING_SIGN = register("ympe_hanging_sign", new TerraformHangingSignBlock(Aylyth.id("entity/signs/hanging/ympe"), Aylyth.id("textures/gui/hanging_signs/ympe"), copyOf(Blocks.OAK_HANGING_SIGN)));
	Block YMPE_WALL_HANGING_SIGN = register("ympe_wall_hanging_sign", new TerraformWallHangingSignBlock(Aylyth.id("entity/signs/hanging/ympe"), Aylyth.id("textures/gui/hanging_signs/ympe"), copyOf(Blocks.OAK_HANGING_SIGN).dropsLike(YMPE_HANGING_SIGN)));
	Block YMPE_LEAVES = register("ympe_leaves", new LeavesBlock(copyOf(Blocks.OAK_LEAVES).mapColor(MapColor.ORANGE)));
	Block FRUIT_BEARING_YMPE_LOG = register("fruit_bearing_ympe_log", new GrowingHarvestablePillarBlock(Aylyth.id("harvest/fruit_bearing_ympe_log"), copyOf(AylythBlocks.YMPE_LOG)));

	Block POMEGRANATE_STRIPPED_LOG = register("stripped_pomegranate_log", new PillarBlock(copyOf(Blocks.STRIPPED_OAK_LOG)));
	Block POMEGRANATE_STRIPPED_WOOD = register("stripped_pomegranate_wood", new PillarBlock(copyOf(Blocks.STRIPPED_OAK_WOOD)));
	Block POMEGRANATE_LOG = register("pomegranate_log", new PillarBlock(copyOf(Blocks.OAK_LOG)));
	Block POMEGRANATE_WOOD = register("pomegranate_wood", new PillarBlock(copyOf(Blocks.OAK_WOOD)));
	Block POMEGRANATE_SAPLING = register("pomegranate_sapling", new SaplingBlock(simpleSaplingGenerator(AylythConfiguredFeatures.POMEGRANATE_TREE), copyOf(Blocks.OAK_SAPLING)));
	Block POMEGRANATE_POTTED_SAPLING = register("potted_pomegranate_sapling", new FlowerPotBlock(POMEGRANATE_SAPLING, copyOf(Blocks.POTTED_OAK_SAPLING)));
	Block POMEGRANATE_PLANKS = register("pomegranate_planks", new Block(copyOf(Blocks.OAK_PLANKS)));
	Block POMEGRANATE_STAIRS = register("pomegranate_stairs", new StairsBlock(POMEGRANATE_PLANKS.getDefaultState(), copyOf(Blocks.OAK_STAIRS)));
	Block POMEGRANATE_SLAB = register("pomegranate_slab", new SlabBlock(copyOf(Blocks.OAK_SLAB)));
	Block POMEGRANATE_FENCE = register("pomegranate_fence", new FenceBlock(copyOf(Blocks.OAK_FENCE)));
	Block POMEGRANATE_FENCE_GATE = register("pomegranate_fence_gate", new FenceGateBlock(copyOf(Blocks.OAK_FENCE_GATE), POMEGRANATE_WOOD_TYPE));
	Block POMEGRANATE_PRESSURE_PLATE = register("pomegranate_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, copyOf(Blocks.OAK_PRESSURE_PLATE), POMEGRANATE_BLOCK_SET_TYPE));
	Block POMEGRANATE_BUTTON = register("pomegranate_button", new ButtonBlock(copyOf(Blocks.OAK_BUTTON), POMEGRANATE_BLOCK_SET_TYPE, 30, true));
	Block POMEGRANATE_TRAPDOOR = register("pomegranate_trapdoor", new TrapdoorBlock(copyOf(Blocks.OAK_TRAPDOOR), POMEGRANATE_BLOCK_SET_TYPE));
	Block POMEGRANATE_DOOR = register("pomegranate_door", new DoorBlock(copyOf(Blocks.OAK_DOOR), POMEGRANATE_BLOCK_SET_TYPE));
	TerraformSignBlock POMEGRANATE_SIGN = register("pomegranate_sign", new TerraformSignBlock(Aylyth.id("entity/signs/pomegranate"), copyOf(Blocks.OAK_SIGN)));
	Block POMEGRANATE_WALL_SIGN = register("pomegranate_wall_sign", new TerraformWallSignBlock(Aylyth.id("entity/signs/pomegranate"), copyOf(Blocks.OAK_WALL_SIGN).dropsLike(POMEGRANATE_SIGN)));
	TerraformHangingSignBlock POMEGRANATE_HANGING_SIGN = register("pomegranate_hanging_sign", new TerraformHangingSignBlock(Aylyth.id("entity/signs/hanging/pomegranate"), Aylyth.id("textures/gui/hanging_signs/pomegranate"), copyOf(Blocks.OAK_HANGING_SIGN)));
	Block POMEGRANATE_WALL_HANGING_SIGN = register("pomegranate_wall_hanging_sign", new TerraformWallHangingSignBlock(Aylyth.id("entity/signs/hanging/pomegranate"), Aylyth.id("textures/gui/hanging_signs/pomegranate"), copyOf(Blocks.OAK_HANGING_SIGN).dropsLike(POMEGRANATE_HANGING_SIGN)));
	Block POMEGRANATE_LEAVES = register("pomegranate_leaves", new PomegranateLeavesBlock(copyOf(Blocks.OAK_LEAVES)));

	Block WRITHEWOOD_STRIPPED_LOG = register("stripped_writhewood_log", new PillarBlock(copyOf(Blocks.STRIPPED_OAK_LOG)));
	Block WRITHEWOOD_STRIPPED_WOOD = register("stripped_writhewood_wood", new PillarBlock(copyOf(Blocks.STRIPPED_OAK_WOOD)));
	Block WRITHEWOOD_LOG = register("writhewood_log", new PillarBlock(copyOf(Blocks.OAK_LOG)));
	Block WRITHEWOOD_WOOD = register("writhewood_wood", new PillarBlock(copyOf(Blocks.OAK_WOOD)));
	Block WRITHEWOOD_SAPLING = register("writhewood_sapling", new WaterloggableSaplingBlock(simpleSaplingGenerator(AylythConfiguredFeatures.WRITHEWOOD_TREE), copyOf(Blocks.OAK_SAPLING)));
	Block WRITHEWOOD_POTTED_SAPLING = register("potted_writhewood_sapling", new FlowerPotBlock(WRITHEWOOD_SAPLING, copyOf(Blocks.POTTED_OAK_SAPLING)));
	Block WRITHEWOOD_PLANKS = register("writhewood_planks", new Block(copyOf(Blocks.OAK_PLANKS)));
	Block WRITHEWOOD_STAIRS = register("writhewood_stairs", new StairsBlock(WRITHEWOOD_PLANKS.getDefaultState(), copyOf(Blocks.OAK_STAIRS)));
	Block WRITHEWOOD_SLAB = register("writhewood_slab", new SlabBlock(copyOf(Blocks.OAK_SLAB)));
	Block WRITHEWOOD_FENCE = register("writhewood_fence", new FenceBlock(copyOf(Blocks.OAK_FENCE)));
	Block WRITHEWOOD_FENCE_GATE = register("writhewood_fence_gate", new FenceGateBlock(copyOf(Blocks.OAK_FENCE_GATE), WRITHEWOOD_WOOD_TYPE));
	Block WRITHEWOOD_PRESSURE_PLATE = register("writhewood_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, copyOf(Blocks.OAK_PRESSURE_PLATE), WRITHEWOOD_BLOCK_SET_TYPE));
	Block WRITHEWOOD_BUTTON = register("writhewood_button", new ButtonBlock(copyOf(Blocks.OAK_BUTTON), WRITHEWOOD_BLOCK_SET_TYPE, 30, true));
	Block WRITHEWOOD_TRAPDOOR = register("writhewood_trapdoor", new TrapdoorBlock(copyOf(Blocks.OAK_TRAPDOOR), WRITHEWOOD_BLOCK_SET_TYPE));
	Block WRITHEWOOD_DOOR = register("writhewood_door", new DoorBlock(copyOf(Blocks.OAK_DOOR), WRITHEWOOD_BLOCK_SET_TYPE));
	TerraformSignBlock WRITHEWOOD_SIGN = register("writhewood_sign", new TerraformSignBlock(Aylyth.id("entity/signs/writhewood"), copyOf(Blocks.OAK_SIGN)));
	Block WRITHEWOOD_WALL_SIGN = register("writhewood_wall_sign", new TerraformWallSignBlock(Aylyth.id("entity/signs/writhewood"), copyOf(Blocks.OAK_WALL_SIGN).dropsLike(WRITHEWOOD_SIGN)));
	TerraformHangingSignBlock WRITHEWOOD_HANGING_SIGN = register("writhewood_hanging_sign", new TerraformHangingSignBlock(Aylyth.id("entity/signs/hanging/writhewood"), Aylyth.id("textures/gui/hanging_signs/writhewood"), copyOf(Blocks.OAK_HANGING_SIGN)));
	Block WRITHEWOOD_WALL_HANGING_SIGN = register("writhewood_wall_hanging_sign", new TerraformWallHangingSignBlock(Aylyth.id("entity/signs/hanging/writhewood"), Aylyth.id("textures/gui/hanging_signs/writhewood"), copyOf(Blocks.OAK_HANGING_SIGN).dropsLike(WRITHEWOOD_HANGING_SIGN)));
	Block WRITHEWOOD_LEAVES = register("writhewood_leaves", new LeavesBlock(copyOf(Blocks.OAK_LEAVES).mapColor(MapColor.ORANGE)));

	Block SEEPING_WOOD = register("seeping_wood", new PillarBlock(copyOf(Blocks.OAK_WOOD)));
	Block GIRASOL_SAPLING = register("girasol_sapling", new GirasolSaplingBlock(simpleSaplingGenerator(AylythConfiguredFeatures.GIRASOL_TREE), copyOf(Blocks.OAK_SAPLING)));
	Block GIRASOL_SAPLING_POTTED = register("potted_girasol_sapling", new FlowerPotBlock(GIRASOL_SAPLING, copyOf(Blocks.FLOWER_POT)));

	Block CHTHONIA_WOOD = register("chthonia_wood", new PillarBlock(copyOf(Blocks.OAK_WOOD).mapColor(MapColor.PALE_GREEN)));
	Block NEPHRITIC_CHTHONIA_WOOD = register("nephritic_chthonia_wood", new OneTimeHarvestablePillarBlock(Aylyth.id("harvest/nephritic_chthonia_wood"), state -> AylythBlocks.CHTHONIA_WOOD.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)), copyOf(Blocks.OAK_WOOD).mapColor(MapColor.PALE_GREEN)));

	Block AYLYTH_BUSH = register("aylyth_bush", new BushBlock());
	Block ANTLER_SHOOTS = register("antler_shoots", new AntlerShootsBlock());
	Block GRIPWEED = register("gripweed", new GripweedBlock());
	
	Block NYSIAN_GRAPE_VINE = register("nysian_grape_vine", new NysianGrapeVineBlock(copyOf(Blocks.VINE)));

	Block MARIGOLD = register("marigolds", new FlowerBlock(AylythStatusEffects.MORTECHIS, 9, copyOf(Blocks.DANDELION)));
	Block MARIGOLD_POTTED = register("potted_marigolds", new FlowerPotBlock(MARIGOLD, copyOf(Blocks.FLOWER_POT)));
	StagedMushroomPlantBlock JACK_O_LANTERN_MUSHROOM = register("jack_o_lantern_mushroom", new JackolanternMushroomBlock(Suppliers.memoize(() -> (SpreadingPlantBlock) Registries.BLOCK.get(Aylyth.id("shelf_jack_o_lantern_mushroom"))), FabricBlockSettings.create().notSolid().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.WART_BLOCK).noCollision().nonOpaque().ticksRandomly().luminance(state -> state.get(JackolanternShelfMushroomBlock.GLOWING) ? state.get(StagedMushroomPlantBlock.STAGE)+4 : 0)));
	ShelfMushroomBlock SHELF_JACK_O_LANTERN_MUSHROOM = register("shelf_jack_o_lantern_mushroom", new JackolanternShelfMushroomBlock(() -> JACK_O_LANTERN_MUSHROOM, FabricBlockSettings.create().notSolid().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.WART_BLOCK).noCollision().nonOpaque().ticksRandomly().luminance(state -> state.get(JackolanternShelfMushroomBlock.GLOWING) ? 5 : 0)));
	Block GHOSTCAP_MUSHROOM = register("ghostcap_mushroom", new SpreadingPlantBlock(FabricBlockSettings.create().notSolid().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.GLOW_LICHEN).noCollision().nonOpaque().ticksRandomly()));

	Block JACK_O_LANTERN_MUSHROOM_STEM = register("jack_o_lantern_mushroom_stem", new MushroomBlock(copyOf(Blocks.MUSHROOM_STEM).mapColor(MapColor.TERRACOTTA_ORANGE)));
	Block JACK_O_LANTERN_MUSHROOM_BLOCK = register("jack_o_lantern_mushroom_block", new MushroomBlock(copyOf(Blocks.BROWN_MUSHROOM_BLOCK).mapColor(state -> state.get(MushroomBlock.UP) ? MapColor.EMERALD_GREEN : MapColor.TERRACOTTA_ORANGE).luminance(15)));

	Block OAK_STREWN_LEAVES = register("oak_strewn_leaves", new StrewnLeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).notSolid().pistonBehavior(PistonBehavior.DESTROY).replaceable().sounds(AylythSoundEvents.STREWN_LEAVES)));
	Block YMPE_STREWN_LEAVES = register("ympe_strewn_leaves", new StrewnLeavesBlock(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).notSolid().pistonBehavior(PistonBehavior.DESTROY).replaceable().sounds(AylythSoundEvents.STREWN_LEAVES)));

	Block SMALL_WOODY_GROWTH = register("small_woody_growth", new SmallWoodyGrowthBlock(FabricBlockSettings.create().burnable().strength(2.0f).sounds(BlockSoundGroup.WOOD)));
	Block LARGE_WOODY_GROWTH = register("large_woody_growth", new LargeWoodyGrowthBlock(FabricBlockSettings.create().burnable().strength(2.0f).sounds(BlockSoundGroup.WOOD)));
	Block WOODY_GROWTH_CACHE = register("woody_growth_cache", new WoodyGrowthCacheBlock(FabricBlockSettings.create().burnable().strength(2.0f).sounds(BlockSoundGroup.WOOD)));

	Block OAK_SEEP = register("oak_seep", new SeepBlock(FabricBlockSettings.create().mapColor(MapColor.OAK_TAN).burnable().strength(2).sounds(BlockSoundGroup.WOOD)));
	Block SPRUCE_SEEP = register("spruce_seep", new SeepBlock(FabricBlockSettings.create().mapColor(MapColor.SPRUCE_BROWN).burnable().strength(2).sounds(BlockSoundGroup.WOOD)));
	Block DARK_OAK_SEEP = register("dark_oak_seep", new SeepBlock(FabricBlockSettings.create().mapColor(MapColor.BROWN).burnable().strength(2).sounds(BlockSoundGroup.WOOD)));
	Block YMPE_SEEP = register("ympe_seep", new SeepBlock(FabricBlockSettings.create().mapColor(MapColor.OAK_TAN).burnable().strength(2).sounds(BlockSoundGroup.WOOD))); // TODO: Change to the map color of ympe, same with seeping wood
	Block SEEPING_WOOD_SEEP = register("seeping_wood_seep", new SeepBlock(FabricBlockSettings.create().mapColor(MapColor.OAK_TAN).burnable().strength(2).sounds(BlockSoundGroup.WOOD)));

	Block DARK_WOODS_TILES = register("dark_woods_tiles", new Block(copyOf(Blocks.DARK_OAK_PLANKS)));
	Block ESSTLINE_BLOCK = register("esstline_block", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.BLACK)));
	Block NEPHRITE_BLOCK = register("nephrite_block", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));

	Block CARVED_SMOOTH_NEPHRITE = register("carved_smooth_nephrite", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));
	Block CARVED_ANTLERED_NEPHRITE = register("carved_antlered_nephrite", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));
	Block CARVED_NEPHRITE_PILLAR = register("carved_nephrite_pillar", new PillarBlock(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));
	Block CARVED_NEPHRITE_TILES = register("carved_nephrite_tiles", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));
	Block CARVED_WOODY_NEPHRITE = register("carved_woody_nephrite", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));

	Block SOUL_HEARTH = register("soul_hearth", new SoulHearthBlock(copyOf(Blocks.DEEPSLATE)));
	Block VITAL_THURIBLE = register("vital_thurible", new VitalThuribleBlock(copyOf(Blocks.DEEPSLATE)));
	Block BLACK_WELL = register("black_well", new BlackWellBlock(copyOf(Blocks.DEEPSLATE)));

	private static <B extends Block> B register(String name, B block) {
		return Registry.register(Registries.BLOCK, Aylyth.id(name), block);
	}

	// TODO move to separate classes

	private static SaplingGenerator simpleSaplingGenerator(RegistryKey<ConfiguredFeature<?, ?>> tree) {
		return new SaplingGenerator() {
			@Override
			protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
				return tree;
			}
		};
	}

	private static LargeTreeSaplingGenerator simpleLargeSaplingGenerator(RegistryKey<ConfiguredFeature<?, ?>> tree, RegistryKey<ConfiguredFeature<?, ?>> largeTree) {
		return new LargeTreeSaplingGenerator() {
			@Override
			protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
				return tree;
			}

			@Override
			protected RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
				return largeTree;
			}
		};
	}

	// Load static initializer
	static void register() {}
}
