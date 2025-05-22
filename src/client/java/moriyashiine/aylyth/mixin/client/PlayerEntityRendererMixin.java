package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.attachments.CuirassStages;
import moriyashiine.aylyth.common.entity.attachments.YmpeInfestation;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
    @Inject(method = "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("TAIL"))
    private void customUpdateRenderState(AbstractClientPlayerEntity player, PlayerEntityRenderState renderState, float f, CallbackInfo ci) {
        CuirassStages cuirassStages = player.getAttached(AylythEntityAttachmentTypes.CUIRASS);
        if (cuirassStages != null) {
            renderState.aylyth$setCuirassStage(cuirassStages.getStage());
        }
        YmpeInfestation infestation = player.getAttached(AylythEntityAttachmentTypes.YMPE_INFESTATION);
        if (infestation != null) {
            renderState.aylyth$setInfestationStage(infestation.getStage());
        }
    }
}
