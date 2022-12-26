package com.ModernCrayfish.init;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.objects.blocks.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.ToIntFunction;

public class BlockInit {

    //Register the blocks
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModernCrayfish.MOD_ID);

    public static final RegistryObject<Block> CEILING_LIGHT = BLOCKS.register("ceiling_light",()-> new CeilingLightBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.2f).sound(SoundType.GLASS).notSolid().setLightLevel(litBlockEmission(15))));
    public static final RegistryObject<Block> CEILING_FAN = BLOCKS.register("ceiling_fan",()-> new CeilingFanBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.2f).sound(SoundType.WOOD).notSolid()));
    public static final RegistryObject<Block> LIGHT_SWITCH = BLOCKS.register("light_switch",()-> new LightSwitchBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.2f).sound(SoundType.STONE).notSolid()));
    public static final RegistryObject<Block> TOILET = BLOCKS.register("toilet",()-> new ToiletBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.2f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GOLDEN_TOILET = BLOCKS.register("golden_toilet",()-> new ToiletBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.2f).sound(SoundType.STONE)));

    public static final RegistryObject<Block> MIRROR = BLOCKS.register("mirror",()-> new MirrorBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> CUP  = BLOCKS.register("cup",()-> new CupBlock(AbstractBlock.Properties.create(Material.GLASS)));
    public static final RegistryObject<Block> CUTTING_BOARD  = BLOCKS.register("cutting_board",()-> new CuttingBoardBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> TOASTER  = BLOCKS.register("toaster",()-> new ToasterBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> BLENDER  = BLOCKS.register("blender",()-> new BlenderBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> COOKIE_JAR  = BLOCKS.register("cookie_jar",()-> new CookieJarBlock(AbstractBlock.Properties.create(Material.GLASS)));
    public static final RegistryObject<Block> MEDICINE_CABINET  = BLOCKS.register("medicine_cabinet",()-> new MedicineCabinetBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> STOVE  = BLOCKS.register("stove",()-> new StoveBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> MICROWAVE  = BLOCKS.register("microwave",()-> new MicrowaveBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> PLATE  = BLOCKS.register("plate",()-> new PlateBlock(AbstractBlock.Properties.create(Material.ROCK)));

    //MODERN BLOCKS
    public static final RegistryObject<Block> MODERN_WINDOW = BLOCKS.register("modern_window",()-> new ModernWindowBlock(AbstractBlock.Properties.create(Material.ROCK)));
   // public static final RegistryObject<Block> MODERN_BED = BLOCKS.register("modern_bed",()-> new ModernBedBlock(AbstractBlock.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> MODERN_DOOR  = BLOCKS.register("modern_door",()-> new ModernDoorBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> MODERN_TABLE = BLOCKS.register("modern_table",()-> new ModernTableBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> MODERN_LIGHT = BLOCKS.register("modern_light",()-> new ModernLightBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.2f).sound(SoundType.STONE).notSolid().setLightLevel(litBlockEmission(15))));

    //COLORED ITEMS
    public static final RegistryObject<Block> LAMP  = BLOCKS.register("lamp",()-> new LampBlock(AbstractBlock.Properties.create(Material.ROCK)));
    public static final RegistryObject<Block> BAR_SEAT  = BLOCKS.register("bar_seat",()-> new LampBlock(AbstractBlock.Properties.create(Material.ROCK)));

    private static ToIntFunction<BlockState> litBlockEmission(int p_235420_0_) {
        return (p_235421_1_) -> {
            return p_235421_1_.get(BlockStateProperties.LIT) ? p_235420_0_ : 0;
        };
    }
}