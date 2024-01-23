package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.*;
import moriyashiine.aylyth.common.registry.util.WoodSuite;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.mixin.BlocksAccessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;

public class ModBlocks {
	private static final BlockSetType YMPE_BLOCK_SET_TYPE = new BlockSetTypeBuilder().register(AylythUtil.id("ympe"));
	private static final BlockSetType POMEGRANATE_BLOCK_SET_TYPE = new BlockSetTypeBuilder().register(AylythUtil.id("pomegranate"));
	private static final BlockSetType WRITHEWOOD_BLOCK_SET_TYPE = new BlockSetTypeBuilder().register(AylythUtil.id("writhewood"));
	private static final WoodType YMPE_WOOD_TYPE = new WoodTypeBuilder().register(AylythUtil.id("ympe"), YMPE_BLOCK_SET_TYPE);
	private static final WoodType POMEGRANATE_WOOD_TYPE = new WoodTypeBuilder().register(AylythUtil.id("pomegranate"), POMEGRANATE_BLOCK_SET_TYPE);
	private static final WoodType WRITHEWOOD_WOOD_TYPE = new WoodTypeBuilder().register(AylythUtil.id("writhewood"), WRITHEWOOD_BLOCK_SET_TYPE);

	public static final FabricBlockSettings STREWN_LEAVES = FabricBlockSettings.create().mapColor(MapColor.DARK_GREEN).notSolid().pistonBehavior(PistonBehavior.DESTROY).replaceable();

	public static final WoodSuite YMPE_BLOCKS = WoodSuite.of(new Identifier(Aylyth.MOD_ID, "ympe"), WoodSuite.CopySettingsSet.DEFAULT_SETTINGS_SET, Registries.BLOCK, MapColor.BROWN, new LargeTreeSaplingGenerator() {

		@Override
		protected @Nullable RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
			return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, AylythUtil.id("big_ympe_tree"));
		}

