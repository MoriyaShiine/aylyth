package moriyashiine.aylyth.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import moriyashiine.aylyth.common.registry.ModEntityComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class CuirassComponent implements AutoSyncedComponent {
    //Under the hood value but in reality should be 5 stages of growth times 2 for each heart and 2 again for halved damage thingy
    public static final float MAX_STAGE = 20;
    private float stage = 0;
    private int stageTimer = 0;
    private final PlayerEntity player;

    public CuirassComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        setStage(tag.getByte("Stage"));
        setStageTimer(tag.getShort("StageTimer"));
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putFloat("Stage", getStage());
        tag.putInt("StageTimer", getStageTimer());
    }

    public float getStage() {
        return stage;
    }

    public void setStage(float stage) {
        this.stage = stage;
        ModEntityComponents.CUIRASS_COMPONENT.sync(player);
    }

    public int getStageTimer() {
        return stageTimer;
    }

    public void setStageTimer(int stageTimer) {
        this.stageTimer = stageTimer;
        ModEntityComponents.CUIRASS_COMPONENT.sync(player);
    }
}
