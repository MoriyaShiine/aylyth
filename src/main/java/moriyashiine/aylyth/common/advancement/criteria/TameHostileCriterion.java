package moriyashiine.aylyth.common.advancement.criteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.entity.types.mob.TameableHostileEntity;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;
import java.util.function.Predicate;

public class TameHostileCriterion extends AbstractCriterion<TameHostileCriterion.Conditions> {
    @Override
    public Codec<Conditions> getConditionsCodec() {
        return Conditions.CODEC;
    }

    public <T extends HostileEntity & TameableHostileEntity> void trigger(ServerPlayerEntity player, T entity) {
        LootContext context = EntityPredicate.createAdvancementEntityLootContext(player, entity);
        this.trigger(player, (Predicate<Conditions>)  conditions -> conditions.matches(context));
    }

    public record Conditions(Optional<LootContextPredicate> player, Optional<LootContextPredicate> targetPredicate) implements AbstractCriterion.Conditions {
        public static final Codec<TameHostileCriterion.Conditions> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(TameHostileCriterion.Conditions::player),
                        EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("target_predicate").forGetter(TameHostileCriterion.Conditions::targetPredicate)
                ).apply(instance, TameHostileCriterion.Conditions::new)
        );

        public boolean matches(LootContext context) {
            return this.targetPredicate.isEmpty() || this.targetPredicate.get().test(context);
        }
    }
}
