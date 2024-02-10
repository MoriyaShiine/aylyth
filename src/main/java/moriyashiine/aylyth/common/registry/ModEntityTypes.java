package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.entity.RootPropEntity;
import moriyashiine.aylyth.common.entity.mob.*;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity.TulpaPlayerEntity;
import moriyashiine.aylyth.common.entity.passive.PilotLightEntity;
import moriyashiine.aylyth.common.entity.projectile.SphereEntity;
import moriyashiine.aylyth.common.entity.projectile.YmpeLanceEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.Heightmap;

public class ModEntityTypes {
	public static final EntityType<PilotLightEntity> PILOT_LIGHT = registerLiving("pilot_light", FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, PilotLightEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build(), PilotLightEntity.createAttributes());
	public static final EntityType<AylythianEntity> AYLYTHIAN = registerLiving("aylythian", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, AylythianEntity::new).dimensions(EntityDimensions.fixed(0.6F, 2.3F)).build(), AylythianEntity.createAttributes());
	public static final EntityType<ElderAylythianEntity> ELDER_AYLYTHIAN = registerLiving("elder_aylythian", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ElderAylythianEntity::new).dimensions(EntityDimensions.fixed(1.4F, 2.3F)).build(), ElderAylythianEntity.createAttributes());
	public static final EntityType<YmpeLanceEntity> YMPE_LANCE = register("ympe_lance", FabricEntityTypeBuilder.<YmpeLanceEntity>create(SpawnGroup.MISC, YmpeLanceEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());

	public static final EntityType<SoulmouldEntity> SOULMOULD = registerLiving("soulmould", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SoulmouldEntity::new).dimensions(EntityDimensions.fixed(0.85F, 2.7F)).fireImmune().build(), SoulmouldEntity.createSoulmouldAttributes());
	public static final EntityType<BoneflyEntity> BONEFLY = registerLiving("bonefly", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BoneflyEntity::new).dimensions(EntityDimensions.fixed(1.4F, 2.1F)).fireImmune().build(), BoneflyEntity.createBoneflyAttributes());

	public static final EntityType<ScionEntity> SCION = registerLiving("scion", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ScionEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).fireImmune().build(), ScionEntity.createAttributes());
	public static final EntityType<RootPropEntity> ROOT_PROP = register("root_prop", FabricEntityTypeBuilder.<RootPropEntity>create(SpawnGroup.MISC, RootPropEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());
	public static final EntityType<RippedSoulEntity> RIPPED_SOUL = registerLiving("ripped_soul", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, RippedSoulEntity::new).dimensions(EntityDimensions.changing(0.9F, 0.9F)).build(), RippedSoulEntity.createVexAttributes());
	public static final EntityType<TulpaEntity> TULPA = registerLiving("tulpa", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TulpaEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).fireImmune().build(), TulpaEntity.createTulpaAttributes());
	public static final EntityType<TulpaPlayerEntity> TULPA_PLAYER = registerLiving("tulpa_player", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TulpaPlayerEntity::new).dimensions(EntityDimensions.fixed(0.6F, 1.95F)).build(), TulpaEntity.createTulpaAttributes());

	public static final EntityType<WreathedHindEntity> WREATHED_HIND_ENTITY = registerLiving("wreathed_hind", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WreathedHindEntity::new).dimensions(EntityDimensions.fixed(1.6F, 2.5F)).build(), WreathedHindEntity.createAttributes());

	public static final EntityType<SphereEntity> SPHERE_ENTITY = register("sphere", FabricEntityTypeBuilder.<SphereEntity>create(SpawnGroup.MISC, SphereEntity::new).dimensions(EntityDimensions.fixed(0.25f,0.25f)).build());

	public static final EntityType<FaunaylythianEntity> FAUNAYLYTHIAN = registerLiving("faunaylythian", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FaunaylythianEntity::new).dimensions(EntityDimensions.fixed(1.2F, 1.5F)).build(), FaunaylythianEntity.createAttributes());

	private static <T extends MobEntity> EntityType<T> registerMob(String id, EntityType<T> entityType,
																   DefaultAttributeContainer.Builder attributeBuilder,
																   SpawnRestriction.Location location,
																   Heightmap.Type heightmapType,
																   SpawnRestriction.SpawnPredicate<T> predicate)
	{
		SpawnRestriction.register(entityType, location, heightmapType, predicate);
		return registerLiving(id, entityType, attributeBuilder);
	}

	private static <T extends LivingEntity> EntityType<T> registerLiving(String id, EntityType<T> entityType,
																		 DefaultAttributeContainer.Builder attributeBuilder)
	{
		FabricDefaultAttributeRegistry.register(entityType, attributeBuilder);
		return register(id, entityType);
	}

	private static <T extends Entity> EntityType<T> register(String id, EntityType<T> entityType) {
		Registry.register(Registries.ENTITY_TYPE, AylythUtil.id(id), entityType);
		return entityType;
	}

	public static void init() {
		SpawnRestriction.register(PILOT_LIGHT, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, PilotLightEntity::canSpawn);
		SpawnRestriction.register(AYLYTHIAN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AylythianEntity::canSpawn);
		SpawnRestriction.register(SCION, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScionEntity::canSpawn);
		SpawnRestriction.register(ELDER_AYLYTHIAN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AylythianEntity::canSpawn);
		SpawnRestriction.register(WREATHED_HIND_ENTITY, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WreathedHindEntity::canSpawn);
		SpawnRestriction.register(FAUNAYLYTHIAN, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FaunaylythianEntity::canSpawn);
	}
}
