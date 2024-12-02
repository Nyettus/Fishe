package com.fishe.Items;


import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;

public class ItemsFishe extends ItemMaster {

    public static void initialize() {


        InitMiningFishe();


    }
    
    private static <T extends Item> T registerFish(T item, String id){
        T temp = registerGroup(item,id);
        ItemMaster.FishMap.put(id,temp);
        return temp;
        
    }

    private static <T extends Item> T registerFish(T item, String id,boolean metalfish){
        T temp = registerFish(item,id);
        if(metalfish){
            String without_ = id.replace("_","");
            ItemMaster.MetalFishMap.put(without_,temp);
        }
        return temp;


    }


    public static final Item STONE_FISHE = registerFish(new Item(new Item.Settings()), "stone_fishe");
    public static final Item COAL_FISHE = registerFish(new Item(new Item.Settings()), "coal_fishe");
    public static final Item COPPER_FISHE = registerFish(new Item(new Item.Settings()), "copper_fishe",true);
    public static final Item IRON_FISHE = registerFish(new Item(new Item.Settings()), "iron_fishe",true);
    public static final Item GOLD_FISHE = registerFish(new Item(new Item.Settings()), "gold_fishe",true);
    public static final Item LAPIS_FISHE = registerFish(new Item(new Item.Settings()), "lapis_fishe");
    public static final Item EMERALD_FISHE = registerFish(new Item(new Item.Settings()), "emerald_fishe");
    public static final Item DIAMOND_FISHE = registerFish(new Item(new Item.Settings()), "diamond_fishe",true);
    public static final Item RED_FISHE = registerFish(new Item(new Item.Settings()), "red_fishe");



    private static void InitMiningFishe() {


        FuelRegistry.INSTANCE.add(COAL_FISHE, 40 * 20);


    }

    public static final Item ROT_FISHE = registerFish(new Item(new Item.Settings()), "rot_fishe");
    public static final Item BONE_FISHE = registerFish(new Item(new Item.Settings()), "bone_fishe");
    public static final Item SPIDER_FISHE = registerFish(new Item(new Item.Settings()), "spider_fishe");
    public static final Item CREEPY_FISHE = registerFish(new Item(new Item.Settings()), "creepy_fishe");
    public static final Item ENDER_FISHE = registerFish(new Item(new Item.Settings()), "ender_fishe");



    public static final Item FISHE_PASTE = registerGroup(new Item(new Item.Settings()),"fishe_paste");





}




