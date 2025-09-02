package com.integration_package_core.animation;

import com.integration_package_core.mixinTool.EntityExpand;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.tool.Maths;
import com.integration_package_core.util.Network.NetworkManager;
import com.integration_package_core.util.RegistryHandler.EntityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.function.Consumer;

public class PlayerAnimationEntity extends Entity implements GeoEntity {


    // protected static final RawAnimation FLY_ANIM = RawAnimation.begin().thenLoop("move.fly");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public Player player;

    private boolean snow = true;

    public Matrix4f head;
    public Matrix4f chest;
    //public Matrix4f leftHand;
    //  public Matrix4f rightHand;//arm
    public Matrix4f buttock;//双腿骨骼基点
    public int stage = 0;
    public ArrayList<Matrix4f> mainTraceList = new ArrayList<>();
    public ArrayList<Matrix4f> offsetTraceList = new ArrayList<>();

    public Consumer<PlayerAnimationEntity> customizeTick;
    private boolean mainTrace = false;

    private boolean offsetTrace = false;
    private int time = 500;

    private int maxStage = 5;
    public int stageCool = 80;//攻击冷却
    public Cube leftArm;
    public Cube rightArm;//arm
    public Matrix4f leftLeg;
    public Matrix4f rightLeg;//arm
    public Matrix4f leftFoot;
    public Matrix4f rightFoot;//arm

    public Matrix4f mainHand;

    //public Matrix4f mainHand1;
    public Vector3d mainItemScale;

    public Vector3d offsetItemScale;
    public Matrix4f offsetHand;//arm
    public float xRotO;

    public float yRotO;
    public float[] traceColor = new float[]{1, 1, 1, 5f};//未同步


    private float mTraceR1 = 0;

    private float mTraceR2 = 1;


    private float oTraceR1 = 0;

    private float oTraceR2 = 1;


    private float xRot = 0;
    private float yRot = 0;


    private String animation = "sit";
    public String newAnimation = "sit";
    public int updateTick = 0;
    public boolean restart = false;
    public int playType = 2;


    public PlayerAnimationEntity(EntityType<PlayerAnimationEntity> pEntityType, Level pLevel) {

        super(pEntityType, pLevel);

        // System.out.println(player);
    }


    public PlayerAnimationEntity(Level pLevel, LivingEntity player, int time) {


        super(EntityRegistry.PlayerAnimationEntity.get(), pLevel);

        this.player = (Player) player;

        setTime(time);
        //  System.out.println(player);
        //setAnimation(animation);

        syncAll();


    }


    public static void playerSync(LivingEntity p) {

        //   System.out.println("34634534535");
        //    if (instanceof Player p)
        if (p == null) return;
        //Player p = l.player;

        p.level().players().forEach(e -> {

            if (e instanceof Player && p.getServer() != null) {

                CompoundTag data = new CompoundTag();

                data.putInt("player", p.getId());


                if (p instanceof PlayerExpand p1) {

                    int id = 0;

                    if (p1.getAnimationEntity() != null) {

                        id = p1.getAnimationEntity().getId();

                    }

                    // System.out.println(id);

                    data.putInt("entity", id);


                    NetworkManager.send("PlayerAnimationSync", data, (ServerPlayer) e);

                }

            }

        });

    }


    public boolean isEnd() {


        return this.tickCount >= time - 6;

    }

    public void recordTrace() {
        if (this.getServer() == null) {


            if (mainTraceList.size() >= maxTrace) {

                mainTraceList.remove(0);
            }

            if (offsetTraceList.size() >= maxTrace) {

                offsetTraceList.remove(0);
            }


            if (mainTrace) mainTraceList.add(mainHand);

            if (offsetTrace) offsetTraceList.add(offsetHand);


        }

    }

    public int maxTrace = 64;


