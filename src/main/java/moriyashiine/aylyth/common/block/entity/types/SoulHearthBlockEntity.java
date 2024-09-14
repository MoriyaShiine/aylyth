package moriyashiine.aylyth.common.block.entity.types;

import moriyashiine.aylyth.common.block.entity.AylythBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class SoulHearthBlockEntity extends BlockEntity {
    public SoulHearthBlockEntity(BlockPos pos, BlockState state) {
        super(AylythBlockEntityTypes.SOUL_HEARTH, pos, state);
    }
}
