package com.fishe.recipe;

import com.fishe.Fishe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void Initialize(){
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Fishe.MOD_ID,FishePasterRecipe.Serializer.ID),
                FishePasterRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Fishe.MOD_ID,FishePasterRecipe.Type.ID),
                FishePasterRecipe.Type.INSTANCE);


        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Fishe.MOD_ID,FisheomancyRecipe.Serializer.ID),
                FisheomancyRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Fishe.MOD_ID,FisheomancyRecipe.Type.ID),
                FisheomancyRecipe.Type.INSTANCE);
    }
}
