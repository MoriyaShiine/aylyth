package moriyashiine.aylyth.common.network;

import moriyashiine.aylyth.common.network.packets.GlaivePacketC2S;
import moriyashiine.aylyth.common.network.packets.SpawnShuckParticlesPacketS2C;
import moriyashiine.aylyth.common.network.packets.UpdatePressingUpDownPacketC2S;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.fabricmc.fabric.api.networking.v1.PacketType;

public class AylythPacketTypes {
    public static final PacketType<SpawnShuckParticlesPacketS2C> SHUCK_PARTICLES_PACKET = PacketType.create(AylythUtil.id("spawn_shuck_particles"), SpawnShuckParticlesPacketS2C::create);
    public static final PacketType<GlaivePacketC2S> GLAIVE_SPECIAL_PACKET = PacketType.create(AylythUtil.id("glaive"), GlaivePacketC2S::create);
    public static final PacketType<UpdatePressingUpDownPacketC2S> UPDATE_RIDER_PACKET = PacketType.create(AylythUtil.id("toggle_pressing_up_down"), UpdatePressingUpDownPacketC2S::create);
}
