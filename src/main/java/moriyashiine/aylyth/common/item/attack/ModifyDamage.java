package moriyashiine.aylyth.common.item.attack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.item.AttackEffectTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.world.ServerWorld;

public record ModifyDamage(float amount, Operation operation) implements AttackEffect {
    public static final MapCodec<ModifyDamage> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("amount").forGetter(ModifyDamage::amount),
                    Operation.CODEC.fieldOf("operation").forGetter(ModifyDamage::operation)
            ).apply(instance, ModifyDamage::new)
    );
    public static final PacketCodec<RegistryByteBuf, ModifyDamage> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, ModifyDamage::amount,
            Operation.PACKET_CODEC, ModifyDamage::operation,
            ModifyDamage::new
    );

    @Override
    public Type<? extends AttackEffect> getType() {
        return AttackEffectTypes.MODIFY_DAMAGE;
    }

    @Override
    public float modifyDamage(LootContext lootContext, float damageAmount) {
        return switch (operation) {
            case ADD -> damageAmount + amount;
            case MULTIPLY -> damageAmount * amount;
            case SET -> amount;
        };
    }
}
