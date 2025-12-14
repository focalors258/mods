package com.integration_package_core.mixin;

import com.integration_package_core.event.Event.ToughnessEvent;
import com.integration_package_core.mixinTool.LivingEntityExpand;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.tool.collide.OBB;
import com.integration_package_core.util.Network.NetworkManager;
import com.integration_package_core.util.Registries;
import com.integration_package_core.util.RegistryHandler.AttributeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.UUID;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, net.minecraftforge.common.extensions.IForgeLivingEntity, LivingEntityExpand {


    //private Object setAttributeBaseValue;

    //  //最大韧性  长期
    //  @Unique
    //  public float maxToughness = 20;
    //击溃时间  长期
    //  @Unique
    //  public float smashTime = 200;
    @Unique
    public float smashEnd = 0;
    @Unique
    public boolean isSmash = false;
    @Unique
    public float toughness = 20;
    @OnlyIn(Dist.CLIENT)
    @Unique
    public float toughness_move = 1;
    @OnlyIn(Dist.CLIENT)
    @Unique
    public float health_move = 1;
    @OnlyIn(Dist.CLIENT)
    @Unique
    public float absorption_move = 0;
    @OnlyIn(Dist.CLIENT)
    @Unique
    public float toughness_end = 0;
    @OnlyIn(Dist.CLIENT)
    @Unique
    public float health_end = 0;
    @OnlyIn(Dist.CLIENT)
    @Unique
    public float absorption_end = 0;
    @OnlyIn(Dist.CLIENT)
    @Unique
    public float otoughness_move = 1;
    @OnlyIn(Dist.CLIENT)
    @Unique
    public float ohealth_move = 1;
    @OnlyIn(Dist.CLIENT)
    @Unique
    public float oabsorption_move = 0;
    //攻击硬直
    @Unique
    public boolean isRigid = false;
    @Unique
    public float rigidEnd = 0;
    @Unique
    public float rigidTime = 0;

    //  @Accessor
    //   private Vec3 position;
    @Unique
    public LivingEntity source;
    @Unique
    private boolean load = true;

    //

    //public float EntityBar_shield_speed = 0;


    public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

    }


    @Shadow
    public abstract float getMaxHealth();

    @Shadow
    public abstract float getHealth();

    @Shadow
    public abstract AttributeMap getAttributes();

    @Shadow
    public abstract float getAbsorptionAmount();

    @Shadow
    public abstract double getAttributeBaseValue(Attribute pAttribute);

//   public float getHealth_move() {

//       return health_move;
//   }

//   public float getAbsorption_move() {

