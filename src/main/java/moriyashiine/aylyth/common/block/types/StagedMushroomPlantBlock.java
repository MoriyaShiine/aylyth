package moriyashiine.aylyth.common.block.types;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
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
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Optional;

public class StagedMushroomPlantBlock extends SpreadingPlantBlock implements Fertilizable {

    public static final IntProperty STAGE = IntProperty.of("stage", 1, 3);
    public static final VoxelShape SHAPE = VoxelShapes.cuboid(0.125, 0, 0.125, 0.875, 0.25, 0.875);

    public StagedMushroomPlantBlock(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(STAGE, 1));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getStackInHand(hand);
        if (heldItem.isOf(asItem()) && !isMaxStage(state)) {
            world.setBlockState(pos, state.with(STAGE, state.get(STAGE)+1));
            AylythUtil.decreaseStack(heldItem, player);
            world.playSound(null, pos, getSoundGroup(state).getPlaceSound(), SoundCategory.BLOCKS, 1.0f, 1.0f);
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isMaxStage(state) && random.nextInt(25) == 0) {
            world.setBlockState(pos, state.with(STAGE, state.get(STAGE)+1));
        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    protected boolean shouldStopSpreading(BlockState state, World world, BlockPos pos) {
        if (!isMaxStage(state)) {
            return true;
        }
        return super.shouldStopSpreading(state, world, pos);
    }

    @Override
    protected Optional<BlockState> findGrowState(WorldAccess world, BlockPos pos) {
        if (world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP)) {
            return Optional.of(getDefaultState());
        }
        return Optional.empty();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return !isMaxStage(state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(STAGE, state.get(STAGE)+1));
    }

    public boolean isMaxStage(BlockState state) {
        return state.get(STAGE) >= 3;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(STAGE);
    }
}
