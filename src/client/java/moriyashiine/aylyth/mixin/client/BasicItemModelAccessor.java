package moriyashiine.aylyth.mixin.client;

import net.minecraft.client.render.item.model.BasicItemModel;
import net.minecraft.client.render.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BasicItemModel.class)
public interface BasicItemModelAccessor {
    @Accessor
    BakedModel getModel();
}
