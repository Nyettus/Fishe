package com.fishe.Blocks.fisheomancy;

import com.fishe.Blocks.Entity.FisheomancyAltarBlockEntity;
import com.fishe.Blocks.Entity.ModBlockEntities;
import com.fishe.Items.ItemsTools;
import net.minecraft.block.*;
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

public class FisheomancyAltarBlock extends BlockWithEntity implements BlockEntityProvider {
    public FisheomancyAltarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FisheomancyAltarBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock()!=newState.getBlock()){
            BlockEntity thisEntity = world.getBlockEntity(pos);
            if(thisEntity instanceof FisheomancyAltarBlockEntity){
                ItemScatterer.spawn(world,pos,(FisheomancyAltarBlockEntity) thisEntity);
                world.updateComparators(pos,this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {

            if (!activateWithWand(state, world, pos, player)) {
                NamedScreenHandlerFactory screenHandlerFactory = ((FisheomancyAltarBlockEntity) world.getBlockEntity(pos));
                if(screenHandlerFactory !=null) player.openHandledScreen(screenHandlerFactory);
                var entity = world.getBlockEntity(pos);
                ((FisheomancyAltarBlockEntity) entity).ValidateMultiblock();
            }


        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if(world.isClient()) return;
        if(world.isReceivingRedstonePower(pos)){
            var entity = world.getBlockEntity(pos);
            if(entity instanceof FisheomancyAltarBlockEntity){
                ((FisheomancyAltarBlockEntity)entity).AttemptCraft(world);
            }
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.FISHEOMANCY_ALTAR_BLOCK_ENTITY_BLOCK_ENTITY,((world1, pos, state1, blockEntity) -> blockEntity.tick(world1,pos,state1)));
    }

    private boolean activateWithWand(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (player.getMainHandStack().getItem() != ItemsTools.FISHE_STAFF) return false;
        var entity = world.getBlockEntity(pos);
        if (entity instanceof FisheomancyAltarBlockEntity) {
            ((FisheomancyAltarBlockEntity) entity).detectExtenders(world, pos,state);
            return true;
        }
        return false;
    }
}
