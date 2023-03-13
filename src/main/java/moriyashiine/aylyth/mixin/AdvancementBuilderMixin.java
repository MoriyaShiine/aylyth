package moriyashiine.aylyth.mixin;

import com.google.gson.JsonObject;
import moriyashiine.aylyth.common.advancement.CustomAdvancementDisplay;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Advancement.Builder.class)
public class AdvancementBuilderMixin {

    @Nullable @Shadow private AdvancementDisplay display;

    @ModifyArgs(method = "toJson", at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonObject;add(Ljava/lang/String;Lcom/google/gson/JsonElement;)V", ordinal = 0))
    private void aylyth_toJson(Args args) {
        if (this.display instanceof CustomAdvancementDisplay) {
            args.set(0, "aylyth:custom_display");
        }

    }

    @ModifyVariable(method = "fromJson", at = @At("STORE"))
    private static AdvancementDisplay aylyth_fromJson(AdvancementDisplay display, JsonObject value) {
        return value.has("aylyth:custom_display") ? CustomAdvancementDisplay.fromJson(JsonHelper.getObject(value, "aylyth:custom_display")) : display;
    }
}
