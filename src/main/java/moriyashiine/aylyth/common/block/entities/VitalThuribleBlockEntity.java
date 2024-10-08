package moriyashiine.aylyth.common.block.entities;

import moriyashiine.aylyth.api.interfaces.VitalHealthHolder;
import moriyashiine.aylyth.common.block.AylythBlockEntityTypes;
import moriyashiine.aylyth.common.block.types.VitalThuribleBlock;
import moriyashiine.aylyth.common.entity.AylythAttributes;
import moriyashiine.aylyth.common.item.AylythItems;
import moriyashiine.aylyth.common.particle.effects.ColorableParticleEffect;
import moriyashiine.aylyth.common.util.AylythUtil;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class VitalThuribleBlockEntity extends BlockEntity implements SingleStackInventory {
    public static final int VITAL_INCREMENT = 2;
    public static final int MAX_VITAL_MODIFIER = 20;

    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public UUID targetUUID = null;
    private int timer = 0;

    public VitalThuribleBlockEntity(BlockPos pos, BlockState state) {
        super(AylythBlockEntityTypes.VITAL_THURIBLE, pos, state);
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
            markDirty();
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
            if (this.getWorld() != null) {
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
        if (blockEntity.getStack().isOf(AylythItems.WRONGMEAT) && blockEntity.getStack().getCount() == 5) {
            blockEntity.timer++;
            if (world.isClient) {
                if (blockEntity.timer > 0) {
                    for (int i = 0; i < 3; i++) {
                        world.addParticle(ColorableParticleEffect.SOUL_EMBER, true,
                                pos.getX() + 0.5 + MathHelper.nextFloat(world.random, -0.2f, 0.2f),
                                pos.getY() + 0.5 + MathHelper.nextFloat(world.random, -0.2f, 0.2f),
                                pos.getZ() + 0.5 + MathHelper.nextFloat(world.random, -0.2f, 0.2f),
                                MathHelper.nextFloat(world.random, -1, 1) / 10,
                                MathHelper.nextFloat(world.random, 0.125f, 1) / 10,
                                MathHelper.nextFloat(world.random, -1, 1) / 10);
                    }
                }
            }
            if (!world.isClient) {
                if (blockEntity.timer > SharedConstants.TICKS_PER_SECOND * 2) {
                    if (blockEntity.targetUUID != null) {
                        PlayerEntity player = world.getPlayerByUuid(blockEntity.targetUUID);
                        EntityAttributeInstance instance = player.getAttributeInstance(AylythAttributes.MAX_VITAL_HEALTH);
                        if (instance != null) {
                            EntityAttributeModifier modifier = instance.getModifier(VitalThuribleBlock.MAX_VITAL_MODIFIER);
                            double currentMax = modifier != null ? modifier.getValue() : 0;
                            if (currentMax < MAX_VITAL_MODIFIER) {
                                double newMax = Math.min(currentMax + VITAL_INCREMENT, MAX_VITAL_MODIFIER);
                                instance.removeModifier(VitalThuribleBlock.MAX_VITAL_MODIFIER);
                                instance.addPersistentModifier(new EntityAttributeModifier(VitalThuribleBlock.MAX_VITAL_MODIFIER, "Vital Thurible Buff", newMax, EntityAttributeModifier.Operation.ADDITION));
                                VitalHealthHolder healthHolder = VitalHealthHolder.find(player);
                                healthHolder.setCurrentVitalHealth(healthHolder.getCurrentVitalHealth() + VITAL_INCREMENT);
                            }
                        }
                    }

                    blockEntity.clear();
                    blockEntity.timer = 0;
                    blockEntity.targetUUID = null;
                    blockEntity.sync();
                    world.setBlockState(pos, state.with(VitalThuribleBlock.ACTIVE, false));
                    world.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
                if (blockEntity.timer > 0) {
                    VitalThuribleBlock.activate(world, pos, state);
                }
            }
        }
    }

    public void onUse(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if ((!hasOwner() || isOwner(player)) && VitalThuribleBlock.isActivateItem(stack) && getStack().getCount() < 5) {
            targetUUID = player.getUuid();
            if (getStack().isEmpty()) {
                setStack(stack.copy().split(1));
            } else {
                getStack().increment(1);
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

    public boolean hasOwner() {
        return targetUUID != null;
    }

    public boolean isOwner(PlayerEntity player) {
        return player.getUuid().equals(targetUUID);
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(0);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(inventory, 0, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, 0);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(0, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}
