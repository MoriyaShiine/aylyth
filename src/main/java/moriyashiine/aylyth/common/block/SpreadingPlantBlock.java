package moriyashiine.aylyth.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Optional;

public class SpreadingPlantBlock extends Block {

    private static final VoxelShape SHAPE = VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.25, 0.75);

    public SpreadingPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (random.nextInt(25) == 0) {
            if (shouldStopSpreading(state, world, pos)) return;
            int xyRange = 2;
            for (BlockPos placePos : BlockPos.iterateRandomly(random, 3, pos.getX()-xyRange, pos.getY()-1, pos.getZ()-xyRange, pos.getX()+xyRange, pos.getY()+1, pos.getZ()+xyRange)) {
                Optional<BlockState> sideStateOptional = findGrowState(world, placePos);
                if (world.isAir(placePos) && isValidLight(world, placePos) && sideStateOptional.isPresent()) {
                    world.setBlockState(placePos, sideStateOptional.get());
                    return;
                }
            }
        }
    }

    protected Optional<BlockState> findGrowState(WorldAccess world, BlockPos pos) {
        return Optional.of(getDefaultState());
    }

    protected boolean shouldStopSpreading(BlockState state, World world, BlockPos pos) {
        int i = 5;
        for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (!world.getBlockState(blockPos).isOf(this) || --i > 0) continue;
            return true;
        }
        return false;
    }

    protected static boolean isValidLight(WorldAccess world, BlockPos pos) {
        return world.getBaseLightLevel(pos, 0) < 13;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        if (type == NavigationType.AIR && !this.collidable) {
            return true;
        }
        return super.canPathfindThrough(state, world, pos, type);
    }
}
