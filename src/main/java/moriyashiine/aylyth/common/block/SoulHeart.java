package moriyashiine.aylyth.common.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import moriyashiine.aylyth.common.registry.ModDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockView;
import net.minecraft.world.CollisionView;
import net.minecraft.world.World;

import java.util.Optional;

public class SoulHeart extends Block {

    public static final BooleanProperty CHARGED;
    private static final ImmutableList<Vec3i> VALID_HORIZONTAL_SPAWN_OFFSETS;
    private static final ImmutableList<Vec3i> VALID_SPAWN_OFFSETS;

    public SoulHeart(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(CHARGED, false));
    }

    public static boolean isAylyth(World world) {
        return world.getRegistryKey().equals(ModDimensions.AYLYTH);
    }


    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (hand == Hand.MAIN_HAND && !isChargeItem(itemStack) && isChargeItem(player.getStackInHand(Hand.OFF_HAND))) {
            return ActionResult.PASS;
        } else if (isChargeItem(itemStack) && canCharge(state)) {
            charge(world, pos, state);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            return ActionResult.success(world.isClient);
        } else if (!state.get(CHARGED)) {
            return ActionResult.PASS;
        } else {
            if (!world.isClient) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
                if (serverPlayerEntity.getSpawnPointDimension() != world.getRegistryKey() || !pos.equals(serverPlayerEntity.getSpawnPointPosition())) {
                    serverPlayerEntity.setSpawnPoint(world.getRegistryKey(), pos, 0.0F, false, true);
                    world.playSound((PlayerEntity)null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return ActionResult.SUCCESS;
                }
            }

            return ActionResult.CONSUME;
        }
    }

    private static boolean isChargeItem(ItemStack stack) {
        return stack.isOf(Items.GLOWSTONE);//TODO
    }

    private static boolean canCharge(BlockState state) {
        return !state.get(CHARGED);
    }

    public static void charge(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(CHARGED, true));
        world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CHARGED);
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
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    static {
        CHARGED = BooleanProperty.of("charged");
        VALID_HORIZONTAL_SPAWN_OFFSETS = ImmutableList.of(new Vec3i(0, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(1, 0, 0), new Vec3i(-1, 0, -1), new Vec3i(1, 0, -1), new Vec3i(-1, 0, 1), new Vec3i(1, 0, 1));
        VALID_SPAWN_OFFSETS = (new ImmutableList.Builder())
                .addAll(VALID_HORIZONTAL_SPAWN_OFFSETS)
                .addAll(VALID_HORIZONTAL_SPAWN_OFFSETS.stream().map(Vec3i::down).iterator())
                .addAll(VALID_HORIZONTAL_SPAWN_OFFSETS.stream().map(Vec3i::up).iterator())
                .add(new Vec3i(0, 1, 0)).build();
    }
}
