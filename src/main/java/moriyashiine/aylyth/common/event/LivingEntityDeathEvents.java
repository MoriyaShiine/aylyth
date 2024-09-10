package moriyashiine.aylyth.common.event;

import moriyashiine.aylyth.api.interfaces.HindPledgeHolder;
import moriyashiine.aylyth.common.block.VitalThuribleBlock;
import moriyashiine.aylyth.common.block.WoodyGrowthCacheBlock;
import moriyashiine.aylyth.common.entity.mob.RippedSoulEntity;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import moriyashiine.aylyth.common.registry.ModEntityAttributes;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.data.AylythDamageTypes;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class LivingEntityDeathEvents {

    public static void init(){
        ServerLivingEntityEvents.ALLOW_DEATH.register(LivingEntityDeathEvents::allowDeath);

        ServerLivingEntityEvents.AFTER_DEATH.register(LivingEntityDeathEvents::spawnRippedSoul);

        ServerPlayerEvents.COPY_FROM.register(LivingEntityDeathEvents::retainVitalHealthAttribute);
        ServerPlayerEvents.COPY_FROM.register(LivingEntityDeathEvents::retainInventoryWhenPledged);
    }

    /**
     * Copies the max vital health attribute from the vital thurible as long as the damage source was not from ympe
     */
    private static void retainVitalHealthAttribute(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if (!alive && (oldPlayer.getRecentDamageSource() == null || !AylythUtil.isSourceYmpe(oldPlayer.getRecentDamageSource()))) {
            EntityAttributeInstance oldInstance = oldPlayer.getAttributeInstance(ModEntityAttributes.MAX_VITAL_HEALTH);
            EntityAttributeInstance newInstance = newPlayer.getAttributeInstance(ModEntityAttributes.MAX_VITAL_HEALTH);
            if (oldInstance != null && newInstance != null && oldInstance.getModifier(VitalThuribleBlock.MAX_VITAL_MODIFIER) != null) {
                newInstance.addPersistentModifier(oldInstance.getModifier(VitalThuribleBlock.MAX_VITAL_MODIFIER));
            }
        }
    }

    private static void retainInventoryWhenPledged(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if (alive || oldPlayer.getRecentDamageSource() == null || !oldPlayer.getRecentDamageSource().isOf(AylythDamageTypes.YMPE)) {
            return;
        }

        if (oldPlayer.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            return;
        }

        HindPledgeHolder.of(oldPlayer).ifPresent(hind -> {
            if (hind.getHindUuid() != null) {
                newPlayer.getInventory().clone(oldPlayer.getInventory());
            }
            hind.setHindUuid(null);
        });
    }

    private static void spawnRippedSoul(LivingEntity livingEntity, DamageSource source) {
        World world = livingEntity.getWorld();
        if(!world.isClient) {
            if(source.isOf(AylythDamageTypes.SOUL_RIP)) {
                RippedSoulEntity soul = new RippedSoulEntity(ModEntityTypes.RIPPED_SOUL, world);
                if (source.getAttacker() != null) {
                    soul.setOwner((PlayerEntity) source.getAttacker());
                    soul.setPosition(livingEntity.getPos().add(0, 1, 0));
                    world.spawnEntity(soul);
                }
            }else if((source.getAttacker() != null && source.getAttacker() instanceof PlayerEntity playerEntity && playerEntity.getMainHandStack().isOf(ModItems.YMPE_GLAIVE))){
                RippedSoulEntity soul = new RippedSoulEntity(ModEntityTypes.RIPPED_SOUL, world);
                soul.setOwner(playerEntity);
                soul.setPosition(playerEntity.getPos().add(0, 1, 0));
                world.spawnEntity(soul);
            }
        }
    }

    private static boolean allowDeath(LivingEntity livingEntity, DamageSource damageSource, float damageAmount) {
        if(livingEntity instanceof ServerPlayerEntity player){
            if (damageSource.isOf(DamageTypes.OUT_OF_WORLD)) {
                return true;
            }
            if (damageSource.isOf(AylythDamageTypes.YMPE) && ((HindPledgeHolder)player).getHindUuid() == null) {
                ScionEntity.summonPlayerScion(player);
                WoodyGrowthCacheBlock.spawnInventory(player.getWorld(), player.getBlockPos(), player);
                return true;
            }
            RegistryKey<World> toWorld = null;
            if (player.getWorld().getRegistryKey() != AylythDimensionData.AYLYTH) {
                boolean teleport = false;
                float chance = switch (player.getWorld().getDifficulty()) {
                    case PEACEFUL -> 0;
                    case EASY -> 0.1f;
                    case NORMAL -> 0.2f;
                    case HARD -> 0.3f;
                };
                // TODO: take another look at this later if people complain abt this not working with an advancement removal mod
                if (player.getRandom().nextFloat() <= chance && canPlayerDieIntoAylyth(player)) {
                    if (damageSource.getAttacker() instanceof WitchEntity) {
                        teleport = true;
                    }
                    if (!teleport) {
                        RegistryEntry<Biome> biome = player.getWorld().getBiome(player.getBlockPos());
                        if (biome.isIn(BiomeTags.IS_TAIGA) || biome.isIn(BiomeTags.IS_FOREST)) {
                            if (damageSource.isIn(DamageTypeTags.IS_FALL) || damageSource.isIn(DamageTypeTags.IS_DROWNING)) {
                                teleport = true;
                            }
                        }
                    }
                    teleport |= AylythUtil.isNearSeep(player.getServerWorld(), player, 8);
                }
                if (!teleport) {
                    for (Hand hand : Hand.values()) {
                        ItemStack stack = player.getStackInHand(hand);
                        if (stack.isOf(ModItems.AYLYTHIAN_HEART)) {
                            teleport = true;
                            AylythUtil.decreaseStack(stack, player);
                            break;
                        }
                    }
                }
                if (teleport) {
                    toWorld = AylythDimensionData.AYLYTH;
                }
            }
            if (toWorld != null) {
                AylythUtil.teleportTo(toWorld, player, 0);
                player.setHealth(player.getMaxHealth() / 2);
                player.clearStatusEffects();
                player.extinguish();
                player.setFrozenTicks(0);
                player.setVelocity(Vec3d.ZERO);
                player.fallDistance = 0;
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200));
                return false;
            }
            return true;
        }
        return true;
    }

    private static boolean canPlayerDieIntoAylyth(ServerPlayerEntity player) {
        Advancement advancement = player.server.getAdvancementLoader().get(new Identifier("nether/root"));
        if (player.getAdvancementTracker().getProgress(advancement).isDone()) {
            return true;
        }
        advancement = player.server.getAdvancementLoader().get(AylythUtil.id("aylyth/root"));
        return player.getAdvancementTracker().getProgress(advancement).isDone();
    }
}
