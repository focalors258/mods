package com.integration_package_core.event.EventHandler.Server;

import com.integration_package_core.IPC;
import com.integration_package_core.data.Date;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.util.Network.NetworkManager;
import com.integration_package_core.util.Utlis;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IPC.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TickEvent {


    @SubscribeEvent
    public static void tl(EntityEvent l) {


        // System.out.println("46456757545435548870784563243");
        //    PlayerAnimationEntity.playerSync(l);


    }

    @SubscribeEvent
    public static void t(LivingEvent.LivingTickEvent l) {


        // System.out.println("46456757545435548870784563243");
        //    PlayerAnimationEntity.playerSync(l);


    }


    @SubscribeEvent
    public static void j(net.minecraftforge.event.TickEvent.PlayerTickEvent e) {


        Utlis.tickCount=Utlis.server.getTickCount();
        CompoundTag c=new CompoundTag();
        c.putInt("tick", Utlis.tickCount);
        NetworkManager.sendAll("tickCountSync",c);







        if (e.player instanceof PlayerExpand p) {

            if (p.getAnimationEntity() != null) {//在非原版维度中该实体tick不会运行

                if (!p.getAnimationEntity().level().dimension().location().getNamespace().equals("minecraft")) {


                  //  p.getAnimationEntity().tick();


                }


            }


        }

        //   CompoundTag a = new CompoundTag();
//
        //   a.putString("34", "346");
//
        //   l.player.getPersistentData().put("124576464", a);

        //  System.out.println("46456757545435548870784563243");
        //  PlayerAnimationEntity.playerSync(l);


    }


}
