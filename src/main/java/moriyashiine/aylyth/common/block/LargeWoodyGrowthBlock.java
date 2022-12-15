package moriyashiine.aylyth.common.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
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
        boolean upperWaterlogged = world.testBlockState(pos.up(), state1 -> state1.getFluidState().getFluid() == Fluids.WATER);
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, upperWaterlogged));
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
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, stack);
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
        var pos = ctx.getBlockPos();
        var world = ctx.getWorld();
        if (pos.getY() < world.getTopY() - 1 && world.getBlockState(pos.up()).canReplace(ctx)) {
            return super.getPlacementState(ctx);
        }
        return null;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            var lowerState = world.getBlockState(pos.down());
            return lowerState.isOf(this) && lowerState.get(HALF) == DoubleBlockHalf.LOWER;
        }
        var stateUp = world.getBlockState(pos.up());
        return (stateUp.isAir() || stateUp.isOf(this)) && super.canPlaceAt(state, world, pos);
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
