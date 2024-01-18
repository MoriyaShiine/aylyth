package moriyashiine.aylyth.common.registry;

import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ModMemoryTypes {
    public static final MemoryModuleType<PlayerEntity> NEAREST_VISIBLE_PLAYER_NEMESIS = register("nearest_visible_player_nemesis");
    public static final MemoryModuleType<PlayerEntity> OWNER_PLAYER = register("owner_player");
    public static final MemoryModuleType<Boolean> SHOULD_FOLLOW_OWNER = register("should_follow_owner");
    public static final MemoryModuleType<PlayerEntity> PLEDGED_PLAYER = register("pledged_player");

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registry.MEMORY_MODULE_TYPE, new Identifier(id), new MemoryModuleType<>(Optional.empty()));
    }

    public static void init() {

    }
}
