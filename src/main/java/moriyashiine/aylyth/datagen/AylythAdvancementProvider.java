package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.criteria.ShuckingCriterion;
import moriyashiine.aylyth.common.criteria.YmpeInfestationCriterion;
import moriyashiine.aylyth.common.registry.ModDimensionKeys;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.ModPotions;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ChangedDimensionCriterion;
import net.minecraft.advancement.criterion.EffectsChangedCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.ItemCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.predicate.entity.EntityEffectPredicate;
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
                .display(potionItem(ModPotions.CIMMERIAN_POTION), Text.translatable("aylyth.advancements.aylyth.cimmerianed.title"), Text.translatable("aylyth.advancements.aylyth.cimmerianed.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_cimmerian_effect", effectsChangedCriteria(EntityEffectPredicate.create().withEffect(ModPotions.CIMMERIAN_EFFECT)))
                .build(consumer, "aylyth:aylyth/cimmerianed");
        Advancement.Builder.create()
                .parent(root)
                .display(potionItem(ModPotions.WYRDED_POTION), Text.translatable("aylyth.advancements.aylyth.wyrded.title"), Text.translatable("aylyth.advancements.aylyth.wyrded.desc"), null, AdvancementFrame.TASK, true, false, false)
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
                .criterion("has_ympe_fruit", inventoryChangedCriteria(ItemPredicate.Builder.create().items(ModItems.YMPE_FRUIT).build()))
                .build(consumer, "aylyth:aylyth/life_at_a_cost");
        Advancement.Builder.create()
                .parent(lifeAtACost)
                .display(stackWithNbt(ModItems.SHUCKED_YMPE_FRUIT, nbtCompound -> nbtCompound.putInt("StoredEntity", 1)), Text.translatable("aylyth.advancements.aylyth.daemon_ritus.title"), Text.translatable("aylyth.advancements.aylyth.daemon_ritus.desc"), null, AdvancementFrame.GOAL, true, false, false)
                .criterion("has_shucked", ShuckingCriterion.Conditions.create())
                .build(consumer, "aylyth:aylyth/daemon_ritus");
        Advancement.Builder.create()
                .parent(lifeAtACost)
                .display(ModItems.CORIC_SEED, Text.translatable("aylyth.advancements.aylyth.manufactured_for_a_purpose.title"), Text.translatable("aylyth.advancements.aylyth.manufactured_for_a_purpose.desc"), null, AdvancementFrame.GOAL, true, false, false)
                .criterion("has_coric_seed", inventoryChangedCriteria(ItemPredicate.Builder.create().items(ModItems.CORIC_SEED).build()))
                .build(consumer, "aylyth:aylyth/manufactured_for_a_purpose");
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

    private ItemCriterion.Conditions itemUsedOnBlockCriteria(LocationPredicate.Builder location, ItemPredicate.Builder item) {
        return ItemCriterion.Conditions.create(location, item);
    }

    private InventoryChangedCriterion.Conditions inventoryChangedCriteria(ItemPredicate... items) {
        return InventoryChangedCriterion.Conditions.items(items);
    }
}
