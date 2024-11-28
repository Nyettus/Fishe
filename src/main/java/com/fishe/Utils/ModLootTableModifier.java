package com.fishe.Utils;

import com.fishe.Items.ItemsFishe;
import net.fabricmc.fabric.api.loot.v2.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class ModLootTableModifier {
    private static final Identifier FISH_ID = new Identifier("minecraft", "gameplay/fishing/fish");


    public static void ModifyFishTable() {
        LootTableEvents.MODIFY.register((ResourceManager, LootManager, id, tablebuilder, source) -> {
            if (FISH_ID.equals(id)) {
                ((FabricLootTableBuilder) tablebuilder).modifyPools((pools) -> {
                    pools.with(ItemEntry.builder(ItemsFishe.COPPER_FISHE).weight(15));
                });
            }
        });
    }
}
