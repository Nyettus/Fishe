package com.fishe.recipe;

import com.fishe.Blocks.Entity.FisheomancyAlterBlockEntity;
import com.fishe.Fishe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.ArrayList;

public class FisheomancyRecipe implements Recipe<SimpleInventory> {
    private final Identifier Id;
    private final ItemStack Output;
    private final Ingredient Catalyst;
    private final DefaultedList<Ingredient> recipeItems;
    public int SlopAmount;

    public FisheomancyRecipe(Identifier id,ItemStack output,Ingredient catalyst ,DefaultedList<Ingredient> recipeItems,int slopAmount){
        this.Id = id;
        this.Output = output;
        this.Catalyst = catalyst;
        this.recipeItems = recipeItems;
        this.SlopAmount = slopAmount;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()) return false;

        if(Catalyst != Ingredient.ofStacks(inventory.getStack(0))){
            return false;
        }
        else if(recipeItems.size() != inventory.size()){
            return false;
        }
        else{
            ArrayList<ItemStack> inv = new ArrayList<>();
            //we start at 1 to skip the catalyst
            for(int i = 1; i<inventory.size();i++){
                inv.add(inventory.getStack(i));
            }
            return inv.toArray()==recipeItems.toArray();

        }

    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return Output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return Output.copy();
    }

    @Override
    public Identifier getId() {
        return Id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<FisheomancyRecipe> {
        public static final FisheomancyRecipe.Serializer INSTANCE = new FisheomancyRecipe.Serializer();
        public static final String ID = "fisheomancy_recipe";

        @Override
        public FisheomancyRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
            Ingredient catalyst = Ingredient.fromJson(JsonHelper.getObject(json,"catalyst"));
            int slopAmount = JsonHelper.asInt(json,"slop_amount");
            Fishe.LOGGER.info("Tried to read json");
            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new FisheomancyRecipe(id, output, catalyst,inputs,slopAmount);
        }

        @Override
        public FisheomancyRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }
            ItemStack output = buf.readItemStack();
            int slop = buf.readShort();
            Ingredient catalyst = Ingredient.fromPacket(buf);
            return new FisheomancyRecipe(id, output, catalyst,inputs,slop);
        }


        @Override
        public void write(PacketByteBuf buf, FisheomancyRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput(null));
            buf.writeShort(recipe.SlopAmount);
            recipe.Catalyst.write(buf);
        }
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<FisheomancyRecipe> {
        public static final FisheomancyRecipe.Type INSTANCE = new FisheomancyRecipe.Type();
        public static final String ID = "fisheomancy_recipe";
    }
}
