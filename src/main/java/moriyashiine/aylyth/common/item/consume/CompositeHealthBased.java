package moriyashiine.aylyth.common.item.consume;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import moriyashiine.aylyth.common.item.AylythConsumeEffectTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.world.World;

import java.util.List;

public record CompositeHealthBased(List<ConsumeEffect> whenHealthFull, List<ConsumeEffect> whenHealthNotFull) implements ConsumeEffect {
    public static final MapCodec<CompositeHealthBased> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ConsumeEffect.CODEC.listOf().fieldOf("when_health_full").forGetter(CompositeHealthBased::whenHealthFull),
                    ConsumeEffect.CODEC.listOf().fieldOf("when_health_not_full").forGetter(CompositeHealthBased::whenHealthNotFull)
            ).apply(instance, CompositeHealthBased::new)
    );
    public static final PacketCodec<RegistryByteBuf, CompositeHealthBased> PACKET_CODEC = PacketCodec.tuple(
            ConsumeEffect.PACKET_CODEC.collect(PacketCodecs.toList()), CompositeHealthBased::whenHealthFull,
            ConsumeEffect.PACKET_CODEC.collect(PacketCodecs.toList()), CompositeHealthBased::whenHealthNotFull,
            CompositeHealthBased::new
    );

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return AylythConsumeEffectTypes.COMPOSITE_HEALTH_BASED;
    }

    @Override
    public boolean onConsume(World world, ItemStack stack, LivingEntity user) {
        if (user.getHealth() == user.getMaxHealth()) {
            if (!whenHealthFull.isEmpty()) {
                whenHealthFull.forEach(consumeEffect -> consumeEffect.onConsume(world, stack, user));
                return true;
            }
        } else if (!whenHealthNotFull.isEmpty()) {
            whenHealthNotFull.forEach(consumeEffect -> consumeEffect.onConsume(world, stack, user));
            return true;
        }
        return false;
    }
}
