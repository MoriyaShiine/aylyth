package moriyashiine.aylyth.common.world;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ModWorldState extends PersistentState {
    public static final Codec<Set<UUID>> CODEC = Uuids.CODEC.listOf().fieldOf("AylythPledgesToRemove").xmap(
            uuids -> (Set<UUID>)new ObjectLinkedOpenHashSet<>(uuids),
            ArrayList::new
    ).codec();

    private final Set<UUID> pledgesToRemove;

    public ModWorldState() {
        this(new HashSet<>());
    }

    ModWorldState(Set<UUID> pledgesToRemove) {
        this.pledgesToRemove = pledgesToRemove;
    }

    public boolean hasPledge(UUID uuid) {
        return pledgesToRemove.contains(uuid);
    }

    public boolean hasPledgesToRemove() {
        return !pledgesToRemove.isEmpty();
    }

    public void addPledgeToRemove(UUID uuid) {
        pledgesToRemove.add(uuid);
        markDirty();
    }

    public boolean removePledge(UUID uuid) {
        boolean removed = pledgesToRemove.remove(uuid);
        if (removed) {
            markDirty();
        }
        return removed;
    }

    public static ModWorldState readNbt(NbtCompound nbt) {
        return CODEC.parse(NbtOps.INSTANCE, nbt).map(ModWorldState::new).getOrThrow(false, s -> {});
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        return (NbtCompound) CODEC.encode(pledgesToRemove, NbtOps.INSTANCE, nbt).getOrThrow(false, s -> {});
    }

    public static ModWorldState get(World world) {
        return world.getServer().getOverworld().getPersistentStateManager().getOrCreate(ModWorldState::readNbt, ModWorldState::new, "aylyth");
    }
}
