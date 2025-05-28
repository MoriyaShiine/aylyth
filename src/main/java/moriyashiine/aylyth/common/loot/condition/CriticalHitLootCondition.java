package moriyashiine.aylyth.common.loot.condition;

import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.loot.AylythLootConditionTypes;
import moriyashiine.aylyth.common.loot.AylythLootContextParameters;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;

public record CriticalHitLootCondition() implements LootCondition {
    public static final CriticalHitLootCondition INSTANCE = new CriticalHitLootCondition();
    public static final MapCodec<CriticalHitLootCondition> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public LootConditionType getType() {
        return AylythLootConditionTypes.CRITICAL_HIT;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.hasParameter(AylythLootContextParameters.CRITICAL);
    }

    public static Builder builder() {
        return Builder.INSTANCE;
    }

    public static class Builder implements LootCondition.Builder {
        public static final Builder INSTANCE = new Builder();
        @Override
        public LootCondition build() {
            return CriticalHitLootCondition.INSTANCE;
        }
    }
}
