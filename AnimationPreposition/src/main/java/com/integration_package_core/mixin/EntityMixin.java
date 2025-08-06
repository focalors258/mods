package com.integration_package_core.mixin;

import com.integration_package_core.tool.EntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin  implements EntityData {


    @Shadow private Vec3 position;

  // protected EntityMixin(Class<Entity> baseClass) {
  //     super(baseClass);
  // }



//  @Override
//  public void setCrit(boolean crit) {//通过mixin添加方法需要继承接口






//  }









    @Inject(
            method ="load",
            at = @At(
                    value = "TAIL"
                    //  shift = At.Shift.AFTER
            )
    )
    public void init(CallbackInfo ci) {


        //EntityData.getHealthMove((Entity) this)



   // this.position = new Vec3(0, 0, 0);

       //System.out.println("3453456347");


    }
















}
