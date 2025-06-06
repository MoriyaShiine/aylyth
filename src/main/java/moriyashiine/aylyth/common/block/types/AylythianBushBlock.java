package moriyashiine.aylyth.common.block.types;

import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.block.AylythProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

public class AylythianBushBlock extends PlantBlock {
	public static final BooleanProperty LEAFY = AylythProperties.LEAFY;
	
	public AylythianBushBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(LEAFY, false));
	}

	@Override
	protected MapCodec<? extends PlantBlock> getCodec() {
		throw new AssertionError("Codec must be implemented");
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState downState = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
		return getDefaultState().with(LEAFY, downState.getBlock() instanceof AylythianBushBlock);
	}
	
	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.getBlock() instanceof AylythianBushBlock || super.canPlantOnTop(floor, world, pos);
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
		super.appendProperties(builder.add(LEAFY));
	}
}
