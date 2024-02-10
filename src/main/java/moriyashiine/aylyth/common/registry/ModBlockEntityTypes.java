package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.block.entity.SeepBlockEntity;
import moriyashiine.aylyth.common.block.entity.SoulHearthBlockEntity;
import moriyashiine.aylyth.common.block.entity.VitalThuribleBlockEntity;
import moriyashiine.aylyth.common.block.entity.WoodyGrowthCacheBlockEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntityTypes {
	public static final BlockEntityType<SeepBlockEntity> SEEP_BLOCK_ENTITY_TYPE = register("seep", FabricBlockEntityTypeBuilder.create(SeepBlockEntity::new, ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD_SEEP).build());
	public static final BlockEntityType<VitalThuribleBlockEntity> VITAL_THURIBLE_BLOCK_ENTITY = register("vital", FabricBlockEntityTypeBuilder.create(VitalThuribleBlockEntity::new, ModBlocks.VITAL_THURIBLE).build());
	public static final BlockEntityType<WoodyGrowthCacheBlockEntity> WOODY_GROWTH_CACHE_BLOCK_ENTITY = register("woody_growth_cache", FabricBlockEntityTypeBuilder.create(WoodyGrowthCacheBlockEntity::new, ModBlocks.WOODY_GROWTH_CACHE).build());
	public static final BlockEntityType<SoulHearthBlockEntity> SOUL_HEARTH_BLOCK_ENTITY = register("soul_hearth", FabricBlockEntityTypeBuilder.create(SoulHearthBlockEntity::new, ModBlocks.SOUL_HEARTH).build());

	private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> entityType) {
		Registry.register(Registries.BLOCK_ENTITY_TYPE, AylythUtil.id(id), entityType);
		return entityType;
	}

	public static void init() {}
}
