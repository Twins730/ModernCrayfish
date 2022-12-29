package com.ModernCrayfish.objects.packet;

import com.ModernCrayfish.objects.container.MicrowaveContainer;
import com.ModernCrayfish.objects.tileEntity.MicrowaveTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketStartMicrowave implements Packet<PacketStartMicrowave> {

    @Override
    public void encode(PacketStartMicrowave var1, PacketBuffer var2) {

    }

    @Override
    public PacketStartMicrowave decode(PacketBuffer var1) {
        return new PacketStartMicrowave();
    }

    @Override
    public void handle(PacketStartMicrowave var1, Supplier<NetworkEvent.Context> supplier) {
        ((NetworkEvent.Context)supplier.get()).enqueueWork(() -> {
            ServerPlayerEntity player = ((NetworkEvent.Context)supplier.get()).getSender();
            if (player != null && player.openContainer instanceof MicrowaveContainer) {
                MicrowaveTileEntity tileEntity = ((MicrowaveContainer)player.openContainer).tileEntity;
                if(tileEntity != null){
                    tileEntity.startMicrowave();
                }
            }
        });
        ((NetworkEvent.Context)supplier.get()).setPacketHandled(true);

    }
}
