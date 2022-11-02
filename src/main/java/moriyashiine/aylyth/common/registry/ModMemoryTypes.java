package moriyashiine.aylyth.common.registry;

import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

public class ModMemoryTypes {
    public static final MemoryModuleType<PlayerEntity> NEAREST_VISIBLE_PLAYER_NEMESIS = register("nearest_visible_player_nemesis");

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registry.MEMORY_MODULE_TYPE, new Identifier(id), new MemoryModuleType<>(Optional.empty()));
    }

    public static void init() {

    }
}
