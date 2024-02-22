package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModBlocks;
import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModStatusEffects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.loader.impl.util.StringUtil;

public class AylythLanguageProvider extends FabricLanguageProvider {

    protected AylythLanguageProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        // MOD ITEMGROUP

        builder.add(ModItems.GROUP, "Aylyth");

        // BLOCKS

        builder.add(ModBlocks.YMPE_STRIPPED_LOG, "Stripped Ympe Log");
        builder.add(ModBlocks.YMPE_STRIPPED_WOOD, "Stripped Ympe Wood");
        builder.add(ModBlocks.YMPE_LOG, "Ympe Log");
        builder.add(ModBlocks.YMPE_WOOD, "Ympe Wood");
        builder.add(ModBlocks.YMPE_SAPLING, "Ympe Sapling");
        builder.add(ModBlocks.YMPE_POTTED_SAPLING, "Potted Ympe Sapling");
        builder.add(ModBlocks.YMPE_PLANKS, "Ympe Planks");
        builder.add(ModBlocks.YMPE_STAIRS, "Ympe Stairs");
        builder.add(ModBlocks.YMPE_SLAB, "Ympe Slab");
        builder.add(ModBlocks.YMPE_FENCE, "Ympe Fence");
        builder.add(ModBlocks.YMPE_FENCE_GATE, "Ympe Fence Gate");
        builder.add(ModBlocks.YMPE_PRESSURE_PLATE, "Ympe Pressure Plate");
        builder.add(ModBlocks.YMPE_BUTTON, "Ympe Button");
        builder.add(ModBlocks.YMPE_TRAPDOOR, "Ympe Trapdoor");
        builder.add(ModBlocks.YMPE_DOOR, "Ympe Door");
        builder.add(ModBlocks.YMPE_SIGN, "Ympe Sign");
        builder.add(ModBlocks.YMPE_HANGING_SIGN, "Ympe Hanging Sign");
        builder.add(ModBlocks.YMPE_LEAVES, "Ympe Leaves");
        builder.add(ModBlocks.FRUIT_BEARING_YMPE_LOG, "Fruit Bearing Ympe Log");
        builder.add(ModBlocks.POMEGRANATE_STRIPPED_LOG, "Stripped Pomegranate Log");
        builder.add(ModBlocks.POMEGRANATE_STRIPPED_WOOD, "Stripped Pomegranate Wood");
        builder.add(ModBlocks.POMEGRANATE_LOG, "Pomegranate Log");
        builder.add(ModBlocks.POMEGRANATE_WOOD, "Pomegranate Wood");
        builder.add(ModBlocks.POMEGRANATE_SAPLING, "Pomegranate Sapling");
        builder.add(ModBlocks.POMEGRANATE_POTTED_SAPLING, "Potted Pomegranate Sapling");
        builder.add(ModBlocks.POMEGRANATE_PLANKS, "Pomegranate Planks");
        builder.add(ModBlocks.POMEGRANATE_STAIRS, "Pomegranate Stairs");
        builder.add(ModBlocks.POMEGRANATE_SLAB, "Pomegranate Slab");
        builder.add(ModBlocks.POMEGRANATE_FENCE, "Pomegranate Fence");
        builder.add(ModBlocks.POMEGRANATE_FENCE_GATE, "Pomegranate Fence Gate");
        builder.add(ModBlocks.POMEGRANATE_PRESSURE_PLATE, "Pomegranate Pressure Plate");
        builder.add(ModBlocks.POMEGRANATE_BUTTON, "Pomegranate Button");
        builder.add(ModBlocks.POMEGRANATE_TRAPDOOR, "Pomegranate Trapdoor");
        builder.add(ModBlocks.POMEGRANATE_DOOR, "Pomegranate Door");
        builder.add(ModBlocks.POMEGRANATE_SIGN, "Pomegranate Sign");
        builder.add(ModBlocks.POMEGRANATE_HANGING_SIGN, "Pomegranate Hanging Sign");
        builder.add(ModBlocks.POMEGRANATE_LEAVES, "Pomegranate Leaves");
        builder.add(ModBlocks.WRITHEWOOD_STRIPPED_LOG, "Stripped Writhewood Log");
        builder.add(ModBlocks.WRITHEWOOD_STRIPPED_WOOD, "Stripped Writhewood Wood");
        builder.add(ModBlocks.WRITHEWOOD_LOG, "Writhewood Log");
        builder.add(ModBlocks.WRITHEWOOD_WOOD, "Writhewood Wood");
        builder.add(ModBlocks.WRITHEWOOD_SAPLING, "Writhewood Sapling");
        builder.add(ModBlocks.WRITHEWOOD_POTTED_SAPLING, "Potted Writhewood Sapling");
        builder.add(ModBlocks.WRITHEWOOD_PLANKS, "Writhewood Planks");
        builder.add(ModBlocks.WRITHEWOOD_STAIRS, "Writhewood Stairs");
        builder.add(ModBlocks.WRITHEWOOD_SLAB, "Writhewood Slab");
        builder.add(ModBlocks.WRITHEWOOD_FENCE, "Writhewood Fence");
        builder.add(ModBlocks.WRITHEWOOD_FENCE_GATE, "Writhewood Fence Gate");
        builder.add(ModBlocks.WRITHEWOOD_PRESSURE_PLATE, "Writhewood Pressure Plate");
        builder.add(ModBlocks.WRITHEWOOD_BUTTON, "Writhewood Button");
        builder.add(ModBlocks.WRITHEWOOD_TRAPDOOR, "Writhewood Trapdoor");
        builder.add(ModBlocks.WRITHEWOOD_DOOR, "Writhewood Door");
        builder.add(ModBlocks.WRITHEWOOD_SIGN, "Writhewood Sign");
        builder.add(ModBlocks.WRITHEWOOD_HANGING_SIGN, "Writhewood Hanging Sign");
        builder.add(ModBlocks.WRITHEWOOD_LEAVES, "Writhewood Leaves");
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
        builder.add(ModBlocks.ESSTLINE_BLOCK, "Esstline Block");
        builder.add(ModBlocks.NEPHRITE_BLOCK, "Nephrite Block");
        builder.add(ModBlocks.CARVED_SMOOTH_NEPHRITE, "Carved Smooth Nephrite");
        builder.add(ModBlocks.CARVED_ANTLERED_NEPHRITE, "Carved Antlered Nephrite");
        builder.add(ModBlocks.CARVED_NEPHRITE_PILLAR, "Carved Nephrite Pillar");
        builder.add(ModBlocks.CARVED_NEPHRITE_TILES, "Carved Nephrite Tiles");
        builder.add(ModBlocks.CARVED_WOODY_NEPHRITE, "Carved Woody Nephrite");

