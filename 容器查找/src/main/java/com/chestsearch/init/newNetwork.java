package com.chestsearch.init;


import com.chestsearch.ChestSearch;
import com.chestsearch.network.buttonNetwork;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class newNetwork {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ChestSearch.MODID, "main_channel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int packetId = 0;

// @OnlyIn(Dist.CLIENT)
// public static void init() {
//     INSTANCE.registerMessage(
//             0, // 与服务器端相同的ID
//             buttonNetwork.class,
//             buttonNetwork::encode,
//             buttonNetwork::new,
//             (message, ctx) -> {
//                 ctx.get().enqueueWork(() -> {
//                     // 客户端处理逻辑
//                     System.out.println("cilent");
//                         // 更新搜索界面
//                     //    SearchScreen.updateFromServer(message.getData());

//                 });
//                 ctx.get().setPacketHandled(true);
//             }
//     );
// }


    public static void registerPackets() {


        INSTANCE.registerMessage(
                packetId++,
                buttonNetwork.class,
                buttonNetwork::encode,
                buttonNetwork::new,
                buttonNetwork::handle
        );
    }

}

