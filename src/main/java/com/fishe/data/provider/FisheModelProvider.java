package com.fishe.data.provider;

import com.fishe.Blocks.BlockMaster;
import com.fishe.Items.ItemMaster;
import com.fishe.Items.ItemsFishe;
import com.fishe.Items.ItemsTools;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import org.apache.logging.log4j.message.ExitMessage;

public class FisheModelProvider extends FabricModelProvider {
    public FisheModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        genBlockModels(blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        genFishModels(itemModelGenerator);
        itemModelGenerator.register(ItemsFishe.FISHE_PASTE,Models.GENERATED);

        itemModelGenerator.register(ItemsTools.COPPER_ROD,Models.HANDHELD_ROD);
        itemModelGenerator.register(ItemsTools.COPPER_ROD,"_cast",Models.HANDHELD_ROD);

        itemModelGenerator.register(ItemsTools.IRON_ROD,Models.HANDHELD_ROD);
        itemModelGenerator.register(ItemsTools.IRON_ROD,"_cast",Models.HANDHELD_ROD);

        itemModelGenerator.register(ItemsTools.GOLD_ROD,Models.HANDHELD_ROD);
        itemModelGenerator.register(ItemsTools.GOLD_ROD,"_cast",Models.HANDHELD_ROD);

        itemModelGenerator.register(ItemsTools.DIAMOND_ROD,Models.HANDHELD_ROD);
        itemModelGenerator.register(ItemsTools.DIAMOND_ROD,"_cast",Models.HANDHELD_ROD);


        genToolModels(itemModelGenerator);



    }


    private void genFishModels(ItemModelGenerator generator){
        for(Item value : ItemMaster.FishMap.values()){
            generator.register(value,Models.GENERATED);
        }
    }
    private void genToolModels(ItemModelGenerator generator){
        for(Item value : ItemMaster.ToolMap.values()){
            generator.register(value,Models.HANDHELD);
        }
    }

    private void genBlockModels(BlockStateModelGenerator generator){
        //generator.registerSimpleCubeAll(BlockMaster.FISHE_PASTER);
    }
}
