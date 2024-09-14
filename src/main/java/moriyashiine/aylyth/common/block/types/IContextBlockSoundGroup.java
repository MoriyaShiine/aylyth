package moriyashiine.aylyth.common.block.types;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;

public interface IContextBlockSoundGroup {
    BlockSoundGroup getBlockSoundGroup(BlockState state, BlockPos pos, BlockSoundGroup currentSoundGroup, Entity entity);
}
