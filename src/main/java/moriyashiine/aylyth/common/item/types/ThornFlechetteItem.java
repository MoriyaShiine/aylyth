package moriyashiine.aylyth.common.item.types;

import moriyashiine.aylyth.common.entity.types.projectile.ThornFlechetteEntity;
import moriyashiine.aylyth.common.item.component.ThornFlechetteEffect;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ThornFlechetteItem extends Item {
    private final ThornFlechetteEffect effect;

    public ThornFlechetteItem(Settings settings, ThornFlechetteEffect effect) {
        super(settings);
        this.effect = effect;
    }

    public ThornFlechetteItem(Settings settings) {
        this(settings, null);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.success(stack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int useTime = user.getItemUseTime();
        if (useTime > 10) {
            ThornFlechetteEntity entity = new ThornFlechetteEntity(user, world);
            ItemStack entityStack = stack.copy();
            entityStack.setCount(1);
            entity.setStack(entityStack);
            entity.setVelocity(user, user.getPitch(), user.getYaw(), 0, 2, 1);
            world.spawnEntity(entity);
            world.playSoundFromEntity(null, entity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1f, 1f);
            AylythUtil.decreaseStack(stack, user); // TODO: this does decrease for entities other than the player too
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    public ThornFlechetteEffect getEffect() {
        return effect;
    }
}
