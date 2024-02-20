package moriyashiine.aylyth.common.network.packets;

import moriyashiine.aylyth.common.network.AylythPacketTypes;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public record SpawnShuckParticlesPacketS2C(int entityId) implements FabricPacket {
	public static SpawnShuckParticlesPacketS2C create(PacketByteBuf buf) {
		return new SpawnShuckParticlesPacketS2C(buf.readInt());
	}

	public static void send(ServerPlayerEntity player, Entity entity) {
		ServerPlayNetworking.send(player, new SpawnShuckParticlesPacketS2C(entity.getId()));
	}

	@Override
	public void write(PacketByteBuf buf) {
		buf.writeInt(entityId);
	}

	@Override
	public PacketType<?> getType() {
		return AylythPacketTypes.SHUCK_PARTICLES_PACKET;
	}
}
