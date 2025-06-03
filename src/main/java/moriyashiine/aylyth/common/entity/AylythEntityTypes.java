package moriyashiine.aylyth.common.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.RootPropEntity;
import moriyashiine.aylyth.common.entity.types.mob.AylythianEntity;
import moriyashiine.aylyth.common.entity.types.mob.BoneflyEntity;
import moriyashiine.aylyth.common.entity.types.mob.ElderAylythianEntity;
import moriyashiine.aylyth.common.entity.types.mob.FaunaylythianEntity;
import moriyashiine.aylyth.common.entity.types.mob.RippedSoulEntity;
import moriyashiine.aylyth.common.entity.types.mob.ScionEntity;
import moriyashiine.aylyth.common.entity.types.mob.YmpemouldEntity;
import moriyashiine.aylyth.common.entity.types.mob.TulpaEntity;
import moriyashiine.aylyth.common.entity.types.mob.TulpaEntity.TulpaPlayerEntity;
import moriyashiine.aylyth.common.entity.types.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.entity.types.mob.PilotLightEntity;
import moriyashiine.aylyth.common.entity.types.projectile.SphereEntity;
import moriyashiine.aylyth.common.entity.types.projectile.ThornFlechetteEntity;
import moriyashiine.aylyth.common.entity.types.projectile.YmpeLanceEntity;
import moriyashiine.aylyth.common.item.AylythItems;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;

public interface AylythEntityTypes {

	EntityType<PilotLightEntity> PILOT_LIGHT = register("pilot_light",
			FabricEntityType.Builder.createMob(PilotLightEntity::new, SpawnGroup.AMBIENT, builder ->
							builder.defaultAttributes(PilotLightEntity::createAttributes)
									.spawnRestriction(SpawnLocationTypes.UNRESTRICTED, Heightmap.Type.MOTION_BLOCKING, PilotLightEntity::canSpawn)
					)
					.dimensions(0.5f, 0.5f)
	);
	EntityType<AylythianEntity> AYLYTHIAN = register("aylythian",
			FabricEntityType.Builder.createMob(AylythianEntity::new,  SpawnGroup.MONSTER, builder ->
							builder.defaultAttributes(AylythianEntity::createAttributes)
									.spawnRestriction(SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AylythianEntity::canSpawn)
					)
					.dimensions(0.6f, 2.3f)
	);
	EntityType<ElderAylythianEntity> ELDER_AYLYTHIAN = register("elder_aylythian",
			FabricEntityType.Builder.createMob(ElderAylythianEntity::new, SpawnGroup.MONSTER, builder ->
							builder.defaultAttributes(ElderAylythianEntity::createAttributes)
									.spawnRestriction(SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AylythianEntity::canSpawn)
					)
					.dimensions(1.4f, 2.3f)
	);
	EntityType<ScionEntity> SCION = register("scion",
			FabricEntityType.Builder.createMob(ScionEntity::new, SpawnGroup.MONSTER, builder ->
							builder.defaultAttributes(ScionEntity::createAttributes)
									.spawnRestriction(SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScionEntity::canSpawn)
					)
					.dimensions(0.6f, 1.95f)
					.makeFireImmune()
	);
	EntityType<FaunaylythianEntity> FAUNAYLYTHIAN = register("faunaylythian",
			FabricEntityType.Builder.createMob(FaunaylythianEntity::new, SpawnGroup.MONSTER, builder ->
							builder.defaultAttributes(FaunaylythianEntity::createAttributes)
									.spawnRestriction(SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FaunaylythianEntity::canSpawn)
					)
					.dimensions(1.2f, 1.5f)
	);
	EntityType<WreathedHindEntity> WREATHED_HIND_ENTITY = register("wreathed_hind",
			FabricEntityType.Builder.createMob(WreathedHindEntity::new, SpawnGroup.MONSTER, builder ->
							builder.defaultAttributes(WreathedHindEntity::createAttributes)
									.spawnRestriction(SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WreathedHindEntity::canSpawn)
					)
					.dimensions(1.6f, 2.5f)
	);

	EntityType<RippedSoulEntity> RIPPED_SOUL = register("ripped_soul",
			FabricEntityType.Builder.createLiving(RippedSoulEntity::new, SpawnGroup.MONSTER, builder ->
							builder.defaultAttributes(RippedSoulEntity::createVexAttributes)
					)
					.dimensions(0.9f, 0.9f)
	);

	EntityType<YmpemouldEntity> YMPEMOULD = register("ympemould",
			FabricEntityType.Builder.createLiving(YmpemouldEntity::new, SpawnGroup.MONSTER, builder ->
							builder.defaultAttributes(YmpemouldEntity::createSoulmouldAttributes)
					)
					.dimensions(0.85f, 2.7f)
					.makeFireImmune()
	);
	EntityType<BoneflyEntity> BONEFLY = register("bonefly",
			FabricEntityType.Builder.createLiving(BoneflyEntity::new, SpawnGroup.MONSTER, builder ->
							builder.defaultAttributes(BoneflyEntity::createBoneflyAttributes)
					)
					.passengerAttachments(1.6f)
					.dimensions(1.4f, 2.1f)
					.makeFireImmune()
	);
	EntityType<TulpaEntity> TULPA = register("tulpa",
			FabricEntityType.Builder.createLiving(TulpaEntity::new, SpawnGroup.MONSTER, builder ->
							builder.defaultAttributes(BoneflyEntity::createBoneflyAttributes)
					)
					.dimensions(0.6f, 1.95f)
					.makeFireImmune()
	);

