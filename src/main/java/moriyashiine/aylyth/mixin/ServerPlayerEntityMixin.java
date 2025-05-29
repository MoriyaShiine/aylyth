package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.common.advancement.AylythCriteria;
import moriyashiine.aylyth.common.block.types.SoulHearthBlock;
import moriyashiine.aylyth.common.data.world.AylythDimensionData;
import moriyashiine.aylyth.common.entity.AylythEntityAttachmentTypes;
import moriyashiine.aylyth.common.entity.attachments.YmpeInfestation;
import moriyashiine.aylyth.common.world.AylythSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        var player = (ServerPlayerEntity) (Object) this;
        boolean isInAylyth = player.getWorld().getRegistryKey() == AylythDimensionData.WORLD;
        if (isInAylyth) {
            player.getAttachedOrCreate(AylythEntityAttachmentTypes.YMPE_INFESTATION);
        }
        if (player.hasAttached(AylythEntityAttachmentTypes.YMPE_INFESTATION)) {
            YmpeInfestation infestation = player.getAttachedOrThrow(AylythEntityAttachmentTypes.YMPE_INFESTATION);
            if (player.isDead() || !player.interactionManager.getGameMode().isSurvivalLike()) {
                return;
            }

            if (isInAylyth) {
                infestation.setInfestationTimer((short) (infestation.getInfestationTimer() + 1));
            } else {
                if (infestation.getStage() > 0 && infestation.getInfestationTimer() <= 0) {
                    infestation.setStage((byte) (infestation.getStage() - 1));
                    infestation.setInfestationTimer(YmpeInfestation.TIME_UNTIL_STAGE_INCREASES);
                }
                if (infestation.getInfestationTimer() > 0) {
                    infestation.setInfestationTimer((short) Math.max(0, infestation.getInfestationTimer() - YmpeInfestation.TIME_UNTIL_STAGE_INCREASES / 20));
                }
            }

            if (infestation.getInfestationTimer() >= YmpeInfestation.TIME_UNTIL_STAGE_INCREASES) {
                AylythCriteria.YMPE_INFESTATION.trigger(player);
                player.getWorld().playSoundFromEntity(null, player, AylythSoundEvents.ENTITY_PLAYER_INCREASE_YMPE_INFESTATION_STAGE.value(), SoundCategory.PLAYERS, 1, player.getSoundPitch());
                infestation.setStage((byte) (infestation.getStage() + 1));
                infestation.setInfestationTimer((short) 0);
                if (infestation.getStage() >= 6) {
                    player.damage(player.getServerWorld(), player.getWorld().aylythDamageSources().ympe(), Float.MAX_VALUE);
                }
            }

            if (infestation.getInfestationTimer() % 20 == 0) {
                switch (infestation.getStage()) {
                    case 2 -> player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 4));
                    case 3 -> applyYmpeEffects(player, 0,0);
                    case 4 -> applyYmpeEffects(player, 1,0);
                    case 5 -> applyYmpeEffects(player, 2, 1);
                    default -> {}
                }
            }
            player.setAttached(AylythEntityAttachmentTypes.YMPE_INFESTATION, infestation);
        }
    }

    @Unique
    private void applyYmpeEffects(PlayerEntity player, int slowAmplifier, int mineAmplifier){
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 4, slowAmplifier));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20 * 4, mineAmplifier));
    }

    @Inject(method = "findRespawnPosition", at = @At(value = "HEAD", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"), cancellable = true)
    private static void soulHearthRespawn(ServerWorld world, BlockPos pos, float spawnAngle, boolean spawnForced, boolean alive, CallbackInfoReturnable<Optional<ServerPlayerEntity.RespawnPos>> cir){
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof SoulHearthBlock && blockState.get(SoulHearthBlock.CHARGES) > 0 && blockState.get(SoulHearthBlock.HALF) == DoubleBlockHalf.LOWER && world.getRegistryKey() == AylythDimensionData.WORLD) {
            Optional<Vec3d> optional = SoulHearthBlock.findRespawnPosition(EntityType.PLAYER, world, pos);
            if (!alive && optional.isPresent()) {
                world.setBlockState(pos, blockState.with(SoulHearthBlock.CHARGES, blockState.get(SoulHearthBlock.CHARGES) - 1).with(SoulHearthBlock.HALF, DoubleBlockHalf.LOWER));
                world.setBlockState(pos.up(), blockState.with(SoulHearthBlock.CHARGES, blockState.get(SoulHearthBlock.CHARGES) - 1).with(SoulHearthBlock.HALF, DoubleBlockHalf.UPPER));
            }
            cir.setReturnValue(optional.map(resPos -> ServerPlayerEntity.RespawnPos.fromCurrentPos(resPos, pos)));
        }
    }
}
