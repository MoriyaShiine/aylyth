package moriyashiine.aylyth.common.item;

import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public interface AylythItemGroups {
    RegistryKey<ItemGroup> MAIN = RegistryKey.of(RegistryKeys.ITEM_GROUP, Aylyth.id(Aylyth.MOD_ID));

    static void register() {
        Registry.register(Registries.ITEM_GROUP, MAIN, FabricItemGroup.builder()
                .icon(AylythItems.YMPE_DAGGER::getDefaultStack)
                .displayName(Text.translatable("itemGroup.aylyth.main"))
                .entries((displayContext, entries) -> {
                    if (displayContext.hasPermissions()) {
                        entries.add(AylythItems.DEBUG_WAND);
                    }

                    entries.add(AylythItems.YMPE_STRIPPED_LOG);
                    entries.add(AylythItems.YMPE_STRIPPED_WOOD);
                    entries.add(AylythItems.YMPE_LOG);
                    entries.add(AylythItems.YMPE_WOOD);
                    entries.add(AylythItems.YMPE_SAPLING);
                    entries.add(AylythItems.YMPE_PLANKS);
                    entries.add(AylythItems.YMPE_STAIRS);
                    entries.add(AylythItems.YMPE_SLAB);
                    entries.add(AylythItems.YMPE_FENCE);
                    entries.add(AylythItems.YMPE_FENCE_GATE);
                    entries.add(AylythItems.YMPE_PRESSURE_PLATE);
                    entries.add(AylythItems.YMPE_BUTTON);
                    entries.add(AylythItems.YMPE_TRAPDOOR);
                    entries.add(AylythItems.YMPE_DOOR);
                    entries.add(AylythItems.YMPE_SIGN);
                    entries.add(AylythItems.YMPE_BOAT);
                    entries.add(AylythItems.YMPE_CHEST_BOAT);
                    entries.add(AylythItems.YMPE_HANGING_SIGN);
                    entries.add(AylythItems.YMPE_LEAVES);
                    entries.add(AylythItems.FRUIT_BEARING_YMPE_LOG);

                    entries.add(AylythItems.POMEGRANATE_STRIPPED_LOG);
                    entries.add(AylythItems.POMEGRANATE_STRIPPED_WOOD);
                    entries.add(AylythItems.POMEGRANATE_LOG);
                    entries.add(AylythItems.POMEGRANATE_WOOD);
                    entries.add(AylythItems.POMEGRANATE_SAPLING);
                    entries.add(AylythItems.POMEGRANATE_PLANKS);
                    entries.add(AylythItems.POMEGRANATE_STAIRS);
                    entries.add(AylythItems.POMEGRANATE_SLAB);
                    entries.add(AylythItems.POMEGRANATE_FENCE);
                    entries.add(AylythItems.POMEGRANATE_FENCE_GATE);
                    entries.add(AylythItems.POMEGRANATE_PRESSURE_PLATE);
                    entries.add(AylythItems.POMEGRANATE_BUTTON);
                    entries.add(AylythItems.POMEGRANATE_TRAPDOOR);
                    entries.add(AylythItems.POMEGRANATE_DOOR);
                    entries.add(AylythItems.POMEGRANATE_SIGN);
                    entries.add(AylythItems.POMEGRANATE_BOAT);
                    entries.add(AylythItems.POMEGRANATE_CHEST_BOAT);
                    entries.add(AylythItems.POMEGRANATE_HANGING_SIGN);
                    entries.add(AylythItems.POMEGRANATE_LEAVES);

                    entries.add(AylythItems.WRITHEWOOD_STRIPPED_LOG);
                    entries.add(AylythItems.WRITHEWOOD_STRIPPED_WOOD);
                    entries.add(AylythItems.WRITHEWOOD_LOG);
                    entries.add(AylythItems.WRITHEWOOD_WOOD);
                    entries.add(AylythItems.WRITHEWOOD_SAPLING);
                    entries.add(AylythItems.WRITHEWOOD_PLANKS);
                    entries.add(AylythItems.WRITHEWOOD_STAIRS);
                    entries.add(AylythItems.WRITHEWOOD_SLAB);
                    entries.add(AylythItems.WRITHEWOOD_FENCE);
                    entries.add(AylythItems.WRITHEWOOD_FENCE_GATE);
                    entries.add(AylythItems.WRITHEWOOD_PRESSURE_PLATE);
                    entries.add(AylythItems.WRITHEWOOD_BUTTON);
                    entries.add(AylythItems.WRITHEWOOD_TRAPDOOR);
                    entries.add(AylythItems.WRITHEWOOD_DOOR);
                    entries.add(AylythItems.WRITHEWOOD_SIGN);
                    entries.add(AylythItems.WRITHEWOOD_BOAT);
                    entries.add(AylythItems.WRITHEWOOD_CHEST_BOAT);
                    entries.add(AylythItems.WRITHEWOOD_HANGING_SIGN);
                    entries.add(AylythItems.WRITHEWOOD_LEAVES);

                    entries.add(AylythItems.SEEPING_WOOD);
                    entries.add(AylythItems.GIRASOL_SEED);

                    entries.add(AylythItems.CHTHONIA_WOOD);
                    entries.add(AylythItems.NEPHRITIC_CHTHONIA_WOOD);

                    entries.add(AylythItems.AYLYTH_BUSH);
                    entries.add(AylythItems.ANTLER_SHOOTS);
                    entries.add(AylythItems.GRIPWEED);
                    entries.add(AylythItems.NYSIAN_GRAPE_VINE);
                    entries.add(AylythItems.MARIGOLD);
                    entries.add(AylythItems.OAK_STREWN_LEAVES);
                    entries.add(AylythItems.YMPE_STREWN_LEAVES);
                    entries.add(AylythItems.JACK_O_LANTERN_MUSHROOM);
                    entries.add(AylythItems.GHOSTCAP_MUSHROOM_SPORES);

                    entries.add(AylythItems.JACK_O_LANTERN_MUSHROOM_STEM);
                    entries.add(AylythItems.JACK_O_LANTERN_MUSHROOM_BLOCK);

                    entries.add(AylythItems.SMALL_WOODY_GROWTH);
                    entries.add(AylythItems.LARGE_WOODY_GROWTH);
                    entries.add(AylythItems.WOODY_GROWTH_CACHE);

                    entries.add(AylythItems.OAK_SEEP);
                    entries.add(AylythItems.SPRUCE_SEEP);
                    entries.add(AylythItems.DARK_OAK_SEEP);
                    entries.add(AylythItems.YMPE_SEEP);
                    entries.add(AylythItems.SEEPING_WOOD_SEEP);
                    entries.add(AylythItems.DARK_WOODS_TILES);
                    entries.add(AylythItems.BARK);

                    entries.add(AylythItems.LANCEOLATE_DAGGER);
                    entries.add(AylythItems.YMPE_DAGGER);
                    entries.add(AylythItems.YMPE_GLAIVE);
                    entries.add(AylythItems.YMPE_LANCE);
                    entries.add(AylythItems.YMPE_FLAMBERGE);
                    entries.add(AylythItems.YMPE_SCYTHE);

                    entries.add(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE);
                    entries.add(AylythItems.CORIC_SEED);
                    entries.add(AylythItems.ESSTLINE);
                    entries.add(AylythItems.NEPHRITE);
                    entries.add(AylythItems.BLIGHTED_THORNS);

                    entries.add(AylythItems.ESSTLINE_BLOCK);
                    entries.add(AylythItems.NEPHRITE_BLOCK);
                    entries.add(AylythItems.CARVED_SMOOTH_NEPHRITE);
                    entries.add(AylythItems.CARVED_ANTLERED_NEPHRITE);
                    entries.add(AylythItems.CARVED_NEPHRITE_PILLAR);
                    entries.add(AylythItems.CARVED_NEPHRITE_TILES);
                    entries.add(AylythItems.CARVED_WOODY_NEPHRITE);

                    entries.add(AylythItems.NEPHRITE_SWORD);
                    entries.add(AylythItems.NEPHRITE_SHOVEL);
                    entries.add(AylythItems.NEPHRITE_PICKAXE);
                    entries.add(AylythItems.NEPHRITE_AXE);
                    entries.add(AylythItems.NEPHRITE_HOE);

                    entries.add(AylythItems.VAMPIRIC_SWORD);
                    entries.add(AylythItems.VAMPIRIC_PICKAXE);
                    entries.add(AylythItems.VAMPIRIC_AXE);
                    entries.add(AylythItems.VAMPIRIC_HOE);

                    entries.add(AylythItems.BLIGHTED_SWORD);
                    entries.add(AylythItems.BLIGHTED_PICKAXE);
                    entries.add(AylythItems.BLIGHTED_AXE);
                    entries.add(AylythItems.BLIGHTED_HOE);

                    entries.add(AylythItems.THORN_FLECHETTE);
                    entries.add(AylythItems.BLIGHTED_THORN_FLECHETTE);

                    entries.add(AylythItems.YMPE_CUIRASS);
                    entries.add(AylythItems.YMPE_EFFIGY);
                    entries.add(AylythItems.NEPHRITE_FLASK);
                    entries.add(AylythItems.DARK_NEPHRITE_FLASK);

                    entries.add(AylythItems.YMPE_MUSH);
                    entries.add(AylythItems.YMPE_FRUIT);
                    entries.add(AylythItems.SHUCKED_YMPE_FRUIT);

                    entries.add(AylythItems.NYSIAN_GRAPES);
                    entries.add(AylythItems.GHOSTCAP_MUSHROOM);
                    entries.add(AylythItems.POMEGRANATE);

                    entries.add(AylythItems.WRONGMEAT);
                    entries.add(AylythItems.AYLYTHIAN_HEART);
                    entries.add(AylythItems.NEPHRITE_HEART);
                    entries.add(AylythItems.YHONDYTH_HEART);

                    entries.add(AylythItems.SOUL_HEARTH);
                    entries.add(AylythItems.VITAL_THURIBLE);
                    entries.add(AylythItems.BLACK_WELL);

                    entries.add(AylythItems.PILOT_LIGHT_SPAWN_EGG);
                    entries.add(AylythItems.AYLYTHIAN_SPAWN_EGG);
                    entries.add(AylythItems.ELDER_AYLYTHIAN_SPAWN_EGG);
                    entries.add(AylythItems.FAUNAYLYTHIAN_SPAWN_EGG);
                    entries.add(AylythItems.WREATHED_HIND_SPAWN_EGG);
                    entries.add(AylythItems.SCION_SPAWN_EGG);
                    entries.add(AylythItems.YMPEMOULD_SPAWN_EGG);
                    entries.add(AylythItems.BONEFLY_SPAWN_EGG);
                    entries.add(AylythItems.TULPA_SPAWN_EGG);

                    entries.add(AylythItems.POMEGRANATE_CASSETTE);
                })
                .build()
        );
    }
}
