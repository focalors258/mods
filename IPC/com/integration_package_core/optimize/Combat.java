package com.integration_package_core.optimize;

import com.integration_package_core.mixinTool.LivingEntityExpand;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class Combat {



    public static void init(LivingEntity entity, CompoundTag persistentData){

     //   System.out.println(entity instanceof LivingEntityExpand);

    //    System.out.println(persistentData!=null);

        if (entity instanceof LivingEntityExpand l&&persistentData!=null) {
            if (!(entity instanceof Player)) {

              //  l.setMaxToughness(persistentData.getFloat("maxToughness"));
                l.setToughness(l.getMaxToughness());


               // System.out.println(l.getToughness());
               // System.out.println(persistentData.getFloat("maxToughness"));
               // System.out.println(persistentData.getFloat("maxToughness"));
            }

            if (entity.getPersistentData().contains("smashTime")) {
                l.setSmashTime(persistentData.getFloat("smashTime"));

            }


        }





















    }

















}
