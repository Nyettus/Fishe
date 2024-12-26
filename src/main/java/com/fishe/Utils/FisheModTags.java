package com.fishe.Utils;

import com.fishe.Fishe;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.command.ReturnCommand;
import net.minecraft.util.Identifier;

public class FisheModTags {
    public static final TagKey<Item> SPECIAL_RODS = register("fishing_rods");
    public static final TagKey<Item> PASTE_REPAIRABLE = register("paste_repairable");
    public static final TagKey<Item> FISHEOMANCY_CATALYST = register("fisheomancy_catalyst");


    private static TagKey<Item> register(String title) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(Fishe.MOD_ID, title));
    }

    public static void Initialize() {

    }
}
