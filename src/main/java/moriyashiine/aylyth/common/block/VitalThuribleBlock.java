package moriyashiine.aylyth.common.block;

import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.api.interfaces.Vital;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.world.ModWorldState;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.UUID;

public class VitalThuribleBlock extends Block {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    private static final VoxelShape SHAPES;

    public VitalThuribleBlock(Settings settings) {
        super(settings.nonOpaque().requiresTool().strength(3.5F).luminance((state) -> state.get(ACTIVE) ? 13 : 0));
        this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVE, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (hand == Hand.MAIN_HAND && !isActivateItem(itemStack) && isActivateItem(player.getStackInHand(Hand.OFF_HAND))) {
            return ActionResult.PASS;
        } else if (isActivateItem(itemStack) && !state.get(ACTIVE)) {
            if(!world.isClient()){
                ModWorldState worldState = ModWorldState.get(world);
                Pair<ServerWorld, BlockPos> existingVitalThurible = getPhylactery(player);
                if (existingVitalThurible != null) {
                    System.out.println("Break");
                    existingVitalThurible.getFirst().breakBlock(existingVitalThurible.getSecond(), true, player);
                }
                worldState.addVitalTurible(player, pos);
            }
            activate(world, pos, state);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.success(true);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(!world.isClient() && state.getBlock() != newState.getBlock()){
            ModWorldState modWorldState = ModWorldState.get(world);
            PlayerEntity player = world.getPlayerByUuid(VitalThuribleBlock.getPlayerForVital(modWorldState, pos));
            if (player != null) {
                modWorldState.removeVitalTurible(player);
                if(world.getServer() != null){
                    for (ServerPlayerEntity serverPlayerEntity : PlayerLookup.all(world.getServer())) {
                        if (serverPlayerEntity.getUuid().equals(player.getUuid())){
                            Vital.of(player).ifPresent(vital -> vital.setVital(false));
                        }
                    }
                }

            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    private static boolean isActivateItem(ItemStack stack) {
        return stack.isOf(ModItems.WRONGMEAT);
    }

    public static void activate(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(ACTIVE, true));
        world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    public void genParticle(ParticleEffect particleEffect, World world, BlockPos pos, Random random){
        for(int i = 0; i < random.nextInt(1) + 1; ++i) {
            double d = (double)pos.getX() + 0.25 + random.nextDouble() / 2;
            double e = (double)pos.getY() + random.nextDouble() / 2;
            double f = (double)pos.getZ() + 0.25 + random.nextDouble() / 2;
            world.addParticle(particleEffect, d, e, f, 0.0, 0.0, 0.0);
        }
    }

    public static UUID getPlayerForVital(ModWorldState worldState, BlockPos pos){
        for (UUID uuid : worldState.vital_thurible.keySet()) {
            if (worldState.vital_thurible.get(uuid).equals(pos)) {
                return uuid;
            }
        }
        return null;
    }

    public static Pair<ServerWorld, BlockPos> getPhylactery(PlayerEntity player) {
        if (player.world instanceof ServerWorld) {
            for (ServerWorld serverWorld : player.world.getServer().getWorlds()) {
                ModWorldState worldState = ModWorldState.get(serverWorld);
                if (worldState.vital_thurible.containsKey(player.getUuid())) {
                    BlockState blockState = serverWorld.getBlockState(worldState.vital_thurible.get(player.getUuid()));
                    if (blockState.getBlock() instanceof VitalThuribleBlock) {
                        return new Pair<>(serverWorld, worldState.vital_thurible.get(player.getUuid()));
                    }
                    worldState.removeVitalTurible(player);
                }
            }
        }
        return null;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(ACTIVE)) {
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
        builder.add(ACTIVE);
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES;
    }

    static {
        SHAPES = VoxelShapes.union(
                createCuboidShape(0,0,0,2,13,2),
                createCuboidShape(0,0,14,2,13,16),
                createCuboidShape(14,0,0,16,13,2),
                createCuboidShape(14,0,14,16,13,16),
                createCuboidShape(1, 0, 1, 15, 12, 15),
                createCuboidShape(3,13,3,13,17,13));
    }
}
