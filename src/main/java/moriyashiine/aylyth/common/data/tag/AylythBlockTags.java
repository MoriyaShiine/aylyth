package moriyashiine.aylyth.common.data.tag;

import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface AylythBlockTags {

    TagKey<Block> YMPE_LOGS = bind("ympe_logs");
    TagKey<Block> POMEGRANATE_LOGS = bind("pomegranate_logs");
    TagKey<Block> WRITHEWOOD_LOGS = bind("writhewood_logs");
    TagKey<Block> SEEPS = bind("seeps");
    TagKey<Block> CARVED_NEPHRITE = bind("carved_nephrite");
    TagKey<Block> WOODY_GROWTHS = bind("woody_growths");
    TagKey<Block> CHTHONIA_WOOD = bind("chthonia_wood");
    TagKey<Block> JACK_O_LANTERN_GENERATE_ON = bind("jack_o_lantern_generate_on");
    TagKey<Block> WOODY_GROWTHS_GENERATE_ON = bind("woody_growths_generate_on");
    TagKey<Block> GHOSTCAP_REPLACEABLE = bind("ghostcap_replaceable");
    TagKey<Block> SCION_REPELLENT = bind("scion_repellent");

    private static TagKey<Block> bind(String name) {
        return TagKey.of(RegistryKeys.BLOCK, AylythUtil.id(name));
    }
}
