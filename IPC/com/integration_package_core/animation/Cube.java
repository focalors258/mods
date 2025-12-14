package com.integration_package_core.animation;

import net.minecraft.client.model.geom.ModelPart;
import software.bernie.geckolib.cache.object.GeoBone;

public class Cube {


    public float x;
    public float y;
    public float z;
    public float xRot;
    public float yRot;
    public float zRot;
    public float xScale = 1.0F;
    public float yScale = 1.0F;
    public float zScale = 1.0F;


    public Cube(net.minecraft.client.model.geom.ModelPart m) {

        x = m.x;
        y = m.y;
        z = m.z;
        xRot = m.xRot;
        yRot = m.yRot;
        zRot = m.zRot;
        xScale = m.xScale;
        yScale = m.yScale;
        zScale = m.zScale;

    }

    public Cube(GeoBone m) {

        x = m.getRotX();
        y = m.getRotY();
        z = m.getRotX();
        xRot = m.getRotX();
        yRot = m.getRotY();
        zRot = m.getRotZ();
        xScale = m.getScaleX();
        yScale = m.getScaleY();
        zScale = m.getScaleZ();

    }


    public ModelPart copy(ModelPart m) {


      // m.x = x;
      // m.y = y;
      // m.z = z;
        m.xRot =- xRot;
        m.yRot =- yRot;
        m.zRot = zRot;
        m.xScale = xScale;
        m.yScale = yScale;
        m.zScale = zScale;

        return m;

    }

public static void clear(ModelPart m){

    m.xRot =- 0;
    m.yRot =- 0;
    m.zRot = 0;
    m.xScale =1;
    m.yScale =1;
    m.zScale =1;






}



}


//   x
//   y
//   z
//   xRot
//   yRot
//   zRot
//   xScale
//   yScale
//   zScale
//
//
//
//
//
//


