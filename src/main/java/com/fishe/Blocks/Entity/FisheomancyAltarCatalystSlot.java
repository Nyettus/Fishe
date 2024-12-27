package com.fishe.Blocks.Entity;

import com.fishe.Utils.FisheModTags;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class FisheomancyAltarCatalystSlot extends Slot {
    public FisheomancyAltarCatalystSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isIn(FisheModTags.FISHEOMANCY_CATALYST);
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }
}
