package com.fishe.Blocks.Entity;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class FisheomancyAlterBasicSlot extends Slot {
    public FisheomancyAlterBasicSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }
}
