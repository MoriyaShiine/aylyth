package moriyashiine.aylyth.common.world;

import moriyashiine.aylyth.api.interfaces.Vital;
import moriyashiine.aylyth.common.Aylyth;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ModWorldState extends PersistentState {
    public static final Map<UUID, BlockPos> vital_thurible = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        NbtList vitalList = new NbtList();
        vital_thurible.forEach(((uuid, blockPos) -> {
            NbtCompound vitalTag = new NbtCompound();
            vitalTag.putUuid("Player", uuid);
            vitalTag.put("Pos", NbtHelper.fromBlockPos(blockPos));
            vitalList.add(vitalTag);
        }));
        tag.put("VitalTurible", vitalList);
        return tag;
    }

    public void addVitalTurible(PlayerEntity player, BlockPos pos){
        vital_thurible.put(player.getUuid(), pos);
        Vital.of(player).ifPresent(vital -> vital.setVital(true));
        markDirty();
    }

    public void removeVitalTurible(PlayerEntity player){
        vital_thurible.remove(player.getUuid());
        Vital.of(player).ifPresent(vital -> vital.setVital(false));
        markDirty();
    }

    public static ModWorldState readNbt(NbtCompound tag) {
        ModWorldState worldState = new ModWorldState();
        NbtList vitalList = tag.getList("VitalTurible", 10);
        for (NbtElement nbt : vitalList) {
            NbtCompound vitalTag = (NbtCompound) nbt;
            vital_thurible.put(vitalTag.getUuid("Player"), NbtHelper.toBlockPos(vitalTag.getCompound("Pos")));
        }
        return worldState;
    }

    public static ModWorldState get(World world) {
        return ((ServerWorld) world).getPersistentStateManager().getOrCreate(ModWorldState::readNbt, ModWorldState::new, Aylyth.MOD_ID + "_universal");
    }
}
