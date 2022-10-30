package moriyashiine.aylyth.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import moriyashiine.aylyth.common.registry.ModComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class RiderComponent implements AutoSyncedComponent {
    private final PlayerEntity entity;
    private boolean pressingUp = false;
    private boolean pressingDown = false;

    public RiderComponent(PlayerEntity entity) {
        this.entity = entity;
    }

    public void readFromNbt(NbtCompound tag) {
        this.setPressingUp(tag.getBoolean("PressingUp"));
        this.setPressingDown(tag.getBoolean("PressingDown"));
    }

    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("PressingUp", this.isPressingUp());
        tag.putBoolean("PressingDown", this.isPressingDown());
    }

    public boolean isPressingUp() {
        return this.pressingUp;
    }

    public boolean isPressingDown() {
        return this.pressingDown;
    }

    public void setPressingUp(boolean pressingUp) {
        boolean pressing = pressingUp == this.pressingUp;
        this.pressingUp = pressingUp;
        if (pressing) {
            ModComponents.RIDER_COMPONENT.sync(this.entity);
        }

    }

    public void setPressingDown(boolean pressingDown) {
        boolean pressing = pressingDown == this.pressingDown;
        this.pressingDown = pressingDown;
        if (pressing) {
            ModComponents.RIDER_COMPONENT.sync(this.entity);
        }

    }
}