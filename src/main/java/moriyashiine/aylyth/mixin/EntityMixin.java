package moriyashiine.aylyth.mixin;

import moriyashiine.aylyth.api.interfaces.Pledgeable;
import moriyashiine.aylyth.common.block.IContextBlockSoundGroup;
import moriyashiine.aylyth.common.world.ModWorldState;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Entity.class)
public abstract class EntityMixin {


    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow public World world;

    @Inject(method = "playStepSound", at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V")}, locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    private void aylyth_playStepSound(BlockPos pos, BlockState state, CallbackInfo ci,BlockState b, BlockSoundGroup blockSoundGroup) {
        var entity = (Entity)(Object)this;
        var world = entity.world;
        var blockState = world.getBlockState(pos.up());
        state = blockState.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState : state;
        if (state.getBlock() instanceof IContextBlockSoundGroup cast) {
            blockSoundGroup = cast.getBlockSoundGroup(state, pos, blockSoundGroup, entity);
            playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * 0.15f, blockSoundGroup.getPitch());
            ci.cancel();
        }
    }



    @Inject(method = "remove", at = @At("TAIL"))
    private void remove(CallbackInfo callbackInfo) {
        if (!world.isClient && this instanceof Pledgeable pledgeable) {
            ModWorldState worldState = ModWorldState.get(world);
            worldState.pledgesToRemove.addAll(pledgeable.getPledgedPlayerUUIDs());
            worldState.markDirty();
        }
    }
}
