package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.common.block.entity.SeepBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SeepBlock extends Block implements BlockEntityProvider {
	public static final Property<Connection> CONNECTION = EnumProperty.of("connection", Connection.class, Connection.values());
	
	public SeepBlock(Block log) {
		super(FabricBlockSettings.of(Material.WOOD).strength(2).sounds(BlockSoundGroup.WOOD).dropsLike(log));
		setDefaultState(getDefaultState().with(CONNECTION, Connection.NONE));
	}
	
	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SeepBlockEntity(pos, state);
	}
	
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = super.getPlacementState(ctx);
		BlockState upper = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
		BlockState down = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
		if (down.contains(CONNECTION) && (down.get(CONNECTION) == Connection.NONE || down.get(CONNECTION) == Connection.UP)) {
			return state.with(CONNECTION, Connection.DOWN);
		}
		else if (upper.contains(CONNECTION) && (upper.get(CONNECTION) == Connection.NONE || upper.get(CONNECTION) == Connection.DOWN)) {
			return state.with(CONNECTION, Connection.UP);
		}
		return state;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.contains(CONNECTION)) {
			if (state.get(CONNECTION) == Connection.NONE) {
				if (direction == Direction.DOWN && neighborState.contains(CONNECTION) && neighborState.get(CONNECTION) == Connection.UP) {
					state = state.with(CONNECTION, Connection.DOWN);
				}
				else if (direction == Direction.UP && neighborState.contains(CONNECTION) && neighborState.get(CONNECTION) == Connection.DOWN) {
					state = state.with(CONNECTION, Connection.UP);
				}
			}
			else if (state.get(CONNECTION) == Connection.UP) {
				if (direction == Direction.UP && (!neighborState.contains(CONNECTION) || neighborState.get(CONNECTION) != Connection.DOWN)) {
					state = state.with(CONNECTION, Connection.NONE);
				}
			}
			else if (state.get(CONNECTION) == Connection.DOWN) {
				if (direction == Direction.DOWN && (!neighborState.contains(CONNECTION) || neighborState.get(CONNECTION) != Connection.UP)) {
					state = state.with(CONNECTION, Connection.NONE);
				}
			}
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder.add(CONNECTION));
	}
	
	public enum Connection implements StringIdentifiable {
		NONE("none"), DOWN("down"), UP("up");
		
		private final String id;
		
		Connection(String id) {
			this.id = id;
		}
		
		@Override
		public String asString() {
			return id;
		}
	}
}
