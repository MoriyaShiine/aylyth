package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.client.screen.AylythDownloadingTerrainScreen;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.network.packets.GlaivePacketC2S;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MusicInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.function.BooleanSupplier;

@Debug(export = true)
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

    @WrapOperation(method = "joinWorld", at = @At(value = "NEW", target = "(Ljava/util/function/BooleanSupplier;Lnet/minecraft/client/gui/screen/DownloadingTerrainScreen$WorldEntryReason;)Lnet/minecraft/client/gui/screen/DownloadingTerrainScreen;"))
    private DownloadingTerrainScreen customDownloadingScreen(BooleanSupplier shouldClose, DownloadingTerrainScreen.WorldEntryReason worldEntryReason, Operation<DownloadingTerrainScreen> original, @Local(argsOnly = true) ClientWorld world) {
        if (world.getRegistryKey() == AylythDimensionData.WORLD) {
            return new AylythDownloadingTerrainScreen(shouldClose, worldEntryReason);
        }
        return original.call(shouldClose, worldEntryReason);
    }

    // TODO: Fix music
//    @ModifyReturnValue(method = "getMusicInstance", at = @At(value = "RETURN", ordinal = 4))
//    private MusicInstance getMusicType(MusicInstance original, @Local RegistryEntry<Biome> biome, @Local World world) {
//        if (world.getRegistryKey() == AylythDimensionData.WORLD) {
//            return biome.value().getMusic().flatMap(pool -> pool.getDataOrEmpty(world.random)).map(MusicInstance::new).orElse(original);
//        }
//        return original;
//    }
}
