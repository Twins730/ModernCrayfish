package com.ModernCrayfish;

import com.ModernCrayfish.init.BlockInit;
import com.ModernCrayfish.init.ItemInit;
import com.ModernCrayfish.init.SoundsInit;
import com.ModernCrayfish.init.TileInit;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//The main class for the Modern Crayfish mod
@Mod(ModernCrayfish.MOD_ID)
@Mod.EventBusSubscriber(modid = ModernCrayfish.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModernCrayfish {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "modern_crayfish";

    //Initialize the mod
    public ModernCrayfish() {
        LOGGER.info("ModID loading:" + MOD_ID + " in progress!");
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        //Register stuff
        BlockInit.BLOCKS.register(bus);
        ItemInit.ITEMS.register(bus);
        TileInit.TILE_ENTITIES.register(bus);
        SoundsInit.SOUNDS.register(bus);
        bus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("ModID loading:" + MOD_ID + " complete!");
    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlockInit.CEILING_LIGHT.get(), RenderType.translucent());
    }

    //The item grouping
    @Mod.EventBusSubscriber(modid = ModernCrayfish.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModernCrayfishGroup extends ItemGroup {

        public static final ModernCrayfishGroup MODERN_CRAYFISH = new ModernCrayfishGroup(ItemGroup.TABS.length, "modern_crayfish");

        public ModernCrayfishGroup(int Int, String label) {
            super(Int, label);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.ANVIL);
        }
    }

}
