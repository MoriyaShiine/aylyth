package moriyashiine.aylyth.common.block.type;

import com.google.common.collect.Lists;
import moriyashiine.aylyth.common.block.entity.type.WoodyGrowthCacheBlockEntity;
import moriyashiine.aylyth.common.block.AylythBlocks;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class WoodyGrowthCacheBlock extends LargeWoodyGrowthBlock implements BlockEntityProvider {

    public static final Identifier CONTENTS = ShulkerBoxBlock.CONTENTS_DYNAMIC_DROP_ID;

    public WoodyGrowthCacheBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && player.isCreative()) {
            BlockEntity be = null;
            if (state.hasBlockEntity()) {
                be = world.getBlockEntity(state.get(HALF) == DoubleBlockHalf.UPPER ? pos.down() : pos);
            }
            dropStacks(state.with(HALF, DoubleBlockHalf.LOWER), world, pos, be, player, player.getMainHandStack());
        }
        super.onBreak(world, pos, state, player);
    }

    public static void spawnInventory(World world, BlockPos pos, PlayerEntity player) {
        PlayerInventory inv = player.getInventory();
        List<ItemStack> list = Lists.newArrayList();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.removeStack(i);
            if (!stack.isEmpty()) {
                list.add(stack);
            }
        }

        int numCaches = (int) Math.ceil(((double)list.size()) / 9.0);
        int i = 0;
        Iterator<BlockPos> iter = BlockPos.iterateOutwards(pos, 4, 0, 4).iterator();
        while (numCaches-- > 0 && iter.hasNext()) {
            BlockPos placePos = iter.next();
            int y = pos.getY()+1;
            BlockState state = AylythBlocks.WOODY_GROWTH_CACHE.getDefaultState();
            do {
                placePos = placePos.withY(y);
            } while (isInvalidPosition(placePos, state, world) && y-- > world.getBottomY());
            if (isInvalidPosition(placePos, state, world)) {
                placePos = placePos.withY(world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, placePos.getX(), placePos.getZ()));
            }
            if (world.getFluidState(placePos).getFluid() == Fluids.WATER) {
                state = state.with(WATERLOGGED, true);
            }
            world.setBlockState(placePos, state);
            world.setBlockState(placePos.up(), state.with(HALF, DoubleBlockHalf.UPPER));
            BlockEntity be = world.getBlockEntity(placePos);
            if (be instanceof WoodyGrowthCacheBlockEntity cache) {
                cache.setPlayerUuid(player);
                i = cache.fill(list, i);
            }
        }
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        BlockEntity be = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
        if (be instanceof WoodyGrowthCacheBlockEntity cache) {
            builder.addDynamicDrop(CONTENTS, consumer -> {
                for (int i = 0; i < cache.size(); i++) {
                    consumer.accept(cache.getItem(i));
                }
            });
        }
        return super.getDroppedStacks(state, builder);
    }

    private static boolean isInvalidPosition(BlockPos pos, BlockState state, World world) {
        return !state.canPlaceAt(world, pos) || world.isAir(pos.down());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? new WoodyGrowthCacheBlockEntity(pos, state) : null;
    }
}
