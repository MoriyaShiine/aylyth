package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class PomegranateLeavesBlock extends LeavesBlock {

    public static final IntProperty FRUITING = IntProperty.of("fruiting_stage", 0, 3);

    public PomegranateLeavesBlock(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(FRUITING, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            if (state.get(FRUITING) == 3 && player.getStackInHand(hand).getItem() != Items.DEBUG_STICK) {
                serverWorld.setBlockState(pos, state.with(FRUITING, 0));
                ItemScatterer.spawn(world, pos.getX()+0.5, pos.getY()-0.15, pos.getZ()+0.5, new ItemStack(ModItems.POMEGRANATE));
                return ActionResult.success(world.isClient());
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (!state.get(PERSISTENT) && state.get(FRUITING) < 3 && random.nextInt(25) == 0 && world.isAir(pos.down())) {
            world.setBlockState(pos, state.with(FRUITING, state.get(FRUITING)+1));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FRUITING);
    }
}
