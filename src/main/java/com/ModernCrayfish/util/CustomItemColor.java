package com.ModernCrayfish.util;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class CustomItemColor implements IItemColor {
    private int color;

    public CustomItemColor(int color){
        this.color = color;
    }


    @Override
    public int getColor(ItemStack p_getColor_1_, int p_getColor_2_) {
        return color;
    }
}
