package moriyashiine.aylyth.common.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ShelfMushroomBlock extends AbstractMushroomBlock {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final VoxelShape NORTH = VoxelShapes.cuboid(0, 0, 0.75, 1, 1, 1);
    public static final VoxelShape SOUTH = VoxelShapes.cuboid(0, 0, 0, 1, 1, 0.25);
    public static final VoxelShape EAST = VoxelShapes.cuboid(0, 0, 0, 0.25, 1, 1);
    public static final VoxelShape WEST = VoxelShapes.cuboid(0.75, 0, 0, 1, 1, 1);

    public ShelfMushroomBlock(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        if (ctx.getSide().getAxis().isHorizontal()) {
            return getDefaultState().with(FACING, ctx.getSide());
        }
        return null;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror.apply(state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case NORTH -> {
                return NORTH;
            }
            case SOUTH -> {
                return SOUTH;
            }
            case EAST -> {
                return EAST;
            }
            case WEST -> {
                return WEST;
            }
        }
        return super.getOutlineShape(state, world, pos, context);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        var direction = state.get(FACING);
        var placePos = pos.offset(direction.getOpposite());
        var behindBlock = world.getBlockState(placePos);
        return behindBlock.isSideSolidFullSquare(world, pos, direction);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        var posStream = BlockPos.stream(pos.add(-3, -1, -3), pos.add(3, 1, 3));
        return posStream.anyMatch(world::isAir);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        randomTick(state, world, pos, random);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public Optional<BlockState> findGrowState(WorldAccess world, BlockPos pos) {
        for (Direction dir : Direction.Type.HORIZONTAL) {
            var blockFacing = getDefaultState().with(FACING, dir.getOpposite());
            if (!world.isAir(pos.offset(dir)) && blockFacing.canPlaceAt(world, pos)) {
                return Optional.of(blockFacing);
            }
        }
        return Optional.empty();
    }
}
