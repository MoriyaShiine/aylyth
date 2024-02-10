package moriyashiine.aylyth.common.world;

import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.registry.key.ModDamageTypeKeys;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.Nullable;

public class ModDamageSources {
	private final Registry<DamageType> damageTypeRegistry;

	public ModDamageSources(DynamicRegistryManager dynamicRegistryManager) {
		damageTypeRegistry = dynamicRegistryManager.get(RegistryKeys.DAMAGE_TYPE);
	}

	public DamageSource ympe() {
		return new DamageSource(damageTypeRegistry.entryOf(ModDamageTypeKeys.YMPE));
	}

	public DamageSource ympeEntity(Entity entity) {
		return new DamageSource(damageTypeRegistry.entryOf(ModDamageTypeKeys.YMPE_ENTITY), entity);
	}

	public DamageSource killingBlow(WreathedHindEntity wreathedHindEntity) {
		return new DamageSource(damageTypeRegistry.entryOf(ModDamageTypeKeys.KILLING_BLOW), wreathedHindEntity);
	}

	public DamageSource soulRip(PlayerEntity player) {
		return new DamageSource(damageTypeRegistry.entryOf(ModDamageTypeKeys.SOUL_RIP), player);
	}

	public DamageSource blight(@Nullable PlayerEntity player) {
		return new DamageSource(damageTypeRegistry.entryOf(ModDamageTypeKeys.BLIGHT), player);
	}
}
