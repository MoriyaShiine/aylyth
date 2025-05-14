package moriyashiine.aylyth.common.item.types;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.attachments.CuirassStages;
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
            CuirassStages cuirass = player.getAttachedOrCreate(AylythEntityAttachmentTypes.CUIRASS);
            if (cuirass.getStage() < CuirassStages.MAX_STAGE) {
                cuirass.setStageTimer(cuirass.getStageTimer() + 1);
                if (cuirass.getStageTimer() >= TIME_UNTIL_STAGE_INCREASES) {
                    if (cuirass.getStage() % 4 == 0) {
                        player.getWorld().playSoundFromEntity(null, player, AylythSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE.value(), SoundCategory.PLAYERS, 1, player.getSoundPitch());
                    }
                    cuirass.setStage((cuirass.getStage() + 1));
                    cuirass.setStageTimer(0);
                }
            }
            player.setAttached(AylythEntityAttachmentTypes.CUIRASS, cuirass);
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onUnequip(stack, slot, entity);
        if (entity instanceof PlayerEntity player) {
            player.removeAttached(AylythEntityAttachmentTypes.CUIRASS);
        }
    }
}
