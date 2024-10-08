package moriyashiine.aylyth.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    // TODO: respawns player in aylyth if they're already in aylyth
    @ModifyVariable(method = "respawnPlayer", at = @At(value = "STORE"), ordinal = 1)
    private ServerWorld respawnPlayerInAylyth(ServerWorld serverWorld, @Local(argsOnly = true) ServerPlayerEntity original) {
        if (original.getWorld().getRegistryKey() == AylythDimensionData.WORLD) {
            return original.getServerWorld();
        }
        return serverWorld;
    }
}