    public void tick() {


        //     snowTick();
        if (player != null) {

            Vec3 pos = player.position();

            this.setPos(pos);//防止实体坐标超出视野而被剔除

            if (customizeTick != null) {

                customizeTick.accept(this);

            }

            if (this.getServer() == null) {




           //     this.level().getEntities(null, new AABB(
           //             this.getX() - 48,
           //             this.getY() - 48,
           //             this.getZ() - 48,
           //             this.getX() + 48,
           //             this.getY() + 48,
           //             this.getZ() + 48)).forEach(e1 -> {
//
           //         if (e1 instanceof EntityExpand e) {
//
           //             e.setDataFreely("234", 234);
           //             System.out.println(e1);
           //         }
           //     });

                if (!mainTrace) mainTraceList = new ArrayList<>();

                if (!offsetTrace) offsetTraceList = new ArrayList<>();


                xRotO = xRot;
                yRotO = yRot;

                if (isEnd()) {//修复旋转过大
                    //  xRotO = xRot;
                    //  yRotO = yRot;
                    //  float[] hv = Maths.getHV(yRot, player.yRotO, xRot, 0);
                    //  xRot = player.xRotO;
                    yRot = player.yRotO;//客户端玩家倾斜角未同步

                }

            }
            ;//自定义tick)

            if (this.level().getServer() != null) {


                if (player.tickCount % 20 == 0 || this.tickCount < 5) {//刚生成时初始化

                    syncAll();

                } else {

                    syncTick();

                }

                PlayerAnimationEntity.playerSync(this.player);//同步其他客户端玩家动画

                if (this.tickCount >= time) {

                    this.setAnimation("air");

                    this.setSnow(false);

                    if (player instanceof PlayerExpand p) {

                        //   p.setAnimationEntity(null);

                    }

                    PlayerAnimationEntity.playerSync(this.player);//同步客户端玩家动画

                }
            }
        }
        // if (this.tickCount % 5 == 0) {      }

    }

    public void syncTick() {

        if (player != null) {

            this.level().players().forEach(e -> {

                        if (e instanceof Player && this.getServer() != null) {
                            CompoundTag data = new CompoundTag();
                        // data.putInt("player", this.player.getId());
                        // data.putInt("entity", this.getId());
                        // data.putInt("tickCount", this.tickCount);
                        // data.putInt("time", this.time);
                            if (this.animation != null &&this.tickCount<10) {

                                data.putString("animation", this.animation);
                             //   data.putString("animation", "null");
                            }

                            NetworkManager.send("PlayerAnimationTick", data, (ServerPlayer) e);
                        }

                    }

            );


        }

    }


    public void syncAll() {


        if (player != null) {

            this.level().players().forEach(e -> {

                if (e instanceof Player && this.getServer() != null) {

                    CompoundTag data = new CompoundTag();
                    data.putInt("entity", this.getId());
                    data.putInt("player", this.player.getId());
                    //  data.putInt("updateTick", this.updateTick);
                    data.putInt("tickCount", this.tickCount);
                    data.putInt("time", this.time);
                    //data.putInt("tick", this.tickCount);
                    //     data.putInt("time", this.time);
                    data.putFloat("xRot", this.xRot);
                    data.putFloat("yRot", this.yRot);
                    data.putInt("playType", this.playType);
                    data.putInt("maxStage", this.maxStage);
                    data.putInt("maxTrace", this.maxTrace);
                    data.putBoolean("mainTrace", this.mainTrace);
                    data.putBoolean("offsetTrace", this.offsetTrace);
                    data.putFloat("mTraceR1", this.mTraceR1);
                    data.putFloat("mTraceR2", this.mTraceR2);
                    data.putFloat("oTraceR2", this.oTraceR2);
                    data.putFloat("oTraceR1", this.oTraceR1);
                    data.putBoolean("restart", this.restart);

                    if (this.animation != null && this.tickCount < time - 6) {
                        data.putString("animation", this.animation);
                    } else {

                        data.putString("animation", "null");

                    }

                    //    data.putFloat("xRot", this.yRot);


                    NetworkManager.send("PlayerAnimationEntity", data, (ServerPlayer) e);

                }

            });


        }

    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Controller", 1, this::addAnimation));
    }

    protected <E extends PlayerAnimationEntity> PlayState addAnimation(final AnimationState<E> event) {

        if (isEnd() || animation == null || animation.equals("null")) {//使用stop会导致动画永久停止

            event.setAndContinue(RawAnimation.begin().thenPlayAndHold("air"));

        } else {

            if (playType == 0) {

                event.setAndContinue(RawAnimation.begin().thenLoop(animation));
            } else if (playType == 1) {

                event.setAndContinue(RawAnimation.begin().thenPlay(animation));
            } else if (playType == 2) {

                event.setAndContinue(RawAnimation.begin().thenPlayAndHold(animation));
            }
        }
        return PlayState.CONTINUE;


    }

