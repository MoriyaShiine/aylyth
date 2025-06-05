package moriyashiine.aylyth.common.block.types;

import moriyashiine.aylyth.common.block.AylythProperties;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class LeafPileBlock extends Block {
    public static final IntProperty LEAVES = AylythProperties.LEAVES;
    private static final VoxelShape[] SHAPE = new VoxelShape[] {
            VoxelShapes.cuboid(0,0, 0, 1, 0.125, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.1875, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.25, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.3125, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.375, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.4375, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.5, 1)
    };

    public LeafPileBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEAVES, 1));
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() == this && state.get(LEAVES) < 7) {
                world.setBlockState(pos, state.with(LEAVES, state.get(LEAVES)+1));
                world.playSound(null, pos, AylythSoundEvents.BLOCK_STREWN_LEAVES_STEP.value(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                stack.decrementUnlessCreative(1, player);
                return ActionResult.SUCCESS;
            }
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE[state.get(LEAVES)-1];
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state) {
        return VoxelShapes.empty();
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (direction == Direction.DOWN && !neighborState.isFullCube(world, neighborPos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Deprecated
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.down());
        return downState.isFullCube(world, pos.down());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(LEAVES));
    }
}
