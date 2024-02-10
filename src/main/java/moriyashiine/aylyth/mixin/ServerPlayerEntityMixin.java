package moriyashiine.aylyth.mixin;

import com.mojang.authlib.GameProfile;
import moriyashiine.aylyth.api.interfaces.ExtraPlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements ExtraPlayerData {
    @Unique
    private NbtCompound aylyth$extraPlayerData;

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void aylyth_writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (aylyth$extraPlayerData != null && !aylyth$extraPlayerData.isEmpty()) {
            nbt.put("ExtraPlayerData", aylyth$extraPlayerData);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void aylyth_readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("ExtraPlayerData")) {
            aylyth$extraPlayerData = nbt.getCompound("ExtraPlayerData");
        }
    }

    @Inject(method = "copyFrom", at = @At("TAIL"))
    private void aylyth_copyPersistentData(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        NbtCompound oldData = ((ExtraPlayerData) oldPlayer).getExtraPlayerData();
        NbtCompound persistent = oldData.getCompound("PersistedPlayer");
        if (persistent != null) {
            NbtCompound thisData = this.getExtraPlayerData();
            thisData.put("PersistedPlayer", persistent);
        }
    }

    @Unique
    @Override
    public NbtCompound getExtraPlayerData() {
        if (aylyth$extraPlayerData == null) {
            aylyth$extraPlayerData = new NbtCompound();
        }
        return aylyth$extraPlayerData;
    }
}
