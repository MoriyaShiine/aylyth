package moriyashiine.aylyth.common.advancement.criteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.advancement.AylythCriteria;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.attachments.YmpeInfestation;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.intprovider.IntProvider;

import java.util.Optional;

public class YmpeInfestationCriterion extends AbstractCriterion<YmpeInfestationCriterion.Conditions> {
    @Override
    public Codec<Conditions> getConditionsCodec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, conditions -> conditions.matches(player));
    }

    public record Conditions(Optional<LootContextPredicate> player, Optional<IntProvider> stage) implements AbstractCriterion.Conditions {
        public static final Codec<YmpeInfestationCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(YmpeInfestationCriterion.Conditions::player),
                        IntProvider.VALUE_CODEC.optionalFieldOf("stage").forGetter(YmpeInfestationCriterion.Conditions::stage)
                ).apply(instance, YmpeInfestationCriterion.Conditions::new)
        );

        public static AdvancementCriterion<Conditions> create(LootContextPredicate player, IntProvider stage) {
            return AylythCriteria.YMPE_INFESTATION.create(new Conditions(Optional.of(player), Optional.of(stage)));
        }

        public static AdvancementCriterion<Conditions> create(LootContextPredicate player) {
            return AylythCriteria.YMPE_INFESTATION.create(new Conditions(Optional.of(player), Optional.empty()));
        }

        public static AdvancementCriterion<Conditions> create(IntProvider stage) {
            return AylythCriteria.YMPE_INFESTATION.create(new Conditions(Optional.empty(), Optional.of(stage)));
        }

        public boolean matches(ServerPlayerEntity player) {
            YmpeInfestation infestation = player.getAttached(AylythEntityAttachmentTypes.YMPE_INFESTATION);
            if (infestation == null) {
                return false;
            }
            return stage.isEmpty() || (stage.get().getMin() <= infestation.getStage() && stage.get().getMax() >= infestation.getStage());
        }
    }
}
