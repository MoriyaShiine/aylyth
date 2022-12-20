package moriyashiine.aylyth.api.interfaces;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.Collection;
import java.util.UUID;

public interface Pledgeable {
    Collection<UUID> getPledgedPlayerUUIDs();

    default void fromNbtPledgeable(NbtCompound tag) {
        NbtList pledgedPlayerUUIDsList = tag.getList("AylythPledgedPlayerUUIDs", NbtType.COMPOUND);
        for (int i = 0; i < pledgedPlayerUUIDsList.size(); i++) {
            getPledgedPlayerUUIDs().add(pledgedPlayerUUIDsList.getCompound(i).getUuid("UUID"));
        }
    }

    default void toNbtPledgeable(NbtCompound tag) {
        NbtList pledgedPlayerUUIDsList = new NbtList();
        for (UUID uuid : getPledgedPlayerUUIDs()) {
            NbtCompound pledgedPlayerUUIDCompound = new NbtCompound();
            pledgedPlayerUUIDCompound.putUuid("UUID", uuid);
            pledgedPlayerUUIDsList.add(pledgedPlayerUUIDCompound);
        }
        tag.put("AylythPledgedPlayerUUIDs", pledgedPlayerUUIDsList);
    }
}
