package moriyashiine.aylyth.common.registry;

import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.terraformersmc.terraform.wood.block.*;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.*;
import moriyashiine.aylyth.common.block.util.ModSaplingBlock;
import moriyashiine.aylyth.common.registry.util.WoodSuite;
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
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;

public class ModBlocks {

	public static final Material STREWN_LEAVES = new Material.Builder(MapColor.DARK_GREEN).notSolid().allowsMovement().destroyedByPiston().replaceable().build();

	public static final Block STRIPPED_YMPE_LOG = new PillarBlock(copyOf(Blocks.OAK_LOG));
	public static final Block STRIPPED_YMPE_WOOD = new PillarBlock(copyOf(STRIPPED_YMPE_LOG));
	public static final Block YMPE_LOG = new StrippableLogBlock(() -> STRIPPED_YMPE_LOG, MapColor.BROWN, copyOf(STRIPPED_YMPE_LOG));
	public static final Block FRUIT_BEARING_YMPE_LOG = new FruitBearingYmpeLogBlock();
	public static final Block YMPE_WOOD = new StrippableLogBlock(() -> STRIPPED_YMPE_WOOD, MapColor.BROWN, copyOf(STRIPPED_YMPE_LOG));
	public static final Block YMPE_LEAVES = BlocksAccessor.callCreateLeavesBlock(BlockSoundGroup.GRASS);
	public static final Block YMPE_SAPLING = new ModSaplingBlock(new LargeTreeSaplingGenerator() {
		@Nullable
		@Override
		protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
			return ModConfiguredFeatures.BIG_YMPE_TREE;
		}

		@Nullable
		@Override
		protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
			return ModConfiguredFeatures.YMPE_TREE;
		}
	});
	public static final Block POTTED_YMPE_SAPLING = new FlowerPotBlock(YMPE_SAPLING, copyOf(Blocks.POTTED_OAK_SAPLING));
	public static final Block YMPE_PLANKS = new Block(copyOf(Blocks.OAK_PLANKS));
	public static final Block YMPE_STAIRS = new StairsBlock(YMPE_PLANKS.getDefaultState(), copyOf(Blocks.OAK_STAIRS));
	public static final Block YMPE_SLAB = new SlabBlock(copyOf(Blocks.OAK_SLAB));
	public static final Block YMPE_FENCE = new FenceBlock(copyOf(Blocks.OAK_FENCE));
	public static final Block YMPE_FENCE_GATE = new FenceGateBlock(copyOf(Blocks.OAK_FENCE_GATE));
	public static final Block YMPE_PRESSURE_PLATE = new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, copyOf(Blocks.OAK_PRESSURE_PLATE));
	public static final Block YMPE_BUTTON = new WoodenButtonBlock(copyOf(Blocks.OAK_BUTTON));
	public static final Block YMPE_TRAPDOOR = new TrapdoorBlock(copyOf(Blocks.OAK_TRAPDOOR));
	public static final Block YMPE_DOOR = new DoorBlock(copyOf(Blocks.OAK_DOOR));
	public static final TerraformSignBlock YMPE_SIGN = new TerraformSignBlock(new Identifier(Aylyth.MOD_ID, "entity/sign/ympe"), copyOf(Blocks.OAK_SIGN));
	public static final Block YMPE_WALL_SIGN = new TerraformWallSignBlock(YMPE_SIGN.getTexture(), copyOf(Blocks.OAK_WALL_SIGN));

	public static final WoodSuite POMEGRANATE_BLOCKS = WoodSuite.of(new Identifier(Aylyth.MOD_ID, "pomegranate"), WoodSuite.CopySettingsSet.DEFAULT_SETTINGS_SET, Registry.BLOCK, MapColor.DARK_RED, BlockSoundGroup.GRASS, new SaplingGenerator() {
		@Nullable
		@Override
		protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
			return null; //TODO: fix
		}
	});
	public static final Block POMEGRANATE_LEAVES = new PomegranateLeavesBlock(copyOf(Blocks.OAK_LEAVES));
	
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

	public static final Block OAK_SEEP = new SeepBlock();
	public static final Block SPRUCE_SEEP = new SeepBlock();
	public static final Block DARK_OAK_SEEP = new SeepBlock();
	public static final Block YMPE_SEEP = new SeepBlock();
	
	public static void init() {
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "stripped_ympe_log"), STRIPPED_YMPE_LOG);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "stripped_ympe_wood"), STRIPPED_YMPE_WOOD);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_log"), YMPE_LOG);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "fruit_bearing_ympe_log"), FRUIT_BEARING_YMPE_LOG);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_wood"), YMPE_WOOD);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_leaves"), YMPE_LEAVES);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_sapling"), YMPE_SAPLING);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "potted_ympe_sapling"), POTTED_YMPE_SAPLING);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_planks"), YMPE_PLANKS);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_stairs"), YMPE_STAIRS);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_slab"), YMPE_SLAB);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_fence"), YMPE_FENCE);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_fence_gate"), YMPE_FENCE_GATE);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_pressure_plate"), YMPE_PRESSURE_PLATE);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_button"), YMPE_BUTTON);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_trapdoor"), YMPE_TRAPDOOR);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_door"), YMPE_DOOR);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_sign"), YMPE_SIGN);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_wall_sign"), YMPE_WALL_SIGN);
		POMEGRANATE_BLOCKS.register();
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "pomegranate_leaves"), POMEGRANATE_LEAVES);
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
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "oak_seep"), OAK_SEEP);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "spruce_seep"), SPRUCE_SEEP);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "dark_oak_seep"), DARK_OAK_SEEP);
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_seep"), YMPE_SEEP);
		FlammableBlockRegistry flammableRegistry = FlammableBlockRegistry.getDefaultInstance();
		flammableRegistry.add(STRIPPED_YMPE_LOG, 5, 5);
		flammableRegistry.add(STRIPPED_YMPE_WOOD, 5, 5);
		flammableRegistry.add(YMPE_LOG, 5, 5);
		flammableRegistry.add(YMPE_WOOD, 5, 5);
		flammableRegistry.add(YMPE_LEAVES, 30, 60);
		flammableRegistry.add(YMPE_PLANKS, 5, 20);
		flammableRegistry.add(YMPE_STAIRS, 5, 20);
		flammableRegistry.add(YMPE_SLAB, 5, 20);
		flammableRegistry.add(YMPE_FENCE, 5, 20);
		flammableRegistry.add(YMPE_FENCE_GATE, 5, 20);
		flammableRegistry.add(AYLYTH_BUSH, 60, 100);
	}

	private static SpreadingPlantBlock wallSupplier() {
		return (SpreadingPlantBlock) SHELF_JACK_O_LANTERN_MUSHROOM;
	}
}
