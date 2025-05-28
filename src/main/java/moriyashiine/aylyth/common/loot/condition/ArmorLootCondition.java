package moriyashiine.aylyth.common.loot.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.loot.AylythLootConditionTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.NumberRange;
import net.minecraft.util.context.ContextParameter;

import java.util.Optional;
import java.util.Set;

public record ArmorLootCondition(NumberRange.IntRange armorAmount, LootContext.EntityTarget target) implements LootCondition {
    public static final MapCodec<ArmorLootCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    NumberRange.IntRange.CODEC.fieldOf("range").forGetter(ArmorLootCondition::armorAmount),
                    LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(ArmorLootCondition::target)
            ).apply(instance, ArmorLootCondition::new)
    );

    @Override
    public LootConditionType getType() {
        return AylythLootConditionTypes.ARMOR;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(target.getParameter());
        if (!(entity instanceof LivingEntity livingEntity)) {
            return false;
        }
        return armorAmount.test(livingEntity.getArmor());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements LootCondition.Builder {
        private NumberRange.IntRange armorAmount = NumberRange.IntRange.ANY;
        private LootContext.EntityTarget target = LootContext.EntityTarget.THIS;

        public Builder range(NumberRange.IntRange range) {
            this.armorAmount = range;
            return this;
        }

        public Builder exactly(int value) {
            return range(NumberRange.IntRange.exactly(value));
        }

        public Builder between(int min, int max) {
            return range(NumberRange.IntRange.between(min, max));
        }

        public Builder atLeast(int value) {
            return range(NumberRange.IntRange.atLeast(value));
        }

        public Builder atMost(int value) {
            return range(NumberRange.IntRange.atMost(value));
        }

        public Builder target(LootContext.EntityTarget target) {
            this.target = target;
            return this;
        }

        @Override
        public LootCondition build() {
            return new ArmorLootCondition(armorAmount, target);
        }
    }
}
