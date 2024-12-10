package com.fishe;

import com.fishe.data.provider.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FisheDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(FisheModelProvider::new);
		pack.addProvider(FisheEnLanguageProvider::new);
		pack.addProvider(FisheRecipeProvider::new);
		pack.addProvider(FisheModBlockLootTableProvider::new);
		pack.addProvider(FisheModTagProvider::new);
		pack.addProvider(FisheModBlockTagProvider::new);

	}
}
