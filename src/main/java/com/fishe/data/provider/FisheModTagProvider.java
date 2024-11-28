package com.fishe.data.provider;


import com.fishe.Fishe;
import com.fishe.Items.ItemsFishe;
import com.fishe.Items.ItemsTools;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;


public class FisheModTagProvider extends ItemTagProvider{
    public FisheModTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture){
        super(output,registriesFuture);

    }

    private static final TagKey<Item> SPECIAL_RODS = TagKey.of(RegistryKeys.ITEM, new Identifier(Fishe.MOD_ID,"fishing_rods"));

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

        getOrCreateTagBuilder(SPECIAL_RODS)
                .add(ItemsTools.COPPER_ROD)
                .add(ItemsTools.IRON_ROD)
                .add(ItemsTools.GOLD_ROD)
                .add(ItemsTools.DIAMOND_ROD);



    }
}