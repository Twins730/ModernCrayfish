package com.ModernCrayfish.events;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.client.renderer.entity.MirrorEntityRenderer;
import com.ModernCrayfish.client.renderer.entity.SeatEntityRenderer;
import com.ModernCrayfish.client.renderer.screen.MicrowaveScreen;
import com.ModernCrayfish.client.renderer.tile.*;
import com.ModernCrayfish.init.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ModernCrayfish.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBus {

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event) {
        // Setup block rendering
        RenderTypeLookup.setRenderLayer(BlockInit.CEILING_LIGHT.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.COOKIE_JAR.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.CUP.get(), RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.MODERN_WINDOW.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.MODERN_TABLE_OUTDOOR.get(), RenderType.getCutout());
        ModernCrayfish.LOGGER.info("Loading render layers for:" + ModernCrayfish.MOD_ID + " complete!");
        // Bind Tiles to their renderers
        ClientRegistry.bindTileEntityRenderer(TileInit.CEILING_FAN_TILE.get(), CeilingFanTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileInit.PLATE_TILE.get(), PlateTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileInit.COOKIE_JAR_TILE.get(), CookieJarTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileInit.CUTTING_BOARD.get(), CuttingBoardTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileInit.TOASTER.get(), ToasterTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileInit.MIRROR_TILE.get(), MirrorTileEntityRenderer::new);
        ModernCrayfish.LOGGER.info("Loading bindTileEntityRenderer for:" + ModernCrayfish.MOD_ID + " complete!");
        // Register Entity renderer to the entity
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.SEAT_ENTITY.get(), SeatEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.MIRROR_ENTITY.get(), MirrorEntityRenderer::new);
        ModernCrayfish.LOGGER.info("Loading registerEntityRenderingHandler for:" + ModernCrayfish.MOD_ID + " complete!");
        // Register Keybindings
        ClientRegistry.registerKeyBinding(KeyBindingInit.KEY_FART);
        ModernCrayfish.LOGGER.info("Loading registerKeyBinding for:" + ModernCrayfish.MOD_ID + " complete!");
        // Register screen factories
        ScreenManager.registerFactory(ContainerInit.MICROWAVE_CONTAINER.get(), MicrowaveScreen::new);
        ModernCrayfish.LOGGER.info("Loaing registerFactory for:" + ModernCrayfish.MOD_ID + " complete!");
    }




}
