package com.fishe.Blocks.Entity;

import com.fishe.Fishe;
import com.fishe.Utils.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Pseudo;


public class FisheomancyAlterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(7, ItemStack.EMPTY);

    private boolean attemptCraft = false;

    /**
     * Give the position of a possible extender relative to an origin
     * Vector2i x = x and y = z
     * as world Y positions are irrelevant
     */
    class ExtenderPositions {
        public Vector2i forwardPos;
        public Vector2i rightPos;
        public Vector2i leftPos;

        public ExtenderPositions(Vector2i forward, Vector2i right, Vector2i left) {
            forwardPos = forward;
            rightPos = right;
            leftPos = left;
        }
    }

    //The extenders will change recipes and are sided
    private @Nullable FisheomancyExtenderBlockEntity forwardExtender;
    private @Nullable FisheomancyExtenderBlockEntity rightExtender;
    private @Nullable FisheomancyExtenderBlockEntity leftExtender;

    private enum ExtenderPositionsEnum {
        forward,
        right,
        left
    }

    private final ExtenderPositions[] extenderDirections = new ExtenderPositions[]{
            //north facing (-Z)
            new ExtenderPositions(new Vector2i(0, -2),
                    new Vector2i(2, 1),
                    new Vector2i(-2, 1)),
            //east facing (+X)
            new ExtenderPositions(new Vector2i(2, 0),
                    new Vector2i(-1, 2),
                    new Vector2i(-1, -2)),
            //south facing (+Z)
            new ExtenderPositions(new Vector2i(0, 2),
                    new Vector2i(-2, -1),
                    new Vector2i(2, -1)),
            //west facing (-X)
            new ExtenderPositions(new Vector2i(-2, 0),
                    new Vector2i(1, -2),
                    new Vector2i(1, 2)),
    };

    public FisheomancyAlterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FISHEOMANCY_EXTENDER_BLOCK_ENTITY_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return null;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {

    }

    @Override
    public Text getDisplayName() {
        return null;
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }


    public void detectExtenders(World world, BlockPos pos) {
        int configurationsFound = 0;
        Fishe.LOGGER.info("Triggered extenders check");
        for (ExtenderPositions positions : extenderDirections) {
            configurationsFound += detectExtenderAtPos(positions, world, pos);
            if (configurationsFound > 1) {
                anullExtenders();
                Fishe.LOGGER.info("Too many possible configs");
                return;
            }
        }
        if (configurationsFound == 1) Fishe.LOGGER.info("Successful Config");
        else Fishe.LOGGER.info("No Configs");

    }

    /**
     * Calculates whether a direction is a valid configuration for the multiblock.
     * returns 0 if it is not, returns 1 if it is.
     */
    private int detectExtenderAtPos(ExtenderPositions positions, World world, BlockPos pos) {
        int hasChangedAny = 0;
        boolean forward = changePosition(positions.forwardPos, pos, world, ExtenderPositionsEnum.forward);
        boolean right = changePosition(positions.rightPos, pos, world, ExtenderPositionsEnum.right);
        boolean left = changePosition(positions.leftPos, pos, world, ExtenderPositionsEnum.left);
        if (forward || left || right) hasChangedAny = 1;

        return hasChangedAny;
    }

    private boolean changePosition(Vector2i modifier, BlockPos origin, World world, ExtenderPositionsEnum positionsEnum) {
        var checkingPosition = posToCheck(modifier, origin);
        var entity = world.getBlockEntity(checkingPosition);
        boolean asBool = entity instanceof FisheomancyExtenderBlockEntity;
        if (asBool) {
            switch (positionsEnum) {
                case forward -> forwardExtender = (FisheomancyExtenderBlockEntity) entity;
                case right -> rightExtender = (FisheomancyExtenderBlockEntity) entity;
                case left -> leftExtender = (FisheomancyExtenderBlockEntity) entity;
            }
        }
        return asBool;
    }


    private BlockPos posToCheck(Vector2i modifier, BlockPos origin) {
        return origin.add(modifier.x, 0, modifier.y);
    }

    private void anullExtenders() {
        forwardExtender = null;
        rightExtender = null;
        leftExtender = null;
    }
}
