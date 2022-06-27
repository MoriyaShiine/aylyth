package moriyashiine.aylyth.common.world.generator;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.registry.ModWorldGenerators;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class AylthianTrunkPlacer extends GiantTrunkPlacer {
	public static final Codec<AylthianTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> fillTrunkPlacerFields(instance).apply(instance, AylthianTrunkPlacer::new));
	
	public AylthianTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
		super(baseHeight, firstRandomHeight, secondRandomHeight);
	}
	
	public AylthianTrunkPlacer() {
		this(12, 2, 5);
	}
	
	@Override
	protected TrunkPlacerType<AylthianTrunkPlacer> getType() {
		return ModWorldGenerators.AYLYTHIAN_TRUNK_PLACER;
	}
	
	@Override
	public List<FoliagePlacer.TreeNode> generate(TreeDecorator.Generator generator) {
		List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
		int height = getHeight(generator.getRandom());
		list.addAll(super.generate(generator.getWorld(), generator::replace, generator.getRandom(), height, generator.getLogPositions().get(0), config));
		for (int y = height; y > height / 3; y--) {
			int branchLength = y >= height - height / 4F ? (baseHeight > 12 ? 8 : 7) : 6;
			while (generator.getRandom().nextInt(3) == 0) {
				float radianAngle = generator.getRandom().nextFloat() * 6.2831855F;
				int x = 0;
				int z = 0;
				
				for (int l = 0; l < branchLength; ++l) {
					x = (int) (1.5F + MathHelper.cos(radianAngle) * (float) l);
					z = (int) (1.5F + MathHelper.sin(radianAngle) * (float) l);
					BlockPos blockPos = generator.getLogPositions().get(0).add(x, y - 3 + l / 2, z);
					getAndSetState(generator.getWorld(), generator::replace, generator.getRandom(), blockPos, config);
				}
				
				list.add(new FoliagePlacer.TreeNode(generator.getLogPositions().get(0).add(x, y + 1, z), 0, false));
				
			}
		}
		
		return list;
	}
}
