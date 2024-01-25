package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.mixin.SaplingBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class GirasolSaplingBlock extends SaplingBlock {

    public static final BooleanProperty FAILED = BooleanProperty.of("failed_to_grow");

    private static final DustColorTransitionParticleEffect FAILED_PARTICLE = new DustColorTransitionParticleEffect(new Vector3f(0.1333f, 0.1333f, 0.1333f), new Vector3f(0.6824f, 0.4275f, 0.0941f), 1);

    public GirasolSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
        setDefaultState(getDefaultState().with(FAILED, false));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(pos.up()) >= 9 && random.nextInt(7) == 0) {
            if (state.get(STAGE) == 0) {
                world.setBlockState(pos, state.cycle(STAGE), Block.NO_REDRAW);
            } else {
                tryToGenerate(state, world, pos, random);
            }
        }
    }

    public void tryToGenerate(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!(world.getRegistryKey().equals(World.OVERWORLD) && ((SaplingBlockAccessor)this).getGenerator().generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random))) {
            world.spawnParticles(FAILED_PARTICLE, pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, 16, 0.3, 0.3, 0.3, 0.0);
            world.setBlockState(pos, state.with(FAILED, true), Block.NO_REDRAW);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (state.get(FAILED) && random.nextInt(10) < 3) {
            for (int i = 0; i < 16; i++) {
                float x = pos.getX()+random.nextFloat();
                float y = pos.getY()+random.nextFloat();
                float z = pos.getZ()+random.nextFloat();
                float xVel = random.nextInt(4) * 0.1f;
                float yVel = random.nextInt(4) * 0.1f;
                float ZVel = random.nextInt(4) * 0.1f;
                world.addParticle(FAILED_PARTICLE, x, y, z, xVel, yVel, ZVel);
            }
        }
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        tryToGenerate(state, world, pos, random);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FAILED);
    }
}