    public void snowTick() {

        if (this.getServer() == null) {

            System.out.println("cilent   " + tickCount + "    " + time);

        } else {

            System.out.println("server   " + tickCount + "    " + time);

        }

    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {

    }


    public void setRestart(boolean restart) {
        this.restart = restart;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_Restart", tag -> tag.putBoolean("restart", restart));
    }

    // 同步属性：xRot
    public void setXRots(float xRot) {
        this.xRot = xRot;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_XRot", tag -> tag.putFloat("xRot", xRot));
    }


    public void setSnow(boolean snow) {
        this.snow = snow;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_snow", tag -> tag.putBoolean("snow", snow));

    }

    // 同步属性：yRot
    public void setYRots(float yRot) {
        this.yRot = yRot;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_YRot", tag -> tag.putFloat("yRot", yRot));
    }

    // 同步属性：playType
    public void setPlayType(int playType) {
        this.playType = playType;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_PlayType", tag -> tag.putInt("playType", playType));
    }

    // 同步属性：maxStage
    public void setMaxStage(int maxStage) {
        this.maxStage = maxStage;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_MaxStage", tag -> tag.putInt("maxStage", maxStage));
    }

    // 同步属性：maxTrace
    public void setMaxTrace(int maxTrace) {
        this.maxTrace = maxTrace;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_MaxTrace", tag -> tag.putInt("maxTrace", maxTrace));
    }

    // 同步属性：mainTrace
    public void setMainTrace(boolean mainTrace) {
        this.mainTrace = mainTrace;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_MainTrace", tag -> tag.putBoolean("mainTrace", mainTrace));
    }

    // 同步属性：offsetTrace
    public void setOffsetTrace(boolean offsetTrace) {
        this.offsetTrace = offsetTrace;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_OffsetTrace", tag -> tag.putBoolean("offsetTrace", offsetTrace));
    }

    // 同步属性：mTraceR1
    public void setMTraceR1(float mTraceR1) {
        this.mTraceR1 = mTraceR1;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_MTraceR1", tag -> tag.putFloat("mTraceR1", mTraceR1));
    }

    // 同步属性：mTraceR2
    public void setMTraceR2(float mTraceR2) {
        this.mTraceR2 = mTraceR2;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_MTraceR2", tag -> tag.putFloat("mTraceR2", mTraceR2));
    }

    // 同步属性：oTraceR1
    public void setOTraceR1(float oTraceR1) {
        this.oTraceR1 = oTraceR1;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_OTraceR1", tag -> tag.putFloat("oTraceR1", oTraceR1));
    }

    // 同步属性：oTraceR2
    public void setOTraceR2(float oTraceR2) {
        this.oTraceR2 = oTraceR2;
        if (this.level().getServer() == null) return;
        sendSyncData("PlayerAnimation_OTraceR2", tag -> tag.putFloat("oTraceR2", oTraceR2));
    }

    // 同步属性：animation（覆盖原setAnimation方法）
    public void setAnimation(String name) {//注意 在一列内修改多次当前动画会导致效果出错


        this.animation = name;
        if (this.level().getServer() == null) return;

        this.level().players().forEach(e -> {
            CompoundTag data = new CompoundTag();

            data.putInt("entity", this.getId()); // 携带实体ID，用于客户端定位实体

            if (name != null && !isEnd()) {
                data.putString("animation", name);
                //  System.out.println("animation");
            } else {
                data.putString("animation", "null");

            }
            NetworkManager.send("AnimationSync", data, (ServerPlayer) e);

        });
    }


    // 同步属性：tickCount（特殊处理，用于同步计时器）
    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;

        if (this.level().getServer() == null) return;

        sendSyncData("PlayerAnimation_TickCount", tag -> tag.putInt("tickCount", tickCount));
    }

