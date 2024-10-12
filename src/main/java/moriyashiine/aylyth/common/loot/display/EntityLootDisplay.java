package moriyashiine.aylyth.common.loot.display;

import net.minecraft.entity.EntityType;

public interface EntityLootDisplay extends LootDisplay {
    EntityType<?> entity();
}
