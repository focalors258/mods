package com.integration_package_core.event.Event;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

@Event.HasResult
public class ItemTransferEvent extends Event{


public LivingEntity player;

public ItemStack itemStack;

public int slot;


    public ItemTransferEvent(LivingEntity player, ItemStack itemStack,int slot) {

        this.player = player;
        this.itemStack = itemStack;
this.slot=slot;
    }

    public static boolean onItemTransfer(LivingEntity player, ItemStack itemStack,int slot) {
        return MinecraftForge.EVENT_BUS.post(new ItemTransferEvent( player,  itemStack,slot) );
    }









}
