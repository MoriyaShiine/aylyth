package moriyashiine.aylyth.common.advancement.criteria;

import com.google.gson.JsonObject;
import moriyashiine.aylyth.common.entity.types.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class HindPledgeCriterion extends AbstractCriterion<HindPledgeCriterion.Conditions> {
    static final Identifier ID = AylythUtil.id("hind_pledge");

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        var targetPredicate = EntityPredicate.contextPredicateFromJson(obj, "target_predicate", predicateDeserializer);
        return new Conditions(playerPredicate, targetPredicate);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, WreathedHindEntity wreathedHind) {
        this.trigger(player, conditions -> conditions.matches(player, wreathedHind));
    }

    public static class Conditions extends AbstractCriterionConditions {

        private final LootContextPredicate targetPredicate;

        public Conditions(LootContextPredicate player, LootContextPredicate target) {
            super(ID, player);
            this.targetPredicate = target;
        }

        public static Conditions create() {
            return new Conditions(LootContextPredicate.EMPTY, LootContextPredicate.EMPTY);
        }

        public static Conditions withPlayer(LootContextPredicate player) {
            return new Conditions(player, LootContextPredicate.EMPTY);
        }

        public static Conditions withTargetHind(LootContextPredicate wreathedHind) {
            return new Conditions(LootContextPredicate.EMPTY, wreathedHind);
        }

        public static Conditions create(LootContextPredicate player, LootContextPredicate target) {
            return new Conditions(player, target);
        }

        public boolean matches(ServerPlayerEntity player, WreathedHindEntity target) {
            LootContext context = EntityPredicate.createAdvancementEntityLootContext(player, target);
            return getPlayerPredicate().test(context) && this.targetPredicate.test(context);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject json = super.toJson(predicateSerializer);
            json.add("target_predicate", targetPredicate.toJson(predicateSerializer));
            return json;
        }
    }
}
