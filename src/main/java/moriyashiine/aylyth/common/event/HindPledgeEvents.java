package moriyashiine.aylyth.common.event;

import moriyashiine.aylyth.api.interfaces.HindPledgeHolder;
import moriyashiine.aylyth.common.data.AylythDamageTypes;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;

public class HindPledgeEvents {
    public static void init() {
        ServerPlayerEvents.COPY_FROM.register(HindPledgeEvents::retainInventoryWhenPledged);
    }

    private static void retainInventoryWhenPledged(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        if (alive || oldPlayer.getRecentDamageSource() == null || !oldPlayer.getRecentDamageSource().isOf(AylythDamageTypes.YMPE)) {
            return;
        }

        if (oldPlayer.server.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            return;
        }

        HindPledgeHolder.of(oldPlayer).ifPresent(hind -> {
            if (hind.getHindUuid() != null) {
                newPlayer.getInventory().clone(oldPlayer.getInventory());
            }
            hind.setHindUuid(null);
        });
    }
}
