package com.integration_package_core.mixinTool;

import com.integration_package_core.animation.PlayerAnimationEntity;
import com.integration_package_core.optimize.WeaponStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface PlayerExpand {


    public PlayerAnimationEntity getAnimationEntity();

  public Vec3  getMove();

  public void setServerMoveCache(Vec3 v);
    public void pushTick( Consumer<PlayerAnimationEntity> customizeTick);
    public void pushTick(Consumer<PlayerAnimationEntity> customizeTick ,Consumer<PlayerAnimationEntity> endTick);

    public void mainTick(Predicate<PlayerAnimationEntity> customizeTick);
    public void popTick();

    public  void setAnimationEntity(PlayerAnimationEntity entity);

    public void playAnimation(int playType, int LinkType, int timeInit, boolean elevationAngle, String animationInit, Consumer<PlayerAnimationEntity> animationConsumer);

    public boolean areAnimationLogic(String name);

    public void clearAnimationLogic();
    public void removeAnimationLogic(String name);
    public void pushTick(int time, String name, Consumer<PlayerAnimationEntity> customizeTick);

    public void playStageAnimation(Consumer<PlayerAnimationEntity> animationConsumer);
    public void playAnimation(int playType, int timeInit, boolean baseInit, String animationInit);

  public WeaponStack getWeaponStack();
  public WeaponStack getWeaponStack(String id);
  public void playStageAnimation(boolean baseInit,Consumer<PlayerAnimationEntity> animationConsumer);
    public void setMove(Vec3 v);
  public void  setWeaponList(ArrayList<String> a);
    public void playAnimation(String animation, int time, Consumer<PlayerAnimationEntity> animationConsumer);
  public boolean isStopMove();
    public void playAnimation(int PlayType, int LinkType, int time, boolean multistage, boolean elevationAngle, String animationInit, Consumer<PlayerAnimationEntity> animationConsumer) ;

  public void setStopMove(boolean s);


   // public void playAnimation(int PlayType,int LinkType, int time,String animation);
   public void initAnimation();
    public void stopAnimation(boolean link);

  public void playAnimations(int PlayType, int LinkType, Predicate<PlayerAnimationEntity> animationConsumer);


  public void playAnimation(boolean repeat, Predicate<PlayerAnimationEntity> animationConsumer);


  public void playAnimation(Predicate<PlayerAnimationEntity> animationConsumer);

  public ArrayList<String> getWeaponList();

  public void addWeaponData(ItemStack item);

  public double getMaxToughness(String itemId);

  public double getWeaponMaxToughness(ItemStack item);
  //public double getMaxToughness(ItemStack item);

  public HashMap<String, WeaponStack> getWeaponData();

  //public void addWeaponData(int slotId);
  public void setToughnessCool(int time, String itemId);
  public void addWeaponList(String id);
  public void addWeaponList(ItemStack item);

  public void pushWeapon(CompoundTag nbt,String id);
  public void addWeaponData(CompoundTag nbt, String id);
 // public void addWeaponData(ItemStack item,int slotId);

  public void pushWeapon(ItemStack slotId);

//public void pushWeapons(ItemStack slotId);
//public void pushWeapons(int slotId);

//public void pushWeapon(int slotId);

  //public void setToughness(float value, String itemId);

  public float getToughness(String itemId);

  public boolean setToughness(float value, String itemId);

  public void hurtToughness(float value);
  public void setToughnessCool(int time, float value, String itemId);
}
