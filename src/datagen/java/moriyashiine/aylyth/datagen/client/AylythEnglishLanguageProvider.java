package moriyashiine.aylyth.datagen.client;

import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.data.world.AylythBiomes;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.item.AylythItemGroups;
import moriyashiine.aylyth.common.item.AylythItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.loader.impl.util.StringUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class AylythEnglishLanguageProvider extends FabricLanguageProvider {

    public AylythEnglishLanguageProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        // MOD ITEMGROUP

        builder.add(AylythItemGroups.MAIN, "Aylyth");

        // BLOCKS

        builder.add(AylythBlocks.SAPSTONE, "Sapstone");
        builder.add(AylythBlocks.AMBER_SAPSTONE, "Amber Sapstone");
        builder.add(AylythBlocks.LIGNITE_SAPSTONE, "Lignite Sapstone");
        builder.add(AylythBlocks.OPALESCENT_SAPSTONE, "Opalescent Sapstone");
        builder.add(AylythBlocks.YMPE_STRIPPED_LOG, "Stripped Ympe Log");
        builder.add(AylythBlocks.YMPE_STRIPPED_WOOD, "Stripped Ympe Wood");
        builder.add(AylythBlocks.YMPE_LOG, "Ympe Log");
        builder.add(AylythBlocks.YMPE_WOOD, "Ympe Wood");
        builder.add(AylythBlocks.YMPE_SAPLING, "Ympe Sapling");
        builder.add(AylythBlocks.YMPE_POTTED_SAPLING, "Potted Ympe Sapling");
        builder.add(AylythBlocks.YMPE_PLANKS, "Ympe Planks");
        builder.add(AylythBlocks.YMPE_STAIRS, "Ympe Stairs");
        builder.add(AylythBlocks.YMPE_SLAB, "Ympe Slab");
        builder.add(AylythBlocks.YMPE_FENCE, "Ympe Fence");
        builder.add(AylythBlocks.YMPE_FENCE_GATE, "Ympe Fence Gate");
        builder.add(AylythBlocks.YMPE_PRESSURE_PLATE, "Ympe Pressure Plate");
        builder.add(AylythBlocks.YMPE_BUTTON, "Ympe Button");
        builder.add(AylythBlocks.YMPE_TRAPDOOR, "Ympe Trapdoor");
        builder.add(AylythBlocks.YMPE_DOOR, "Ympe Door");
        builder.add(AylythBlocks.YMPE_SIGN, "Ympe Sign");
        builder.add(AylythBlocks.YMPE_HANGING_SIGN, "Ympe Hanging Sign");
        builder.add(AylythBlocks.YMPE_LEAVES, "Ympe Leaves");
        builder.add(AylythBlocks.FRUIT_BEARING_YMPE_LOG, "Fruit Bearing Ympe Log");
        builder.add(AylythBlocks.POMEGRANATE_STRIPPED_LOG, "Stripped Pomegranate Log");
        builder.add(AylythBlocks.POMEGRANATE_STRIPPED_WOOD, "Stripped Pomegranate Wood");
        builder.add(AylythBlocks.POMEGRANATE_LOG, "Pomegranate Log");
        builder.add(AylythBlocks.POMEGRANATE_WOOD, "Pomegranate Wood");
        builder.add(AylythBlocks.POMEGRANATE_SAPLING, "Pomegranate Sapling");
        builder.add(AylythBlocks.POMEGRANATE_POTTED_SAPLING, "Potted Pomegranate Sapling");
        builder.add(AylythBlocks.POMEGRANATE_PLANKS, "Pomegranate Planks");
        builder.add(AylythBlocks.POMEGRANATE_STAIRS, "Pomegranate Stairs");
        builder.add(AylythBlocks.POMEGRANATE_SLAB, "Pomegranate Slab");
        builder.add(AylythBlocks.POMEGRANATE_FENCE, "Pomegranate Fence");
        builder.add(AylythBlocks.POMEGRANATE_FENCE_GATE, "Pomegranate Fence Gate");
        builder.add(AylythBlocks.POMEGRANATE_PRESSURE_PLATE, "Pomegranate Pressure Plate");
        builder.add(AylythBlocks.POMEGRANATE_BUTTON, "Pomegranate Button");
        builder.add(AylythBlocks.POMEGRANATE_TRAPDOOR, "Pomegranate Trapdoor");
        builder.add(AylythBlocks.POMEGRANATE_DOOR, "Pomegranate Door");
        builder.add(AylythBlocks.POMEGRANATE_SIGN, "Pomegranate Sign");
        builder.add(AylythBlocks.POMEGRANATE_HANGING_SIGN, "Pomegranate Hanging Sign");
        builder.add(AylythBlocks.POMEGRANATE_LEAVES, "Pomegranate Leaves");
        builder.add(AylythBlocks.WRITHEWOOD_STRIPPED_LOG, "Stripped Writhewood Log");
        builder.add(AylythBlocks.WRITHEWOOD_STRIPPED_WOOD, "Stripped Writhewood Wood");
        builder.add(AylythBlocks.WRITHEWOOD_LOG, "Writhewood Log");
        builder.add(AylythBlocks.WRITHEWOOD_WOOD, "Writhewood Wood");
        builder.add(AylythBlocks.WRITHEWOOD_SAPLING, "Writhewood Sapling");
        builder.add(AylythBlocks.WRITHEWOOD_POTTED_SAPLING, "Potted Writhewood Sapling");
        builder.add(AylythBlocks.WRITHEWOOD_PLANKS, "Writhewood Planks");
        builder.add(AylythBlocks.WRITHEWOOD_STAIRS, "Writhewood Stairs");
        builder.add(AylythBlocks.WRITHEWOOD_SLAB, "Writhewood Slab");
        builder.add(AylythBlocks.WRITHEWOOD_FENCE, "Writhewood Fence");
        builder.add(AylythBlocks.WRITHEWOOD_FENCE_GATE, "Writhewood Fence Gate");
        builder.add(AylythBlocks.WRITHEWOOD_PRESSURE_PLATE, "Writhewood Pressure Plate");
        builder.add(AylythBlocks.WRITHEWOOD_BUTTON, "Writhewood Button");
        builder.add(AylythBlocks.WRITHEWOOD_TRAPDOOR, "Writhewood Trapdoor");
        builder.add(AylythBlocks.WRITHEWOOD_DOOR, "Writhewood Door");
        builder.add(AylythBlocks.WRITHEWOOD_SIGN, "Writhewood Sign");
        builder.add(AylythBlocks.WRITHEWOOD_HANGING_SIGN, "Writhewood Hanging Sign");
        builder.add(AylythBlocks.WRITHEWOOD_LEAVES, "Writhewood Leaves");
        builder.add(AylythBlocks.AYLYTH_BUSH, "Aylyth Bush");
        builder.add(AylythBlocks.ANTLER_SHOOTS, "Antler Shoots");
        builder.add(AylythBlocks.GRIPWEED, "Gripweed");
        builder.add(AylythBlocks.NYSIAN_GRAPE_VINE, "Nysian Grape Vines");
        builder.add(AylythBlocks.MARIGOLD, "Marigolds");
        builder.add(AylythBlocks.OAK_STREWN_LEAVES, "Oak Strewn Leaves");
        builder.add(AylythBlocks.YMPE_STREWN_LEAVES, "Ympe Strewn Leaves");
        builder.add(AylythBlocks.JACK_O_LANTERN_MUSHROOM, "Jack O'Lantern Mushroom");
        builder.add(AylythBlocks.GHOSTCAP_MUSHROOM, "Ghostcap Mushroom Spores");
        builder.add(AylythBlocks.OAK_SEEP, "Oak Seep");
        builder.add(AylythBlocks.SPRUCE_SEEP, "Spruce Seep");
        builder.add(AylythBlocks.DARK_OAK_SEEP, "Dark Oak Seep");
        builder.add(AylythBlocks.YMPE_SEEP, "Ympe Seep");
        builder.add(AylythBlocks.DARK_WOODS_TILES, "Dark Wood Tiles");
        builder.add(AylythBlocks.SOUL_HEARTH, "Soul Hearth");
        builder.add(AylythBlocks.VITAL_THURIBLE, "Vital Thurible");
        builder.add(AylythBlocks.SMALL_WOODY_GROWTH, "Small Woody Growth");
        builder.add(AylythBlocks.LARGE_WOODY_GROWTH, "Large Woody Growth");
        builder.add(AylythBlocks.WOODY_GROWTH_CACHE, "Woody Growth Cache");
        builder.add(AylythBlocks.SEEPING_WOOD, "Seeping Wood");
        builder.add(AylythBlocks.SEEPING_WOOD_SEEP, "Seeping Wood Seep");
        builder.add(AylythBlocks.GIRASOL_SAPLING, "Girasol Sapling");
        builder.add(AylythBlocks.GIRASOL_SAPLING_POTTED, "Potted Girasol Sapling");
        builder.add(AylythBlocks.ESSTLINE_BLOCK, "Esstline Block");
        builder.add(AylythBlocks.NEPHRITE_BLOCK, "Nephrite Block");
        builder.add(AylythBlocks.CARVED_SMOOTH_NEPHRITE, "Carved Smooth Nephrite");
        builder.add(AylythBlocks.CARVED_ANTLERED_NEPHRITE, "Carved Antlered Nephrite");
        builder.add(AylythBlocks.CARVED_NEPHRITE_PILLAR, "Carved Nephrite Pillar");
        builder.add(AylythBlocks.CARVED_NEPHRITE_TILES, "Carved Nephrite Tiles");
        builder.add(AylythBlocks.CARVED_WOODY_NEPHRITE, "Carved Woody Nephrite");
        builder.add(AylythBlocks.BLACK_WELL, "Black Well");
        builder.add(AylythBlocks.CHTHONIA_WOOD, "Chthonia Wood");
        builder.add(AylythBlocks.NEPHRITIC_CHTHONIA_WOOD, "Nephritic Chthonia Wood");
        builder.add(AylythBlocks.JACK_O_LANTERN_MUSHROOM_STEM, "Jack O'Lantern Mushroom Stem");
        builder.add(AylythBlocks.JACK_O_LANTERN_MUSHROOM_BLOCK, "Jack O'Lantern Mushroom Block");

        // ITEMS

        builder.add(AylythItems.DEBUG_WAND, "Debug Wand");
        builder.add(AylythItems.YMPE_BOAT, "Ympe Boat");
        builder.add(AylythItems.YMPE_CHEST_BOAT, "Ympe Chest Boat");
        builder.add(AylythItems.POMEGRANATE_BOAT, "Pomegranate Boat");
        builder.add(AylythItems.POMEGRANATE_CHEST_BOAT, "Pomegranate Chest Boat");
        builder.add(AylythItems.WRITHEWOOD_BOAT, "Writhewood Boat");
        builder.add(AylythItems.WRITHEWOOD_CHEST_BOAT, "Writhewood Chest Boat");
        builder.add(AylythItems.POMEGRANATE, "Pomegranate");
        builder.add(AylythItems.GHOSTCAP_MUSHROOM, "Ghostcap Mushroom");
        builder.add(AylythItems.YMPE_DAGGER, "Ympe Dagger");
        builder.add(AylythItems.YMPE_LANCE, "Ympe Lance");
        builder.add(AylythItems.YMPE_GLAIVE, "Ympe Glaive");
        builder.add(AylythItems.YMPE_FLAMBERGE, "Ympe Flamberge");
        builder.add(AylythItems.YMPE_SCYTHE, "Ympe Scythe");
        builder.add(AylythItems.YMPE_MUSH, "Ympe Mush");
        builder.add(AylythItems.YMPE_FRUIT, "Ympe Fruit");
        builder.add(AylythItems.SHUCKED_YMPE_FRUIT, "Shucked Ympe Fruit");
        builder.add(AylythItems.NYSIAN_GRAPES, "Nysian Grapes");
        builder.add(AylythItems.CORIC_SEED, "Coric Seed");
        builder.add(AylythItems.AYLYTHIAN_HEART, "Aylythian Heart");
        builder.add(AylythItems.NEPHRITE_HEART, "Nephrite Heart");
        builder.add(AylythItems.YHONDYTH_HEART, "Yhondyth Heart");
        builder.add(AylythItems.PILOT_LIGHT_SPAWN_EGG, "Pilot Light Spawn Egg");
        builder.add(AylythItems.AYLYTHIAN_SPAWN_EGG, "Aylythian Spawn Egg");
        builder.add(AylythItems.ELDER_AYLYTHIAN_SPAWN_EGG, "Elder Aylythian Spawn Egg");
        builder.add(AylythItems.SCION_SPAWN_EGG, "Scion Spawn Egg");
        builder.add(AylythItems.FAUNAYLYTHIAN_SPAWN_EGG, "Faunaylytian Spawn Egg");
        builder.add(AylythItems.WREATHED_HIND_SPAWN_EGG, "Wreathed Hind Spawn Egg");
        builder.add(AylythItems.YMPEMOULD_SPAWN_EGG, "Ympemould Spawn Egg");
        builder.add(AylythItems.BONEFLY_SPAWN_EGG, "Bonefly Spawn Egg");
        builder.add(AylythItems.TULPA_SPAWN_EGG, "Tulpa Spawn Egg");
        builder.add(AylythItems.YMPE_EFFIGY, "Ympe Effigy");
        builder.add(AylythItems.WRONGMEAT, "Wrongmeat");
        builder.add(AylythItems.YMPE_CUIRASS, "Ympe Cuirass");
        builder.add(AylythItems.GIRASOL_SEED, "Girasol Seed");
        builder.add(AylythItems.AYLYTHIAN_UPGRADE_SMITHING_TEMPLATE, "Aylythian Upgrade Smithing Template");
        builder.add(AylythItems.ESSTLINE, "Esstline");
        builder.add(AylythItems.NEPHRITE, "Nephrite");
        builder.add(AylythItems.NEPHRITE_SWORD, "Nephrite Sword");
        builder.add(AylythItems.NEPHRITE_SHOVEL, "Nephrite Shovel");
        builder.add(AylythItems.NEPHRITE_PICKAXE, "Nephrite Pickaxe");
        builder.add(AylythItems.NEPHRITE_AXE, "Nephrite Axe");
        builder.add(AylythItems.NEPHRITE_HOE, "Nephrite Hoe");
        builder.add(AylythItems.VAMPIRIC_SWORD, "Vampiric Sword");
        builder.add(AylythItems.VAMPIRIC_PICKAXE, "Vampiric Pick");
        builder.add(AylythItems.VAMPIRIC_AXE, "Vampiric Axe");
        builder.add(AylythItems.VAMPIRIC_HOE, "Vampiric Sickle");
        builder.add(AylythItems.BLIGHTED_SWORD, "Blighted Sword");
        builder.add(AylythItems.BLIGHTED_PICKAXE, "Blighted Pick");
        builder.add(AylythItems.BLIGHTED_AXE, "Blighted Axe");
        builder.add(AylythItems.BLIGHTED_HOE, "Blighted Sickle");
        builder.add(AylythItems.POMEGRANATE_CASSETTE, "Cassette");
        builder.add(AylythItems.BARK, "Bark");
        builder.add(AylythItems.BLIGHTED_THORNS, "Blighted Thorns");
        builder.add(AylythItems.NEPHRITE_FLASK, "Nephrite Flask");
        builder.add(AylythItems.DARK_NEPHRITE_FLASK, "Dark Nephrite Flask");
        builder.add(AylythItems.THORN_FLECHETTE, "Thorn Flechette");
        builder.add(AylythItems.BLIGHTED_THORN_FLECHETTE, "Blighted Thorn Flechette");
        builder.add(AylythItems.LANCEOLATE_DAGGER, "Lanceolate Dagger");
        potionSet(builder, "mortechis");
        potionSet(builder, "cimmerian");
        potionSet(builder, "wyrded");
        potionSet(builder, "blight");

        builder.add("item.aylyth.glaive.desc_1", "\u00a76\u00a7oIt is the lament of the fallen");
        builder.add("item.aylyth.glaive.desc_2", "\u00a76\u00a7owhich pushes the living onward.");
        builder.add("item.aylyth.pomegranate_cassette.desc", "DEMON AND MAX - Pomegranate");
        builder.add("item.aylyth.smithing_template.aylythian_upgrade.applies_to", "Ympe Sapling");
        builder.add("item.aylyth.smithing_template.aylythian_upgrade.ingredients", "Esstline");
        builder.add("item.aylyth.smithing_template.aylythian_upgrade.base_slot_description", "Ympe Sapling");
        builder.add("item.aylyth.smithing_template.aylythian_upgrade.additions_slot_description", "Esstline");
        builder.add("upgrade.aylyth.aylythian_upgrade", "Aylythian Upgrade");

        // ENTITIES

        builder.add(AylythEntityTypes.PILOT_LIGHT, "Pilot Light");
        builder.add(AylythEntityTypes.AYLYTHIAN, "Aylythian");
        builder.add(AylythEntityTypes.ELDER_AYLYTHIAN, "Elder Aylythian");
        builder.add(AylythEntityTypes.YMPE_LANCE, "Ympe Lance");
        builder.add(AylythEntityTypes.SOULMOULD, "Mould of Souls");
        builder.add(AylythEntityTypes.BONEFLY, "Bonefly");
        builder.add(AylythEntityTypes.SCION, "Scion");
        builder.add(AylythEntityTypes.TULPA, "Tulpa");
        builder.add(AylythEntityTypes.WREATHED_HIND_ENTITY, "Wreathed Hind");
        builder.add(AylythEntityTypes.FAUNAYLYTHIAN, "Faunaylythian");

        // STATUS EFFECTS

        builder.add(AylythStatusEffects.MORTECHIS, "Mortechis");
        builder.add(AylythStatusEffects.CIMMERIAN, "Cimmerian");
        builder.add(AylythStatusEffects.WYRDED, "Wyrded");
        builder.add(AylythStatusEffects.BLIGHT, "Blight");
        builder.add(AylythStatusEffects.CRIMSON_CURSE, "Crimson Curse");

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
        builder.add("subtitles.aylyth.entity.soulmould.ambient", "Ympemould groans");
        builder.add("subtitles.aylyth.entity.soulmould.attack", "Ympemould attacks");
        builder.add("subtitles.aylyth.entity.soulmould.damage", "Ympemould hurts");
        builder.add("subtitles.aylyth.entity.soulmould.death", "Ympemould dies");
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

        biome(builder, AylythBiomes.COPSE, "Copse");
        biome(builder, AylythBiomes.CONIFEROUS_COPSE, "Coniferous Copse");
        biome(builder, AylythBiomes.DEEPWOOD, "Deepwood");
        biome(builder, AylythBiomes.CONIFEROUS_DEEPWOOD, "Coniferous Deepwood");
        biome(builder, AylythBiomes.CLEARING, "Clearing");
        biome(builder, AylythBiomes.OVERGROWN_CLEARING, "Overgrown Clearing");
        biome(builder, AylythBiomes.UPLANDS, "Uplands");
        biome(builder, AylythBiomes.MIRE, "Mire");
        biome(builder, AylythBiomes.BOWELS, "Bowels");

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
        builder.add("tag.aylyth.nephrite_flasks", "Nephrite Flasks");
        builder.add("tag.aylyth.boss_hearts", "Hearts of Bosses");
        builder.add("tag.aylyth.chthonia_wood", "Chthonia Wood");
        builder.add("tag.aylyth.heart_harvesters", "Heart Harvesters");
        builder.add("tag.aylyth.ympe_fruit_harvesters", "Ympe Fruit Harvesters");
        builder.add("tag.aylyth.storage_blocks.esstline", "Esstline Storage Blocks");
        builder.add("tag.aylyth.storage_blocks.nephrite", "Nephrite Storage Blocks");
    }
    
    private void biome(TranslationBuilder builder, RegistryKey<Biome> biomeKey, String translation) {
        builder.add(biomeKey.getValue().toTranslationKey("biome"), translation);
    }

    private void potionSet(TranslationBuilder builder, String effectName) {
        builder.add("item.minecraft.potion.effect.%s".formatted(effectName), "Potion of %s".formatted(StringUtil.capitalize(effectName)));
        builder.add("item.minecraft.splash_potion.effect.%s".formatted(effectName), "Splash Potion of %s".formatted(StringUtil.capitalize(effectName)));
        builder.add("item.minecraft.tipped_arrow.effect.%s".formatted(effectName), "Arrow of %s".formatted(StringUtil.capitalize(effectName)));
        builder.add("item.minecraft.lingering_potion.effect.%s".formatted(effectName), "Lingering Potion of %s".formatted(StringUtil.capitalize(effectName)));
    }
}
