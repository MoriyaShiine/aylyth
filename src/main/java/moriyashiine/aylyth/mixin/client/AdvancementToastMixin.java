package moriyashiine.aylyth.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import moriyashiine.aylyth.common.advancement.CustomAdvancementDisplay;
import net.minecraft.advancement.Advancement;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.toast.AdvancementToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementToast.class)
public class AdvancementToastMixin {

    @Shadow @Final private Advancement advancement;

    @Inject(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderInGui(Lnet/minecraft/item/ItemStack;II)V"))
    private void aylyth_draw(MatrixStack matrices, ToastManager manager, long startTime, CallbackInfoReturnable<Toast.Visibility> cir) {
        if (this.advancement.getDisplay() instanceof CustomAdvancementDisplay customAdvancementDisplay) {
            RenderSystem.setShaderTexture(0, customAdvancementDisplay.getTexture());
            DrawableHelper.drawTexture(matrices, 8, 8, 0, 0, 16, 16, 16, 16);
        }
    }
}
