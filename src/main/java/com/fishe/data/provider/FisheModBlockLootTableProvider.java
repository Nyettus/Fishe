package com.fishe.data.provider;

import com.fishe.Blocks.BlockMaster;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class FisheModBlockLootTableProvider extends FabricBlockLootTableProvider {


    public FisheModBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> exporter) {

    }
    @Override
    public void generate(){
        addDrop(BlockMaster.FISHE_PASTER);
    }
}
