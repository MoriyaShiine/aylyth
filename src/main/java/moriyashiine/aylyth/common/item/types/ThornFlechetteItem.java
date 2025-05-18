package moriyashiine.aylyth.common.item.types;

import moriyashiine.aylyth.common.entity.types.projectile.ThornFlechetteEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ThornFlechetteItem extends Item {
    public ThornFlechetteItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int useTime = user.getItemUseTime();
        if (useTime > 10) {
            ThornFlechetteEntity entity = new ThornFlechetteEntity(user, world, stack.copyWithCount(1));
            entity.setVelocity(user, user.getPitch(), user.getYaw(), 0, 2, 1);
            world.spawnEntity(entity);
            world.playSoundFromEntity(null, entity, SoundEvents.ITEM_TRIDENT_THROW.value(), SoundCategory.PLAYERS, 1f, 1f);
            stack.decrementUnlessCreative(1, user);
            return true;
        }
        return super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }
}
