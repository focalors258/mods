package com.chestsearch.tool;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class M{



public static float getVecX(Entity entity){

  float  vertical= (float) (Math.PI*entity.xRotO/180);

    float horizontal=(float) (Math.PI*entity.yRotO/180);

 return (float) (-Math.cos(vertical) * Math.sin(horizontal));

}



    public static float getVecY(Entity entity){

        float  vertical= (float) (Math.PI*entity.xRotO/180);

        float horizontal=(float) (Math.PI*entity.yRotO/180);

        return (float)-Math.sin(vertical) ;

    }




    public static float getVecZ(Entity entity){

    //  System.out.println(entity.xRotO);

    //  System.out.println(entity.yRotO);

        float  vertical= (float) (Math.PI*entity.xRotO/180);

        float horizontal=(float) (Math.PI*entity.yRotO/180);

        return (float) ( Math.cos(vertical) * Math.cos(horizontal) );

    }

public static Vec3 getVec(Entity entity){

    return new Vec3(getVecX(entity),getVecY(entity),getVecZ(entity));

}

    public static int getColor(int r,int  g,int  b,int  a)  {

        return (((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                ((b & 0xFF) << 0));

    }













}
