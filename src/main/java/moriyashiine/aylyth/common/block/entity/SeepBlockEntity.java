package moriyashiine.aylyth.common.block.entity;

import moriyashiine.aylyth.common.registry.ModBlocks;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class SeepBlockEntity extends BlockEntity implements BlockEntityClientSerializable {
	public SeepBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlocks.SEEP_BLOCK_ENTITY_TYPE, pos, state);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound tag) {
		return super.writeNbt(tag);
	}

	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);
	}

	@Override
	public void fromClientTag(NbtCompound tag) {
		readNbt(tag);
	}

	@Override
	public NbtCompound toClientTag(NbtCompound tag) {
		return writeNbt(tag);
	}
}
