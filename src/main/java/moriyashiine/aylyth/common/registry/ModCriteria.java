package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.criteria.ShuckingCriterion;
import moriyashiine.aylyth.common.criteria.YmpeInfestationCriterion;
import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {
    public static void init() {}

    public static final YmpeInfestationCriterion YMPE_INFESTATION = Criteria.register(new YmpeInfestationCriterion());
    public static final ShuckingCriterion SHUCKING = Criteria.register(new ShuckingCriterion());
}
