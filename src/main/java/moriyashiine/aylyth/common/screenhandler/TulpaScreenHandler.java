package moriyashiine.aylyth.common.screenhandler;

import com.mojang.datafixers.util.Pair;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.registry.ModScreenHandlers;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

public class TulpaScreenHandler extends ScreenHandler {
    private final PlayerEntity player;
    public final TulpaEntity tulpaEntity;
    public final Inventory inventory;
    public final Inventory armorInventory;
    private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER = new EquipmentSlot[]{
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};


    public TulpaScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory,
                playerInventory.player.getWorld().getEntityById(buf.readVarInt()) instanceof TulpaEntity tulpaEntity2 ? tulpaEntity2 : null);
    }

    public TulpaScreenHandler(int syncId, PlayerInventory playerInventory, TulpaEntity tulpaEntity) {
        this(syncId, playerInventory, tulpaEntity.getInventory(), tulpaEntity.armorInventory, tulpaEntity);

    }

    public TulpaScreenHandler(int id, PlayerInventory playerInventory, SimpleInventory inventory, SimpleInventory armorInventory, TulpaEntity tulpaEntity) {
        super(ModScreenHandlers.TULPA_SCREEN_HANDLER, id);
        this.inventory = inventory;
        this.armorInventory = armorInventory;
        this.player = playerInventory.player;
        tulpaEntity.setInteractTarget(player);
        this.tulpaEntity = tulpaEntity;
        inventory.onOpen(playerInventory.player);
        armorInventory.onOpen(playerInventory.player);
        this.addSlot(new Slot(armorInventory, 0, 8, 9) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return EQUIPMENT_SLOT_ORDER[0] == MobEntity.getPreferredEquipmentSlot(stack);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);
                tulpaEntity.equipStack(EquipmentSlot.HEAD, stack);
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_HELMET_SLOT_TEXTURE);
            }
        });
        this.addSlot(new Slot(armorInventory, 1, 8, 26) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return EQUIPMENT_SLOT_ORDER[1] == MobEntity.getPreferredEquipmentSlot(stack);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);
                tulpaEntity.equipStack(EquipmentSlot.CHEST, stack);
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_CHESTPLATE_SLOT_TEXTURE);
            }
        });
        this.addSlot(new Slot(armorInventory, 2, 8, 44) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return EQUIPMENT_SLOT_ORDER[2] == MobEntity.getPreferredEquipmentSlot(stack);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);
                tulpaEntity.equipStack(EquipmentSlot.LEGS, stack);
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_LEGGINGS_SLOT_TEXTURE);
            }
        });
        this.addSlot(new Slot(armorInventory, 3, 8, 62) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return EQUIPMENT_SLOT_ORDER[3] == MobEntity.getPreferredEquipmentSlot(stack);
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);
                tulpaEntity.equipStack(EquipmentSlot.FEET, stack);
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_BOOTS_SLOT_TEXTURE);
            }
        });

        this.addSlot(new Slot(inventory, 0, 77, 44) {

            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);
                tulpaEntity.equipStack(EquipmentSlot.MAINHAND, stack);
            }
        });

        this.addSlot(new Slot(inventory, 1, 77, 62) {
            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);
                tulpaEntity.equipStack(EquipmentSlot.OFFHAND, stack);
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT);
            }
        });


        for (int x = 0; x < 4; ++x) {
            this.addSlot(new Slot(inventory, (x + 2), 97 + x * 18, 18));
            this.addSlot(new Slot(inventory, (x + 3) + (3), 97 + x * 18, 18 + 18));
            this.addSlot(new Slot(inventory, (x + 4) + (2 * 3), 97 + x * 18, 18 + 2 * 18));
        }


        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            int i = this.inventory.size();
            if (index < i) {
                if (!this.insertItem(itemstack1, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).canInsert(itemstack1) && !this.getSlot(1).hasStack()) {
                if (!this.insertItem(itemstack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).canInsert(itemstack1)) {
                if (!this.insertItem(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 2 || !this.insertItem(itemstack1, 2, i, false)) {
                int j = i + 27;
                int k = j + 9;
                if (index >= j && index < k) {
                    if (!this.insertItem(itemstack1, i, j, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= i && index < j) {
                    if (!this.insertItem(itemstack1, j, k, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.insertItem(itemstack1, j, j, false)) {
                    return ItemStack.EMPTY;
                }

                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.tulpaEntity.setInteractTarget(null);
        this.inventory.onClose(player);
        this.armorInventory.onClose(player);
    }
}
