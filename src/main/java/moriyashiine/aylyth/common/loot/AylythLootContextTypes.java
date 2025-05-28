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
    ContextType ATTACK = register("attack", builder ->
            builder.require(LootContextParameters.THIS_ENTITY)
                    .require(LootContextParameters.ORIGIN)
                    .require(LootContextParameters.DAMAGE_SOURCE)
                    .allow(LootContextParameters.ATTACKING_ENTITY)
                    .allow(LootContextParameters.DIRECT_ATTACKING_ENTITY)
                    .allow(LootContextParameters.LAST_DAMAGE_PLAYER)
                    .allow(AylythLootContextParameters.CRITICAL)
    );

    private static ContextType register(String name, UnaryOperator<ContextType.Builder> builder) {
        ContextType type = builder.apply(new ContextType.Builder()).build();
        LootContextTypesAccessor.getMap().put(Aylyth.id(name), type);
        return type;
    }

    static void register() {}
}
