package moriyashiine.aylyth.client.network.packet;

import moriyashiine.aylyth.common.Aylyth;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SpawnShuckParticlesPacket {
	public static final Identifier ID = new Identifier(Aylyth.MOD_ID, "spawn_shuck_particles");
	
	public static void send(PlayerEntity player, Entity entity) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeInt(entity.getId());
		ServerPlayNetworking.send((ServerPlayerEntity) player, ID, buf);
	}
	
	public static void receive(MinecraftClient client, ClientPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
		int id = buf.readInt();
		client.execute(() -> {
			ClientWorld world = client.world;
			if (world != null) {
				Entity entity = world.getEntityById(id);
				if (entity != null) {
					for (int i = 0; i < 32; i++) {
						world.addParticle(ParticleTypes.SMOKE, entity.getParticleX(1), entity.getRandomBodyY(), entity.getParticleZ(1), 0, 0, 0);
						world.addParticle(ParticleTypes.FALLING_HONEY, entity.getParticleX(1), entity.getRandomBodyY(), entity.getParticleZ(1), 0, 0, 0);
					}
				}
			}
		});
	}
}
