package com.ModernCrayfish.init;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.objects.container.MicrowaveContainer;
import com.ModernCrayfish.objects.tileEntity.MicrowaveTileEntity;
import com.mrcrayfish.furniture.inventory.container.CrateContainer;
import com.mrcrayfish.furniture.tileentity.CrateTileEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.ContainerType.IFactory;
import net.minecraft.util.IntReferenceHolder;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ModernCrayfish.MOD_ID);

    public static final RegistryObject<ContainerType<MicrowaveContainer>> MICROWAVE_CONTAINER = CONTAINERS.register("microwave",()-> IForgeContainerType.create(MicrowaveContainer::new));

}