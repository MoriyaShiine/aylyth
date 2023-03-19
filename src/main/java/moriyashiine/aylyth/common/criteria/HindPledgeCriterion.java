package moriyashiine.aylyth.common.criteria;

import com.google.gson.JsonObject;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class HindPledgeCriterion extends AbstractCriterion<HindPledgeCriterion.Conditions> {
    static final Identifier ID = AylythUtil.id("hind_pledge");

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        EntityPredicate.Extended targetPredicate = EntityPredicate.Extended.getInJson(obj, "target_predicate", predicateDeserializer);
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

        private final EntityPredicate.Extended targetPredicate;

        public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended target) {
            super(ID, player);
            this.targetPredicate = target;
        }

        public static Conditions create() {
            return new Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY);
        }

        public static Conditions withPlayer(EntityPredicate.Extended player) {
            return new Conditions(player, EntityPredicate.Extended.EMPTY);
        }

        public static Conditions withTargetHind(EntityPredicate.Extended wreathedHind) {
            return new Conditions(EntityPredicate.Extended.EMPTY, wreathedHind);
        }

        public static Conditions create(EntityPredicate.Extended player, EntityPredicate.Extended target) {
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
