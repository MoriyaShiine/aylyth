package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.entity.RootPropEntity;
import moriyashiine.aylyth.common.entity.mob.*;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity.TulpaPlayerEntity;
import moriyashiine.aylyth.common.entity.passive.PilotLightEntity;
import moriyashiine.aylyth.common.entity.projectile.SphereEntity;
import moriyashiine.aylyth.common.entity.projectile.ThornFlechetteEntity;
import moriyashiine.aylyth.common.entity.projectile.YmpeLanceEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.Heightmap;

public class ModEntityTypes {
	public static final EntityType<PilotLightEntity> PILOT_LIGHT = register("pilot_light", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.AMBIENT)
			.entityFactory(PilotLightEntity::new)
			.dimensions(EntityDimensions.fixed(0.5F, 0.5F))
			.defaultAttributes(PilotLightEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, PilotLightEntity::canSpawn)
			.build());
	public static final EntityType<AylythianEntity> AYLYTHIAN = register("aylythian", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(AylythianEntity::new)
			.dimensions(EntityDimensions.fixed(0.6F, 2.3F))
			.defaultAttributes(AylythianEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AylythianEntity::canSpawn)
			.build());
	public static final EntityType<ElderAylythianEntity> ELDER_AYLYTHIAN = register("elder_aylythian", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(ElderAylythianEntity::new)
			.dimensions(EntityDimensions.fixed(1.4F, 2.3F))
			.defaultAttributes(ElderAylythianEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AylythianEntity::canSpawn)
			.build());
	public static final EntityType<ScionEntity> SCION = register("scion", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(ScionEntity::new)
			.dimensions(EntityDimensions.fixed(0.6F, 1.95F))
			.defaultAttributes(ScionEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScionEntity::canSpawn)
			.fireImmune()
			.build());
	public static final EntityType<FaunaylythianEntity> FAUNAYLYTHIAN = register("faunaylythian", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(FaunaylythianEntity::new)
			.dimensions(EntityDimensions.fixed(1.2F, 1.5F))
			.defaultAttributes(FaunaylythianEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FaunaylythianEntity::canSpawn)
			.build());
	public static final EntityType<WreathedHindEntity> WREATHED_HIND_ENTITY = register("wreathed_hind", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(WreathedHindEntity::new)
			.dimensions(EntityDimensions.fixed(1.6F, 2.5F))
			.defaultAttributes(WreathedHindEntity::createAttributes)
			.spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WreathedHindEntity::canSpawn)
			.build());

	public static final EntityType<RippedSoulEntity> RIPPED_SOUL = register("ripped_soul", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(RippedSoulEntity::new)
			.dimensions(EntityDimensions.changing(0.9F, 0.9F))
			.defaultAttributes(RippedSoulEntity::createVexAttributes)
			.build());

	public static final EntityType<SoulmouldEntity> SOULMOULD = register("soulmould", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(SoulmouldEntity::new)
			.dimensions(EntityDimensions.fixed(0.85F, 2.7F))
			.defaultAttributes(SoulmouldEntity::createSoulmouldAttributes)
			.fireImmune()
			.build());
	public static final EntityType<BoneflyEntity> BONEFLY = register("bonefly", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(BoneflyEntity::new)
			.dimensions(EntityDimensions.fixed(1.4F, 2.1F))
			.defaultAttributes(BoneflyEntity::createBoneflyAttributes)
			.fireImmune()
			.build());
	public static final EntityType<TulpaEntity> TULPA = register("tulpa", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(TulpaEntity::new)
			.dimensions(EntityDimensions.fixed(0.6F, 1.95F))
			.defaultAttributes(TulpaEntity::createTulpaAttributes)
			.fireImmune()
			.build());
	public static final EntityType<TulpaPlayerEntity> TULPA_PLAYER = register("tulpa_player", FabricEntityTypeBuilder.createMob()
			.spawnGroup(SpawnGroup.MONSTER)
			.entityFactory(TulpaPlayerEntity::new)
			.dimensions(EntityDimensions.fixed(0.6F, 1.95F))
			.defaultAttributes(TulpaEntity::createTulpaAttributes)
			.build());

	public static final EntityType<RootPropEntity> ROOT_PROP = register("root_prop", FabricEntityTypeBuilder.<RootPropEntity>create(SpawnGroup.MISC, RootPropEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());
	public static final EntityType<SphereEntity> SPHERE_ENTITY = register("sphere", FabricEntityTypeBuilder.<SphereEntity>create(SpawnGroup.MISC, SphereEntity::new).dimensions(EntityDimensions.fixed(0.25f,0.25f)).build());
	public static final EntityType<YmpeLanceEntity> YMPE_LANCE = register("ympe_lance", FabricEntityTypeBuilder.<YmpeLanceEntity>create(SpawnGroup.MISC, YmpeLanceEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());
	public static final EntityType<ThornFlechetteEntity> THORN_FLECHETTE = register("thorn_flechette", FabricEntityTypeBuilder.<ThornFlechetteEntity>create(SpawnGroup.MISC, ThornFlechetteEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());


	private static <T extends Entity> EntityType<T> register(String id, EntityType<T> entityType) {
		Registry.register(Registries.ENTITY_TYPE, AylythUtil.id(id), entityType);
		return entityType;
	}

	public static void init() {}
}
