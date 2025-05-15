package moriyashiine.aylyth.common.loot;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.mixin.LootContextTypesAccessor;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.context.ContextType;

import java.util.function.UnaryOperator;

public interface AylythLootContextTypes {
    ContextType HARVEST = register("harvest", builder ->
            builder.require(LootContextParameters.ORIGIN)
                    .require(LootContextParameters.BLOCK_STATE)
                    .allow(LootContextParameters.TOOL)
    );
    ContextType STRIP = register("strip", builder ->
            builder.require(LootContextParameters.ORIGIN)
                    .require(LootContextParameters.BLOCK_STATE)
                    .allow(LootContextParameters.TOOL)
    );

    private static ContextType register(String name, UnaryOperator<ContextType.Builder> builder) {
        ContextType type = builder.apply(new ContextType.Builder()).build();
        LootContextTypesAccessor.getMap().put(Aylyth.id(name), type);
        return type;
    }

    static void register() {}
}
