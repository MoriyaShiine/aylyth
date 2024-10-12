package moriyashiine.aylyth.client.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import moriyashiine.aylyth.common.entity.types.mob.ElderAylythianEntity;
import moriyashiine.aylyth.common.entity.types.mob.WreathedHindEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DaggerLootEmiRecipe implements EmiRecipe {
    private Entity renderedEntity;
    private final Identifier id;
    private final EntityType<?> entityType;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public DaggerLootEmiRecipe(Identifier id, EntityType<?> entity, float chance, EmiIngredient weapons, ItemStack output) {
        this.id = id;
        entityType = entity;
        this.input = List.of(weapons);
        if (output.getItem() == Items.PLAYER_HEAD && MinecraftClient.getInstance().player != null) {
            output.getOrCreateNbt().putString("SkullOwner", MinecraftClient.getInstance().player.getName().getString());
        }
        this.output = List.of(EmiStack.of(output));
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ModEMIPlugin.daggerCategory;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of();
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return input;
    }

    @Override
    public int getDisplayWidth() {
        return 76 + 18;
    }

    @Override
    public int getDisplayHeight() {
        return 18 + 18 + 2;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        if (renderedEntity == null) {
            renderedEntity = entityType == EntityType.PLAYER ? MinecraftClient.getInstance().player : entityType.create(MinecraftClient.getInstance().world);
        }

        widgets.addDrawable(18,18,20,20, (context, mouseX, mouseY, delta) -> {
            if (renderedEntity instanceof LivingEntity livingEntity) {
                boolean needsResize = livingEntity instanceof WreathedHindEntity || livingEntity instanceof ElderAylythianEntity;
                int y = needsResize ? 16 : 18;
                int size = needsResize ? 10 : 18;
                InventoryScreen.drawEntity(context, 9, y, size, 27 - mouseX, y - 9 - mouseY, livingEntity);
            }
        });

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 40, 10);

        widgets.addSlot(output.get(0), 58 + 9, 10).recipeContext(this);
    }
}
