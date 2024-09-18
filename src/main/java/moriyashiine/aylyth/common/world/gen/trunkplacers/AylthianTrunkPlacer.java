package moriyashiine.aylyth.common.world.gen.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.world.gen.AylythTrunkPlacerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.List;
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
		return AylythTrunkPlacerTypes.AYLYTHIAN;
	}

	@Override
	public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
		List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
		list.addAll(super.generate(world, replacer, random, height, startPos, config));
		for (int y = height; y > height / 3; y--) {
			int branchLength = y >= height - height / 4F ? (baseHeight > 12 ? 8 : 7) : 6;
			while (random.nextInt(3) == 0) {
				float radianAngle = random.nextFloat() * 6.2831855F;
				int x = 0;
				int z = 0;
				
				for (int l = 0; l < branchLength; ++l) {
					x = (int) (1.5F + MathHelper.cos(radianAngle) * (float) l);
					z = (int) (1.5F + MathHelper.sin(radianAngle) * (float) l);
					BlockPos blockPos = startPos.add(x, y - 3 + l / 2, z);
					getAndSetState(world, replacer, random, blockPos, config);
				}
				
				list.add(new FoliagePlacer.TreeNode(startPos.add(x, y + 1, z), 0, false));
				
			}
		}
		
		return list;
	}
}
