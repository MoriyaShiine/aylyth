package moriyashiine.aylyth.common.entity.mob;

import com.mojang.serialization.Dynamic;
import moriyashiine.aylyth.common.entity.ai.brain.ScionBrain;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class ScionEntity extends HostileEntity {
    private static final TrackedData<Optional<UUID>> PLAYER_SKIN_UUID = DataTracker.registerData(ScionEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    public ScionEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @org.jetbrains.annotations.Nullable EntityData entityData, @org.jetbrains.annotations.Nullable NbtCompound entityNbt) {
        ScionBrain.setCurrentPosAsHome(this);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(PLAYER_SKIN_UUID, Optional.empty());
    }

    @Override
    protected void mobTick() {
        this.world.getProfiler().push("scionBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().pop();
        ScionBrain.updateActivities(this);
        super.mobTick();
    }


    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getStoredPlayerUUID() != null) {
            nbt.putUuid("uuid", this.getStoredPlayerUUID());
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        UUID uUID;
        if (nbt.containsUuid("uuid")) {
            uUID = nbt.getUuid("uuid");
        } else {
            String string = nbt.getString("uuid");
            uUID = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }

        if (uUID != null) {
            this.setStoredPlayerUUID(uUID);
        }
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return ScionBrain.create(this, dynamic);
    }

    @SuppressWarnings("All")
    @Override
    public Brain<ScionEntity> getBrain() {
        return (Brain<ScionEntity>) super.getBrain();
    }

    public boolean isEnemy(LivingEntity entity) {//TODO
        return this.world == entity.world
                && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(entity)
                && !this.isTeammate(entity)
                && entity.getType() != EntityType.ARMOR_STAND
                && !entity.isInvulnerable()
                && !entity.isDead()
                && this.world.getWorldBorder().contains(entity.getBoundingBox());
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3)
                .add(EntityAttributes.GENERIC_ARMOR, 2)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    public UUID getStoredPlayerUUID() {
        return this.dataTracker.get(PLAYER_SKIN_UUID).orElse(null);
    }


    public void setStoredPlayerUUID(@Nullable UUID uuid) {
        this.dataTracker.set(PLAYER_SKIN_UUID, Optional.ofNullable(uuid));
    }

    public static void summonPlayerScion(PlayerEntity playerEntity) {
        ScionEntity scionEntity = ModEntityTypes.SCION.create(playerEntity.world);
        if (scionEntity != null) {
            scionEntity.setStoredPlayerUUID(playerEntity.getUuid());
            Iterable<ItemStack> armorItems = playerEntity.getArmorItems();

            armorItems.forEach(stack -> {
                EquipmentSlot equipmentSlot = getPreferredEquipmentSlot(stack);
                scionEntity.equipStack(equipmentSlot, stack);
                playerEntity.equipStack(equipmentSlot, ItemStack.EMPTY);
            });

            scionEntity.copyPositionAndRotation(playerEntity);
            scionEntity.limbDistance = playerEntity.limbDistance;
            scionEntity.lastLimbDistance = playerEntity.lastLimbDistance;
            scionEntity.headYaw = playerEntity.headYaw;
            scionEntity.refreshPositionAndAngles(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), playerEntity.getYaw(), playerEntity.getPitch());
            scionEntity.setCustomName(playerEntity.getCustomName());
            scionEntity.setCustomNameVisible(playerEntity.isCustomNameVisible());
            scionEntity.handDropChances[EquipmentSlot.MAINHAND.getEntitySlotId()] = 100.0F;
            scionEntity.handDropChances[EquipmentSlot.OFFHAND.getEntitySlotId()] = 100.0F;
            scionEntity.armorDropChances[EquipmentSlot.HEAD.getEntitySlotId()] = 100.0F;
            scionEntity.armorDropChances[EquipmentSlot.CHEST.getEntitySlotId()] = 100.0F;
            scionEntity.armorDropChances[EquipmentSlot.LEGS.getEntitySlotId()] = 100.0F;
            scionEntity.armorDropChances[EquipmentSlot.FEET.getEntitySlotId()] = 100.0F;
            scionEntity.setPersistent();

            playerEntity.world.spawnEntity(scionEntity);
        }
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public boolean isUndead() {
        return true;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.ENTITY_SCION_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.ENTITY_SCION_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.ENTITY_SCION_DEATH;
    }

    public static boolean canSpawn(EntityType<ScionEntity> scionEntityEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return canMobSpawn(scionEntityEntityType, serverWorldAccess, spawnReason, blockPos, random) && serverWorldAccess.getDifficulty() != Difficulty.PEACEFUL && random.nextBoolean();
    }


}
