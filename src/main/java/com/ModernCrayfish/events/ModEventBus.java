package com.ModernCrayfish.events;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.init.BlockInit;
import com.ModernCrayfish.init.ItemInit;
import com.ModernCrayfish.util.Constants;
import com.ModernCrayfish.util.CustomBlockColor;
import com.ModernCrayfish.util.CustomItemColor;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItem;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModEventBus {

    /*
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void registerBlockColourHandlers(final ColorHandlerEvent.Block event)
    {
        // Use the colour of the biome or the default colour
        final IBlockColor grassColourHandler = (state, blockAccess, pos, tintIndex) -> {

            if (blockAccess != null && pos != null)
            {
                return BiomeColors.getFoliageColor(blockAccess, pos);
            }

            return GrassColors.get(0.5D, 1.0D);
        };

        event.getBlockColors().register(grassColourHandler, BlockInit.TOILET.get());
    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void registerItemColourHandlers(final ColorHandlerEvent.Item event)
    {
        // Use the Block's colour handler for an ItemBlock
        final IItemColor itemBlockColourHandler = (stack, tintIndex) -> {
            BlockState iblockstate = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
            return event.getBlockColors().getColor(iblockstate, null, null, tintIndex);
        };

        event.getItemColors().register(itemBlockColourHandler, BlockInit.TOILET.get());
    }
     */

    public static void registerBlockColors(ColorHandlerEvent.Block event){
        // Register the blocks to the water color modifier
        event.getBlockColors().register(Constants.waterColor, BlockInit.TOILET.get(),BlockInit.GOLDEN_TOILET.get());
    }

    public static void registerItemColors(ColorHandlerEvent.Item event){
        // Register the items to the water color modifier
        event.getItemColors().register(new CustomItemColor(0x3D8EFF), ItemInit.TOILET.get(), ItemInit.GOLDEN_TOILET.get());
    }


}
