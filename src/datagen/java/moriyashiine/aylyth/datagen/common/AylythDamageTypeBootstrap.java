package moriyashiine.aylyth.datagen.common;

import moriyashiine.aylyth.common.data.AylythDamageTypes;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;

public final class AylythDamageTypeBootstrap {
    private AylythDamageTypeBootstrap() {}

    public static void bootstrap(Registerable<DamageType> context) {
        context.register(AylythDamageTypes.YMPE, new DamageType("ympe", DamageScaling.ALWAYS, 0));
        context.register(AylythDamageTypes.YMPE_ENTITY, new DamageType("ympe_entity", 0.1f));
        context.register(AylythDamageTypes.KILLING_BLOW, new DamageType("wreathed_hind_killing_blow", DamageScaling.ALWAYS, 0));
        context.register(AylythDamageTypes.SOUL_RIP, new DamageType("soul_rip", 0.1f));
        context.register(AylythDamageTypes.BLIGHT, new DamageType("blight", 0f));
        context.register(AylythDamageTypes.SHUCKING, new DamageType("shucking", 0f));
    }
}
