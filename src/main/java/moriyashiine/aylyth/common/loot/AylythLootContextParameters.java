package moriyashiine.aylyth.common.loot;

import net.minecraft.util.Unit;
import net.minecraft.util.context.ContextParameter;

public interface AylythLootContextParameters {
    ContextParameter<Unit> CRITICAL = ContextParameter.of("critical");
}
