package com.fishe.Items;

import com.fishe.Items.materials.FisheArmorMaterials;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;


public class ItemsArmor extends ItemMaster{
    public static void initialize(){

    }
    private static <T extends Item>  T registerArmor(T item, String id) {
        T temp = registerGroup(item,id);
        ItemMaster.ArmorMap.put(id, temp);
        return temp;

    }

    public static final ArmorItem COPPERFISHE_HELMET = registerArmor(new ArmorItem(FisheArmorMaterials.COPPERFISHE, ArmorItem.Type.HELMET,new Item.Settings()),"copperfishe_helmet");
    public static final ArmorItem COPPERFISHE_CHESTPLATE = registerArmor(new ArmorItem(FisheArmorMaterials.COPPERFISHE, ArmorItem.Type.CHESTPLATE,new Item.Settings()),"copperfishe_chestplate");
    public static final ArmorItem COPPERFISHE_LEGGINGS = registerArmor(new ArmorItem(FisheArmorMaterials.COPPERFISHE, ArmorItem.Type.LEGGINGS,new Item.Settings()),"copperfishe_leggings");
    public static final ArmorItem COPPERFISHE_BOOTS = registerArmor(new ArmorItem(FisheArmorMaterials.COPPERFISHE, ArmorItem.Type.BOOTS,new Item.Settings()),"copperfishe_boots");

    public static final ArmorItem IRONFISHE_HELMET = registerArmor(new ArmorItem(FisheArmorMaterials.IRONFISHE, ArmorItem.Type.HELMET,new Item.Settings()),"ironfishe_helmet");
    public static final ArmorItem IRONFISHE_CHESTPLATE = registerArmor(new ArmorItem(FisheArmorMaterials.IRONFISHE, ArmorItem.Type.CHESTPLATE,new Item.Settings()),"ironfishe_chestplate");
    public static final ArmorItem IRONFISHE_LEGGINGS = registerArmor(new ArmorItem(FisheArmorMaterials.IRONFISHE, ArmorItem.Type.LEGGINGS,new Item.Settings()),"ironfishe_leggings");
    public static final ArmorItem IRONFISHE_BOOTS = registerArmor(new ArmorItem(FisheArmorMaterials.IRONFISHE, ArmorItem.Type.BOOTS,new Item.Settings()),"ironfishe_boots");

    public static final ArmorItem GOLDFISHE_HELMET = registerArmor(new ArmorItem(FisheArmorMaterials.GOLDFISHE, ArmorItem.Type.HELMET,new Item.Settings()),"goldfishe_helmet");
    public static final ArmorItem GOLDFISHE_CHESTPLATE = registerArmor(new ArmorItem(FisheArmorMaterials.GOLDFISHE, ArmorItem.Type.CHESTPLATE,new Item.Settings()),"goldfishe_chestplate");
    public static final ArmorItem GOLDFISHE_LEGGINGS = registerArmor(new ArmorItem(FisheArmorMaterials.GOLDFISHE, ArmorItem.Type.LEGGINGS,new Item.Settings()),"goldfishe_leggings");
    public static final ArmorItem GOLDFISHE_BOOTS = registerArmor(new ArmorItem(FisheArmorMaterials.GOLDFISHE, ArmorItem.Type.BOOTS,new Item.Settings()),"goldfishe_boots");

    public static final ArmorItem DIAMONDFISHE_HELMET = registerArmor(new ArmorItem(FisheArmorMaterials.DIAMONDFISHE, ArmorItem.Type.HELMET,new Item.Settings()),"diamondfishe_helmet");
    public static final ArmorItem DIAMONDFISHE_CHESTPLATE = registerArmor(new ArmorItem(FisheArmorMaterials.DIAMONDFISHE, ArmorItem.Type.CHESTPLATE,new Item.Settings()),"diamondfishe_chestplate");
    public static final ArmorItem DIAMONDFISHE_LEGGINGS = registerArmor(new ArmorItem(FisheArmorMaterials.DIAMONDFISHE, ArmorItem.Type.LEGGINGS,new Item.Settings()),"diamondfishe_leggings");
    public static final ArmorItem DIAMONDFISHE_BOOTS = registerArmor(new ArmorItem(FisheArmorMaterials.DIAMONDFISHE, ArmorItem.Type.BOOTS,new Item.Settings()),"diamondfishe_boots");


}
