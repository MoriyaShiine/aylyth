package moriyashiine.aylyth.datagen.common.util.recipe;

import com.google.gson.JsonObject;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.AylythRecipeTypes;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class YmpeDaggerRecipeJsonBuilder {
    public final EntityType<?> entityType;
    private final ItemStack output;
    public final float chance;
    public final int min;
    public final int max;

    public YmpeDaggerRecipeJsonBuilder(EntityType<?> entityType, ItemStack output, float chance, int min, int max) {
        this.entityType = entityType;
        this.output = output;
        this.chance = chance;
        this.min = min;
        this.max = max;
    }

    public static YmpeDaggerRecipeJsonBuilder create(EntityType<?> entityType, Item output, float chance, int min, int max) {
        return new YmpeDaggerRecipeJsonBuilder(entityType, new ItemStack(output), chance, min, max);
    }

    public static YmpeDaggerRecipeJsonBuilder create(EntityType<?> entityType, ItemStack output, float chance, int min, int max) {
        return new YmpeDaggerRecipeJsonBuilder(entityType, output, chance, min, max);
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter) {
        this.offerTo(exporter, new Identifier(Aylyth.MOD_ID, "ympe_dagger_drops/%s_from_%s".formatted(Registries.ITEM.getId(output.getItem()).getPath(), Registries.ENTITY_TYPE.getId(entityType).getPath())));
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        exporter.accept(new RecipeJsonProvider() {
            @Override
            public void serialize(JsonObject json) {
                json.addProperty("entity_type", Registries.ENTITY_TYPE.getId(entityType).toString());
                JsonObject obj = new JsonObject();
                obj.addProperty("item", Registries.ITEM.getId(output.getItem()).toString());
                if (output.getCount() > 1) {
                    obj.addProperty("count", output.getCount());
                }
                json.add("result", obj);
                json.addProperty("chance", chance);
                json.addProperty("min", min);
                json.addProperty("max", max);
            }

            @Override
            public Identifier getRecipeId() {
                return recipeId;
            }

            @Override
            public RecipeSerializer<?> getSerializer() {
                return AylythRecipeTypes.YMPE_DAGGER_DROP_SERIALIZER;
            }

            @Nullable
            @Override
            public JsonObject toAdvancementJson() {
                return null;
            }

            @Nullable
            @Override
            public Identifier getAdvancementId() {
                return null;
            }
        });
    }
}
