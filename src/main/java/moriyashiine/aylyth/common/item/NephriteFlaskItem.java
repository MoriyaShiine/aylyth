package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.block.SoulHearthBlock;
import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NephriteFlaskItem extends Item {
    public static final int MAX_USES = 6;

    public NephriteFlaskItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack stack = context.getStack();
        BlockState state = world.getBlockState(pos);
        if (state.isOf(ModBlocks.SOUL_HEARTH) && state.get(SoulHearthBlock.CHARGES) > 0) {
            world.setBlockState(pos, state.with(SoulHearthBlock.CHARGES, state.get(SoulHearthBlock.CHARGES)-1), Block.NOTIFY_ALL);
            world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            fill(stack);
            if (context.getPlayer() != null) {
                context.getPlayer().incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            }
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 16;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (hasCharges(user.getStackInHand(hand))) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return super.use(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        growBy(stack, -1);
        if (user.getHealth() == user.getMaxHealth()) {
            VitalHealthHolder.of(user).ifPresent(vitalHolder -> {
                vitalHolder.set(vitalHolder.get()+4);
            });
        } else {
            // TODO: Switch to component for more versatility
            if (this == ModItems.NEPHRITE_FLASK) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1));
            } else if (this == ModItems.DARK_NEPHRITE_FLASK) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1));
            }
        }

        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public static boolean hasCharges(ItemStack stack) {
        return getCharges(stack) > 0;
    }

    public static int getCharges(ItemStack stack) {
        return stack.hasNbt() ? stack.getNbt().getInt("uses") : 0;
    }

    public static ItemStack fill(ItemStack stack) {
        return withUses(stack, MAX_USES);
    }

    public static ItemStack growBy(ItemStack stack, int uses) {
        return withUses(stack, stack.getOrCreateNbt().getInt("uses") + uses);
    }

    public static ItemStack withUses(ItemStack stack, int uses) {
        if (uses > 0) {
            stack.getOrCreateNbt().putInt("uses", uses);
        } else if (stack.hasNbt()) {
            stack.getNbt().remove("uses");
            if (stack.getNbt().isEmpty()) {
                stack.setNbt(null);
            }
        }
        return stack;
    }
}
