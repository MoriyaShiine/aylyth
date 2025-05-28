package moriyashiine.aylyth.common.item.attack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import moriyashiine.aylyth.common.registry.AylythRegistries;
import moriyashiine.aylyth.common.registry.AylythRegistryKeys;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextAware;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.world.ServerWorld;

public interface AttackEffect extends LootContextAware {
    Codec<AttackEffect> CODEC = AylythRegistries.ATTACK_EFFECT_TYPE.getCodec().dispatch(AttackEffect::getType, Type::codec);
    PacketCodec<RegistryByteBuf, AttackEffect> PACKET_CODEC = PacketCodecs.registryValue(AylythRegistryKeys.ATTACK_EFFECT_TYPE)
            .dispatch(AttackEffect::getType, Type::packetCodec);

    Type<? extends AttackEffect> getType();

    float modifyDamage(LootContext lootContext, float damageAmount);

    record Type<T extends AttackEffect>(MapCodec<T> codec, PacketCodec<RegistryByteBuf, T> packetCodec) {}
}
