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


    public static final BlockEntityType<FisheRepairTableBlockEntity> FISHE_REPAIR_TABLE_BLOCK_ENTITY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Fishe.MOD_ID,"fishe_repair_table_be"),
                    FabricBlockEntityTypeBuilder.create(FisheRepairTableBlockEntity::new,BlockMaster.FISHE_REPAIR_TABLE).build());

    public static final BlockEntityType<FisheomancyAlterBlockEntity> FISHEOMANCY_ALTER_BLOCK_ENTITY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Fishe.MOD_ID,"fisheomancy_alter_be"),
                    FabricBlockEntityTypeBuilder.create(FisheomancyAlterBlockEntity::new,BlockMaster.FISHEOMANCY_ALTER).build());

    public static final BlockEntityType<FisheomancyExtenderBlockEntity> FISHEOMANCY_EXTENDER_BLOCK_ENTITY_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Fishe.MOD_ID,"fisheomancy_extender_be"),
                    FabricBlockEntityTypeBuilder.create(FisheomancyExtenderBlockEntity::new,BlockMaster.FISHEOMANCY_EXTENDER).build());

    public static void Initialize(){

    }

}
