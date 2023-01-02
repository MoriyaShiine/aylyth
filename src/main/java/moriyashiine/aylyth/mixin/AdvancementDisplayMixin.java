package moriyashiine.aylyth.mixin;

import com.google.gson.JsonObject;
import moriyashiine.aylyth.common.advancement.CustomAdvancementDisplay;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementDisplay.class)
public class AdvancementDisplayMixin {

    @Inject(method = "fromJson", at = @At("HEAD"), cancellable = true)
    private static void aylyth_fromJson(JsonObject obj, CallbackInfoReturnable<AdvancementDisplay> cir) {
        if (obj.has("aylyth:custom_display")) {
            cir.setReturnValue(CustomAdvancementDisplay.fromJson(JsonHelper.getObject(obj, "aylyth:custom_display")));
        }
    }

    @Inject(method = "fromPacket", at = @At("HEAD"), cancellable = true)
    private static void aylyth_fromPacket(PacketByteBuf buf, CallbackInfoReturnable<AdvancementDisplay> cir) {
        if (buf.readVarInt() == -1) {
            cir.setReturnValue(CustomAdvancementDisplay.fromPacket(buf));
        }
    }

    @Inject(method = "toPacket", at = @At("HEAD"))
    private void aylyth_toPacket(PacketByteBuf buf, CallbackInfo ci) {
        buf.writeVarInt(1);
    }
}
