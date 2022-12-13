package moriyashiine.aylyth.common.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import moriyashiine.aylyth.common.block.entity.SoulHearthBlockEntity;
import moriyashiine.aylyth.common.registry.ModDimensionKeys;
import moriyashiine.aylyth.common.registry.ModItems;
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
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SoulHearthBlock extends Block implements BlockEntityProvider {

    public static final IntProperty CHARGES;
    private static final ImmutableList<Vec3i> VALID_HORIZONTAL_SPAWN_OFFSETS;
    private static final ImmutableList<Vec3i> VALID_SPAWN_OFFSETS;
    public static final EnumProperty<DoubleBlockHalf> HALF;
    private static final VoxelShape LOWER_SHAPES;
    private static final VoxelShape UPPER_SHAPES;

    public SoulHearthBlock(Settings settings) {
        super(settings.nonOpaque().requiresTool().strength(3.5F).luminance((state) -> state.get(CHARGES) > 0 ? 13 : 0));
        this.setDefaultState(this.stateManager.getDefaultState().with(CHARGES, 0).with(HALF, DoubleBlockHalf.LOWER));
    }


    public static boolean isAylyth(World world) {
        return world.getRegistryKey().equals(ModDimensionKeys.AYLYTH);
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            pos = pos.down();
        }

        ItemStack itemStack = player.getStackInHand(hand);
        if (hand == Hand.MAIN_HAND && !isChargeItem(itemStack) && isChargeItem(player.getStackInHand(Hand.OFF_HAND))) {
            return ActionResult.PASS;
        } else if (isChargeItem(itemStack) && canCharge(state)) {
            charge(world, pos, state);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            if (!world.isClient) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
                serverPlayerEntity.setSpawnPoint(world.getRegistryKey(), pos, 0.0F, true, true);
                world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResult.SUCCESS;
            }
            return ActionResult.success(true);
        } else if (state.get(CHARGES) == 0) {
            return ActionResult.PASS;
        } else {
            return ActionResult.CONSUME;
        }
    }

    private static boolean isChargeItem(ItemStack stack) {
        return stack.isOf(ModItems.POMEGRANATE);
    }

    private static boolean canCharge(BlockState state) {
        return state.get(CHARGES) < 5;
    }

    public static void charge(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(CHARGES, state.get(CHARGES) + 1).with(HALF, DoubleBlockHalf.LOWER));
        world.setBlockState(pos.up(), state.with(CHARGES, state.get(CHARGES) + 1).with(HALF, DoubleBlockHalf.UPPER));
        world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
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
        builder.add(CHARGES).add(HALF);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        return blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx) ? super.getPlacementState(ctx) : null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockPos blockPos = pos.up();
        world.setBlockState(blockPos, this.getDefaultState().with(CHARGES, world.getBlockState(pos).get(CHARGES)).with(HALF, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
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
        return optional.isPresent() ? optional : findRespawnPosition(entity, world, pos, false);
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
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            return LOWER_SHAPES;
        }else{
            return UPPER_SHAPES;
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SoulHearthBlockEntity(pos, state);
    }

    static {
        HALF = Properties.DOUBLE_BLOCK_HALF;
        CHARGES = IntProperty.of("charged", 0, 5);
        VALID_HORIZONTAL_SPAWN_OFFSETS = ImmutableList.of(new Vec3i(0, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(1, 0, 0), new Vec3i(-1, 0, -1), new Vec3i(1, 0, -1), new Vec3i(-1, 0, 1), new Vec3i(1, 0, 1));
        VALID_SPAWN_OFFSETS = new ImmutableList.Builder()
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
}
