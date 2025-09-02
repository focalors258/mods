package com.integration_package_core.event.EventHandler.Client;


import com.integration_package_core.IntegrationPackageCore;
import com.integration_package_core.util.Network.NetworkEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = IntegrationPackageCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class SyncEvent {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static  void p(NetworkEvent e){

          if(Objects.equals(e.key, "player_move_set")){

          CompoundTag v=e.data;

             e.player.setDeltaMovement(
                     v.getDouble("x"),
                     v.getDouble("y"),
                     v.getDouble("z"));

          }


    }















}
