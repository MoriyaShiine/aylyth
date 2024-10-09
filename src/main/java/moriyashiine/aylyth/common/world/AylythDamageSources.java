package moriyashiine.aylyth.common.world;

import moriyashiine.aylyth.common.data.AylythDamageTypes;
import moriyashiine.aylyth.common.entity.types.mob.WreathedHindEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.Nullable;

public class AylythDamageSources {
	private final Registry<DamageType> damageTypeRegistry;
	private final DamageSource ympe;
	private final DamageSource shucking;

	public AylythDamageSources(DynamicRegistryManager dynamicRegistryManager) {
		damageTypeRegistry = dynamicRegistryManager.get(RegistryKeys.DAMAGE_TYPE);
		this.ympe = new DamageSource(damageTypeRegistry.entryOf(AylythDamageTypes.YMPE));
		this.shucking = new DamageSource(damageTypeRegistry.entryOf(AylythDamageTypes.SHUCKING));
	}

	public DamageSource ympe() {
		return ympe;
	}

	public DamageSource shucking() {
		return shucking;
	}

	public DamageSource ympeEntity(Entity entity) {
		return new DamageSource(damageTypeRegistry.entryOf(AylythDamageTypes.YMPE_ENTITY), entity);
	}

	public DamageSource killingBlow(WreathedHindEntity wreathedHindEntity) {
		return new DamageSource(damageTypeRegistry.entryOf(AylythDamageTypes.KILLING_BLOW), wreathedHindEntity);
	}

	public DamageSource soulRip(PlayerEntity player) {
		return new DamageSource(damageTypeRegistry.entryOf(AylythDamageTypes.SOUL_RIP), player);
	}

	public DamageSource blight(@Nullable PlayerEntity player) {
		return new DamageSource(damageTypeRegistry.entryOf(AylythDamageTypes.BLIGHT), player);
	}
}
