package com.fishe.Items.materials;

import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public enum FisheToolMaterials implements ToolMaterial {
    COPPERFISHE(MiningLevels.STONE,99,5,1.0f,5),
    IRONFISHE(MiningLevels.IRON,188,7,2,10),
    GOLDFISHE(MiningLevels.STONE,40,14,0,22),
    DIAMONDFISHE(MiningLevels.DIAMOND,1170,10,3,10);


    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private FisheToolMaterials(int miningLevel,int itemDurability, float miningSpeed, float attackDamage, int enchantability){
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
    }


    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
}
