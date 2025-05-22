package moriyashiine.aylyth.common.item.types;

import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.types.SoulHearthBlock;
import moriyashiine.aylyth.common.item.AylythDataComponentTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NephriteFlaskItem extends Item {
    public NephriteFlaskItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack stack = context.getStack();
        BlockState state = world.getBlockState(pos);
        int uses = stack.getOrDefault(AylythDataComponentTypes.FLASK_CHARGES, 0);
        int maxUses = stack.getOrDefault(AylythDataComponentTypes.MAX_FLASK_CHARGES, 0);
        if (maxUses > 0 && uses < maxUses) {
            if (state.isOf(AylythBlocks.SOUL_HEARTH) && state.get(SoulHearthBlock.CHARGES) > 0) {
                world.setBlockState(pos, state.with(SoulHearthBlock.CHARGES, state.get(SoulHearthBlock.CHARGES)-1), Block.NOTIFY_ALL);
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                stack.set(AylythDataComponentTypes.FLASK_CHARGES, maxUses);
                if (context.getPlayer() != null) {
                    context.getPlayer().incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                }
                return ActionResult.SUCCESS;
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        int uses = user.getStackInHand(hand).getOrDefault(AylythDataComponentTypes.FLASK_CHARGES, 0);
        if (uses > 0) {
            return super.use(world, user, hand);
        }
        return ActionResult.PASS;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        int uses = stack.getOrDefault(AylythDataComponentTypes.FLASK_CHARGES, 0);
        if (uses - 1 <= 0) {
            stack.remove(AylythDataComponentTypes.FLASK_CHARGES);
        } else {
            stack.set(AylythDataComponentTypes.FLASK_CHARGES, uses - 1);
        }
        return super.finishUsing(stack, world, user);
    }
}
