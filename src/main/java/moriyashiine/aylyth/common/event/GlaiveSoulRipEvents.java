package moriyashiine.aylyth.common.event;

import moriyashiine.aylyth.common.data.AylythDamageTypes;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.entity.types.mob.RippedSoulEntity;
import moriyashiine.aylyth.common.item.AylythItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class GlaiveSoulRipEvents {
    public static void init() {
        ServerLivingEntityEvents.AFTER_DEATH.register(GlaiveSoulRipEvents::spawnRippedSoul);
    }

    private static void spawnRippedSoul(LivingEntity livingEntity, DamageSource source) {
        World world = livingEntity.getWorld();
        if(!world.isClient) {
            if(source.isOf(AylythDamageTypes.SOUL_RIP)) {
                RippedSoulEntity soul = new RippedSoulEntity(AylythEntityTypes.RIPPED_SOUL, world);
                if (source.getAttacker() != null) {
                    soul.setOwner((PlayerEntity) source.getAttacker());
                    soul.setPosition(livingEntity.getPos().add(0, 1, 0));
                    world.spawnEntity(soul);
                }
            }else if((source.getAttacker() != null && source.getAttacker() instanceof PlayerEntity playerEntity && playerEntity.getMainHandStack().isOf(AylythItems.YMPE_GLAIVE))){
                RippedSoulEntity soul = new RippedSoulEntity(AylythEntityTypes.RIPPED_SOUL, world);
                soul.setOwner(playerEntity);
                soul.setPosition(playerEntity.getPos().add(0, 1, 0));
                world.spawnEntity(soul);
            }
        }
    }
}
