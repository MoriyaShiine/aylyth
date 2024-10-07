package moriyashiine.aylyth.common.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.stream.Stream;

public class AllFeature extends Feature<AllFeature.AllFeatureConfig> {
    public AllFeature(Codec<AllFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<AllFeatureConfig> context) {
        boolean hadSuccess = false;
        for (RegistryEntry<PlacedFeature> feature : context.getConfig().features()) {
            hadSuccess |= feature.value().generateUnregistered(context.getWorld(), context.getGenerator(), context.getRandom(), context.getOrigin());
        }
        return hadSuccess;
    }

    public record AllFeatureConfig(RegistryEntryList<PlacedFeature> features) implements FeatureConfig {
        public static final Codec<AllFeatureConfig> CODEC = Codecs.nonEmptyEntryList(PlacedFeature.LIST_CODEC)
                .fieldOf("placed_features").xmap(AllFeatureConfig::new, AllFeatureConfig::features).codec();

        @Override
        public Stream<ConfiguredFeature<?, ?>> getDecoratedFeatures() {
            return features.stream().flatMap(placedFeatureRegistryEntry -> placedFeatureRegistryEntry.value().getDecoratedFeatures());
        }
    }
}
