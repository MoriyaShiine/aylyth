package moriyashiine.aylyth.common.block.types;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class JackolanternMushroomBlock extends StagedMushroomPlantBlock {

    public static final BooleanProperty GLOWING = BooleanProperty.of("glowing");

    private final Supplier<SpreadingPlantBlock> wallBlock;

    public JackolanternMushroomBlock(Supplier<SpreadingPlantBlock> wallBlock, Settings settings) {
        super(settings);
        this.wallBlock = wallBlock;
        setDefaultState(stateManager.getDefaultState().with(GLOWING, false));
    }

    @Override
    protected Optional<BlockState> findGrowState(WorldAccess world, BlockPos pos) {
        Optional<BlockState> wall = wallBlock.get().findGrowState(world, pos);
        if (wall.isPresent()) {
            return wall;
        }
        return super.findGrowState(world, pos);
    }

    @Override
    protected boolean shouldStopSpreading(BlockState state, World world, BlockPos pos) {
        int i = 5;
        for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (!(world.getBlockState(blockPos).isOf(this) || world.getBlockState(blockPos).isOf(wallBlock.get())) || --i > 0) continue;
            return true;
        }
        return false;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (!world.getBlockTickScheduler().isQueued(pos, this)) {
            world.scheduleBlockTick(pos, this, 100);
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        if (state != null) {
            World world = ctx.getWorld();
            state = state.with(GLOWING, getLight(world, ctx.getBlockPos()) < 6);
        }
        return state;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (!world.getBlockTickScheduler().isQueued(pos, this)) {
            this.scheduledTick(state, world, pos, random);
        }
    }

    private int getLight(World world, BlockPos pos) {
        return world.getLightLevel(pos, 0) - ((world.getTimeOfDay() < 23000 && world.getTimeOfDay() >= 13000) ? 10 : 0);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        int lightLevel = getLight(world, pos);
        boolean glowingState = state.get(GLOWING);
        if (glowingState ^ lightLevel < 8) {
            world.setBlockState(pos, state.with(GLOWING, lightLevel < 6));
        }
        world.scheduleBlockTick(pos, this, 100);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(GLOWING);
    }
}