        // ITEMS

        builder.add(ModItems.DEBUG_WAND, "Debug Wand");
        builder.add(ModItems.YMPE_BOAT, "Ympe Boat");
        builder.add(ModItems.YMPE_CHEST_BOAT, "Ympe Chest Boat");
        builder.add(ModItems.POMEGRANATE_BOAT, "Pomegranate Boat");
        builder.add(ModItems.POMEGRANATE_CHEST_BOAT, "Pomegranate Chest Boat");
        builder.add(ModItems.WRITHEWOOD_BOAT, "Writhewood Boat");
        builder.add(ModItems.WRITHEWOOD_CHEST_BOAT, "Writhewood Chest Boat");
        builder.add(ModItems.POMEGRANATE, "Pomegranate");
        builder.add(ModItems.GHOSTCAP_MUSHROOM, "Ghostcap Mushroom");
        builder.add(ModItems.YMPE_DAGGER, "Ympe Dagger");
        builder.add(ModItems.YMPE_LANCE, "Ympe Lance");
        builder.add(ModItems.YMPE_GLAIVE, "Ympe Glaive");
        builder.add("item.aylyth.glaive.desc_1", "\u00a76\u00a7oIt is the lament of the fallen");
        builder.add("item.aylyth.glaive.desc_2", "\u00a76\u00a7owhich pushes the living onward.");
        builder.add(ModItems.YMPE_FLAMBERGE, "Ympe Flamberge");
        builder.add(ModItems.YMPE_SCYTHE, "Ympe Scythe");
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
        builder.add(ModItems.YMPE_EFFIGY, "Ympe Effigy");
        builder.add(ModItems.WRONGMEAT, "Wrongmeat");
        builder.add(ModItems.YMPE_CUIRASS, "Ympe Cuirass");
        builder.add(ModItems.GIRASOL_SEED, "Girasol Seed");
        builder.add(ModItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, "Aylythian Upgrade Smithing Template");
        builder.add(ModItems.ESSTLINE, "Esstline");
        builder.add(ModItems.NEPHRITE, "Nephrite");
        builder.add(ModItems.NEPHRITE_SWORD, "Nephrite Sword");
        builder.add(ModItems.NEPHRITE_SHOVEL, "Nephrite Shovel");
        builder.add(ModItems.NEPHRITE_PICKAXE, "Nephrite Pickaxe");
        builder.add(ModItems.NEPHRITE_AXE, "Nephrite Axe");
        builder.add(ModItems.NEPHRITE_HOE, "Nephrite Hoe");
        builder.add(ModItems.VAMPIRIC_SWORD, "Vampiric Sword");
        builder.add(ModItems.VAMPIRIC_PICKAXE, "Vampiric Pick");
        builder.add(ModItems.VAMPIRIC_AXE, "Vampiric Axe");
        builder.add(ModItems.VAMPIRIC_HOE, "Vampiric Sickle");
        builder.add(ModItems.BLIGHTED_SWORD, "Blighted Sword");
        builder.add(ModItems.BLIGHTED_PICKAXE, "Blighted Pick");
        builder.add(ModItems.BLIGHTED_AXE, "Blighted Axe");
        builder.add(ModItems.BLIGHTED_HOE, "Blighted Sickle");
        builder.add(ModItems.POMEGRANATE_CASSETTE, "Cassette");
        builder.add(ModItems.BARK, "Bark");
        builder.add(ModItems.BLIGHTED_THORNS, "Blighted Thorns");
        potionSet(builder, "mortechis");
        potionSet(builder, "cimmerian");
        potionSet(builder, "wyrded");

