package moriyashiine.aylyth.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import moriyashiine.aylyth.common.component.entity.*;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;

public class ModEntityComponents implements EntityComponentInitializer {
	public static final ComponentKey<YmpeThornsComponent> YMPE_THORNS = ComponentRegistry.getOrCreate(AylythUtil.id("ympe_thorns"), YmpeThornsComponent.class);
	public static final ComponentKey<PreventDropsComponent> PREVENT_DROPS = ComponentRegistry.getOrCreate(AylythUtil.id("prevent_drops"), PreventDropsComponent.class);

	public static final ComponentKey<YmpeInfestationComponent> YMPE_INFESTATION = ComponentRegistry.getOrCreate(AylythUtil.id("ympe_infestation"), YmpeInfestationComponent.class);
	public static final ComponentKey<RiderComponent> RIDER_COMPONENT = ComponentRegistry.getOrCreate(AylythUtil.id("rider"), RiderComponent.class);
	public static final ComponentKey<CuirassComponent> CUIRASS_COMPONENT = ComponentRegistry.getOrCreate(AylythUtil.id("cuirass"), CuirassComponent.class);
	public static final ComponentKey<VitalHealthComponent> VITAL_HEALTH = ComponentRegistry.getOrCreate(AylythUtil.id("vital_health"), VitalHealthComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(LivingEntity.class, YMPE_THORNS).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(YmpeThornsComponent::new);
		registry.registerFor(MobEntity.class, PREVENT_DROPS, entity -> new PreventDropsComponent());
		registry.registerForPlayers(YMPE_INFESTATION, YmpeInfestationComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerForPlayers(VITAL_HEALTH, VitalHealthComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
		registry.registerForPlayers(RIDER_COMPONENT, RiderComponent::new, RespawnCopyStrategy.NEVER_COPY);
		registry.registerForPlayers(CUIRASS_COMPONENT, CuirassComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
