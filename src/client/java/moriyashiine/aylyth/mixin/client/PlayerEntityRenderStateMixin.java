package moriyashiine.aylyth.mixin.client;

import moriyashiine.aylyth.client.api.interfaces.ExtendedPlayerEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements ExtendedPlayerEntityRenderState {
    @Unique
    private int cuirassStage;
    @Unique
    private int infestationStage;

    @Override
    public void aylyth$setCuirassStage(int stage) {
        cuirassStage = stage;
    }

    @Override
    public int aylyth$cuirassStage() {
        return cuirassStage;
    }

    @Override
    public void aylyth$setInfestationStage(int stage) {
        infestationStage = stage;
    }

    @Override
    public int aylyth$infestationStage() {
        return infestationStage;
    }
}
