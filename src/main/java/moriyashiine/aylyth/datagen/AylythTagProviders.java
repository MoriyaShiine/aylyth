package moriyashiine.aylyth.datagen;

import dev.architectury.platform.Mod;
import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.common.registry.key.ModBiomeKeys;
import moriyashiine.aylyth.common.registry.key.ModDamageTypeKeys;
import moriyashiine.aylyth.common.registry.tag.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEntityTypeTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.*;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class AylythTagProviders {

    public static class ModBiomeTagProvider extends FabricTagProvider<Biome> {
        public ModBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
            super(output, RegistryKeys.BIOME, registries);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries) {
            getOrCreateTagBuilder(ModBiomeTags.IS_CLEARING).add(ModBiomeKeys.CLEARING, ModBiomeKeys.OVERGROWN_CLEARING);
            getOrCreateTagBuilder(ModBiomeTags.IS_COPSE).add(ModBiomeKeys.COPSE, ModBiomeKeys.CONIFEROUS_COPSE);
            getOrCreateTagBuilder(ModBiomeTags.IS_DEEPWOOD).add(ModBiomeKeys.DEEPWOOD, ModBiomeKeys.CONIFEROUS_DEEPWOOD);
            getOrCreateTagBuilder(ModBiomeTags.IS_CONIFEROUS).add(ModBiomeKeys.CONIFEROUS_COPSE, ModBiomeKeys.CONIFEROUS_DEEPWOOD);
            getOrCreateTagBuilder(ModBiomeTags.IS_FOREST_LIKE).add(ModBiomeKeys.OVERGROWN_CLEARING, ModBiomeKeys.COPSE, ModBiomeKeys.DEEPWOOD);
            getOrCreateTagBuilder(ModBiomeTags.IS_TAIGA_LIKE).addTag(ModBiomeTags.IS_CONIFEROUS).add(ModBiomeKeys.DEEPWOOD);
            getOrCreateTagBuilder(ModBiomeTags.GENERATES_SEEP).addOptionalTag(ConventionalBiomeTags.FOREST).addOptionalTag(ConventionalBiomeTags.TAIGA);
            getOrCreateTagBuilder(BiomeTags.HAS_CLOSER_WATER_FOG).add(ModBiomeKeys.MIRE);
        }
    }

    public static class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

        public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
            super(output, registries);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries) {
            getOrCreateTagBuilder(ModBlockTags.CARVED_NEPHRITE).add(ModBlocks.CARVED_SMOOTH_NEPHRITE, ModBlocks.CARVED_ANTLERED_NEPHRITE, ModBlocks.CARVED_NEPHRITE_PILLAR, ModBlocks.CARVED_NEPHRITE_TILES, ModBlocks.CARVED_WOODY_NEPHRITE);
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).addTag(ModBlockTags.WOODY_GROWTHS).add(ModBlocks.AYLYTH_BUSH, ModBlocks.ANTLER_SHOOTS, ModBlocks.GRIPWEED, ModBlocks.NYSIAN_GRAPE_VINE, ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD, ModBlocks.SEEPING_WOOD_SEEP, ModBlocks.DARK_WOODS_TILES);
            getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(ModBlocks.YMPE_LEAVES, ModBlocks.POMEGRANATE_LEAVES, ModBlocks.WRITHEWOOD_LEAVES);
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).addTag(ModBlockTags.CARVED_NEPHRITE).add(ModBlocks.SOUL_HEARTH, ModBlocks.VITAL_THURIBLE, ModBlocks.ESSTLINE_BLOCK, ModBlocks.NEPHRITE_BLOCK);
            getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(ModBlocks.OAK_STREWN_LEAVES, ModBlocks.YMPE_STREWN_LEAVES);
            getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(ModBlocks.NYSIAN_GRAPE_VINE);
            getOrCreateTagBuilder(ModBlockTags.SEEPS).add(ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD_SEEP);
            getOrCreateTagBuilder(ModBlockTags.WOODY_GROWTHS).add(ModBlocks.SMALL_WOODY_GROWTH, ModBlocks.LARGE_WOODY_GROWTH, ModBlocks.WOODY_GROWTH_CACHE);
            getOrCreateTagBuilder(ModBlockTags.JACK_O_LANTERN_GENERATE_ON).add(Blocks.DARK_OAK_LOG, Blocks.OAK_LOG);
            getOrCreateTagBuilder(ModBlockTags.GHOSTCAP_REPLACEABLE).add(Blocks.GRASS_BLOCK);

            getOrCreateTagBuilder(ModBlockTags.YMPE_LOGS).add(ModBlocks.YMPE_LOG, ModBlocks.YMPE_WOOD, ModBlocks.YMPE_STRIPPED_LOG, ModBlocks.YMPE_STRIPPED_WOOD);
            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(ModBlockTags.YMPE_LOGS);
            getOrCreateTagBuilder(BlockTags.PLANKS).add(ModBlocks.YMPE_PLANKS);
            getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(ModBlocks.YMPE_STAIRS);
            getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(ModBlocks.YMPE_SLAB);
            getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(ModBlocks.YMPE_FENCE);
            getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(ModBlocks.YMPE_FENCE_GATE);
            getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(ModBlocks.YMPE_BUTTON);
            getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.YMPE_PRESSURE_PLATE);
            getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(ModBlocks.YMPE_DOOR);
            getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.YMPE_TRAPDOOR);
            getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(ModBlocks.YMPE_SIGN);
            getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(ModBlocks.YMPE_WALL_SIGN);
            getOrCreateTagBuilder(BlockTags.SAPLINGS).add(ModBlocks.YMPE_SAPLING);
            getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(ModBlocks.YMPE_POTTED_SAPLING);
            getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(ModBlocks.YMPE_HANGING_SIGN);
            getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(ModBlocks.YMPE_WALL_HANGING_SIGN);
            getOrCreateTagBuilder(ModBlockTags.YMPE_LOGS).add(ModBlocks.FRUIT_BEARING_YMPE_LOG);

            getOrCreateTagBuilder(ModBlockTags.POMEGRANATE_LOGS).add(ModBlocks.POMEGRANATE_LOG, ModBlocks.POMEGRANATE_WOOD, ModBlocks.POMEGRANATE_STRIPPED_LOG, ModBlocks.POMEGRANATE_STRIPPED_WOOD);
            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(ModBlockTags.POMEGRANATE_LOGS);
            getOrCreateTagBuilder(BlockTags.PLANKS).add(ModBlocks.POMEGRANATE_PLANKS);
            getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(ModBlocks.POMEGRANATE_STAIRS);
            getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(ModBlocks.POMEGRANATE_SLAB);
            getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(ModBlocks.POMEGRANATE_FENCE);
            getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(ModBlocks.POMEGRANATE_FENCE_GATE);
            getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(ModBlocks.POMEGRANATE_BUTTON);
            getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.POMEGRANATE_PRESSURE_PLATE);
            getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(ModBlocks.POMEGRANATE_DOOR);
            getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.POMEGRANATE_TRAPDOOR);
            getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(ModBlocks.POMEGRANATE_SIGN);
            getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(ModBlocks.POMEGRANATE_WALL_SIGN);
            getOrCreateTagBuilder(BlockTags.SAPLINGS).add(ModBlocks.POMEGRANATE_SAPLING);
            getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(ModBlocks.POMEGRANATE_POTTED_SAPLING);
            getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(ModBlocks.POMEGRANATE_HANGING_SIGN);
            getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(ModBlocks.POMEGRANATE_WALL_HANGING_SIGN);

            getOrCreateTagBuilder(ModBlockTags.WRITHEWOOD_LOGS).add(ModBlocks.WRITHEWOOD_LOG, ModBlocks.WRITHEWOOD_WOOD, ModBlocks.WRITHEWOOD_STRIPPED_LOG, ModBlocks.WRITHEWOOD_STRIPPED_WOOD);
            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(ModBlockTags.WRITHEWOOD_LOGS);
            getOrCreateTagBuilder(BlockTags.PLANKS).add(ModBlocks.WRITHEWOOD_PLANKS);
            getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(ModBlocks.WRITHEWOOD_STAIRS);
            getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(ModBlocks.WRITHEWOOD_SLAB);
            getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(ModBlocks.WRITHEWOOD_FENCE);
            getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(ModBlocks.WRITHEWOOD_FENCE_GATE);
            getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(ModBlocks.WRITHEWOOD_BUTTON);
            getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.WRITHEWOOD_PRESSURE_PLATE);
            getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(ModBlocks.WRITHEWOOD_DOOR);
            getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.WRITHEWOOD_TRAPDOOR);
            getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(ModBlocks.WRITHEWOOD_SIGN);
            getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(ModBlocks.WRITHEWOOD_WALL_SIGN);
            getOrCreateTagBuilder(BlockTags.SAPLINGS).add(ModBlocks.WRITHEWOOD_SAPLING);
            getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(ModBlocks.WRITHEWOOD_POTTED_SAPLING);
            getOrCreateTagBuilder(BlockTags.CEILING_HANGING_SIGNS).add(ModBlocks.WRITHEWOOD_HANGING_SIGN);
            getOrCreateTagBuilder(BlockTags.WALL_HANGING_SIGNS).add(ModBlocks.WRITHEWOOD_WALL_HANGING_SIGN);
            
            getOrCreateTagBuilder(BlockTags.LEAVES).add(ModBlocks.YMPE_LEAVES, ModBlocks.POMEGRANATE_LEAVES, ModBlocks.WRITHEWOOD_LEAVES);
            getOrCreateTagBuilder(ModBlockTags.WOODY_GROWTHS_GENERATE_ON).add(Blocks.MUD);
            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(ModBlocks.SEEPING_WOOD);
            getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS).add(ModBlocks.ESSTLINE_BLOCK, ModBlocks.NEPHRITE_BLOCK);
        }
    }

    public static class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

        public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries, @Nullable BlockTagProvider blockTagProvider) {
            super(output, registries, blockTagProvider);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries) {
            getOrCreateTagBuilder(ItemTags.BOATS).add(ModItems.YMPE_BOAT, ModItems.POMEGRANATE_BOAT, ModItems.WRITHEWOOD_BOAT);
            getOrCreateTagBuilder(ItemTags.CHEST_BOATS).add(ModItems.YMPE_CHEST_BOAT, ModItems.POMEGRANATE_CHEST_BOAT, ModItems.WRITHEWOOD_CHEST_BOAT);
            getOrCreateTagBuilder(ModItemTags.YMPE_FOODS).add(ModItems.YMPE_FRUIT);
            copy(ModBlockTags.YMPE_LOGS, ModItemTags.YMPE_LOGS);
            copy(ModBlockTags.POMEGRANATE_LOGS, ModItemTags.POMEGRANATE_LOGS);
            copy(ModBlockTags.WRITHEWOOD_LOGS, ModItemTags.WRITHEWOOD_LOGS);
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
            copy(ModBlockTags.WOODY_GROWTHS, ModItemTags.WOODY_GROWTHS);
            copy(ModBlockTags.SEEPS, ModItemTags.SEEPS);
            copy(ModBlockTags.CARVED_NEPHRITE, ModItemTags.CARVED_NEPHRITE);
            getOrCreateTagBuilder(ModItemTags.PLEDGE_ITEMS).add(ModItems.NYSIAN_GRAPES);
            getOrCreateTagBuilder(ModItemTags.NEPHRITE_REPAIR_ITEMS).add(ModItems.NEPHRITE);
            getOrCreateTagBuilder(ItemTags.SWORDS).add(ModItems.NEPHRITE_SWORD, ModItems.VAMPIRIC_SWORD, ModItems.BLIGHTED_SWORD);
            getOrCreateTagBuilder(ItemTags.SHOVELS).add(ModItems.NEPHRITE_SHOVEL);
            getOrCreateTagBuilder(ItemTags.PICKAXES).add(ModItems.NEPHRITE_PICKAXE, ModItems.VAMPIRIC_PICKAXE, ModItems.BLIGHTED_PICKAXE);
            getOrCreateTagBuilder(ItemTags.AXES).add(ModItems.NEPHRITE_AXE, ModItems.VAMPIRIC_AXE, ModItems.BLIGHTED_AXE);
            getOrCreateTagBuilder(ItemTags.HOES).add(ModItems.NEPHRITE_HOE, ModItems.VAMPIRIC_HOE, ModItems.BLIGHTED_HOE);
            getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(ModItems.POMEGRANATE_CASSETTE);
        }
    }

    public static class ModEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {

        public ModEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
            super(output, registries);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries) {
            getOrCreateTagBuilder(ModEntityTypeTags.GRIPWEED_IMMUNE).add(ModEntityTypes.AYLYTHIAN, ModEntityTypes.ELDER_AYLYTHIAN);
            getOrCreateTagBuilder(ModEntityTypeTags.SHUCK_BLACKLIST).forceAddTag(ConventionalEntityTypeTags.BOSSES).add(EntityType.ELDER_GUARDIAN);
        }
    }

    public static class ModDamageTypeTagProvider extends FabricTagProvider<DamageType> {

        public ModDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
            super(output, RegistryKeys.DAMAGE_TYPE, registries);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries) {
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR).add(ModDamageTypeKeys.YMPE, ModDamageTypeKeys.KILLING_BLOW, ModDamageTypeKeys.SOUL_RIP);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(ModDamageTypeKeys.YMPE, ModDamageTypeKeys.KILLING_BLOW);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_RESISTANCE).add(ModDamageTypeKeys.YMPE, ModDamageTypeKeys.KILLING_BLOW);
//            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY).add(ModDamageTypeKeys.YMPE, ModDamageTypeKeys.UNBLOCKABLE);
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS).add(ModDamageTypeKeys.KILLING_BLOW);
            getOrCreateTagBuilder(DamageTypeTags.WITCH_RESISTANT_TO).add(ModDamageTypeKeys.SOUL_RIP);
            getOrCreateTagBuilder(DamageTypeTags.AVOIDS_GUARDIAN_THORNS).add(ModDamageTypeKeys.SOUL_RIP);
            getOrCreateTagBuilder(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH).add(ModDamageTypeKeys.SOUL_RIP);
            getOrCreateTagBuilder(ModDamageTypeTags.IS_YMPE).add(ModDamageTypeKeys.YMPE, ModDamageTypeKeys.YMPE_ENTITY);
        }
    }
}
