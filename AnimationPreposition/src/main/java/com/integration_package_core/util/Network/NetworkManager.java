package com.integration_package_core.util.Network;

//import com.integration_package_core.tool.E;

import com.integration_package_core.util.RegistryHandler.NetworkRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class NetworkManager {
    public static HashMap<String, handle> Network;
    private final CompoundTag data; // 示例：传输字符串数据
    private final String key;

    // 构造方法（客户端发数据包时用）
    public NetworkManager(String key, CompoundTag data) {
        this.key = key;
        this.data = data;
    }

    public NetworkManager(String data) {
        this.data = new CompoundTag();
        this.key = data;
    }

    // 解码（服务器/客户端接收时用）
    public NetworkManager(FriendlyByteBuf buf) {

        //
        this.data = buf.readNbt();
        this.key = buf.readUtf();
    }

    public static void send(String key, CompoundTag data) {


        NetworkManager Network = new NetworkManager(key, data);
        // 发送到服务器
        NetworkRegistry.INSTANCE.send(
                PacketDistributor.SERVER.noArg(), // 目标：服务器
                Network
        );

    }

    public static void send(String key) {

        NetworkManager Network = new NetworkManager(key);
        // 发送到服务器
        NetworkRegistry.INSTANCE.send(
                PacketDistributor.SERVER.noArg(), // 目标：服务器
                Network
        );
    }

    public static void send(String key, CompoundTag data, ServerPlayer player) {
        //  System.out.println("42343543");

        NetworkManager Network = new NetworkManager(key, data);
        // 发送到客户端

        // newNetwork.INSTANCE.sendTo(Network,player.connection.connection,NetworkDirection.PLAY_TO_CLIENT);

        NetworkRegistry.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player), // 目标：服务器
                Network)
        ;

    }

    public static void send(String key, ServerPlayer player) {
        // 发送到客户端
        NetworkManager Network = new NetworkManager(key);


        //      System.out.println(Network);
        //  System.out.println(PacketDistributor.PLAYER);


        NetworkRegistry.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player), // 目标：服务器
                Network
        );
    }

// public static void event(String key, handle event) {

//     Network.put(key, event);
// }

    // 编码（客户端发送时用）
    public void encode(FriendlyByteBuf buf) {

        buf.writeNbt(data);
        buf.writeUtf(key);


    }

    // 处理逻辑（服务器/客户端接收后执行）
    public void handle(Supplier<net.minecraftforge.network.NetworkEvent.Context> ctx) {

       // System.out.println("->");



        if (NetworkList.containsKey(key)) {

            Player p=ctx.get().getSender();

if(p!=null){//客户端->服务端


    NetworkList.get(key).forEach(e -> e.InternalHandle(new NetworkEvent(p, this.data)));

}else{//服务端->客户端


    NetworkList.get(key).forEach(e -> e.InternalHandle(new NetworkEvent(null, this.data)));


}










        }
        //  else {


        //      NetworkList.put(key, e.InternalHandle(new NetworkEvent(null, this.data)));


        //  }

        //  System.out.println(ctx.get().getSender());
//
        //  System.out.println(this.data);

        ctx.get().setPacketHandled(true);
    }

    public interface handle {
        void InternalHandle(NetworkEvent event);


    }

    public static HashMap<String, List<handle>> NetworkList = new HashMap<String, List<handle>>();

    public static void NetworkEvent(String key, handle event) {

        if (NetworkList.containsKey(key)) {

            NetworkList.get(key).add(event);

        } else {

            List<handle> a = new ArrayList<>();//com.sun.tools.javac.util

            a.add(event);

            NetworkList.put(key, a);

         //  System.out.println(NetworkList);
         //  System.out.println("345934905t4uy89tgrei9ughi9ty4389re498ug34908564309548340956849068034985340840985408686y086");

        }


    }

}


//   e.player= ;
//   e.data=;