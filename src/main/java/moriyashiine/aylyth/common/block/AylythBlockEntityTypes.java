package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.block.entities.SeepBlockEntity;
import moriyashiine.aylyth.common.block.entities.SoulHearthBlockEntity;
import moriyashiine.aylyth.common.block.entities.VitalThuribleBlockEntity;
import moriyashiine.aylyth.common.block.entities.WoodyGrowthCacheBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder.Factory;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythBlockEntityTypes {

	BlockEntityType<SeepBlockEntity> SEEP = register("seep", SeepBlockEntity::new, AylythBlocks.OAK_SEEP, AylythBlocks.SPRUCE_SEEP, AylythBlocks.DARK_OAK_SEEP, AylythBlocks.YMPE_SEEP, AylythBlocks.SEEPING_WOOD_SEEP);
	BlockEntityType<VitalThuribleBlockEntity> VITAL_THURIBLE = register("vital", VitalThuribleBlockEntity::new, AylythBlocks.VITAL_THURIBLE);
	BlockEntityType<WoodyGrowthCacheBlockEntity> WOODY_GROWTH_CACHE = register("woody_growth_cache", WoodyGrowthCacheBlockEntity::new, AylythBlocks.WOODY_GROWTH_CACHE);
	BlockEntityType<SoulHearthBlockEntity> SOUL_HEARTH = register("soul_hearth",SoulHearthBlockEntity::new, AylythBlocks.SOUL_HEARTH);

	private static <E extends BlockEntity> BlockEntityType<E> register(String name, BlockEntityType<E> type) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Aylyth.id(name), type);
	}

	private static <E extends BlockEntity> BlockEntityType<E> register(String name, Factory<E> factory, Block... supportedBlocks) {
		return register(name, FabricBlockEntityTypeBuilder.create(factory, supportedBlocks).build());
	}

	// Load static initializer
	static void register() {}
}
