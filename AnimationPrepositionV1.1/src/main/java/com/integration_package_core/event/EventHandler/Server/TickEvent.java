package com.integration_package_core.event.EventHandler.Server;

import com.integration_package_core.IntegrationPackageCore;
import com.integration_package_core.animation.PlayerAnimationEntity;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.util.Network.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IntegrationPackageCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TickEvent {


    @SubscribeEvent
    public static void t(LivingEvent.LivingTickEvent l) {


        // System.out.println("46456757545435548870784563243");
        //    PlayerAnimationEntity.playerSync(l);


    }


    @SubscribeEvent
    public static void j(net.minecraftforge.event.TickEvent.PlayerTickEvent e) {




     //   CompoundTag a = new CompoundTag();
//
     //   a.putString("34", "346");
//
     //   l.player.getPersistentData().put("124576464", a);

        //  System.out.println("46456757545435548870784563243");
        //  PlayerAnimationEntity.playerSync(l);


    }


}
