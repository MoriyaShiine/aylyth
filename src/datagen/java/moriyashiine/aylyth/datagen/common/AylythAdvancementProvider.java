package moriyashiine.aylyth.datagen.common;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.advancement.criteria.HindPledgeCriterion;
import moriyashiine.aylyth.common.advancement.criteria.YmpeInfestationCriterion;
import moriyashiine.aylyth.common.block.AylythBlocks;
import moriyashiine.aylyth.common.entity.AylythEntityTypes;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.item.potion.AylythPotions;
import moriyashiine.aylyth.common.data.world.AylythBiomes;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.*;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.predicate.entity.EntityEffectPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.predicate.item.ItemSubPredicateTypes;
import net.minecraft.predicate.item.PotionContentsPredicate;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.text.Text;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.biome.Biome;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class AylythAdvancementProvider extends FabricAdvancementProvider {
    public AylythAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        var root = Advancement.Builder.create()
                .display(AylythItems.YMPE_SAPLING.getDefaultStack(), Text.translatable("advancements.aylyth.aylyth.root.title"), Text.translatable("advancements.aylyth.aylyth.root.desc"), Aylyth.id("textures/block/ympe_planks.png"), AdvancementFrame.TASK, true, true, false)
                .criterion("entered_aylyth", ChangedDimensionCriterion.Conditions.to(AylythDimensionData.WORLD))
                .build(consumer, "aylyth:aylyth/root");
        // TODO: Setup models for rendering. Probably just use a stick item with the custom model.
//        Advancement.Builder.create()
//                .parent(root)
//                .display(TextureRendererData.standard(Aylyth.id("textures/mob_effect/cimmerian.png")), Text.translatable("advancements.aylyth.aylyth.cimmerianed.title"), Text.translatable("advancements.aylyth.aylyth.cimmerianed.desc"), null, AdvancementFrame.TASK, true, true, false)
//                .criterion("has_cimmerian_effect", effectsChangedCriteria(EntityEffectPredicate.Builder.create().addEffect(AylythStatusEffects.CIMMERIAN).build().get()))
//                .build(consumer, "aylyth:aylyth/cimmerianed");
//        Advancement.Builder.create()
//                .parent(root)
//                .display(TextureRendererData.standard(Aylyth.id("textures/mob_effect/wyrded.png")), Text.translatable("advancements.aylyth.aylyth.wyrded.title"), Text.translatable("advancements.aylyth.aylyth.wyrded.desc"), null, AdvancementFrame.TASK, true, true, false)
//                .criterion("has_wyrded_effect", effectsChangedCriteria(EntityEffectPredicate.Builder.create().addEffect(AylythStatusEffects.WYRDED).build().get()))
//                .build(consumer, "aylyth:aylyth/wyrded");
        var inTheBranches = Advancement.Builder.create()
                .parent(root)
                .display(AylythItems.YMPE_DAGGER, Text.translatable("advancements.aylyth.aylyth.in_the_branches.title"), Text.translatable("advancements.aylyth.aylyth.in_the_branches.desc"), null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_ympe_infestation", YmpeInfestationCriterion.Conditions.create(UniformIntProvider.create(1, 6)))
                .build(consumer, "aylyth:aylyth/in_the_branches");
        var lifeAtACost = Advancement.Builder.create()
                .parent(inTheBranches)
                .display(AylythItems.YMPE_FRUIT, Text.translatable("advancements.aylyth.aylyth.life_at_a_cost.title"), Text.translatable("advancements.aylyth.aylyth.life_at_a_cost.desc"), null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_ympe_fruit", InventoryChangedCriterion.Conditions.items(AylythItems.YMPE_FRUIT))
                .build(consumer, "aylyth:aylyth/life_at_a_cost");
        // TODO: Fix after setting up items
//        Advancement.Builder.create()
//                .parent(lifeAtACost)
//                .display(stackWithNbt(AylythItems.SHUCKED_YMPE_FRUIT, nbtCompound -> nbtCompound.putInt("StoredEntity", 1)), Text.translatable("advancements.aylyth.aylyth.daemon_ritus.title"), Text.translatable("advancements.aylyth.aylyth.daemon_ritus.desc"), null, AdvancementFrame.GOAL, true, true, false)
//                .criterion("has_shucked", ShuckingCriterion.Conditions.create())
//                .build(consumer, "aylyth:aylyth/daemon_ritus");
        Advancement.Builder.create()
                .parent(lifeAtACost)
                .display(AylythItems.CORIC_SEED, Text.translatable("advancements.aylyth.aylyth.manufactured_for_a_purpose.title"), Text.translatable("advancements.aylyth.aylyth.manufactured_for_a_purpose.desc"), null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_coric_seed", InventoryChangedCriterion.Conditions.items(AylythItems.CORIC_SEED))
                .build(consumer, "aylyth:aylyth/manufactured_for_a_purpose");
        var laccus = Advancement.Builder.create()
                .parent(root)
                .display(AylythItems.NYSIAN_GRAPE_VINE, Text.translatable("advancements.aylyth.aylyth.laccus.title"), Text.translatable("advancements.aylyth.aylyth.laccus.desc"), null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_nysian_grapes", InventoryChangedCriterion.Conditions.items(AylythItems.NYSIAN_GRAPES))
                .build(consumer, "aylyth:aylyth/laccus");
        Advancement.Builder.create()
                .parent(laccus)
                .display(potionItem(AylythPotions.MORTECHIS), Text.translatable("advancements.aylyth.aylyth.libations.title"), Text.translatable("advancements.aylyth.aylyth.libations.desc"), null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_mortechis_potion", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().subPredicate(ItemSubPredicateTypes.POTION_CONTENTS, new PotionContentsPredicate(RegistryEntryList.of(AylythPotions.MORTECHIS))).build()))
                .build(consumer, "aylyth:aylyth/libations");
        Advancement.Builder.create()
                .parent(laccus)
                .display(AylythItems.NYSIAN_GRAPES, Text.translatable("advancements.aylyth.aylyth.come_wayward_souls.title"), Text.translatable("advancements.aylyth.aylyth.come_wayward_souls.desc"), null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_pledged", HindPledgeCriterion.Conditions.create())
                .build(consumer, "aylyth:aylyth/come_wayward_souls");
        // TODO: Fix display
        var dontLookBack = Advancement.Builder.create()
                .parent(root)
//                .display(new TextureRendererData(Aylyth.id("textures/particle/pilot_light.png"), 0xFFFF33FF), Text.translatable("advancements.aylyth.aylyth.dont_look_back.title"), Text.translatable("advancements.aylyth.aylyth.dont_look_back.desc"), null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_interacted_with_pilot_light", PlayerInteractedWithEntityCriterion.Conditions.create(ItemPredicate.Builder.create(), Optional.of(EntityPredicate.asLootContextPredicate(EntityPredicate.Builder.create().type(EntityTypePredicate.create(registryLookup.getOrThrow(RegistryKeys.ENTITY_TYPE), AylythEntityTypes.PILOT_LIGHT)).build()))))
                .build(consumer, "aylyth:aylyth/dont_look_back");
        Advancement.Builder.create()
                .parent(dontLookBack)
                .display(AylythItems.AYLYTHIAN_HEART, Text.translatable("advancements.aylyth.aylyth.into_the_fire_we_fly.title"), Text.translatable("advancements.aylyth.aylyth.into_the_fire_we_fly.desc"), null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_aylythian_heart", InventoryChangedCriterion.Conditions.items(AylythItems.AYLYTHIAN_HEART))
                .build(consumer, "aylyth:aylyth/into_the_fire_we_fly");
        Advancement.Builder.create()
                .parent(root)
                .display(AylythItems.SOUL_HEARTH, Text.translatable("advancements.aylyth.aylyth.something_between_life_and_death.title"), Text.translatable("advancements.aylyth.aylyth.something_between_life_and_death.desc"), null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_placed_soul_hearth", ItemCriterion.Conditions.createPlacedBlock(AylythBlocks.SOUL_HEARTH))
                .build(consumer, "aylyth:aylyth/something_between_life_and_death");
        var meatFound = Advancement.Builder.create()
                .parent(root)
                .display(AylythItems.WRONGMEAT, Text.translatable("advancements.aylyth.aylyth.meat_found.title"), Text.translatable("advancements.aylyth.aylyth.meat_found.desc"), null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_wrongmeat", InventoryChangedCriterion.Conditions.items(AylythItems.WRONGMEAT))
                .build(consumer, "aylyth:aylyth/meat_found");
        Advancement.Builder.create()
                .parent(meatFound)
                .display(AylythItems.VITAL_THURIBLE, Text.translatable("advancements.aylyth.aylyth.flesh_increased_by_two.title"), Text.translatable("advancements.aylyth.aylyth.flesh_increased_by_two.desc"), null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_placed_vital_thurible", ItemCriterion.Conditions.createPlacedBlock(AylythBlocks.VITAL_THURIBLE))
                .build(consumer, "aylyth:aylyth/flesh_increased_by_two");
        Advancement.Builder.create()
                .parent(root)
                .display(AylythItems.WRITHEWOOD_DOOR, Text.translatable("advancements.aylyth.aylyth.way_of_the_wood.title"), Text.translatable("advancements.aylyth.aylyth.way_of_the_wood.desc"), null, AdvancementFrame.CHALLENGE, true, true, false)
                .criterion("has_visited_copse", biomeCriterion(registryLookup, AylythBiomes.COPSE))
                .criterion("has_visited_coniferous_copse", biomeCriterion(registryLookup, AylythBiomes.CONIFEROUS_COPSE))
                .criterion("has_visited_deepwood", biomeCriterion(registryLookup, AylythBiomes.DEEPWOOD))
                .criterion("has_visited_coniferous_deepwood", biomeCriterion(registryLookup, AylythBiomes.CONIFEROUS_DEEPWOOD))
                .criterion("has_visited_clearing", biomeCriterion(registryLookup, AylythBiomes.CLEARING))
                .criterion("has_visited_overgrown_clearing", biomeCriterion(registryLookup, AylythBiomes.OVERGROWN_CLEARING))
                .criterion("has_visited_uplands", biomeCriterion(registryLookup, AylythBiomes.UPLANDS))
                .criterion("has_visited_mire", biomeCriterion(registryLookup, AylythBiomes.MIRE))
                .criterion("has_visited_bowels", biomeCriterion(registryLookup, AylythBiomes.BOWELS))
                .build(consumer, "aylyth:aylyth/way_of_the_wood");
        Advancement.Builder.create()
                .parent(root)
                .display(AylythItems.GIRASOL_SEED, Text.translatable("advancements.aylyth.aylyth.see_you_in_the_trees.title"), Text.translatable("advancements.aylyth.aylyth.see_you_in_the_trees.desc"), null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_placed_girasol_sapling", ItemCriterion.Conditions.createPlacedBlock(AylythBlocks.GIRASOL_SAPLING))
                .build(consumer, "aylyth:aylyth/see_you_in_the_trees");
    }

    private ItemStack potionItem(RegistryEntry<Potion> potion) {
        return PotionContentsComponent.createStack(Items.POTION, potion);
    }

    private AdvancementCriterion<EffectsChangedCriterion.Conditions> effectsChangedCriteria(EntityEffectPredicate.Builder predicate) {
        return EffectsChangedCriterion.Conditions.create(predicate);
    }
    
    private AdvancementCriterion<TickCriterion.Conditions> biomeCriterion(RegistryWrapper.WrapperLookup registryLookup, RegistryKey<Biome> biomeKey) {
        return TickCriterion.Conditions.createLocation(LocationPredicate.Builder.createBiome(registryLookup.getOrThrow(RegistryKeys.BIOME).getOrThrow(biomeKey)));
    } 
}
