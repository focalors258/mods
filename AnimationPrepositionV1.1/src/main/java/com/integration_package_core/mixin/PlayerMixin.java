package com.integration_package_core.mixin;

import com.integration_package_core.animation.PlayerAnimationEntity;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.util.Network.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;
import java.util.function.Consumer;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements net.minecraftforge.common.extensions.IForgePlayer, PlayerExpand {


    @Unique
    PlayerAnimationEntity animationEntity;


    @Unique
    public Vec3 serverMoveCache =new Vec3(0,0,0);


    public void setAnimationLogic(Consumer<PlayerAnimationEntity> customizeTick) {

        if (getAnimationEntity() != null) getAnimationEntity().customizeTick = customizeTick;

    }

    public PlayerAnimationEntity getAnimationEntity() {
        return animationEntity;
    }

    public void setAnimationEntity(PlayerAnimationEntity entity) {

        animationEntity = entity;

    }


    public void playAnimation(int PlayType, int time, boolean elevationAngle, String animation) {

        playAnimation(PlayType, 0, time, elevationAngle, animation, null);


    }

    public void playStageAnimation(Consumer<PlayerAnimationEntity> animationConsumer) {

        playAnimation(2, 2, 100, true, "air", animationConsumer);


    }

    public void playAnimation(String animation, int time, Consumer<PlayerAnimationEntity> animationConsumer) {//单次播放

        if (animationEntity == null || !Objects.equals(animation, animationEntity.getAnimation())) {

            playAnimation(2, 1, time, false, true, animation, animationConsumer);

        }
    }

    public void playAnimation(int PlayType, int LinkType, int time, boolean elevationAngle, String animationInit, Consumer<PlayerAnimationEntity> animationConsumer) {


        playAnimation(PlayType, LinkType, time, true, elevationAngle, animationInit, animationConsumer);


    }

    public void playAnimation(int PlayType, int LinkType, int time, boolean multistage, boolean elevationAngle, String animationInit, Consumer<PlayerAnimationEntity> animationConsumer) {
        //System.out.println(animationEntity.valid);
//vvalid
        //  System.out.println(animationEntity);

        if ((animationEntity != null && animationEntity.isEnd()) || LinkType == 0) {

            if (this.animationEntity != null) {

                this.animationEntity.customizeTick=null;//重置tick

                this.animationEntity.stage = 0;
                this.animationEntity.setSnow(false);//remove(RemovalReason.DISCARDED);
            }
            //    this.animationEntity = null;
        }

        //     if (animationEntity!=null&&this.level().getEntity(animationEntity.getId())==null){
//
        //         System.out.println(animationEntity);
//
        //         animationEntity.remove(null);
//
        //         this.level().addFreshEntity(animationEntity);
//
        //     }

        if (this.animationEntity == null || this.level().getEntity(animationEntity.getId()) == null) {//注意 刚创建的实体对象不会马上同步到客户端

            PlayerAnimationEntity entity = new PlayerAnimationEntity(this.level(), this, time);//刚新建的实体动画仍无法传到客户端

            this.level().addFreshEntity(entity);

            animationEntity = entity;


            animationEntity.playType = PlayType;

            if (elevationAngle) animationEntity.setXRots(this.xRotO);

            animationEntity.setYRots(this.yRotO);

            animationEntity.setAnimation(animationInit);//是否为多段动画

            if (animationConsumer != null) animationConsumer.accept(animationEntity);
            //  if (!multistage)


        } //初始化
        else if (!this.animationEntity.getSnow()) {



            animationEntity.setPos(this.position());

            animationEntity.setTickCount(0);//注意  动画播放过程与tick关联

            animationEntity.setTime(time);

            this.animationEntity.setSnow(true);

            animationEntity.playType = PlayType;

            this.animationEntity.stage = 0;

            if (elevationAngle) animationEntity.setXRots(this.xRotO);

            animationEntity.setYRots(this.yRotO);

            if (animationConsumer != null) animationConsumer.accept(animationEntity);

            if (!multistage) animationEntity.setAnimation(animationInit);//是否为多段动画

        } //第一段
        else if (animationEntity.stageCool < animationEntity.tickCount || LinkType == 1) { //&& animationEntity.time <= animationEntity.tickCount

            animationEntity.setPos(this.position());

            this.animationEntity.setSnow(true);


            animationEntity.stage++;

            if (animationEntity.stage >= animationEntity.getMaxStage()) animationEntity.stage = 0;


            animationEntity.playType = PlayType;

            animationEntity.setTickCount(0);//注意  动画播放过程与tick关联

            // animationEntity.updateTick=0;

            animationEntity.setTime(time);


            if (elevationAngle) animationEntity.setXRots(this.xRotO);

            animationEntity.setYRots(this.yRotO);


            if (!multistage) animationEntity.setAnimation(animationInit);//是否为多段动画


            //避免同时修改动画
            if (animationConsumer != null) animationConsumer.accept(animationEntity);


        }//多段


        // System.out.println(animationEntity);
        // System.out.println(this.xRotO);

        // System.out.println(this.yRotO);


    }

    public void stopAnimation() {

        if (animationEntity != null) {

            animationEntity.setTime(tickCount - 6);

            this.animationEntity.customizeTick=null;//重置tick

            this.animationEntity.setSnow(false);

        }


    }


    public void setServerMoveCache(Vec3 v) {

        serverMoveCache=v;

    }


public Vec3 getMove(){

    if (this.getServer() == null) {

   return      this.getDeltaMovement();


    }else{


        return serverMoveCache;

    }


}
    public void setMove(Vec3 v) {

        if (this.getServer() == null) {

            this.setDeltaMovement(v);

        } else {

            CompoundTag s = new CompoundTag();

            s.putDouble("x", v.x);
            s.putDouble("y", v.y);
            s.putDouble("z", v.z);


            NetworkManager.send("player_move_set", s, (ServerPlayer) this.level().getPlayerByUUID(this.getUUID()));

        }


    }


    @Shadow
    @Final
    private Inventory inventory;
    @Shadow
    @Final
    protected static EntityDataAccessor<Byte> DATA_PLAYER_MAIN_HAND;

    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return this.inventory.armor;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        if (pSlot == EquipmentSlot.MAINHAND) {
            return this.inventory.getSelected();
        } else if (pSlot == EquipmentSlot.OFFHAND) {
            return this.inventory.offhand.get(0);
        } else {
            return pSlot.getType() == EquipmentSlot.Type.ARMOR ? this.inventory.armor.get(pSlot.getIndex()) : ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
        this.verifyEquippedItem(pStack);
        if (pSlot == EquipmentSlot.MAINHAND) {
            this.onEquipItem(pSlot, this.inventory.items.set(this.inventory.selected, pStack), pStack);
        } else if (pSlot == EquipmentSlot.OFFHAND) {
            this.onEquipItem(pSlot, this.inventory.offhand.set(0, pStack), pStack);
        } else if (pSlot.getType() == EquipmentSlot.Type.ARMOR) {
            this.onEquipItem(pSlot, this.inventory.armor.set(pSlot.getIndex(), pStack), pStack);
        }

    }

    @Override
    public HumanoidArm getMainArm() {
        return this.entityData.get(DATA_PLAYER_MAIN_HAND) == 0 ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }


//  @Override
//  public boolean alwaysAccepts() {
//      return super.alwaysAccepts();
//  }

    //    if (this instanceof EntityExpand e){
//
    //     e.setDimensions(new EntityDimensions(0,0,false));
    //        System.out.println(4563543);
//
    //    }
    //animationEntity.newAnimation = animation;
//  @Override
//  public LivingEntity self() {
//      return super.self();
//  }
}
