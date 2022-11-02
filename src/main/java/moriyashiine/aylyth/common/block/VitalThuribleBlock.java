package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
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
        if (hand == Hand.MAIN_HAND && !isChargeItem(itemStack) && isChargeItem(player.getStackInHand(Hand.OFF_HAND))) {
            return ActionResult.PASS;
        } else if (isChargeItem(itemStack) && !state.get(ACTIVE)) {
            charge(world, pos, state);
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            return ActionResult.success(true);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    private static boolean isChargeItem(ItemStack stack) {
        return stack.isOf(ModItems.WRONGMEAT);
    }

    public static void charge(World world, BlockPos pos, BlockState state) {
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
