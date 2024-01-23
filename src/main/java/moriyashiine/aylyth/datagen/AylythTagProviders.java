package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.common.registry.key.ModBiomeKeys;
import moriyashiine.aylyth.common.registry.key.ModDamageTypeKeys;
import moriyashiine.aylyth.common.registry.tag.*;
import moriyashiine.aylyth.common.registry.util.WoodSuite;
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
            getOrCreateTagBuilder(ConventionalBiomeTags.FOREST).add(ModBiomeKeys.OVERGROWN_CLEARING_ID, ModBiomeKeys.COPSE_ID, ModBiomeKeys.DEEPWOOD_ID);
            getOrCreateTagBuilder(ConventionalBiomeTags.TAIGA).add(ModBiomeKeys.CONIFEROUS_COPSE_ID, ModBiomeKeys.CONIFEROUS_DEEPWOOD_ID, ModBiomeKeys.DEEPWOOD_ID);
            getOrCreateTagBuilder(ModBiomeTags.GENERATES_SEEP).addOptionalTag(ConventionalBiomeTags.FOREST).addOptionalTag(ConventionalBiomeTags.TAIGA);
            getOrCreateTagBuilder(BiomeTags.HAS_CLOSER_WATER_FOG).add(ModBiomeKeys.MIRE_ID);
        }
    }

    public static class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

        public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
            super(output, registries);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries) {
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).addTag(ModBlockTags.WOODY_GROWTHS).add(ModBlocks.AYLYTH_BUSH, ModBlocks.ANTLER_SHOOTS, ModBlocks.GRIPWEED, ModBlocks.NYSIAN_GRAPE_VINE, ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD, ModBlocks.SEEPING_WOOD_SEEP);
            getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(ModBlocks.YMPE_LEAVES, ModBlocks.POMEGRANATE_LEAVES, ModBlocks.WRITHEWOOD_LEAVES);
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(ModBlocks.SOUL_HEARTH).add(ModBlocks.VITAL_THURIBLE);
            getOrCreateTagBuilder(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(ModBlocks.OAK_STREWN_LEAVES, ModBlocks.YMPE_STREWN_LEAVES);
            getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(ModBlocks.NYSIAN_GRAPE_VINE);
            getOrCreateTagBuilder(ModBlockTags.SEEPS).add(ModBlocks.OAK_SEEP, ModBlocks.SPRUCE_SEEP, ModBlocks.DARK_OAK_SEEP, ModBlocks.YMPE_SEEP, ModBlocks.SEEPING_WOOD_SEEP);
            getOrCreateTagBuilder(ModBlockTags.WOODY_GROWTHS).add(ModBlocks.SMALL_WOODY_GROWTH, ModBlocks.LARGE_WOODY_GROWTH, ModBlocks.WOODY_GROWTH_CACHE);
            getOrCreateTagBuilder(ModBlockTags.JACK_O_LANTERN_GENERATE_ON).add(Blocks.DARK_OAK_LOG, Blocks.OAK_LOG);
            getOrCreateTagBuilder(ModBlockTags.GHOSTCAP_REPLACEABLE).add(Blocks.GRASS_BLOCK);
            addSuite(ModBlocks.YMPE_BLOCKS, ModBlockTags.YMPE_LOGS);
            getOrCreateTagBuilder(ModBlockTags.YMPE_LOGS).add(ModBlocks.FRUIT_BEARING_YMPE_LOG);
            addSuite(ModBlocks.POMEGRANATE_BLOCKS, ModBlockTags.POMEGRANATE_LOGS);
            addSuite(ModBlocks.WRITHEWOOD_BLOCKS, ModBlockTags.WRITHEWOOD_LOGS);
            getOrCreateTagBuilder(BlockTags.LEAVES).add(ModBlocks.YMPE_LEAVES, ModBlocks.POMEGRANATE_LEAVES, ModBlocks.WRITHEWOOD_LEAVES);
            getOrCreateTagBuilder(ModBlockTags.WOODY_GROWTHS_GENERATE_ON).add(Blocks.MUD);
            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(ModBlocks.SEEPING_WOOD);
            getOrCreateTagBuilder(BlockTags.BEACON_BASE_BLOCKS).add(ModBlocks.ESSTLINE_BLOCK, ModBlocks.NEPHRITE_BLOCK);
            getOrCreateTagBuilder(ModBlockTags.CARVED_NEPHRITE).add(ModBlocks.CARVED_SMOOTH_NEPHRITE, ModBlocks.CARVED_ANTLERED_NEPHRITE, ModBlocks.CARVED_NEPHRITE_PILLAR, ModBlocks.POLISHED_CARVED_NEPHRITE, ModBlocks.CARVED_NEPHRITE_TILES, ModBlocks.CARVED_WOODY_NEPHRITE);
        }

        private void addSuite(WoodSuite suite, TagKey<Block> logTag) {
            getOrCreateTagBuilder(logTag).add(suite.log, suite.wood, suite.strippedLog, suite.strippedWood);

            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).addTag(logTag);
            getOrCreateTagBuilder(BlockTags.PLANKS).add(suite.planks);
            getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS).add(suite.stairs);
            getOrCreateTagBuilder(BlockTags.WOODEN_SLABS).add(suite.slab);
            getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(suite.fence);
            getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(suite.fenceGate);
            getOrCreateTagBuilder(BlockTags.WOODEN_BUTTONS).add(suite.button);
            getOrCreateTagBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(suite.pressurePlate);
            getOrCreateTagBuilder(BlockTags.WOODEN_DOORS).add(suite.door);
            getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS).add(suite.trapdoor);
            getOrCreateTagBuilder(BlockTags.STANDING_SIGNS).add(suite.floorSign);
            getOrCreateTagBuilder(BlockTags.WALL_SIGNS).add(suite.wallSign);
            getOrCreateTagBuilder(BlockTags.SAPLINGS).add(suite.sapling);
            getOrCreateTagBuilder(BlockTags.FLOWER_POTS).add(suite.pottedSapling);
        }
    }

    public static class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

        public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries, @Nullable BlockTagProvider blockTagProvider) {
            super(output, registries, blockTagProvider);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup registries) {
            getOrCreateTagBuilder(ItemTags.BOATS).add(ModItems.YMPE_ITEMS.boat, ModItems.POMEGRANATE_ITEMS.boat, ModItems.WRITHEWOOD_ITEMS.boat);
            getOrCreateTagBuilder(ItemTags.CHEST_BOATS).add(ModItems.YMPE_ITEMS.chestBoat, ModItems.POMEGRANATE_ITEMS.chestBoat, ModItems.WRITHEWOOD_ITEMS.chestBoat);
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
