package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.advancement.CustomAdvancementDisplay;
import moriyashiine.aylyth.common.criteria.HindPledgeCriterion;
import moriyashiine.aylyth.common.criteria.ShuckingCriterion;
import moriyashiine.aylyth.common.criteria.YmpeInfestationCriterion;
import moriyashiine.aylyth.common.registry.*;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.predicate.entity.EntityEffectPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class AylythAdvancementProvider extends FabricAdvancementProvider {

    protected AylythAdvancementProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        // TODO: it'll take some mixins, but I would like to replace the potion things with the mobeffect texture instead
        var root = Advancement.Builder.create()
                .display(ModItems.YMPE_ITEMS.sapling.getDefaultStack(), Text.translatable("aylyth.advancements.aylyth.root.title"), Text.translatable("aylyth.advancements.aylyth.root.desc"), new Identifier(Aylyth.MOD_ID, "textures/block/ympe_planks.png"), AdvancementFrame.TASK, true, false, false)
                .criterion("entered_aylyth", ChangedDimensionCriterion.Conditions.to(ModDimensionKeys.AYLYTH))
                .build(consumer, "aylyth:aylyth/root");
        Advancement.Builder.create()
                .parent(root)
                .display(new CustomAdvancementDisplay(AylythUtil.id("textures/mob_effect/cimmerian.png"), Text.translatable("aylyth.advancements.aylyth.cimmerianed.title"), Text.translatable("aylyth.advancements.aylyth.cimmerianed.desc"), null, AdvancementFrame.TASK, true, false, false))
                .criterion("has_cimmerian_effect", effectsChangedCriteria(EntityEffectPredicate.create().withEffect(ModPotions.CIMMERIAN_EFFECT)))
                .build(consumer, "aylyth:aylyth/cimmerianed");
        Advancement.Builder.create()
                .parent(root)
                .display(new CustomAdvancementDisplay(AylythUtil.id("textures/mob_effect/wyrded.png"), Text.translatable("aylyth.advancements.aylyth.wyrded.title"), Text.translatable("aylyth.advancements.aylyth.wyrded.desc"), null, AdvancementFrame.TASK, true, false, false))
                .criterion("has_wyrded_effect", effectsChangedCriteria(EntityEffectPredicate.create().withEffect(ModPotions.WYRDED_EFFECT)))
                .build(consumer, "aylyth:aylyth/wyrded");
        var inTheBranches = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.YMPE_DAGGER, Text.translatable("aylyth.advancements.aylyth.in_the_branches.title"), Text.translatable("aylyth.advancements.aylyth.in_the_branches.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_ympe_infestation", YmpeInfestationCriterion.Conditions.create(UniformIntProvider.create(1, 6)))
                .build(consumer, "aylyth:aylyth/in_the_branches");
        var lifeAtACost = Advancement.Builder.create()
                .parent(inTheBranches)
                .display(ModItems.YMPE_FRUIT, Text.translatable("aylyth.advancements.aylyth.life_at_a_cost.title"), Text.translatable("aylyth.advancements.aylyth.life_at_a_cost.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_ympe_fruit", InventoryChangedCriterion.Conditions.items(ModItems.YMPE_FRUIT))
                .build(consumer, "aylyth:aylyth/life_at_a_cost");
        Advancement.Builder.create()
                .parent(lifeAtACost)
                .display(stackWithNbt(ModItems.SHUCKED_YMPE_FRUIT, nbtCompound -> nbtCompound.putInt("StoredEntity", 1)), Text.translatable("aylyth.advancements.aylyth.daemon_ritus.title"), Text.translatable("aylyth.advancements.aylyth.daemon_ritus.desc"), null, AdvancementFrame.GOAL, true, false, false)
                .criterion("has_shucked", ShuckingCriterion.Conditions.create())
                .build(consumer, "aylyth:aylyth/daemon_ritus");
        Advancement.Builder.create()
                .parent(lifeAtACost)
                .display(ModItems.CORIC_SEED, Text.translatable("aylyth.advancements.aylyth.manufactured_for_a_purpose.title"), Text.translatable("aylyth.advancements.aylyth.manufactured_for_a_purpose.desc"), null, AdvancementFrame.GOAL, true, false, false)
                .criterion("has_coric_seed", InventoryChangedCriterion.Conditions.items(ModItems.CORIC_SEED))
                .build(consumer, "aylyth:aylyth/manufactured_for_a_purpose");
        var laccus = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.NYSIAN_GRAPE_VINE, Text.translatable("aylyth.advancements.aylyth.laccus.title"), Text.translatable("aylyth.advancements.aylyth.laccus.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_nysian_grapes", InventoryChangedCriterion.Conditions.items(ModItems.NYSIAN_GRAPES))
                .build(consumer, "aylyth:aylyth/laccus");
        Advancement.Builder.create()
                .parent(laccus)
                .display(potionItem(ModPotions.MORTECHIS_POTION), Text.translatable("aylyth.advancements.aylyth.libations.title"), Text.translatable("aylyth.advancements.aylyth.libations.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_mortechis_potion", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().potion(ModPotions.MORTECHIS_POTION).build()))
                .build(consumer, "aylyth:aylyth/libations");
        Advancement.Builder.create()
                .parent(laccus)
                .display(ModItems.NYSIAN_GRAPES, Text.translatable("aylyth.advancements.aylyth.come_wayward_souls.title"), Text.translatable("aylyth.advancements.aylyth.come_wayward_souls.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_pledged", HindPledgeCriterion.Conditions.create())
                .build(consumer, "aylyth:aylyth/come_wayward_souls");
        var dontLookBack = Advancement.Builder.create()
                .parent(root)
                .display(Items.SOUL_CAMPFIRE, Text.translatable("aylyth.advancements.aylyth.dont_look_back.title"), Text.translatable("aylyth.advancements.aylyth.dont_look_back.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_interacted_with_pilot_light", PlayerInteractedWithEntityCriterion.Conditions.create(ItemPredicate.Builder.create(), EntityPredicate.Extended.ofLegacy(EntityPredicate.Builder.create().type(ModEntityTypes.PILOT_LIGHT).build())))
                .build(consumer, "aylyth:aylyth/dont_look_back");
        Advancement.Builder.create()
                .parent(dontLookBack)
                .display(ModItems.AYLYTHIAN_HEART, Text.translatable("aylyth.advancements.aylyth.into_the_fire_we_fly.title"), Text.translatable("aylyth.advancements.aylyth.into_the_fire_we_fly.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_aylythian_heart", InventoryChangedCriterion.Conditions.items(ModItems.AYLYTHIAN_HEART))
                .build(consumer, "aylyth:aylyth/into_the_fire_we_fly");
        Advancement.Builder.create()
                .parent(root)
                .display(ModItems.SOUL_HEARTH, Text.translatable("aylyth.advancements.aylyth.something_between_life_and_death.title"), Text.translatable("aylyth.advancements.aylyth.something_between_life_and_death.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_placed_soul_hearth", PlacedBlockCriterion.Conditions.block(ModBlocks.SOUL_HEARTH))
                .build(consumer, "aylyth:aylyth/something_between_life_and_death");
        var meatFound = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.WRONGMEAT, Text.translatable("aylyth.advancements.aylyth.meat_found.title"), Text.translatable("aylyth.advancements.aylyth.meat_found.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_wrongmeat", InventoryChangedCriterion.Conditions.items(ModItems.WRONGMEAT))
                .build(consumer, "aylyth:aylyth/meat_found");
        Advancement.Builder.create()
                .parent(meatFound)
                .display(ModItems.VITAL_THURIBLE, Text.translatable("aylyth.advancements.aylyth.flesh_increased_by_two.title"), Text.translatable("aylyth.advancements.aylyth.flesh_increased_by_two.desc"), null, AdvancementFrame.GOAL, true, false, false)
                .criterion("has_placed_vital_thurible", PlacedBlockCriterion.Conditions.block(ModBlocks.VITAL_THURIBLE))
                .build(consumer, "aylyth:aylyth/flesh_increased_by_two");
        Advancement.Builder.create()
                .parent(root)
                .display(ModItems.WRITHEWOOD_ITEMS.door, Text.translatable("aylyth.advancements.aylyth.way_of_the_wood.title"), Text.translatable("aylyth.advancements.aylyth.way_of_the_wood.desc"), null, AdvancementFrame.CHALLENGE, true, false, false)
                .criterion("has_visited_copse", TickCriterion.Conditions.createLocation(LocationPredicate.biome(ModBiomeKeys.COPSE_ID)))
                .criterion("has_visited_coniferous_copse", TickCriterion.Conditions.createLocation(LocationPredicate.biome(ModBiomeKeys.CONIFEROUS_COPSE_ID)))
                .criterion("has_visited_deepwood", TickCriterion.Conditions.createLocation(LocationPredicate.biome(ModBiomeKeys.DEEPWOOD_ID)))
                .criterion("has_visited_coniferous_deepwood", TickCriterion.Conditions.createLocation(LocationPredicate.biome(ModBiomeKeys.CONIFEROUS_DEEPWOOD_ID)))
                .criterion("has_visited_clearing", TickCriterion.Conditions.createLocation(LocationPredicate.biome(ModBiomeKeys.CLEARING_ID)))
                .criterion("has_visited_overgrown_clearing", TickCriterion.Conditions.createLocation(LocationPredicate.biome(ModBiomeKeys.OVERGROWN_CLEARING_ID)))
                .criterion("has_visited_uplands", TickCriterion.Conditions.createLocation(LocationPredicate.biome(ModBiomeKeys.UPLANDS_ID)))
                .criterion("has_visited_mire", TickCriterion.Conditions.createLocation(LocationPredicate.biome(ModBiomeKeys.MIRE_ID)))
                .criterion("has_visited_bowels", TickCriterion.Conditions.createLocation(LocationPredicate.biome(ModBiomeKeys.BOWELS_ID)))
                .build(consumer, "aylyth:aylyth/way_of_the_wood");
        Advancement.Builder.create()
                .parent(root)
                .display(ModItems.GIRASOL_SEED, Text.translatable("aylyth.advancements.aylyth.see_you_in_the_trees.title"), Text.translatable("aylyth.advancements.aylyth.see_you_in_the_trees.desc"), null, AdvancementFrame.GOAL, true, false, false)
                .criterion("has_placed_girasol_sapling", PlacedBlockCriterion.Conditions.block(ModBlocks.GIRASOL_SAPLING))
                .build(consumer, "aylyth:aylyth/see_you_in_the_trees");
    }

    private ItemStack stackWithNbt(Item item, Consumer<NbtCompound> consumer) {
        var stack = new ItemStack(item);
        var nbt = new NbtCompound();
        consumer.accept(nbt);
        stack.setNbt(nbt);
        return stack;
    }

    private ItemStack potionItem(Potion potion) {
        return PotionUtil.setPotion(new ItemStack(Items.POTION), potion);
    }

    private EffectsChangedCriterion.Conditions effectsChangedCriteria(EntityEffectPredicate predicate) {
        return EffectsChangedCriterion.Conditions.create(predicate);
    }
}
