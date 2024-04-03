package moriyashiine.aylyth.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import moriyashiine.aylyth.common.registry.ModEntityComponents;
import moriyashiine.aylyth.common.registry.ModCriteria;
import moriyashiine.aylyth.common.registry.ModSoundEvents;
import moriyashiine.aylyth.common.registry.key.ModDimensionKeys;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
		if (obj.getWorld().getRegistryKey() == ModDimensionKeys.AYLYTH) {
			setInfestationTimer((short) (getInfestationTimer() + 1));
		} else {
			if (getStage() > 0 && getInfestationTimer() <= 0) {
				setStage((byte) (getStage() - 1));
				setInfestationTimer(TIME_UNTIL_STAGE_INCREASES);
			}
			if (getInfestationTimer() > 0) {
				setInfestationTimer((short) Math.max(0, getInfestationTimer() - TIME_UNTIL_STAGE_INCREASES / 20));
			}
		}
		if (getInfestationTimer() >= TIME_UNTIL_STAGE_INCREASES) {
			obj.getWorld().playSoundFromEntity(null, obj, ModSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE.value(), SoundCategory.PLAYERS, 1, obj.getSoundPitch());
			setStage((byte) (getStage() + 1));
			setInfestationTimer((short) 0);
			if (getStage() >= 6) {
				obj.damage(obj.getWorld().modDamageSources().ympe(), Float.MAX_VALUE);
			}
		}
		if(getInfestationTimer() % 20 == 0){
			switch (getStage()){
				case 2 -> obj.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 4));
				case 3 -> applyYmpeEffects(obj, 0,0);
				case 4 -> applyYmpeEffects(obj, 1,0);
				case 5 -> applyYmpeEffects(obj, 2, 1);
				default -> {}
			}
		}
	}

	private void applyYmpeEffects(PlayerEntity player, int slowAmplifier, int mineAmplifier){
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 4, slowAmplifier));
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20 * 4, mineAmplifier));
	}
	
	public byte getStage() {
		return stage;
	}
	
	public void setStage(byte stage) {
		this.stage = stage;
		ModEntityComponents.YMPE_INFESTATION.sync(obj);
		if (obj instanceof ServerPlayerEntity serverPlayer) {
			ModCriteria.YMPE_INFESTATION.trigger(serverPlayer);
		}
	}
	
	public short getInfestationTimer() {
		return infestationTimer;
	}
	
	public void setInfestationTimer(short infestationTimer) {
		this.infestationTimer = infestationTimer;
		ModEntityComponents.YMPE_INFESTATION.sync(obj);
	}
}
