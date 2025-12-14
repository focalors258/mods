package com.integration_package_core.event.EventHandler.Server;

import com.integration_package_core.IPC;
import com.integration_package_core.event.Event.ItemTransferEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IPC.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class PlayerEvent {


    @SubscribeEvent
    public static void p(net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent e) {


        ItemTransferEvent.onItemTransfer(e.getEntity(), e.getCrafting(),-999);


    }

    @SubscribeEvent
    public static void p(net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent e) {


        ItemTransferEvent.onItemTransfer(e.getEntity(), e.getStack(),-999);


    }


}
