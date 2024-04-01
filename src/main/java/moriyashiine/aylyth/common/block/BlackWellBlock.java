package moriyashiine.aylyth.common.block;

import com.google.common.base.Suppliers;
import moriyashiine.aylyth.common.registry.ModPotions;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

@SuppressWarnings("UnstableApiUsage")
public class BlackWellBlock extends Block {
    private static final Supplier<ItemVariant> BLIGHT_POTION = Suppliers.memoize(() -> ItemVariant.of(PotionUtil.setPotion(new ItemStack(Items.POTION), ModPotions.BLIGHT_POTION)));

    public BlackWellBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ContainerItemContext storage = ContainerItemContext.forPlayerInteraction(player, hand);
        try (Transaction transaction = Transaction.openOuter()) {
            if (storage.getItemVariant().isOf(Items.GLASS_BOTTLE)) {
                if (storage.exchange(BLIGHT_POTION.get(), 1, transaction) == 1) {
                    transaction.commit();
                }
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 1.0f, 1.0f);
                return ActionResult.success(world.isClient);
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
