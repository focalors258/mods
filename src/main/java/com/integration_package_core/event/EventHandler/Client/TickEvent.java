package com.integration_package_core.event.EventHandler.Client;

import com.integration_package_core.IntegrationPackageCore;
import com.integration_package_core.util.Network.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = IntegrationPackageCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TickEvent {



    @SubscribeEvent
    public static void j(net.minecraftforge.event.TickEvent.ClientTickEvent e) {


    Player p= Minecraft.getInstance().player;

        Vec3 v= null;
        if (p != null) {
            v = p.getDeltaMovement();


        CompoundTag s = new CompoundTag();

        s.putDouble("x", v.x);
        s.putDouble("y", v.y);
        s.putDouble("z", v.z);

        NetworkManager.send("player_move_get",s);
    }

    }


}
