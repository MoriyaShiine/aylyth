package moriyashiine.aylyth.common.loot;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.mixin.LootContextTypesAccessor;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;

import java.util.function.UnaryOperator;

public interface AylythLootContextTypes {
    LootContextType HARVEST = register("harvest", builder ->
            builder.require(LootContextParameters.ORIGIN)
                    .require(LootContextParameters.BLOCK_STATE)
                    .allow(LootContextParameters.TOOL)
    );

    private static LootContextType register(String name, UnaryOperator<LootContextType.Builder> builder) {
        LootContextType type = builder.apply(LootContextType.create()).build();
        LootContextTypesAccessor.getMap().put(Aylyth.id(name), type);
        return type;
    }

    static void register() {}
}
