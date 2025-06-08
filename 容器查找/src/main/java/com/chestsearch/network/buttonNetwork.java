package com.chestsearch.network;

import com.chestsearch.gui.searchList.SearchScreen;
import com.chestsearch.gui.searchList.chestContainer;
import com.chestsearch.init.newNetwork;
import com.chestsearch.tool.E;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import com.chestsearch.gui.searchList.SearchMenu;

import java.util.*;
import java.util.function.Supplier;

public class buttonNetwork {
    public static HashMap<String, handle> Network;
    private final CompoundTag data; // 示例：传输字符串数据
    private final String key;

    // 构造方法（客户端发数据包时用）
    public buttonNetwork(String key, CompoundTag data) {
        this.key = key;
        this.data = data;
    }

    public buttonNetwork(String data) {
        this.data = new CompoundTag();
        this.key = data;
    }

    // 解码（服务器/客户端接收时用）
    public buttonNetwork(FriendlyByteBuf buf) {

        //
        this.data = buf.readNbt();
        this.key = buf.readUtf();
    }

    public static void send(String key, CompoundTag data) {


        buttonNetwork Network = new buttonNetwork(key, data);
        // 发送到服务器
        newNetwork.INSTANCE.send(
                PacketDistributor.SERVER.noArg(), // 目标：服务器
                Network
        );

    }

    public static void send(String key) {

        buttonNetwork Network = new buttonNetwork(key);
        // 发送到服务器
        newNetwork.INSTANCE.send(
                PacketDistributor.SERVER.noArg(), // 目标：服务器
                Network
        );
    }

    public static void send(String key, CompoundTag data, ServerPlayer player) {
      //  System.out.println("42343543");

        buttonNetwork Network = new buttonNetwork(key, data);
        // 发送到客户端

  // newNetwork.INSTANCE.sendTo(Network,player.connection.connection,NetworkDirection.PLAY_TO_CLIENT);

  newNetwork.INSTANCE.send(
         PacketDistributor.PLAYER.with(()->player), // 目标：服务器
         Network)
  ;

    }

    public static void send(String key, ServerPlayer player) {
        // 发送到客户端
        buttonNetwork Network = new buttonNetwork(key);


        System.out.println(Network);
        System.out.println(PacketDistributor.PLAYER);



        newNetwork.INSTANCE.send(
                PacketDistributor.PLAYER.with(()->player), // 目标：服务器
                Network
        );
    }

    public static void event(String key, handle event) {

        Network.put(key, event);
    }

    // 编码（客户端发送时用）
    public void encode(FriendlyByteBuf buf) {

        buf.writeNbt(data);
        buf.writeUtf(key);


    }

    // 处理逻辑（服务器/客户端接收后执行）
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {//注意 此处代码错误不会报错
            // 这里写数据包的业务逻辑
            // System.out.println("收到数据包内容: " + data);


            if (Objects.equals(key, "syncSearch")) {


                SearchScreen.of.disorderContainer=data;


            }

            System.out.println(key);

            if (Objects.equals(key, "openSearch")) {//客户端<-服务端

                ServerPlayer player = ctx.get().getSender();


                int[] pos = player.getPersistentData().getIntArray("chestPos");

                if (pos.length == 3) {
                    LevelChunk levelchunk0 = E.getChunk(player, pos[0], pos[2]);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]), SectionPos.blockToSectionCoord(pos[2]));
                    LevelChunk levelchunk1 = E.getChunk(player, pos[0] - 16, pos[2] - 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]-16), SectionPos.blockToSectionCoord(pos[2]-16));
                    LevelChunk levelchunk2 = E.getChunk(player, pos[0] + 16, pos[2] + 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]+16), SectionPos.blockToSectionCoord(pos[2]+16));
                    LevelChunk levelchunk3 = E.getChunk(player, pos[0] - 16, pos[2] + 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]-16), SectionPos.blockToSectionCoord(pos[2]+16));
                    LevelChunk levelchunk4 = E.getChunk(player, pos[0] + 16, pos[2] - 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]+16), SectionPos.blockToSectionCoord(pos[2]-16));

                    Map<BlockPos, BlockEntity> list = new HashMap<>();

                    list.putAll(levelchunk0.getBlockEntities());
                    list.putAll(levelchunk1.getBlockEntities());
                    list.putAll(levelchunk2.getBlockEntities());
                    list.putAll(levelchunk3.getBlockEntities());
                    list.putAll(levelchunk4.getBlockEntities());

                    CompoundTag tag = new CompoundTag();


                 //  System.out.println("3");

                    list.forEach((key, block) -> {
                        tag.put(key.toString(), block.saveWithFullMetadata());

                    });

                  //  System.out.println( ((CompoundTag)tag.get("BlockPos{x=-71, y=-60, z=-94}")).get("id"));
                    player.openMenu(new SimpleMenuProvider((a, b, c) -> {
                        return new SearchMenu(100);
                    }, Component.literal("465")));


               //     System.out.println("1");

                    send("syncSearch", tag, player);
                }


            }

            //    int[] pos = player.getPersistentData().getIntArray("chestPos");
