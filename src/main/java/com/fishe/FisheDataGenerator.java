package com.fishe;

import com.fishe.data.provider.FisheEnLanguageProvider;
import com.fishe.data.provider.FisheModTagProvider;
import com.fishe.data.provider.FisheModelProvider;
import com.fishe.data.provider.FisheRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FisheDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(FisheModelProvider::new);
		pack.addProvider(FisheEnLanguageProvider::new);
		pack.addProvider(FisheRecipeProvider::new);
		//loot table go here
		pack.addProvider(FisheModTagProvider::new);

	}
}
