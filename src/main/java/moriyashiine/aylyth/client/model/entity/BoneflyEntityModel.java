package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.mob.BoneflyEntity;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class BoneflyEntityModel extends DefaultedEntityGeoModel<BoneflyEntity> {
    public BoneflyEntityModel() {
        super(AylythUtil.id("living/bonefly"));
    }
}
