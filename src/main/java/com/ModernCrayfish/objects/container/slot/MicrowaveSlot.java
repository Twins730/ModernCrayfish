package com.ModernCrayfish.objects.container.slot;

import com.ModernCrayfish.objects.container.MicrowaveContainer;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;


public class MicrowaveSlot extends Slot {

    private final MicrowaveContainer container;

    public MicrowaveSlot(MicrowaveContainer container, IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.container = container;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return container.hasRecipe(stack);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }
}
