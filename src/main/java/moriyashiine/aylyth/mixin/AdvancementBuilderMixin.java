package moriyashiine.aylyth.mixin;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import moriyashiine.aylyth.common.advancement.CustomAdvancementDisplay;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Advancement.Builder.class)
public class AdvancementBuilderMixin {

    @Nullable @Shadow private AdvancementDisplay display;

    @ModifyArgs(method = "toJson", at = @At(remap = false, value = "INVOKE", target = "Lcom/google/gson/JsonObject;add(Ljava/lang/String;Lcom/google/gson/JsonElement;)V", ordinal = 0))
    private void aylyth_toJson(Args args) {
        if (this.display instanceof CustomAdvancementDisplay) {
            args.set(0, "aylyth:custom_display");
        }

    }

    @Inject(method = "fromJson", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/AdvancementCriterion;criteriaFromJson(Lcom/google/gson/JsonObject;Lnet/minecraft/predicate/entity/AdvancementEntityPredicateDeserializer;)Ljava/util/Map;"))
    private static void aylyth_fromJson(JsonObject obj, AdvancementEntityPredicateDeserializer predicateDeserializer, CallbackInfoReturnable<Advancement.Builder> cir, @Local LocalRef<AdvancementDisplay> displayRef) {
        if (obj.has("aylyth:custom_display")) {
            displayRef.set(CustomAdvancementDisplay.fromJson(JsonHelper.getObject(obj, "aylyth:custom_display")));
        }
    }
}
