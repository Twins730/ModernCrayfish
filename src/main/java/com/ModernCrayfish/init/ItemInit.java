package com.ModernCrayfish.init;

import com.ModernCrayfish.ModernCrayfish;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    //Register the Items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModernCrayfish.MOD_ID);

    public static final RegistryObject<Item> CEILING_LIGHT = ITEMS.register("ceiling_light",()-> new BlockItem(BlockInit.CEILING_LIGHT.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));

}
