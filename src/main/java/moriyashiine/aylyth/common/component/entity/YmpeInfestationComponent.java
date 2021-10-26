package moriyashiine.aylyth.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import moriyashiine.aylyth.common.registry.ModComponents;
import moriyashiine.aylyth.common.registry.ModDamageSources;
import moriyashiine.aylyth.common.registry.ModDimensions;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;

public class YmpeInfestationComponent implements AutoSyncedComponent, ServerTickingComponent {
	public static final short TIME_UNTIL_STAGE_INCREASES = 2400;
	
	private final PlayerEntity obj;
	private byte stage = 0;
	private short infestationTimer = 0;
	
	public YmpeInfestationComponent(PlayerEntity obj) {
		this.obj = obj;
	}
	
	@Override
	public void readFromNbt(NbtCompound tag) {
		setStage(tag.getByte("Stage"));
		setInfestationTimer(tag.getShort("InfestationTimer"));
	}
	
	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putByte("Stage", getStage());
		tag.putShort("InfestationTimer", getInfestationTimer());
	}
	
	@Override
	public void serverTick() {
		if (obj.isDead() || !((ServerPlayerEntity) obj).interactionManager.getGameMode().isSurvivalLike()) {
			return;
		}
		if (obj.world.getRegistryKey() == ModDimensions.AYLYTH) {
			setInfestationTimer((short) (getInfestationTimer() + 1));
		}
		else {
			if (getStage() > 0 && getInfestationTimer() <= 0) {
				setStage((byte) (getStage() - 1));
				setInfestationTimer(TIME_UNTIL_STAGE_INCREASES);
			}
			if (getInfestationTimer() > 0) {
				setInfestationTimer((short) Math.max(0, getInfestationTimer() - TIME_UNTIL_STAGE_INCREASES / 20));
			}
		}
		if (getInfestationTimer() >= TIME_UNTIL_STAGE_INCREASES) {
			obj.world.playSoundFromEntity(null, obj, ModSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE, SoundCategory.PLAYERS, 1, obj.getSoundPitch());
			setStage((byte) (getStage() + 1));
			setInfestationTimer((short) 0);
			if (getStage() >= 6) {
				obj.damage(ModDamageSources.YMPE, Float.MAX_VALUE);
			}
		}
	}
	
	public byte getStage() {
		return stage;
	}
	
	public void setStage(byte stage) {
		this.stage = stage;
		ModComponents.YMPE_INFESTATION.sync(obj);
	}
	
	public short getInfestationTimer() {
		return infestationTimer;
	}
	
	public void setInfestationTimer(short infestationTimer) {
		this.infestationTimer = infestationTimer;
		ModComponents.YMPE_INFESTATION.sync(obj);
	}
}
