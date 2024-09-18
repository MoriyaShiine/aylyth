package moriyashiine.aylyth.common.entity.ai;

import moriyashiine.aylyth.common.entity.ai.brains.WreathedHindBrain;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;

import java.util.Optional;

public interface AylythMemoryTypes {

    MemoryModuleType<PlayerEntity> NEAREST_VISIBLE_PLAYER_NEMESIS = register("nearest_visible_player_nemesis");
    MemoryModuleType<PlayerEntity> OWNER_PLAYER = register("owner_player");
    MemoryModuleType<Unit> SHOULD_FOLLOW_OWNER = register("should_follow_owner");
    MemoryModuleType<PlayerEntity> PLEDGED_PLAYER = register("pledged_player");
    MemoryModuleType<WreathedHindBrain.SecondChance> SECOND_CHANCE = register("second_chance");
    MemoryModuleType<Unit> ROOT_ATTACK_COOLDOWN = register("root_attack_cooldown");
    MemoryModuleType<Unit> ROOT_ATTACK_DELAY = register("root_attack_delay");

    private static <M> MemoryModuleType<M> register(String name) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, AylythUtil.id(name), new MemoryModuleType<>(Optional.empty()));
    }

    // Load static initializer
    static void register() {}
}
