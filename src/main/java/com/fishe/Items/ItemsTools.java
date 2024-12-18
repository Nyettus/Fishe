package com.fishe.Items;

import com.fishe.Items.materials.FisheToolMaterials;
import com.fishe.Utils.RodTiers;
import net.minecraft.item.*;

public class ItemsTools extends ItemMaster{
    public static void initialize(){

    }
    private static <T extends Item>  T registerTool(T item, String id) {
        T temp = registerGroup(item,id);
        ItemMaster.ToolMap.put(id, temp);
        return temp;

    }

    public static enum ToolType {Shovel,Pickaxe,Axe,Hoe,Sword}


    public static final ModFishingRodItem COPPER_ROD = registerGroup(new ModFishingRodItem(new Item.Settings()
            .maxDamage(64), RodTiers.RodTier.COPPER), "copper_rod");

    public static final ModFishingRodItem IRON_ROD = registerGroup(new ModFishingRodItem(new Item.Settings()
            .maxDamage(64*2), RodTiers.RodTier.IRON), "iron_rod");

    public static final ModFishingRodItem GOLD_ROD = registerGroup(new ModFishingRodItem(new Item.Settings()
            .maxDamage(64), RodTiers.RodTier.GOLD), "gold_rod");

    public static final ModFishingRodItem DIAMOND_ROD = registerGroup(new ModFishingRodItem(new Item.Settings()
            .maxDamage(64*12), RodTiers.RodTier.DIAMOND), "diamond_rod");

    //Pickaxe items
    private static final int pickaxeAttackDamage = 1;
    private static final float pickaxeAttackSpeed = -2.8f;

    public static final PickaxeItem COPPERFISHE_PICKAXE = registerTool(
            new PickaxeItem(FisheToolMaterials.COPPERFISHE,pickaxeAttackDamage,pickaxeAttackSpeed,new Item.Settings()),
            "copperfishe_pickaxe");

    public static final PickaxeItem IRONFISHE_PICKAXE = registerTool(
            new PickaxeItem(FisheToolMaterials.IRONFISHE,pickaxeAttackDamage,pickaxeAttackSpeed,new Item.Settings()),
            "ironfishe_pickaxe");

    public static final PickaxeItem GOLDFISHE_PICKAXE = registerTool(
            new PickaxeItem(FisheToolMaterials.GOLDFISHE,pickaxeAttackDamage,pickaxeAttackSpeed,new Item.Settings()),
            "goldfishe_pickaxe");

    public static final PickaxeItem DIAMONDFISHE_PICKAXE = registerTool(
            new PickaxeItem(FisheToolMaterials.DIAMONDFISHE,pickaxeAttackDamage,pickaxeAttackSpeed,new Item.Settings()),
            "diamondfishe_pickaxe");


    //Axe items
    private static final float axeAttackDamage = 6f;
    private static final float axeAttackSpeed = -3.0f;

    public static final AxeItem COPPERFISHE_AXE = registerTool(
            new AxeItem(FisheToolMaterials.COPPERFISHE,axeAttackDamage,axeAttackSpeed,new Item.Settings()),
            "copperfishe_axe");

    public static final AxeItem IRONFISHE_AXE = registerTool(
            new AxeItem(FisheToolMaterials.IRONFISHE,axeAttackDamage+1,axeAttackSpeed,new Item.Settings()),
            "ironfishe_axe");

    public static final AxeItem GOLDFISHE_AXE = registerTool(
            new AxeItem(FisheToolMaterials.GOLDFISHE,axeAttackDamage,axeAttackSpeed,new Item.Settings()),
            "goldfishe_axe");

    public static final AxeItem DIAMONDFISHE_AXE = registerTool(
            new AxeItem(FisheToolMaterials.DIAMONDFISHE,axeAttackDamage+1.5f,axeAttackSpeed,new Item.Settings()),
            "diamondfishe_axe");

    //Shovel
    private static final float shovelAttackDamage = 1.5f;
    private static final float shovelAttackSpeed = -3.0f;

    public static final ShovelItem COPPERFISHE_SHOVEL = registerTool(
            new ShovelItem(FisheToolMaterials.COPPERFISHE,shovelAttackDamage,shovelAttackSpeed,new Item.Settings()),
            "copperfishe_shovel");

    public static final ShovelItem IRONFISHE_SHOVEL = registerTool(
            new ShovelItem(FisheToolMaterials.IRONFISHE,shovelAttackDamage,shovelAttackSpeed,new Item.Settings()),
            "ironfishe_shovel");

    public static final ShovelItem GOLDFISHE_SHOVEL = registerTool(
            new ShovelItem(FisheToolMaterials.GOLDFISHE,shovelAttackDamage,shovelAttackSpeed,new Item.Settings()),
            "goldfishe_shovel");

    public static final ShovelItem DIAMONDFISHE_SHOVEL = registerTool(
            new ShovelItem(FisheToolMaterials.DIAMONDFISHE,shovelAttackDamage,shovelAttackSpeed,new Item.Settings()),
            "diamondfishe_shovel");

    //Hoe

    public static final HoeItem COPPERFISHE_HOE = registerTool(
            new HoeItem(FisheToolMaterials.COPPERFISHE,-1,0,new Item.Settings()),
            "copperfishe_hoe");

    public static final HoeItem IRONFISHE_HOE = registerTool(
            new HoeItem(FisheToolMaterials.IRONFISHE,-2,-2,new Item.Settings()),
            "ironfishe_hoe");

    public static final HoeItem GOLDFISHE_HOE = registerTool(
            new HoeItem(FisheToolMaterials.GOLDFISHE,0,-2,new Item.Settings()),
            "goldfishe_hoe");

    public static final HoeItem DIAMONDFISHE_HOE = registerTool(
            new HoeItem(FisheToolMaterials.IRONFISHE,-2,-3,new Item.Settings()),
            "diamondfishe_hoe");

    //Sword
    private static final int swordAttackDamage = 3;
    private static final float swordAttackSpeed = -2.4f;
    public static final SwordItem COPPERFISHE_SWORD = registerTool(
            new SwordItem(FisheToolMaterials.COPPERFISHE,swordAttackDamage,swordAttackSpeed,new Item.Settings()),
            "copperfishe_sword");

    public static final SwordItem IRONFISHE_SWORD = registerTool(
            new SwordItem(FisheToolMaterials.IRONFISHE,swordAttackDamage,swordAttackSpeed,new Item.Settings()),
            "ironfishe_sword");

    public static final SwordItem GOLDFISHE_SWORD = registerTool(
            new SwordItem(FisheToolMaterials.GOLDFISHE,swordAttackDamage,swordAttackSpeed,new Item.Settings()),
            "goldfishe_sword");

    public static final SwordItem DIAMONDFISHE_SWORD = registerTool(
            new SwordItem(FisheToolMaterials.IRONFISHE,swordAttackDamage,swordAttackSpeed,new Item.Settings()),
            "diamondfishe_sword");

    public static Item FISHE_STAFF = registerGroup(new Item(new Item.Settings()),"fishe_staff");

}
