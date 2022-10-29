package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.util.WoodSuite;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class AylythLanguageProvider extends FabricLanguageProvider {

    protected AylythLanguageProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.GHOSTCAP_MUSHROOM, "Ghostcap Mushroom");
        String pomegranate = "Pomegranate";
        translationBuilder.add(ModItems.POMEGRANATE, pomegranate);
        addWoodsuite(translationBuilder, ModBlocks.POMEGRANATE_BLOCKS, pomegranate);
        boat(translationBuilder, ModItems.POMEGRANATE_ITEMS.boat, pomegranate);
        chestBoat(translationBuilder, ModItems.POMEGRANATE_ITEMS.chestBoat, pomegranate);
        leaves(translationBuilder, ModBlocks.POMEGRANATE_LEAVES, pomegranate);
    }

    private void leaves(TranslationBuilder builder, Block block, String woodName) {
        builder.add(block, "%s Leaves".formatted(woodName));
    }

    private void chestBoat(TranslationBuilder builder, Item boatItem, String woodName) {
        builder.add(boatItem, "%s Boat with Chest".formatted(woodName));
    }

    private void boat(TranslationBuilder builder, Item boatItem, String woodName) {
        builder.add(boatItem, "%s Boat".formatted(woodName));
    }

    private void addWoodsuite(TranslationBuilder builder, WoodSuite suite, String woodName) {
        builder.add(suite.strippedLog, "Stripped %s Log".formatted(woodName));
        builder.add(suite.strippedWood, "Stripped %s Wood".formatted(woodName));
        builder.add(suite.log, "%s Log".formatted(woodName));
        builder.add(suite.wood, "%s Wood".formatted(woodName));
        builder.add(suite.sapling, "%s Sapling".formatted(woodName));
        builder.add(suite.pottedSapling, "Potted %s Sapling".formatted(woodName));
        builder.add(suite.planks, "%s Planks".formatted(woodName));
        builder.add(suite.stairs, "%s Stairs".formatted(woodName));
        builder.add(suite.slab, "%s Slab".formatted(woodName));
        builder.add(suite.fence, "%s Fence".formatted(woodName));
        builder.add(suite.fenceGate, "%s Fence Gate".formatted(woodName));
        builder.add(suite.pressurePlate, "%s Pressure Plate".formatted(woodName));
        builder.add(suite.button, "%s Button".formatted(woodName));
        builder.add(suite.trapdoor, "%s Trapdoor".formatted(woodName));
        builder.add(suite.door, "%s Door".formatted(woodName));
        builder.add(suite.floorSign, "%s Sign".formatted(woodName));
    }
}
