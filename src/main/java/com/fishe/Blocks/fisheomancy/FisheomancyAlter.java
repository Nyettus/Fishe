package com.fishe.Blocks.fisheomancy;

import com.fishe.Blocks.Entity.FisheomancyAlterBlockEntity;
import com.fishe.Fishe;
import com.fishe.Items.ItemsTools;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FisheomancyAlter extends BlockWithEntity implements BlockEntityProvider {
    public FisheomancyAlter(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FisheomancyAlterBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {

            if (!activateWithWand(state, world, pos, player)) {
                //This is where the screen handling stuff will go
                var entity = world.getBlockEntity(pos);
                ((FisheomancyAlterBlockEntity) entity).ValidateMultiblock();
            }


        }
        return ActionResult.SUCCESS;
    }

    private boolean activateWithWand(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (player.getMainHandStack().getItem() != ItemsTools.FISHE_STAFF) return false;
        var entity = world.getBlockEntity(pos);
        Fishe.LOGGER.info(""+(entity));
        if (entity instanceof FisheomancyAlterBlockEntity) {
            ((FisheomancyAlterBlockEntity) entity).detectExtenders(world, pos,state);
            return true;
        }
        return false;
    }
}
