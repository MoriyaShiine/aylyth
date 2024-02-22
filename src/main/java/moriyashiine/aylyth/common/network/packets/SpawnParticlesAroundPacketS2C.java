package moriyashiine.aylyth.common.network.packets;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.network.AylythPacketTypes;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;

import java.util.List;

public record SpawnParticlesAroundPacketS2C(int entityId, int numEach, List<ParticleEffect> particles) implements FabricPacket {
	public static SpawnParticlesAroundPacketS2C create(PacketByteBuf buf) {
		int entityId = buf.readVarInt();
		int numEach = buf.readVarInt();
		int size = buf.readVarInt();
		List<ParticleEffect> particleEffects = new ObjectArrayList<>();
		for (int i = 0; i < size; i++) {
			ParticleType<?> particleType = buf.readRegistryValue(Registries.PARTICLE_TYPE);
			particleEffects.add(readParticleEffect(particleType, buf));
		}
		return new SpawnParticlesAroundPacketS2C(entityId, numEach, particleEffects);
	}

	private static <T extends ParticleEffect> ParticleEffect readParticleEffect(ParticleType<T> particleType, PacketByteBuf buf) {
		return particleType.getParametersFactory().read(particleType, buf);
	}

	@Override
	public void write(PacketByteBuf buf) {
		buf.writeVarInt(entityId);
		buf.writeVarInt(numEach);
		buf.writeVarInt(particles.size());
		for (ParticleEffect effect : particles) {
			buf.writeRegistryValue(Registries.PARTICLE_TYPE, effect.getType());
			effect.write(buf);
		}
	}

	@Override
	public PacketType<?> getType() {
		return AylythPacketTypes.SPAWN_PARTICLES_AROUND_PACKET;
	}
}
