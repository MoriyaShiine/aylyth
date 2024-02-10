package moriyashiine.aylyth.common.registry;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.generator.trunkplacer.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacerTypes {
    public static void init() {}

    public static final TrunkPlacerType<AylthianTrunkPlacer> AYLYTHIAN_TRUNK_PLACER = register("aylythian_trunk_placer", AylthianTrunkPlacer.CODEC);
    public static final TrunkPlacerType<YmpeTrunkPlacer> YMPE_TRUNK_PLACER = register("ympe_trunk_placer", YmpeTrunkPlacer.CODEC);
    public static final TrunkPlacerType<BigYmpeTrunkPlacer> BIG_YMPE_TRUNK_PLACER = register("big_ympe_trunk_placer", BigYmpeTrunkPlacer.CODEC);
    public static final TrunkPlacerType<PomegranateTrunkPlacer> POMEGRANATE_TRUNK_PLACER = register("pomegranate_trunk_placer", PomegranateTrunkPlacer.CODEC);
    public static final TrunkPlacerType<WrithewoodTrunkPlacer> WRITHEWOOD_TRUNK_PLACER = register("writhewood_trunk_placer", WrithewoodTrunkPlacer.CODEC);
    public static final TrunkPlacerType<GirasolTrunkPlacer> GIRASOL_TRUNK_PLACER = register("girasol_trunk_placer", GirasolTrunkPlacer.CODEC);
    
    private static <T extends TrunkPlacer> TrunkPlacerType<T> register(String id, Codec<T> codec) {
        return Registry.register(Registries.TRUNK_PLACER_TYPE, AylythUtil.id(id), new TrunkPlacerType<>(codec));
    }
}
