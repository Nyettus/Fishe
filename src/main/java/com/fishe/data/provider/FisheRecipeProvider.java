package com.fishe.data.provider;

import com.fishe.Blocks.BlockItems;
import com.fishe.Fishe;
import com.fishe.Items.ItemMaster;
import com.fishe.Items.ItemsFishe;
import com.fishe.Items.ItemsMisc;
import com.fishe.Items.ItemsTools;
import com.fishe.Items.ItemsTools.ToolType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class FisheRecipeProvider extends FabricRecipeProvider {
    public FisheRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        genMiningFisheRecipe(consumer);
        genNightFisheRecipe(consumer);
        genToolRecipes(consumer);
        genCompactingRecipe(consumer);
        genFisheFermenter(consumer);
        genFisheRepairTable(consumer);
        genCopperTruth(consumer);
        genFisheStaff(consumer);
    }

    private void genMiningFisheRecipe(Consumer<RecipeJsonProvider> consumer) {
        ShapelessLooper(consumer, RecipeCategory.BUILDING_BLOCKS, Items.COBBLESTONE, ItemsFishe.STONE_FISHE, 1);
        ShapelessLooper(consumer, RecipeCategory.MISC, Items.COAL, ItemsFishe.COAL_FISHE, 4);
        ShapelessLooper(consumer, RecipeCategory.MISC, Items.RAW_COPPER, ItemsFishe.COPPER_FISHE, 4);
        ShapelessLooper(consumer, RecipeCategory.MISC, Items.RAW_IRON, ItemsFishe.IRON_FISHE, 4);
        ShapelessLooper(consumer, RecipeCategory.MISC, Items.RAW_GOLD, ItemsFishe.GOLD_FISHE, 4);
        ShapelessLooper(consumer, RecipeCategory.MISC, Items.LAPIS_LAZULI, ItemsFishe.LAPIS_FISHE, 1);
        ShapelessLooper(consumer, RecipeCategory.MISC, Items.EMERALD, ItemsFishe.EMERALD_FISHE, 4);
        ShapelessLooper(consumer, RecipeCategory.MISC, Items.DIAMOND, ItemsFishe.DIAMOND_FISHE, 8);
        ShapelessLooper(consumer, RecipeCategory.REDSTONE, Items.REDSTONE, ItemsFishe.RED_FISHE, 1);

        FishingRodScaffold(consumer, ItemsTools.COPPER_ROD, Items.COPPER_INGOT, Fishe.MOD_ID + "b");
        FishingRodScaffold(consumer, ItemsTools.COPPER_ROD, ItemsFishe.COPPER_FISHE, Fishe.MOD_ID);
        FishingRodScaffold(consumer, ItemsTools.IRON_ROD, ItemsFishe.IRON_FISHE, Fishe.MOD_ID);
        FishingRodScaffold(consumer, ItemsTools.GOLD_ROD, ItemsFishe.GOLD_FISHE, Fishe.MOD_ID);
        FishingRodScaffold(consumer, ItemsTools.DIAMOND_ROD, ItemsFishe.DIAMOND_FISHE, Fishe.MOD_ID);

    }

    private void genNightFisheRecipe(Consumer<RecipeJsonProvider> consumer) {
        ShapelessLooper(consumer, RecipeCategory.MISC, Items.ROTTEN_FLESH, ItemsFishe.ROT_FISHE, 1);

        ShapelessLooper(consumer, RecipeCategory.MISC, Items.BONE_MEAL, ItemsFishe.BONE_FISHE, 1);
        ShapelessLooper(consumer, RecipeCategory.MISC, Items.BONE, ItemsFishe.BONE_FISHE, 3, Fishe.MOD_ID + "b");

        ShapelessLooper(consumer, RecipeCategory.MISC, Items.STRING, ItemsFishe.SPIDER_FISHE, 1);
        ShapelessLooper(consumer, RecipeCategory.MISC, Items.SPIDER_EYE, ItemsFishe.SPIDER_FISHE, 2, Fishe.MOD_ID + "b");

        ShapelessLooper(consumer, RecipeCategory.MISC, Items.GUNPOWDER, ItemsFishe.CREEPY_FISHE, 3);
        ShapelessLooper(consumer, RecipeCategory.MISC, Items.ENDER_PEARL, ItemsFishe.ENDER_FISHE, 6);
    }


    private void ShapelessLooper(Consumer<RecipeJsonProvider> consumer, RecipeCategory category, Item output, Item input, int inputCount) {
        ShapelessLooper(consumer, category, output, input, inputCount, Fishe.MOD_ID);

    }

    private void ShapelessLooper(Consumer<RecipeJsonProvider> consumer, RecipeCategory category, Item output, Item input, int inputCount, String special) {
        ShapelessRecipeJsonBuilder builder = ShapelessRecipeJsonBuilder.create(category, output, 1);
        for (int i = 0; i < inputCount; i++) {
            builder.input(input);
        }
        builder.criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(consumer, new Identifier(special + getRecipeName(input)));

    }

    private void FishingRodScaffold(Consumer<RecipeJsonProvider> consumer, Item output, Item input, String specialTag) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, output).pattern("  f").pattern(" fs").pattern("f s")
                .input('f', input)
                .input('s', Items.STRING)
                .criterion(FabricRecipeProvider.hasItem(input)
                        , FabricRecipeProvider.conditionsFromItem(input))
                .criterion(FabricRecipeProvider.hasItem(Items.STRING),
                        FabricRecipeProvider.conditionsFromItem(Items.STRING))
                .offerTo(consumer, new Identifier(specialTag + getRecipeName(output)));

    }


    private void genToolRecipes(Consumer<RecipeJsonProvider> consumer) {
        for (String key : ItemMaster.MetalFishMap.keySet()) {
            Item fishe = ItemMaster.MetalFishMap.get(key);
            ;

            for (ToolType tool : ToolType.values()) {
                GetScaffold(tool, consumer, key, fishe);
            }
        }
    }

    private void GetScaffold(ToolType num, Consumer<RecipeJsonProvider> consumer, String key, Item input) {
        switch (num) {

            case Shovel -> {
                Item shovel = ItemMaster.ToolMap.get(key + "_shovel");
                ShovelScaffold(consumer, shovel, input);
            }
            case Pickaxe -> {
                Item pickaxe = ItemMaster.ToolMap.get(key + "_pickaxe");
                PickaxeScaffold(consumer, pickaxe, input);
            }
            case Axe -> {
                Item axe = ItemMaster.ToolMap.get(key + "_axe");
                AxeScaffold(consumer, axe, input);
            }
            case Hoe -> {
                Item hoe = ItemMaster.ToolMap.get(key + "_hoe");
                HoeScaffold(consumer, hoe, input);
            }
            case Sword -> {
                Item sword = ItemMaster.ToolMap.get(key + "_sword");
                SwordScaffold(consumer, sword, input);
            }
        }
    }


    private void ShovelScaffold(Consumer<RecipeJsonProvider> consumer, Item output, Item input) {
        ToolScaffold(consumer, output, input, Fishe.MOD_ID, " f ", " s ", " s ");
    }

    private void PickaxeScaffold(Consumer<RecipeJsonProvider> consumer, Item output, Item input) {
        ToolScaffold(consumer, output, input, Fishe.MOD_ID, "fff", " s ", " s ");
    }

    private void AxeScaffold(Consumer<RecipeJsonProvider> consumer, Item output, Item input) {
        ToolScaffold(consumer, output, input, Fishe.MOD_ID, " ff", " sf", " s ");
        ToolScaffold(consumer, output, input, Fishe.MOD_ID + 'b', "ff ", "fs ", " s ");
    }

    private void HoeScaffold(Consumer<RecipeJsonProvider> consumer, Item output, Item input) {
        ToolScaffold(consumer, output, input, Fishe.MOD_ID, "ff ", " s ", " s ");
        ToolScaffold(consumer, output, input, Fishe.MOD_ID + 'b', " ff", " s ", " s ");
    }

    private void SwordScaffold(Consumer<RecipeJsonProvider> consumer, Item output, Item input) {
        ToolScaffold(consumer, output, input, Fishe.MOD_ID, " f ", " f ", " s ");
    }


    private void ToolScaffold(Consumer<RecipeJsonProvider> consumer, Item output, Item input, String specialTag, String toprow, String midrow, String lastrow) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, output).pattern(toprow).pattern(midrow).pattern(lastrow)
                .input('s', Items.STICK)
                .input('f', input)
                .criterion(FabricRecipeProvider.hasItem(input)
                        , FabricRecipeProvider.conditionsFromItem(input))
                .criterion(FabricRecipeProvider.hasItem(Items.STICK)
                        , FabricRecipeProvider.conditionsFromItem(Items.STICK))
                .offerTo(consumer, new Identifier(specialTag + getRecipeName(output)));
        Fishe.LOGGER.info("Successfully registered " + output.getName().getString());

    }

    private void genFisheFermenter(Consumer<RecipeJsonProvider> consumer) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockItems.FISHE_FERMENTER)
                .pattern("pcp")
                .pattern("pip")
                .pattern("pbp")
                .input('p', ItemTags.PLANKS)
                .input('c', ItemsFishe.COPPER_FISHE)
                .input('i', ItemsFishe.IRON_FISHE)
                .input('b', ItemsFishe.COAL_FISHE)
                .criterion(FabricRecipeProvider.hasItem(ItemsFishe.COPPER_FISHE)
                        , FabricRecipeProvider.conditionsFromItem(ItemsFishe.COPPER_FISHE))
                .offerTo(consumer, new Identifier(getRecipeName(BlockItems.FISHE_FERMENTER)));
    }

    private void genFisheRepairTable(Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, BlockItems.FISHE_REPAIR_TABLE)
                .pattern("ifi")
                .pattern(" l ")
                .pattern("ccc")
                .input('i', ItemsFishe.IRON_FISHE)
                .input('f', BlockItems.FISHE_PASTE_BLOCK)
                .input('l', ItemsFishe.LAPIS_FISHE)
                .input('c', ItemsFishe.COPPER_FISHE)
                .criterion(FabricRecipeProvider.hasItem(ItemsFishe.COPPER_FISHE)
                        , FabricRecipeProvider.conditionsFromItem(ItemsFishe.COPPER_FISHE))
                .offerTo(consumer, new Identifier(getRecipeName(BlockItems.FISHE_REPAIR_TABLE)));
    }

    private void genCompactingRecipe(Consumer<RecipeJsonProvider> consumer) {
        CompactingRecipe(consumer, ItemsFishe.FISHE_PASTE, BlockItems.FISHE_PASTE_BLOCK);
    }


    private void CompactingRecipe(Consumer<RecipeJsonProvider> consumer, Item smaller, Item larger) {
        ShapelessRecipeJsonBuilder compacting = ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, larger, 1);
        for (int i = 0; i < 9; i++) {
            compacting.input(smaller);
        }
        compacting.criterion(hasItem(smaller), conditionsFromItem(smaller))
                .offerTo(consumer, new Identifier(Fishe.MOD_ID + "_compacting_" + getRecipeName(smaller)));


        ShapelessRecipeJsonBuilder expanding = ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, smaller, 9);
        expanding.input(larger);
        expanding.criterion(hasItem(larger), conditionsFromItem(larger))
                .offerTo(consumer, new Identifier(Fishe.MOD_ID + "_compacting_" + getRecipeName(larger)));
    }

    private void genCopperTruth(Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemsMisc.REINFORCED_COPPER)
                .pattern("ccc")
                .pattern("ded")
                .pattern("ccc")
                .input('c', ItemsFishe.COPPER_FISHE)
                .input('d', ItemsFishe.DIAMOND_FISHE)
                .input('e', Items.ENDER_PEARL)
                .criterion(FabricRecipeProvider.hasItem(ItemsFishe.DIAMOND_FISHE)
                        , FabricRecipeProvider.conditionsFromItem(ItemsFishe.DIAMOND_FISHE))
                .offerTo(consumer, new Identifier(getRecipeName(ItemsMisc.REINFORCED_COPPER)));
    }

    private void genFisheStaff(Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ItemsTools.FISHE_STAFF)
                .pattern(" t ")
                .pattern(" c ")
                .pattern(" c ")
                .input('t', ItemsMisc.REINFORCED_COPPER)
                .input('c', ItemsFishe.COPPER_FISHE)
                .criterion(FabricRecipeProvider.hasItem(ItemsMisc.REINFORCED_COPPER)
                        , FabricRecipeProvider.conditionsFromItem(ItemsMisc.REINFORCED_COPPER))
                .offerTo(consumer, new Identifier(getRecipeName(ItemsTools.FISHE_STAFF)));
    }

}


