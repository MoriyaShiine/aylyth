package moriyashiine.aylyth.common.network;

import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.attachments.RiderControls;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.network.packets.GlaivePacketC2S;
import moriyashiine.aylyth.common.network.packets.UpdatePressingUpDownPacketC2S;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

public final class AylythServerPacketHandler {
    private AylythServerPacketHandler() {}

    public static void handleUpdatePressingUpDown(UpdatePressingUpDownPacketC2S packet, ServerPlayNetworking.Context context) {
        RiderControls rider = context.player().getAttached(AylythEntityAttachmentTypes.RIDER);
        if (rider != null) {
            rider.setPressingUp(packet.pressingUp());
            rider.setPressingDown(packet.pressingDown());
            context.player().setAttached(AylythEntityAttachmentTypes.RIDER, rider);
        }
    }

    public static void handleGlaiveSpecial(GlaivePacketC2S packet, ServerPlayNetworking.Context context) {
        ItemStack mainStack = context.player().getMainHandStack();
        if (mainStack.isOf(AylythItems.YMPE_GLAIVE)) {
            Entity crosshairTarget = context.player().getWorld().getEntityById(packet.entityId());
            if (crosshairTarget != null) {
                float baseDamage = (float)context.player().getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
                float cooldownMult = context.player().getAttackCooldownProgress(0.5F);
                baseDamage *= 0.2F + cooldownMult * cooldownMult * 0.8F;
                DamageSource damageSource = context.player().getWorld().aylythDamageSources().soulRip(context.player());
                float finalDamage = EnchantmentHelper.getDamage(context.player().getServerWorld(), mainStack, crosshairTarget, damageSource, baseDamage);
                crosshairTarget.damage(
                        context.player().getServerWorld(),
                        damageSource,
                        finalDamage
                );
            }
        }
    }
}
