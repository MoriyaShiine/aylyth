package moriyashiine.aylyth.common.event;

import moriyashiine.aylyth.common.advancement.AylythCriteria;
import moriyashiine.aylyth.common.data.tag.AylythEntityTypeTags;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.network.packets.SpawnParticlesAroundPacketS2C;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Unit;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShuckingEvents {
    public static void init() {
        AttackEntityCallback.EVENT.register(ShuckingEvents::attackWithYmpeDagger);
    }

    private static ActionResult attackWithYmpeDagger(PlayerEntity attacker, World world, Hand hand, Entity target, @Nullable EntityHitResult hitResult) {
        if (attacker.getStackInHand(hand).isOf(AylythItems.YMPE_DAGGER) && target instanceof MobEntity mob) {
            ItemStack offhand = attacker.getOffHandStack();
            if (offhand.isOf(AylythItems.SHUCKED_YMPE_FRUIT)) {
                NbtComponent storedEntity = offhand.getOrDefault(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT);
                if (storedEntity.isEmpty() && !mob.getType().isIn(AylythEntityTypeTags.NON_SHUCKABLE)) {
                    if (attacker instanceof ServerPlayerEntity serverPlayer) {
                        AylythCriteria.SHUCKING.trigger(serverPlayer, mob);
                        mob.setHealth(mob.getMaxHealth()); // TODO: check whether this is intended behavior
                        mob.clearStatusEffects();
                        mob.extinguish();
                        mob.setFrozenTicks(0);
                        mob.setVelocity(Vec3d.ZERO);
                        mob.fallDistance = 0;
                        mob.setAttached(AylythEntityAttachmentTypes.PREVENT_DROPS, Unit.INSTANCE);
                        PlayerLookup.tracking(mob).forEach(trackingPlayer -> {
                            ServerPlayNetworking.send(trackingPlayer, new SpawnParticlesAroundPacketS2C(mob.getId(), 32, List.of(ParticleTypes.SMOKE, ParticleTypes.FALLING_HONEY)));
                        });
                        world.playSound(null, mob.getBlockPos(), AylythSoundEvents.ENTITY_GENERIC_SHUCKED.value(), mob.getSoundCategory(), 1, mob.getSoundPitch());
                        NbtComponent.set(DataComponentTypes.ENTITY_DATA, offhand, mob::writeNbt);
                        mob.remove(Entity.RemovalReason.DISCARDED);
                        // deal a bit of damage to the player
                        attacker.damage(serverPlayer.getServerWorld(), world.aylythDamageSources().shucking(), 1);
                    }
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }
}
