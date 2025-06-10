package moriyashiine.aylyth.common.entity;

import moriyashiine.aylyth.common.entity.ai.BasicAttackType;
import moriyashiine.aylyth.common.entity.types.mob.BoneflyEntity;
import moriyashiine.aylyth.common.entity.types.mob.PilotLightEntity;
import moriyashiine.aylyth.common.entity.types.mob.TulpaEntity;
import moriyashiine.aylyth.common.entity.types.mob.WreathedHindEntity;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public interface AylythTrackedDataHandlers {

    TrackedDataHandler<TulpaEntity.ActionState> TULPA_ACTION_STATE = register(TrackedDataHandler.create(TulpaEntity.ActionState.PACKET_CODEC));
    TrackedDataHandler<WreathedHindEntity.AttackType> WREATHED_ATTACK_TYPE = register(TrackedDataHandler.create(WreathedHindEntity.AttackType.PACKET_CODEC));
    TrackedDataHandler<BasicAttackType> BASIC_ATTACK_TYPE = register(TrackedDataHandler.create(BasicAttackType.PACKET_CODEC));
    TrackedDataHandler<PilotLightEntity.Color> PILOT_LIGHT_COLOR = register(TrackedDataHandler.create(PilotLightEntity.Color.PACKET_CODEC));
    TrackedDataHandler<BoneflyEntity.ActionState> BONEFLY_ACTION_STATE = register(TrackedDataHandler.create(BoneflyEntity.ActionState.PACKET_CODEC));

    private static <T> TrackedDataHandler<T> register(TrackedDataHandler<T> dataHandler) {
        TrackedDataHandlerRegistry.register(dataHandler);
        return dataHandler;
    }

    static void register() {}
}
