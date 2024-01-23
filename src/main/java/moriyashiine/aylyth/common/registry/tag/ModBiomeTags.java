package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class ModBiomeTags {
    public static final TagKey<Biome> IS_CLEARING = createTag("is_clearing");
    public static final TagKey<Biome> GENERATES_SEEP = createTag("generates_seep");
    public static final TagKey<Biome> IS_COPSE = createTag("is_copse");
    public static final TagKey<Biome> IS_DEEPWOOD = createTag("is_deepwoods");
    public static final TagKey<Biome> IS_CONIFEROUS = createTag("is_coniferous");

    private static TagKey<Biome> createTag(String tag) {
        return TagKey.of(RegistryKeys.BIOME, AylythUtil.id(tag));
    }
}
