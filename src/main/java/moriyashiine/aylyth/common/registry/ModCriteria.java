package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.advancement.criteria.HindPledgeCriterion;
import moriyashiine.aylyth.common.advancement.criteria.ShuckingCriterion;
import moriyashiine.aylyth.common.advancement.criteria.TameHostileCriterion;
import moriyashiine.aylyth.common.advancement.criteria.YmpeInfestationCriterion;
import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
    public static void init() {}

    public static final YmpeInfestationCriterion YMPE_INFESTATION = Criteria.register(new YmpeInfestationCriterion());
    public static final ShuckingCriterion SHUCKING = Criteria.register(new ShuckingCriterion());
    public static final TameHostileCriterion TAME_HOSTILE = Criteria.register(new TameHostileCriterion());
    public static final HindPledgeCriterion HIND_PLEDGE = Criteria.register(new HindPledgeCriterion());
}