        builder.add("item.aylyth.pomegranate_cassette.desc", "DEMON AND MAX - Pomegranate");
        builder.add("item.aylyth.smithing_template.aylythian_upgrade.applies_to", "Ympe Sapling");
        builder.add("item.aylyth.smithing_template.aylythian_upgrade.ingredients", "Esstline");
        builder.add("item.aylyth.smithing_template.aylythian_upgrade.base_slot_description", "Ympe Sapling");
        builder.add("item.aylyth.smithing_template.aylythian_upgrade.additions_slot_description", "Esstline");
        builder.add("upgrade.aylyth.aylythian_upgrade", "Aylythian Upgrade");

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

        builder.add(ModStatusEffects.MORTECHIS, "Mortechis");
        builder.add(ModStatusEffects.CIMMERIAN, "Cimmerian");
        builder.add(ModStatusEffects.WYRDED, "Wyrded");

        // ADVANCEMENTS

        builder.add("advancements.aylyth.aylyth.root.title", "Into the Unknown");
        builder.add("advancements.aylyth.aylyth.root.desc", "In the midst of the journey of our life, I found myself in a dark wood without paths.");
        builder.add("advancements.aylyth.aylyth.cimmerianed.title", "Cimmerianed");
        builder.add("advancements.aylyth.aylyth.cimmerianed.desc", "Hidden from the undead...");
        builder.add("advancements.aylyth.aylyth.wyrded.title", "Wyrded");
        builder.add("advancements.aylyth.aylyth.wyrded.desc", "The seep seem to beg that I stay...");
        builder.add("advancements.aylyth.aylyth.in_the_branches.title", "In the Branches");
        builder.add("advancements.aylyth.aylyth.in_the_branches.desc", "Become infested by Ympe Branches. There has to be a cure...");
        builder.add("advancements.aylyth.aylyth.life_at_a_cost.title", "Life at a cost");
        builder.add("advancements.aylyth.aylyth.life_at_a_cost.desc", "Obtain an Ympe Fruit, used to help subside the Branches, among other things.");
        builder.add("advancements.aylyth.aylyth.daemon_ritus.title", "Daemon Ritus");
        builder.add("advancements.aylyth.aylyth.daemon_ritus.desc", "Using a Shucked Ympe Fruit and an Ympe Dagger, store the soul of a mob in the hollowed fruit (this may require murder)");
        builder.add("advancements.aylyth.aylyth.manufactured_for_a_purpose.title", "Manufactured for a Purpose");
        builder.add("advancements.aylyth.aylyth.manufactured_for_a_purpose.desc", "Obtain a Coric Seed, perhaps one can find the way to make one hidden in dungeons and lost structures...");
        builder.add("advancements.aylyth.aylyth.laccus.title", "Laccus");
        builder.add("advancements.aylyth.aylyth.laccus.desc", "Find and harvest Nysian Grapes.");
        builder.add("advancements.aylyth.aylyth.libations.title", "Libations");
        builder.add("advancements.aylyth.aylyth.libations.desc", "Brew Nysian Grapes into a special potion that protects those near death.");
        builder.add("advancements.aylyth.aylyth.come_wayward_souls.title", "Come Wayward Souls");
        builder.add("advancements.aylyth.aylyth.come_wayward_souls.desc", "Give a Wreathed Hind, the deer-like creatures found in the Deepest of Woods, Nysian Grapes in order to earn its protection.");
        builder.add("advancements.aylyth.aylyth.dont_look_back.title", "Don’t Look Back");
        builder.add("advancements.aylyth.aylyth.dont_look_back.desc", "Reach out to a Pilot Light so it may guide you back home, requires 5 XP levels.");
        builder.add("advancements.aylyth.aylyth.into_the_fire_we_fly.title", "Into the Fire we Fly");
        builder.add("advancements.aylyth.aylyth.into_the_fire_we_fly.desc", "Obtain an Aylythian Heart, a drop from certain stronger creatures, and use it to avoid death, at the cost of returning to Aylyth.");
        builder.add("advancements.aylyth.aylyth.something_between_life_and_death.title", "Between Life and Death");
        builder.add("advancements.aylyth.aylyth.something_between_life_and_death.desc", "Create a Soul Hearth, give it Pomegranates, and then right-click on it to activate it, allowing you to set spawn in Aylyth.");
        builder.add("advancements.aylyth.aylyth.meat_found.title", "Meat Found!");
        builder.add("advancements.aylyth.aylyth.meat_found.desc", "Slay certain Aylythian entities with an Ympe Dagger, and collect their special drop.");
        builder.add("advancements.aylyth.aylyth.flesh_increased_by_two.title", "Flesh Increased by 2");
        builder.add("advancements.aylyth.aylyth.flesh_increased_by_two.desc", "Create a Vital Thurible, and place 5 Wrongmeat in it, to gain additional health! Watch out for spectral entities and Ympe, however...");
        builder.add("advancements.aylyth.aylyth.way_of_the_wood.title", "Way of the Wood");
        builder.add("advancements.aylyth.aylyth.way_of_the_wood.desc", "Encounter every biome in Aylyth.");
        builder.add("advancements.aylyth.aylyth.see_you_in_the_trees.title", "I’ll see you in the Trees");
        builder.add("advancements.aylyth.aylyth.see_you_in_the_trees.desc", "Create a Girasol Seed, and plant it in the Overworld, to create a new Seep.");

