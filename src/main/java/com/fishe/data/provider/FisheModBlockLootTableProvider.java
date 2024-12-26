package com.fishe.data.provider;

import com.fishe.Blocks.BlockItems;
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
        addDrop(BlockMaster.FISHE_FERMENTER,drops(BlockItems.FISHE_FERMENTER));
        addDrop(BlockMaster.FISHE_REPAIR_TABLE,drops(BlockItems.FISHE_REPAIR_TABLE));
        addDrop(BlockMaster.FISHE_PASTE_BLOCK,drops(BlockItems.FISHE_PASTE_BLOCK));
        addDrop(BlockMaster.FISHEOMANCY_ALTER,drops(BlockItems.FISHEOMANCY_ALTER));
        addDrop(BlockMaster.FISHEOMANCY_EXTENDER,drops(BlockItems.FISHEOMANCY_EXTENDER));
    }
}
