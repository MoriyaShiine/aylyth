package moriyashiine.aylyth.common.block;

import com.google.common.collect.Lists;
import moriyashiine.aylyth.common.block.entity.WoodyGrowthCacheBlockEntity;
import moriyashiine.aylyth.common.registry.ModBlocks;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
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
        super.onBreak(world, pos, state, player);
//        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
//            pos = pos.down();
//        }
        if (!world.isClient()) {
            if (world.getBlockEntity(pos) instanceof WoodyGrowthCacheBlockEntity be) {
                be.drop(world, pos);
            }
        }
    }

    public static void spawnInventory(World world, BlockPos pos, Inventory inv) {
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
            } while ((!state.canPlaceAt(world, placePos) || world.isAir(placePos.down())) && y-- > world.getBottomY());
            if (!state.canPlaceAt(world, placePos) || world.isAir(placePos.down())) {
                placePos = placePos.withY(world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, placePos.getX(), placePos.getZ()));
            }
            world.setBlockState(placePos, ModBlocks.WOODY_GROWTH_CACHE.getDefaultState());
            var be = world.getBlockEntity(placePos);
            if (be instanceof WoodyGrowthCacheBlockEntity cache) {
                i = cache.fill(list, i);
            } else {
                throw new IllegalStateException("Something has gone wrong."); // TODO?
            }
        }
    }

//    private static int findYValue() {
//
//    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? new WoodyGrowthCacheBlockEntity(pos, state) : null;
    }
}
