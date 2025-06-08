package com.chestsearch.data;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.UUID;

public class EntityData {

    public  EntityData(Entity pEntity, @NonNull String pName, @NonNull Float define) {





        AttributeList.put(pName, define);
        //pEntity.per
        EntityDataList.put(pEntity.getUUID(), AttributeList);

    }

    public void addData(Entity pEntity, @NonNull String pName, @NonNull Float define){

        new EntityData(pEntity,pName, define);

    }


    public float getData(Entity pEntity, @NonNull String pName){

        return EntityDataList.get(pEntity.getUUID()).get(pName);

    }



    public HashMap<String, Float> AttributeList;

    public static HashMap<UUID, HashMap<String, Float>> EntityDataList;




    public static HashMap<UUID, Float> EntityBar_toughness;

    public static HashMap<UUID, Float> EntityBar_health_move;

    public static HashMap<UUID, Float> EntityBar_shield_move;

    public static HashMap<UUID, Float> EntityBar_toughness_move;

    public static HashMap<UUID, Float> EntityBar_health_speed;
    public static HashMap<UUID, Float> EntityBar_shield_speed;

    public static HashMap<UUID, Float> EntityBar_toughness_speed;

   public static float getToughness(LivingEntity entity){

       return EntityBar_toughness.get(entity.getUUID());

   }

    public static float getHealthMove(Entity entity){

        return EntityBar_health_move.get(entity.getUUID());

    }
    public static float getShieldMove(LivingEntity entity){

        return EntityBar_shield_move.get(entity.getUUID());

    }
    public static float getToughnessMove(LivingEntity entity){

        return EntityBar_toughness_move.get(entity.getUUID());

    }
    public static float getHealthSpeed(LivingEntity entity){

        return EntityBar_health_speed.get(entity.getUUID());

    }
    public static float getShieldSpeed(LivingEntity entity){

        return EntityBar_shield_speed.get(entity.getUUID());

    }

    public static float getToughnessSpeed(LivingEntity entity){

        return EntityBar_toughness_speed.get(entity.getUUID());

    }

    public static float setToughness(LivingEntity entity,float value){

        return EntityBar_toughness.get(entity.getUUID());

    }

    public static float setHealthMove(LivingEntity entity,float value){

        return EntityBar_health_move.get(entity.getUUID());

    }
    public static float setShieldMove(LivingEntity entity,float value){

        return EntityBar_shield_move.get(entity.getUUID());

    }
    public static float setToughnessMove(LivingEntity entity,float value){

        return EntityBar_toughness_move.get(entity.getUUID());

    }
    public static float setHealthSpeed(LivingEntity entity,float value){

        return EntityBar_health_speed.get(entity.getUUID());

    }
    public static float setShieldSpeed(LivingEntity entity,float value){

        return EntityBar_shield_speed.get(entity.getUUID());

    }

    public static float setToughnessSpeed(LivingEntity entity,float value){

        return EntityBar_toughness_speed.get(entity.getUUID());

    }


}