		@Override
		protected @Nullable RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
			return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, AylythUtil.id("ympe_tree"));
		}
	}, YMPE_WOOD_TYPE).register();
	public static final Block FRUIT_BEARING_YMPE_LOG = register("fruit_bearing_ympe_log", new FruitBearingYmpeLogBlock());
	public static final Block YMPE_LEAVES = register("ympe_leaves", BlocksAccessor.callCreateLeavesBlock(BlockSoundGroup.GRASS));

	public static final WoodSuite POMEGRANATE_BLOCKS = WoodSuite.of(new Identifier(Aylyth.MOD_ID, "pomegranate"), WoodSuite.CopySettingsSet.DEFAULT_SETTINGS_SET, Registries.BLOCK, MapColor.DARK_RED, new SaplingGenerator() {

		@Nullable
		@Override
		protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
			return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, AylythUtil.id("pomegranate_tree"));
		}
	}, POMEGRANATE_WOOD_TYPE).register();
	public static final Block POMEGRANATE_LEAVES = register("pomegranate_leaves", new PomegranateLeavesBlock(copyOf(Blocks.OAK_LEAVES)));

	// This is ew ew. Make a builder for making specific optional changes
	public static final WoodSuite WRITHEWOOD_BLOCKS = WoodSuite.of(new Identifier(Aylyth.MOD_ID, "writhewood"), WoodSuite.CopySettingsSet.DEFAULT_SETTINGS_SET, Registries.BLOCK, MapColor.OAK_TAN, null, null, null, null, null, new WaterloggableSaplingBlock(new SaplingGenerator() {

		@Nullable
		@Override
		protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
			return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, AylythUtil.id("writhewood_tree"));
		}
	}, copyOf(Blocks.OAK_SAPLING)), null, null, null, null, null, null, null, null, null, null, null, null, WRITHEWOOD_WOOD_TYPE).register();
	public static final Block WRITHEWOOD_LEAVES = register("writhewood_leaves", BlocksAccessor.callCreateLeavesBlock(BlockSoundGroup.GRASS));

	public static final Block SEEPING_WOOD = register("seeping_wood", new PillarBlock(copyOf(Blocks.OAK_WOOD)));
	public static final Block GIRASOL_SAPLING = register("girasol_sapling", new GirasolSaplingBlock(new SaplingGenerator() {
		@Nullable
		@Override
		protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
			return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, AylythUtil.id("seeping_tree"));
		}
	}));
	public static final Block GIRASOL_SAPLING_POTTED = register("potted_girasol_sapling", new FlowerPotBlock(GIRASOL_SAPLING, copyOf(Blocks.FLOWER_POT)));

	public static final Block AYLYTH_BUSH = register("aylyth_bush", new BushBlock());
	public static final Block ANTLER_SHOOTS = register("antler_shoots", new AntlerShootsBlock());
	public static final Block GRIPWEED = register("gripweed", new GripweedBlock());
	
	public static final Block NYSIAN_GRAPE_VINE = register("nysian_grape_vine", new NysianGrapeVineBlock(copyOf(Blocks.VINE)));

	public static final Block MARIGOLD = register("marigolds", new FlowerBlock(ModPotions.MORTECHIS_EFFECT, 9, copyOf(Blocks.DANDELION)));
	public static final Block MARIGOLD_POTTED = register("potted_marigolds", new FlowerPotBlock(MARIGOLD, copyOf(Blocks.FLOWER_POT)));
	public static final Block JACK_O_LANTERN_MUSHROOM = register("jack_o_lantern_mushroom", new JackolanternMushroomBlock(ModBlocks::wallSupplier, FabricBlockSettings.create().notSolid().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.WART_BLOCK).noCollision().nonOpaque().ticksRandomly().luminance(state -> state.get(JackolanternShelfMushroomBlock.GLOWING) ? state.get(StagedMushroomPlantBlock.STAGE)+2 : 0)));
	public static final Block SHELF_JACK_O_LANTERN_MUSHROOM = register("shelf_jack_o_lantern_mushroom", new JackolanternShelfMushroomBlock(() -> JACK_O_LANTERN_MUSHROOM, FabricBlockSettings.create().notSolid().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.WART_BLOCK).noCollision().nonOpaque().ticksRandomly().luminance(state -> state.get(JackolanternShelfMushroomBlock.GLOWING) ? 3 : 0)));
	public static final Block GHOSTCAP_MUSHROOM = register("ghostcap_mushroom", new SpreadingPlantBlock(FabricBlockSettings.create().notSolid().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.GLOW_LICHEN).noCollision().nonOpaque().ticksRandomly()));

	public static final Block OAK_STREWN_LEAVES = register("oak_strewn_leaves", new StrewnLeavesBlock(FabricBlockSettings.copyOf(STREWN_LEAVES).sounds(ModSoundEvents.STREWN_LEAVES)));
	public static final Block YMPE_STREWN_LEAVES = register("ympe_strewn_leaves", new StrewnLeavesBlock(FabricBlockSettings.copyOf(STREWN_LEAVES).sounds(ModSoundEvents.STREWN_LEAVES)));

	public static final Block SMALL_WOODY_GROWTH = register("small_woody_growth", new SmallWoodyGrowthBlock(FabricBlockSettings.create().burnable().strength(2.0f).sounds(BlockSoundGroup.WOOD)));
	public static final Block LARGE_WOODY_GROWTH = register("large_woody_growth", new LargeWoodyGrowthBlock(FabricBlockSettings.create().burnable().strength(2.0f).sounds(BlockSoundGroup.WOOD)));
	public static final Block WOODY_GROWTH_CACHE = register("woody_growth_cache", new WoodyGrowthCacheBlock(FabricBlockSettings.create().burnable().strength(2.0f).sounds(BlockSoundGroup.WOOD)));

	public static final Block OAK_SEEP = register("oak_seep", new SeepBlock());
	public static final Block SPRUCE_SEEP = register("spruce_seep", new SeepBlock());
	public static final Block DARK_OAK_SEEP = register("dark_oak_seep", new SeepBlock());
	public static final Block YMPE_SEEP = register("ympe_seep", new SeepBlock());
	public static final Block SEEPING_WOOD_SEEP = register("seeping_wood_seep", new SeepBlock());

	public static final Block DARK_WOODS_TILES = register("dark_woods_tiles", new Block(copyOf(Blocks.DARK_OAK_PLANKS)));
	public static final Block ESSTLINE_BLOCK = register("esstline_block", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.BLACK)));
	public static final Block NEPHRITE_BLOCK = register("nephrite_block", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));

	public static final Block CARVED_SMOOTH_NEPHRITE = register("carved_smooth_nephrite", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));
	public static final Block CARVED_ANTLERED_NEPHRITE = register("carved_antlered_nephrite", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));
	public static final Block CARVED_NEPHRITE_PILLAR = register("carved_nephrite_pillar", new PillarBlock(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));
	public static final Block POLISHED_CARVED_NEPHRITE = register("polished_carved_nephrite", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));
	public static final Block CARVED_NEPHRITE_TILES = register("carved_nephrite_tiles", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));
	public static final Block CARVED_WOODY_NEPHRITE = register("carved_woody_nephrite", new Block(copyOf(Blocks.DIAMOND_BLOCK).mapColor(MapColor.PALE_GREEN)));

	public static final Block SOUL_HEARTH = register("soul_hearth", new SoulHearthBlock(copyOf(Blocks.DEEPSLATE)));
	public static final Block VITAL_THURIBLE = register("vital_thurible", new VitalThuribleBlock(copyOf(Blocks.DEEPSLATE)));

	private static <T extends Block> T register(String name, T item) {
		Registry.register(Registries.BLOCK, AylythUtil.id(name), item);
		return item;
	}

	public static void init() {
		FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableRegistry.add(YMPE_BLOCKS.strippedLog, 5, 5);
		flammableRegistry.add(YMPE_BLOCKS.strippedWood, 5, 5);
		flammableRegistry.add(YMPE_BLOCKS.log, 5, 5);
		flammableRegistry.add(YMPE_BLOCKS.wood, 5, 5);
		flammableRegistry.add(YMPE_BLOCKS.planks, 5, 20);
		flammableRegistry.add(YMPE_BLOCKS.stairs, 5, 20);
		flammableRegistry.add(YMPE_BLOCKS.slab, 5, 20);
		flammableRegistry.add(YMPE_BLOCKS.fence, 5, 20);
		flammableRegistry.add(YMPE_BLOCKS.fenceGate, 5, 20);
		flammableRegistry.add(YMPE_LEAVES, 30, 60);
		flammableRegistry.add(POMEGRANATE_BLOCKS.strippedLog, 5, 5);
		flammableRegistry.add(POMEGRANATE_BLOCKS.strippedWood, 5, 5);
		flammableRegistry.add(POMEGRANATE_BLOCKS.log, 5, 5);
		flammableRegistry.add(POMEGRANATE_BLOCKS.wood, 5, 5);
		flammableRegistry.add(POMEGRANATE_BLOCKS.planks, 5, 20);
		flammableRegistry.add(POMEGRANATE_BLOCKS.stairs, 5, 20);
		flammableRegistry.add(POMEGRANATE_BLOCKS.slab, 5, 20);
		flammableRegistry.add(POMEGRANATE_BLOCKS.fence, 5, 20);
		flammableRegistry.add(POMEGRANATE_BLOCKS.fenceGate, 5, 20);
		flammableRegistry.add(POMEGRANATE_LEAVES, 30, 60);
		flammableRegistry.add(WRITHEWOOD_BLOCKS.strippedLog, 5, 5);
		flammableRegistry.add(WRITHEWOOD_BLOCKS.strippedWood, 5, 5);
		flammableRegistry.add(WRITHEWOOD_BLOCKS.log, 5, 5);
		flammableRegistry.add(WRITHEWOOD_BLOCKS.wood, 5, 5);
		flammableRegistry.add(WRITHEWOOD_BLOCKS.planks, 5, 20);
		flammableRegistry.add(WRITHEWOOD_BLOCKS.stairs, 5, 20);
		flammableRegistry.add(WRITHEWOOD_BLOCKS.slab, 5, 20);
		flammableRegistry.add(WRITHEWOOD_BLOCKS.fence, 5, 20);
		flammableRegistry.add(WRITHEWOOD_BLOCKS.fenceGate, 5, 20);
		flammableRegistry.add(WRITHEWOOD_LEAVES, 30, 60);
		flammableRegistry.add(SEEPING_WOOD, 5, 5);
		flammableRegistry.add(AYLYTH_BUSH, 60, 100);
	}

	private static SpreadingPlantBlock wallSupplier() {
		return (SpreadingPlantBlock) SHELF_JACK_O_LANTERN_MUSHROOM;
	}
}
