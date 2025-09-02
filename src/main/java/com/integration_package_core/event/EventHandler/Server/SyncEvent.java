package com.integration_package_core.event.EventHandler.Server;

import com.integration_package_core.IntegrationPackageCore;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.util.Network.NetworkEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = IntegrationPackageCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SyncEvent {

    @SubscribeEvent
    public static void p(NetworkEvent e) {

        if (Objects.equals(e.key, "player_move_get")) {


            if (e.player instanceof PlayerExpand p) {

                CompoundTag v = e.data;
                if(v!=null){
                    p.setServerMoveCache(new Vec3(
                            v.getDouble("x"),
                            v.getDouble("y"),
                            v.getDouble("z")));

                }
            }


        }


    }


}
