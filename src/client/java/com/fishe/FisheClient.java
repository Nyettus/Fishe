package com.fishe;

import com.fishe.Blocks.BlockMaster;
import com.fishe.Items.ItemsTools;
import com.fishe.Items.ModFishingRodItem;
import com.fishe.Screen.ModScreenHandler;
import com.fishe.ScreenClient.FisheFermenterScreen;
import com.fishe.ScreenClient.FisheRepairTableScreen;
import com.fishe.ScreenClient.FisheomancyAltarScreen;
import com.fishe.ScreenClient.FisheomancyExtenderScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.util.Identifier;

public class FisheClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		InitRod();
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		HandledScreens.register(ModScreenHandler.FISHE_PASTER_SCREEN_HANDLER_SCREEN_HANDLER, FisheFermenterScreen::new);
		HandledScreens.register(ModScreenHandler.FISHE_REPAIR_TABLE_SCREEN_HANDLER_SCREEN_HANDLER, FisheRepairTableScreen::new);
		HandledScreens.register(ModScreenHandler.FISHEOMANCY_ALTAR_SCREEN_HANDLER_SCREEN_HANDLER, FisheomancyAltarScreen::new);
		HandledScreens.register(ModScreenHandler.FISHEOMANCY_EXPANDER_SCREEN_HANDLER_SCREEN_HANDLER, FisheomancyExtenderScreen::new);

		BlockRenderLayerMap.INSTANCE.putBlock(BlockMaster.FISHEOMANCY_ALTAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(BlockMaster.FISHEOMANCY_EXTENDER, RenderLayer.getCutout());
	}

	private static void InitRod() {
		ScaffoldInitRod(ItemsTools.COPPER_ROD);
		ScaffoldInitRod(ItemsTools.IRON_ROD);
		ScaffoldInitRod(ItemsTools.GOLD_ROD);
		ScaffoldInitRod(ItemsTools.DIAMOND_ROD);
	}

	private static void ScaffoldInitRod(ModFishingRodItem rod){
		ModelPredicateProviderRegistry.register(rod, new Identifier("cast"), (itemStack, clientWorld, livingEntity, seed) -> {
			if (livingEntity == null) {
				return 0.0F;
			} else {
				boolean bl = livingEntity.getMainHandStack() == itemStack;
				boolean bl2 = livingEntity.getOffHandStack() == itemStack;
				if (livingEntity.getMainHandStack().getItem() instanceof FishingRodItem) {
					bl2 = false;
				}

				return (bl || bl2) && livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).fishHook != null ? 1.0F : 0.0F;
			}
		});
	}

}