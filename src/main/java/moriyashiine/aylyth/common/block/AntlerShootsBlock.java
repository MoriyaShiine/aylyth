package moriyashiine.aylyth.common.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import org.jetbrains.annotations.Nullable;

public class AntlerShootsBlock extends PlantBlock implements Waterloggable {

	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	public AntlerShootsBlock() {
		super(Settings.copy(Blocks.GRASS).offsetType(OffsetType.XZ));
		setDefaultState(getDefaultState().with(WATERLOGGED, false));
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		if (ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER) {
			return getDefaultState().with(WATERLOGGED, true);
		}
		return super.getPlacementState(ctx);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) {
			return Fluids.WATER.getStill(false);
		}
		return super.getFluidState(state);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(WATERLOGGED);
	}
}
