package com.fishe.patchouli.processor;

import com.fishe.recipe.FisheomancyRecipe;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class FisheomancyRecipeProcessor implements IComponentProcessor {
    private FisheomancyRecipe recipe;

    @Override
    public void setup(World world, IVariableProvider iVariableProvider) {
        String recipeId = iVariableProvider.get("recipe").asString();
        RecipeManager manager = world.getRecipeManager();
        recipe = (FisheomancyRecipe) manager.get(new Identifier(recipeId)).orElseThrow();

    }

    @Override
    public IVariable process(World world, String s) {
        var easyOutput = switch(s){
            case "out_recipe" -> IVariable.wrap(recipe.getName());
            case "output" -> IVariable.from(falseCraft(world));
            case "catalyst" -> IVariable.from(recipe.getCatalyst());
            case "slop_amount"->IVariable.wrap(recipe.getSlopAsPercent());
            default -> null;
        };
        if(easyOutput !=null) return easyOutput;

        for(int i = 0; i<15;i++){
            if(s.equals("input" + i)){
                return IVariable.from(recipe.getInputAtPos(i));
            }
        }

        return null;
    }



    private ItemStack falseCraft(World world){
        ItemStack falseOutput = recipe.getOutput(world.getRegistryManager()).copy();
        if(recipe.getEnchantment()==null) return falseOutput;
        EnchantmentHelper.set(recipe.getEnchantment(),falseOutput);
        return falseOutput;
    }
}
