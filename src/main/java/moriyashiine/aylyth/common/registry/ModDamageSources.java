package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.component.entity.CuirassComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class ModDamageSources {
	public static final DamageSource YMPE = new YmpeDamageSource("ympe");
	public static final DamageSource YMPE_ENTITY = new YmpeEntityDamageSource("ympe_entity");
	public static final DamageSource UNBLOCKABLE = new DamageSource("unblockable").setBypassesArmor().setUnblockable();

	public static float handleDamage(LivingEntity livingEntity, DamageSource source, float amount) {
		if(livingEntity instanceof PlayerEntity player){
			CuirassComponent component = ModComponents.CUIRASS_COMPONENT.get(player);
			boolean bl = source.isMagic() || source.isFromFalling() || source.isOutOfWorld();
			boolean bl2 = source.getAttacker() != null && source.getAttacker() instanceof LivingEntity livingEntity1 && livingEntity1.getMainHandStack().getItem() instanceof AxeItem;
			boolean bl3 = source.isFire();
			if(bl2 || bl3){
				component.setStage(0);
				component.setStageTimer(0);
				player.world.playSoundFromEntity(null, player, ModSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE, SoundCategory.PLAYERS, 1, player.getSoundPitch());
				return amount;
			} else if(bl){
				return amount;
			} else{
				while (component.getStage() > 0) {
					amount--;
					component.setStage(component.getStage() - 1);
					player.world.playSoundFromEntity(null, player, ModSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE, SoundCategory.PLAYERS, 1, player.getSoundPitch());
				}
			}
		}
		return amount;
	}

	private static class YmpeDamageSource extends DamageSource {
		public YmpeDamageSource(String name) {
			super(name);
			setBypassesArmor();
			setOutOfWorld();
		}
	}

	private static class YmpeEntityDamageSource extends DamageSource {
		public YmpeEntityDamageSource(String name) {
			super(name);
		}
	}

	public static class SoulRipDamageSource extends DamageSource {
		protected final Entity source;

		public static DamageSource playerRip(PlayerEntity attacker) {
			return (new SoulRipDamageSource("soul_rip", attacker)).setUsesMagic();
		}

		public SoulRipDamageSource(String name, PlayerEntity source) {
			super(name);
			this.source = source;
		}

		@Override
		public Entity getAttacker() {
			return this.source;
		}

		@Override
		public boolean isScaledWithDifficulty() {
			return this.source instanceof LivingEntity && !(this.source instanceof PlayerEntity);
		}

		@Override
		public @Nullable Vec3d getPosition() {
			return this.source.getPos();
		}

		@Override
		public String toString() {
			return "EntityDamageSource (" + this.source + ")";
		}
	}
}
