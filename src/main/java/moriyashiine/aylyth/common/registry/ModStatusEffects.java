package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.statuseffect.*;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModStatusEffects {
    public static void init() {}

    public static final StatusEffect MORTECHIS = register("mortechis", new MortechisStatusEffect());
    public static final StatusEffect CIMMERIAN = register("cimmerian", new CimmerianStatusEffect());
    public static final StatusEffect WYRDED = register("wyrded", new WyrdedStatusEffect());
    public static final StatusEffect CRIMSON_CURSE = register("crimson_curse", new CrimsonCurseEffect());
    public static final StatusEffect BLIGHT = register("blight", new BlightEffect());

    private static <T extends StatusEffect> T register(String id, T statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, AylythUtil.id(id), statusEffect);
    }
}
