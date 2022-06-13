package com.ModernCrayfish.init;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.objects.blocks.LightBlock;
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

    public static final RegistryObject<Block> CEILING_LIGHT = BLOCKS.register("ceiling_light",()-> new LightBlock(AbstractBlock.Properties.of(Material.GLASS).strength(0.2f).sound(SoundType.GLASS).noOcclusion().lightLevel(litBlockEmission(15))));


    private static ToIntFunction<BlockState> litBlockEmission(int p_235420_0_) {
        return (p_235421_1_) -> {
            return p_235421_1_.getValue(BlockStateProperties.LIT) ? p_235420_0_ : 0;
        };
    }
}
