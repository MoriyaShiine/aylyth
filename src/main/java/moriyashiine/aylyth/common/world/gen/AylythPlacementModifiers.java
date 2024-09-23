package moriyashiine.aylyth.common.world.gen;

import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.world.gen.placementmodifiers.EnvironmentCheckPlacementModifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

public interface AylythPlacementModifiers {

    PlacementModifierType<EnvironmentCheckPlacementModifier> ENVIRONMENT_CHECK = register("environment_check", EnvironmentCheckPlacementModifier.CODEC);

    private static <T extends PlacementModifier> PlacementModifierType<T> register(String id, Codec<T> codec) {
        return Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, Aylyth.id(id), () -> codec);
    }

    static void register() {}
}
