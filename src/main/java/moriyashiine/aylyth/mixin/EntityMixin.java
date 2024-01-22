package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.common.block.IContextBlockSoundGroup;
import moriyashiine.aylyth.common.block.util.SeepTeleportable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public abstract class EntityMixin implements SeepTeleportable {

    @Unique
    BlockPos lastSeepPos;

    @Unique boolean isInSeep;


    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow public World world;

    @Unique
    @Override
    public BlockPos getLastSeepPos() {
        return lastSeepPos;
    }

    @Unique
    @Override
    public boolean isInSeep() {
        return isInSeep;
    }

    @Unique
    @Override
    public void setInSeep(BlockPos pos) {
        if (!this.world.isClient() && (lastSeepPos == null || !lastSeepPos.equals(pos))) {
            lastSeepPos = pos.toImmutable();
        }
        isInSeep = true;
    }

    @Inject(method = "playStepSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void aylyth_playStepSound(BlockPos pos, BlockState state, CallbackInfo ci, BlockSoundGroup blockSoundGroup) {
        Entity entity = (Entity)(Object)this;
        World world = entity.getWorld();
        BlockState blockState = world.getBlockState(pos.up());
        state = blockState.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState : state; // TODO: Rewrite in 1.20 with Mojang's new multi block sound system.
        if (state.getBlock() instanceof IContextBlockSoundGroup cast) {
            blockSoundGroup = cast.getBlockSoundGroup(state, pos, blockSoundGroup, entity);
            playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * 0.15f, blockSoundGroup.getPitch());
            ci.cancel();
        }
    }
}
