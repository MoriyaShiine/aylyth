package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.common.block.entity.WoodyGrowthCacheBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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
        // Find how many caches will need to be made
        // Use BlockPos.randomIterate to get positions for number of caches
        // loop through until the index returned from the blockentity is size of the inventory

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? new WoodyGrowthCacheBlockEntity(pos, state) : null;
    }
}
