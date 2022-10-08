package moriyashiine.aylyth.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class StrewnLeavesBlock extends Block {

    public static final IntProperty LEAVES = IntProperty.of("leaves", 0, 7);
    private static final VoxelShape[] SHAPE = new VoxelShape[] {
            VoxelShapes.cuboid(0,0, 0, 1, 0.0625, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.125, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.1875, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.25, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.3125, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.375, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.4375, 1),
            VoxelShapes.cuboid(0,0, 0, 1, 0.5, 1)
    };

    public StrewnLeavesBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEAVES, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock().equals(this) && state.get(LEAVES) < 7) {
                world.setBlockState(pos, state.with(LEAVES, state.get(LEAVES)+1));
                world.playSound(null, pos, getSoundGroup(state).getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResult.success(world.isClient);
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE[state.get(LEAVES)];
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        if (pos.down().equals(sourcePos) && !world.getBlockState(sourcePos).isFullCube(world, sourcePos)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        var downState = world.getBlockState(pos.down());
        return downState.isFullCube(world, pos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LEAVES);
    }
}
