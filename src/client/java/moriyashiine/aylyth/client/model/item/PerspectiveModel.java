package moriyashiine.aylyth.client.model.item;

import moriyashiine.aylyth.client.util.RenderUtils;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

import java.util.Map;
import java.util.function.Supplier;

public class PerspectiveModel extends ForwardingBakedModel {
    private final Map<ModelTransformationMode, BakedModel> models;
    public PerspectiveModel(Map<ModelTransformationMode, BakedModel> models) {
        this.wrapped = models.get(ModelTransformationMode.NONE);
        this.models = models;
    }
    @Override
    public boolean isVanillaAdapter() {
        return false;
    }
    @Override
    public ModelTransformation getTransformation() {
        return ModelTransformation.NONE;
    }
    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        ModelTransformationMode transformMode = context.itemTransformationMode();
        BakedModel model = models.get(transformMode);
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(0.5f, 0.5f, 0.5f);
        model.getTransformation().getTransformation(transformMode).apply(transformMode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND || transformMode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND, matrixStack);
        matrixStack.translate(-0.5f, -0.5f, -0.5f);
        RenderUtils.copyOver(context, matrixStack);
        model.emitItemQuads(stack, randomSupplier, context);
        context.popTransform();
    }
}