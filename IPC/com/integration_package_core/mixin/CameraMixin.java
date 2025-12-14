package com.integration_package_core.mixin;

import com.integration_package_core.mixinTool.CameraMember;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class CameraMixin implements CameraMember {


    @Shadow
    protected abstract void setPosition(double pX, double pY, double pZ);

    @Shadow
    protected abstract void move(double pDistanceOffset, double pVerticalOffset, double pHorizontalOffset);

    @Shadow
    protected abstract void setRotation(float pYRot, float pXRot);





    @Shadow
    protected abstract double getMaxZoom(double pStartingDistance);

    @Shadow
    private Vec3 position;
    @Shadow private float yRot;
    @Shadow private float xRot;
    @Shadow private boolean detached;
    @Unique
    public Vec3 oldPosition = Vec3.ZERO;

    @Unique
    public float moveSpeed = 1;
    @Unique
    public float moveEnd = 0;


    public void setDetached(boolean detached){

        this.detached=detached;

    }


    @Override
    public void $setPosition(Vec3 pPos) {


        this.setPosition(pPos.x, pPos.y, pPos.z);


    }

public     void $setXRot(float x){


setRotation(this.yRot,x);


  };
public     void $setYRot(float y){

//this.yRot=y;
   setRotation(y,this.xRot);


    };








    @Override
    public void $move(double pDistanceOffset, double pVerticalOffset, double pHorizontalOffset) {


        this.move(pDistanceOffset, pVerticalOffset, pHorizontalOffset);

    }

    @Override
    public void $setRotation(float pYRot, float pXRot) {
        this.setRotation(pYRot, pXRot);
    }


    public double $getMaxZoom(double pStartingDistance) {

        return this.getMaxZoom(pStartingDistance);

    }


    /**
     * 注入到getPosition方法的返回节点，修改返回的坐标
     *
     * @param cir 回调信息，包含原返回值和设置新返回值的方法
     */
    @Inject(
            method = "getPosition", // 目标方法名（无参数，直接写方法名）
            at = @At("RETURN"), // 注入时机：原方法执行完即将返回时
            cancellable = true)
    private void modifyCameraPosition(CallbackInfoReturnable<Vec3> cir) {

        //  if (Minecraft.getInstance().player != null) {
        //      float Delta=this.moveEnd-Minecraft.getInstance().player.tickCount;

        //      Delta=Delta>=0?Delta:0;

        //      Delta=1-Delta;

        //      cir.setReturnValue(new Vec3(
        //              Mth.lerp(Delta, this.oldPosition.x, this.position.x),
        //              Mth.lerp(Delta, this.oldPosition.x, this.position.x),
        //              Mth.lerp(Delta, this.oldPosition.x, this.position.x)));


        //  }


    }

    @Inject(method = "setPosition(Lnet/minecraft/world/phys/Vec3;)V",
            at = @At("HEAD")
    )
    private void updatePosition(Vec3 pPos, CallbackInfo ci) {

        //this.oldPosition = this.position;

        //if (Minecraft.getInstance().player != null) {
        //    this.moveEnd= Minecraft.getInstance().player.tickCount+this.moveSpeed;
        //}

    }

}
