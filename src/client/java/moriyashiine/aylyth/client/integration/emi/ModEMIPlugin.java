package moriyashiine.aylyth.client.integration.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
import moriyashiine.aylyth.common.loot.display.LootDisplay;
import moriyashiine.aylyth.common.loot.display.DaggerLootDisplay;
import moriyashiine.aylyth.common.registry.AylythRegistryKeys;
import moriyashiine.aylyth.mixin.client.ItemRendererAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.data.client.ModelIds;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;

import java.util.List;
import java.util.stream.StreamSupport;

public final class ModEMIPlugin implements EmiPlugin {
    static EmiRecipeCategory daggerCategory;

    @Override
    public void register(EmiRegistry emiRegistry) {
        DynamicRegistryManager registryManager = MinecraftClient.getInstance().world.getRegistryManager();
        for (RegistryEntry<LootDisplay> display : registryManager.get(AylythRegistryKeys.LOOT_TABLE_DISPLAY).getIndexedEntries()) {
            if (display.value() instanceof DaggerLootDisplay daggerLootDisplay) {
                Identifier id = display.getKey().orElseThrow().getValue();
                EmiIngredient weapons = daggerLootDisplay.weapons().getStorage().map(EmiIngredient::of, registryEntries -> EmiIngredient.of(registryEntries.stream().map(itemRegistryEntry -> EmiStack.of(itemRegistryEntry.value())).toList()));
                emiRegistry.addRecipe(new DaggerLootEmiRecipe(id, daggerLootDisplay.entity(), daggerLootDisplay.chance(), weapons, daggerLootDisplay.outputs()));
            }
        }
        daggerCategory = new EmiRecipeCategory(Aylyth.id("dagger_drops"), new SwappingTagRenderable(AylythItemTags.FLESH_HARVESTERS));
        emiRegistry.addCategory(daggerCategory);
    }
}
