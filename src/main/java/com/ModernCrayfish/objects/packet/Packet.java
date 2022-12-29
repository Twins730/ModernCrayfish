package com.ModernCrayfish.objects.packet;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface Packet<T> {
    void encode(T var1, PacketBuffer var2);

    T decode(PacketBuffer var1);

    void handle(T var1, Supplier<NetworkEvent.Context> var2);

}

