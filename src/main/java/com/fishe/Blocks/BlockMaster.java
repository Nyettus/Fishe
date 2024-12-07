package com.fishe.Blocks;

import com.fishe.Blocks.Entity.ModBlockEntities;
import com.fishe.Fishe;
import com.fishe.Items.ItemMaster;
import com.fishe.Screen.ModScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class BlockMaster {
    public static <T extends Block> T register(T block, String name, boolean asItem){
        Identifier identifier = new Identifier(Fishe.MOD_ID,name);
        T returnBlock = Registry.register(Registries.BLOCK,identifier,block);
        if(asItem){
            BlockItem isItem = new BlockItem(block,new Item.Settings());
            BlockItems.FISHE_FERMENTER = ItemMaster.registerGroup(isItem,name);
        }
        return returnBlock;
    }


    public static final FisheFermenterBlock FISHE_FERMENTER = register(
            new FisheFermenterBlock(FabricBlockSettings.copyOf(Blocks.STONE)),
            "fishe_fermenter",
            true
    );



    public static void Initialize() {
        ModBlockEntities.Initialize();
        ModScreenHandler.Initialize();

        BlockItems.Initialise();
    }
}
