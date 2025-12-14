package com.integration_package_core.entity;

import com.integration_package_core.util.Network.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class PlayerOptimize {




    public static void updateMove(){





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
