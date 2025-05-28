package moriyashiine.aylyth.common.item.attack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import moriyashiine.aylyth.common.item.AttackEffectTypes;
import moriyashiine.aylyth.common.network.packets.SpawnParticlesAroundPacketS2C;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.List;
import java.util.function.IntFunction;

public record SpawnParticlesAround(int count, List<ParticleEffect> particleEffects, LootContext.EntityTarget entityTarget) implements AttackEffect {
    public static final MapCodec<SpawnParticlesAround> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.INT.fieldOf("count").forGetter(SpawnParticlesAround::count),
                    ParticleTypes.TYPE_CODEC.listOf().fieldOf("particles").forGetter(SpawnParticlesAround::particleEffects),
                    LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(SpawnParticlesAround::entityTarget)
            ).apply(instance, SpawnParticlesAround::new)
    );
    public static final PacketCodec<RegistryByteBuf, SpawnParticlesAround> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, SpawnParticlesAround::count,
            ParticleTypes.PACKET_CODEC.collect(PacketCodecs.toList()), SpawnParticlesAround::particleEffects,
            PacketCodecs.codec(LootContext.EntityTarget.CODEC), SpawnParticlesAround::entityTarget,
            SpawnParticlesAround::new
    );

    @Override
    public Type<? extends AttackEffect> getType() {
        return AttackEffectTypes.SPAWN_PARTICLES_AROUND;
    }

    @Override
    public float modifyDamage(LootContext lootContext, float damageAmount) {
        Entity target = lootContext.getOrThrow(entityTarget.getParameter());
        PlayerLookup.tracking(target).forEach(trackingPlayer -> {
            ServerPlayNetworking.send(trackingPlayer, new SpawnParticlesAroundPacketS2C(target.getId(), count, particleEffects));
        });

        if (target instanceof ServerPlayerEntity player) {
            ServerPlayNetworking.send(player, new SpawnParticlesAroundPacketS2C(target.getId(), count, particleEffects));
        }
        return damageAmount;
    }
}