    // 同步属性：time
    public void setTime(int time) {
        this.time = time;
        if (this.level().getServer() == null) return;

        //  System.out.println("ser    " + time);

        sendSyncData("PlayerAnimation_Time", tag -> tag.putInt("time", time));
    }


    // 获取restart属性
    public boolean getRestart() {
        return this.restart;
    }

    // 获取xRot属性
    public float getXRots() {
        return this.xRot;
    }

    // 获取yRot属性
    public float getYRots() {
        return this.yRot;
    }

    // 获取playType属性
    public int getPlayType() {
        return this.playType;
    }

    // 获取maxStage属性
    public int getMaxStage() {
        return this.maxStage;
    }

    // 获取maxTrace属性
    public int getMaxTrace() {
        return this.maxTrace;
    }

    // 获取mainTrace属性
    public boolean getMainTrace() {
        return this.mainTrace;
    }

    // 获取offsetTrace属性
    public boolean getOffsetTrace() {
        return this.offsetTrace;
    }

    public boolean getSnow() {
        return this.snow;
    }

    // 获取mTraceR1属性
    public float getMTraceR1() {
        return this.mTraceR1;
    }

    // 获取mTraceR2属性
    public float getMTraceR2() {
        return this.mTraceR2;
    }

    // 获取oTraceR1属性
    public float getOTraceR1() {
        return this.oTraceR1;
    }

    // 获取oTraceR2属性
    public float getOTraceR2() {
        return this.oTraceR2;
    }

    // 获取animation属性
    public String getAnimation() {
        return this.animation;
    }

    // 获取tickCount属性（使用父类的tickCount，添加getter）
    public int getTickCount() {
        return this.tickCount;
    }

    // 获取time属性
    public int getTime() {
        return this.time;
    }

    // 获取关联的玩家实体
    public Player getPlayer() {
        return this.player;
    }

    // 获取主手矩阵（示例：补充其他矩阵属性的getter）
    public Matrix4f getMainHand() {
        return this.mainHand;
    }

    // 获取左手矩阵

    // 获取右手矩阵

    // 获取头部矩阵
    public Matrix4f getHead() {
        return this.head;
    }

    // 获取胸部矩阵
    public Matrix4f getChest() {
        return this.chest;
    }

    // 获取臀部（双腿基点）矩阵
    public Matrix4f getButtock() {
        return this.buttock;
    }

    // 获取左腿矩阵
    public Matrix4f getLeftLeg() {
        return this.leftLeg;
    }

    // 获取右腿矩阵
    public Matrix4f getRightLeg() {
        return this.rightLeg;
    }

    // 获取左脚矩阵
    public Matrix4f getLeftFoot() {
        return this.leftFoot;
    }

    // 获取右脚矩阵
    public Matrix4f getRightFoot() {
        return this.rightFoot;
    }

    // 获取主手轨迹列表
    public ArrayList<Matrix4f> getMainTraceList() {
        return this.mainTraceList;
    }

    // 获取副手轨迹列表
    public ArrayList<Matrix4f> getOffsetTraceList() {
        return this.offsetTraceList;
    }


    // 通用网络发送工具方法（服务器端向所有玩家发送同步数据）
    private void sendSyncData(String eventName, Consumer<CompoundTag> dataSetter) {
        // 仅在服务器端发送同步数据
        if (this.getServer() == null || this.player == null) return;

        this.level().players().forEach(player -> {
            if (player instanceof ServerPlayer serverPlayer) {
                CompoundTag data = new CompoundTag();
                data.putInt("entityId", this.getId()); // 携带实体ID，用于客户端定位实体
                dataSetter.accept(data); // 设置具体属性值
                NetworkManager.send(eventName, data, serverPlayer);
            }
        });
    }


}



            /*
                if (Math.abs(xRot - player.xRotO) >=1) {
                    xRotO = xRot;
                    xRot = (float) (xRot - 0.2 * Math.max(0, xRot - player.xRotO));
                }else{
                    xRot = player.xRotO;
                }

                if (Math.abs(yRot - player.yRotO) >=1) {
                    yRotO = yRot;
                    yRot = (float) (yRot - 0.2 * Math.max(0, yRot - player.yRotO));
                }else{
                    yRot = player.yRotO;
                }
*/
