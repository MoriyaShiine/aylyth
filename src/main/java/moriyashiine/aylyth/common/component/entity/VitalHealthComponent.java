package moriyashiine.aylyth.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.registry.ModEntityComponents;
import moriyashiine.aylyth.common.registry.ModEntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

public class VitalHealthComponent implements AutoSyncedComponent, VitalHealthHolder {
    private float vitalLevel;
    private PlayerEntity obj;

    public VitalHealthComponent(PlayerEntity player) {
        this.obj = player;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        setCurrentVitalHealth(tag.getFloat("vital_level"));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putFloat("vital_level", vitalLevel);
    }

    @Override
    public boolean shouldSyncWith(ServerPlayerEntity player) {
        return obj == player;
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeFloat(vitalLevel);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        vitalLevel = buf.readFloat();
    }

    @Override
    public float getCurrentVitalHealth() {
        return vitalLevel;
    }

    @Override
    public void setCurrentVitalHealth(float vital) {
        if (vital == vitalLevel) {
            return;
        }
        this.vitalLevel = (float) MathHelper.clamp(vital, 0, obj.getAttributeValue(ModEntityAttributes.MAX_VITAL_HEALTH));
        ModEntityComponents.VITAL_HEALTH.sync(obj);
    }
}
