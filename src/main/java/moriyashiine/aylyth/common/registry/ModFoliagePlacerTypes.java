package moriyashiine.aylyth.common.registry;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.util.AylythUtil;
import moriyashiine.aylyth.common.world.generator.foliageplacer.GirasolFoliagePlacer;
import moriyashiine.aylyth.common.world.generator.foliageplacer.PomegranateFoliagePlacer;
import moriyashiine.aylyth.common.world.generator.foliageplacer.WrithewoodFoliagePlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class ModFoliagePlacerTypes {
    public static void init() {}

    public static final FoliagePlacerType<PomegranateFoliagePlacer> POMEGRANATE_FOLIAGE_PLACER = register("pomegranate_foliage_placer", PomegranateFoliagePlacer.CODEC);
    public static final FoliagePlacerType<WrithewoodFoliagePlacer> WRITHEWOOD_FOLIAGE_PLACER = register("writhewood_foliage_placer", WrithewoodFoliagePlacer.CODEC);
    public static final FoliagePlacerType<GirasolFoliagePlacer> GIRASOL_FOLIAGE_PLACER = register("girasol_foliage_placer", GirasolFoliagePlacer.CODEC);
    
    private static <T extends FoliagePlacer> FoliagePlacerType<T> register(String id, Codec<T> codec) {
        return Registry.register(Registries.FOLIAGE_PLACER_TYPE, AylythUtil.id(id), new FoliagePlacerType<>(codec));
    }
}
