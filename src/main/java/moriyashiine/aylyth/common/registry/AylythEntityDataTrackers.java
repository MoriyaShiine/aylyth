package moriyashiine.aylyth.common.registry;

import moriyashiine.aylyth.common.entity.ai.BaseAttackType;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.entity.mob.WreathedHindEntity;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public interface AylythEntityDataTrackers {

    TrackedDataHandler<TulpaEntity.ActionState> TULPA_ACTION_STATE = register(TrackedDataHandler.ofEnum(TulpaEntity.ActionState.class));
    TrackedDataHandler<WreathedHindEntity.AttackType> WREATHED_ATTACK_TYPE = register(TrackedDataHandler.ofEnum(WreathedHindEntity.AttackType.class));
    TrackedDataHandler<BaseAttackType> BASE_ATTACK_TYPE = register(TrackedDataHandler.ofEnum(BaseAttackType.class));

    private static <T> TrackedDataHandler<T> register(TrackedDataHandler<T> dataHandler) {
        TrackedDataHandlerRegistry.register(dataHandler);
        return dataHandler;
    }

    static void register() {}
}
