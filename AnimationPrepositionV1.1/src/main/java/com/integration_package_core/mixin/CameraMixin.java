package com.integration_package_core.mixin;

import com.integration_package_core.mixinTool.CameraMember;
import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Camera.class)
public abstract class CameraMixin implements CameraMember {


    @Shadow
    protected abstract void setPosition(double pX, double pY, double pZ);

    @Shadow
    protected abstract void move(double pDistanceOffset, double pVerticalOffset, double pHorizontalOffset);

    @Shadow
    protected abstract void setRotation(float pYRot, float pXRot);

    @Override
    public void $setPosition(Vec3 pPos) {


        this.setPosition(pPos.x, pPos.y, pPos.z);


    }

    @Override
    public void $move(double pDistanceOffset, double pVerticalOffset, double pHorizontalOffset) {


        this.move(pDistanceOffset, pVerticalOffset, pHorizontalOffset);

    }

    @Override
    public void $setRotation(float pYRot, float pXRot) {
        this.setRotation(pYRot, pXRot);
    }


}
