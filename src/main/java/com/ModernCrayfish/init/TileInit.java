package com.ModernCrayfish.init;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.objects.tileEntity.CeilingFanTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileInit {

    //Register Tiles
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModernCrayfish.MOD_ID);

    public static final RegistryObject<TileEntityType<CeilingFanTileEntity>> CEILING_FAN_TILE = TILE_ENTITIES.register("ceiling_fan",()-> TileEntityType.Builder.of(CeilingFanTileEntity::new,BlockInit.CEILING_FAN.get()).build(null));

}
