package moriyashiine.aylyth.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.component.entity.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class ModComponents implements EntityComponentInitializer {
	public static final ComponentKey<YmpeThornsComponent> YMPE_THORNS = ComponentRegistry.getOrCreate(new Identifier(Aylyth.MOD_ID, "ympe_thorns"), YmpeThornsComponent.class);
	public static final ComponentKey<YmpeInfestationComponent> YMPE_INFESTATION = ComponentRegistry.getOrCreate(new Identifier(Aylyth.MOD_ID, "ympe_infestation"), YmpeInfestationComponent.class);
	public static final ComponentKey<PreventDropsComponent> PREVENT_DROPS = ComponentRegistry.getOrCreate(new Identifier(Aylyth.MOD_ID, "prevent_drops"), PreventDropsComponent.class);

	public static final ComponentKey<RiderComponent> RIDER_COMPONENT = ComponentRegistry.getOrCreate(new Identifier(Aylyth.MOD_ID, "rider"), RiderComponent.class);
	public static final ComponentKey<CuirassComponent> CUIRASS_COMPONENT = ComponentRegistry.getOrCreate(AylythUtil.id("cuirass"), CuirassComponent.class);
	
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(LivingEntity.class, YMPE_THORNS).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(YmpeThornsComponent::new);
		registry.registerForPlayers(YMPE_INFESTATION, YmpeInfestationComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerFor(MobEntity.class, PREVENT_DROPS, entity -> new PreventDropsComponent());
		registry.registerForPlayers(RIDER_COMPONENT, RiderComponent::new, RespawnCopyStrategy.NEVER_COPY);
		registry.registerForPlayers(CUIRASS_COMPONENT, CuirassComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
