package com.fishe.Blocks.Entity;

import com.fishe.Fishe;
import com.fishe.Items.ItemsFishe;
import com.fishe.Items.ItemsTools;
import com.fishe.Screen.FishePasterScreenHandler;
import com.fishe.Utils.ImplementedInventory;
import com.fishe.recipe.FishePasterRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FishePasterBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    public FishePasterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FISHE_PASTER_BLOCK_ENTITY_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FishePasterBlockEntity.this.progress;
                    case 1 -> FishePasterBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FishePasterBlockEntity.this.progress = value;
                    case 1 -> FishePasterBlockEntity.this.progress = value;
                }
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
        return Text.literal("Fishe Paster");
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,inventory);
        nbt.putInt("fishe_paster.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,inventory);
        progress = nbt.getInt("fishe_paster.progress");

    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FishePasterScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient) return;

        if(isOutputtable()){
            if(this.hasRecipe()){
                this.increaseCraftProgress();
                markDirty(world,pos,state);

                if(finishCrafting()){
                    this.craftItem();
                    this.resetProgress();
                }
            }
            else{
                this.resetProgress();
            }
        }
        else{
            this.resetProgress();
            markDirty(world,pos,state);
        }



    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void craftItem() {
        var recipe = getCurrentRecipe();
        this.removeStack(INPUT_SLOT,1);

        ItemStack result = recipe.get().getOutput(null);
        this.setStack(OUTPUT_SLOT,new ItemStack(result.getItem(),getStack(OUTPUT_SLOT).getCount()+result.getCount()));
    }

    private boolean finishCrafting() {
        return progress>=maxProgress;
    }

    private void increaseCraftProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for(int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }
        var hasInput = getCurrentRecipe();


        return hasInput.isPresent() && canInsertAmount(hasInput.get().getOutput(null)) && canInsertItem(hasInput.get().getOutput(null).getItem());
    }

    private Optional<FishePasterRecipe> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for(int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }

        return getWorld().getRecipeManager().getFirstMatch(FishePasterRecipe.Type.INSTANCE, inv, getWorld());
    }

    private boolean canInsertItem(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmount(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).getCount() + output.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean isOutputtable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }
}
