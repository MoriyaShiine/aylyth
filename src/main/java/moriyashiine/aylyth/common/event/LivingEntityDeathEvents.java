package moriyashiine.aylyth.common.event;

import moriyashiine.aylyth.api.interfaces.ExtraPlayerData;
import moriyashiine.aylyth.api.interfaces.HindPledgeHolder;
import moriyashiine.aylyth.api.interfaces.VitalHolder;
import moriyashiine.aylyth.common.block.WoodyGrowthCacheBlock;
import moriyashiine.aylyth.common.entity.mob.RippedSoulEntity;
import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.key.ModDamageTypeKeys;
import moriyashiine.aylyth.common.registry.key.ModDimensionKeys;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
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
    private static Identifier lateEvent = AylythUtil.id("late");

    public static void init(){
        ServerLivingEntityEvents.ALLOW_DEATH.addPhaseOrdering(Event.DEFAULT_PHASE, lateEvent);

        ServerLivingEntityEvents.ALLOW_DEATH.register(lateEvent, LivingEntityDeathEvents::hindKeepInv);
        ServerLivingEntityEvents.ALLOW_DEATH.register(LivingEntityDeathEvents::allowDeath);

        ServerLivingEntityEvents.AFTER_DEATH.register(LivingEntityDeathEvents::checkVital);
        ServerLivingEntityEvents.AFTER_DEATH.register(LivingEntityDeathEvents::spawnRippedSoul);

        ServerPlayerEvents.AFTER_RESPAWN.register(LivingEntityDeathEvents::afterRespawn);
        ServerPlayerEvents.AFTER_RESPAWN.register(LivingEntityDeathEvents::restoreInv);
    }

    /**
     * If the player has stored extra data {@link ExtraPlayerData} of the old players inventory it will restore
     * it to the new player and then clear the extra data. Clears Hind pledge after
     * @param oldPlayer the player who died
     * @param newPlayer the player who spawned
     * @param alive if player was alive on respawn
     */
    private static void restoreInv(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if(!alive){
            NbtCompound playerData = AylythUtil.getPlayerData(newPlayer);
            if (!newPlayer.getWorld().isClient() && playerData.contains("RestoreInv")) {
                NbtList nbtList = playerData.getList("RestoreInv", 10);
                AylythUtil.loadInv(nbtList, newPlayer.getInventory());
                AylythUtil.getPlayerData(newPlayer).getList("RestoreInv", 10).clear();
                AylythUtil.getPlayerData(newPlayer).remove("RestoreInv");
            }
            ((HindPledgeHolder) newPlayer).setHindUuid(null);
        }
    }

    /**
     * Checks if the player is pledged and died by the Ympe damage source and if that's the case store the
     * players inventory in {@link ExtraPlayerData} to be restored upon respawn
     * @param livingEntity entity which died
     * @param damageSource source of damage
     * @param damageAmount amount of damage
     * @return true if the entity should die
     */
    private static boolean hindKeepInv(LivingEntity livingEntity, DamageSource damageSource, float damageAmount) {
        if(livingEntity instanceof PlayerEntity player){
            if (player.getWorld().isClient() || player.isCreative() || player.isSpectator()) {
                return true;
            }

            if(!player.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)){
                HindPledgeHolder.of(player).ifPresent(hind -> {
                    if(damageSource.isOf(ModDamageTypeKeys.YMPE) && hind.getHindUuid() != null){
                        PlayerInventory newInv = new PlayerInventory(null);
                        NbtList nbtList = new NbtList();

                        AylythUtil.transferList(newInv.armor, player.getInventory().armor);
                        AylythUtil.transferList(newInv.offHand, player.getInventory().offHand);
                        AylythUtil.transferList(newInv.main, player.getInventory().main);
                        if(!newInv.isEmpty()){
                            newInv.writeNbt(nbtList);
                            AylythUtil.getPlayerData(player).put("RestoreInv", nbtList);
                        }
                    }
                });
            }
        }
        return true;
    }

    /**
     * Resets extra health after death if the source of death was {@link AylythUtil#isSourceYmpe}
     * @param livingEntity the entity who maybe has vital
     * @param source the damage source
     */
    private static void checkVital(LivingEntity livingEntity, DamageSource source) {
        if(livingEntity instanceof PlayerEntity player && AylythUtil.isSourceYmpe(source)){
            VitalHolder.of(player).ifPresent(vital -> vital.setVitalThuribleLevel(0));
        }
    }

    private static void spawnRippedSoul(LivingEntity livingEntity, DamageSource source) {
        World world = livingEntity.getWorld();
        if(!world.isClient) {
            if(source.isOf(ModDamageTypeKeys.SOUL_RIP)) {
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

    private static void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if (oldPlayer.getWorld().getRegistryKey().equals(ModDimensionKeys.AYLYTH)) {
            AylythUtil.teleportTo(ModDimensionKeys.AYLYTH, newPlayer, 0);
        }
        VitalHolder.of(newPlayer).ifPresent(vital -> vital.setVitalThuribleLevel(((VitalHolder) oldPlayer).getVitalThuribleLevel()));
    }

    private static boolean allowDeath(LivingEntity livingEntity, DamageSource damageSource, float damageAmount) {
        if(livingEntity instanceof ServerPlayerEntity player){
            if (damageSource.isOf(DamageTypes.OUT_OF_WORLD)) {
                return true;
            }
            if (damageSource.isOf(ModDamageTypeKeys.YMPE) && ((HindPledgeHolder)player).getHindUuid() == null) {
                ScionEntity.summonPlayerScion(player);
                WoodyGrowthCacheBlock.spawnInventory(player.getWorld(), player.getBlockPos(), player);
                return true;
            }
            RegistryKey<World> toWorld = null;
            if (player.getWorld().getRegistryKey() != ModDimensionKeys.AYLYTH) {
                boolean teleport = false;
                float chance = switch (player.getWorld().getDifficulty()) {
                    case PEACEFUL -> 0;
                    case EASY -> 0.1f;
                    case NORMAL -> 0.2f;
                    case HARD -> 0.3f;
                };
                // TODO: take another look at this later if people complain abt this not working with an advancement removal mod
                Advancement netherRoot = player.server.getAdvancementLoader().get(new Identifier("nether/root"));
                if (player.getRandom().nextFloat() <= chance && player.getAdvancementTracker().getProgress(netherRoot).isDone()) {
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
                    teleport |= AylythUtil.isNearSeep(player, 8);
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
                    toWorld = ModDimensionKeys.AYLYTH;
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
}
