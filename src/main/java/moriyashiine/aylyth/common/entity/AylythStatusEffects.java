package moriyashiine.aylyth.common.entity;

import moriyashiine.aylyth.common.entity.statuseffect.*;
import moriyashiine.aylyth.common.entity.statuseffect.type.*;
import moriyashiine.aylyth.common.util.AylythUtil;
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
        return Registry.register(Registries.STATUS_EFFECT, AylythUtil.id(name), effect);
    }

    // Load static initializer
    static void register() {}
}
