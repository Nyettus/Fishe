package com.fishe.Blocks;

import com.fishe.Blocks.Entity.FisheFermenterBlockEntity;
import com.fishe.Blocks.Entity.FisheRepairTableBlockEntity;
import com.fishe.Blocks.Entity.ModBlockEntities;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FisheRepairTableBlock extends BlockWithEntity implements BlockEntityProvider {
    protected FisheRepairTableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FisheRepairTableBlockEntity(pos,state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {

        if(state.getBlock() != newState.getBlock()){
            BlockEntity entity = world.getBlockEntity(pos);
            if(entity instanceof FisheRepairTableBlockEntity){
                ItemScatterer.spawn(world,pos,(FisheRepairTableBlockEntity)entity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);

        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient){
            NamedScreenHandlerFactory screenHandlerFactory = ((FisheRepairTableBlockEntity) world.getBlockEntity(pos));
            if(screenHandlerFactory!=null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.FISHE_REPAIR_TABLE_BLOCK_ENTITY_BLOCK_ENTITY,((world1, pos, state1, blockEntity) -> blockEntity.tick(world1,pos,state1)));
    }
}
