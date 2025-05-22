package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.api.interfaces.ExtendedLivingEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements ExtendedLivingEntityRenderState {
    @Unique
    private int thornsStage;

    @Override
    public void aylyth$setThornsStage(int stage) {
        thornsStage = stage;
    }

    @Override
    public int aylyth$thornsStage() {
        return thornsStage;
    }
}
