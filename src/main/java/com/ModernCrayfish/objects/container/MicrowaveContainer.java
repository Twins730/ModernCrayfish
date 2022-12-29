package com.ModernCrayfish.objects.container;

import com.ModernCrayfish.init.ContainerInit;
import com.ModernCrayfish.objects.container.slot.MicrowaveSlot;
import com.ModernCrayfish.objects.tileEntity.MicrowaveTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

public class MicrowaveContainer extends Container {

    public MicrowaveTileEntity tileEntity;
    public final World world;
    public IIntArray data;

    public MicrowaveContainer(final int windowId, final PlayerInventory playerInventory, PacketBuffer data) {
        this(windowId, playerInventory, new MicrowaveTileEntity(), new IntArray(1));
    }

    public MicrowaveContainer(int id, PlayerInventory playerInventory, MicrowaveTileEntity tileEntity, IIntArray data) {
        super(ContainerInit.MICROWAVE_CONTAINER.get(), id);
        this.tileEntity = tileEntity;
        this.data = data;
        this.world = playerInventory.player.world;
        tileEntity.openInventory(playerInventory.player);
        this.addSlot(new MicrowaveSlot(this,tileEntity,0,65,42));


        int i = (3 - 4) * 18 + 7;
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
        }

        this.trackIntArray(data);
    }

    public boolean hasRecipe(ItemStack stack) {
        return this.world.getRecipeManager().getRecipe(IRecipeType.CAMPFIRE_COOKING, new Inventory(stack), this.world).isPresent();
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemCopy = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack item = slot.getStack();
            itemCopy = item.copy();

            if (index < 1) {
                if (!this.mergeItemStack(item, 1, this.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.hasRecipe(item)) {
                if (!this.mergeItemStack(item, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 27) {
                if (!this.mergeItemStack(item, 27, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(item, 1, 27, false)) {
                return ItemStack.EMPTY;
            }

            if (item.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemCopy;
    }

    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.tileEntity.closeInventory(playerEntity);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }


}
