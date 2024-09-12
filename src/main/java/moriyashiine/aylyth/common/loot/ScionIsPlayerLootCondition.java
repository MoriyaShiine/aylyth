package moriyashiine.aylyth.common.loot;

import com.google.common.base.Suppliers;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import moriyashiine.aylyth.common.entity.type.mob.ScionEntity;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonSerializer;

import java.util.function.Supplier;

public class ScionIsPlayerLootCondition implements LootCondition {
    public static final Supplier<ScionIsPlayerLootCondition> INSTANCE = Suppliers.memoize(ScionIsPlayerLootCondition::new);
    public static final Codec<ScionIsPlayerLootCondition> CODEC = Codec.unit(INSTANCE);

    public static ScionIsPlayerLootCondition getInstance() {
        return INSTANCE.get();
    }

    @Override
    public LootConditionType getType() {
        return AylythLootConditionTypes.SCION_IS_PLAYER;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(LootContextParameters.THIS_ENTITY);
        if (entity instanceof ScionEntity scion) {
            return scion.getStoredPlayerUUID() != null;
        }
        return false;
    }

    public static class Serializer implements JsonSerializer<ScionIsPlayerLootCondition> {

        @Override
        public void toJson(JsonObject json, ScionIsPlayerLootCondition object, JsonSerializationContext context) {
            CODEC.encode(object, JsonOps.INSTANCE, json);
        }

        @Override
        public ScionIsPlayerLootCondition fromJson(JsonObject json, JsonDeserializationContext context) {
            return INSTANCE.get();
        }
    }
}
