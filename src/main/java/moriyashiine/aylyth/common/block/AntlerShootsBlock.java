package moriyashiine.aylyth.common.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;

public class AntlerShootsBlock extends PlantBlock {
	public AntlerShootsBlock() {
		super(Settings.copy(Blocks.GRASS));
	}
	
	@Override
	public OffsetType getOffsetType() {
		return OffsetType.XZ;
	}
}
