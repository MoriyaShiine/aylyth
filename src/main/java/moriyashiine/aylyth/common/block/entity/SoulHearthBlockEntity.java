package moriyashiine.aylyth.common.block.entity;

import moriyashiine.aylyth.common.registry.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class SoulHearthBlockEntity extends BlockEntity {
    public SoulHearthBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SOUL_HEARTH_BLOCK_ENTITY, pos, state);
    }
}
