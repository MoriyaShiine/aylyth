package moriyashiine.aylyth.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;

public class ModSaplingBlock extends SaplingBlock {
	public ModSaplingBlock(SaplingGenerator generator) {
		super(generator, FabricBlockSettings.copy(Blocks.OAK_SAPLING));
	}
}
