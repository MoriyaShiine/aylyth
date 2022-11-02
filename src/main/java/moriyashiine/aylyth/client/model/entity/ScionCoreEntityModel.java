package moriyashiine.aylyth.client.model.entity;

import moriyashiine.aylyth.common.entity.mob.ScionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AbstractZombieModel;

@Environment(EnvType.CLIENT)
public class ScionCoreEntityModel<T extends ScionEntity> extends AbstractZombieModel<T> {
    public ScionCoreEntityModel(ModelPart modelPart) {
        super(modelPart);
    }

    public boolean isAttacking(T scionEntity) {
        return scionEntity.isAttacking();
    }
}