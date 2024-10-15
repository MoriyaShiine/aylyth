package moriyashiine.aylyth.common.world.gen.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.world.gen.AylythTreeDecoratorTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class PlaceAroundTreeDecorator extends TreeDecorator {
    public static final Codec<PlaceAroundTreeDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PlacedFeature.LIST_CODEC.fieldOf("placed_features").forGetter(placeAroundTreeDecorator -> placeAroundTreeDecorator.features)
    ).apply(instance, PlaceAroundTreeDecorator::new));

    private final RegistryEntryList<PlacedFeature> features;

    public PlaceAroundTreeDecorator(RegistryEntryList<PlacedFeature> placedFeatures) {
        this.features = placedFeatures;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return AylythTreeDecoratorTypes.PLACE_AROUND;
    }

    @Override
    public void generate(Generator generator) {
        StructureWorldAccess worldAccess = (StructureWorldAccess) generator.getWorld();
        BlockPos rootPos = generator.getLogPositions().get(0);

        for (RegistryEntry<PlacedFeature> feature : features) {
            feature.value().generateUnregistered(worldAccess, worldAccess.toServerWorld().getChunkManager().getChunkGenerator(), generator.getRandom(), rootPos);
        }
    }
}
