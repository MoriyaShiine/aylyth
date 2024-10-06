package moriyashiine.aylyth.common.block.types;

import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.block.entities.SeepBlockEntity;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.AylythPointOfInterestTypes;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SeepBlock extends Block implements BlockEntityProvider {
	private static final VoxelShape SINGLE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(createCuboidShape(0.0D, 1.0D, 1.0D, 16.0D, 15.0D, 15.0D), createCuboidShape(1.0D, 1.0D, 0.0D, 15.0D, 15.0D, 16.0D)), BooleanBiFunction.ONLY_FIRST);
	private static final VoxelShape UP_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(createCuboidShape(0.0D, 1.0D, 1.0D, 16.0D, 16.0D, 15.0D), createCuboidShape(1.0D, 1.0D, 0.0D, 15.0D, 16.0D, 16.0D)), BooleanBiFunction.ONLY_FIRST);
	private static final VoxelShape DOWN_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(createCuboidShape(0.0D, 0.0D, 1.0D, 16.0D, 15.0D, 15.0D), createCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 15.0D, 16.0D)), BooleanBiFunction.ONLY_FIRST);
	
	public static final Property<Connection> CONNECTION = EnumProperty.of("connection", Connection.class, Connection.values());
	
	public SeepBlock(AbstractBlock.Settings settings) {
		super(settings);
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
				MinecraftServer server = serverWorld.getServer();
				ServerWorld toWorld = world.getRegistryKey() == AylythDimensionData.WORLD ? server.getOverworld() : server.getWorld(AylythDimensionData.WORLD);
				if (toWorld == null) {
					return;
				}
				Optional<BlockPos> connectedSeep;
				if (state.isOf(AylythBlocks.SEEPING_WOOD_SEEP)) {
					connectedSeep = findConnectedSeepSpawn(toWorld, pos);
				} else {
					connectedSeep = Optional.empty();
				}
				AylythUtil.teleportTo(toWorld, entity, pos,
						(serverWorld1, blockPos) -> connectedSeep.orElseGet(() -> AylythUtil.findTeleportPosition(serverWorld1, blockPos)),
						(serverWorld1, blockPos, entity1) -> {});
			}
		}
	}

	public Optional<BlockPos> findConnectedSeepSpawn(ServerWorld world, BlockPos center) {
		return world.getPointOfInterestStorage()
				.getInSquare(point -> point.matchesKey(AylythPointOfInterestTypes.SEEP), center, 32, PointOfInterestStorage.OccupationStatus.ANY)
				.findFirst()
				.flatMap(pointOfInterest -> {
					for (Direction dir : Direction.Type.HORIZONTAL) {
						BlockPos pos = pointOfInterest.getPos().offset(dir);
						if (AylythUtil.canTeleportToPos(world, pos)) {
							return Optional.of(pos);
						}
					}
					return Optional.empty();
				});
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new SeepBlockEntity(pos, state);
	}
	
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
