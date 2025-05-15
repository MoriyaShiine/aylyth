package moriyashiine.aylyth.common.block.entities;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.block.AylythBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class WoodyGrowthCacheBlockEntity extends BlockEntity {

    static final int MAX_SIZE = 9;
    protected final List<ItemStack> inventory = new ObjectArrayList<>();
    protected UUID playerUuid;

    public WoodyGrowthCacheBlockEntity(BlockPos pos, BlockState state) {
        super(AylythBlockEntityTypes.WOODY_GROWTH_CACHE, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        if (!inventory.isEmpty()) {
            NbtList list = new NbtList();
            for (int i = 0; i < inventory.size(); i++) {
                NbtCompound itemNbt = new NbtCompound();
                itemNbt.putInt("slot", i);
                itemNbt.put("item", inventory.get(i).toNbt(registries));
                list.add(itemNbt);
            }
            nbt.put("items", list);
        }
        if (playerUuid != null) {
            nbt.putUuid("player", playerUuid);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        if (nbt.contains("items")) {
            NbtList list = nbt.getList("items", NbtList.COMPOUND_TYPE);
            for (NbtElement ele : list) {
                NbtCompound compound = (NbtCompound) ele;
                int slot = compound.getInt("slot");
                NbtCompound item = compound.getCompound("item");
                inventory.add(slot, ItemStack.fromNbt(registries, item).orElseThrow());
            }
        }
        if (nbt.contains("player")) {
            if (nbt.getType("player") == NbtElement.STRING_TYPE) {
                playerUuid = UUID.fromString(nbt.getString("player"));
            } else {
                playerUuid = nbt.getUuid("player");
            }
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = new NbtCompound();
        writeNbt(nbt, registries);
        return nbt;
    }

    @Nullable
    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public void setPlayerUuid(PlayerEntity player) {
        playerUuid = player.getUuid();
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    public ItemStack getItem(int index) {
        return inventory.get(index);
    }

    public int size() {
        return inventory.size();
    }

    public int fill(Inventory inv, int index) {
        int i = index;
        while (inventory.size() < MAX_SIZE && i < inv.size()) {
            ItemStack stack = inv.removeStack(i++);
            if (!stack.isEmpty()) {
                inventory.add(stack);
            }
        }
        markDirty();
        return i;
    }

    public int fill(List<ItemStack> items, int index) {
        int i = index;
        while (inventory.size() < MAX_SIZE && i < items.size()) {
            ItemStack stack = items.get(i++);
            if (!stack.isEmpty()) {
                inventory.add(stack);
            }
        }
        markDirty();
        return i;
    }

    public void drop(@NotNull World world, @NotNull BlockPos pos) {
        for (ItemStack stack : inventory) {
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
        markDirty();
    }
}
