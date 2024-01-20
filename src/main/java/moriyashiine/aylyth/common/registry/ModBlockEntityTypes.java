package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.entity.SeepBlockEntity;
import moriyashiine.aylyth.common.block.entity.SoulHearthBlockEntity;
import moriyashiine.aylyth.common.block.entity.VitalThuribleBlockEntity;
import moriyashiine.aylyth.common.block.entity.WoodyGrowthCacheBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityTypes {
	public static final BlockEntityType<SeepBlockEntity> SEEP_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(SeepBlockEntity::new, ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD_SEEP).build();
	public static final BlockEntityType<VitalThuribleBlockEntity> VITAL_THURIBLE_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(VitalThuribleBlockEntity::new, ModBlocks.VITAL_THURIBLE).build();
	public static final BlockEntityType<WoodyGrowthCacheBlockEntity> WOODY_GROWTH_CACHE_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(WoodyGrowthCacheBlockEntity::new, ModBlocks.WOODY_GROWTH_CACHE).build();
	public static final BlockEntityType<SoulHearthBlockEntity> SOUL_HEARTH_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(SoulHearthBlockEntity::new, ModBlocks.SOUL_HEARTH).build();


	public static void init() {
		Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Aylyth.MOD_ID, "seep"), SEEP_BLOCK_ENTITY_TYPE);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Aylyth.MOD_ID, "vital"), VITAL_THURIBLE_BLOCK_ENTITY);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Aylyth.MOD_ID, "woody_growth_cache"), WOODY_GROWTH_CACHE_BLOCK_ENTITY);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Aylyth.MOD_ID, "soul_hearth"), SOUL_HEARTH_BLOCK_ENTITY);
	}
}
