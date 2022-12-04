package moriyashiine.aylyth.common.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
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
        state = state.with(HALF, DoubleBlockHalf.UPPER);
        world.setBlockState(pos.up(), state);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            if (player.isCreative()) {
                TallPlantBlock.onBreakInCreative(world, pos, state, player);
            } else {
                BlockEntity be = null;
                if (state.hasBlockEntity() && state.get(HALF) == DoubleBlockHalf.UPPER) {
                    be = world.getBlockEntity(pos.down());
                }
                dropStacks(state, world, pos, be, player, player.getMainHandStack());
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
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.up()).isAir() && super.canPlaceAt(state, world, pos);
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
