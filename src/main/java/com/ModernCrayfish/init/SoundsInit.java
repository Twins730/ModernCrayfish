package com.ModernCrayfish.init;

import com.ModernCrayfish.ModernCrayfish;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundsInit {

    //Register Sounds
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModernCrayfish.MOD_ID);

    public static final RegistryObject<SoundEvent> LIGHT_SWITCH = SOUNDS.register("light_switch", ()-> new SoundEvent(new ResourceLocation(ModernCrayfish.MOD_ID,"block.light_switch")));
    public static final RegistryObject<SoundEvent> FART = SOUNDS.register("fart", ()-> new SoundEvent(new ResourceLocation(ModernCrayfish.MOD_ID,"entity.fart")));

}
