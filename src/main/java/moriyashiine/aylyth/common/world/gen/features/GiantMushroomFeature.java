package moriyashiine.aylyth.common.world.gen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class GiantMushroomFeature extends Feature<GiantMushroomFeature.GiantMushroomConfig> {
    public GiantMushroomFeature(Codec<GiantMushroomConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<GiantMushroomConfig> context) {
        StructureWorldAccess worldAccess = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        int capSize = context.getConfig().capSize().get(random);
        int stemSize = context.getConfig().stemSize().get(random);

        BlockPos stemEndPos = origin.up(stemSize+1);
        for (BlockPos loc : BlockPos.iterate(origin, stemEndPos)) {
            BlockState stemState = context.getConfig().stemProvider().get(random, loc);
            worldAccess.setBlockState(loc, stemState, Block.NOTIFY_ALL);
        }

        for (BlockPos loc : BlockPos.iterate(stemEndPos.add(-capSize, 0, -capSize), stemEndPos.add(capSize, 0, capSize))) {
            BlockState capState = context.getConfig().capProvider().get(random, loc);
            boolean isXEdge = Math.abs(loc.getX() - origin.getX()) == capSize;
            boolean isZEdge = Math.abs(loc.getZ() - origin.getZ()) == capSize;
            if (!isXEdge || !isZEdge) {
                worldAccess.setBlockState(loc, capState, Block.NOTIFY_ALL);
                if (!isXEdge && !isZEdge) {
                    worldAccess.setBlockState(loc.up(), capState, Block.NOTIFY_ALL);
                }
            }
        }

        return true;
    }

    public record GiantMushroomConfig(BlockStateProvider capProvider, BlockStateProvider stemProvider, IntProvider capSize, IntProvider stemSize) implements FeatureConfig {
        public static final Codec<GiantMushroomConfig> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        BlockStateProvider.TYPE_CODEC.fieldOf("cap_provider").forGetter(GiantMushroomConfig::capProvider),
                        BlockStateProvider.TYPE_CODEC.fieldOf("stem_provider").forGetter(GiantMushroomConfig::stemProvider),
                        IntProvider.POSITIVE_CODEC.fieldOf("cap_size").forGetter(GiantMushroomConfig::capSize),
                        IntProvider.POSITIVE_CODEC.fieldOf("stem_size").forGetter(GiantMushroomConfig::stemSize)
                ).apply(instance, GiantMushroomConfig::new)
        );
    }
}
