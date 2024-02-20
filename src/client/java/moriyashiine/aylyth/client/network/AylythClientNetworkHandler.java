package moriyashiine.aylyth.client.network;

import moriyashiine.aylyth.common.network.packets.SpawnShuckParticlesPacketS2C;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;

public class AylythClientNetworkHandler {
    public static void handleSpawnShuckParticles(SpawnShuckParticlesPacketS2C packet, ClientPlayerEntity player, PacketSender responseSender) {
        ClientWorld world = player.clientWorld;
        if (world != null) {
            Entity entity = world.getEntityById(packet.entityId());
            if (entity != null) {
                for (int i = 0; i < 32; i++) {
                    world.addParticle(ParticleTypes.SMOKE, entity.getParticleX(1), entity.getRandomBodyY(), entity.getParticleZ(1), 0, 0, 0);
                    world.addParticle(ParticleTypes.FALLING_HONEY, entity.getParticleX(1), entity.getRandomBodyY(), entity.getParticleZ(1), 0, 0, 0);
                }
            }
        }
    }
}
