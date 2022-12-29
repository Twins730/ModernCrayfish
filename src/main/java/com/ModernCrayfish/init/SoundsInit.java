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

    public static final RegistryObject<SoundEvent> LIGHT_SWITCH = SOUNDS.register("light_switch", ()-> new SoundEvent(new ResourceLocation(ModernCrayfish.MOD_ID,"modern_crayfish.block.light_switch")));
    public static final RegistryObject<SoundEvent> FART = SOUNDS.register("fart", ()-> new SoundEvent(new ResourceLocation(ModernCrayfish.MOD_ID,"modern_crayfish.entity.fart")));
    public static final RegistryObject<SoundEvent> MICROWAVE_RUNNING = SOUNDS.register("microwave_running", ()-> new SoundEvent(new ResourceLocation(ModernCrayfish.MOD_ID,"modern_crayfish.microwave.run")));
    public static final RegistryObject<SoundEvent> MICROWAVE_FINISH = SOUNDS.register("microwave_finish", ()-> new SoundEvent(new ResourceLocation(ModernCrayfish.MOD_ID,"modern_crayfish.microwave.finish")));

}
