package moriyashiine.aylyth.datagen;

import moriyashiine.aylyth.common.registry.key.ModDamageTypeKeys;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;

public class AylythDamageTypes {

    public static void bootstrap(Registerable<DamageType> context) {
        context.register(ModDamageTypeKeys.YMPE, new DamageType("ympe", DamageScaling.ALWAYS, 0));
        context.register(ModDamageTypeKeys.YMPE_ENTITY, new DamageType("ympe_entity", 0.1f));
        context.register(ModDamageTypeKeys.KILLING_BLOW, new DamageType("wreathed_hind_killing_blow", DamageScaling.ALWAYS, 0));
        context.register(ModDamageTypeKeys.SOUL_RIP, new DamageType("soul_rip", 0.1f));
        context.register(ModDamageTypeKeys.BLIGHT, new DamageType("blight", 0f));
    }
}
