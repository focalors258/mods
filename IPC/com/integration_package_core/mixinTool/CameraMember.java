package com.integration_package_core.mixinTool;

import net.minecraft.world.phys.Vec3;

public interface CameraMember {

    public double $getMaxZoom(double pStartingDistance);

 void $setPosition(Vec3 pPos);

    void $move(double pDistanceOffset, double pVerticalOffset, double pHorizontalOffset);

    void $setRotation(float pYRot, float pXRot);

    public void setDetached(boolean detached);
    void $setXRot(float x);

    void $setYRot(float y);
















}
