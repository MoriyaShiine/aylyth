package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModPotions;
import moriyashiine.aylyth.common.registry.util.WoodSuite;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.loader.impl.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class AylythLanguageProvider extends FabricLanguageProvider {

    protected AylythLanguageProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        // MOD ITEMGROUP

        builder.add(ModItems.GROUP, "Aylyth");

        // BLOCKS

        String ympe = "Ympe";
        addWoodsuite(builder, ModBlocks.YMPE_BLOCKS, ympe);
        builder.add(ModBlocks.FRUIT_BEARING_YMPE_LOG, "Fruit Bearing Ympe Log");
        leaves(builder, ModBlocks.YMPE_LEAVES, ympe);
        String pomegranate = "Pomegranate";
        addWoodsuite(builder, ModBlocks.POMEGRANATE_BLOCKS, pomegranate);
        leaves(builder, ModBlocks.POMEGRANATE_LEAVES, pomegranate);
        String writhewood = "Writhewood";
        addWoodsuite(builder, ModBlocks.WRITHEWOOD_BLOCKS, writhewood);
        leaves(builder, ModBlocks.WRITHEWOOD_LEAVES, writhewood);
        builder.add(ModBlocks.AYLYTH_BUSH, "Aylyth Bush");
        builder.add(ModBlocks.ANTLER_SHOOTS, "Antler Shoots");
        builder.add(ModBlocks.GRIPWEED, "Gripweed");
        builder.add(ModBlocks.NYSIAN_GRAPE_VINE, "Nysian Grape Vines");
        builder.add(ModBlocks.MARIGOLD, "Marigolds");
        builder.add(ModBlocks.OAK_STREWN_LEAVES, "Oak Strewn Leaves");
        builder.add(ModBlocks.YMPE_STREWN_LEAVES, "Ympe Strewn Leaves");
        builder.add(ModBlocks.JACK_O_LANTERN_MUSHROOM, "Jack O'Lantern Mushroom");
        builder.add(ModBlocks.GHOSTCAP_MUSHROOM, "Ghostcap Mushroom Spores");
        builder.add(ModBlocks.OAK_SEEP, "Oak Seep");
        builder.add(ModBlocks.SPRUCE_SEEP, "Spruce Seep");
        builder.add(ModBlocks.DARK_OAK_SEEP, "Dark Oak Seep");
        builder.add(ModBlocks.YMPE_SEEP, "Ympe Seep");

        // ITEMS

        boat(builder, ModItems.YMPE_ITEMS.boat, ympe);
        chestBoat(builder, ModItems.YMPE_ITEMS.chestBoat, ympe);
        boat(builder, ModItems.POMEGRANATE_ITEMS.boat, pomegranate);
        chestBoat(builder, ModItems.POMEGRANATE_ITEMS.chestBoat, pomegranate);
        boat(builder, ModItems.WRITHEWOOD_ITEMS.boat, writhewood);
        chestBoat(builder, ModItems.WRITHEWOOD_ITEMS.chestBoat, writhewood);
        builder.add(ModItems.POMEGRANATE, pomegranate);
        builder.add(ModItems.GHOSTCAP_MUSHROOM, "Ghostcap Mushroom");
        builder.add(ModItems.YMPE_DAGGER, "Ympe Dagger");
        builder.add(ModItems.YMPE_LANCE, "Ympe Lance");
        builder.add(ModItems.GLAIVE, "Glaive");
        builder.add(ModItems.YMPE_FRUIT, "Ympe Fruit");
        builder.add(ModItems.SHUCKED_YMPE_FRUIT, "Shucked Ympe Fruit");
        builder.add(ModItems.NYSIAN_GRAPES, "Nysian Grapes");
        builder.add(ModItems.CORIC_SEED, "Coric Seed");
        builder.add(ModItems.AYLYTHIAN_HEART, "Aylythian Heart");
        builder.add(ModItems.PILOT_LIGHT_SPAWN_EGG, "Pilot Light Spawn Egg");
        builder.add(ModItems.AYLYTHIAN_SPAWN_EGG, "Aylythian Spawn Egg");
        builder.add(ModItems.ELDER_AYLYTHIAN_SPAWN_EGG, "Elder Aylythian Spawn Egg");
        builder.add(ModItems.SOULMOULD_ITEM, "Soulmould");
        builder.add(ModItems.SOULTRAP_EFFIGY_ITEM, "Soultrap Effigy");
        builder.add(ModItems.BONEFLY_SKULL, "Bonefly Skull");
        potionSet(builder, "mortechis");
        potionSet(builder, "cimmerian");
        potionSet(builder, "wyrded");

        // STATUS EFFECTS

        builder.add(ModPotions.MORTECHIS_EFFECT, "Mortechis");
        builder.add(ModPotions.CIMMERIAN_EFFECT, "Cimmerian");
        builder.add(ModPotions.WYRDED_EFFECT, "Wyrded");

        // ENTITIES

        builder.add(ModEntityTypes.PILOT_LIGHT, "Pilot Light");
        builder.add(ModEntityTypes.AYLYTHIAN, "Aylythian");
        builder.add(ModEntityTypes.ELDER_AYLYTHIAN, "Elder Aylythian");
        builder.add(ModEntityTypes.YMPE_LANCE, "Ympe Lance");

        // DEATH MESSAGES

        builder.add("death.attack.ympe", "%1$s has gone to the trees");
        builder.add("death.attack.ympe.player", "%1$s has gone to the trees whilst trying to escape %2$s");

        // SUBTITLES

        builder.add("aylyth.subtitles.block.ympe_log.pick_fruit", "Fruit picked");
        builder.add("aylyth.subtitles.entity.player.increase_ympe_infestation_stage", "Branches spread");
        builder.add("aylyth.subtitles.entity.generic.shucked", "Entity shucked");
        builder.add("aylyth.subtitles.entity.aylythian.ambient", "Aylythian groans");
        builder.add("aylyth.subtitles.entity.aylythian.hurt", "Aylythian hurts");
        builder.add("aylyth.subtitles.entity.aylythian.death", "Aylythian dies");
        builder.add("aylyth.subtitles.entity.elder_aylythian.ambient", "Elder Aylythian rumbles");
        builder.add("aylyth.subtitles.entity.elder_aylythian.hurt", "Elder Aylythian hurts");
        builder.add("aylyth.subtitles.entity.elder_aylythian.death", "Elder Aylythian dies");
        builder.add("aylyth.subtitles.entity.soulmould.ambient", "Soulmould groans");
        builder.add("aylyth.subtitles.entity.soulmould.attack", "Soulmould attack");
        builder.add("aylyth.subtitles.entity.soulmould.damage", "Soulmould hurt");
        builder.add("aylyth.subtitles.entity.soulmould.death", "Soulmould dies");
        builder.add("aylyth.subtitles.block.stick_break", "Stick cracks");
        builder.add("aylyth.subtitles.block.strewn_leaves.step", "Leaves crunch");
        builder.add("aylyth.subtitles.block.strewn_leaves.pile_destroy", "Leaves scatter");
        builder.add("aylyth.subtitles.block.strewn_leaves.pile_step", "Leaf pile crunches");

        // INFO

        builder.add("info.tot.mould_activate", "Soulmould Active");
        builder.add("info.tot.mould_deactivate", "Soulmould De-active");

        // COMPAT - REI

        builder.add("rei.aylyth.ympe_dagger_drops", "Ympe Dagger Drops");
    }

    private void potionSet(TranslationBuilder builder, String effectName) {
        builder.add("item.minecraft.potion.effect.%s".formatted(effectName), "Potion of %s".formatted(StringUtil.capitalize(effectName)));
        builder.add("item.minecraft.splash_potion.effect.%s".formatted(effectName), "Splash Potion of %s".formatted(StringUtil.capitalize(effectName)));
        builder.add("item.minecraft.tipped_arrow.effect.%s".formatted(effectName), "Arrow of %s".formatted(StringUtil.capitalize(effectName)));
        builder.add("item.minecraft.lingering_potion.effect.%s".formatted(effectName), "Lingering Potion of %s".formatted(StringUtil.capitalize(effectName)));
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
