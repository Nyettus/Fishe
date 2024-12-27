package com.fishe.recipe;

import com.fishe.Blocks.Entity.FisheomancyAlterBlockEntity;
import com.fishe.Fishe;
import com.fishe.Utils.UsefulBox;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.*;
import java.util.function.BiFunction;

public class FisheomancyRecipe implements Recipe<SimpleInventory> {
    private final Identifier Id;
    private final ItemStack Output;
    private final Ingredient Catalyst;
    private final DefaultedList<Ingredient> recipeItems;
    public int SlopAmount;
    private Map<String,Integer> enchantment;

    public FisheomancyRecipe(Identifier id, ItemStack output, Ingredient catalyst, DefaultedList<Ingredient> recipeItems, int slopAmount,Map<String,Integer> enchantment) {
        this.Id = id;
        this.Output = output;
        this.Catalyst = catalyst;
        this.recipeItems = recipeItems;
        this.SlopAmount = slopAmount;
        this.enchantment = enchantment;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (world.isClient()) return false;

        if (!Catalyst.test(inventory.getStack(0))) {
            return false;
        } else if (recipeItems.size() != (inventory.size() - 1)) {
            return false;
        } else {
            ArrayList<ItemStack> inv = new ArrayList<>();
            //we start at 1 to skip the catalyst
            for (int i = 1; i < inventory.size(); i++) {
                inv.add(inventory.getStack(i));
            }

            //I hate everything about this but i dont know how to compare the stacks
            for (Ingredient quickref : recipeItems) {
                boolean hasFoundMatch = false;
                for(ItemStack item : inv){
                    if(quickref.test(item)){
                        inv.remove(item);
                        hasFoundMatch=true;
                        break;
                    }
                }
                if(!hasFoundMatch) return false;
            }
            return true;

        }

    }

    public Map<Enchantment,Integer> getEnchantment(){
        if(enchantment.isEmpty()) return null;
        return UsefulBox.StringMapToEnchantment(enchantment);
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
            Ingredient catalyst = Ingredient.fromJson(JsonHelper.getObject(json, "catalyst"));
            int slopAmount = JsonHelper.getInt(json, "slop_amount");
            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            Map<String,Integer> enchant = UsefulBox.StringMapFromJson(JsonHelper.getArray(json, "enchantment"));
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(ingredients.size(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new FisheomancyRecipe(id, output, catalyst, inputs, slopAmount,enchant);
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

            Map<String,Integer> map = new HashMap<>();
            int size = buf.readInt();
            for(int i = 0; i<size;i++){
                String key = buf.readString();
                int value = buf.readInt();
                map.put(key,value);
            }


            return new FisheomancyRecipe(id, output, catalyst, inputs, slop,map);
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

            buf.writeInt(recipe.enchantment.size());
            for(Map.Entry<String,Integer> entry:recipe.enchantment.entrySet()){
                buf.writeString(entry.getKey());
                buf.writeInt(entry.getValue());
            }

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
