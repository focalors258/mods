package com.integration_package_core.util.Network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class NetworkEvent {


    public CompoundTag data;

    public Player player;

    public Level level;

public NetworkEvent(Player player, CompoundTag data){

    this.player=player;

    this.data=data;

    if(player!=null){
        this.level=player.level();
    }else{

       this.level=  Minecraft.getInstance().level;

    }


    //MinecraftServer.

}

















}
