package moriyashiine.aylyth.datagen.common.tag;

import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.data.tag.AylythBlockTags;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.bewitchment.common.registry.BWTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
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
        copy(AylythBlockTags.WOODY_GROWTHS, AylythItemTags.WOODY_GROWTHS);
        copy(AylythBlockTags.SEEPS, AylythItemTags.SEEPS);
        copy(AylythBlockTags.CARVED_NEPHRITE, AylythItemTags.CARVED_NEPHRITE);
        copy(AylythBlockTags.CHTHONIA_WOOD, AylythItemTags.CHTHONIA_WOOD);
        getOrCreateTagBuilder(ItemTags.BOATS).add(ModItems.YMPE_BOAT, ModItems.POMEGRANATE_BOAT, ModItems.WRITHEWOOD_BOAT);
        getOrCreateTagBuilder(ItemTags.CHEST_BOATS).add(ModItems.YMPE_CHEST_BOAT, ModItems.POMEGRANATE_CHEST_BOAT, ModItems.WRITHEWOOD_CHEST_BOAT);
        getOrCreateTagBuilder(AylythItemTags.DECREASES_BRANCHES).add(ModItems.YMPE_FRUIT);
        getOrCreateTagBuilder(AylythItemTags.PLEDGE_ITEMS).add(ModItems.NYSIAN_GRAPES);
        getOrCreateTagBuilder(AylythItemTags.BOSS_HEARTS).add(ModItems.YHONDYTH_HEART);
        getOrCreateTagBuilder(AylythItemTags.NEPHRITE_TOOL_MATERIALS).add(ModItems.NEPHRITE);
        getOrCreateTagBuilder(ItemTags.SWORDS).add(ModItems.YMPE_DAGGER, ModItems.NEPHRITE_SWORD, ModItems.VAMPIRIC_SWORD, ModItems.BLIGHTED_SWORD);
        getOrCreateTagBuilder(ItemTags.SHOVELS).add(ModItems.NEPHRITE_SHOVEL);
        getOrCreateTagBuilder(ItemTags.PICKAXES).add(ModItems.NEPHRITE_PICKAXE, ModItems.VAMPIRIC_PICKAXE, ModItems.BLIGHTED_PICKAXE);
        getOrCreateTagBuilder(ItemTags.AXES).add(ModItems.NEPHRITE_AXE, ModItems.VAMPIRIC_AXE, ModItems.BLIGHTED_AXE);
        getOrCreateTagBuilder(ItemTags.HOES).add(ModItems.NEPHRITE_HOE, ModItems.VAMPIRIC_HOE, ModItems.BLIGHTED_HOE);
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(ModItems.POMEGRANATE_CASSETTE);
        getOrCreateTagBuilder(ItemTags.COALS).add(ModItems.BARK);
        getOrCreateTagBuilder(AylythItemTags.YMPE_WEAPONS).add(ModItems.YMPE_DAGGER, ModItems.YMPE_LANCE, ModItems.YMPE_GLAIVE, ModItems.YMPE_FLAMBERGE, ModItems.YMPE_SCYTHE);
        getOrCreateTagBuilder(AylythItemTags.VAMPIRIC_WEAPON).add(ModItems.VAMPIRIC_AXE, ModItems.VAMPIRIC_HOE, ModItems.VAMPIRIC_PICKAXE, ModItems.VAMPIRIC_SWORD);
        getOrCreateTagBuilder(AylythItemTags.BLIGHTED_WEAPON).add(ModItems.BLIGHTED_AXE, ModItems.BLIGHTED_HOE, ModItems.BLIGHTED_PICKAXE, ModItems.BLIGHTED_SWORD);
        getOrCreateTagBuilder(AylythItemTags.NEPHRITE_FLASKS).add(ModItems.NEPHRITE_FLASK, ModItems.DARK_NEPHRITE_FLASK);
        getOrCreateTagBuilder(AylythItemTags.HEART_HARVESTERS).add(ModItems.LANCEOLATE_DAGGER, ModItems.YMPE_DAGGER);

        getOrCreateTagBuilder(BWTags.BARKS).add(ModItems.BARK);
    }
}