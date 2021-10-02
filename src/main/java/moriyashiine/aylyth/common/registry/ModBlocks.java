package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.ModSaplingBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ModBlocks {
	public static Block TEST_SAPLING = new ModSaplingBlock(new SaplingGenerator() {
		@Nullable
		@Override
		protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
			return ModWorldGenerators.BIG_YMPE_TREE;
		}
	});

	public static void init() {
		Registry.register(Registry.BLOCK, new Identifier(Aylyth.MOD_ID, "test_sapling"), TEST_SAPLING);
		Registry.register(Registry.ITEM, new Identifier(Aylyth.MOD_ID, "test_sapling"), new BlockItem(TEST_SAPLING, new FabricItemSettings()));
	}
}
