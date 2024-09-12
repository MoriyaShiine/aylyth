package moriyashiine.aylyth.common.block.type;

import moriyashiine.aylyth.common.item.AylythItems;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@SuppressWarnings("UnstableApiUsage")
public class PomegranateLeavesBlock extends LeavesBlock {

    public static final IntProperty FRUITING = IntProperty.of("fruiting_stage", 0, 3);

    public PomegranateLeavesBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FRUITING, 0));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(FRUITING) == 3 && player.getStackInHand(hand).isOf(Items.DEBUG_STICK)) {
            world.setBlockState(pos, state.with(FRUITING, 0));
            PlayerInventoryStorage storage = PlayerInventoryStorage.of(player);
            try (Transaction transaction = Transaction.openOuter()) {
                storage.offerOrDrop(ItemVariant.of(AylythItems.POMEGRANATE), 1, transaction);
                transaction.commit();
            }
            world.playSound(null, hit.getBlockPos(), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
            return ActionResult.success(world.isClient());
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return super.hasRandomTicks(state) || state.get(FRUITING) < 3;
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
