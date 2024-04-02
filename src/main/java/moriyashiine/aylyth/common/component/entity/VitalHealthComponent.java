package moriyashiine.aylyth.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import moriyashiine.aylyth.api.interfaces.VitalHolder;
import moriyashiine.aylyth.common.registry.ModComponents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

import java.util.UUID;

public class VitalHealthComponent implements AutoSyncedComponent, VitalHolder {
    public static final UUID MODIFIER_UUID = UUID.fromString("1ee98b0b-7181-46ac-97ce-d8f7307bffb1");
    private int vitalLevel;
    private PlayerEntity obj;

    public VitalHealthComponent(PlayerEntity player) {
        this.obj = player;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        setVitalThuribleLevel(tag.getInt("vital_level"));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("vital_level", vitalLevel);
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return obj == player;
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeByte(vitalLevel);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        vitalLevel = buf.readByte();
    }

    @Override
    public int getVitalThuribleLevel() {
        return vitalLevel;
    }

    @Override
    public void setVitalThuribleLevel(int vital) {
        if (vital == vitalLevel) {
            return;
        }
        // arbitrary limitation because idk
        this.vitalLevel = MathHelper.clamp(vital, 0, 10);
        updateHealthAttribute();
        ModComponents.VITAL_HEALTH.sync(obj);
    }

    private void updateHealthAttribute() {
        EntityAttributeInstance instance = obj.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (instance != null) {
            double healthBoost = 2 * vitalLevel;
            instance.removeModifier(MODIFIER_UUID);
            if (healthBoost != 0) {
                instance.addPersistentModifier(new EntityAttributeModifier(MODIFIER_UUID, "Vital Health", healthBoost, EntityAttributeModifier.Operation.ADDITION));
            }
        }
    }
}
