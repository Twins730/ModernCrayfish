package com.ModernCrayfish;

import com.ModernCrayfish.client.renderer.entity.SeatEntityRenderer;
import com.ModernCrayfish.client.renderer.tile.*;
import com.ModernCrayfish.init.*;
import com.ModernCrayfish.objects.entity.SeatEntity;
import com.ModernCrayfish.objects.tileEntity.CookieJarTileEntity;
import com.ModernCrayfish.objects.tileEntity.PlateTileEntity;
import com.ModernCrayfish.util.Constants;
import com.ModernCrayfish.util.CustomBlockColor;
import com.ModernCrayfish.util.CustomItemColor;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.GameSettings;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.*;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.utils.Log;

import javax.swing.text.html.parser.Entity;
import java.util.Objects;

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
        EntityInit.ENTITIES.register(bus);

        // Set up the colors via the Mod Event Bus
        bus.addListener(this::registerBlockColors);
        bus.addListener(this::registerItemColors);

        // FMLClientSetup Bus
        bus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("ModID loading:" + MOD_ID + " complete!");
    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        // Setup block rendering
        RenderTypeLookup.setRenderLayer(BlockInit.CEILING_LIGHT.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.COOKIE_JAR.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlockInit.CUP.get(), RenderType.translucent());

        // Bind Tiles to their renderers
        ClientRegistry.bindTileEntityRenderer(TileInit.CEILING_FAN_TILE.get(), CeilingFanTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileInit.PLATE_TILE.get(), PlateTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileInit.COOKIE_JAR_TILE.get(), CookieJarTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileInit.CUTTING_BOARD.get(), CuttingBoardTileEntityRenderer::new);

        // Register Entity renderer to the entity
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.SEAT_ENTITY.get(), SeatEntityRenderer::new);

        // Register Keybindings
        ClientRegistry.registerKeyBinding(KeyBindingInit.KEY_FART);
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.getVehicle() instanceof SeatEntity) {
            if (((SeatEntity) event.player.getVehicle()).toilet) {
                if (KeyBindingInit.KEY_FART.consumeClick()) {
                    event.player.getVehicle().playSound(SoundsInit.FART.get(), 0.5f, 0.3f);
                }
            }
        }
    }


    public void registerBlockColors(ColorHandlerEvent.Block event){
        // Register the blocks to the water color modifier
        event.getBlockColors().register(Constants.waterColor, BlockInit.TOILET.get(),BlockInit.GOLDEN_TOILET.get());
    }


    public void registerItemColors(ColorHandlerEvent.Item event){
        // Register the items to the water color modifier
        event.getItemColors().register(new CustomItemColor(0x3D8EFF), ItemInit.TOILET.get(), ItemInit.GOLDEN_TOILET.get());
    }



    // The item grouping
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
