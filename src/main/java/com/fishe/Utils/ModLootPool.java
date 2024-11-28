package com.fishe.Utils;

import com.fishe.Fishe;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.util.Identifier;

public class ModLootPool {
    public static final Identifier COPPER_POOL = new Identifier(Fishe.MOD_ID,"custom_fishing/copper_rod_pool");
    public static final Identifier IRON_POOL = new Identifier(Fishe.MOD_ID,"custom_fishing/iron_rod_pool");
    public static final Identifier GOLD_POOL = new Identifier(Fishe.MOD_ID,"custom_fishing/gold_rod_pool");
    public static final Identifier DIAMOND_POOL = new Identifier(Fishe.MOD_ID,"custom_fishing/diamond_rod_pool");
    public static final Identifier NIGHT_POOL = new Identifier(Fishe.MOD_ID,"custom_fishing/night_pool");



}
