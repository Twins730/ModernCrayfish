package com.ModernCrayfish;

import com.ModernCrayfish.events.ModEventBus;
import com.ModernCrayfish.init.*;
import com.ModernCrayfish.objects.entity.SeatEntity;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.*;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
        ContainerInit.CONTAINERS.register(bus);
        SoundsInit.SOUNDS.register(bus);
        EntityInit.ENTITIES.register(bus);

        // Set up the colors via the Mod Event Bus
        bus.addListener(ModEventBus::registerBlockColors);
        bus.addListener(ModEventBus::registerItemColors);

        // FMLClientSetup Bus
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        LOGGER.info("ModID loading:" + MOD_ID + " complete!");
    }


    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.getRidingEntity() instanceof SeatEntity) {
            if (((SeatEntity) event.player.getRidingEntity()).toilet) {
                if (KeyBindingInit.KEY_FART.isPressed()) {
                    event.player.getRidingEntity().playSound(SoundsInit.FART.get(), 0.5f, 0.3f);
                }
            }
        }
    }

    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event){
      //  MirrorTileEntityRenderer.tick(event);
    }

    @SubscribeEvent
    public void onClientWorldUnload(WorldEvent.Unload event) {
        //if(event.getWorld() instanceof ClientWorld) {
           // MirrorTileEntityRenderer.clearRegisteredMirrors();
       // }
    }


    public void onCommonSetup(FMLCommonSetupEvent event) {
       PacketInit.init();
    }


    // The item grouping
    @Mod.EventBusSubscriber(modid = ModernCrayfish.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModernCrayfishGroup extends ItemGroup {

        public static final ModernCrayfishGroup MODERN_CRAYFISH = new ModernCrayfishGroup(ItemGroup.GROUPS.length, "modern_crayfish");

        public ModernCrayfishGroup(int Int, String label) {
            super(Int, label);
        }

        public ItemStack createIcon() {
            return new ItemStack(Items.ANVIL);
        }
    }

}
