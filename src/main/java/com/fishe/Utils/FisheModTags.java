package com.fishe.Utils;

import com.fishe.Fishe;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class FisheModTags {
    public static final TagKey<Item> SPECIAL_RODS = TagKey.of(RegistryKeys.ITEM, new Identifier(Fishe.MOD_ID, "fishing_rods"));
    public static final TagKey<Item> PASTE_REPAIRABLE = TagKey.of(RegistryKeys.ITEM, new Identifier(Fishe.MOD_ID, "paste_repairable"));


    public static void Initialize(){

    }
}