        // DEATH MESSAGES

        builder.add("death.attack.ympe", "%1$s has gone to the trees");
        builder.add("death.attack.ympe.player", "%1$s has gone to the trees whilst trying to escape %2$s");
        builder.add("death.attack.wreathed_hind_killing_blow", "%1$s's mutual agreement was betrayed");

        // SUBTITLES

        builder.add("subtitles.aylyth.block.ympe_log.pick_fruit", "Fruit picked");
        builder.add("subtitles.aylyth.entity.player.increase_ympe_infestation_stage", "Branches spread");
        builder.add("subtitles.aylyth.entity.generic.shucked", "Entity shucked");
        builder.add("subtitles.aylyth.entity.aylythian.ambient", "Aylythian groans");
        builder.add("subtitles.aylyth.entity.aylythian.hurt", "Aylythian hurts");
        builder.add("subtitles.aylyth.entity.aylythian.death", "Aylythian dies");
        builder.add("subtitles.aylyth.entity.faunaylythian.ambient", "Faunaylythian snarls");
        builder.add("subtitles.aylyth.entity.faunaylythian.hurt", "Faunaylythian hurts");
        builder.add("subtitles.aylyth.entity.faunaylythian.death", "Faunaylythian dies");
        builder.add("subtitles.aylyth.entity.elder_aylythian.ambient", "Elder Aylythian rumbles");
        builder.add("subtitles.aylyth.entity.elder_aylythian.hurt", "Elder Aylythian hurts");
        builder.add("subtitles.aylyth.entity.elder_aylythian.death", "Elder Aylythian dies");
        builder.add("subtitles.aylyth.entity.soulmould.ambient", "Soulmould groans");
        builder.add("subtitles.aylyth.entity.soulmould.attack", "Soulmould attack");
        builder.add("subtitles.aylyth.entity.soulmould.damage", "Soulmould hurt");
        builder.add("subtitles.aylyth.entity.soulmould.death", "Soulmould dies");
        builder.add("subtitles.aylyth.block.stick_break", "Stick cracks");
        builder.add("subtitles.aylyth.block.strewn_leaves.step", "Leaves crunch");
        builder.add("subtitles.aylyth.block.strewn_leaves.pile_destroy", "Leaves scatter");
        builder.add("subtitles.aylyth.block.strewn_leaves.pile_step", "Leaf pile crunches");

