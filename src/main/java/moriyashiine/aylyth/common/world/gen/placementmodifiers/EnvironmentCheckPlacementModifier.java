package moriyashiine.aylyth.common.world.gen.placementmodifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.world.gen.AylythPlacementModifiers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.AbstractConditionalPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public class EnvironmentCheckPlacementModifier extends AbstractConditionalPlacementModifier {
    public static final Codec<EnvironmentCheckPlacementModifier> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Direction.CODEC.fieldOf("direction_to_scan").forGetter(modifier -> modifier.checkDirection),
                    BlockPredicate.BASE_CODEC.fieldOf("target_predicate").forGetter(modifier -> modifier.targetPredicate),
                    IntProvider.POSITIVE_CODEC.fieldOf("max_steps").forGetter(modifier -> modifier.maxSteps)
            ).apply(instance, EnvironmentCheckPlacementModifier::new)
    );

    private final Direction checkDirection;
    private final BlockPredicate targetPredicate;
    private final IntProvider maxSteps;

    public EnvironmentCheckPlacementModifier(Direction checkDirection, BlockPredicate targetPredicate, IntProvider maxSteps) {
        this.checkDirection = checkDirection;
        this.targetPredicate = targetPredicate;
        this.maxSteps = maxSteps;
    }

    @Override
    protected boolean shouldPlace(FeaturePlacementContext context, Random random, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        int steps = maxSteps.get(random);

        while (!targetPredicate.test(context.getWorld(), mutable) && steps > 0) {
            mutable.move(checkDirection);
            steps--;
        }

        if (!targetPredicate.test(context.getWorld(), mutable)) {
            return false;
        }

        return steps > 0;
    }

    @Override
    public PlacementModifierType<?> getType() {
        return AylythPlacementModifiers.ENVIRONMENT_CHECK;
    }
}
