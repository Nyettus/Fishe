package com.fishe.Blocks.Entity;

import com.fishe.Blocks.BlockMaster;
import com.fishe.Fishe;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<FisheFermenterBlockEntity> FISHE_PASTER_BLOCK_ENTITY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Fishe.MOD_ID,"fishe_paster_be"),
                    FabricBlockEntityTypeBuilder.create(FisheFermenterBlockEntity::new, BlockMaster.FISHE_FERMENTER).build());



    public static void Initialize(){

    }

}
