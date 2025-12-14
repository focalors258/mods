package com.integration_package_core.mixin;

import com.integration_package_core.IPC;
import com.integration_package_core.animation.CustomizeTick;
import com.integration_package_core.animation.PlayerAnimationEntity;
import com.integration_package_core.mixinTool.ItemStackExpand;
import com.integration_package_core.mixinTool.LivingEntityExpand;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.optimize.WeaponStack;
import com.integration_package_core.tool.DeBug;
import com.integration_package_core.util.Network.NetworkManager;
import com.integration_package_core.util.Registries;
import com.integration_package_core.util.RegistryHandler.AttributeRegistry;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.integration_package_core.event.Event.WeaponInitEvent.onWeaponInit;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements net.minecraftforge.common.extensions.IForgePlayer, PlayerExpand {


    @Shadow
    @Final
    protected static EntityDataAccessor<Byte> DATA_PLAYER_MAIN_HAND;
    @Unique
    public ArrayList<String> weaponList = new ArrayList<>();
    @Unique
    public LinkedHashMap<String, WeaponStack> weaponData = new LinkedHashMap<>();
    @Unique
    public Vec3 serverMoveCache = new Vec3(0, 0, 0);
    @Unique
    public boolean stopMove = true;
    @Unique
    PlayerAnimationEntity animationEntity;
    @Shadow
    @Final
    private Inventory inventory;


    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public abstract Iterable<ItemStack> getHandSlots();

    public ArrayList<String> getWeaponList() {

        return weaponList;
    }

    public void setWeaponList(ArrayList<String> a) {

        weaponList = a;
    }

    public WeaponStack getWeaponStack() {

        return getWeaponStack(Registries.getId(this.getItemBySlot(EquipmentSlot.MAINHAND)));
    }

    public WeaponStack getWeaponStack(String id) {

        if (weaponList.contains(id))
            return weaponData.get(id);

        return null;
    }

    public HashMap<String, WeaponStack> getWeaponData() {

        return weaponData;
    }

    public void setToughnessCool(int time, String itemId) {

        WeaponStack data = this.getWeaponData().get(itemId);
        if (this.getServer() == null) return;

        if (this.getToughness(itemId) < this.getMaxToughness(itemId)) {
            data.toughnessRecover = false;

            data.setToughnessTime(time);//计算恢复速度

        } else {
            data.toughnessRecover = true;
        }

    }

    public void hurtToughness(float value) {

        AttributeInstance a = this.getAttribute(Attributes.ARMOR_TOUGHNESS);
        if (a != null) {
            value = (float) (value * (30 / (a.getValue() + 30)));
        }

        String id = Registries.getId(this.getItemBySlot(EquipmentSlot.MAINHAND));

        setToughness(getToughness(id) - value, id);

    }

    public boolean setToughness(float value, String itemId) {//不加物品名称时  设置玩家的的成员变量韧性

        if (this.getWeaponData().containsKey(itemId) && this.getServer() != null) {

            WeaponStack data = this.getWeaponData().get(itemId);

            data.toughnessValue = (float) Math.min(Math.max(0, value), this.getMaxToughness(itemId));

            //  MinecraftForge.EVENT_BUS.post(new ToughnessEvent.SmashEvent(0, (LivingEntity) (Object) this, null));

            NetworkManager.send((Player) (Object) this, "toughnessSyncPlayer", e -> {
                e.putString("itemId", itemId);
                e.putBoolean("recover", data.toughnessRecover);
                e.putFloat("value", data.toughnessValue);
            });
            return false;
        }
        return true;
    }

    public float getToughness(String itemId) {
        //直接获取
        if (this.getWeaponData().containsKey(itemId)) {
            WeaponStack data = this.getWeaponData().get(itemId);

            return data.toughnessValue;
        }
        return ((LivingEntityExpand) this).getToughness();

    }

    public void pushWeapon(ItemStack slotId) {
        NetworkManager.send((Player) (Object) this, "weaponSync", e -> {
            if (slotId.getTag() != null) {
                e.put("nbt", slotId.getTag());
            }
            e.putString("id", Registries.getId(slotId));
        });
        pushWeapon(slotId.getTag(), Registries.getId(slotId));

    }

    public void pushWeapon(CompoundTag nbt, String id) {

        addWeaponData(nbt, id);
        addWeaponList(id);


        // NetworkManager.send((Player) (Object) this, "weaponListSync", e -> {
        //    Collections.sort(weaponList)
        //     weaponList.forEach(e1->{
        //    //     e.arr
        //     });
        // });
    }

    public void addWeaponList(String id) {//常加载列表

        if (!weaponList.contains(id)) weaponList.add(id);

    }


    // public void clearAnimationLogic() {

    //     if (getAnimationEntity() != null) getAnimationEntity().customizeTickList =null;

    // }

    public void addWeaponList(ItemStack item) {//常加载列表

        String id = Registries.getId(item);

        if (!weaponList.contains(id)) weaponList.add(id);

    }

    ;


    //   public void removeAnimationLogic(String name) {
//
    //       if (getAnimationEntity() != null) {
//
    //           if( getAnimationEntity().customizeTickList==null) getAnimationEntity().customizeTickList=new HashMap<>();
    //           getAnimationEntity().customizeTickList.remove(name);
    //       }
//
    //   }

    public void addWeaponData(CompoundTag nbt, String id) {

        //    String id = Registries.getId(item);
        //    System.out.println("cnm"+getWeaponMaxToughness(Registries.getItemStack(id)));

        if (!weaponData.containsKey(id)) {

            CompoundTag data = new CompoundTag();

            onWeaponInit(id, nbt, data);

            weaponData.put(id, new WeaponStack(id, nbt, data, (Player) (Object) this));//设置当前值

        }
//DeBug.tell("789");


        if (weaponData.size() > 36) {

            ArrayList<String> a = new ArrayList<>(weaponData.keySet());
            weaponData.remove(a.get(0));
        }

    }

    public double getMaxToughness(String itemId) {

        if (weaponData.containsKey(itemId)) {

            return getWeaponMaxToughness(weaponData.get(itemId).getItem());

        }
        return 0;
    }

    //IPC 韧性 用在武器身上
//kubejs 韧性 用在玩家身上
    public double getWeaponMaxToughness(ItemStack item) {//玩家受武器影响的属性会延迟更新

        // double value = this.getAttributeValue(AttributeRegistry.MaxToughness.get()) -
        //         ((ItemStackExpand) (Object) (this.getItemBySlot(EquipmentSlot.MAINHAND)))
        //                 .getAttributeValue(EquipmentSlot.MAINHAND, AttributeRegistry.MaxToughness.get());
        //System.out.println(((ItemStackExpand) (Object) item).getAttributeValue(EquipmentSlot.MAINHAND, AttributeRegistry.MaxToughness.get())+getAttributeValue(AttributeRegistry.PlayerMaxToughness.get()));

        return ((ItemStackExpand) (Object) item).getAttributeValue(EquipmentSlot.MAINHAND, AttributeRegistry.MaxToughness.get()) + getAttributeValue(AttributeRegistry.PlayerMaxToughness.get());
    }

    public void mainTick(Predicate<PlayerAnimationEntity> customizeTick) {
        if (getAnimationEntity() != null) {
            getAnimationEntity().newTick = customizeTick;

        }
    }

    //   public boolean areAnimationLogic(String name) {
//
    //       if (getAnimationEntity() != null) {
    //           if( getAnimationEntity().customizeTickList==null) getAnimationEntity().customizeTickList=new HashMap<>();
//
    //           return getAnimationEntity().customizeTickList.containsKey(name);
    //       }
    //       return false;
    //   }
    public void popTick() {
        if (getAnimationEntity() != null) {
            if (getAnimationEntity().poseTickList == null) getAnimationEntity().poseTickList = new ArrayList<>();
            getAnimationEntity().poseTickList.get(0).endTick.accept(getAnimationEntity());
            getAnimationEntity().poseTickList.remove(0);
        }


    }

    public void pushTick(Consumer<PlayerAnimationEntity> customizeTick, Consumer<PlayerAnimationEntity> endTick) {
        if (getAnimationEntity() != null) {
            if (getAnimationEntity().poseTickList == null) getAnimationEntity().poseTickList = new ArrayList<>();
            getAnimationEntity().poseTickList.add(new CustomizeTick(endTick, customizeTick));
        }
    }

    public void pushTick(Consumer<PlayerAnimationEntity> customizeTick) {
        if (getAnimationEntity() != null) {
            if (getAnimationEntity().poseTickList == null) getAnimationEntity().poseTickList = new ArrayList<>();
            getAnimationEntity().poseTickList.add(new CustomizeTick(null, customizeTick));
        }
    }

    public PlayerAnimationEntity getAnimationEntity() {
        return animationEntity;
    }

    public void setAnimationEntity(PlayerAnimationEntity entity) {

        animationEntity = entity;

        if (this.level().getServer() == null) return;

        CompoundTag data = new CompoundTag();

        data.putInt("player", this.getId());


        int id = animationEntity.getId();

        data.putInt("entity", id);

        NetworkManager.sendAll("PlayerAnimationSync", data);


    }

    public void playAnimation(int PlayType, int time, boolean baseInit, String animation) {//可重复播放单次动画

        if (animationEntity != null) {
            //  System.out.println(animation);
            //  System.out.println(animationEntity.getAnimation());
            if (!Objects.equals(animation, animationEntity.getAnimation())) {

                playAnimation(PlayType, 0, time, baseInit, animation, null);

            } else {

                animationEntity.setTickCount(animationEntity.timeEnd - animationEntity.time);

                //   System.out.println(animationEntity.getAnimation());
                // animationEntity.setAnimation("animation.animation3");
                // animationEntity.setTime(0);
                //animation.animation3
                //  System.out.println(animationEntity.getAnimation());
                //         playAnimation(PlayType, 0, 0, baseInit, "air", null);

                //   playAnimation(PlayType, 0, time, baseInit, animation, null);


                //      System.out.println(animationEntity.getAnimation());
            }


        }
    }

    @Deprecated
    public void playStageAnimation(Consumer<PlayerAnimationEntity> animationConsumer) {

        playAnimation(2, 2, 0, true, "air", animationConsumer);

    }

    @Deprecated
    public void playStageAnimation(boolean baseInit, Consumer<PlayerAnimationEntity> animationConsumer) {

        playAnimation(2, 2, 0, baseInit, "air", animationConsumer);

    }

    public void playAnimation(String animation, int time, Consumer<PlayerAnimationEntity> animationConsumer) {//单次播放

        if (animationEntity == null || !Objects.equals(animation, animationEntity.getAnimation())) {

            playAnimation(2, 1, time, false, true, animation, animationConsumer);

        }
    }

    @Deprecated
    public void playAnimation(int PlayType, int LinkType, int time, boolean baseInit, String animationInit, Consumer<PlayerAnimationEntity> animationConsumer) {


        playAnimation(PlayType, LinkType, time, true, baseInit, animationInit, animationConsumer);


    }

    public void initAnimation() {

        PlayerAnimationEntity e = new PlayerAnimationEntity(this.level(), this, 0);

        animationEntity = e;
    }

    @Inject(
            method = "<init>",
            at = @At(
                    value = "TAIL"
                    //  shift = At.Shift.AFTER
            )
    )
    public void Player(Level pLevel, BlockPos pPos, float pYRot, GameProfile pGameProfile, CallbackInfo ci) {


        if (pLevel.getServer() != null) initAnimation();//初始化动画实体


    }

    @Deprecated
    public void playAnimation(int PlayType, int LinkType, int time, boolean multistage, boolean baseInit, String animationInit, Consumer<PlayerAnimationEntity> animationConsumer) {


        //   if (this.level().getServer()!=null){
        //   System.out.println(this.level().getServer() + "cnmcncmcncmcnm");
        // System.out.println(this.level().getServer());
        PlayerAnimationEntity ae = this.animationEntity;

        {
            if ((ae != null && ae.isEnd()) || LinkType == 0) {

                if (this.animationEntity != null) {
                    if (baseInit) {
                        this.animationEntity.poseTickList = null;//重置tick
                        animationEntity.setTickCount(animationEntity.timeEnd - animationEntity.time);
                        this.animationEntity.setStage(0);
                        this.animationEntity.setSnow(false);//remove(RemovalReason.DISCARDED);
                    }

                }
                //    this.animationEntity = null;
            }


            if (this.animationEntity == null) {//注意 刚创建的实体对象不会马上同步到客户端

//取消同时创建实体及设置动画
                initAnimation();//不在此处初始化

                //  DeBug.tell("add");
                try {
                    if (animationConsumer != null && ae != null) animationConsumer.accept(ae);
                } catch (Exception a) {
                    DeBug.error("AnimationPlaybackError", a);
                }


            } //初始化
            else if (!this.animationEntity.getSnow()) {

                //  if (this.level() != ae.level()) this.level().addFreshEntity(ae);
                //     DeBug.tell("play1");

                if (baseInit) {

                    ae.setTime(time);

                    this.animationEntity.setSnow(true);

                    ae.setPlayType(PlayType);

                    this.animationEntity.setStage(0);

                    ae.setAnimation(animationInit);//是否为多段动画

                }


                if (animationConsumer != null) animationConsumer.accept(ae);


            } //第一段
            else if (ae.stageCool < ae.time - (ae.getTimeEnd() - ae.tickCount) || LinkType == 1) { //&& animationEntity.time <= animationEntity.tickCount


                if (baseInit) {
                    this.animationEntity.setSnow(true);

                    ae.setStage(ae.stage + 1);

                    if (ae.stage >= ae.getMaxStage()) ae.setStage(0);

                    ae.setPlayType(PlayType);

                    ae.setTime(time);

                    ae.setAnimation(animationInit);//是否为多段动画

                }
                //避免同时修改动画
                if (animationConsumer != null) animationConsumer.accept(ae);


            }//多段
        }

        //      CompoundTag data=new CompoundTag();
        //
        //   //   data.putString();
        //
        //      NetworkManager.send("PlayerAnimationToServer");


        // System.out.println(animationEntity);
        // System.out.println(this.xRotO);
        // System.out.println(this.yRotO);


    }

    public void playAnimation(Predicate<PlayerAnimationEntity> animationConsumer) {

        playAnimations(2, 2, animationConsumer);

    }

    public void playAnimations(int PlayType, int LinkType, Predicate<PlayerAnimationEntity> animationConsumer) {
        PlayerAnimationEntity ae = this.animationEntity;

        //  DeBug.tell("c");

        //LinkType=0  可重复播放同一个动作
        //LinkType=1  可无视后摇//待决定是否删除

        {
            if ((ae != null && ae.isEnd())) {
                if (this.animationEntity != null) {
                    //此段待决定是否删除
                    //  if (animationConsumer != null && animationConsumer.test(ae)) {   }|| LinkType == 0
                    this.animationEntity.poseTickList = null;//重置tick
                    this.animationEntity.setStage(0);
                    // this.animationEntity.setSnow(false);//remove(RemovalReason.DISCARDED);
                }
            }
            if (this.animationEntity == null) {//注意 刚创建的实体对象不会马上同步到客户端//初始化
                initAnimation();//不在此处初始化
                try {

                    if (animationConsumer != null && ae != null) animationConsumer.test(ae);
                } catch (Exception a) {
                    DeBug.error("AnimationPlaybackError", a);
                }


            } else if (!this.animationEntity.getSnow()) {
                //  if (this.level() != ae.level()) this.level().addFreshEntity(ae);
                this.animationEntity.setStage(0);

                try {
                    if (animationConsumer != null && animationConsumer.test(ae)) {
                        this.animationEntity.setSnow(true);
                    }

                } catch (Exception a) {
                    DeBug.error("AnimationPlaybackError", a);
                }

                // if (animationConsumer != null) animationConsumer.test(ae); //第一段
            } else if (ae.stageCool < ae.time - (ae.getTimeEnd() - ae.tickCount) || LinkType == 1) { //&& animationEntity.time <= animationEntity.tickCount
                //    System.out.println("stage1"+ae.stage);
                ae.setStage(ae.stage + 1);
                //   System.out.println("stage2"+ae.stage);
                if (ae.stage >= ae.getMaxStage()) ae.setStage(0);
                //   DeBug.tell("a");
                String id = ae.getAnimation();

                int tickCount = ae.tickCount;

                if (LinkType == 0) {
                    ae.setTickCount(ae.timeEnd - ae.time);//重置动画时间
                }
                try {
                    if (animationConsumer != null && animationConsumer.test(ae)) {
                        if (!Objects.equals(id, ae.getAnimation())) {//允许重复从头播放一个动画  注意 需设置后摇为0
                            ae.setTickCount(tickCount);//条件未达成  取消tick修改
                            ae.setTime(ae.time);//重置endtick
                        }
                        this.animationEntity.setSnow(true);//      ae.setPlayType(PlayType);

                    } else {
                        ae.setTickCount(tickCount);//条件未达成  取消tick修改
                    //    ae.setTime(ae.time);//重置endtick////////////////////////
                        ae.setStage(ae.stage - 1);
                        if (ae.stage < 0) ae.setStage(ae.getMaxStage() - 1);
                    }
                } catch (Exception a) {
                    DeBug.error("AnimationPlaybackError", a);
                }
                //  System.out.println("stage3"+ae.stage);
                //避免同时修改动画

            }//多段

            // if (ae != null) {
////
            //  // System.out.println(ae.stageCool);
            //   System.out.println(ae.stage);
            // }
        }

    }


    public void playAnimation(boolean repeat, Predicate<PlayerAnimationEntity> animationConsumer) {
        PlayerAnimationEntity ae = this.animationEntity;
        {
            if (this.animationEntity == null) {//注意 刚创建的实体对象不会马上同步到客户端//初始化
                initAnimation();//不在此处初始化
                try {

                    if (animationConsumer != null && ae != null) animationConsumer.test(ae);
                } catch (Exception a) {
                    DeBug.error("AnimationPlaybackError", a);
                }

            } else if (!this.animationEntity.getSnow()) {
                //  if (this.level() != ae.level()) this.level().addFreshEntity(ae);
                this.animationEntity.setStage(0);
                try {
                    if (animationConsumer != null && animationConsumer.test(ae)) {
                        this.animationEntity.setSnow(true);
                    }
                } catch (Exception a) {
                    DeBug.error("AnimationPlaybackError", a);
                }
                // if (animationConsumer != null) animationConsumer.test(ae); //第一段
            } else if (ae.stageCool < ae.time - (ae.getTimeEnd() - ae.tickCount)) { //&& animationEntity.time <= animationEntity.tickCount

                ae.setStage(ae.stage + 1);
                if (ae.stage >= ae.getMaxStage()) ae.setStage(0);

                String id = ae.getAnimation();
                int tickCount = ae.tickCount;

                if (repeat) {
                    ae.setTickCount(ae.timeEnd - ae.time);//重置动画时间
                }
                try {
                    if (animationConsumer != null && animationConsumer.test(ae)) {
                        if (!Objects.equals(id, ae.getAnimation())) {
                            ae.setTickCount(tickCount);//条件未达成  取消tick修改
                            ae.setTime(ae.time);//重置endtick
                        }
                        this.animationEntity.setSnow(true);//      ae.setPlayType(PlayType);

                    } else {
                        ae.setTickCount(tickCount);//条件未达成  取消tick修改
                     //   ae.setTime(ae.time);//重置endtick///////////////////
                       // ae.seten
                        ae.setStage(ae.stage - 1);
                        if (ae.stage < 0) ae.setStage(ae.getMaxStage() - 1);
                    }

                } catch (Exception a) {
                    DeBug.error("AnimationPlaybackError", a);
                }


            }//多段

        }

    }


    public void stopAnimation(boolean link) {

        if (animationEntity != null && !animationEntity.isEnd()) {
            //  this.animationEntity.customizeTickList = null;//重置tick
        //    animationEntity.setAnimationResource(new ResourceLocation(IPC.MODID, "animations/animation_entity/main.animation.json")  );
            animationEntity.setStageCool(0);//zuijin
           // animationEntity.setAnimation("air");
            animationEntity.setTypeName("");
            animationEntity.setEndLink(link);
            animationEntity.setTime(4);

            if (!link) {

                this.animationEntity.setSnow(false);
                //animationEntity.setTime(4);
                //animationEntity.setAnimation("air");
            } else {
                //  animationEntity.setStageCool(0);
                //  animationEntity.setTime(4);//bug  动画衔接时间过短导致上一段动画无法播放完
                //  animationEntity.setAnimation("air");
                //
            }
        }
    }

    public void setServerMoveCache(Vec3 v) {

        serverMoveCache = v;

    }

    public Vec3 getMove() {

        if (this.getServer() == null) {

            return this.getDeltaMovement();


        } else {


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

    @Inject(
            method = "tick",
            at = @At(
                    value = "TAIL"

            )
    )
    public void tick1(CallbackInfo ci) {//始终加载动画实体


        if (tickCount % 5 == 0) {
            if (this.animationEntity == null) {
                // this.animationEntity.isRemoved() ||
                // !this.animationEntity.isAddedToWorld() ||
                // !this.animationEntity.level().dimension().location().toString().equals(level().dimension().location().toString())) {

                initAnimation();
            }
        }

        //            this.animationEntity.distanceToSqr(this) > 10000 |
        if (this.animationEntity != null) animationEntity.tick();


    }

    @Inject(
            method = "setItemSlot",
            at = @At(
                    value = "HEAD"
            )
    )
    private void setItemSlots(EquipmentSlot pSlot, ItemStack pStack, CallbackInfo ci) {


        System.out.println(325);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return this.inventory.armor;
    }


    // @Override
    // public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
    //     this.verifyEquippedItem(pStack);
    //     if (pSlot == EquipmentSlot.MAINHAND) {
    //         this.onEquipItem(pSlot, this.inventory.items.set(this.inventory.selected, pStack), pStack);
    //     } else if (pSlot == EquipmentSlot.OFFHAND) {
    //         this.onEquipItem(pSlot, this.inventory.offhand.set(0, pStack), pStack);
    //     } else if (pSlot.getType() == EquipmentSlot.Type.ARMOR) {
    //         this.onEquipItem(pSlot, this.inventory.armor.set(pSlot.getIndex(), pStack), pStack);
    //     }

    // }

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
    public HumanoidArm getMainArm() {
        return this.entityData.get(DATA_PLAYER_MAIN_HAND) == 0 ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }

    public boolean isStopMove() {
        return stopMove;
    }

    public void setStopMove(boolean s) {

        stopMove = s;
    }


//  @Override
//  public boolean alwaysAccepts() {
//      return super.alwaysAccepts();
//  }

    //  DeBug.tell("play2");
    // if (elevationAngle) animationEntity.setXRots(this.xRotO);
    //  animationEntity.setYRots(this.yRotO);
    // animationEntity.setTickCount(0);//注意  动画播放过程与tick关联
    //     if (elevationAngle) animationEntity.setXRots(this.xRotO);
    //      animationEntity.setYRots(this.yRotO);
    // animationEntity.updateTick=0;
    //       animationEntity.setTickCount(0);//注意  动画播放过程与tick关联
    // DeBug.tell(String.valueOf(this.level().getEntity(animationEntity.getId()) == null));

    //       DeBug.tell(String.valueOf(this.animationEntity));
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
