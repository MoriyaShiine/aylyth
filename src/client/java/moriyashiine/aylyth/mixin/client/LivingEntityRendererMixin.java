package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.attachments.YmpeThorns;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    @Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
    private void customUpdateRenderState(T livingEntity, S livingEntityRenderState, float f, CallbackInfo ci) {
        YmpeThorns ympeThorns = livingEntity.getAttached(AylythEntityAttachmentTypes.YMPE_THORNS);
        if (ympeThorns != null) {
            livingEntityRenderState.aylyth$setThornsStage(ympeThorns.getStage());
        }
    }
}
