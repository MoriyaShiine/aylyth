package moriyashiine.aylyth.common.block;

import moriyashiine.aylyth.common.data.world.feature.AylythConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class SaplingGenerators {
    public static final SaplingGenerator GREEN_AYLYTHIAN_OAK = new SaplingGenerator(
            "green_aylythian_oak",
            Optional.of(AylythConfiguredFeatures.GREEN_AYLYTHIAN_DARK_OAK),
            Optional.empty(),
            Optional.empty()
    );
    public static final SaplingGenerator ORANGE_AYLYTHIAN_OAK = new SaplingGenerator(
            "orange_aylythian_oak",
            Optional.of(AylythConfiguredFeatures.ORANGE_AYLYTHIAN_DARK_OAK),
            Optional.empty(),
            Optional.empty()
    );
    public static final SaplingGenerator RED_AYLYTHIAN_OAK = new SaplingGenerator(
            "red_aylythian_oak",
            Optional.of(AylythConfiguredFeatures.RED_AYLYTHIAN_DARK_OAK),
            Optional.empty(),
            Optional.empty()
    );
    public static final SaplingGenerator BROWN_AYLYTHIAN_OAK = new SaplingGenerator(
            "brown_aylythian_oak",
            Optional.of(AylythConfiguredFeatures.BROWN_AYLYTHIAN_DARK_OAK),
            Optional.empty(),
            Optional.empty()
    );
    public static final SaplingGenerator YMPE = new SaplingGenerator(
            "ympe",
            Optional.of(AylythConfiguredFeatures.BIG_YMPE_TREE),
            Optional.of(AylythConfiguredFeatures.YMPE_TREE),
            Optional.empty()
    );
    public static final SaplingGenerator POMEGRANATE = new SaplingGenerator(
            "pomegranate",
            Optional.empty(),
            Optional.of(AylythConfiguredFeatures.POMEGRANATE_TREE),
            Optional.empty()
    );
    public static final SaplingGenerator WRITHEWOOD = new SaplingGenerator(
            "writhewood",
            Optional.empty(),
            Optional.of(AylythConfiguredFeatures.WRITHEWOOD_TREE),
            Optional.empty()
    );
    public static final SaplingGenerator GIRASOL = new SaplingGenerator(
            "girasol",
            Optional.empty(),
            Optional.of(AylythConfiguredFeatures.GIRASOL_TREE),
            Optional.empty()
    );
}
