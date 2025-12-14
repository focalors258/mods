package com.integration_package_core.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.HashMap;

public class Type {


    private static HashMap<String, Object> List = new HashMap<>();


    public static void add(String name, Attribute value) {

        List.put(name, value);

    }

    public static <T extends Entity> void add(String name, EntityType<T> value) {

        List.put(name, value);

    }

    public static void add(String name, MobEffect value) {

        List.put(name, value);

    }

    public static void add(String name, Item value) {

        List.put(name, value);

    }


    public static Attribute getAttribute(String name){

        if (List.containsKey(name)&&List.get(name) instanceof Attribute a){

            return a;
        }

       return null;

    }

    public static <T extends Entity> Entity getEntity(String name){

        if (List.containsKey(name)&&List.get(name) instanceof Entity a){

            return  a;
        }

        return null;

    }

    public static  MobEffect getMobEffect(String name){

        if (List.containsKey(name)&&List.get(name) instanceof MobEffect a){

            return  a;
        }

        return null;

    }

    public static  Item getItem(String name){

        if (List.containsKey(name)&&List.get(name) instanceof Item a){

            return  a;
        }

        return null;

    }












}
