package moriyashiine.aylyth.datagen.tags;

import moriyashiine.aylyth.common.registry.ModStatusEffects;
import moriyashiine.aylyth.common.registry.tag.ModEffectTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class AylythStatusEffectTagProvider extends FabricTagProvider<StatusEffect> {
    public AylythStatusEffectTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, RegistryKeys.STATUS_EFFECT, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(ModEffectTags.BYPASSES_EFFIGY).add(ModStatusEffects.CRIMSON_CURSE,
                StatusEffects.WITHER, StatusEffects.INSTANT_DAMAGE, StatusEffects.INSTANT_HEALTH);
        getOrCreateTagBuilder(ModEffectTags.BYPASSES_MILK).add(ModStatusEffects.CRIMSON_CURSE);
    }
}
