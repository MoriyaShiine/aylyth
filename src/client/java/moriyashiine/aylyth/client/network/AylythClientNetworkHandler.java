package moriyashiine.aylyth.client.network;

import moriyashiine.aylyth.common.network.packets.SpawnParticlesAroundPacketS2C;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;

public final class AylythClientNetworkHandler {
    private AylythClientNetworkHandler() {}

    public static void handleSpawnParticlesAround(SpawnParticlesAroundPacketS2C packet, ClientPlayerEntity player, PacketSender responseSender) {
        ClientWorld world = player.clientWorld;
        if (world != null) {
            Entity entity = world.getEntityById(packet.entityId());
            if (entity != null) {
                for (int i = 0; i < packet.numEach(); i++) {
                    for (ParticleEffect particleEffect : packet.particles()) {
                        world.addParticle(particleEffect, entity.getParticleX(1), entity.getRandomBodyY(), entity.getParticleZ(1), 0, 0, 0);
                    }
                }
            }
        }
    }
}
