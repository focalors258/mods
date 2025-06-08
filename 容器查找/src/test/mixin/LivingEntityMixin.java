package com.chestsearch.mixin;

import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.minecraftforge.common.extensions.IForgeLivingEntity {


    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

    }



    @Inject(
            method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V",
            at = @At(
                    value = "TAIL"

            )
    )
    public void init(EntityType<? extends LivingEntity> pEntityType, Level pLevel, CallbackInfo ci){

    //    System.out.println( this.getPersistentData().getInt("345345"));
//
//
//
    //    System.out.println("我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈");
//
//
//
//
//
//
    }

    @Shadow
    public abstract float getMaxHealth();



    @Shadow
    public abstract float getHealth();

    //  @Accessor
    //   private Vec3 position;

    @Shadow
    public abstract AttributeMap getAttributes();


    @Shadow
    public abstract float getAbsorptionAmount();

    //
    @Unique
    public float health_move = 0;
    //public float EntityBar_shield_speed = 0;


    @Shadow
    public abstract double getAttributeBaseValue(Attribute pAttribute);




    @Inject(
            method = "tick",
            at = @At(
                    value = "TAIL"

            )
    )
    public void tick(CallbackInfo ci) {

        if (getAttributes() == null) return;


        //System.out.println( this.getPersistentData().getInt("345345"));
//this.getServer().getWorldData().


  //      com.entity_bar.data.EntityData.getHealthMove(this);




























     //   System.out.println(this.tickCount );

      //  float[] a = EntityBarDataHandle.getBarMove(EntityData.EntityBar_health_move.get(uuid), getHealth() / getMaxHealth(), EntityData.EntityBar_health_speed.get(uuid));
//
      //  EntityData.EntityBar_health_move.put(this.uuid, a[0]);
//
      //  EntityData.EntityBar_health_speed.put(this.uuid, a[1]);
//
      //  System.out.println("变化条" + a[0]);
//
      //  System.out.println("速度" + a[1]);

    }

}

// "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;render(Lnet/minecraft/world/entity/Entity;DDDFFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",

//this.position
//EntityBar_health_move = getHealth();

//  if(Minecraft.getInstance().player.openMenu())
//  System.out.println(oldHealth);
//Minecraft.getInstance().;
// this.level().getplay
//System.out.println(111);
//  shift = At.Shift.AFTER

//target ="Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/network/syncher/SynchedEntityData;define(Lnet.minecraft.network.syncher)",


//private float*  toughness;

//private  float health_move;

//private float  shield_move;
//private float toughness_move;


//  this.entityData.define(SynchedEntityData.defineId(LivingEntityMixin.class, EntityDataSerializers.INT),0);
// EntityData.EntityBar_health_move.put(this.uuid, 1F);
// EntityData.EntityBar_toughness_move.put(this.uuid, 1D);
// EntityData.EntityBar_shield_move.put(this.uuid, 1F);
// EntityData.EntityBar_health_speed.put(this.uuid, 0F);
// EntityData.EntityBar_shield_speed.put(this.uuid, 0F);
// EntityData.EntityBar_toughness_speed.put(this.uuid, 0F);
// EntityData.EntityBar_toughness.put(this.uuid, this.getAttributeBaseValue(Attributes.ARMOR_TOUGHNESS));

//@Inject(
//        method = "reapplyPosition",
//        at = @At(
//                value = "TAIL"//不能在构造函数头部mixin
//        )
//)
// public void init_oldHealth(EntityType<? extends LivingEntity> pEntityType, Level pLevel, CallbackInfo ci) {
// //    if (getAttributes() == null) return;
//
//     LivingEntityData.EntityBar_health_move.put(this.uuid, 1F);
//
//
//     LivingEntityData.EntityBar_toughness_move.put(this.uuid, 1D);
//
//     LivingEntityData.EntityBar_shield_move.put(this.uuid, 1F);
//
//     LivingEntityData.EntityBar_health_speed.put(this.uuid, 0F);
//
//     LivingEntityData.EntityBar_shield_speed.put(this.uuid, 0F);
//
//     LivingEntityData.EntityBar_toughness_speed.put(this.uuid, 0F);
//
//
//     LivingEntityData.EntityBar_toughness.put(this.uuid, this.getAttributeBaseValue(Attributes.ARMOR_TOUGHNESS));
//
// }