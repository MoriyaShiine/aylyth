package moriyashiine.aylyth.mixin.client;

import net.minecraft.client.render.item.HeldItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HeldItemRenderer.class)
public interface HeldItemRendererAccessor {

    @Accessor
    float getEquipProgressMainHand();

    @Accessor
    float getEquipProgressOffHand();
}
