package moriyashiine.aylyth.common.advancement.criteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.advancement.AylythCriteria;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

public class ShuckingCriterion extends AbstractCriterion<ShuckingCriterion.Conditions> {
    @Override
    public Codec<Conditions> getConditionsCodec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayerEntity player, LivingEntity target) {
        LootContext context = new LootContext.Builder(
                new LootWorldContext.Builder(player.getServerWorld())
                        .add(LootContextParameters.ATTACKING_ENTITY, player)
                        .add(LootContextParameters.THIS_ENTITY, target)
                        .add(LootContextParameters.DAMAGE_SOURCE, player.getDamageSources().playerAttack(player))
                        .add(LootContextParameters.ORIGIN, player.getPos())
                        .build(LootContextTypes.ENTITY)
        ).build(Optional.empty());
        this.trigger(player, conditions -> conditions.matches(context));
    }

    public record Conditions(Optional<LootContextPredicate> player, Optional<LootContextPredicate> targetPredicate) implements AbstractCriterion.Conditions {
        public static final Codec<ShuckingCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(ShuckingCriterion.Conditions::player),
                        EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("target_predicate").forGetter(ShuckingCriterion.Conditions::targetPredicate)
                ).apply(instance, ShuckingCriterion.Conditions::new)
        );

        public static AdvancementCriterion<Conditions> create() {
            return AylythCriteria.SHUCKING.create(new Conditions(Optional.empty(), Optional.empty()));
        }

        public static AdvancementCriterion<Conditions> create(LootContextPredicate player) {
            return AylythCriteria.SHUCKING.create(new Conditions(Optional.of(player), Optional.empty()));
        }

        public static AdvancementCriterion<Conditions> create(LootContextPredicate player, LootContextPredicate target) {
            return AylythCriteria.SHUCKING.create(new Conditions(Optional.of(player), Optional.of(target)));
        }

        public boolean matches(LootContext entity) {
            return this.targetPredicate.isEmpty() || this.targetPredicate.get().test(entity);
        }
    }
}
