package com.fishe.Blocks.Entity;

import com.fishe.Blocks.BlockItems;
import com.fishe.Fishe;
import com.fishe.Items.ItemsFishe;
import com.fishe.Screen.FisheomancyAlterScreenHandler;
import com.fishe.Utils.ImplementedInventory;
import com.fishe.Utils.UsefulBox;
import com.google.common.base.FinalizableSoftReference;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;


public class FisheomancyAlterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);

    private static final int SLOP_SLOT = 0;
    private static final int CATALYST_SLOT = 1;
    private static final int[] BASIC_SLOT = {2, 3, 4, 5, 6};

    protected final PropertyDelegate propertyDelegate;

    private int slopAmount;
    private int slopMax = 512;
    private int progress;
    private int maxProgress = 200;

    /**
     * Give the position of a possible extender relative to an origin
     * Vector2i x = x and y = z
     * as world Y positions are irrelevant
     */
    static class ExtenderPositions {
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
    private @Nullable BlockPos forwardExtender;
    private @Nullable BlockPos rightExtender;
    private @Nullable BlockPos leftExtender;

    public enum ExtenderPositionsEnum {
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
        super(ModBlockEntities.FISHEOMANCY_ALTER_BLOCK_ENTITY_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FisheomancyAlterBlockEntity.this.slopAmount;
                    case 1 -> FisheomancyAlterBlockEntity.this.slopMax;
                    case 2 -> FisheomancyAlterBlockEntity.this.progress;
                    case 3 -> FisheomancyAlterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> FisheomancyAlterBlockEntity.this.slopAmount = value;
                    case 1 -> FisheomancyAlterBlockEntity.this.progress = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("fisheomancy_alter.slop_amount", slopAmount);

        nbt.putLong("fisheomancy_alter.forward_extender", UsefulBox.BlockPosToLong(forwardExtender));
        nbt.putLong("fisheomancy_alter.right_extender", UsefulBox.BlockPosToLong(rightExtender));
        nbt.putLong("fisheomancy_alter.left_extender", UsefulBox.BlockPosToLong(leftExtender));

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        Inventories.writeNbt(nbt, inventory);
        slopAmount = nbt.getInt("fisheomancy_alter.slop_amount");

        forwardExtender = UsefulBox.LongToBlockPos(nbt.getLong("fisheomancy_alter.forward_extender"));
        rightExtender = UsefulBox.LongToBlockPos(nbt.getLong("fisheomancy_alter.right_extender"));
        leftExtender = UsefulBox.LongToBlockPos(nbt.getLong("fisheomancy_alter.left_extender"));
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Alter");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FisheomancyAlterScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }

    //<editor-fold desc="Multiblock Section">

    public void detectExtenders(World world, BlockPos pos, BlockState state) {
        int configurationsFound = 0;
        Fishe.LOGGER.info("Triggered extenders check");
        for (ExtenderPositions positions : extenderDirections) {
            configurationsFound += detectExtenderAtPos(positions, world, pos);
            if (configurationsFound > 1) {
                anullExtenders();
                Fishe.LOGGER.info("Too many possible configs");
                markDirty(world, pos, state);
                return;
            }
        }
        if (configurationsFound == 1) Fishe.LOGGER.info("Successful Config");
        else Fishe.LOGGER.info("No Configs");
        markDirty(world, pos, state);
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
                case forward -> forwardExtender = checkingPosition;
                case right -> rightExtender = checkingPosition;
                case left -> leftExtender = checkingPosition;
            }
            ((FisheomancyExtenderBlockEntity) entity).initialize(origin, positionsEnum);

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


    public void ValidateMultiblock() {
        int amount = 0;
        if (forwardExtender != null) amount++;
        if (rightExtender != null) amount++;
        if (leftExtender != null) amount++;
        Fishe.LOGGER.info("Amount of multiblock is " + amount);
    }

    public void RemoveExtender(ExtenderPositionsEnum positionEnum) {
        switch (positionEnum) {
            case forward -> forwardExtender = null;
            case right -> rightExtender = null;
            case left -> leftExtender = null;
        }
        this.markDirty();
    }

//</editor-fold>

    //<editor-fold desc="Tick Section">
    public void tick(World world, BlockPos pos, BlockState state){
        if(world.isClient) return;
        tickSlopSection();
        tickCraftSection();

    }

    private void tickSlopSection(){
        if(slopAmount >= slopMax) return;
        if(this.getStack(SLOP_SLOT).getItem() == ItemsFishe.FISHE_PASTE){
            slopAmount++;
            this.removeStack(SLOP_SLOT,1);
        }
        else if(this.getStack(SLOP_SLOT).getItem() == BlockItems.FISHE_PASTE_BLOCK){
            slopAmount+=9;
            this.removeStack(SLOP_SLOT,1);
        }
    }

    private void tickCraftSection(){

    }



    //</editor-fold>

}
