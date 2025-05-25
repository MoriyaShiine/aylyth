package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.client.render.AylythRenderLayers;
import moriyashiine.aylyth.client.render.item.AylythItemRenderStateGlint;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @WrapOperation(method = "renderItem(Lnet/minecraft/item/ModelTransformationMode;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II[ILnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/render/item/ItemRenderState$Glint;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lnet/minecraft/client/render/VertexConsumer;"))
    private static VertexConsumer renderItemWithTint(VertexConsumerProvider vertexConsumers, RenderLayer layer, boolean solid, boolean glint, Operation<VertexConsumer> original, @Local(argsOnly = true) ItemRenderState.Glint stateGlint) {
        return stateGlint == AylythItemRenderStateGlint.TINT ? VertexConsumers.union(vertexConsumers.getBuffer(AylythRenderLayers.TINT), vertexConsumers.getBuffer(layer)) : original.call(vertexConsumers, layer, solid, glint);
    }
}
