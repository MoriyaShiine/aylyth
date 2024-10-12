package moriyashiine.aylyth.client.integration.emi;

import dev.emi.emi.api.render.EmiRender;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.api.stack.EmiIngredient;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.data.tag.AylythItemTags;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import org.joml.Matrix4f;

import java.util.List;

public class SwappingTagRenderable implements EmiRenderable {
    private final EmiIngredient tagIngredient;
    private final List<BakedModel> models;
    private long lastTime;
    private int index = 0;

    public SwappingTagRenderable(TagKey<Item> itemTagKey) {
        this.tagIngredient = EmiIngredient.of(itemTagKey);
        this.models = createModelList(itemTagKey);
    }

    private List<BakedModel> createModelList(TagKey<Item> tag) {
        List<BakedModel> models = new ObjectArrayList<>();
        for (RegistryEntry<Item> entry : Registries.ITEM.iterateEntries(tag)) {
            models.add(MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(entry.getKey().orElseThrow().getValue(), "inventory")));
        }
        return models;
    }

    @Override
    public void render(DrawContext context, int x, int y, float delta) {
        BakedModel model = models.get(index);

        context.getMatrices().push();
        context.getMatrices().translate(x + 8, y + 8, 150);
        context.getMatrices().multiplyPositionMatrix(new Matrix4f().scaling(1.0f, -1.0f, 1.0f));
        context.getMatrices().scale(16.0f, 16.0f, 16.0f);

        model.getTransformation().getTransformation(ModelTransformationMode.GUI).apply(false, context.getMatrices());
        context.getMatrices().translate(-0.5f, -0.5f, -0.5f);

        if (!model.isSideLit()) {
            DiffuseLighting.disableGuiDepthLighting();
        }
        VertexConsumerProvider.Immediate immediate = context.getVertexConsumers();

        ((ItemRendererAccessor)MinecraftClient.getInstance().getItemRenderer())
                .invokeRenderBakedItemModel(model,
                        ItemStack.EMPTY, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, context.getMatrices(),
                        ItemRenderer.getDirectItemGlintConsumer(immediate,
                                TexturedRenderLayers.getItemEntityTranslucentCull(), true, false));
        immediate.draw();

        if (!model.isSideLit()) {
            DiffuseLighting.enableGuiDepthLighting();
        }

        context.getMatrices().pop();

        EmiRender.renderTagIcon(tagIngredient, context, x, y);

        // TODO: switch this to something that doesn't depend on the world
        long time = MinecraftClient.getInstance().world.getTime();
        if (time % 20 == 0) {
            if (lastTime != time) {
                index = (index + 1) % models.size();
            }
            lastTime = time;
        }
    }
}
