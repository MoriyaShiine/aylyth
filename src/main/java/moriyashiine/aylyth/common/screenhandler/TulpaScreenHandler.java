package moriyashiine.aylyth.common.screenhandler;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moriyashiine.aylyth.common.entity.mob.TulpaEntity;
import moriyashiine.aylyth.common.registry.ModScreenHandlers;
import moriyashiine.aylyth.mixin.MobEntityAccessor;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
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
import net.minecraft.util.collection.DefaultedList;

public class TulpaScreenHandler extends ScreenHandler {
    private final PlayerEntity player;
    public final TulpaEntity tulpaEntity;
    public final Inventory inventory;
    public final Inventory armorInventory;
    public final Inventory handInventory;
    public final int inventorySize;
    private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER = new EquipmentSlot[] {
            EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST,EquipmentSlot.HEAD
    };


    public TulpaScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory,
                playerInventory.player.getWorld().getEntityById(buf.readVarInt()) instanceof TulpaEntity tulpaEntity2 ? tulpaEntity2 : null);
    }

    public TulpaScreenHandler(int syncId, PlayerInventory playerInventory, TulpaEntity tulpaEntity) {
        this(syncId, playerInventory, tulpaEntity.getInventory(), ((MobEntityAccessor)tulpaEntity).armorItems(), ((MobEntityAccessor)tulpaEntity).handItems(), tulpaEntity);

    }

    public TulpaScreenHandler(int id, PlayerInventory playerInventory, SimpleInventory inventory, DefaultedList<ItemStack> armorItems, DefaultedList<ItemStack> handItems, TulpaEntity tulpaEntity) {
        super(ModScreenHandlers.TULPA_SCREEN_HANDLER, id);
        this.inventory = inventory;
        this.armorInventory = new SimpleInventory(armorItems.toArray(ItemStack[]::new));
        this.handInventory = new SimpleInventory(handItems.toArray(ItemStack[]::new));
        this.inventorySize = inventory.size() + armorInventory.size() + handInventory.size();
        this.player = playerInventory.player;
        tulpaEntity.setInteractTarget(player);
        this.tulpaEntity = tulpaEntity;
        this.inventory.onOpen(player);
        this.armorInventory.onOpen(player);
        this.handInventory.onOpen(player);

        this.addSlot(new Slot(armorInventory, 0, 8, 62) {
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
                tulpaEntity.equipStack(EquipmentSlot.FEET, stack);
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_BOOTS_SLOT_TEXTURE);
            }
        });
        this.addSlot(new Slot(armorInventory, 1, 8, 44) {
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
                tulpaEntity.equipStack(EquipmentSlot.LEGS, stack);
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_LEGGINGS_SLOT_TEXTURE);
            }
        });
        this.addSlot(new Slot(armorInventory, 2, 8, 26) {
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
                tulpaEntity.equipStack(EquipmentSlot.CHEST, stack);
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_CHESTPLATE_SLOT_TEXTURE);
            }
        });
        this.addSlot(new Slot(armorInventory, 3, 8, 9) {
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
                tulpaEntity.equipStack(EquipmentSlot.HEAD, stack);
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_HELMET_SLOT_TEXTURE);
            }
        });

        this.addSlot(new Slot(handInventory, 0, 77, 44) {

            @Override
            public void setStack(ItemStack stack) {
                super.setStack(stack);
                tulpaEntity.equipStack(EquipmentSlot.MAINHAND, stack);
            }
        });

        this.addSlot(new Slot(handInventory, 1, 77, 62) {
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

        for (int y = 0; y < 3; ++y) {
            int yPos = (y * 18) + 18;
            this.addSlot(new Slot(this.inventory, y * 4, 97, yPos));
            this.addSlot(new Slot(this.inventory, y * 4 + 1, 97 + 18, yPos));
            this.addSlot(new Slot(this.inventory, y * 4 + 2, 97 + 2 * 18, yPos));
            this.addSlot(new Slot(this.inventory, y * 4 + 3, 97 + 3 * 18, yPos));
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
            // check if selecting from tulpa inventory and move to player's
            if (index < inventorySize) {
                if (!this.insertItem(itemstack1, inventorySize, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else { // we're moving from player inventory to tulpa's
                // check if can insert into armor slot
                if (canInsertIntoArmor(itemstack1)) {
                    if (!this.insertItem(itemstack1, 0, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } // check main hand
                else if (getSlot(4).canInsert(itemstack1) && insertItem(itemstack1, 4, 5, false)) {
                    return ItemStack.EMPTY;
                } // check is shield and off hand
                else if (itemstack1.isIn(ConventionalItemTags.SHIELDS) && getSlot(5).canInsert(itemstack1) && insertItem(itemstack1, 5, 6, false)) {
                    return ItemStack.EMPTY;
                } else if (this.insertItem(itemstack1, 6, 18, false)) {
                    return ItemStack.EMPTY;
                } else if (this.insertItem(itemstack1, 5, 6, false)) {
                    return ItemStack.EMPTY;
                }
            }


            if (itemstack1.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemstack;
    }

    protected boolean canInsertIntoArmor(ItemStack stack) {
        return  getSlot(0).canInsert(stack) && !getSlot(0).hasStack() ||
                getSlot(1).canInsert(stack) && !getSlot(1).hasStack() ||
                getSlot(2).canInsert(stack) && !getSlot(2).hasStack() ||
                getSlot(3).canInsert(stack) && !getSlot(3).hasStack();
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
        this.handInventory.onClose(player);
    }
}
