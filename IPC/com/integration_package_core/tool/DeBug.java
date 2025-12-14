package com.integration_package_core.tool;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.lang.reflect.Method;

public class DeBug {


    private static void error(Class<?> ConsoleJS,String type,String name,Throwable ignored) throws ReflectiveOperationException {

        Object scripts=    ConsoleJS.getField(type).get(null);

        Method error = ConsoleJS.getMethod(
                "error",
                String.class,
                Throwable.class
        );

        error.invoke(scripts,name,ignored);

    }

    public static void error(String name,Throwable ignored){

        try {
            Class<?> ConsoleJS = Class.forName("dev.latvian.mods.kubejs.util.ConsoleJS");
            try {
           ;
       Class.forName("net.minecraft.client.Minecraft");
                error(ConsoleJS,"STARTUP",name,ignored);

            } catch (ClassNotFoundException ignored1) {

                error(ConsoleJS,"STARTUP",name,ignored);

            }

        } catch (ReflectiveOperationException ignored1) {


        }

    }









    public  static void tell(String a) {
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal(a));
        }
    }
}
