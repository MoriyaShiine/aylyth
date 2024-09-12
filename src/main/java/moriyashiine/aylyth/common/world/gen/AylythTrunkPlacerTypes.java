package moriyashiine.aylyth.common.world.gen;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.gen.trunkplacer.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public interface AylythTrunkPlacerTypes {

    // TODO remove "_trunk_placer"
    TrunkPlacerType<AylthianTrunkPlacer> AYLYTHIAN = register("aylythian_trunk_placer", AylthianTrunkPlacer.CODEC);
    TrunkPlacerType<YmpeTrunkPlacer> YMPE = register("ympe_trunk_placer", YmpeTrunkPlacer.CODEC);
    TrunkPlacerType<BigYmpeTrunkPlacer> BIG_YMPE = register("big_ympe_trunk_placer", BigYmpeTrunkPlacer.CODEC);
    TrunkPlacerType<PomegranateTrunkPlacer> POMEGRANATE = register("pomegranate_trunk_placer", PomegranateTrunkPlacer.CODEC);
    TrunkPlacerType<WrithewoodTrunkPlacer> WRITHEWOOD = register("writhewood_trunk_placer", WrithewoodTrunkPlacer.CODEC);
    TrunkPlacerType<GirasolTrunkPlacer> GIRASOL = register("girasol_trunk_placer", GirasolTrunkPlacer.CODEC);
    
    private static <T extends TrunkPlacer> TrunkPlacerType<T> register(String name, Codec<T> codec) {
        return Registry.register(Registries.TRUNK_PLACER_TYPE, AylythUtil.id(name), new TrunkPlacerType<>(codec));
    }

    // Load static initializer
    static void register() {}
}
