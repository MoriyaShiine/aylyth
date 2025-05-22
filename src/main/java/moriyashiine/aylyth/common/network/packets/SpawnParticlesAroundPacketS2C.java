package moriyashiine.aylyth.common.network.packets;

import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

import java.util.List;

public record SpawnParticlesAroundPacketS2C(int entityId, int numEach, List<ParticleEffect> particles) implements CustomPayload {
	public static final CustomPayload.Id<SpawnParticlesAroundPacketS2C> ID = new Id<>(Aylyth.id("spawn_multiple_particles"));
	public static final PacketCodec<? super RegistryByteBuf, SpawnParticlesAroundPacketS2C> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecs.INTEGER.cast(), SpawnParticlesAroundPacketS2C::entityId,
			PacketCodecs.INTEGER.cast(), SpawnParticlesAroundPacketS2C::numEach,
			ParticleTypes.PACKET_CODEC.collect(PacketCodecs.toList()), SpawnParticlesAroundPacketS2C::particles,
			SpawnParticlesAroundPacketS2C::new
	);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
