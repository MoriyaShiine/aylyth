package moriyashiine.aylyth.datagen.common.tag;

import moriyashiine.aylyth.common.data.tag.AylythPotionTags;
import moriyashiine.aylyth.common.item.potion.AylythPotions;
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
        getOrCreateTagBuilder(AylythPotionTags.BLIGHT).add(AylythPotions.BLIGHT, AylythPotions.LONG_BLIGHT, AylythPotions.STRONG_BLIGHT);
    }
}
