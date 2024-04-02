package moriyashiine.aylyth.datagen.tags;

import moriyashiine.aylyth.common.registry.ModEntityTypes;
import moriyashiine.aylyth.common.registry.tag.ModEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class AylythEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {

    public AylythEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(ModEntityTypeTags.GRIPWEED_IMMUNE).add(ModEntityTypes.AYLYTHIAN, ModEntityTypes.ELDER_AYLYTHIAN);
        getOrCreateTagBuilder(ModEntityTypeTags.NON_SHUCKABLE).forceAddTag(ConventionalEntityTypeTags.BOSSES).add(EntityType.ELDER_GUARDIAN);
    }
}
