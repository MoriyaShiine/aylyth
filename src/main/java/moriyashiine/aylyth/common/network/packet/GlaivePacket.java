package moriyashiine.aylyth.common.network.packet;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.registry.ModDamageSources;
import moriyashiine.aylyth.common.registry.ModItems;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class GlaivePacket {
    public static final Identifier ID = new Identifier(Aylyth.MOD_ID, "glaive");

    public GlaivePacket() {
    }

    public static void send(@Nullable Entity entity) {
        PacketByteBuf buf = PacketByteBufs.create();
        if (entity != null) {
            buf.writeInt(entity.getId());
        }

        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, PlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        int entityId = buf.isReadable() ? buf.readInt() : -1;
        server.execute(() -> {
            if (player.getStackInHand(Hand.MAIN_HAND).getItem().equals(ModItems.YMPE_GLAIVE)) {
                float f = (float)player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                float g = EnchantmentHelper.getAttackDamage(player.getMainHandStack(), player.getGroup());
                float h = player.getAttackCooldownProgress(0.5F);
                f *= 0.2F + h * h * 0.8F;
                g *= h;
                f += g;
                Entity crosshairTarget = player.getWorld().getEntityById(entityId);
                if (crosshairTarget != null) {
                    crosshairTarget.damage(ModDamageSources.soulRip(player), f);
                }
            }

        });
    }
}