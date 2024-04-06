package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModBlockTags {
    public static final TagKey<Block> YMPE_LOGS = create("ympe_logs");
    public static final TagKey<Block> POMEGRANATE_LOGS = create("pomegranate_logs");
    public static final TagKey<Block> WRITHEWOOD_LOGS = create("writhewood_logs");
    public static final TagKey<Block> SEEPS = create("seeps");
    public static final TagKey<Block> CARVED_NEPHRITE = create("carved_nephrite");
    public static final TagKey<Block> WOODY_GROWTHS = create("woody_growths");
    public static final TagKey<Block> CHTHONIA_WOOD = create("chthonia_wood");
    public static final TagKey<Block> JACK_O_LANTERN_GENERATE_ON = create("jack_o_lantern_generate_on");
    public static final TagKey<Block> WOODY_GROWTHS_GENERATE_ON = create("woody_growths_generate_on");
    public static final TagKey<Block> GHOSTCAP_REPLACEABLE = create("ghostcap_replaceable");
    public static final TagKey<Block> SCION_REPELLENT = create("scion_repellent");

    private static TagKey<Block> create(String tag) {
        return TagKey.of(RegistryKeys.BLOCK, AylythUtil.id(tag));
    }
}
