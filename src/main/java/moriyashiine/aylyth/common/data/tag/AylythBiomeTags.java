package moriyashiine.aylyth.common.data.tag;

import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public interface AylythBiomeTags {

    // Used for specifying vanilla biomes to generate seep in
    TagKey<Biome> GENERATES_SEEP = bind("generates_seep");

    TagKey<Biome> IS_CLEARING = bind("is_clearing");
    TagKey<Biome> IS_COPSE = bind("is_copse");
    TagKey<Biome> IS_DEEPWOOD = bind("is_deepwoods");
    TagKey<Biome> IS_CONIFEROUS = bind("is_coniferous");
    TagKey<Biome> IS_FOREST_LIKE = bind("is_forest_like");
    TagKey<Biome> IS_TAIGA_LIKE = bind("is_taiga_like");

    TagKey<Biome> HAS_WEAK_FOG = bind("has_weak_fog");
    TagKey<Biome> HAS_AVERAGE_FOG = bind("has_average_fog");
    TagKey<Biome> HAS_STRONG_FOG = bind("has_strong_fog");

    private static TagKey<Biome> bind(String name) {
        return TagKey.of(RegistryKeys.BIOME, AylythUtil.id(name));
    }
}
