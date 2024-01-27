package moriyashiine.aylyth.common.component.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import moriyashiine.aylyth.common.registry.ModComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class YmpeThornsComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;
	private int thornProgress = 0, timer = 0;

	public YmpeThornsComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {
		EntityAttributeInstance speedInst = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		EntityAttributeModifier speedMod = new EntityAttributeModifier(UUID.fromString("87a6f60c-573c-4eef-8e3b-f0f7bec57c2d"), "Thorns modifier", -0.1 * thornProgress, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

		if(speedInst != null && speedInst.hasModifier(speedMod))
			speedInst.removeModifier(speedMod);

		if(thornProgress > 0) {
			if(speedInst != null && !speedInst.hasModifier(speedMod))
				speedInst.addTemporaryModifier(speedMod);

			if(timer > 0 && timer % 60 == 0)
				setThornProgress(getThornProgress() - 1);
		}

		if(thornProgress > 0)
			timer++;
		else
			timer = 0;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		thornProgress = tag.getInt("YmpeThornProgress");
		timer = tag.getInt("YmpeThornTimer");
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putInt("YmpeThornProgress", thornProgress);
		tag.putInt("YmpeThornTimer", timer);
	}

	public int getThornProgress() {
		return thornProgress;
	}

	public void setThornProgress(int progress) {
		if(progress > thornProgress)
			timer = 0;

		thornProgress = progress;
		ModComponents.YMPE_THORNS.sync(entity);
	}

	public void incrementThornProgress(int progress) {
		setThornProgress(getThornProgress() + progress);
	}
}
