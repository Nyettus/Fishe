package com.fishe.enchantments;

import com.fishe.ModFishingRodItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class NightEnchantment extends Enchantment {
    protected NightEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.FISHING_ROD, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level){
        return 14 + (level - 1) * 9;
    }

    @Override
    public int getMaxPower(int level){
        return super.getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel(){
        return 2;
    }

}
