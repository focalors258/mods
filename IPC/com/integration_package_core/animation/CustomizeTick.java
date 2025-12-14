package com.integration_package_core.animation;

import java.util.function.Consumer;

public class CustomizeTick {




    public Consumer<PlayerAnimationEntity> tick;


    public Consumer<PlayerAnimationEntity>endTick;
    public CustomizeTick(Consumer<PlayerAnimationEntity> endTick,Consumer<PlayerAnimationEntity> tick){

        this.endTick=endTick;
        this.tick=tick;


    }







}
