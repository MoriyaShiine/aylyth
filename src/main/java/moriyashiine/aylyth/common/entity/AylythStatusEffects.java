package moriyashiine.aylyth.common.entity;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.statuseffects.BlightEffect;
import moriyashiine.aylyth.common.entity.statuseffects.CimmerianStatusEffect;
import moriyashiine.aylyth.common.entity.statuseffects.CrimsonCurseEffect;
import moriyashiine.aylyth.common.entity.statuseffects.MortechisStatusEffect;
import moriyashiine.aylyth.common.entity.statuseffects.WyrdedStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public interface AylythStatusEffects {

    RegistryEntry<StatusEffect> MORTECHIS = register("mortechis", new MortechisStatusEffect());
    RegistryEntry<StatusEffect> CIMMERIAN = register("cimmerian", new CimmerianStatusEffect());
    RegistryEntry<StatusEffect> WYRDED = register("wyrded", new WyrdedStatusEffect());
    RegistryEntry<StatusEffect> CRIMSON_CURSE = register("crimson_curse", new CrimsonCurseEffect());
    RegistryEntry<StatusEffect> BLIGHT = register("blight", new BlightEffect());

    private static <E extends StatusEffect> RegistryEntry<StatusEffect> register(String name, E effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Aylyth.id(name), effect);
    }

    // Load static initializer
    static void register() {}
}
