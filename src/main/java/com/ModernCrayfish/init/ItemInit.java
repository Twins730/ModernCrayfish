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
    public static final RegistryObject<Item> CEILING_FAN = ITEMS.register("ceiling_fan",()-> new BlockItem(BlockInit.CEILING_FAN.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> MODERN_LIGHT = ITEMS.register("modern_light",()-> new BlockItem(BlockInit.MODERN_LIGHT.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> LIGHT_SWITCH = ITEMS.register("light_switch",()-> new BlockItem(BlockInit.LIGHT_SWITCH.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> CEILING_FAN_FAN = ITEMS.register("ceiling_fan_fan",()-> new Item(new Item.Properties().stacksTo(1)));

}
