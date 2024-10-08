package moriyashiine.aylyth.common.item.types;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import moriyashiine.aylyth.common.data.tag.AylythStatusEffectTags;
import moriyashiine.aylyth.common.item.AylythItems;
import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class YmpeEffigyItem extends TrinketItem {
    public YmpeEffigyItem(Settings settings) {
        super(settings);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.tick(stack, slot, entity);
        if (entity instanceof PlayerEntity player) {
            player.getHungerManager().setFoodLevel(20);
            player.getHungerManager().setSaturationLevel(10.0F);
        }
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {
            this.cleanBadEffects(player);
        }


        super.onEquip(stack, slot, entity);
    }


    private void cleanBadEffects(PlayerEntity player) {
        for (StatusEffectInstance effectInstance : player.getStatusEffects()) {
            StatusEffect effectType = effectInstance.getEffectType();
            if (!TagUtil.isIn(AylythStatusEffectTags.EFFIGY_CANNOT_CURE, effectType)) {
                player.removeStatusEffect(effectType);
            }
        }
    }

    public boolean isEquipped(LivingEntity entity) {
        return TrinketsApi.getTrinketComponent(entity).filter(trinketComponent -> trinketComponent.isEquipped(this)).isPresent();
    }
}