package moriyashiine.aylyth.mixin.client;

import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.util.UserCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SkullBlockEntity.class)
public interface SkullBlockEntityAccessor {

    @Accessor
    static UserCache getUserCache() {
        throw new UnsupportedOperationException();
    }
}
