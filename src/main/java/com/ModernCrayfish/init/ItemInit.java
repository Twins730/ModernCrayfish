package com.ModernCrayfish.init;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.objects.item.LightSwitchItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.util.datafix.fixes.ShulkerBoxItemColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    //Register the Items
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModernCrayfish.MOD_ID);

    public static final RegistryObject<Item> CEILING_LIGHT = ITEMS.register("ceiling_light",()-> new BlockItem(BlockInit.CEILING_LIGHT.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> CEILING_FAN = ITEMS.register("ceiling_fan",()-> new BlockItem(BlockInit.CEILING_FAN.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> LIGHT_SWITCH = ITEMS.register("light_switch",()-> new BlockItem(BlockInit.LIGHT_SWITCH.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> TOILET = ITEMS.register("toilet",()-> new BlockItem(BlockInit.TOILET.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> GOLDEN_TOILET = ITEMS.register("golden_toilet",()-> new BlockItem(BlockInit.GOLDEN_TOILET.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));

    public static final RegistryObject<Item> CUP = ITEMS.register("cup",()-> new BlockItem(BlockInit.CUP.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> CUTTING_BOARD = ITEMS.register("cutting_board",()-> new BlockItem(BlockInit.CUTTING_BOARD.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> BLENDER = ITEMS.register("blender",()-> new BlockItem(BlockInit.BLENDER.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> COOKIE_JAR = ITEMS.register("cookie_jar",()-> new BlockItem(BlockInit.COOKIE_JAR.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> STOVE = ITEMS.register("stove",()-> new BlockItem(BlockInit.STOVE.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> MEDICINE_CABINET = ITEMS.register("medicine_cabinet",()-> new BlockItem(BlockInit.MEDICINE_CABINET.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> TOASTER = ITEMS.register("toaster",()-> new BlockItem(BlockInit.TOASTER.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> MICROWAVE = ITEMS.register("microwave",()-> new BlockItem(BlockInit.MICROWAVE.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> PLATE = ITEMS.register("plate",()-> new BlockItem(BlockInit.PLATE.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));

    //MODERN BLOCKS
    public static final RegistryObject<Item> MODERN_DOOR = ITEMS.register("modern_door",()-> new BlockItem(BlockInit.MODERN_DOOR.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> MODERN_WINDOW = ITEMS.register("modern_window",()-> new BlockItem(BlockInit.MODERN_WINDOW.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> MODERN_BED = ITEMS.register("modern_bed",()-> new BlockItem(BlockInit.MODERN_BED.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> MODERN_TABLE = ITEMS.register("modern_table",()-> new BlockItem(BlockInit.MODERN_TABLE.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> MODERN_LIGHT = ITEMS.register("modern_light",()-> new BlockItem(BlockInit.MODERN_LIGHT.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));

    //COLORED ITEMS
    public static final RegistryObject<Item> LAMP = ITEMS.register("lamp",()-> new BlockItem(BlockInit.LAMP.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));
    public static final RegistryObject<Item> BAR_SEAT = ITEMS.register("bar_seat",()-> new BlockItem(BlockInit.BAR_SEAT.get(),new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).stacksTo(64)));

    //NON USABLE ITEMS
    public static final RegistryObject<Item> CEILING_FAN_FAN = ITEMS.register("ceiling_fan_fan",()-> new Item(new Item.Properties().stacksTo(1)));


    //FOOD
    public static final RegistryObject<Item> TOAST = ITEMS.register("toast", ()-> new Item(new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).food(new Food.Builder().nutrition(4).saturationMod(1.0f).build())));
    public static final RegistryObject<Item> BREAD_SLICE = ITEMS.register("bread_slice", ()-> new Item(new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH).food(new Food.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final RegistryObject<Item> CUTTING_KNIFE = ITEMS.register("cutting_knife", ()-> new Item(new Item.Properties().tab(ModernCrayfish.ModernCrayfishGroup.MODERN_CRAYFISH)));

}
