package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.network.packets.GlaivePacketC2S;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
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

    @Inject(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;doAttack()Z"))
    public void aylyth_glaiveStab(CallbackInfo info) {
        if (player != null) {
            if (player.getStackInHand(player.getActiveHand()).isOf(AylythItems.YMPE_GLAIVE)) {
                if (player.getAttackCooldownProgress(0.5F) == 1F && (!player.getItemCooldownManager().isCoolingDown(player.getMainHandStack())) && crosshairTarget != null) {
                    if (crosshairTarget instanceof EntityHitResult entityHitResult) {
                        ClientPlayNetworking.send(new GlaivePacketC2S(entityHitResult.getEntity().getId()));
                    }

                    if (crosshairTarget.getType() == HitResult.Type.BLOCK) {
                        player.resetLastAttackedTicks();
                    }
                }
            }
        }

        if(!info.isCancelled() && attackQueued) {
            attackQueued = false;
        }
    }

//    @ModifyReturnValue(method = "getMusicInstance", at = @At(value = "RETURN", ordinal = 4))
//    private MusicInstance aylyth$getMusicType(MusicInstance original, @Local RegistryEntry<Biome> biome, @Local World world) {
//        if (player.getWorld().getRegistryKey() == AylythDimensionData.WORLD) {
//            return biome.value().getMusic().map(pool -> pool.getDataOrEmpty(world.random)).orElse(Optional.of(original));
//        }
//        return original;
//    }
}
