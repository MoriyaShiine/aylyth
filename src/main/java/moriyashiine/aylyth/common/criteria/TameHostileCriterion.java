package moriyashiine.aylyth.common.criteria;

import com.google.gson.JsonObject;
import moriyashiine.aylyth.common.entity.mob.TameableHostileEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public class TameHostileCriterion extends AbstractCriterion<TameHostileCriterion.Conditions> {
    static final Identifier ID = AylythUtil.id("tame_hostile");

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        var targetPredicate = EntityPredicate.Extended.getInJson(obj, "target_predicate", predicateDeserializer);
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

        private final EntityPredicate.Extended targetPredicate;

        public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended target) {
            super(ID, player);
            this.targetPredicate = target;
        }

        public <T extends HostileEntity & TameableHostileEntity> boolean matches(ServerPlayerEntity player, T target) {
            var context = EntityPredicate.createAdvancementEntityLootContext(player, target);
            return getPlayerPredicate().test(context) && this.targetPredicate.test(context);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            var json = super.toJson(predicateSerializer);
            json.add("target_predicate", targetPredicate.toJson(predicateSerializer));
            return json;
        }
    }
}
