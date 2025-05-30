package moriyashiine.aylyth.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.client.screen.AylythDownloadingTerrainScreen;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BooleanSupplier;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @WrapOperation(method = "startWorldLoading", at = @At(value = "NEW", target = "(Ljava/util/function/BooleanSupplier;Lnet/minecraft/client/gui/screen/DownloadingTerrainScreen$WorldEntryReason;)Lnet/minecraft/client/gui/screen/DownloadingTerrainScreen;"))
    private DownloadingTerrainScreen customDownloadingScreen(BooleanSupplier shouldClose, DownloadingTerrainScreen.WorldEntryReason worldEntryReason, Operation<DownloadingTerrainScreen> original, @Local(argsOnly = true) ClientWorld world) {
        if (world.getRegistryKey() == AylythDimensionData.WORLD) {
            return new AylythDownloadingTerrainScreen(shouldClose, worldEntryReason);
        }
        return original.call(shouldClose, worldEntryReason);
    }
}
