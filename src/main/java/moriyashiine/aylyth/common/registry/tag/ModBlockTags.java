package moriyashiine.aylyth.common.registry.tag;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
    public static final TagKey<Block> YMPE_LOGS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "ympe_logs"));
    public static final TagKey<Block> POMEGRANATE_LOGS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "pomegranate_logs"));
    public static final TagKey<Block> WRITHEWOOD_LOGS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "writhewood_logs"));
    public static final TagKey<Block> SEEPS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "seeps"));
    public static final TagKey<Block> JACK_O_LANTERN_GENERATE_ON = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "jack_o_lantern_generate_on"));
    public static final TagKey<Block> GHOSTCAP_REPLACEABLE = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "ghostcap_replaceable"));
    public static final TagKey<Block> SCION_REPELLENT = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "scion_repellent"));
    public static final TagKey<Block> WOODY_GROWTHS = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "woody_growths"));
    public static final TagKey<Block> WOODY_GROWTHS_GENERATE_ON = TagKey.of(RegistryKeys.BLOCK, new Identifier(Aylyth.MOD_ID, "woody_growths_generate_on"));
}
