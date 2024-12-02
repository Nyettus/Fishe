package com.fishe.data.provider;

import com.fishe.Items.ItemsFishe;
import com.fishe.Items.ItemsTools;
import com.fishe.enchantments.EnchantmentMaster;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.item.*;

public class FisheEnLanguageProvider extends FabricLanguageProvider {
    public FisheEnLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ItemsFishe.STONE_FISHE,"Stone Fishe");
        translationBuilder.add(ItemsFishe.COAL_FISHE,"Coal Fishe");
        translationBuilder.add(ItemsFishe.COPPER_FISHE,"Copper Fishe");
        translationBuilder.add(ItemsFishe.IRON_FISHE,"Iron Fishe");
        translationBuilder.add(ItemsFishe.GOLD_FISHE,"Gold Fishe");
        translationBuilder.add(ItemsFishe.LAPIS_FISHE,"Lapis Fishe");
        translationBuilder.add(ItemsFishe.EMERALD_FISHE,"Emerald Fishe");
        translationBuilder.add(ItemsFishe.DIAMOND_FISHE,"Diamond Fishe");
        translationBuilder.add(ItemsFishe.RED_FISHE,"Red Fishe");

        translationBuilder.add(ItemsFishe.ROT_FISHE,"Rot Fishe");
        translationBuilder.add(ItemsFishe.BONE_FISHE,"Bone Fishe");
        translationBuilder.add(ItemsFishe.SPIDER_FISHE,"Spider Fishe");
        translationBuilder.add(ItemsFishe.CREEPY_FISHE,"Creepy Fishe");
        translationBuilder.add(ItemsFishe.ENDER_FISHE,"Ender Fishe");

        translationBuilder.add(ItemsFishe.FISHE_PASTE,"Fishe Paste");

        translationBuilder.add(ItemsTools.COPPER_ROD,"Copper Fishing Rod");
        translationBuilder.add(ItemsTools.IRON_ROD,"Iron Fishing Rod");
        translationBuilder.add(ItemsTools.GOLD_ROD,"Gold Fishing Rod");
        translationBuilder.add(ItemsTools.DIAMOND_ROD,"Diamond Fishing Rod");

        translationBuilder.add(EnchantmentMaster.CALL_OF_THE_NIGHT,"Call of the Night");

        GenerateToolNames(translationBuilder,"Copperfishe",
                ItemsTools.COPPERFISHE_SHOVEL,
                ItemsTools.COPPERFISHE_PICKAXE,
                ItemsTools.COPPERFISHE_AXE,
                ItemsTools.COPPERFISHE_HOE,
                ItemsTools.COPPERFISHE_SWORD);

        GenerateToolNames(translationBuilder,"Ironfishe",
                ItemsTools.IRONFISHE_SHOVEL,
                ItemsTools.IRONFISHE_PICKAXE,
                ItemsTools.IRONFISHE_AXE,
                ItemsTools.IRONFISHE_HOE,
                ItemsTools.IRONFISHE_SWORD);

        GenerateToolNames(translationBuilder,"Goldfishe",
                ItemsTools.GOLDFISHE_SHOVEL,
                ItemsTools.GOLDFISHE_PICKAXE,
                ItemsTools.GOLDFISHE_AXE,
                ItemsTools.GOLDFISHE_HOE,
                ItemsTools.GOLDFISHE_SWORD);

        GenerateToolNames(translationBuilder,"Diamondfishe",
                ItemsTools.DIAMONDFISHE_SHOVEL,
                ItemsTools.DIAMONDFISHE_PICKAXE,
                ItemsTools.DIAMONDFISHE_AXE,
                ItemsTools.DIAMONDFISHE_HOE,
                ItemsTools.DIAMONDFISHE_SWORD);

    }







    private void GenerateToolNames(TranslationBuilder builder, String prefix, ShovelItem shovel, PickaxeItem pickaxe, AxeItem axe, HoeItem hoe, SwordItem sword){

        builder.add(shovel,prefix+" Shovel");
        builder.add(pickaxe,prefix+" Pickaxe");
        builder.add(axe,prefix+" Axe");
        builder.add(hoe,prefix+" Hoe");
        builder.add(sword,prefix+" Sword");

    }
}