//       return absorption_move;
//   }

    //  public float getToughness_move() {

    @Shadow
    @Nullable
    public abstract AttributeInstance getAttribute(Attribute pAttribute);

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot pSlot);

    @Inject(
            method = "tick",
            at = @At(
                    value = "TAIL"

            )
    )
    @OnlyIn(Dist.CLIENT)
    public void tick(CallbackInfo ci) {

        if (getAttributes() == null || (((LivingEntity) (Object) this) instanceof Player)) return;


        if (
                Minecraft.getInstance().player != null &&
                        this.level().getServer() == null &&
                        Minecraft.getInstance().player.distanceTo(this) < 20) {


            float presentH = this.getHealth() / this.getMaxHealth();

            float presentT = toughness / getMaxToughness();

            float presentA = this.getAbsorptionAmount() / this.getMaxHealth();
            if (this.tickCount <= 1) {
                toughness = getMaxToughness();
            }

            //  oabsorption_move = absorption_move;
            // // ohealth_move = health_move;
            //  otoughness_move = toughness_move;

            //     float speed = 0;

            //     if (this.invulnerableTime == 0) {
            //         speed = 0.02F;
            //     } else {
            //         speed = 0.005F;
            //     }
            if (health_end <= tickCount) {
                //  if (health_end <= tickCount) {  }
                ohealth_move = presentH;
            } else {
                //   health_move = presentH;
            }

            if (toughness_end <= tickCount) {
                //  if (toughness_move != presentT) {
                //      toughness_end = tickCount + 30;
                //  }
                //   if (toughness_end <= tickCount) {  }
                otoughness_move = presentT;

            } else {
                //  toughness_move = presentT;
            }

            if (absorption_end <= tickCount) {
                //  if (absorption_move != presentA) {
                //      absorption_end = tickCount + 30;
                //  }
                //  if (absorption_end <= tickCount) {}
                oabsorption_move = presentA;
            } else {
                //   absorption_move = presentA;
            }


            //if (0) {}
            //EntityBar.getBarMove(health_move, presentH, speed);
            //    absorption_move = EntityBar.getBarMove(absorption_move, presentA, speed);
            //    toughness_move = EntityBar.getBarMove(toughness_move, presentT, speed);


        }


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

    @Inject(
            method = "tick",
            at = @At(
                    value = "HEAD"

            )
    )
    public void tick1(CallbackInfo ci) {

        // DeBug.tell(String.valueOf(this.getMaxToughness()));&& !(((LivingEntity) (Object) this) instanceof Player)

        if (this.level().getServer() != null) {

            if ((smashEnd - this.tickCount) <= 0 && toughness == 0 && !isSmash) {

                setSmashEnd(this.tickCount + getSmashTime());
                setSmash(true);
            }
            if ((smashEnd - this.tickCount) <= 0 && isSmash) {
                //  System.out.println(smashEnd - this.tickCount + "   " + isSmash)
                setToughness(getMaxToughness());
                setSmash(false);

            }
            if (this.rigidEnd < tickCount) {
                this.setRigid(false);
            }

            if (((Entity) this) instanceof Mob m) {

                if (isSmash || isRigid) {
                    m.setNoAi(true);
                } else {
                    m.setNoAi(false);
                }
            }

            if (load) {

                setToughness(getMaxToughness());

                load = false;

            }
        }

    }

    //      return toughness_move;
    //  }
//弱点击破
    public boolean isSmash() {

        return isSmash;

    }

    public void setSmash(boolean value) {
        isSmash = value;
        CompoundTag d = new CompoundTag();
        d.putInt("id", this.getId());
        d.putBoolean("smash", value);

        if (this.level().getServer() != null) NetworkManager.sendAll("isSmash", d);
    }

    public void addRigid(int time) {
        if (!isRigid) {
            setRigidEnd(time);
            setRigidTime(time);
            setRigid(true);
        }

    }

    public boolean isRigid() {

        return isRigid;
    }

    public void setRigid(boolean r) {

        isRigid = r;
        CompoundTag d = new CompoundTag();
        d.putInt("id", this.getId());
        d.putBoolean("isRigid", isRigid);
        if (this.level().getServer() != null) NetworkManager.sendAll("isRigidSync", d);

    }

    public float getRigidEnd() {

        return rigidEnd;

    }

    public void setRigidEnd(float value) {

        //   System.out.println(value);


        rigidEnd = value + this.tickCount;
        CompoundTag d = new CompoundTag();
        d.putInt("id", this.getId());
        d.putFloat("rigidEnd", value);
        if (this.level().getServer() != null) NetworkManager.sendAll("rigidEndSync", d);
    }


    public void hit(Vector3f pose,Vector3f deflect, DamageSource ds, float v){

     OBB o=   OBB.of(this);
                o.pose=pose;
                o.deflect=deflect;

        this.hit(o,ds,v);

    }


    public void hit(OBB o, DamageSource ds, float v) {


        this.level().getEntities(this, o.postAABB()).forEach(e->{

            if (e instanceof LivingEntity&&OBB.of(e).collide(o)){

                e.hurt(ds, v);

            }

        });

    //  if (ds.getEntity() instanceof LivingEntity) {
    //      if (new OBB(ds.getEntity()).collide(o)) {

    //
    //      }
    //  }
    }

    public float getRigidTime() {

        return rigidTime;

    }

    public void setRigidTime(float value) {

        rigidTime = value;
        CompoundTag d = new CompoundTag();
        d.putInt("id", this.getId());
        d.putFloat("rigidTime", rigidTime);
        if (this.level().getServer() != null) NetworkManager.sendAll("rigidTimeSync", d);
    }

    public float getHealth_end() {

        return health_end;
    }

    /**
     * 设置
     *
     * @param health_end
     */
    public void setHealth_end(float health_end) {
        this.health_end = health_end;
    }

    public float getAbsorption_end() {

        return absorption_end;
    }

    /**
     * 设置
     *
     * @param absorption_end
     */
    public void setAbsorption_end(float absorption_end) {
        this.absorption_end = absorption_end;
    }

    public float getToughness_end() {

        return toughness_end;
    }

    /**
     * 设置
     *
     * @param toughness_end
     */
    public void setToughness_end(float toughness_end) {
        this.toughness_end = toughness_end;
    }

    public float getOHealth_move() {

        return ohealth_move;
    }

    public float getOAbsorption_move() {

        return oabsorption_move;
    }

    public float getOToughness_move() {

        return otoughness_move;
    }

    public float getSmashEnd() {

        return smashEnd;
    }

    public void setSmashEnd(float value) {

        smashEnd = value;
        CompoundTag d = new CompoundTag();
        d.putInt("id", this.getId());
        d.putFloat("smashEnd", smashEnd);
        if (this.level().getServer() != null) NetworkManager.sendAll("smashEndSync", d);
    }

    public float getSmashTime() {

        AttributeInstance a = this.getAttribute(AttributeRegistry.SmashTime.get());
        if (a != null) {

            return (float) a.getValue();
        }

        return 0;


    }

    public void setSmashTime(float value) {


        AttributeInstance a = this.getAttribute(AttributeRegistry.SmashTime.get());

        if (a != null) {
            if (a.getModifier(new UUID(6776, 35653)) == null) {
                a.addPermanentModifier(new AttributeModifier(new UUID(6776, 35653), "SmashTime", value - 200, AttributeModifier.Operation.ADDITION));
            } else {
                a.removeModifier(new UUID(6776, 35653));
                a.addPermanentModifier(new AttributeModifier(new UUID(6776, 35653), "SmashTime", value - 200, AttributeModifier.Operation.ADDITION));
            }
        }

    }

    public void hurtToughness(LivingEntity source, float value) {

        MinecraftForge.EVENT_BUS.post(new ToughnessEvent.HurtEvent(value, (LivingEntity) (Object) this, source));
        this.source = source;
        hurtToughnessBase(value);
    }

    public void hurtToughness(float value) {

        MinecraftForge.EVENT_BUS.post(new ToughnessEvent.HurtEvent(value, (LivingEntity) (Object) this));

        hurtToughnessBase(value);
    }

    @Unique
    public void hurtToughnessBase(float value) {


        AttributeInstance a = this.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (a != null) {
            value = (float) (value * (30 / (a.getValue() + 30)));
        }

        CompoundTag c=new CompoundTag();
        c.putInt("hurt", this.getId());
        c.putFloat("value",value);
        c.putFloat("maxHealth", getMaxHealth());
        NetworkManager.send("damageEventBar",c);

        setToughness(getToughness() - value);


    }

    public float getToughness() {

        if ((Object) this instanceof PlayerExpand p) {

            ItemStack item = this.getItemBySlot(EquipmentSlot.MAINHAND);

            String id = Registries.getId(item);
//需要常加载列表里存在
            if (p.getWeaponList().contains(id)) return p.getWeaponData().get(id).toughnessValue;

            return toughness;

        } else {
            // if (this.level().getServer() != null)// System.out.println(toughness);
            return toughness;
        }

    }

    public void setToughness(float value) {

        //    boolean base = true;

        if (((Object) this instanceof PlayerExpand p)) {
            //    ItemStack item = this.getItemBySlot(EquipmentSlot.MAINHAND);
            //String id = Registries.getId(item);

            // base = p.setToughnessBase(value, "minecraft:air");

        }

        //  if (base) {    }//原版

        toughness = Math.min(Math.max(0, value), getMaxToughness());
        CompoundTag d = new CompoundTag();
        d.putFloat("toughness", toughness);
        d.putInt("id", this.getId());

        if (this.level().getServer() != null) {

            if (toughness == 0 && !isSmash()) {

                MinecraftForge.EVENT_BUS.post(new ToughnessEvent.SmashEvent(0, (LivingEntity) (Object) this, source));

                this.source = null;
            }

            NetworkManager.sendAll("toughnessSync", d);
        }


    }

    public float getMaxToughness() {
        AttributeInstance a = this.getAttribute(AttributeRegistry.MaxToughness.get());

        if (a != null) {

            return (float) a.getValue();
        }

        return 0;
    }

    public void setMaxToughness(float value) {

        AttributeInstance a = this.getAttribute(AttributeRegistry.MaxToughness.get());

        if (a != null) {
            if (a.getModifier(new UUID(67546, 353)) == null) {
                a.addPermanentModifier(new AttributeModifier(new UUID(67546, 353), "MaxToughness", value - 20, AttributeModifier.Operation.ADDITION));
            } else {
                a.removeModifier(new UUID(67546, 353));
                a.addPermanentModifier(new AttributeModifier(new UUID(67546, 353), "MaxToughness", value - 20, AttributeModifier.Operation.ADDITION));
            }
        }

    }

    @Inject(
            method = "setAbsorptionAmount",
            at = @At(
                    value = "TAIL"

            )
    )
    public void setAbsorptionAmount1(float pAbsorptionAmount, CallbackInfo ci) {


        CompoundTag d = new CompoundTag();
        d.putInt("id", this.getId());
        d.putFloat("AbsorptionAmount", pAbsorptionAmount);


        if (this.level().getServer() != null) NetworkManager.sendAll("AbsorptionAmount", d);

    }

    /**
     * 获取
     *
     * @return isSmash
     */
    public boolean isIsSmash() {
        return isSmash;
    }

    /**
     * 设置
     *
     * @param isSmash
     */
    public void setIsSmash(boolean isSmash) {
        this.isSmash = isSmash;
    }

    /**
     * 获取
     *
     * @return toughness_move
     */
    public float getToughness_move() {
        return toughness_move;
    }

    /**
     * 设置
     *
     * @param toughness_move
     */
    public void setToughness_move(float toughness_move) {
        this.toughness_move = toughness_move;
    }

    /**
     * 获取
     *
     * @return health_move
     */
    public float getHealth_move() {
        return health_move;
    }

    /**
     * 设置
     *
     * @param health_move
     */
    public void setHealth_move(float health_move) {
        this.health_move = health_move;
    }

    /**
     * 获取
     *
     * @return absorption_move
     */
    public float getAbsorption_move() {
        return absorption_move;
    }

    /**
     * 设置
     *
     * @param absorption_move
     */
    public void setAbsorption_move(float absorption_move) {
        this.absorption_move = absorption_move;
    }

    /**
     * 获取
     *
     * @return otoughness_move
     */
    public float getOtoughness_move() {
        return otoughness_move;
    }

    /**
     * 设置
     *
     * @param otoughness_move
     */
    public void setOtoughness_move(float otoughness_move) {
        this.otoughness_move = otoughness_move;
    }

    /**
     * 获取
     *
     * @return ohealth_move
     */
    public float getOhealth_move() {
        return ohealth_move;
    }

    /**
     * 设置
     *
     * @param ohealth_move
     */
    public void setOhealth_move(float ohealth_move) {
        this.ohealth_move = ohealth_move;
    }

    /**
     * 获取
     *
     * @return oabsorption_move
     */
    public float getOabsorption_move() {
        return oabsorption_move;
    }

    /**
     * 设置
     *
     * @param oabsorption_move
     */
    public void setOabsorption_move(float oabsorption_move) {
        this.oabsorption_move = oabsorption_move;
    }

    /**
     * 获取
     *
     * @return isRigid
     */
    public boolean isIsRigid() {
        return isRigid;
    }

    /**
     * 设置
     *
     * @param isRigid
     */
    public void setIsRigid(boolean isRigid) {
        this.isRigid = isRigid;
    }

    /**
     * 获取
     *
     * @return source
     */
    public LivingEntity getSource() {
        return source;
    }

    /**
     * 设置
     *
     * @param source
     */
    public void setSource(LivingEntity source) {
        this.source = source;
    }

    /**
     * 获取
     *
     * @return load
     */
    public boolean isLoad() {
        return load;
    }

    /**
     * 设置
     *
     * @param load
     */
    public void setLoad(boolean load) {
        this.load = load;
    }

    public String toString() {
        return "LivingEntityMixin{smashEnd = " + smashEnd + ", isSmash = " + isSmash + ", toughness = " + toughness + ", toughness_move = " + toughness_move + ", health_move = " + health_move + ", absorption_move = " + absorption_move + ", toughness_end = " + toughness_end + ", health_end = " + health_end + ", absorption_end = " + absorption_end + ", otoughness_move = " + otoughness_move + ", ohealth_move = " + ohealth_move + ", oabsorption_move = " + oabsorption_move + ", isRigid = " + isRigid + ", rigidEnd = " + rigidEnd + ", rigidTime = " + rigidTime + ", source = " + source + ", load = " + load + "}";
    }
/*
    @ModifyVariable(
            method = "hurt",
            at = @At(

                    //  value = "STORE",//在局部变量被赋值时注入 需指定局部变量索引
                    value = "HEAD",//在形参被赋值时注入
                    ordinal = 0//在目标变量的第n次访问后注入(一般为 value = "STORE"时才有效
            ),

            ordinal = 6  ,// 指明要修改的是第二个参数 (0是第一个pDamageSource, 1是第二个pDamageAmount)

            argsOnly = true//排除非形参的变量
              //name = "pDamageAmount" // 明确指定要修改的参数名称
    )
    private float modifyDamageAmount(float pDamageAmount) {
        // 1. 创建事件实例，传入原始伤害值
        LivingEntity entity = (LivingEntity) (Object) this;

        LivingAttackEvent e = new LivingAttackEvent(entity, pDamageSource, pDamageAmount);

        MinecraftForge.EVENT_BUS.post(e);

       // if (e.isCanceled()) ci.cancel();
        // 2. 触发事件








        System.out.println(pDamageAmount);
        // 3. 检查事件是否被取消，如果被取消，则将伤害设为0
        //  if (event.isCanceled()) {
        //      return 0.0F;
        //  }

        // 4. 返回事件中可能被修改过的金额
        // 这将成为方法内后续所有逻辑使用的 pDamageAmount 值
        return e.getAmount();
    }


 */
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