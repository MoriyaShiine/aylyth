package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.Aylyth;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.predicate.entity.EntityEffectPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

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
                .display(ModItems.YMPE_FRUIT, Text.translatable("aylyth.advancements.aylyth.life_at_a_cost.title"), Text.translatable("aylyth.advancements.aylyth.life_at_a_cost.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_item", inventoryChangedCriteria(ItemPredicate.Builder.create().items(ModItems.YMPE_FRUIT).build()))
                .build(consumer, "aylyth:aylyth/life_at_a_cost");
        Advancement.Builder.create()
                .parent(root)
                .display(potionItem(ModPotions.CIMMERIAN_POTION), Text.translatable("aylyth.advancements.aylyth.cimmerianed.title"), Text.translatable("aylyth.advancements.aylyth.cimmerianed.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_effect", effectsChangedCriteria(EntityEffectPredicate.create().withEffect(ModPotions.CIMMERIAN_EFFECT)))
                .build(consumer, "aylyth:aylyth/cimmerianed");
        Advancement.Builder.create()
                .parent(root)
                .display(potionItem(ModPotions.WYRDED_POTION), Text.translatable("aylyth.advancements.aylyth.wyrded.title"), Text.translatable("aylyth.advancements.aylyth.wyrded.desc"), null, AdvancementFrame.TASK, true, false, false)
                .criterion("has_effect", effectsChangedCriteria(EntityEffectPredicate.create().withEffect(ModPotions.WYRDED_EFFECT)))
                .build(consumer, "aylyth:aylyth/wyrded");
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
