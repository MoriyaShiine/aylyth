package moriyashiine.aylyth.common.world;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModWorldState extends PersistentState {
    public final List<UUID> pledgesToRemove = new ArrayList<>();

    public static ModWorldState readNbt(NbtCompound nbt) {
        ModWorldState worldState = new ModWorldState();
        NbtList pledgesToRemoveList = nbt.getList("AylythPledgesToRemove", NbtType.COMPOUND);
        for (int i = 0; i < pledgesToRemoveList.size(); i++) {
            worldState.pledgesToRemove.add(pledgesToRemoveList.getCompound(i).getUuid("UUID"));
        }
        return worldState;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList pledgesToRemoveList = new NbtList();
        for (UUID uuid : this.pledgesToRemove) {
            NbtCompound pledgeCompound = new NbtCompound();
            pledgeCompound.putUuid("UUID", uuid);
            pledgesToRemoveList.add(pledgeCompound);
        }
        nbt.put("AylythPledgesToRemove", pledgesToRemoveList);
        return nbt;
    }

    public static ModWorldState get(World world) {
        return world.getServer().getOverworld().getPersistentStateManager().getOrCreate(ModWorldState::readNbt, ModWorldState::new, "aylyth");
    }
}
