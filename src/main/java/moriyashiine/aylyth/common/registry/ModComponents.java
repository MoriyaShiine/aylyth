package moriyashiine.aylyth.common.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.component.entity.PreventDropsComponent;
import moriyashiine.aylyth.common.component.entity.YmpeInfestationComponent;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class ModComponents implements EntityComponentInitializer {
	public static final ComponentKey<YmpeInfestationComponent> YMPE_INFESTATION = ComponentRegistry.getOrCreate(new Identifier(Aylyth.MOD_ID, "ympe_infestation"), YmpeInfestationComponent.class);
	public static final ComponentKey<PreventDropsComponent> PREVENT_DROPS = ComponentRegistry.getOrCreate(new Identifier(Aylyth.MOD_ID, "prevent_drops"), PreventDropsComponent.class);
	
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(YMPE_INFESTATION, YmpeInfestationComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.registerFor(MobEntity.class, PREVENT_DROPS, entity -> new PreventDropsComponent());
	}
}
