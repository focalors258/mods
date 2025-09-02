package com.integration_package_core.tool;

import net.minecraft.client.gui.GuiGraphics;

import java.lang.reflect.Field;

public class modifyPrivate {


   public static <T,M> void set(String wayName,T value,M target) throws NoSuchFieldException, IllegalAccessException {


    Field wayController = target.getClass() .getDeclaredField(wayName);

   wayController.setAccessible(true);

     wayController.set(target, value);

   }

    public static <T,M> Object get(String wayName,M target) throws IllegalAccessException {





        try {

            Field wayController = target.getClass().getDeclaredField(wayName);

            wayController.setAccessible(true);



            return   wayController.get(target);




        } catch (NoSuchFieldException e) {



            // 处理未找到字段的情况（例如记录日志或返回null）
          //  System.err.println("字段 '" + wayName + "' 不存在于类 " + target.getClass());
            return null;
        }




    }









}
