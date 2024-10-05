package moriyashiine.aylyth.common.world.gen;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.gen.trunkplacers.AylthianTrunkPlacer;
import moriyashiine.aylyth.common.world.gen.trunkplacers.BigYmpeTrunkPlacer;
import moriyashiine.aylyth.common.world.gen.trunkplacers.GirasolTrunkPlacer;
import moriyashiine.aylyth.common.world.gen.trunkplacers.PomegranateTrunkPlacer;
import moriyashiine.aylyth.common.world.gen.trunkplacers.WrithewoodTrunkPlacer;
import moriyashiine.aylyth.common.world.gen.trunkplacers.YmpeTrunkPlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public interface AylythTrunkPlacerTypes {

    TrunkPlacerType<AylthianTrunkPlacer> AYLYTHIAN = register("aylythian", AylthianTrunkPlacer.CODEC);
    TrunkPlacerType<YmpeTrunkPlacer> YMPE = register("ympe", YmpeTrunkPlacer.CODEC);
    TrunkPlacerType<BigYmpeTrunkPlacer> BIG_YMPE = register("big_ympe", BigYmpeTrunkPlacer.CODEC);
    TrunkPlacerType<PomegranateTrunkPlacer> POMEGRANATE = register("pomegranate", PomegranateTrunkPlacer.CODEC);
    TrunkPlacerType<WrithewoodTrunkPlacer> WRITHEWOOD = register("writhewood", WrithewoodTrunkPlacer.CODEC);
    TrunkPlacerType<GirasolTrunkPlacer> GIRASOL = register("girasol", GirasolTrunkPlacer.CODEC);
    
    private static <T extends TrunkPlacer> TrunkPlacerType<T> register(String name, Codec<T> codec) {
        return Registry.register(Registries.TRUNK_PLACER_TYPE, Aylyth.id(name), new TrunkPlacerType<>(codec));
    }

    // Load static initializer
    static void register() {}
}