        builder.add("subtitles.aylyth.entity.wreathed_hind.ambient", "Wreathed Hind groans");
        builder.add("subtitles.aylyth.entity.wreathed_hind.hurt", "Wreathed Hind hurt");
        builder.add("subtitles.aylyth.entity.wreathed_hind.death", "Wreathed Hind dies");

        builder.add("subtitles.aylyth.entity.scion.ambient", "Scion groans");
        builder.add("subtitles.aylyth.entity.scion.hurt", "Scion hurt");
        builder.add("subtitles.aylyth.entity.scion.death", "Scion dies");

        // INFO

        builder.add("info.aylyth.ympemould_activate", "Ympemould Activated");
        builder.add("info.aylyth.ympemould_deactivate", "Ympemould Deactivated");

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
        builder.add("tag.aylyth.seeps", "Logs with Seep");
        builder.add("tag.aylyth.carved_nephrite", "Carved Nephrite");
        builder.add("tag.aylyth.woody_growths", "Woody Growths");
        builder.add("tag.aylyth.decreases_branches", "Heals Branches");
        builder.add("tag.aylyth.pledge_items", "Pledge Items");
        builder.add("tag.aylyth.nephrite_tool_materials", "Nephrite Tool Materials");
        builder.add("tag.aylyth.ympe_weapons", "Ympe Weapons");
        builder.add("tag.aylyth.blighted_weapons", "Blighted Weapons");
        builder.add("tag.aylyth.vampiric_weapons", "Vampiric Weapons");
    }

    private void potionSet(TranslationBuilder builder, String effectName) {
        builder.add("item.minecraft.potion.effect.%s".formatted(effectName), "Potion of %s".formatted(StringUtil.capitalize(effectName)));
        builder.add("item.minecraft.splash_potion.effect.%s".formatted(effectName), "Splash Potion of %s".formatted(StringUtil.capitalize(effectName)));
        builder.add("item.minecraft.tipped_arrow.effect.%s".formatted(effectName), "Arrow of %s".formatted(StringUtil.capitalize(effectName)));
        builder.add("item.minecraft.lingering_potion.effect.%s".formatted(effectName), "Lingering Potion of %s".formatted(StringUtil.capitalize(effectName)));
    }
}
