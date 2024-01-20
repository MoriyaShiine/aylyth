package moriyashiine.aylyth.common.block.entity;

import moriyashiine.aylyth.api.interfaces.VitalHolder;
import moriyashiine.aylyth.common.block.VitalThuribleBlock;
import moriyashiine.aylyth.common.registry.ModBlockEntityTypes;
import moriyashiine.aylyth.common.registry.ModItems;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;


public class VitalThuribleBlockEntity extends BlockEntity implements Inventory {
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public UUID targetUUID = null;
    private int timer = 0;

    public VitalThuribleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.VITAL_THURIBLE_BLOCK_ENTITY, pos, state);
    }


    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbt = super.toInitialChunkDataNbt();
        writeNbt(nbt);
        return nbt;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this, (BlockEntity b) -> this.toNbt());
    }



    public void sync() {
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
            toUpdatePacket();
        }
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        this.timer = nbt.getShort("Timer");

        UUID uUID = null;
        if (nbt.containsUuid("uuid")) {
            uUID = nbt.getUuid("uuid");
        } else {
            String string = nbt.getString("uuid");
            if(this.getWorld() != null){
                uUID = ServerConfigHandler.getPlayerUuidByName(this.getWorld().getServer(), string);
            }
        }

        if (uUID != null) {
            this.targetUUID = uUID;
        }


    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("Timer", (short)this.timer);
        Inventories.writeNbt(nbt, this.inventory);
        if (this.targetUUID != null) {
            nbt.putUuid("uuid", this.targetUUID);
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, VitalThuribleBlockEntity blockEntity) {
        if (world != null) {
            if(blockEntity.inventory.get(0).isOf(ModItems.WRONGMEAT) && blockEntity.inventory.get(0).getCount() == 5){
                blockEntity.timer++;
                if (world.isClient) {
                    if (blockEntity.timer > 0) {
                        for (int i = 0; i < 3; i++) {
                            world.addParticle(ParticleTypes.SOUL, true,
                                    pos.getX() + 0.5 + MathHelper.nextFloat(world.random, -0.2f, 0.2f),
                                    pos.getY() + 0.5 + MathHelper.nextFloat(world.random, -0.2f, 0.2f),
                                    pos.getZ() + 0.5 + MathHelper.nextFloat(world.random, -0.2f, 0.2f),
                                    MathHelper.nextFloat(world.random, -1, 1) / 10,
                                    MathHelper.nextFloat(world.random, 0.125f, 1) / 10,
                                    MathHelper.nextFloat(world.random, -1, 1) / 10);
                        }
                    }
                }
                if(!world.isClient){
                    if(blockEntity.timer > 20 * 2){
                        if(blockEntity.targetUUID != null){
                            PlayerEntity player = world.getPlayerByUuid(blockEntity.targetUUID);

                            VitalHolder.of(player).ifPresent(vital -> {
                                if(vital.getVitalThuribleLevel() < 10){
                                    vital.setVitalThuribleLevel(vital.getVitalThuribleLevel() + 1);
                                }

                            });
                        }

                        blockEntity.inventory.clear();
                        blockEntity.timer = 0;
                        blockEntity.targetUUID = null;
                        blockEntity.sync();
                        markDirty(world, pos, state);
                        world.setBlockState(pos, state.with(VitalThuribleBlock.ACTIVE, false));
                        world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    if(blockEntity.timer > 0){
                        VitalThuribleBlock.activate(world, pos, state);
                    }
                }
            }
        }
    }

    public void onUse(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if(stack.isOf(ModItems.WRONGMEAT) && inventory.get(0).getCount() < 5){
            targetUUID = player.getUuid();
            if(getStack(0).isOf(Items.AIR)){
                setStack(0, new ItemStack(ModItems.WRONGMEAT));
            }else{
                getStack(0).increment(1);
            }
            sync();
            AylythUtil.decreaseStack(stack, player);
        }
    }

    public NbtCompound toNbt(){
        NbtCompound rtn = new NbtCompound();
        rtn.putInt("Timer",  (short)this.timer);
        Inventories.writeNbt(rtn, inventory);
        return rtn;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            if (getStack(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        inventory.clear();
    }


}
