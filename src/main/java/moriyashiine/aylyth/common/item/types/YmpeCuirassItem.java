package moriyashiine.aylyth.common.item.types;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import moriyashiine.aylyth.common.entity.AylythEntityComponents;
import moriyashiine.aylyth.common.entity.components.CuirassComponent;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;

public class YmpeCuirassItem extends TrinketItem {
    public static final short TIME_UNTIL_STAGE_INCREASES = 200;
    public YmpeCuirassItem(Settings settings) {
        super(settings);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.tick(stack, slot, entity);
        if (entity instanceof PlayerEntity player && player.getSteppingBlockState().isIn(BlockTags.DIRT)) {
            AylythEntityComponents.CUIRASS_COMPONENT.maybeGet(player).ifPresent(comp -> {
                if (comp.getStage() < CuirassComponent.MAX_STAGE) {
                    comp.setStageTimer(comp.getStageTimer() + 1);
                    if (comp.getStageTimer() >= TIME_UNTIL_STAGE_INCREASES) {
                        if (comp.getStage() % 4 == 0) {
                            player.getWorld().playSoundFromEntity(null, player, AylythSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE.value(), SoundCategory.PLAYERS, 1, player.getSoundPitch());
                        }
                        comp.setStage((comp.getStage() + 1));
                        comp.setStageTimer(0);
                    }
                }
            });
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onUnequip(stack, slot, entity);
        if(entity instanceof PlayerEntity player){
            AylythEntityComponents.CUIRASS_COMPONENT.maybeGet(player).ifPresent(comp -> {
                comp.setStage(0.0F);
                comp.setStageTimer(0);
            });
        }
    }
}
