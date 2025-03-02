package com.fishe.Blocks.fisheomancy;

import com.fishe.Blocks.Entity.FisheomancyAltarBlockEntity;
import com.fishe.Blocks.Entity.FisheomancyExtenderBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FisheomancyExtenderBlock extends BlockWithEntity implements BlockEntityProvider {
    public FisheomancyExtenderBlock(Settings settings) {
        super(settings);
    }
    private static final VoxelShape SHAPE = VoxelShapes.cuboid(0,0,0,1,0.5,1);

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FisheomancyExtenderBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            var thisEntity = world.getBlockEntity(pos);
            if (thisEntity instanceof FisheomancyExtenderBlockEntity) {

                if (((FisheomancyExtenderBlockEntity) thisEntity).controllerPos != null) {
                    var controllerEntity = world.getBlockEntity(((FisheomancyExtenderBlockEntity) thisEntity).controllerPos);
                    if (controllerEntity instanceof FisheomancyAltarBlockEntity) {
                        ((FisheomancyAltarBlockEntity) controllerEntity).RemoveExtender(((FisheomancyExtenderBlockEntity) thisEntity).style);
                    }
                }
                ItemScatterer.spawn(world,pos,(FisheomancyExtenderBlockEntity) thisEntity);
                world.updateComparators(pos,this);


            }


            super.onStateReplaced(state, world, pos, newState, moved);
        }

    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {

            NamedScreenHandlerFactory screenHandlerFactory = ((FisheomancyExtenderBlockEntity) world.getBlockEntity(pos));
            if(screenHandlerFactory!=null){
                player.openHandledScreen(screenHandlerFactory);
            }


        }
        return ActionResult.SUCCESS;
    }
}