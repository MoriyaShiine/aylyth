package moriyashiine.aylyth.common.block;

import com.google.common.collect.Lists;
import moriyashiine.aylyth.common.block.entity.WoodyGrowthCacheBlockEntity;
import moriyashiine.aylyth.common.registry.ModBlocks;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WoodyGrowthCacheBlock extends LargeWoodyGrowthBlock implements BlockEntityProvider {

    public WoodyGrowthCacheBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            BlockEntity be = null;
            if (state.hasBlockEntity() && state.get(HALF) == DoubleBlockHalf.UPPER) {
                be = world.getBlockEntity(pos.down());
            }
            dropStacks(state, world, pos, be, player, player.getMainHandStack());
        }
        super.onBreak(world, pos, state, player);
    }

    public static void spawnInventory(World world, BlockPos pos, PlayerEntity player) {
        var inv = player.getInventory();
        List<ItemStack> list = Lists.newArrayList();
        for (int i = 0; i < inv.size(); i++) {
            var stack = inv.removeStack(i);
            if (!stack.isEmpty()) {
                list.add(stack);
            }
        }

        var numCaches = (int) Math.ceil(((double)list.size()) / 9.0);
        int i = 0;
        var iter = BlockPos.iterateOutwards(pos, 4, 0, 4).iterator();
        while (numCaches-- > 0 && iter.hasNext()) {
            var placePos = iter.next();
            var y = pos.getY()+1;
            var state = ModBlocks.WOODY_GROWTH_CACHE.getDefaultState();
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
            var be = world.getBlockEntity(placePos);
            if (be instanceof WoodyGrowthCacheBlockEntity cache) {
                cache.setPlayerUuid(player);
                i = cache.fill(list, i);
            } else {
                throw new IllegalStateException("Something has gone wrong."); // TODO?
            }
        }
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
