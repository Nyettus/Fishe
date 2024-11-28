package com.fishe.enchantments;

import com.fishe.Fishe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class EnchantmentMaster {
    public static Enchantment CALL_OF_THE_NIGHT = new NightEnchantment();




    public static void Initialize(){
        Registry.register(Registries.ENCHANTMENT,new Identifier(Fishe.MOD_ID,"call_of_the_night"), CALL_OF_THE_NIGHT);
    }

}
