package moriyashiine.aylyth.common.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModComponents;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.tag.BlockTags;

public class YmpeCuirassItem extends TrinketItem {
    public static final short TIME_UNTIL_STAGE_INCREASES = 200;
    public YmpeCuirassItem(Settings settings) {
        super(settings);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.tick(stack, slot, entity);
        if(entity instanceof PlayerEntity player && player.getSteppingBlockState().isIn(BlockTags.DIRT)){
            ModComponents.CUIRASS_COMPONENT.maybeGet(player).ifPresent(comp -> {
                if(comp.getStage() < comp.MAX_STAGE){
                    comp.setStageTimer(comp.getStageTimer() + 1);
                    if(comp.getStageTimer() >= TIME_UNTIL_STAGE_INCREASES){
                        if(comp.getStage() % 4 == 0){
                            player.world.playSoundFromEntity(null, player, ModSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE, SoundCategory.PLAYERS, 1, player.getSoundPitch());
                        }
                        comp.setStage((comp.getStage() + 1));
                        comp.setStageTimer(0);
                    }
                    if(Aylyth.isDebugMode() && entity.world.getTime() % 10 == 0){
                        System.out.println(comp.getStage() + " : " + comp.getStageTimer());
                    }
                }
            });
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onUnequip(stack, slot, entity);
        if(entity instanceof PlayerEntity player){
            ModComponents.CUIRASS_COMPONENT.maybeGet(player).ifPresent(comp -> {
                comp.setStage(0.0F);
                comp.setStageTimer(0);
            });
        }
    }
}