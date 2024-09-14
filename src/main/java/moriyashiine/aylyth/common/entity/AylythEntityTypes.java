package moriyashiine.aylyth.common.entity;

import moriyashiine.aylyth.common.entity.types.RootPropEntity;
import moriyashiine.aylyth.common.entity.types.mob.*;
import moriyashiine.aylyth.common.entity.types.mob.TulpaEntity.TulpaPlayerEntity;
import moriyashiine.aylyth.common.entity.types.mob.passive.PilotLightEntity;
import moriyashiine.aylyth.common.entity.types.projectile.SphereEntity;
import moriyashiine.aylyth.common.entity.types.projectile.ThornFlechetteEntity;
import moriyashiine.aylyth.common.entity.types.projectile.YmpeLanceEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.Heightmap;

public interface AylythEntityTypes {

	EntityType<PilotLightEntity> PILOT_LIGHT = register("pilot_light", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.AMBIENT)
			.entityFactory(PilotLightEntity::new)
			.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
			.defaultAttributes(PilotLightEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, PilotLightEntity::canSpawn)
			.build()
	);
	EntityType<AylythianEntity> AYLYTHIAN = register("aylythian", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(AylythianEntity::new)
			.dimensions(EntityDimensions.fixed(0.6f, 2.3f))
			.defaultAttributes(AylythianEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AylythianEntity::canSpawn)
			.build()
	);
	EntityType<ElderAylythianEntity> ELDER_AYLYTHIAN = register("elder_aylythian", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(ElderAylythianEntity::new)
			.dimensions(EntityDimensions.fixed(1.4f, 2.3f))
			.defaultAttributes(ElderAylythianEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AylythianEntity::canSpawn)
			.build()
	);
	EntityType<ScionEntity> SCION = register("scion", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(ScionEntity::new)
			.dimensions(EntityDimensions.fixed(0.6f, 1.95f))
			.defaultAttributes(ScionEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScionEntity::canSpawn)
			.fireImmune()
			.build()
	);
	EntityType<FaunaylythianEntity> FAUNAYLYTHIAN = register("faunaylythian", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(FaunaylythianEntity::new)
			.dimensions(EntityDimensions.fixed(1.2f, 1.5f))
			.defaultAttributes(FaunaylythianEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FaunaylythianEntity::canSpawn)
			.build()
	);
	EntityType<WreathedHindEntity> WREATHED_HIND_ENTITY = register("wreathed_hind", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(WreathedHindEntity::new)
			.dimensions(EntityDimensions.fixed(1.6f, 2.5f))
			.defaultAttributes(WreathedHindEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WreathedHindEntity::canSpawn)
			.build()
	);

	EntityType<RippedSoulEntity> RIPPED_SOUL = register("ripped_soul", FabricEntityTypeBuilder.createLiving()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(RippedSoulEntity::new)
			.dimensions(EntityDimensions.changing(0.9f, 0.9f))
			.defaultAttributes(RippedSoulEntity::createVexAttributes)
			.build()
	);

	EntityType<SoulmouldEntity> SOULMOULD = register("soulmould", FabricEntityTypeBuilder.createLiving()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(SoulmouldEntity::new)
			.dimensions(EntityDimensions.fixed(0.85f, 2.7f))
			.fireImmune()
			.defaultAttributes(SoulmouldEntity::createSoulmouldAttributes)
			.build()
	);
	EntityType<BoneflyEntity> BONEFLY = register("bonefly", FabricEntityTypeBuilder.createLiving()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(BoneflyEntity::new)
			.dimensions(EntityDimensions.fixed(1.4f, 2.1f))
			.fireImmune()
			.defaultAttributes(BoneflyEntity::createBoneflyAttributes)
			.build()
	);
	EntityType<TulpaEntity> TULPA = register("tulpa", FabricEntityTypeBuilder.createLiving()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(TulpaEntity::new)
			.dimensions(EntityDimensions.fixed(0.6f, 1.95f))
			.fireImmune()
			.defaultAttributes(BoneflyEntity::createBoneflyAttributes)
			.build()
	);
	EntityType<TulpaPlayerEntity> TULPA_PLAYER = register("tulpa_player", FabricEntityTypeBuilder.createLiving()
			.entityFactory(TulpaPlayerEntity::new)
			.spawnGroup(SpawnGroup.MONSTER)
			.dimensions(EntityDimensions.fixed(0.6f, 1.95f))
			.defaultAttributes(TulpaEntity::createTulpaAttributes)
			.build()
	);

	EntityType<RootPropEntity> ROOT_PROP = register("root_prop", FabricEntityTypeBuilder.<RootPropEntity>create(SpawnGroup.MISC, RootPropEntity::new)
			.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
			.build()
	);
	EntityType<SphereEntity> SPHERE_ENTITY = register("sphere", FabricEntityTypeBuilder.<SphereEntity>create(SpawnGroup.MISC, SphereEntity::new)
			.dimensions(EntityDimensions.fixed(0.25f,0.25f))
			.build()
	);
	EntityType<YmpeLanceEntity> YMPE_LANCE = register("ympe_lance", FabricEntityTypeBuilder.<YmpeLanceEntity>create(SpawnGroup.MISC, YmpeLanceEntity::new)
			.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
			.build()
	);
	EntityType<ThornFlechetteEntity> THORN_FLECHETTE = register("thorn_flechette", FabricEntityTypeBuilder.<ThornFlechetteEntity>create(SpawnGroup.MISC, ThornFlechetteEntity::new)
			.dimensions(EntityDimensions.fixed(0.5f, 0.5f))
			.build()
	);

	private static <E extends Entity> EntityType<E> register(String name, EntityType<E> type) {
		return Registry.register(Registries.ENTITY_TYPE, AylythUtil.id(name), type);
	}

	// Load static initializer
	static void register() {}
}
