package com.fishe.Items;

import com.fishe.Fishe;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ItemMaster
{
    public static final RegistryKey<ItemGroup> FISHE_ITEMS_KEYS = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier(Fishe.MOD_ID,"item_group"));
    public static ItemGroup FISHE_ITEM_GROUP = FabricItemGroup.builder()
            .icon(()->new ItemStack(ItemsFishe.STONE_FISHE))
            .displayName(Text.translatable("itemGroup.FisheItems"))
            .build();

    public static Map<String,Item> FishMap = new HashMap<>();
    public static Map<String,Item> MetalFishMap = new HashMap<>();
    public static Map<String,Item> ToolMap = new HashMap<>();


    public static <T extends Item>  T register(T item, String id) {
        //Create the identifier for the item
        Identifier itemID = new Identifier(Fishe.MOD_ID, id);

        //Register the item
        T registereditem = Registry.register(Registries.ITEM, itemID, item);

        return registereditem;

    }

    public static <T extends Item>  T registerGroup(T item, String id) {
        //Create the identifier for the item
        Identifier itemID = new Identifier(Fishe.MOD_ID, id);

        //Register the item
        T registereditem = Registry.register(Registries.ITEM, itemID, item);
        ItemGroupEvents.modifyEntriesEvent(FISHE_ITEMS_KEYS).register((itemGroup)->itemGroup.add(registereditem));
        return registereditem;
    }



    public static void Initialize(){
        Registry.register(Registries.ITEM_GROUP, FISHE_ITEMS_KEYS, FISHE_ITEM_GROUP);
        ItemsFishe.initialize();
        ItemsTools.initialize();
        ItemsMisc.initialize();


    }
}
