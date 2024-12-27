package com.fishe.Blocks;


import com.fishe.Fishe;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;


public class BlockItems {

    public static final Item FISHE_FERMENTER = Registries.ITEM.get(new Identifier(Fishe.MOD_ID, "fishe_fermenter"));
    public static final Item FISHE_REPAIR_TABLE = Registries.ITEM.get(new Identifier(Fishe.MOD_ID, "fishe_repair_table"));
    public static final Item FISHE_PASTE_BLOCK = Registries.ITEM.get(new Identifier(Fishe.MOD_ID, "fishe_paste_block"));
    public static final Item FISHEOMANCY_ALTAR = Registries.ITEM.get(new Identifier(Fishe.MOD_ID, "fisheomancy_altar"));
    public static final Item FISHEOMANCY_EXTENDER = Registries.ITEM.get(new Identifier(Fishe.MOD_ID, "fisheomancy_extender"));

    public static void Initialise() {

    }
}
