package moriyashiine.aylyth.datagen.common.tag;

import moriyashiine.aylyth.common.entity.AylythStatusEffects;
import moriyashiine.aylyth.common.data.tag.AylythStatusEffectTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public final class AylythStatusEffectTagProvider extends FabricTagProvider<StatusEffect> {
    public AylythStatusEffectTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, RegistryKeys.STATUS_EFFECT, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(AylythStatusEffectTags.EFFIGY_CANNOT_CURE).add(AylythStatusEffects.CRIMSON_CURSE.value(),
                StatusEffects.WITHER.value(), StatusEffects.INSTANT_DAMAGE.value(), StatusEffects.INSTANT_HEALTH.value());
        getOrCreateTagBuilder(AylythStatusEffectTags.MILK_CANNOT_CURE).add(AylythStatusEffects.CRIMSON_CURSE.value());
        getOrCreateTagBuilder(AylythStatusEffectTags.PREVENTS_HEALING).add(AylythStatusEffects.CRIMSON_CURSE.value());
    }
}
