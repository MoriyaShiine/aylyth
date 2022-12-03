package moriyashiine.aylyth.common.block.entity;

import com.google.common.collect.Lists;
import moriyashiine.aylyth.common.registry.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WoodyGrowthCacheBlockEntity extends BlockEntity {

    static final int MAX_SIZE = 9;
    protected final List<ItemStack> inventory = Lists.newLinkedList();

    public WoodyGrowthCacheBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.WOODY_GROWTH_CACHE_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!inventory.isEmpty()) {
            var list = new NbtList();
            for (int i = 0; i < inventory.size(); i++) {
                var itemNbt = new NbtCompound();
                itemNbt.putInt("Slot", i);
                itemNbt.put("Item", inventory.get(i).writeNbt(new NbtCompound()));
                list.add(itemNbt);
            }
            nbt.put("Items", list);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("Items")) {
            var list = nbt.getList("Items", NbtList.COMPOUND_TYPE);
            for (NbtElement ele : list) {
                var compound = (NbtCompound) ele;
                var slot = compound.getInt("Slot");
                var item = (NbtCompound)compound.get("Item");
                inventory.add(slot, ItemStack.fromNbt(item));
            }
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        var nbt = new NbtCompound();
        writeNbt(nbt);
        return nbt;
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
            var stack = inv.removeStack(i++);
            if (!stack.isEmpty()) {
                inventory.add(stack);
            }
        }
        markDirty();
        return i;
    }

    public int fill(List<ItemStack> items, int index) {
        int i = 0;
        while (inventory.size() < MAX_SIZE && i < items.size()) {
            var stack = items.get(i++);
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