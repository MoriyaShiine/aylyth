package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.client.render.item.AylythItemRenderStateGlint;
import moriyashiine.aylyth.common.item.AylythDataComponentTypes;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.model.BasicItemModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BasicItemModel.class)
public class BasicItemModelMixin {
    @Inject(method = "update", at = @At("TAIL"))
    private void updateWithGlint(ItemRenderState state, ItemStack stack, ItemModelManager resolver, ModelTransformationMode transformationMode, ClientWorld world, LivingEntity user, int seed, CallbackInfo ci, @Local ItemRenderState.LayerRenderState layerState) {
        if (!stack.hasGlint() && stack.contains(AylythDataComponentTypes.YELLOW_TINTED)) {
            layerState.setGlint(AylythItemRenderStateGlint.TINT);
        }
    }
}
