package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.ModDamageSources;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;

public class AylythDamageTypes {

    public static void bootstrap(Registerable<DamageType> context) {
        context.register(ModDamageSources.YMPE, new DamageType("ympe", DamageScaling.ALWAYS, 0));
        context.register(ModDamageSources.YMPE_ENTITY, new DamageType("ympe_entity", 0.1f));
        context.register(ModDamageSources.UNBLOCKABLE, new DamageType("unblockable", DamageScaling.ALWAYS, 0));
        context.register(ModDamageSources.SOUL_RIP, new DamageType("soul_rip", 0.1f));
    }
}