//
//
            //    LevelChunk levelchunk = null;
            //    if (player != null) {
            //        levelchunk = player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]), SectionPos.blockToSectionCoord(pos[2]));
            if (Objects.equals(key, "openChest")) {//服务端<-客户端
                ServerPlayer player = ctx.get().getSender();
                if (player != null) {

                    //    System.out.println("746754765895745656865");

                    int[] pos = data.getIntArray("chestPos");

                    BlockPos a = new BlockPos(pos[0], pos[1], pos[2]);

                    System.out.println("pos");

                    System.out.println(data);
                    // System.out.println(pos);


                    Block block = player.level().getBlockState(a).getBlock();

                    MenuProvider menuprovider = block.getMenuProvider(player.level().getBlockState(a), player.level(), a);

                    player.openMenu(menuprovider);


                }
            }
            if (Objects.equals(key, "openOriginalChest")) {//服务端<-客户端
                ServerPlayer player = ctx.get().getSender();
                if (player != null) {

                    //    System.out.println("746754765895745656865");

                    int[] pos = data.getIntArray("chestPos");

                    BlockPos a = new BlockPos(pos[0], pos[1], pos[2]);

                    System.out.println("pos");

                    System.out.println(data);
                    // System.out.println(pos);


                    Block block = player.level().getBlockState(a).getBlock();

                    MenuProvider menuprovider = block.getMenuProvider(player.level().getBlockState(a), player.level(), a);

                    player.openMenu(menuprovider);


                }
            }





        });
        ctx.get().setPacketHandled(true);
    }


    public interface handle {
        void InternalHandle(customizeEvent event);
    }
    //    CompoundTag a=          new CompoundTag();
    //  a.putInt("35",345);
    //  a.putInt("634534",345);
    //  a.putInt("6435345",345);
    //  a.putInt("3453466",345);
    //    // throw new IllegalArgumentException("Data too large for network packet");  System.out.println("6546465467967986786786788678678605468908608605486549664665658768567679677679788678678768979");

    //  System.out.println( +"4032975394879034756093856093457804364903578094385034985034580436466906");

    //      buf.writeVarIntArray(new int[]{456,4,564,56});
    //  Map<String, Object> a = new HashMap<String, Object>();
    //  try {
    //      NbtIo.write(this.data, new ByteBufOutputStream(buf));
    //  } catch (IOException ioexception) {
    //      throw new EncoderException(ioexception);
    //  }

    //  buf.writeUtf(this.key);
}   //    if (Network.containsKey(key)) {
//System.out.println(data);
//  Network.get(key).InternalHandle(new customizeEvent(player, data));
//  System.out.println(levelchunk.getBlockEntities());
//        Network.get(key).InternalHandle(new customizeEvent(player, data));

//    }