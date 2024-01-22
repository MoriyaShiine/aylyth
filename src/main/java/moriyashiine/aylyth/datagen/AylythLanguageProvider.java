package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModPotions;
import moriyashiine.aylyth.common.registry.util.WoodSuite;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.loader.impl.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class AylythLanguageProvider extends FabricLanguageProvider {

    protected AylythLanguageProvider(FabricDataOutput output) {
        super(output);
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
        builder.add(ModBlocks.DARK_WOODS_TILES, "Dark Wood Tiles");
        builder.add(ModBlocks.SOUL_HEARTH, "Soul Hearth");
        builder.add(ModBlocks.VITAL_THURIBLE, "Vital Thurible");
        builder.add(ModBlocks.SMALL_WOODY_GROWTH, "Small Woody Growth");
        builder.add(ModBlocks.LARGE_WOODY_GROWTH, "Large Woody Growth");
        builder.add(ModBlocks.WOODY_GROWTH_CACHE, "Woody Growth Cache");
        builder.add(ModBlocks.SEEPING_WOOD, "Seeping Wood");
        builder.add(ModBlocks.SEEPING_WOOD_SEEP, "Seeping Wood Seep");
        builder.add(ModBlocks.GIRASOL_SAPLING, "Girasol Sapling");
        builder.add(ModBlocks.GIRASOL_SAPLING_POTTED, "Potted Girasol Sapling");

        // ITEMS

        builder.add(ModItems.DEBUG_WAND, "Debug Wand");
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
        builder.add(ModItems.YMPE_GLAIVE, "Ympe Glaive");
        builder.add("item.aylyth.glaive.desc_1", "\u00a76\u00a7oIt is the lament of the fallen");
        builder.add("item.aylyth.glaive.desc_2", "\u00a76\u00a7owhich pushes the living onward.");
        builder.add(ModItems.YMPE_FRUIT, "Ympe Fruit");
        builder.add(ModItems.SHUCKED_YMPE_FRUIT, "Shucked Ympe Fruit");
        builder.add(ModItems.NYSIAN_GRAPES, "Nysian Grapes");
        builder.add(ModItems.CORIC_SEED, "Coric Seed");
        builder.add(ModItems.AYLYTHIAN_HEART, "Aylythian Heart");
        builder.add(ModItems.PILOT_LIGHT_SPAWN_EGG, "Pilot Light Spawn Egg");
        builder.add(ModItems.SCION_SPAWN_EGG, "Scion Spawn Egg");
        builder.add(ModItems.FAUNAYLYTHIAN_SPAWN_EGG, "Faunaylytian Spawn Egg");
        builder.add(ModItems.WREATHED_HIND_SPAWN_EGG, "Wreathed Hind Spawn Egg");
        builder.add(ModItems.AYLYTHIAN_SPAWN_EGG, "Aylythian Spawn Egg");
        builder.add(ModItems.ELDER_AYLYTHIAN_SPAWN_EGG, "Elder Aylythian Spawn Egg");
        builder.add(ModItems.YMPEMOULD_ITEM, "Ympemould");
        builder.add(ModItems.YMPE_EFFIGY_ITEM, "Ympe Effigy");
        builder.add(ModItems.WRONGMEAT, "Wrongmeat");
        builder.add(ModItems.YMPE_CUIRASS, "Ympe Cuirass");
        builder.add(ModItems.GIRASOL_SEED, "Girasol Seed");
        builder.add(ModItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, "Aylythian Upgrade Smithing Template");
        builder.add(ModItems.ESSTLINE, "Esstline");
        potionSet(builder, "mortechis");
        potionSet(builder, "cimmerian");
        potionSet(builder, "wyrded");

        // ENTITIES

        builder.add(ModEntityTypes.PILOT_LIGHT, "Pilot Light");
        builder.add(ModEntityTypes.AYLYTHIAN, "Aylythian");
        builder.add(ModEntityTypes.ELDER_AYLYTHIAN, "Elder Aylythian");
        builder.add(ModEntityTypes.YMPE_LANCE, "Ympe Lance");
        builder.add(ModEntityTypes.SOULMOULD, "Mould of Souls");
        builder.add(ModEntityTypes.BONEFLY, "Bonefly");
        builder.add(ModEntityTypes.SCION, "Scion");
        builder.add(ModEntityTypes.TULPA, "Tulpa");
        builder.add(ModEntityTypes.WREATHED_HIND_ENTITY, "Wreathed Hind");
        builder.add(ModEntityTypes.FAUNAYLYTHIAN, "Faunaylythian");

        // STATUS EFFECTS

        builder.add(ModPotions.MORTECHIS_EFFECT, "Mortechis");
        builder.add(ModPotions.CIMMERIAN_EFFECT, "Cimmerian");
        builder.add(ModPotions.WYRDED_EFFECT, "Wyrded");

        // ADVANCEMENTS

        builder.add("aylyth.advancements.aylyth.root.title", "Into the Unknown");
        builder.add("aylyth.advancements.aylyth.root.desc", "In the midst of the journey of our life, I found myself in a dark wood without paths.");
        builder.add("aylyth.advancements.aylyth.cimmerianed.title", "Cimmerianed");
        builder.add("aylyth.advancements.aylyth.cimmerianed.desc", "Play some hide and seek with the undead");
        builder.add("aylyth.advancements.aylyth.wyrded.title", "Wyrded");
        builder.add("aylyth.advancements.aylyth.wyrded.desc", "Try approaching a seep!");
        builder.add("aylyth.advancements.aylyth.in_the_branches.title", "In the Branches");
        builder.add("aylyth.advancements.aylyth.in_the_branches.desc", "Become infested by Ympe Branches. There has to be a cure...");
        builder.add("aylyth.advancements.aylyth.life_at_a_cost.title", "Life at a cost");
        builder.add("aylyth.advancements.aylyth.life_at_a_cost.desc", "Obtain an Ympe Fruit, used to help subside the Branches, among other things.");
        builder.add("aylyth.advancements.aylyth.daemon_ritus.title", "Daemon Ritus");
        builder.add("aylyth.advancements.aylyth.daemon_ritus.desc", "Using a Shucked Ympe Fruit and an Ympe Dagger, store the soul of a mob in the hollowed fruit (this may require murder)");
        builder.add("aylyth.advancements.aylyth.manufactured_for_a_purpose.title", "Manufactured for a Purpose");
        builder.add("aylyth.advancements.aylyth.manufactured_for_a_purpose.desc", "Obtain a Coric Seed, perhaps one can find the way to make one hidden in dungeons and lost structures...");
        builder.add("aylyth.advancements.aylyth.laccus.title", "Laccus");
        builder.add("aylyth.advancements.aylyth.laccus.desc", "Find and harvest Nysian Grapes.");
        builder.add("aylyth.advancements.aylyth.libations.title", "Libations");
        builder.add("aylyth.advancements.aylyth.libations.desc", "Brew Nysian Grapes into a special potion that protects those near death.");
        builder.add("aylyth.advancements.aylyth.come_wayward_souls.title", "Come Wayward Souls");
        builder.add("aylyth.advancements.aylyth.come_wayward_souls.desc", "Give a Wreathed Hind, the deer-like creatures found in the Deepest of Woods, Nysian Grapes in order to earn its protection.");
        builder.add("aylyth.advancements.aylyth.dont_look_back.title", "Don’t Look Back");
        builder.add("aylyth.advancements.aylyth.dont_look_back.desc", "Reach out to a Pilot Light so it may guide you back home, requires 5 XP levels.");
        builder.add("aylyth.advancements.aylyth.into_the_fire_we_fly.title", "Into the Fire we Fly");
        builder.add("aylyth.advancements.aylyth.into_the_fire_we_fly.desc", "Obtain an Aylythian Heart, a drop from certain stronger creatures, and use it to avoid death, at the cost of returning to Aylyth.");
        builder.add("aylyth.advancements.aylyth.something_between_life_and_death.title", "Between Life and Death");
        builder.add("aylyth.advancements.aylyth.something_between_life_and_death.desc", "Create a Soul Hearth, give it Pomegranates, and then right-click on it to activate it, allowing you to set spawn in Aylyth.");
        builder.add("aylyth.advancements.aylyth.meat_found.title", "Meat Found!");
        builder.add("aylyth.advancements.aylyth.meat_found.desc", "Slay certain Aylythian entities with an Ympe Dagger, and collect their special drop.");
        builder.add("aylyth.advancements.aylyth.flesh_increased_by_two.title", "Flesh Increased by 2");
        builder.add("aylyth.advancements.aylyth.flesh_increased_by_two.desc", "Create a Vital Thurible, and place 5 Wrongmeat in it, to gain additional health! Watch out for spectral entities and Ympe, however...");
        builder.add("aylyth.advancements.aylyth.way_of_the_wood.title", "Way of the Wood");
        builder.add("aylyth.advancements.aylyth.way_of_the_wood.desc", "Encounter every biome in Aylyth.");
        builder.add("aylyth.advancements.aylyth.see_you_in_the_trees.title", "I’ll see you in the Trees");
        builder.add("aylyth.advancements.aylyth.see_you_in_the_trees.desc", "Create a Girasol Seed, and plant it in the Overworld, to create a new Seep.");

        // DEATH MESSAGES

        builder.add("death.attack.ympe", "%1$s has gone to the trees");
        builder.add("death.attack.ympe.player", "%1$s has gone to the trees whilst trying to escape %2$s");
        builder.add("death.attack.wreathed_hind_killing_blow", "%1$s's mutual agreement was betrayed");

        // SUBTITLES

        builder.add("aylyth.subtitles.block.ympe_log.pick_fruit", "Fruit picked");
        builder.add("aylyth.subtitles.entity.player.increase_ympe_infestation_stage", "Branches spread");
        builder.add("aylyth.subtitles.entity.generic.shucked", "Entity shucked");
        builder.add("aylyth.subtitles.entity.aylythian.ambient", "Aylythian groans");
        builder.add("aylyth.subtitles.entity.aylythian.hurt", "Aylythian hurts");
        builder.add("aylyth.subtitles.entity.aylythian.death", "Aylythian dies");
        builder.add("aylyth.subtitles.entity.faunaylythian.ambient", "Faunaylythian snarls");
        builder.add("aylyth.subtitles.entity.faunaylythian.hurt", "Faunaylythian hurts");
        builder.add("aylyth.subtitles.entity.faunaylythian.death", "Faunaylythian dies");
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

        builder.add("aylyth.subtitles.entity.wreathed_hind.ambient", "Wreathed Hind groans");
        builder.add("aylyth.subtitles.entity.wreathed_hind.hurt", "Wreathed Hind hurt");
        builder.add("aylyth.subtitles.entity.wreathed_hind.death", "Wreathed Hind dies");

        builder.add("aylyth.subtitles.entity.scion.ambient", "Scion groans");
        builder.add("aylyth.subtitles.entity.scion.hurt", "Scion hurt");
        builder.add("aylyth.subtitles.entity.scion.death", "Scion dies");

        // INFO

        builder.add("info.aylyth.mould_activate", "Mould of Souls Activated");
        builder.add("info.aylyth.mould_deactivate", "Mould of Souls Deactivated");

        builder.add("info.aylyth.tulpa_wander", "Wander");
        builder.add("info.aylyth.tulpa_follow", "Follow");
        builder.add("info.aylyth.tulpa_stay", "Stay");

        // COMPAT - REI

        builder.add("rei.aylyth.ympe_dagger_drops", "Ympe Dagger Drops");

        // COMPAT - EMI

        builder.add("emi.category.aylyth.ympe_dagger_drops", "Ympe Dagger Drops");
        builder.add("tag.aylyth.ympe_logs", "Ympe Logs");
        builder.add("tag.aylyth.pomegranate_logs", "Pomegranate Logs");
        builder.add("tag.aylyth.writhewood_logs", "Writhewood Logs");
        builder.add("tag.aylyth.woody_growths", "Woody Growths");
        builder.add("tag.aylyth.ympe_foods", "Ympe Foods");
        builder.add("tag.aylyth.pledge_items", "Pledge Items");
        builder.add("tag.aylyth.seeps", "Logs with Seep");
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
