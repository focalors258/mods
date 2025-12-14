package com.integration_package_core.event.EventHandler.Server;

import com.integration_package_core.IPC;
import com.integration_package_core.event.Event.ItemTransferEvent;
import com.integration_package_core.util.RegistryHandler.AttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@Mod.EventBusSubscriber(modid = IPC.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class EntityEvent {



    @SubscribeEvent
    public static void p(ItemTransferEvent e) {


         System.out.println(e.itemStack);

        //   System.out.println(   e.getLootItems());

        //   System.out.println   (   e.source);

//e.getLootItems()
//e.getLootContext()
//e.setCanceled(true);

    }


}
