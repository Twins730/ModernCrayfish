package com.ModernCrayfish.mixin;

import com.mrcrayfish.furniture.block.CoffeeTableBlock;
import com.mrcrayfish.furniture.block.FurnitureWaterloggedBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CoffeeTableBlock.class)
public class CoffeeTableMixin extends FurnitureWaterloggedBlock {

    public CoffeeTableMixin(Properties properties) {
        super(properties);
    }
}
