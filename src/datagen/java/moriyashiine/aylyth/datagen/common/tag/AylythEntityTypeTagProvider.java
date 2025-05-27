package moriyashiine.aylyth.datagen.common.tag;

import moriyashiine.aylyth.common.data.tag.AylythEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

import static moriyashiine.aylyth.common.entity.AylythEntityTypes.*;

public final class AylythEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
    public AylythEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, registries);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        getOrCreateTagBuilder(AylythEntityTypeTags.GRIPWEED_IMMUNE)
                .add(AYLYTHIAN, ELDER_AYLYTHIAN);
        getOrCreateTagBuilder(AylythEntityTypeTags.NON_SHUCKABLE)
                .forceAddTags(ConventionalEntityTypeTags.BOSSES, ConventionalEntityTypeTags.CAPTURING_NOT_SUPPORTED)
                .add(EntityType.ELDER_GUARDIAN);

        getOrCreateTagBuilder(ConventionalEntityTypeTags.BOATS)
                .add(YMPE_BOAT, YMPE_CHEST_BOAT, POMEGRANATE_BOAT, POMEGRANATE_CHEST_BOAT, WRITHEWOOD_BOAT, WRITHEWOOD_CHEST_BOAT);

        getOrCreateTagBuilder(EntityTypeTags.BOAT)
                .add(YMPE_BOAT, YMPE_CHEST_BOAT, POMEGRANATE_BOAT, POMEGRANATE_CHEST_BOAT, WRITHEWOOD_BOAT, WRITHEWOOD_CHEST_BOAT);
        getOrCreateTagBuilder(EntityTypeTags.UNDEAD)
                .add(AYLYTHIAN, BONEFLY, ELDER_AYLYTHIAN, FAUNAYLYTHIAN, SCION, YMPEMOULD);
    }
}
