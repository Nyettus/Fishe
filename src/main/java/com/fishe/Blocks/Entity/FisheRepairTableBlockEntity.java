package com.fishe.Blocks.Entity;

import com.fishe.Blocks.BlockItems;
import com.fishe.Items.ItemsFishe;
import com.fishe.Screen.FisheRepairTableScreenHandler;
import com.fishe.Utils.FisheModTags;
import com.fishe.Utils.ImplementedInventory;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FisheRepairTableBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4,ItemStack.EMPTY);

    private static final int SLOP_SLOT = 0;
    private static final int[] TOOL_SLOTS = {1,2,3};

    protected final PropertyDelegate propertyDelegate;
    private int slop =0;
    private int maxSlop = 128;

    public FisheRepairTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FISHE_REPAIR_TABLE_BLOCK_ENTITY_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> FisheRepairTableBlockEntity.this.slop;
                    case 1 -> FisheRepairTableBlockEntity.this.maxSlop;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                FisheRepairTableBlockEntity.this.slop = value;
            }

            @Override
            public int size() {
                return 2;
            }
        };
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
        return Text.literal("Fishe Repair Table");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,inventory);
        nbt.putInt("fishe_repair_table.slop",slop);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,inventory);
        slop = nbt.getInt("fishe_repair_table.slop");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FisheRepairTableScreenHandler(syncId, playerInventory, this,this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos,  BlockState state){
        if(world.isClient) return;
        tickSlopSection();
        tickRepairSection();


    }

   //Tool Repair Section
    private void tickRepairSection(){
        ItemStack randomTool = returnRandomTool();
        if(randomTool == null) return;
        repairRandomTool(randomTool);
    }

    private ItemStack returnRandomTool(){
        List<Integer> indexOfTools = new ArrayList<>();
        for(int i : TOOL_SLOTS){
            var holding = inventory.get(i);
            if(holding.isIn(FisheModTags.PASTE_REPAIRABLE)){
                indexOfTools.add(i);
            }
        }
        if(indexOfTools.isEmpty())
        return null;
        else{
            Random random = new Random();
            int randomToolIndex = indexOfTools.get(random.nextInt(indexOfTools.size()));
            return inventory.get(randomToolIndex);
        }
    }

    private boolean repairRandomTool(ItemStack tool){
        if(canBeRepaired(tool)&&slop>0){
            tool.setDamage(tool.getDamage()-1);
            slop--;
        }

        return false;
    }

    private boolean canBeRepaired(ItemStack tool){
        return !(tool.getDamage()==0 || (!tool.isDamageable()));
    }

    //Slop Accumulate section
    private void tickSlopSection(){
        if(slop >= maxSlop) return;
        if(this.getStack(SLOP_SLOT).getItem() == ItemsFishe.FISHE_PASTE){
            slop++;
            this.removeStack(SLOP_SLOT,1);
        }
        else if(this.getStack(SLOP_SLOT).getItem() == BlockItems.FISHE_PASTE_BLOCK){
            slop+=9;
            this.removeStack(SLOP_SLOT,1);
        }
    }






}
