package moriyashiine.aylyth.datagen.common.tag;

import moriyashiine.aylyth.common.registry.ModPotions;
import moriyashiine.aylyth.common.data.tag.ModPotionTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.potion.Potion;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public final class AylythPotionTagProvider extends FabricTagProvider<Potion> {
    public AylythPotionTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, RegistryKeys.POTION, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(ModPotionTags.BLIGHT).add(ModPotions.BLIGHT_POTION, ModPotions.LONG_BLIGHT_POTION, ModPotions.STRONG_BLIGHT_POTION);
    }
}
