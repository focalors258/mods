package com.integration_package_core.mixinTool;

import com.integration_package_core.animation.PlayerAnimationEntity;

import java.util.function.Consumer;

public interface PlayerExpand {


    public PlayerAnimationEntity getAnimationEntity();


    public  void setAnimationEntity(PlayerAnimationEntity entity);

    public void playAnimation(int playType,int LinkType, int timeInit,String animationInit,  Consumer<PlayerAnimationEntity> animationConsumer);


    public void stopAnimation();


}
