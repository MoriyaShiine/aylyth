package moriyashiine.aylyth.datagen.common.tag;

import moriyashiine.aylyth.common.data.tag.AylythBlockTags;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static moriyashiine.aylyth.common.item.AylythItems.*;

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
        copy(AylythBlockTags.SAPSTONES, AylythItemTags.SAPSTONES);
        copy(AylythBlockTags.BARE_BRANCHES, AylythItemTags.BARE_BRANCHES);
        copy(AylythBlockTags.LEAFY_BRANCHES, AylythItemTags.LEAFY_BRANCHES);
        copy(AylythBlockTags.BRANCHES, AylythItemTags.BRANCHES);
        getOrCreateTagBuilder(AylythItemTags.DECREASES_BRANCHES).add(YMPE_FRUIT, YMPE_MUSH);
        getOrCreateTagBuilder(AylythItemTags.PLEDGE_ITEMS).add(NYSIAN_GRAPES);
        getOrCreateTagBuilder(AylythItemTags.BOSS_HEARTS).forceAddTag(ConventionalItemTags.NETHER_STARS).add(YHONDYTH_HEART);
        getOrCreateTagBuilder(AylythItemTags.NEPHRITE_TOOL_MATERIALS).add(NEPHRITE);
        getOrCreateTagBuilder(AylythItemTags.YMPE_WEAPONS).add(YMPE_DAGGER, YMPE_LANCE, YMPE_GLAIVE, YMPE_FLAMBERGE, YMPE_SCYTHE);
        getOrCreateTagBuilder(AylythItemTags.VAMPIRIC_WEAPONS).add(VAMPIRIC_AXE, VAMPIRIC_HOE, VAMPIRIC_PICKAXE, VAMPIRIC_SWORD);
        getOrCreateTagBuilder(AylythItemTags.BLIGHTED_WEAPONS).add(BLIGHTED_AXE, BLIGHTED_HOE, BLIGHTED_PICKAXE, BLIGHTED_SWORD);
        getOrCreateTagBuilder(AylythItemTags.NEPHRITE_FLASKS).add(NEPHRITE_FLASK, DARK_NEPHRITE_FLASK);
        getOrCreateTagBuilder(AylythItemTags.FLESH_HARVESTERS).add(LANCEOLATE_DAGGER, YMPE_DAGGER);
        getOrCreateTagBuilder(AylythItemTags.YMPE_FRUIT_HARVESTERS).add(LANCEOLATE_DAGGER, YMPE_DAGGER);
        getOrCreateTagBuilder(AylythItemTags.STRIPS_OFF_BARK).add(LANCEOLATE_DAGGER, YMPE_DAGGER);
        getOrCreateTagBuilder(AylythItemTags.FLECHETTE_TOOLS).add(THORN_FLECHETTE, BLIGHTED_THORN_FLECHETTE);
        getOrCreateTagBuilder(AylythItemTags.DAGGERS).add(LANCEOLATE_DAGGER, YMPE_DAGGER);
        getOrCreateTagBuilder(AylythItemTags.NEPHRITE_GEMS).add(NEPHRITE);
        getOrCreateTagBuilder(AylythItemTags.ESSTLINE_CLUMPS).add(ESSTLINE);

        getOrCreateTagBuilder(ConventionalItemTags.SPEAR_TOOLS).add(YMPE_LANCE);
        getOrCreateTagBuilder(ConventionalItemTags.MELEE_WEAPON_TOOLS)
                .add(YMPE_LANCE, LANCEOLATE_DAGGER, YMPE_DAGGER, YMPE_GLAIVE, YMPE_FLAMBERGE, YMPE_SCYTHE, NEPHRITE_SWORD, VAMPIRIC_SWORD, BLIGHTED_SWORD, NEPHRITE_AXE, VAMPIRIC_AXE, BLIGHTED_AXE);
        getOrCreateTagBuilder(ConventionalItemTags.RANGED_WEAPON_TOOLS).add(YMPE_LANCE, THORN_FLECHETTE, BLIGHTED_THORN_FLECHETTE);
        getOrCreateTagBuilder(ConventionalItemTags.MINING_TOOL_TOOLS).add(NEPHRITE_PICKAXE, VAMPIRIC_PICKAXE, BLIGHTED_PICKAXE);
        getOrCreateTagBuilder(ConventionalItemTags.GEMS).addTag(AylythItemTags.NEPHRITE_GEMS);
        getOrCreateTagBuilder(ConventionalItemTags.CLUMPS).addTag(AylythItemTags.ESSTLINE_CLUMPS);
        getOrCreateTagBuilder(ConventionalItemTags.FRUIT_FOODS).add(YMPE_FRUIT, NYSIAN_GRAPES, POMEGRANATE);
        getOrCreateTagBuilder(ConventionalItemTags.BERRY_FOODS).add(NYSIAN_GRAPES);
        getOrCreateTagBuilder(ConventionalItemTags.FOOD_POISONING_FOODS).add(GHOSTCAP_MUSHROOM);
        getOrCreateTagBuilder(ConventionalItemTags.RAW_MEAT_FOODS).add(WRONGMEAT);
        getOrCreateTagBuilder(ConventionalItemTags.MUSHROOMS).add(GHOSTCAP_MUSHROOM);
        getOrCreateTagBuilder(ConventionalItemTags.MUSIC_DISCS).add(POMEGRANATE_CASSETTE);

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
        copy(BlockTags.DIRT, ItemTags.DIRT);
        copy(ConventionalBlockTags.SMALL_FLOWERS, ConventionalItemTags.SMALL_FLOWERS);
        copy(ConventionalBlockTags.STORAGE_BLOCKS, ConventionalItemTags.STORAGE_BLOCKS);
        copy(ConventionalBlockTags.STRIPPED_LOGS, ConventionalItemTags.STRIPPED_LOGS);
        copy(ConventionalBlockTags.STRIPPED_WOODS, ConventionalItemTags.STRIPPED_WOODS);
        getOrCreateTagBuilder(ItemTags.SWORDS).add(YMPE_DAGGER, LANCEOLATE_DAGGER, NEPHRITE_SWORD, VAMPIRIC_SWORD, BLIGHTED_SWORD);
        getOrCreateTagBuilder(ItemTags.SHOVELS).add(NEPHRITE_SHOVEL);
        getOrCreateTagBuilder(ItemTags.PICKAXES).add(NEPHRITE_PICKAXE, VAMPIRIC_PICKAXE, BLIGHTED_PICKAXE);
        getOrCreateTagBuilder(ItemTags.AXES).add(NEPHRITE_AXE, VAMPIRIC_AXE, BLIGHTED_AXE);
        getOrCreateTagBuilder(ItemTags.HOES).add(NEPHRITE_HOE, VAMPIRIC_HOE, BLIGHTED_HOE);
        getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(POMEGRANATE_CASSETTE);
        getOrCreateTagBuilder(ItemTags.COALS).add(BARK);
        getOrCreateTagBuilder(ItemTags.BOATS).add(YMPE_BOAT, POMEGRANATE_BOAT, WRITHEWOOD_BOAT);
        getOrCreateTagBuilder(ItemTags.CHEST_BOATS).add(YMPE_CHEST_BOAT, POMEGRANATE_CHEST_BOAT, WRITHEWOOD_CHEST_BOAT);
        getOrCreateTagBuilder(ItemTags.TRIDENT_ENCHANTABLE).add(YMPE_LANCE);
        getOrCreateTagBuilder(ItemTags.VANISHING_ENCHANTABLE).add(YMPE_LANCE);
        getOrCreateTagBuilder(ItemTags.MEAT).add(YMPE_MUSH, YMPE_FRUIT, WRONGMEAT);

        // TODO: bewitchment compat
//        getOrCreateTagBuilder(BWTags.BARKS).add(BARK);
    }
}