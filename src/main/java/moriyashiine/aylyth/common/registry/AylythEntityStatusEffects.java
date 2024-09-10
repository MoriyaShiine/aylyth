package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.statuseffect.*;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythEntityStatusEffects {

    StatusEffect MORTECHIS = register("mortechis", new MortechisStatusEffect());
    StatusEffect CIMMERIAN = register("cimmerian", new CimmerianStatusEffect());
    StatusEffect WYRDED = register("wyrded", new WyrdedStatusEffect());
    StatusEffect CRIMSON_CURSE = register("crimson_curse", new CrimsonCurseEffect());
    StatusEffect BLIGHT = register("blight", new BlightEffect());

    private static <E extends StatusEffect> E register(String id, E effect) {
        return Registry.register(Registries.STATUS_EFFECT, AylythUtil.id(id), effect);
    }

    // Load static initializer
    static void register() {}
}
