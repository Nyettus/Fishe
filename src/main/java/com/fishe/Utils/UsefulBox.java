package com.fishe.Utils;

import com.fishe.Fishe;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ibm.icu.impl.UResource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Array;
import java.util.*;

public class UsefulBox {
    private static BlockPos nullpos = new BlockPos(0, 500, 0);

    /**
     * Essentially BlockPos.asLong but with null protection
     *
     * @param original
     * @return
     */
    public static long BlockPosToLong(BlockPos original) {
        if (original == null) return nullpos.asLong();
        return original.asLong();
    }

    /**
     * Essentially BlockPos.fromLong but with null protection
     *
     * @param original
     * @return
     */
    public static BlockPos LongToBlockPos(long original) {
        if (original == nullpos.asLong()) return null;
        return BlockPos.fromLong(original);
    }

    /**
     * Concatenate 2 Defaulted List
     * @param list1
     * @param list2
     * @param defaultValue
     * @return
     * @param <E>
     */
    public static <E> DefaultedList<E> ConcatDefaultedList(DefaultedList<E> list1, DefaultedList<E> list2, E defaultValue){
        // Create a new DefaultedList with enough space for both lists
        DefaultedList<E> result = DefaultedList.ofSize(list1.size() + list2.size(), defaultValue);

        // Copy elements from list1
        for (int i = 0; i < list1.size(); i++) {
            result.set(i, list1.get(i));
        }

        // Copy elements from list2
        for (int i = 0; i < list2.size(); i++) {
            result.set(list1.size() + i, list2.get(i));
        }

        return result;
    }

    public static <E> DefaultedList<E> ShrinkDefaultedList(DefaultedList<E> list, E defaultValue){
        ArrayList<E> asList = new ArrayList<>();
        for(var value: list){
            if(value != defaultValue) asList.add(value);
        }

        E[] test = (E[]) asList.toArray();
        return DefaultedList.copyOf(defaultValue,test);
    }

    public static Map<String,Integer> StringMapFromJson(JsonArray object){
        Map<String,Integer> returnMap = new HashMap<>();

        for(JsonElement element:object){
            JsonObject asObj = element.getAsJsonObject();
            int level = asObj.get("level").getAsInt();
            returnMap.put(asObj.get("identifier").getAsString(),level);
        }
        return returnMap;
    }

    public static Map<Enchantment,Integer> StringMapToEnchantment(Map<String,Integer> original){
        Map<Enchantment,Integer> returnMap = new HashMap<>();
        for(Map.Entry<String,Integer> entry:original.entrySet()){
            Enchantment asEnchant = Registries.ENCHANTMENT.get(
                    new Identifier(entry.getKey())
            );
            Integer level = entry.getValue();
            returnMap.put(asEnchant,level);
        }
        return returnMap;
    }


}
