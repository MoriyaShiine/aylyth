package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.network.packets.GlaivePacketC2S;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.biome.Biome;
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

    @Inject(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;doAttack()Z", ordinal = 0))
    public void aylyth_glaiveStab(CallbackInfo info) {
        if(player != null) {
            if(player.getStackInHand(player.getActiveHand()).isOf(AylythItems.YMPE_GLAIVE)) {
                if(player.getAttackCooldownProgress(0.5F) == 1F && (!player.getItemCooldownManager().isCoolingDown(player.getMainHandStack().getItem())) && crosshairTarget != null) {
                    if (crosshairTarget instanceof EntityHitResult entityHitResult) {
                        ClientPlayNetworking.send(new GlaivePacketC2S(entityHitResult.getEntity().getId()));
                    }

                    if(crosshairTarget.getType() == HitResult.Type.BLOCK)
                        player.resetLastAttackedTicks();
                }
            }
        }

        if(!info.isCancelled() && attackQueued)
            attackQueued = false;
    }

    @ModifyReturnValue(method = "getMusicType", at = @At(value = "RETURN", ordinal = 4))
    private MusicSound aylyth$getMusicType(MusicSound original, @Local RegistryEntry<Biome> biome) {
        if (player.getWorld().getRegistryKey() == AylythDimensionData.WORLD) {
            return biome.value().getMusic().orElse(original);
        }
        return original;
    }
}
