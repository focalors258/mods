package com.integration_package_core.mixinTool;

import com.integration_package_core.animation.PlayerAnimationEntity;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public interface PlayerExpand {


    public PlayerAnimationEntity getAnimationEntity();

  public Vec3  getMove();

  public void setServerMoveCache(Vec3 v);

    public  void setAnimationLogic(Consumer<PlayerAnimationEntity> customizeTick);

    public  void setAnimationEntity(PlayerAnimationEntity entity);

    public void playAnimation(int playType,int LinkType, int timeInit,boolean elevationAngle,String animationInit,  Consumer<PlayerAnimationEntity> animationConsumer);


    public void playStageAnimation(Consumer<PlayerAnimationEntity> animationConsumer);
    public void playAnimation(int playType,int timeInit,boolean elevationAngle,String animationInit);


    public void setMove(Vec3 v);

    public void playAnimation(String animation, int time,Consumer<PlayerAnimationEntity> animationConsumer);

    public void playAnimation(int PlayType, int LinkType, int time, boolean multistage, boolean elevationAngle, String animationInit, Consumer<PlayerAnimationEntity> animationConsumer) ;

   // public void playAnimation(int PlayType,int LinkType, int time,String animation);

    public void stopAnimation();


}
