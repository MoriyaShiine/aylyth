package moriyashiine.aylyth.common.world.gen.features;

import moriyashiine.aylyth.common.data.tag.AylythBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class MireFloorPoolFeature extends Feature<DefaultFeatureConfig> {
    public MireFloorPoolFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();

        int floorY = 36;
        int floorYMin = 24;
        int floorRadius = 12;
        boolean placedAny = false;

        for (int dx = -floorRadius; dx <= floorRadius; dx++) {
            for (int dz = -floorRadius; dz <= floorRadius; dz++) {
                if (dx * dx + dz * dz > floorRadius * floorRadius) continue;

                for (int y = floorYMin; y <= floorY; y++) {
                    BlockPos pos = new BlockPos(origin.getX() + dx, y, origin.getZ() + dz);
                    BlockState state = world.getBlockState(pos);
                    if (state.isAir()) {
                        BlockState waterState = Blocks.WATER.getDefaultState();
                        world.setBlockState(pos, waterState, 3);
                        world.scheduleFluidTick(pos, waterState.getFluidState().getFluid(), 10);
                        placedAny = true;
                    }
                }
            }
        }

        return placedAny;
    }
}
