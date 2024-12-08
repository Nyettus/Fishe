package com.fishe.Blocks.Entity;

import com.fishe.Items.ItemsFishe;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class FisheRepairSlopSlot extends Slot {
    public FisheRepairSlopSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        boolean canInsert = super.canInsert(stack);
        boolean isSlop = stack.getItem()== ItemsFishe.FISHE_PASTE;
        return canInsert && isSlop;
    }
}
