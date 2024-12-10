package com.fishe.data.provider;

import com.fishe.Blocks.BlockMaster;
import com.fishe.Fishe;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class FisheModBlockTagProvider extends BlockTagProvider {
    public FisheModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        assignMineableTag();
    }


    private void assignMineableTag(){
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(BlockMaster.FISHE_REPAIR_TABLE);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(BlockMaster.FISHE_REPAIR_TABLE);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(BlockMaster.FISHE_FERMENTER);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(BlockMaster.FISHE_PASTE_BLOCK);

    }
}
