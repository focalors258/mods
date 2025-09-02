package com.integration_package_core.mixinTool;

import net.minecraft.world.phys.Vec3;

public interface CameraMember {



 void $setPosition(Vec3 pPos);

    void $move(double pDistanceOffset, double pVerticalOffset, double pHorizontalOffset);

    void $setRotation(float pYRot, float pXRot);
}
