package com.chestsearch.gui;
import net.minecraft.client.Minecraft;

public class tool {







    public static Minecraft Client = Minecraft.getInstance();

    public static boolean newVirtualButton(float x, float y, float width, float height) {



        float MouseX = getMouseX(); //(float) Minecraft.getInstance().mouseHandler.xpos();

        float MouseY =  getMouseY();//(float) Minecraft.getInstance().mouseHandler.ypos();

       // System.out.println(MouseX > x && MouseX < (x + width) && MouseY > y && MouseY < (y + height));

        return MouseX > x && MouseX < (x + width) && MouseY > y && MouseY < (y + height);

    }

    public static boolean mouseLeftOutside = false;

    public static boolean mouseLeftInside= false;



    public static float getMouseX() {

        return (float) (Client.mouseHandler.xpos() / Client.getWindow().getGuiScale());

    }

    public static float getMouseY() {

        return (float) (Client.mouseHandler.ypos() / Client.getWindow().getGuiScale());

    }

   public static double getGuiScale  () {

      // if (getMouseX()> x && getMouseX() < (x + width) && getMouseY()> y && getMouseY() < (y + height)) return true;


       return Client.getWindow().getGuiScale();

   }






}
