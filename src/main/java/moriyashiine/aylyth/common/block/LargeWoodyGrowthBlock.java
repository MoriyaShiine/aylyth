package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.common.registry.AylythBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class LargeWoodyGrowthBlock extends SmallWoodyGrowthBlock {

    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape LOWER_SHAPE = VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 1, 0.75);
    private static final VoxelShape UPPER_SHAPE = VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.5, 0.75);

    public LargeWoodyGrowthBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        boolean upperWaterlogged = world.testFluidState(pos.up(), fluidState -> fluidState.getFluid() == Fluids.WATER);
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, upperWaterlogged));
    }

    /** This is a helper method for placing woody growths. It handles placing the two block states of large woody
     *   growths and handles waterlogging. If the passed state is not one of the expected blocks it will do nothing.
     * @param state The desired state. NOTE: The waterlog state will be overwritten in this method
     * @param world Desired world
     * @param pos The BlockPos to test and place at. Large woody growths will place another state above it.*/
    public static void placeInWorld(BlockState state, World world, BlockPos pos) {
        if (state.isOf(AylythBlocks.LARGE_WOODY_GROWTH) || state.isOf(AylythBlocks.WOODY_GROWTH_CACHE)) {
            world.setBlockState(pos, state.with(WATERLOGGED, world.testFluidState(pos, fluidState -> fluidState.getFluid() == Fluids.WATER)));
            world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, world.testFluidState(pos.up(), fluidState -> fluidState.getFluid() == Fluids.WATER)));
        } else if (state.isOf(AylythBlocks.SMALL_WOODY_GROWTH)) {
            world.setBlockState(pos, state.with(WATERLOGGED, world.testFluidState(pos, fluidState -> fluidState.getFluid() == Fluids.WATER)));
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            if (player.isCreative()) {
                TallPlantBlock.onBreakInCreative(world, pos, state, player);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP) && (!neighborState.isOf(this) || neighborState.get(HALF) == doubleBlockHalf)) {
            return neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf ? state.with(HALF, neighborState.get(HALF)) : Blocks.AIR.getDefaultState();
        }
        return doubleBlockHalf == DoubleBlockHalf.LOWER ? super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos) : state;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (pos.getY() < world.getTopY() - 1 && world.getBlockState(pos.up()).canReplace(ctx)) {
            return super.getPlacementState(ctx);
        }
        return null;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            BlockState lowerState = world.getBlockState(pos.down());
            return lowerState.isOf(this) && lowerState.get(HALF) == DoubleBlockHalf.LOWER;
        }
        BlockState stateUp = world.getBlockState(pos.up());
        return (stateUp.isReplaceable() || stateUp.isOf(this)) && super.canPlaceAt(state, world, pos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? LOWER_SHAPE : UPPER_SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HALF);
    }
}
