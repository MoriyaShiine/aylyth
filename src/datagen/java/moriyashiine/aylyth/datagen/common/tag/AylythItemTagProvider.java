package moriyashiine.aylyth.datagen.common.tag;

import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.data.tag.AylythBlockTags;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.bewitchment.common.registry.BWTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class AylythItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public AylythItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries, @Nullable BlockTagProvider blockTagProvider) {
        super(output, registries, blockTagProvider);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        copy(AylythBlockTags.YMPE_LOGS, AylythItemTags.YMPE_LOGS);
        copy(AylythBlockTags.POMEGRANATE_LOGS, AylythItemTags.POMEGRANATE_LOGS);
        copy(AylythBlockTags.WRITHEWOOD_LOGS, AylythItemTags.WRITHEWOOD_LOGS);
        copy(AylythBlockTags.WOODY_GROWTHS, AylythItemTags.WOODY_GROWTHS);
        copy(AylythBlockTags.SEEPS, AylythItemTags.SEEPS);
        copy(AylythBlockTags.CARVED_NEPHRITE, AylythItemTags.CARVED_NEPHRITE);
        copy(AylythBlockTags.CHTHONIA_WOOD, AylythItemTags.CHTHONIA_WOOD);
        copy(AylythBlockTags.STORAGE_BLOCKS_ESSTLINE, AylythItemTags.STORAGE_BLOCKS_ESSTLINE);
        copy(AylythBlockTags.STORAGE_BLOCKS_NEPHRITE, AylythItemTags.STORAGE_BLOCKS_NEPHRITE);
        getOrCreateTagBuilder(AylythItemTags.DECREASES_BRANCHES).add(AylythItems.YMPE_FRUIT, AylythItems.YMPE_MUSH);
        getOrCreateTagBuilder(AylythItemTags.PLEDGE_ITEMS).add(AylythItems.NYSIAN_GRAPES);
        getOrCreateTagBuilder(AylythItemTags.BOSS_HEARTS).add(AylythItems.YHONDYTH_HEART, Items.NETHER_STAR);
        getOrCreateTagBuilder(AylythItemTags.NEPHRITE_TOOL_MATERIALS).add(AylythItems.NEPHRITE);
        getOrCreateTagBuilder(AylythItemTags.YMPE_WEAPONS).add(AylythItems.YMPE_DAGGER, AylythItems.YMPE_LANCE, AylythItems.YMPE_GLAIVE, AylythItems.YMPE_FLAMBERGE, AylythItems.YMPE_SCYTHE);
        getOrCreateTagBuilder(AylythItemTags.VAMPIRIC_WEAPONS).add(AylythItems.VAMPIRIC_AXE, AylythItems.VAMPIRIC_HOE, AylythItems.VAMPIRIC_PICKAXE, AylythItems.VAMPIRIC_SWORD);
        getOrCreateTagBuilder(AylythItemTags.BLIGHTED_WEAPONS).add(AylythItems.BLIGHTED_AXE, AylythItems.BLIGHTED_HOE, AylythItems.BLIGHTED_PICKAXE, AylythItems.BLIGHTED_SWORD);
        getOrCreateTagBuilder(AylythItemTags.NEPHRITE_FLASKS).add(AylythItems.NEPHRITE_FLASK, AylythItems.DARK_NEPHRITE_FLASK);
        getOrCreateTagBuilder(AylythItemTags.DAGGERS).add(AylythItems.LANCEOLATE_DAGGER, AylythItems.YMPE_DAGGER);
        getOrCreateTagBuilder(AylythItemTags.HEART_HARVESTERS).add(AylythItems.LANCEOLATE_DAGGER, AylythItems.YMPE_DAGGER);
        getOrCreateTagBuilder(AylythItemTags.YMPE_FRUIT_HARVESTERS).add(AylythItems.LANCEOLATE_DAGGER, AylythItems.YMPE_DAGGER);
        getOrCreateTagBuilder(AylythItemTags.STRIPS_OFF_BARK).add(AylythItems.LANCEOLATE_DAGGER, AylythItems.YMPE_DAGGER);
        getOrCreateTagBuilder(AylythItemTags.DECREASES_BRANCHES_1_IN_4).add(AylythItems.YMPE_MUSH);

        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        getOrCreateTagBuilder(ItemTags.SWORDS).add(AylythItems.YMPE_DAGGER, AylythItems.NEPHRITE_SWORD, AylythItems.VAMPIRIC_SWORD, AylythItems.BLIGHTED_SWORD);
        getOrCreateTagBuilder(ItemTags.SHOVELS).add(AylythItems.NEPHRITE_SHOVEL);
        getOrCreateTagBuilder(ItemTags.PICKAXES).add(AylythItems.NEPHRITE_PICKAXE, AylythItems.VAMPIRIC_PICKAXE, AylythItems.BLIGHTED_PICKAXE);
        getOrCreateTagBuilder(ItemTags.AXES).add(AylythItems.NEPHRITE_AXE, AylythItems.VAMPIRIC_AXE, AylythItems.BLIGHTED_AXE);
        getOrCreateTagBuilder(ItemTags.HOES).add(AylythItems.NEPHRITE_HOE, AylythItems.VAMPIRIC_HOE, AylythItems.BLIGHTED_HOE);
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(AylythItems.POMEGRANATE_CASSETTE);
        getOrCreateTagBuilder(ItemTags.COALS).add(AylythItems.BARK);
        getOrCreateTagBuilder(ItemTags.BOATS).add(AylythItems.YMPE_BOAT, AylythItems.POMEGRANATE_BOAT, AylythItems.WRITHEWOOD_BOAT);
        getOrCreateTagBuilder(ItemTags.CHEST_BOATS).add(AylythItems.YMPE_CHEST_BOAT, AylythItems.POMEGRANATE_CHEST_BOAT, AylythItems.WRITHEWOOD_CHEST_BOAT);

        getOrCreateTagBuilder(BWTags.BARKS).add(AylythItems.BARK);
    }
}