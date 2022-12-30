package moriyashiine.aylyth.common.criteria;

import com.google.gson.JsonObject;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class ShuckingCriterion extends AbstractCriterion<ShuckingCriterion.Conditions> {
    static final Identifier ID = AylythUtil.id("shucking");

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        var targetPredicate = EntityPredicate.Extended.getInJson(obj, "target_predicate", predicateDeserializer);
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

        private final EntityPredicate.Extended targetPredicate;

        public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended target) {
            super(ID, player);
            this.targetPredicate = target;
        }

        public static Conditions create() {
            return new Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY);
        }

        public static Conditions create(EntityPredicate.Extended player) {
            return new Conditions(player, EntityPredicate.Extended.EMPTY);
        }

        public static Conditions create(EntityPredicate.Extended player, EntityPredicate.Extended target) {
            return new Conditions(player, target);
        }

        public boolean matches(ServerPlayerEntity player, LivingEntity target) {
            var playerContext = new LootContext.Builder((ServerWorld) player.world).parameter(LootContextParameters.KILLER_ENTITY, player).build(LootContextTypes.ENTITY);
            var targetContext = new LootContext.Builder((ServerWorld) player.world).parameter(LootContextParameters.THIS_ENTITY, target).build(LootContextTypes.ENTITY);
            return getPlayerPredicate().test(playerContext) && this.targetPredicate.test(targetContext);
        }
    }
}
