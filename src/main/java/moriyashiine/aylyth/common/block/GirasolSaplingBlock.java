package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.common.block.util.ModSaplingBlock;
import moriyashiine.aylyth.mixin.SaplingBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.particle.DustColorTransitionParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class GirasolSaplingBlock extends ModSaplingBlock {

    public static final BooleanProperty FAILED = BooleanProperty.of("failed_to_grow");

    private static final DustColorTransitionParticleEffect FAILED_PARTICLE = new DustColorTransitionParticleEffect(new Vec3f(0.1333f, 0.1333f, 0.1333f), new Vec3f(0.6824f, 0.4275f, 0.0941f), 1);

    public GirasolSaplingBlock(SaplingGenerator generator) {
        super(generator);
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
        if (!((SaplingBlockAccessor)this).getGenerator().generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random)) {
            world.spawnParticles(FAILED_PARTICLE, pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, 16, 0.3, 0.3, 0.3, 0.0);
            world.setBlockState(pos, state.with(FAILED, true), Block.NO_REDRAW);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (state.get(FAILED) && random.nextInt(10) < 3) {
            for (int i = 0; i < 16; i++) {
                var x = pos.getX()+random.nextFloat();
                var y = pos.getY()+random.nextFloat();
                var z = pos.getZ()+random.nextFloat();
                var xVel = random.nextInt(4) * 0.1;
                var yVel = random.nextInt(4) * 0.1;
                var ZVel = random.nextInt(4) * 0.1;
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
