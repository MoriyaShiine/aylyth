package moriyashiine.aylyth.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BushBlock extends PlantBlock {
	public static final BooleanProperty BUSHY = BooleanProperty.of("bushy");
	
	public BushBlock() {
		super(Settings.copy(Blocks.GRASS));
		setDefaultState(getDefaultState().with(BUSHY, false));
	}
	
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState downState = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
		return getDefaultState().with(BUSHY, downState.getBlock() instanceof BushBlock);
	}
	
	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.getBlock() instanceof BushBlock || super.canPlantOnTop(floor, world, pos);
	}
	
	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		return false;
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		entity.slowMovement(state, new Vec3d(0.95, 0.95, 0.95));
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder.add(BUSHY));
	}
}
