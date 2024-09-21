package moriyashiine.aylyth.common.advancement.criteria;

import com.google.gson.JsonObject;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.types.mob.TameableHostileEntity;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public class TameHostileCriterion extends AbstractCriterion<TameHostileCriterion.Conditions> {
    static final Identifier ID = Aylyth.id("tame_hostile");

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        LootContextPredicate targetPredicate = EntityPredicate.contextPredicateFromJson(obj, "target_predicate", predicateDeserializer);
        return new Conditions(playerPredicate, targetPredicate);
    }

    @Override
    public Identifier getId() {
        return ID;
    }
    
    public <T extends HostileEntity & TameableHostileEntity> void trigger(ServerPlayerEntity player, T entity) {
        Predicate<Conditions> predicate = conditions -> conditions.matches(player, entity);
        this.trigger(player, predicate);
    }

    public static class Conditions extends AbstractCriterionConditions {

        private final LootContextPredicate targetPredicate;

        public Conditions(LootContextPredicate player, LootContextPredicate target) {
            super(ID, player);
            this.targetPredicate = target;
        }

        public <T extends HostileEntity & TameableHostileEntity> boolean matches(ServerPlayerEntity player, T target) {
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
