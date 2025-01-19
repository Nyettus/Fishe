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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     *
     * @param list1
     * @param list2
     * @param defaultValue
     * @param <E>
     * @return
     */
    public static <E> DefaultedList<E> ConcatDefaultedList(DefaultedList<E> list1, DefaultedList<E> list2, E defaultValue) {
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

    public static <E> DefaultedList<E> ShrinkDefaultedList(DefaultedList<E> list, E defaultValue) {
        ArrayList<E> asList = new ArrayList<>();
        for (var value : list) {
            if (value != defaultValue) asList.add(value);
        }

        E[] test = (E[]) asList.toArray();
        return DefaultedList.copyOf(defaultValue, test);
    }

    public static Map<String, Integer> StringMapFromJson(JsonArray object) {
        Map<String, Integer> returnMap = new HashMap<>();

        for (JsonElement element : object) {
            JsonObject asObj = element.getAsJsonObject();
            int level = asObj.get("level").getAsInt();
            returnMap.put(asObj.get("identifier").getAsString(), level);
        }
        return returnMap;
    }

    public static Map<Enchantment, Integer> StringMapToEnchantment(Map<String, Integer> original) {
        Map<Enchantment, Integer> returnMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : original.entrySet()) {
            Enchantment asEnchant = Registries.ENCHANTMENT.get(
                    new Identifier(entry.getKey())
            );
            Integer level = entry.getValue();
            returnMap.put(asEnchant, level);
        }
        return returnMap;
    }

    public static final String[] RomanNumerals = {"I", "II", "III", "IV", "V"};

    public static String FirstEnchantmentRomanFromString(Map<String, Integer> original) {
        if (original.isEmpty()) {
            throw new IllegalArgumentException("Enchantment map is empty");
        }
        Map.Entry<String, Integer> firstEntry = original.entrySet().iterator().next();
        return KeyToPresentable(firstEntry.getKey()) + " " + RomanNumerals[firstEntry.getValue() + 1];
    }

    public static String FirstEnchantmentRoman(Map<Enchantment, Integer> original){
        if (original.isEmpty()) {
            throw new IllegalArgumentException("Enchantment map is empty");
        }
        Map.Entry<Enchantment, Integer> firstEntry = original.entrySet().iterator().next();
        return firstEntry.getKey().getName(firstEntry.getValue()).getString();
    }

    public static String KeyToPresentable(String key) {
        // Define the regex pattern to capture the name part
        Pattern pattern = Pattern.compile("^[^:]+:([a-zA-Z0-9_]+)$");
        Matcher matcher = pattern.matcher(key);

        if (matcher.find()) {
            // Extract the name group
            String name = matcher.group(1);

            // Replace underscores with spaces and capitalize each word
            StringBuilder result = new StringBuilder();
            for (String part : name.split("_")) {
                if (!part.isEmpty()) {
                    result.append(part.substring(0, 1).toUpperCase())
                            .append(part.substring(1).toLowerCase())
                            .append(" "); // Add a space after each word
                }
            }

            // Return the final formatted string without trailing space
            return result.toString().trim();
        } else {
            // Return a default value for invalid input
            return "Invalid key format";
        }
    }


}
