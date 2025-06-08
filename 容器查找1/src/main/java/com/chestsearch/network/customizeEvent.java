package com.chestsearch.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class customizeEvent {


    public CompoundTag data;

    public Player player;


public customizeEvent(Player player, CompoundTag data){

    this.player=player;

    this.data=data;

}

















}
