package moriyashiine.aylyth.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import moriyashiine.aylyth.common.registry.ModComponents;
import moriyashiine.aylyth.common.registry.ModDamageSources;
import moriyashiine.aylyth.common.registry.ModDimensions;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;

public class YmpeInfestationComponent implements AutoSyncedComponent, ServerTickingComponent {
	private static final int TIME_UNTIL_STAGE_INCREASES = 6000;
	
	private final PlayerEntity obj;
	private int stage = 0;
	private int infestationTimer = 0;
	
	public YmpeInfestationComponent(PlayerEntity obj) {
		this.obj = obj;
	}
	
	@Override
	public void readFromNbt(NbtCompound tag) {
		setStage(tag.getInt("Stage"));
		setInfestationTimer(tag.getInt("InfestationTimer"));
	}
	
	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("Stage", getStage());
		tag.putInt("InfestationTimer", getInfestationTimer());
	}
	
	@Override
	public void serverTick() {
		if (obj.isDead()) {
			return;
		}
		if (obj.world.getRegistryKey() == ModDimensions.AYLYTH) {
			setInfestationTimer(getInfestationTimer() + 1);
		}
		else if (obj.age % 20 == 0) {
			if (getStage() > 0) {
				setStage(getStage() - 1);
			}
			if (getInfestationTimer() > 0) {
				setInfestationTimer(0);
			}
		}
		if (getInfestationTimer() >= TIME_UNTIL_STAGE_INCREASES) {
			obj.world.playSoundFromEntity(null, obj, ModSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE, SoundCategory.PLAYERS, 1, obj.getSoundPitch());
			setStage(getStage() + 1);
			setInfestationTimer(Math.max(0, getInfestationTimer() - TIME_UNTIL_STAGE_INCREASES));
			if (getStage() >= 6) {
				obj.damage(ModDamageSources.YMPE, Float.MAX_VALUE);
			}
		}
	}
	
	public int getStage() {
		return stage;
	}
	
	public void setStage(int stage) {
		this.stage = stage;
		ModComponents.YMPE_INFESTATION.sync(obj);
	}
	
	public int getInfestationTimer() {
		return infestationTimer;
	}
	
	public void setInfestationTimer(int infestationTimer) {
		this.infestationTimer = infestationTimer;
	}
}
