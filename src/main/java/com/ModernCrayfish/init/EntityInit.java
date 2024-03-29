package com.ModernCrayfish.init;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.objects.entity.MirrorEntity;
import com.ModernCrayfish.objects.entity.SeatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ModernCrayfish.MOD_ID);

    public static final RegistryObject<EntityType<SeatEntity>> SEAT_ENTITY = ENTITIES.register("seat",()-> EntityType.Builder.<SeatEntity>create((type,world) -> new SeatEntity(world), EntityClassification.MISC).size(0,0).setCustomClientFactory((spawnEntity, world) -> new SeatEntity(world)).build("seat"));
    public static final RegistryObject<EntityType<MirrorEntity>> MIRROR_ENTITY = ENTITIES.register("mirror",()-> EntityType.Builder.<MirrorEntity>create((type, world) -> new MirrorEntity(world), EntityClassification.MISC).size(0.1f,0.1f).setCustomClientFactory((spawnEntity, world) -> new MirrorEntity(world)).build("mirror"));

}
