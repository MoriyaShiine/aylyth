package moriyashiine.aylyth.common.criteria;

import com.google.gson.JsonObject;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ShuckingCriterion extends AbstractCriterion<ShuckingCriterion.Conditions> {
    static final Identifier ID = AylythUtil.id("shucking");

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        LootContextPredicate targetPredicate = EntityPredicate.contextPredicateFromJson(obj, "target_predicate", predicateDeserializer);
        return new Conditions(playerPredicate, targetPredicate);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, LivingEntity target) {
        this.trigger(player, conditions -> conditions.matches(player, target));
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

        public static Conditions create(LootContextPredicate player) {
            return new Conditions(player, LootContextPredicate.EMPTY);
        }

        public static Conditions create(LootContextPredicate player, LootContextPredicate target) {
            return new Conditions(player, target);
        }

        public boolean matches(ServerPlayerEntity player, LivingEntity target) {
            LootContext context = new LootContext.Builder(new LootContextParameterSet.Builder(player.getServerWorld()).add(LootContextParameters.KILLER_ENTITY, player).add(LootContextParameters.THIS_ENTITY, target).add(LootContextParameters.DAMAGE_SOURCE, player.getDamageSources().playerAttack(player)).add(LootContextParameters.ORIGIN, player.getPos()).build(LootContextTypes.ENTITY)).build(null);
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
