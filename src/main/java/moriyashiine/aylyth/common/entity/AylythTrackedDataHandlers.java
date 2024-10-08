package moriyashiine.aylyth.common.entity;

import moriyashiine.aylyth.common.entity.ai.BasicAttackType;
import moriyashiine.aylyth.common.entity.types.mob.PilotLightEntity;
import moriyashiine.aylyth.common.entity.types.mob.TulpaEntity;
import moriyashiine.aylyth.common.entity.types.mob.WreathedHindEntity;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public interface AylythTrackedDataHandlers {

    TrackedDataHandler<TulpaEntity.ActionState> TULPA_ACTION_STATE = register(TrackedDataHandler.ofEnum(TulpaEntity.ActionState.class));
    TrackedDataHandler<WreathedHindEntity.AttackType> WREATHED_ATTACK_TYPE = register(TrackedDataHandler.ofEnum(WreathedHindEntity.AttackType.class));
    TrackedDataHandler<BasicAttackType> BASIC_ATTACK_TYPE = register(TrackedDataHandler.ofEnum(BasicAttackType.class));
    TrackedDataHandler<PilotLightEntity.Color> PILOT_LIGHT_COLOR = register(TrackedDataHandler.ofEnum(PilotLightEntity.Color.class));

    private static <T> TrackedDataHandler<T> register(TrackedDataHandler<T> dataHandler) {
        TrackedDataHandlerRegistry.register(dataHandler);
        return dataHandler;
    }

    static void register() {}
}
