package moriyashiine.aylyth.common.advancement;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.advancement.criteria.HindPledgeCriterion;
import moriyashiine.aylyth.common.advancement.criteria.ShuckingCriterion;
import moriyashiine.aylyth.common.advancement.criteria.TameHostileCriterion;
import moriyashiine.aylyth.common.advancement.criteria.YmpeInfestationCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AylythCriteria {

    YmpeInfestationCriterion YMPE_INFESTATION = register("ympe_infestation", new YmpeInfestationCriterion());
    ShuckingCriterion SHUCKING = register("shucking", new ShuckingCriterion());
    TameHostileCriterion TAME_HOSTILE = register("tame_hostile", new TameHostileCriterion());
    HindPledgeCriterion HIND_PLEDGE = register("hind_pledge", new HindPledgeCriterion());

    private static <I extends Criterion<?>> I register(String name, I criterion) {
        return Registry.register(Registries.CRITERION, Aylyth.id(name), criterion);
    }

    // Load static initializer
    static void register() {}
}
