package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.advancement.criteria.HindPledgeCriterion;
import moriyashiine.aylyth.common.advancement.criteria.ShuckingCriterion;
import moriyashiine.aylyth.common.advancement.criteria.TameHostileCriterion;
import moriyashiine.aylyth.common.advancement.criteria.YmpeInfestationCriterion;
import net.minecraft.advancement.criterion.Criteria;

public interface AylythCriteria {

    YmpeInfestationCriterion YMPE_INFESTATION = Criteria.register(new YmpeInfestationCriterion());
    ShuckingCriterion SHUCKING = Criteria.register(new ShuckingCriterion());
    TameHostileCriterion TAME_HOSTILE = Criteria.register(new TameHostileCriterion());
    HindPledgeCriterion HIND_PLEDGE = Criteria.register(new HindPledgeCriterion());

    // Load static initializer
    static void register() {}
}
