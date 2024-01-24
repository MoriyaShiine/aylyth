package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.common.network.packet.GlaivePacket;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.registry.key.ModDimensionKeys;
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

import java.util.Optional;

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

    @ModifyReturnValue(method = "getMusicType", at = @At(value = "RETURN", ordinal = 4))
    private MusicSound aylyth$getMusicType(MusicSound original, @Local RegistryEntry<Biome> biome) {
        if (player.getWorld().getDimensionKey() == ModDimensionKeys.AYLYTH_DIMENSION_TYPE) {
            return biome.value().getMusic().orElse(original);
        }
        return original;
    }
}
