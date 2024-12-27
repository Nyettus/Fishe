package com.fishe.Blocks.Entity;

import com.fishe.Screen.FisheomancyExtenderScreenHandler;
import com.fishe.Utils.ImplementedInventory;
import com.fishe.Utils.UsefulBox;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class FisheomancyExtenderBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, SidedInventory {
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public BlockPos controllerPos = null;
    public FisheomancyAltarBlockEntity.ExtenderPositionsEnum style;

    public FisheomancyExtenderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FISHEOMANCY_EXTENDER_BLOCK_ENTITY_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if(style!=null) nbt.putInt("fisheomancy_extender.style",style.ordinal());
        nbt.putLong("fisheomancy_extender.controller_pos", UsefulBox.BlockPosToLong(controllerPos));
        Inventories.writeNbt(nbt,inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        style =  FisheomancyAltarBlockEntity.ExtenderPositionsEnum.values()[nbt.getInt("fisheomancy_extender.style")];
        controllerPos = UsefulBox.LongToBlockPos(nbt.getLong("fisheomancy_extender.controller_pos"));
        Inventories.readNbt(nbt,inventory);
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
        return Text.literal("Fisheomancy Extender");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FisheomancyExtenderScreenHandler(syncId, playerInventory,this);
    }


    public void initialize(BlockPos pos, FisheomancyAltarBlockEntity.ExtenderPositionsEnum positionEnum){
        this.controllerPos = pos;
        this.style = positionEnum;
        this.markDirty();
    }


    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[]{0,1,2};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return inventory.get(slot).isEmpty();
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }
}