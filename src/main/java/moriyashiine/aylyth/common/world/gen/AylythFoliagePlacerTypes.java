package moriyashiine.aylyth.common.world.gen;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.gen.foliageplacers.GirasolFoliagePlacer;
import moriyashiine.aylyth.common.world.gen.foliageplacers.PomegranateFoliagePlacer;
import moriyashiine.aylyth.common.world.gen.foliageplacers.WrithewoodFoliagePlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public interface AylythFoliagePlacerTypes {

    FoliagePlacerType<PomegranateFoliagePlacer> POMEGRANATE = register("pomegranate", PomegranateFoliagePlacer.CODEC);
    FoliagePlacerType<WrithewoodFoliagePlacer> WRITHEWOOD = register("writhewood", WrithewoodFoliagePlacer.CODEC);
    FoliagePlacerType<GirasolFoliagePlacer> GIRASOL = register("girasol", GirasolFoliagePlacer.CODEC);
    
    private static <T extends FoliagePlacer> FoliagePlacerType<T> register(String id, Codec<T> codec) {
        return Registry.register(Registries.FOLIAGE_PLACER_TYPE, Aylyth.id(id), new FoliagePlacerType<>(codec));
    }

    // Load static initializer
    static void register() {}
}
