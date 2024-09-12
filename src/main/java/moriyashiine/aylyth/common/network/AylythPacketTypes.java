package moriyashiine.aylyth.common.network;

import moriyashiine.aylyth.common.network.packets.GlaivePacketC2S;
import moriyashiine.aylyth.common.network.packets.SpawnParticlesAroundPacketS2C;
import moriyashiine.aylyth.common.network.packets.UpdatePressingUpDownPacketC2S;
import moriyashiine.aylyth.common.other.util.AylythUtil;
import net.fabricmc.fabric.api.networking.v1.PacketType;

public class AylythPacketTypes {
    public static final PacketType<SpawnParticlesAroundPacketS2C> SPAWN_PARTICLES_AROUND_PACKET = PacketType.create(AylythUtil.id("spawn_multiple_particles"), SpawnParticlesAroundPacketS2C::create);
    public static final PacketType<GlaivePacketC2S> GLAIVE_SPECIAL_PACKET = PacketType.create(AylythUtil.id("glaive"), GlaivePacketC2S::create);
    public static final PacketType<UpdatePressingUpDownPacketC2S> UPDATE_RIDER_PACKET = PacketType.create(AylythUtil.id("toggle_pressing_up_down"), UpdatePressingUpDownPacketC2S::create);
}
