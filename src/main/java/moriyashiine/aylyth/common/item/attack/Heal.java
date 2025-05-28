package moriyashiine.aylyth.common.item.attack;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.item.AttackEffectTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.provider.number.LootNumberProvider;
import net.minecraft.loot.provider.number.LootNumberProviderTypes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record Heal(LootNumberProvider healAmount, Operation operation, LootContext.EntityTarget entityTarget) implements AttackEffect {
    public static final MapCodec<Heal> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    LootNumberProviderTypes.CODEC.fieldOf("heal_amount").forGetter(Heal::healAmount),
                    Operation.CODEC.fieldOf("operation").forGetter(Heal::operation),
                    LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(Heal::entityTarget)
            ).apply(instance, Heal::new)
    );
    public static final PacketCodec<RegistryByteBuf, Heal> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.codec(LootNumberProviderTypes.CODEC), Heal::healAmount,
            Operation.PACKET_CODEC, Heal::operation,
            PacketCodecs.codec(LootContext.EntityTarget.CODEC), Heal::entityTarget,
            Heal::new
    );

    @Override
    public Type<? extends AttackEffect> getType() {
        return AttackEffectTypes.HEAL;
    }

    @Override
    public float modifyDamage(LootContext lootContext, float damageAmount) {
        if (lootContext.get(entityTarget.getParameter()) instanceof LivingEntity livingTarget) {
            livingTarget.heal(
                    switch (operation) {
                        case ADD -> damageAmount + healAmount.nextFloat(lootContext);
                        case MULTIPLY -> damageAmount * healAmount.nextFloat(lootContext);
                        case SET -> healAmount.nextFloat(lootContext);
                    }
            );
        }
        return damageAmount;
    }
}
