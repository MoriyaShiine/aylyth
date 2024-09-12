package moriyashiine.aylyth.common.attachment;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import moriyashiine.aylyth.common.entity.type.mob.WreathedHindEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Uuids;

import java.util.UUID;

public class PledgeState {
    public static final Codec<PledgeState> CODEC = Codec.unboundedMap(Uuids.CODEC, Uuids.CODEC)
            .xmap(map -> new PledgeState(HashBiMap.create(map)), state -> state.pledgeData);
    // player uuid -> Pledgeable uuid
    private final BiMap<UUID, UUID> pledgeData;

    private PledgeState(BiMap<UUID, UUID> map) {
        pledgeData = map;
    }

    public PledgeState() {
        this(HashBiMap.create());
    }

    public void addPledge(UUID player, UUID pledgeable) {
        pledgeData.put(player, pledgeable);
    }

    public UUID getPledge(PlayerEntity player) {
        return pledgeData.get(player.getUuid());
    }

    public UUID getPledged(WreathedHindEntity pledgeable) {
        return pledgeData.inverse().get(pledgeable.getUuid());
    }

    public void removePledge(PlayerEntity player) {
        pledgeData.remove(player.getUuid());
    }

    public void removePledge(WreathedHindEntity pledgeable) {
        pledgeData.inverse().remove(pledgeable.getUuid());
    }

    public boolean isPlayerPledged(PlayerEntity player) {
        return pledgeData.containsKey(player.getUuid());
    }
}
