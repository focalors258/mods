package com.integration_package_core.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;

@Mixin(AnimationController.class)
public abstract class AnimationControllerMixin<T extends GeoAnimatable> {

    @Shadow protected RawAnimation currentRawAnimation;


    @Shadow protected abstract boolean stopTriggeredAnimation();

    public void setCurrentRawAnimation(RawAnimation a){


    this.currentRawAnimation=a;

}

 public void $stopTriggeredAnimation(){

        this.stopTriggeredAnimation();

}





}
