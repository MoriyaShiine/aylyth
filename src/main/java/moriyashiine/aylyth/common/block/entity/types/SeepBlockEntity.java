package moriyashiine.aylyth.common.block.entity.types;

import moriyashiine.aylyth.common.block.entity.AylythBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class SeepBlockEntity extends BlockEntity {
	public SeepBlockEntity(BlockPos pos, BlockState state) {
		super(AylythBlockEntityTypes.SEEP, pos, state);
	}
}
