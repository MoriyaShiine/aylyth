package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.AylythianEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntityTypes {
	public static final EntityType<AylythianEntity> AYLYTHIAN = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, AylythianEntity::new).dimensions(EntityDimensions.fixed(0.6F, 2.3F)).build();
	
	public static void init() {
		FabricDefaultAttributeRegistry.register(AYLYTHIAN, AylythianEntity.createAttributes());
		Registry.register(Registry.ENTITY_TYPE, new Identifier(Aylyth.MOD_ID, "aylythian"), AYLYTHIAN);
	}
}
