package moriyashiine.aylyth.common.world.gen.features;

import moriyashiine.aylyth.common.data.tag.AylythBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class MirePoolsFeature extends Feature<DefaultFeatureConfig> {
    public MirePoolsFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();

        int radius = 2;
        boolean placedAny = false;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz > radius * radius) continue;

                BlockPos pos = origin.add(dx, 0, dz);
                BlockState state = world.getBlockState(pos);

                if (state.isIn(AylythBlockTags.POOL_NEIGHBORING)
                        && (world.isAir(pos.up())) //|| world.getBlockState(pos.up()).isOf(Blocks.WATER))
                        && isSurroundedBySolid(world, pos)
                        && context.getRandom().nextFloat() < 0.7f) {
                    BlockState waterState = Blocks.WATER.getDefaultState();
                    world.setBlockState(pos, waterState, 3);
                    world.scheduleFluidTick(pos, waterState.getFluidState().getFluid(), 10);
                    placedAny = true;
                }
            }
        }

        return placedAny;
    }

    private boolean isSurroundedBySolid(StructureWorldAccess world, BlockPos pos) {
        BlockState down = world.getBlockState(pos.down());
        BlockState current = world.getBlockState(pos);

        if (!down.isIn(AylythBlockTags.POOL_NEIGHBORING)) return false;
        if (!current.isAir() && !current.isIn(AylythBlockTags.POOL_NEIGHBORING)) return false;

        int solidCount = 0;
        if (world.getBlockState(pos.north()).isIn(AylythBlockTags.POOL_NEIGHBORING)) solidCount++;
        if (world.getBlockState(pos.south()).isIn(AylythBlockTags.POOL_NEIGHBORING)) solidCount++;
        if (world.getBlockState(pos.east()).isIn(AylythBlockTags.POOL_NEIGHBORING)) solidCount++;
        if (world.getBlockState(pos.west()).isIn(AylythBlockTags.POOL_NEIGHBORING)) solidCount++;

        return solidCount >= 4;
    }
}
