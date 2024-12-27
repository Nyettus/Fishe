package com.fishe.Screen;

import com.fishe.Blocks.BlockItems;
import com.fishe.Blocks.Entity.FisheRepairSlopSlot;
import com.fishe.Blocks.Entity.FisheomancyAltarBasicSlot;
import com.fishe.Blocks.Entity.FisheomancyAltarBlockEntity;
import com.fishe.Blocks.Entity.FisheomancyAltarCatalystSlot;
import com.fishe.Items.ItemsFishe;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class FisheomancyAltarScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final FisheomancyAltarBlockEntity blockEntity;

    public FisheomancyAltarScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(4));
    }

    public FisheomancyAltarScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandler.FISHEOMANCY_ALTAR_SCREEN_HANDLER_SCREEN_HANDLER, syncId);
        checkSize((Inventory) blockEntity, 8);
        this.inventory = (Inventory) blockEntity;
        playerInventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = (FisheomancyAltarBlockEntity) blockEntity;

        this.addSlot(new FisheRepairSlopSlot(inventory, 0, 26, 39));
        this.addSlot(new FisheomancyAltarCatalystSlot(inventory, 1, 98, 39));
        this.addSlot(new FisheomancyAltarBasicSlot(inventory, 2, 88, 19));
        this.addSlot(new FisheomancyAltarBasicSlot(inventory, 3, 108, 19));
        this.addSlot(new FisheomancyAltarBasicSlot(inventory, 4, 78, 39));
        this.addSlot(new FisheomancyAltarBasicSlot(inventory, 5, 118, 39));
        this.addSlot(new FisheomancyAltarBasicSlot(inventory, 6, 88, 59));
        this.addSlot(new FisheomancyAltarBasicSlot(inventory, 7, 108, 59));
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addProperties(arrayPropertyDelegate);
    }


    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }else if(originalStack.isOf(ItemsFishe.FISHE_PASTE)||originalStack.isOf(BlockItems.FISHE_PASTE_BLOCK)){
                if (!this.insertItem(originalStack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else {
                int firstAvailableSlot = invSlot;
                for(int i =1; i<this.inventory.size();i++){
                    if (!(this.slots.get(i).hasStack()||!this.slots.get(i).canInsert(originalStack))){
                        firstAvailableSlot = i;
                        break;
                    }
                }
                if(firstAvailableSlot==invSlot) return ItemStack.EMPTY;;


                ItemStack toDecrease = originalStack.copyWithCount(1);
                originalStack.decrement(1);
                this.slots.get(firstAvailableSlot).setStack(toDecrease);
                return ItemStack.EMPTY;
            }


            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, originalStack);

        }

        return newStack;
    }
//    @Override
//    public ItemStack quickMove(PlayerEntity player, int slot) {
//        ItemStack itemStack = ItemStack.EMPTY;
//        Slot slot2 = this.slots.get(slot);
//        if (slot2 != null && slot2.hasStack()) {
//            ItemStack itemStack2 = slot2.getStack();
//            itemStack = itemStack2.copy();
//            if (slot == 0) {
//                if (!this.insertItem(itemStack2, 2, 38, true)) {
//                    return ItemStack.EMPTY;
//                }
//            } else if (slot == 1) {
//                if (!this.insertItem(itemStack2, 2, 38, true)) {
//                    return ItemStack.EMPTY;
//                }
//            } else if (itemStack2.isOf(Items.LAPIS_LAZULI)) {
//                if (!this.insertItem(itemStack2, 1, 2, true)) {
//                    return ItemStack.EMPTY;
//                }
//            } else {
//                if (this.slots.get(0).hasStack() || !this.slots.get(0).canInsert(itemStack2)) {
//                    return ItemStack.EMPTY;
//                }
//
//                ItemStack toDecrease = itemStack2.copyWithCount(1);
//                itemStack2.decrement(1);
//                this.slots.get(0).setStack(toDecrease);
//            }
//
//            if (itemStack2.isEmpty()) {
//                slot2.setStack(ItemStack.EMPTY);
//            } else {
//                slot2.markDirty();
//            }
//
//            if (itemStack2.getCount() == itemStack.getCount()) {
//                return ItemStack.EMPTY;
//            }
//
//            slot2.onTakeItem(player, itemStack2);
//        }
//
//        return itemStack;
//    }


    public int getSlopProgress() {
        int slop = this.propertyDelegate.get(0);
        int maxSlop = this.propertyDelegate.get(1);
        int progressBarSize = 52;

        return slop * progressBarSize / maxSlop;
    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
