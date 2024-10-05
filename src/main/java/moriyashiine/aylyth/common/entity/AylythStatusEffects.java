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

public interface AylythStatusEffects {

    StatusEffect MORTECHIS = register("mortechis", new MortechisStatusEffect());
    StatusEffect CIMMERIAN = register("cimmerian", new CimmerianStatusEffect());
    StatusEffect WYRDED = register("wyrded", new WyrdedStatusEffect());
    StatusEffect CRIMSON_CURSE = register("crimson_curse", new CrimsonCurseEffect());
    StatusEffect BLIGHT = register("blight", new BlightEffect());

    private static <E extends StatusEffect> E register(String name, E effect) {
        return Registry.register(Registries.STATUS_EFFECT, Aylyth.id(name), effect);
    }

    // Load static initializer
    static void register() {}
}
