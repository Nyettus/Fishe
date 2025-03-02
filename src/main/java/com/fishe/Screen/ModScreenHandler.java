package com.fishe.Screen;

import com.fishe.Fishe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandler {
    public static final ScreenHandlerType<FisheFermenterScreenHandler> FISHE_PASTER_SCREEN_HANDLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Fishe.MOD_ID, "fishe_paster_ui"),
                    new ExtendedScreenHandlerType<>(FisheFermenterScreenHandler::new));

    public static final ScreenHandlerType<FisheRepairTableScreenHandler> FISHE_REPAIR_TABLE_SCREEN_HANDLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Fishe.MOD_ID, "fishe_repair_table_ui"),
                    new ExtendedScreenHandlerType<>(FisheRepairTableScreenHandler::new));

    public static final ScreenHandlerType<FisheomancyAltarScreenHandler> FISHEOMANCY_ALTAR_SCREEN_HANDLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Fishe.MOD_ID, "fisheomancy_altar_ui"),
                    new ExtendedScreenHandlerType<>(FisheomancyAltarScreenHandler::new));

    public static final ScreenHandlerType<FisheomancyExtenderScreenHandler> FISHEOMANCY_EXPANDER_SCREEN_HANDLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(Fishe.MOD_ID, "fisheomancy_expander_ui"),
                    new ExtendedScreenHandlerType<>(FisheomancyExtenderScreenHandler::new));



    public static void Initialize() {

    }

}
