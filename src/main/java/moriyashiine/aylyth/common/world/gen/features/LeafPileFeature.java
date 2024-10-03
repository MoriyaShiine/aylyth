package moriyashiine.aylyth.common.world.gen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class LeafPileFeature extends Feature<LeafPileFeature.LeafPileConfig> {
    public LeafPileFeature() {
        super(LeafPileConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<LeafPileConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();
        if (world.isAir(origin)) {
            BlockState peakState = context.getConfig().peakProvider().get(random, origin);
            world.setBlockState(origin, peakState, Block.NOTIFY_LISTENERS);
            for (Direction dir : Direction.Type.HORIZONTAL) {
                BlockPos offset = origin.offset(dir, 1);
                BlockState slopeState = context.getConfig().slopeProvider().get(random, origin);
                if (world.isAir(offset) && slopeState.canPlaceAt(world, offset)) {
                    world.setBlockState(offset, slopeState, Block.NOTIFY_LISTENERS);
                }
            }
            return true;
        }
        return false;
    }

    public record LeafPileConfig(BlockStateProvider peakProvider, BlockStateProvider slopeProvider) implements FeatureConfig {
        public static final Codec<LeafPileConfig> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        BlockStateProvider.TYPE_CODEC.fieldOf("peak_provider").forGetter(LeafPileConfig::peakProvider),
                        BlockStateProvider.TYPE_CODEC.fieldOf("slope_provider").forGetter(LeafPileConfig::slopeProvider)
                ).apply(instance, LeafPileConfig::new)
        );
    }
}
