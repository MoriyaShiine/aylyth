package moriyashiine.aylyth.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public class JackolanternShelfMushroomBlock extends ShelfMushroomBlock {

    public static final BooleanProperty GLOWING = BooleanProperty.of("glowing");

    private final Supplier<StagedMushroomPlantBlock> groundBlock;

    public JackolanternShelfMushroomBlock(Supplier<StagedMushroomPlantBlock> groundBlock, Settings settings) {
        super(settings);
        this.groundBlock = groundBlock;
        setDefaultState(stateManager.getDefaultState().with(GLOWING, false));
    }

    @Override
    protected boolean shouldStopSpreading(BlockState state, World world, BlockPos pos) {
        int i = 5;
        for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (!(world.getBlockState(blockPos).isOf(this) || world.getBlockState(blockPos).isOf(groundBlock.get())) || --i > 0) continue;
            return true;
        }
        return false;
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
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (!world.getBlockTickScheduler().isQueued(pos, this)) {
            world.scheduleBlockTick(pos, this, 100);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
    }

    @Override
    public Optional<BlockState> findGrowState(WorldAccess world, BlockPos pos) {
        if (world.getRandom().nextFloat() < 0.5) {
            Optional<BlockState> groundState = groundBlock.get().findGrowState(world, pos);
            if (groundState.isPresent()) {
                return groundState;
            }
        }
        return super.findGrowState(world, pos);
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
        if (!world.getBlockTickScheduler().isQueued(pos, this)) {
            world.scheduleBlockTick(pos, this, 100);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(GLOWING);
    }
}
