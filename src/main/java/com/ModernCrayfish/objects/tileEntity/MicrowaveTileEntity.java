package com.ModernCrayfish.objects.tileEntity;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.init.SoundsInit;
import com.ModernCrayfish.init.TileInit;
import com.ModernCrayfish.objects.container.MicrowaveContainer;
import com.google.common.collect.Maps;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

public class MicrowaveTileEntity extends LockableLootTileEntity implements IClearable, ITickableTileEntity {

    private NonNullList<ItemStack> itemStack = NonNullList.withSize(1, ItemStack.EMPTY);
    public int invSize = 1;
    public final int maxCookProgress = 27;
    public boolean cooking;
    public IntArray holder = new IntArray(1);
    public boolean finishedCooking;
    public int tick;

    public MicrowaveTileEntity() {
        super(TileInit.MICROWAVE_TILE.get());
    }

    @Override
    public void tick() {
        assert world != null;
        if (!world.isRemote()) {
            if (cooking && !itemStack.get(0).isEmpty()) {
                if (holder.get(0) == maxCookProgress) {
                    this.holder.set(0, 0);
                    this.cook();
                    this.cooking = false;
                } else {
                    this.holder.set(0, holder.get(0) + 1);
                }
                world.notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
            } else {
                this.holder.set(0, 0);
                this.cooking = false;
            }
        } else {
            if(holder.get(0) == maxCookProgress && !finishedCooking){
                this.finishedCooking = true;
            }
            if(cooking && !itemStack.get(0).isEmpty()) {
                if (tick == 20) {
                    this.tick = 0;
                }
                if(tick == 0){
                    world.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundsInit.MICROWAVE_RUNNING.get(), SoundCategory.BLOCKS, 0.75F, 1.0F, false);
                }
                tick++;
            }
        }
    }


    public void cook() {
        ItemStack itemstack = this.itemStack.get(0);
        if (!itemstack.isEmpty()) {
            IInventory iinventory = new Inventory(itemstack);
            ItemStack itemstack1 = this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, iinventory, this.world).map((campfireRecipe) -> {
                return campfireRecipe.getCraftingResult(iinventory);
            }).orElse(itemstack);

            this.itemStack.set(0, itemstack1);
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return itemStack;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.itemStack = itemsIn;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("modern_crayfish.container.microwave");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new MicrowaveContainer(id,player, this, holder);
    }

    public void startMicrowave(){
        this.cooking = true;

    }

    @Override
    public int getSizeInventory() {
        return invSize;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.itemStack);
        }
        compound.putBoolean("cooking",cooking);
        compound.putInt("cookProgress",holder.get(0));
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.itemStack = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(nbt)) {
            ItemStackHelper.loadAllItems(nbt, this.itemStack);
        }
        this.cooking = nbt.getBoolean("cooking");
        this.holder.set(0,nbt.getInt("cookProgress"));
    }


    public Optional<CampfireCookingRecipe> findMatchingRecipe(ItemStack itemStackIn) {
        return this.itemStack.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, new Inventory(itemStackIn), this.world);
    }

    // Synchronization

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(pos, 1, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        assert world != null;
        this.read(world.getBlockState(pos),pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state,tag);
        super.handleUpdateTag(state, tag);
    }
}
