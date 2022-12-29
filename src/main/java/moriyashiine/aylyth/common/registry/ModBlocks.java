package moriyashiine.aylyth.common.registry;

import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.*;
import moriyashiine.aylyth.common.block.util.BetterLargeSaplingGenerator;
import moriyashiine.aylyth.common.block.util.BetterSaplingGenerator;
import moriyashiine.aylyth.common.block.util.ModSaplingBlock;
import moriyashiine.aylyth.common.registry.util.WoodSuite;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.datagen.worldgen.features.ModConfiguredFeatures;
import moriyashiine.aylyth.mixin.BlocksAccessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;

public class ModBlocks {
	public static final Material STREWN_LEAVES = new Material.Builder(MapColor.DARK_GREEN).notSolid().allowsMovement().destroyedByPiston().replaceable().build();

	public static final WoodSuite YMPE_BLOCKS = WoodSuite.of(new Identifier(Aylyth.MOD_ID, "ympe"), WoodSuite.CopySettingsSet.DEFAULT_SETTINGS_SET, Registry.BLOCK, MapColor.BROWN, new BetterLargeSaplingGenerator() {

		@Override
		protected @Nullable RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeKey(Random random) {
			return RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AylythUtil.id("big_ympe_tree"));
		}

		@Override
		protected @Nullable RegistryKey<ConfiguredFeature<?, ?>> getTreeKey(Random random, boolean bees) {
			return RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AylythUtil.id("ympe_tree"));
		}
	});
	public static final Block FRUIT_BEARING_YMPE_LOG = new FruitBearingYmpeLogBlock();
	public static final Block YMPE_LEAVES = BlocksAccessor.callCreateLeavesBlock(BlockSoundGroup.GRASS);

	public static final WoodSuite POMEGRANATE_BLOCKS = WoodSuite.of(new Identifier(Aylyth.MOD_ID, "pomegranate"), WoodSuite.CopySettingsSet.DEFAULT_SETTINGS_SET, Registry.BLOCK, MapColor.DARK_RED, new BetterSaplingGenerator() {

		@Nullable
		@Override
		protected RegistryKey<ConfiguredFeature<?, ?>> getTreeKey(Random random, boolean bees) {
			return RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AylythUtil.id("pomegranate_tree"));
		}
	});
	public static final Block POMEGRANATE_LEAVES = new PomegranateLeavesBlock(copyOf(Blocks.OAK_LEAVES));

	// This is ew ew. Make a builder for making specific optional changes
	public static final WoodSuite WRITHEWOOD_BLOCKS = WoodSuite.of(new Identifier(Aylyth.MOD_ID, "writhewood"), WoodSuite.CopySettingsSet.DEFAULT_SETTINGS_SET, Registry.BLOCK, MapColor.OAK_TAN, null, null, null, null, null, new WaterloggableSaplingBlock(new BetterSaplingGenerator() {

		@Nullable
		@Override
		protected RegistryKey<ConfiguredFeature<?, ?>> getTreeKey(Random random, boolean bees) {
			return RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AylythUtil.id("writhewood_tree"));
		}
	}, copyOf(Blocks.OAK_SAPLING)), null, null, null, null, null, null, null, null, null, null, null, null);
	public static final Block WRITHEWOOD_LEAVES = BlocksAccessor.callCreateLeavesBlock(BlockSoundGroup.GRASS);

	public static final Block SEEPING_WOOD = new PillarBlock(copyOf(Blocks.OAK_WOOD));
	public static final Block GIRASOL_SAPLING = new ModSaplingBlock(new BetterLargeSaplingGenerator() {
		@Override
		protected @Nullable RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeKey(Random random) {
			return RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, AylythUtil.id("seeping_tree"));
		}

		@Override
		protected @Nullable RegistryKey<ConfiguredFeature<?, ?>> getTreeKey(Random random, boolean bees) {
			return null;
		}
	});
	public static final Block GIRASOL_SAPLING_POTTED = new FlowerPotBlock(GIRASOL_SAPLING, copyOf(Blocks.FLOWER_POT));

	public static final Block AYLYTH_BUSH = new BushBlock();
	public static final Block ANTLER_SHOOTS = new AntlerShootsBlock();
	public static final Block GRIPWEED = new GripweedBlock();
	
	public static final Block NYSIAN_GRAPE_VINE = new NysianGrapeVineBlock(copyOf(Blocks.VINE));

	public static final Block MARIGOLD = new FlowerBlock(ModPotions.MORTECHIS_EFFECT, 9, copyOf(Blocks.DANDELION));
	public static final Block MARIGOLD_POTTED = new FlowerPotBlock(MARIGOLD, copyOf(Blocks.FLOWER_POT));
	public static final Block JACK_O_LANTERN_MUSHROOM = new JackolanternMushroomBlock(ModBlocks::wallSupplier, FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.WART_BLOCK).noCollision().nonOpaque().ticksRandomly().luminance(state -> state.get(JackolanternShelfMushroomBlock.GLOWING) ? state.get(StagedMushroomPlantBlock.STAGE)+2 : 0));
	public static final Block SHELF_JACK_O_LANTERN_MUSHROOM = new JackolanternShelfMushroomBlock(() -> JACK_O_LANTERN_MUSHROOM, FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.WART_BLOCK).noCollision().nonOpaque().ticksRandomly().luminance(state -> state.get(JackolanternShelfMushroomBlock.GLOWING) ? 3 : 0));
	public static final Block GHOSTCAP_MUSHROOM = new SpreadingPlantBlock(FabricBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GLOW_LICHEN).noCollision().nonOpaque().ticksRandomly());

	public static final Block OAK_STREWN_LEAVES = new StrewnLeavesBlock(FabricBlockSettings.of(STREWN_LEAVES).sounds(ModSoundEvents.STREWN_LEAVES));
	public static final Block YMPE_STREWN_LEAVES = new StrewnLeavesBlock(FabricBlockSettings.of(STREWN_LEAVES).sounds(ModSoundEvents.STREWN_LEAVES));

	public static final Block SMALL_WOODY_GROWTH = new SmallWoodyGrowthBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD));
	public static final Block LARGE_WOODY_GROWTH = new LargeWoodyGrowthBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD));
	public static final Block WOODY_GROWTH_CACHE = new WoodyGrowthCacheBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD));

	public static final Block OAK_SEEP = new SeepBlock();
	public static final Block SPRUCE_SEEP = new SeepBlock();
	public static final Block DARK_OAK_SEEP = new SeepBlock();
	public static final Block YMPE_SEEP = new SeepBlock();
	public static final Block SEEPING_WOOD_SEEP = new SeepBlock();

	public static final Block DARK_WOODS_TILES = new Block(copyOf(Blocks.DARK_OAK_PLANKS));

	public static final Block SOUL_HEARTH = new SoulHearthBlock(copyOf(Blocks.DEEPSLATE));
	public static final Block VITAL_THURIBLE = new VitalThuribleBlock(copyOf(Blocks.DEEPSLATE));

	public static void init() {
		YMPE_BLOCKS.register();
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "fruit_bearing_ympe_log"), FRUIT_BEARING_YMPE_LOG);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_leaves"), YMPE_LEAVES);
		POMEGRANATE_BLOCKS.register();
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "pomegranate_leaves"), POMEGRANATE_LEAVES);
		WRITHEWOOD_BLOCKS.register();
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "writhewood_leaves"), WRITHEWOOD_LEAVES);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "seeping_log"), SEEPING_WOOD);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "girasol_sapling"), GIRASOL_SAPLING);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "potted_girasol_sapling"), GIRASOL_SAPLING_POTTED);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "aylyth_bush"), AYLYTH_BUSH);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "antler_shoots"), ANTLER_SHOOTS);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "gripweed"), GRIPWEED);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "nysian_grape_vine"), NYSIAN_GRAPE_VINE);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "marigolds"), MARIGOLD);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "potted_marigolds"), MARIGOLD_POTTED);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "jack_o_lantern_mushroom"), JACK_O_LANTERN_MUSHROOM);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "shelf_jack_o_lantern_mushroom"), SHELF_JACK_O_LANTERN_MUSHROOM);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ghostcap_mushroom"), GHOSTCAP_MUSHROOM);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "oak_strewn_leaves"), OAK_STREWN_LEAVES);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_strewn_leaves"), YMPE_STREWN_LEAVES);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "small_woody_growth"), SMALL_WOODY_GROWTH);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "large_woody_growth"), LARGE_WOODY_GROWTH);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "woody_growth_cache"), WOODY_GROWTH_CACHE);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "oak_seep"), OAK_SEEP);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "spruce_seep"), SPRUCE_SEEP);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "dark_oak_seep"), DARK_OAK_SEEP);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_seep"), YMPE_SEEP);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "seeping_wood_seep"), SEEPING_WOOD_SEEP);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "dark_woods_tiles"), DARK_WOODS_TILES);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "soul_hearth"), SOUL_HEARTH);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "vital_thurible"), VITAL_THURIBLE);

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
