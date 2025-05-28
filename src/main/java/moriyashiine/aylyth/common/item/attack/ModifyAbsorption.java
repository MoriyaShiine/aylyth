package moriyashiine.aylyth.common.item.attack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.item.AttackEffectTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.world.ServerWorld;

public record ModifyAbsorption(float amount, Operation operation, LootContext.EntityTarget entityTarget) implements AttackEffect {
    public static final MapCodec<ModifyAbsorption> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("amount").forGetter(ModifyAbsorption::amount),
                    Operation.CODEC.fieldOf("operation").forGetter(ModifyAbsorption::operation),
                    LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(ModifyAbsorption::entityTarget)
            ).apply(instance, ModifyAbsorption::new)
    );
    public static final PacketCodec<RegistryByteBuf, ModifyAbsorption> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, ModifyAbsorption::amount,
            Operation.PACKET_CODEC, ModifyAbsorption::operation,
            PacketCodecs.codec(LootContext.EntityTarget.CODEC), ModifyAbsorption::entityTarget,
            ModifyAbsorption::new
    );

    @Override
    public Type<? extends AttackEffect> getType() {
        return AttackEffectTypes.MODIFY_ABSORPTION;
    }

    @Override
    public float modifyDamage(LootContext lootContext, float damageAmount) {
        if (lootContext.get(entityTarget.getParameter()) instanceof LivingEntity target) {
            // TODO: doesn't cover if the operation is adding positive absorption
            if (target.getAbsorptionAmount() > 0) {
                if (target.getAbsorptionAmount() > 1) {
                    target.setAbsorptionAmount(
                            switch (operation) {
                                case ADD -> target.getAbsorptionAmount() + amount;
                                case MULTIPLY -> target.getAbsorptionAmount() * amount;
                                case SET -> amount;
                            }
                    );
                    return 0;
                } else {
                    float absorption = target.getAbsorptionAmount();
                    target.setAbsorptionAmount(0);
                    return damageAmount - absorption;
                }
            }
        }
        return damageAmount;
    }
}
