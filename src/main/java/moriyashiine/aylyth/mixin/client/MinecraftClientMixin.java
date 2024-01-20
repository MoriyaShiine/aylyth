package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.common.network.packet.GlaivePacket;
import moriyashiine.aylyth.common.registry.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;
    @Shadow
    @Nullable
    public HitResult crosshairTarget;
    @Unique
    private boolean attackQueued = false;

    public MinecraftClientMixin() {
    }

    @Inject(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;doAttack()Z", ordinal = 0))
    public void aylyth_glaiveStab(CallbackInfo info) {
        if(player != null) {
            if(player.getStackInHand(player.getActiveHand()).isOf(ModItems.YMPE_GLAIVE)) {
                if(player.getAttackCooldownProgress(0.5F) == 1F && (!player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem())) && crosshairTarget != null) {
                    GlaivePacket.send(crosshairTarget.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) crosshairTarget).getEntity() : null);

                    if(crosshairTarget.getType() == HitResult.Type.BLOCK)
                        player.resetLastAttackedTicks();
                }
            }
        }

        if(!info.isCancelled() && attackQueued)
            attackQueued = false;
    }
}
