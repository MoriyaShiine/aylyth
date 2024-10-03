package moriyashiine.aylyth.common.network;

import moriyashiine.aylyth.common.entity.AylythEntityComponents;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.network.packets.GlaivePacketC2S;
import moriyashiine.aylyth.common.network.packets.UpdatePressingUpDownPacketC2S;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public final class AylythServerPacketHandler {
    private AylythServerPacketHandler() {}

    public static void handleUpdatePressingUpDown(UpdatePressingUpDownPacketC2S packet, ServerPlayerEntity player, PacketSender responseSender) {
        AylythEntityComponents.RIDER_COMPONENT.get(player).setPressingUp(packet.pressingUp());
        AylythEntityComponents.RIDER_COMPONENT.get(player).setPressingDown(packet.pressingDown());
    }

    public static void handleGlaiveSpecial(GlaivePacketC2S packet, ServerPlayerEntity player, PacketSender sender) {
        if (player.getStackInHand(Hand.MAIN_HAND).getItem().equals(AylythItems.YMPE_GLAIVE)) {
            float f = (float)player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            float g = EnchantmentHelper.getAttackDamage(player.getMainHandStack(), player.getGroup());
            float h = player.getAttackCooldownProgress(0.5F);
            f *= 0.2F + h * h * 0.8F;
            g *= h;
            f += g;
            Entity crosshairTarget = player.getWorld().getEntityById(packet.entityId());
            if (crosshairTarget != null) {
                crosshairTarget.damage(player.getWorld().modDamageSources().soulRip(player), f);
            }
        }
    }
}
