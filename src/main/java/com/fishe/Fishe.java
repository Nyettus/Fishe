package com.fishe;

import com.fishe.Blocks.BlockMaster;
import com.fishe.Items.ItemMaster;
import com.fishe.Utils.FisheModTags;
import com.fishe.Utils.ModLootTableModifier;
import com.fishe.enchantments.EnchantmentMaster;
import com.fishe.recipe.ModRecipes;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Fishe implements ModInitializer {
	public static final String MOD_ID = "fishe";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		ItemMaster.Initialize();
		BlockMaster.Initialize();
		FisheModTags.Initialize();

		ModRecipes.Initialize();

		EnchantmentMaster.Initialize();
		ModLootTableModifier.ModifyFishTable();
	}
}