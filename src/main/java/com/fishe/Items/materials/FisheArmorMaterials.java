package com.fishe.Items.materials;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public enum FisheArmorMaterials implements ArmorMaterial {
    COPPERFISHE(10,15,2,3,4,0,0,"copperfishe"),
    IRONFISHE(14,11,2,5,5,0,0,"ironfishe"),
    GOLDFISHE(5,30,2,2,4,0,0,"goldfishe"),
    DIAMONDFISHE(30,13,3,6,8,2,0.2f,"diamondfishe");

    private final int DURABILITY_MULTIPLIER;
    private final int ENCHANTABILITY;
    private final int  BOOTS_HELM_PROT;
    private final int  LEGGINGS_PROT;
    private final int  CHEST_PROT;
    private final int TOUGHNESS;
    private final float KNOCKBACK_RESIST;
    private final String NAME;
    private FisheArmorMaterials(int DURABILITY_MULTIPLIER,int ENCHANTABILITY,int BOOTS_HELM_PROT,int LEGGINGS_PROT,int CHEST_PROT,int TOUGHNESS, float KNOCKBACK_RESIST, String NAME){
        this.DURABILITY_MULTIPLIER = DURABILITY_MULTIPLIER;
        this.ENCHANTABILITY = ENCHANTABILITY;
        this.BOOTS_HELM_PROT = BOOTS_HELM_PROT;
        this.LEGGINGS_PROT = LEGGINGS_PROT;
        this.CHEST_PROT = CHEST_PROT;
        this.TOUGHNESS = TOUGHNESS;
        this.KNOCKBACK_RESIST = KNOCKBACK_RESIST;
        this.NAME = NAME;
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return switch (type){
            case BOOTS ->  13 * DURABILITY_MULTIPLIER;
            case LEGGINGS -> 15 * DURABILITY_MULTIPLIER;
            case CHESTPLATE -> 16 * DURABILITY_MULTIPLIER;
            case HELMET -> 11 * DURABILITY_MULTIPLIER;
            default -> 0;
        };
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return switch (type){
            case BOOTS,HELMET  -> BOOTS_HELM_PROT;
            case LEGGINGS -> LEGGINGS_PROT;
            case CHESTPLATE -> CHEST_PROT;
        };
    }

    @Override
    public int getEnchantability() {
        return ENCHANTABILITY;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public float getToughness() {
        return TOUGHNESS;
    }

    @Override
    public float getKnockbackResistance() {
        return KNOCKBACK_RESIST;
    }
}
