package com.ModernCrayfish.init;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.objects.blocks.LightSwitchBlock;
import com.ModernCrayfish.objects.blocks.MicrowaveBlock;
import com.ModernCrayfish.objects.tileEntity.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileInit {

    //Register Tiles
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModernCrayfish.MOD_ID);

    public static final RegistryObject<TileEntityType<CeilingFanTileEntity>> CEILING_FAN_TILE = TILE_ENTITIES.register("ceiling_fan",()-> TileEntityType.Builder.create(CeilingFanTileEntity::new,BlockInit.CEILING_FAN.get()).build(null));
    public static final RegistryObject<TileEntityType<LightSwitchTileEntity>> LIGHT_SWITCH_TILE = TILE_ENTITIES.register("light_switch",()-> TileEntityType.Builder.create(LightSwitchTileEntity::new,BlockInit.LIGHT_SWITCH.get()).build(null));
    public static final RegistryObject<TileEntityType<PlateTileEntity>> PLATE_TILE = TILE_ENTITIES.register("plate",()-> TileEntityType.Builder.create(PlateTileEntity::new,BlockInit.PLATE.get()).build(null));
    public static final RegistryObject<TileEntityType<CookieJarTileEntity>> COOKIE_JAR_TILE = TILE_ENTITIES.register("cookie_jar",()-> TileEntityType.Builder.create(CookieJarTileEntity::new,BlockInit.COOKIE_JAR.get()).build(null));
    public static final RegistryObject<TileEntityType<CuttingBoardTileEntity>> CUTTING_BOARD = TILE_ENTITIES.register("cutting_board",()-> TileEntityType.Builder.create(CuttingBoardTileEntity::new,BlockInit.CUTTING_BOARD.get()).build(null));
    public static final RegistryObject<TileEntityType<ToasterTileEntity>> TOASTER = TILE_ENTITIES.register("toaster",()-> TileEntityType.Builder.create(ToasterTileEntity::new,BlockInit.TOASTER.get()).build(null));
    public static final RegistryObject<TileEntityType<MirrorTileEntity>> MIRROR_TILE = TILE_ENTITIES.register("mirror",()-> TileEntityType.Builder.create(MirrorTileEntity::new, BlockInit.MIRROR.get()).build(null));
    public static final RegistryObject<TileEntityType<MicrowaveTileEntity>> MICROWAVE_TILE = TILE_ENTITIES.register("microwave",()-> TileEntityType.Builder.create(MicrowaveTileEntity::new, BlockInit.MICROWAVE.get()).build(null));

}
