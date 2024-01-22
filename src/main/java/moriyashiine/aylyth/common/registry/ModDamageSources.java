package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class ModDamageSources {
	private final Registry<DamageType> damageTypeRegistry;
	public static final RegistryKey<DamageType> YMPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, AylythUtil.id("ympe"));
	public static final RegistryKey<DamageType> YMPE_ENTITY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, AylythUtil.id("ympe_entity"));
	public static final RegistryKey<DamageType> UNBLOCKABLE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, AylythUtil.id("unblockable"));
	public static final RegistryKey<DamageType> SOUL_RIP = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, AylythUtil.id("soul_rip"));

	public ModDamageSources(DynamicRegistryManager dynamicRegistryManager) {
		damageTypeRegistry = dynamicRegistryManager.get(RegistryKeys.DAMAGE_TYPE);
	}

	public DamageSource ympe() {
		return new DamageSource(damageTypeRegistry.entryOf(YMPE));
	}

	public DamageSource ympeEntity() {
		return new DamageSource(damageTypeRegistry.entryOf(YMPE_ENTITY));
	}

	public DamageSource unblockable() {
		return new DamageSource(damageTypeRegistry.entryOf(UNBLOCKABLE));
	}

	public DamageSource soulRip(PlayerEntity player) {
		return new DamageSource(damageTypeRegistry.entryOf(SOUL_RIP), player);
	}

	/*
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
				System.out.println("isFire");
				return amount;
			} else if(bl){
				System.out.println("isMagic");
				return amount;
			} else {
				while (component.getStage() > 0) {
					System.out.println("while");
					amount--;
					component.setStage(component.getStage() - 1);
					player.world.playSoundFromEntity(null, player, ModSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE, SoundCategory.PLAYERS, 1, player.getSoundPitch());
				}
				return amount;
			}
		}
		return amount;
	}

	 */
}
