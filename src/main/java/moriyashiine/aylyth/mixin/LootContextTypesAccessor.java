package moriyashiine.aylyth.mixin;

import com.google.common.collect.BiMap;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootContextTypes.class)
public interface LootContextTypesAccessor {
    @Accessor
    static BiMap<Identifier, LootContextType> getMap() {
        throw new AssertionError("Implemented via mixin");
    }
}