	EntityType<TulpaPlayerEntity> TULPA_PLAYER = register("tulpa_player",
			FabricEntityType.Builder.createLiving(TulpaPlayerEntity::new, SpawnGroup.MONSTER, builder ->
							builder.defaultAttributes(TulpaEntity::createTulpaAttributes)
					)
					.dimensions(0.6f, 1.95f)
	);

	EntityType<RootPropEntity> ROOT_PROP = register("root_prop",
			EntityType.Builder.<RootPropEntity>create(RootPropEntity::new, SpawnGroup.MISC)
					.dimensions(0.5f, 0.5f)
	);
	EntityType<SphereEntity> SPHERE_ENTITY = register("sphere",
			EntityType.Builder.<SphereEntity>create(SphereEntity::new, SpawnGroup.MISC)
					.dimensions(0.25f,0.25f)
	);
	EntityType<YmpeLanceEntity> YMPE_LANCE = register("ympe_lance",
			EntityType.Builder.<YmpeLanceEntity>create(YmpeLanceEntity::new, SpawnGroup.MISC)
					.dimensions(0.5f, 0.5f)
	);
	EntityType<ThornFlechetteEntity> THORN_FLECHETTE = register("thorn_flechette",
			EntityType.Builder.<ThornFlechetteEntity>create(ThornFlechetteEntity::new, SpawnGroup.MISC)
					.dimensions(0.5f, 0.5f)
	);

	EntityType<BoatEntity> YMPE_BOAT = register("ympe_boat",
			EntityType.Builder.<BoatEntity>create((type, world) -> new BoatEntity(type, world, () -> AylythItems.YMPE_BOAT), SpawnGroup.MISC)
					.dropsNothing()
					.dimensions(1.375F, 0.5625F)
					.eyeHeight(0.5625F)
					.maxTrackingRange(10)
	);
	EntityType<ChestBoatEntity> YMPE_CHEST_BOAT = register("ympe_chest_boat",
			EntityType.Builder.<ChestBoatEntity>create((type, world) -> new ChestBoatEntity(type, world, () -> AylythItems.YMPE_CHEST_BOAT), SpawnGroup.MISC)
					.dropsNothing()
					.dimensions(1.375F, 0.5625F)
					.eyeHeight(0.5625F)
					.maxTrackingRange(10)
	);
	EntityType<BoatEntity> POMEGRANATE_BOAT = register("pomegranate_boat",
			EntityType.Builder.<BoatEntity>create((type, world) -> new BoatEntity(type, world, () -> AylythItems.POMEGRANATE_BOAT), SpawnGroup.MISC)
					.dropsNothing()
					.dimensions(1.375F, 0.5625F)
					.eyeHeight(0.5625F)
					.maxTrackingRange(10)
	);
	EntityType<ChestBoatEntity> POMEGRANATE_CHEST_BOAT = register("pomegranate_chest_boat",
			EntityType.Builder.<ChestBoatEntity>create((type, world) -> new ChestBoatEntity(type, world, () -> AylythItems.POMEGRANATE_CHEST_BOAT), SpawnGroup.MISC)
					.dropsNothing()
					.dimensions(1.375F, 0.5625F)
					.eyeHeight(0.5625F)
					.maxTrackingRange(10)
	);
	EntityType<BoatEntity> WRITHEWOOD_BOAT = register("writhewood_boat",
			EntityType.Builder.<BoatEntity>create((type, world) -> new BoatEntity(type, world, () -> AylythItems.WRITHEWOOD_BOAT), SpawnGroup.MISC)
					.dropsNothing()
					.dimensions(1.375F, 0.5625F)
					.eyeHeight(0.5625F)
					.maxTrackingRange(10)
	);
	EntityType<ChestBoatEntity> WRITHEWOOD_CHEST_BOAT = register("writhewood_chest_boat",
			EntityType.Builder.<ChestBoatEntity>create((type, world) -> new ChestBoatEntity(type, world, () -> AylythItems.WRITHEWOOD_CHEST_BOAT), SpawnGroup.MISC)
					.dropsNothing()
					.dimensions(1.375F, 0.5625F)
					.eyeHeight(0.5625F)
					.maxTrackingRange(10)
	);

	private static <E extends Entity> EntityType<E> register(String name, EntityType.Builder<E> builder) {
		return Registry.register(Registries.ENTITY_TYPE, Aylyth.id(name), builder.build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Aylyth.id(name))));
	}

	// Load static initializer
	static void register() {}
}
