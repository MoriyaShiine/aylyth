package moriyashiine.aylyth.common.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.MapMaker;
import com.google.common.collect.UnmodifiableIterator;
import moriyashiine.aylyth.common.block.entity.SoulHearthBlockEntity;
import moriyashiine.aylyth.common.item.AylythItems;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.InsertionOnlyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.CollisionView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class SoulHearthBlock extends Block implements BlockEntityProvider {

    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    public static final IntProperty CHARGES = IntProperty.of("charged", 0, 5);
    private static final ImmutableList<Vec3i> VALID_HORIZONTAL_SPAWN_OFFSETS;
    private static final ImmutableList<Vec3i> VALID_SPAWN_OFFSETS;
    private static final VoxelShape LOWER_SHAPES;
    private static final VoxelShape UPPER_SHAPES;

    public SoulHearthBlock(Settings settings) {
        super(settings.nonOpaque().requiresTool().strength(3.5F).luminance((state) -> state.get(CHARGES) > 0 ? 13 : 0));
        this.setDefaultState(this.stateManager.getDefaultState().with(CHARGES, 0).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockPos finalPos = pos;
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            finalPos = finalPos.down();
        }

        boolean insertSuccess = false;
        ContainerItemContext storage = ContainerItemContext.forPlayerInteraction(player, hand);
        ItemVariant variant = storage.getItemVariant();
        if (!storage.getItemVariant().isBlank()) {
            Storage<ItemVariant> soulHearthStorage = ItemStorage.SIDED.find(world, finalPos, null);
            try (Transaction transaction = Transaction.openOuter()) {
                if (StorageUtil.move(storage.getMainSlot(), soulHearthStorage, itemVariant -> true, 1, transaction) == 1) {
                    transaction.commit();
                    insertSuccess = true;
                }
            }
        }

        if (insertSuccess) {
            if (!world.isClient) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
                player.incrementStat(Stats.USED.getOrCreateStat(variant.getItem()));
                if (!finalPos.equals(serverPlayerEntity.getSpawnPointPosition())) {
                    serverPlayerEntity.setSpawnPoint(world.getRegistryKey(), finalPos, 0.0F, true, true);
                    world.playSound(null, (double) finalPos.getX() + 0.5, (double) finalPos.getY() + 0.5, (double) finalPos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
            return ActionResult.success(world.isClient);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public void genParticle(ParticleEffect particleEffect, World world, BlockPos pos, Random random){
        for(int i = 0; i < random.nextInt(1) + 1; ++i) {
            double d = (double)pos.getX() + 0.25 + random.nextDouble() / 2;
            double e = (double)pos.getY() + random.nextDouble() / 2;
            double f = (double)pos.getZ() + 0.25 + random.nextDouble() / 2;
            world.addParticle(particleEffect, d, e, f, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(CHARGES) > 0) {
            if (random.nextInt(10) == 0) {
                world.playSound((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
            }
            if (random.nextInt(5) == 0) {
                genParticle(ParticleTypes.SOUL_FIRE_FLAME, world, pos, random);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(CHARGES, HALF));
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        return blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx) ? super.getPlacementState(ctx) : null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), this.getDefaultState().with(CHARGES, state.get(CHARGES)).with(HALF, DoubleBlockHalf.UPPER));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative()) {
            TallPlantBlock.onBreakInCreative(world, pos, state, player);
        }
        super.onBreak(world, pos, state, player);
    }

    public static Optional<Vec3d> findRespawnPosition(EntityType<?> entity, CollisionView world, BlockPos pos) {
        Optional<Vec3d> optional = findRespawnPosition(entity, world, pos, true);
        return optional.or(() -> findRespawnPosition(entity, world, pos, false));
    }

    private static Optional<Vec3d> findRespawnPosition(EntityType<?> entity, CollisionView world, BlockPos pos, boolean ignoreInvalidPos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        UnmodifiableIterator<Vec3i> var5 = VALID_SPAWN_OFFSETS.iterator();

        Vec3d vec3d;
        do {
            if (!var5.hasNext()) {
                return Optional.empty();
            }

            Vec3i vec3i = var5.next();
            mutable.set(pos).move(vec3i);
            vec3d = Dismounting.findRespawnPos(entity, world, mutable, ignoreInvalidPos);
        } while(vec3d == null);

        return Optional.of(vec3d);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP) && (!neighborState.isOf(this) || neighborState.get(HALF) == doubleBlockHalf)) {
            return neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf ? state.with(CHARGES, neighborState.get(CHARGES)).with(HALF, neighborState.get(HALF)) : Blocks.AIR.getDefaultState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? LOWER_SHAPES : UPPER_SHAPES;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SoulHearthBlockEntity(pos, state);
    }

    static {
        VALID_HORIZONTAL_SPAWN_OFFSETS = ImmutableList.of(new Vec3i(0, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(1, 0, 0), new Vec3i(-1, 0, -1), new Vec3i(1, 0, -1), new Vec3i(-1, 0, 1), new Vec3i(1, 0, 1));
        VALID_SPAWN_OFFSETS = new ImmutableList.Builder<Vec3i>()
                .addAll(VALID_HORIZONTAL_SPAWN_OFFSETS)
                .addAll(VALID_HORIZONTAL_SPAWN_OFFSETS.stream().map(Vec3i::down).iterator())
                .addAll(VALID_HORIZONTAL_SPAWN_OFFSETS.stream().map(Vec3i::up).iterator())
                .add(new Vec3i(0, 1, 0)).build();
        LOWER_SHAPES = VoxelShapes.union(
                createCuboidShape(1, 0, 1, 15, 3, 15),
                createCuboidShape(2, 3, 2, 14, 6, 14),
                createCuboidShape(3.5, 6, 3.5, 12.5, 8, 12.5),

                createCuboidShape(16 - 4.5, 6, 2.5, 16 - 2.5, 10, 4.5),
                createCuboidShape(2.5, 6, 2.5, 4.5, 10, 4.5),
                createCuboidShape(16 - 4.5, 6, 16 - 4.5, 16 - 2.5, 10, 16 - 2.5),
                createCuboidShape(2.5, 6, 16 - 4.5, 4.5, 10, 16 - 2.5),

                createCuboidShape(11.75, 10, 2.75, 13.25, 16, 4.25),
                createCuboidShape(2.75, 10, 2.75, 4.25, 16, 4.25),
                createCuboidShape(11.75, 10, 11.75, 13.25, 16, 13.25),
                createCuboidShape(2.75, 10, 11.75, 4.25, 16, 13.25)
        );
        UPPER_SHAPES = VoxelShapes.union(
                createCuboidShape(11.75, 0, 2.75, 13.25, 5, 4.25),
                createCuboidShape(2.75, 0, 2.75, 4.25, 5, 4.25),
                createCuboidShape(11.75, 0, 11.75, 13.25, 5, 13.25),
                createCuboidShape(2.75, 0, 11.75, 4.25, 5, 13.25),

                createCuboidShape(2, 3, 2, 14, 5, 14),
                createCuboidShape(4, 5, 4, 12, 8, 12)
        );

    }

    public static class SoulHearthStorage extends SnapshotParticipant<Integer> implements InsertionOnlyStorage<ItemVariant> {
        private static final Map<MutableBlockReference, SoulHearthStorage> COLLECTION_CACHE = new MapMaker().concurrencyLevel(1).weakValues().makeMap();

        private final MutableBlockReference reference;
        private int charge;

        private SoulHearthStorage(MutableBlockReference reference) {
            this.reference = reference;
            this.charge = reference.getBlockState().get(CHARGES);
        }

        public static SoulHearthStorage getOrCreate(World world, BlockPos pos) {
            return COLLECTION_CACHE.computeIfAbsent(new MutableBlockReference(world, pos), SoulHearthStorage::new);
        }

        @Override
        protected Integer createSnapshot() {
            return charge;
        }

        @Override
        protected void readSnapshot(Integer snapshot) {
            this.charge = snapshot;
        }

        @Override
        protected void onFinalCommit() {
            reference.setWithProperty(state -> state.with(CHARGES, charge));
            reference.setWithProperty(Direction.UP, state -> state.with(CHARGES, charge));
            reference.world.playSound(null, (double)reference.pos.getX() + 0.5, (double)reference.pos.getY() + 0.5, (double)reference.pos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

        @Override
        public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            if (maxAmount < 0) return 0;
            if (!resource.isOf(AylythItems.POMEGRANATE)) return 0;
            int currCharges = reference.getBlockState().get(CHARGES);
            if (currCharges == 5) return 0;

            long inserted = Math.min(5-currCharges, maxAmount);
            charge = currCharges+(int)inserted;
            updateSnapshots(transaction);

            return inserted;
        }

        public record MutableBlockReference(World world, BlockPos pos) {
            public void setBlockState(BlockState state) {
                world.setBlockState(pos, state);
            }

            public void setWithProperty(UnaryOperator<BlockState> stateOperator) {
                setBlockState(stateOperator.apply(getBlockState()));
            }

            public void setWithProperty(Direction posOffset, UnaryOperator<BlockState> stateOperator) {
                BlockPos offsetPos = pos.offset(posOffset);
                world.setBlockState(offsetPos, stateOperator.apply(world.getBlockState(offsetPos)));
            }

            public BlockState getBlockState() {
                return world.getBlockState(pos);
            }
        }
    }
}
