package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.common.registry.ModComponents;
import moriyashiine.aylyth.common.registry.tag.ModEntityTypeTags;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GripweedBlock extends PlantBlock {
	private static final VoxelShape SHAPE = createCuboidShape(0, 0, 0, 16, 11, 16);
	
	public GripweedBlock() {
		super(Settings.copy(Blocks.GRASS).offset(OffsetType.XZ));
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity.getType().isIn(ModEntityTypeTags.GRIPWEED_IMMUNE)) {
			return;
		}
		if (entity instanceof LivingEntity living && EnchantmentHelper.hasSoulSpeed(living)) {
			return;
		}
		if (entity instanceof PlayerEntity player && ModComponents.YMPE_INFESTATION.get(player).getStage() >= 2) {
			return;
		}
		entity.slowMovement(state, new Vec3d(0.5, 0.5, 0.5));
	}
}
