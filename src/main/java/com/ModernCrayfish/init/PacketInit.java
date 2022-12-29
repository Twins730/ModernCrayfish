package com.ModernCrayfish.init;

import com.ModernCrayfish.ModernCrayfish;
import com.ModernCrayfish.objects.packet.Packet;
import com.ModernCrayfish.objects.packet.PacketStartMicrowave;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketInit {

    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel instance;
    private static int nextId = 0;

    public static void init() {
        instance = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ModernCrayfish.MOD_ID, "network"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();


        PacketInit.register(PacketStartMicrowave.class, new PacketStartMicrowave());
    }

    private static <T> void register(Class<T> clazz, Packet<T> message) {
        PacketInit.instance.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle);
    }
}
