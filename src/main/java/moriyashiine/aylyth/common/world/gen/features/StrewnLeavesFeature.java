package moriyashiine.aylyth.common.world.gen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class StrewnLeavesFeature extends Feature<StrewnLeavesFeature.StrewnLeavesConfig> {
    public StrewnLeavesFeature() {
        super(StrewnLeavesConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<StrewnLeavesConfig> context) {
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        if (!world.testBlockState(origin, AbstractBlock.AbstractBlockState::isAir)) {
            return false;
        }

        BlockState strewnState = context.getConfig().stateProvider().get(random, origin);
        world.setBlockState(origin, strewnState, Block.NOTIFY_LISTENERS);
        return true;
    }

    public record StrewnLeavesConfig(BlockStateProvider stateProvider) implements FeatureConfig {
            public static final Codec<StrewnLeavesConfig> CODEC = RecordCodecBuilder.create(instance ->
                    instance.group(BlockStateProvider.TYPE_CODEC.fieldOf("state_provider").forGetter(StrewnLeavesConfig::stateProvider))
                    .apply(instance, StrewnLeavesConfig::new));
    }
}
