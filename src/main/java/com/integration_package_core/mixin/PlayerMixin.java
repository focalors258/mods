package com.integration_package_core.mixin;

import com.integration_package_core.animation.PlayerAnimationEntity;
import com.integration_package_core.mixinTool.PlayerExpand;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements net.minecraftforge.common.extensions.IForgePlayer, PlayerExpand {


    @Unique
    PlayerAnimationEntity animationEntity;


    public PlayerAnimationEntity getAnimationEntity() {
        return animationEntity;
    }

    public void setAnimationEntity(PlayerAnimationEntity entity) {

        animationEntity = entity;

    }

    ;

    public void playAnimation(int PlayType,int LinkType, int time,String animationInit, Consumer<PlayerAnimationEntity> animationConsumer) {
        //System.out.println(animationEntity.valid);
//vvalid
        //  System.out.println(animationEntity);

        if (((animationEntity != null && animationEntity.isEnd()))||LinkType==0) {

            if (this.animationEntity != null) {
                this.animationEntity.remove(RemovalReason.DISCARDED);
            }
            this.animationEntity = null;
        }

        if (this.animationEntity == null) {//注意 刚创建的实体对象不会马上同步到客户端

            PlayerAnimationEntity entity = new PlayerAnimationEntity(this.level(), this, time);

            this.level().addFreshEntity(entity);

            animationEntity = entity;

            animationEntity.animation = animationInit;

            //animationEntity.newAnimation = animation;
            animationEntity.playType = PlayType;

            animationEntity.xRot = this.xRotO;

            animationEntity.yRot = this.yRotO;

        if(animationConsumer!=null)    animationConsumer.accept(animationEntity);

        } else if(animationEntity.stageCool <animationEntity.tickCount||LinkType==1){ //&& animationEntity.time <= animationEntity.tickCount

            animationEntity.stage++;

            if( animationEntity.stage >= animationEntity.maxStage)   animationEntity.stage =0;

            animationEntity.restart = true;
            //System.out.println(animationEntity.isRemoved());
            animationEntity.animation = animationInit;

            animationEntity.playType = PlayType;

            animationEntity.tickCount = 0;

           // animationEntity.updateTick=0;

            animationEntity.time = time;

            animationEntity.xRot = this.xRotO;

            animationEntity.yRot = this.yRotO;

            if(animationConsumer!=null)    animationConsumer.accept(animationEntity);
        }



       // System.out.println(animationEntity);
        // System.out.println(this.xRotO);

        // System.out.println(this.yRotO);



    }

    public void stopAnimation() {

        if (animationEntity != null) {

            animationEntity.time = tickCount - 6;

            animationEntity = null;

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

//  @Override
//  public LivingEntity self() {
//      return super.self();
//  }
}
