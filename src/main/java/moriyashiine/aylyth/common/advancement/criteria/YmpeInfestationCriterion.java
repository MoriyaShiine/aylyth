package moriyashiine.aylyth.common.advancement.criteria;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.AylythEntityComponents;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.IntProvider;

public class YmpeInfestationCriterion extends AbstractCriterion<YmpeInfestationCriterion.Conditions> {
    static final Identifier ID = Aylyth.id("ympe_infestation");

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        IntProvider stage = IntProvider.VALUE_CODEC.decode(JsonOps.INSTANCE, obj.get("stage")).result().get().getFirst();
        return new Conditions(playerPredicate, stage);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, conditions -> conditions.matches(player));
    }

    public static class Conditions extends AbstractCriterionConditions {

        private final IntProvider stage;

        public Conditions(LootContextPredicate entity, IntProvider stage) {
            super(ID, entity);
            this.stage = stage;
        }

        public static Conditions create(IntProvider stage) {
            return new Conditions(LootContextPredicate.EMPTY, stage);
        }

        public boolean matches(ServerPlayerEntity player) {
            return AylythEntityComponents.YMPE_INFESTATION.maybeGet(player).map(component -> stage.getMin() <= component.getStage() && stage.getMax() >= component.getStage()).orElse(false);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject json = super.toJson(predicateSerializer);
            DataResult<JsonElement> result = IntProvider.VALUE_CODEC.encodeStart(JsonOps.INSTANCE, stage);
            json.add("stage", result.result().get());
            return json;
        }
    }
}
