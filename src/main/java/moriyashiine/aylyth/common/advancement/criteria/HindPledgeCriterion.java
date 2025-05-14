package moriyashiine.aylyth.common.advancement.criteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.entity.types.mob.WreathedHindEntity;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

public class HindPledgeCriterion extends AbstractCriterion<HindPledgeCriterion.Conditions> {
    @Override
    public Codec<Conditions> getConditionsCodec() {
        return HindPledgeCriterion.Conditions.CODEC;
    }

    public void trigger(ServerPlayerEntity player, WreathedHindEntity wreathedHind) {
        LootContext context = EntityPredicate.createAdvancementEntityLootContext(player, wreathedHind);
        this.trigger(player, conditions -> conditions.matches(context));
    }

    public record Conditions(Optional<LootContextPredicate> player, Optional<LootContextPredicate> targetPredicate) implements AbstractCriterion.Conditions {
        public static final Codec<HindPledgeCriterion.Conditions> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(HindPledgeCriterion.Conditions::player),
                                EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("target_predicate").forGetter(HindPledgeCriterion.Conditions::targetPredicate)
                        )
                        .apply(instance, HindPledgeCriterion.Conditions::new)
        );

        public static Conditions create() {
            return new Conditions(Optional.empty(), Optional.empty());
        }

        public static Conditions withPlayer(LootContextPredicate player) {
            return new Conditions(Optional.of(player), Optional.empty());
        }

        public static Conditions withTargetHind(LootContextPredicate wreathedHind) {
            return new Conditions(Optional.empty(), Optional.of(wreathedHind));
        }

        public static Conditions create(LootContextPredicate player, LootContextPredicate target) {
            return new Conditions(Optional.of(player), Optional.of(target));
        }

        public boolean matches(LootContext context) {
            return this.targetPredicate.isEmpty() || this.targetPredicate.get().test(context);
        }
    }
}
