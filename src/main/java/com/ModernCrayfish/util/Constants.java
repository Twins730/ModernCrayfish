package com.ModernCrayfish.util;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.world.biome.BiomeColors;

public class Constants {

    public static final IBlockColor waterColor = (p_228060_0_, p_228060_1_, p_228060_2_, p_228060_3_) -> p_228060_1_ != null && p_228060_2_ != null ? BiomeColors.getWaterColor(p_228060_1_, p_228060_2_) : -1;

}
