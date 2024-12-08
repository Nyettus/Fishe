package com.fishe.data.provider;


import com.fishe.Fishe;
import com.fishe.Items.ItemMaster;
import com.fishe.Items.ItemsFishe;
import com.fishe.Items.ItemsTools;
import com.fishe.Utils.FisheModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;



public class FisheModTagProvider extends ItemTagProvider {
    public FisheModTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);

    }



    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.FISHES)
                .add(ItemsFishe.STONE_FISHE)
                .add(ItemsFishe.COAL_FISHE)
                .add(ItemsFishe.COPPER_FISHE)
                .add(ItemsFishe.IRON_FISHE)
                .add(ItemsFishe.GOLD_FISHE)
                .add(ItemsFishe.LAPIS_FISHE)
                .add(ItemsFishe.EMERALD_FISHE)
                .add(ItemsFishe.DIAMOND_FISHE)
                .add(ItemsFishe.RED_FISHE);


        getOrCreateTagBuilder(ItemTags.TOOLS)
                .add(ItemsTools.COPPER_ROD)
                .add(ItemsTools.IRON_ROD)
                .add(ItemsTools.GOLD_ROD)
                .add(ItemsTools.DIAMOND_ROD);

        getOrCreateTagBuilder(FisheModTags.SPECIAL_RODS)
                .add(ItemsTools.COPPER_ROD)
                .add(ItemsTools.IRON_ROD)
                .add(ItemsTools.GOLD_ROD)
                .add(ItemsTools.DIAMOND_ROD);

        assignRepairableTag();

    }


    private void assignRepairableTag() {
        var tagBuilder = getOrCreateTagBuilder(FisheModTags.PASTE_REPAIRABLE);
        //Manually do fishing rods
        tagBuilder.add(ItemsTools.COPPER_ROD)
                .add(ItemsTools.IRON_ROD)
                .add(ItemsTools.GOLD_ROD)
                .add(ItemsTools.DIAMOND_ROD);

        for (Item tool : ItemMaster.ToolMap.values()) {
            tagBuilder.add(tool);
        }


    }

}