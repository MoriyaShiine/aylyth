package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.common.AylythUtil;
import moriyashiine.aylyth.common.block.entity.SeepBlockEntity;
import moriyashiine.aylyth.common.registry.ModDimensions;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;

public class SeepBlock extends Block implements BlockEntityProvider {
	private static final VoxelShape SINGLE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(createCuboidShape(0.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D), createCuboidShape(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 16.0D)), BooleanBiFunction.ONLY_FIRST);
	private static final VoxelShape UP_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(createCuboidShape(0.0D, 1.0D, 1.0D, 16.0D, 16.0D, 15.0D), createCuboidShape(1.0D, 1.0D, 0.0D, 15.0D, 16.0D, 16.0D)), BooleanBiFunction.ONLY_FIRST);
	private static final VoxelShape DOWN_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(createCuboidShape(0.0D, 0.0D, 1.0D, 16.0D, 15.0D, 15.0D), createCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 15.0D, 16.0D)), BooleanBiFunction.ONLY_FIRST);
	
	public static final Property<Connection> CONNECTION = EnumProperty.of("connection", Connection.class, Connection.values());
	
	public SeepBlock() {
		super(FabricBlockSettings.of(Material.WOOD).strength(2).sounds(BlockSoundGroup.WOOD));
		setDefaultState(getDefaultState().with(CONNECTION, Connection.NONE));
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(CONNECTION)) {
			case NONE -> SINGLE_SHAPE;
			case UP -> UP_SHAPE;
			case DOWN -> DOWN_SHAPE;
		};
	}
	
	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		super.onEntityCollision(state, world, pos, entity);
		if (world instanceof ServerWorld serverWorld) {
			if (entity.getPos().distanceTo(new Vec3d(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F)) < 0.6F) {
				ServerWorld aylyth = serverWorld.getServer().getWorld(ModDimensions.AYLYTH);
				ServerWorld toWorld = entity.world == aylyth ? serverWorld.getServer().getWorld(RegistryKey.of(Registry.WORLD_KEY, DimensionTypes.OVERWORLD_ID)) : aylyth;
				FabricDimensions.teleport(entity, toWorld, new TeleportTarget(Vec3d.of(AylythUtil.getSafePosition(toWorld, entity.getBlockPos().mutableCopy(), 0)), Vec3d.ZERO, entity.getHeadYaw(), entity.getPitch()));
			}
		}
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
