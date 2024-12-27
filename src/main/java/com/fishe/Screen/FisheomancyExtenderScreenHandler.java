package com.fishe.Screen;

import com.fishe.Blocks.Entity.FisheomancyAltarBasicSlot;
import com.fishe.Blocks.Entity.FisheomancyExtenderBlockEntity;
import com.fishe.Fishe;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class FisheomancyExtenderScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final FisheomancyExtenderBlockEntity blockEntity;

    public FisheomancyExtenderScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId,inventory,inventory.player.getWorld().getBlockEntity(buf.readBlockPos()));
    }

    public FisheomancyExtenderScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        super(ModScreenHandler.FISHEOMANCY_EXPANDER_SCREEN_HANDLER_SCREEN_HANDLER,syncId);
        checkSize((Inventory) blockEntity,3);
        this.inventory = (Inventory) blockEntity;
        this.blockEntity = (FisheomancyExtenderBlockEntity) blockEntity;

        this.addSlot(new FisheomancyAltarBasicSlot(inventory,0,44,47));
        this.addSlot(new FisheomancyAltarBasicSlot(inventory,1,80,26));
        this.addSlot(new FisheomancyAltarBasicSlot(inventory,2,116,47));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
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
            } else {
                int firstAvailableSlot = invSlot;
                for(int i =0; i<this.inventory.size();i++){
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

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
