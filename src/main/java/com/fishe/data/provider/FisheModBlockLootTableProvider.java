package com.fishe.data.provider;

import com.fishe.Blocks.BlockMaster;
import com.fishe.Fishe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class FisheModBlockLootTableProvider extends FabricBlockLootTableProvider {


    public FisheModBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate(){
        addDrop(BlockMaster.FISHE_FERMENTER,drops(Registries.ITEM.get(new Identifier(Fishe.MOD_ID,"fishe_fermenter"))));
        addDrop(BlockMaster.FISHE_REPAIR_TABLE,drops(Registries.ITEM.get(new Identifier(Fishe.MOD_ID,"fishe_repair_table"))));
        addDrop(BlockMaster.FISHE_PASTE_BLOCK,drops(Registries.ITEM.get(new Identifier(Fishe.MOD_ID,"fishe_paste_block"))));
    }
}
