package moriyashiine.aylyth.common.item.type;

import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class PomegranateItem extends Item {

    public PomegranateItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (this.isFood() && world.getRegistryKey() != AylythDimensionData.AYLYTH) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), user.getEatSound(stack), SoundCategory.NEUTRAL, 1.0f, 1.0f + (world.random.nextFloat() - world.random.nextFloat()) * 0.4f);
                List<Pair<StatusEffectInstance, Float>> list = getFoodComponent().getStatusEffects();
                for (Pair<StatusEffectInstance, Float> pair : list) {
                    if (world.isClient || pair.getFirst() == null || !(world.random.nextFloat() < pair.getSecond())) continue;
                    user.addStatusEffect(new StatusEffectInstance(pair.getFirst()));
                }
                if (user instanceof PlayerEntity player) {
                    player.getHungerManager().add(getFoodComponent().getHunger() / 2, getFoodComponent().getSaturationModifier() / 2);
                    player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5f, world.random.nextFloat() * 0.1f + 0.9f);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)player, stack);
                    }
                }
                AylythUtil.decreaseStack(stack, user);
                user.emitGameEvent(GameEvent.EAT);
            return stack;
        }
        return super.finishUsing(stack, world, user);
    }
}
