package com.integration_package_core.util.Network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

import java.awt.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class NetworkEvent extends Event {


    public CompoundTag data;

    public Player player;

    public  String key;

    public Level level;




    //当使用BiFunction作为形参类型时  在静态上下文中使用 类名::非静态方法(一个形参) 作为实参且BiFunction的泛型第一位为该类类型  java会自动将接收到BiFunction类型的函数式接口的第一位添加该类类型的形参
 //至于该静态上下文中的该类类型来源则是在非静态上下文中填入

  //public final  static  NetworkEvent aaa=new NetworkEvent(NetworkEvent::setPlayer);

  //  public NetworkEvent( BiFunction<Integer,Player,Boolean> p){ }
//  public boolean setPlayer(NetworkEvent a,Player player) {
//      this.player = player;
//      return true;
//  }





public NetworkEvent(Player player, CompoundTag data,String key){

    this.player=player;

    this.key=key;
    this.data=data;

    if(player!=null){
        this.level=player.level();
    }else{
this.player=Minecraft.getInstance().player;

       this.level=  Minecraft.getInstance().level;

    }


    //MinecraftServer.

}




    /**
     * 获取
     * @return data
     */
    public CompoundTag getData() {
        return data;
    }

    /**
     * 设置
     * @param data
     */
    public void setData(CompoundTag data) {
        this.data = data;
    }

    /**
     * 获取
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * 设置
     * @param player
     */

    /**
     * 获取
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取
     * @return level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * 设置
     * @param level
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    public String toString() {
        return "NetworkEvent{data = " + data + ", player = " + player + ", key = " + key + ", level = " + level + "}";
    }
}
