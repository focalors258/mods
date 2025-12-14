package com.integration_package_core.animation;

import com.integration_package_core.Config;
import com.integration_package_core.mixin.PlayerMixin;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.tool.DeBug;
import com.integration_package_core.tool.Maths;
import com.integration_package_core.util.Network.NetworkManager;
import com.integration_package_core.util.RegistryHandler.EntityRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PlayerAnimationEntity extends Entity implements GeoEntity {


    // protected static final RawAnimation FLY_ANIM = RawAnimation.begin().thenLoop("move.fly");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);


    // public float yRotO1;

    // public float xRotO1;

    public Vector2f transitRotate = new Vector2f();
    public Player player;
    public Double viewDistance = Config.MAX_COMBAT_VISUAL_ANGLE.get();
    public int oTime = -1;
    @Unique
    public Vec3 transitCamera = Vec3.ZERO;
    @Unique
    public Entity transitTarget;
    @Unique
    public float transitSpeed = 10;
    @Unique
    public float transitEnd = 0;

    @Unique
    public float angleTransitEnd = 0;


    public boolean angleLock = Config.ANGLE_LOCKING.get();
    public ResourceLocation animationResource;
    public HashMap<String, Integer> nextStage = new HashMap<String, Integer>();
    public Matrix4f head;
    public Matrix4f chest;
    //public Matrix4f leftHand;
    //  public Matrix4f rightHand;//arm
    public Matrix4f buttock;//双腿骨骼基点
    public int stage = 0;
    public ArrayList<Matrix4f> mainTraceList = new ArrayList<>();
    public ArrayList<Matrix4f> offsetTraceList = new ArrayList<>();
    public ArrayList<CustomizeTick> poseTickList;
    public Predicate<PlayerAnimationEntity> mainTick;
    public int timeEnd = 0;
    public ResourceLocation model;
    public ResourceLocation texture;
    public int stageCool = 0;//攻击冷却
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
    public float[] traceColor = new float[]{1, 1, 1, 1};//未同步
    public String newAnimation = "sit";
    public int updateTick = 0;
    public boolean restart = false;
    public int playType = 2;
    public int maxTrace = 64;
    public Predicate<PlayerAnimationEntity> newTick;
    public Entity target;
    public String typeName;
    public int time = 0;
    public boolean endLink = true;
    public ResourceLocation mainTraceResource;
    public ResourceLocation offsetTraceResource;
    public boolean syncOtherPlayer = false;//防止信息回传
    public boolean mainRender = true;
    public boolean offsetRender = true;
    public boolean headRender = true;
    public boolean chestRender = true;
    public boolean legRender = true;
    public boolean footRender = true;
    public HashMap<String, Integer> nextStageTime = new HashMap<>();
    private Storyboard storyboard;
    private boolean snow = false;
    private boolean mainTrace = false;
    private boolean offsetTrace = false;
    private int maxStage = 5;
    private float mTraceR1 = 0;
    private float mTraceR2 = 1;
    private float oTraceR1 = 0;
    private float oTraceR2 = 1;
    private float xRot = 0;
    private float yRot = 0;
    private String animation = "air";
    public PlayerAnimationEntity(EntityType<PlayerAnimationEntity> pEntityType, Level pLevel) {

        super(pEntityType, pLevel);

        // System.out.println(player);
    }

    public PlayerAnimationEntity(Level pLevel, LivingEntity player, int timeEnd) {






        super(EntityRegistry.PlayerAnimationEntity.get(), pLevel);

        this.player = (Player) player;

        //  setTimeEnd(time);
        //  System.out.println(player);
        //setAnimation(animation);

        //  syncAll();


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
    public void updateSmoothStart(boolean Ang) {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        if(Ang){
        transitRotate.x = camera.getXRot();
        transitRotate.y = camera.getYRot();
    }
        this.transitCamera = new Vec3(
                camera.getPosition().x,
                camera.getPosition().y,
                camera.getPosition().z);

    }
    public void updateSmoothStart() {
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        transitRotate.x = camera.getXRot();
        transitRotate.y = camera.getYRot();
        this.transitCamera = new Vec3(
                camera.getPosition().x,
                camera.getPosition().y,
                camera.getPosition().z);

    }

    public Storyboard getStoryboard() {

        return storyboard;
    }
//x前后  y上下 z左右(注意和原游戏坐标轴差了90度)
    public void addStoryboard(Storyboard storyboard) {

        //setYRots(player.yRotO);
        float ang = Maths.getHorizontal(new Vector3f(storyboard.startPos).rotateY((float) ((-player.yRotO-90)/180*Math.PI)));
    //   System.out.println(player.yRotO);
        storyboard.setYRot(ang);

        this.storyboard = storyboard;
        if (storyboard.smooth) smoothCamera();

    }

    public void setMainRender(boolean r) {

        mainRender = r;
        sendSyncData("PlayerAnimation_mainRender", tag -> tag.putBoolean("value", r));

    }

    public void setOffsetRender(boolean r) {

        offsetRender = r;

        sendSyncData("PlayerAnimation_offsetRender", tag -> tag.putBoolean("value", r));

    }

    public void setHeadRender(boolean r) {
        headRender = r;
        sendSyncData("PlayerAnimation_headRender", tag -> tag.putBoolean("value", r));

    }

    public void setChestRender(boolean r) {
        chestRender = r;
        sendSyncData("PlayerAnimation_chestRender", tag -> tag.putBoolean("value", r));

    }

    public void setLegRender(boolean r) {
        legRender = r;
        sendSyncData("PlayerAnimation_legRender", tag -> tag.putBoolean("value", r));

    }

    public void setFootRender(boolean r) {
        footRender = r;
        sendSyncData("PlayerAnimation_footRender", tag -> tag.putBoolean("value", r));

    }

    public boolean isEnd() {


        return this.tickCount >= timeEnd - 6;

    }

    public void recordTrace() {
        if (this.getServer() == null) {

            if (getSnow()) {
                if (mainTraceList.size() >= maxTrace && !mainTraceList.isEmpty()) {

                    mainTraceList.remove(0);
                }

                if (offsetTraceList.size() >= maxTrace && !offsetTraceList.isEmpty()) {

                    offsetTraceList.remove(0);
                }


                if (mainTrace) mainTraceList.add(mainHand);

                if (offsetTrace) offsetTraceList.add(offsetHand);


            }

        }

    }


    public void smoothCamera() {
        this.transitEnd = player.tickCount + this.transitSpeed;
    }


    public void tick() {


        if (storyboard != null) {


            if ((storyboard.isEnd())) {

                if (storyboard.smooth) smoothCamera();

                storyboard = null;
            }
        }


        //    DeBug.tell(String.valueOf(this));

        nextStageTime.forEach((k, v) -> {
            if (v < player.tickCount) nextStage.remove(k);
        });//定时阶段


        // snowTick();
        if (player != null) {

            //  this.stage--;
            //  if (this.stage < 0)this.stage=this.maxStage-1;

            if (poseTickList != null) {

                try {
                    poseTickList.forEach((t) -> {
                        t.tick.accept(this);
                        //    if (ct.time < tickCount&&ct.time>=0) poseTickList.remove(n);
                    });//逻辑判断

                } catch (Exception a) {
                    DeBug.error("AnimationTickError", a);
                }


            }
            try {
                if (mainTick != null) {
                    mainTick.test(this);
                }
                if (newTick != null) {
                    Object a = newTick.test(this);
                    if (a instanceof Boolean b && b) {
                        mainTick = newTick;
                        newTick = null;
                    }
                }
            } catch (Exception a) {
                DeBug.error("AnimationTickError", a);
            }


            //  this.stage++;
            //  if (this.stage >= this.maxStage) this.stage=0;

            if (this.getServer() == null) {

                if (!mainTrace) mainTraceList = new ArrayList<>();
                if (!offsetTrace) offsetTraceList = new ArrayList<>();

                xRotO = xRot;
                yRotO = yRot;

                if (isEnd()) {//修复旋转过大 if (endLink)
                    this.animation = "air";
                    yRot = player.yRotO;//客户端玩家倾斜角未同步
                }
                if (this.timeEnd < this.tickCount || (isEnd() && !endLink)) {//不衔接
                    setSnow(false);
                }
            }
            ;//自定义tick)
            if (this.level().getServer() != null) {

                if (player.tickCount % 40 == 0 || this.tickCount < 40) {//刚生成时初始化
                    //  setPlayer(player);//同步自身绑定玩家
                    // syncAll();
                    //   playerSync(this.player);//同步其他客户端玩家动画

                    if (player instanceof PlayerExpand p) {

                        //        if (p.getAnimationEntity() != this) this.remove(RemovalReason.DISCARDED);


                    }


                }
            }
        }
        // if (this.tickCount % 5 == 0) {      }
        this.tickCount++;

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
                            if (this.animation != null && this.tickCount < 10) {

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
                    data.putBoolean("SyncOtherPlayer", true);//排除发信的玩家
                    data.putInt("entity", this.getId());
                    data.putInt("player", this.player.getId());
                    //  data.putInt("updateTick", this.updateTick);
                    data.putInt("tickCount", this.tickCount);
                    data.putInt("time", this.timeEnd);
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

                    if (this.animation != null && this.tickCount < timeEnd - 6) {
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
        int tick = 0;
        if (endLink) {
            tick = 1;
        }
//只在启动时执行一次
        controllers.add(new AnimationController<>(this, "Controller", 1, this::addAnimation));


        //updatedAt
    }

    protected <E extends PlayerAnimationEntity> PlayState addAnimation(final AnimationState<E> event) {

        //    DeBug.tell(String.valueOf("animationTick   "+event.animationTick));


        if (isEnd() || animation == null || animation.equals("null") || animation.equals("air")) {//使用stop会导致动画永久停止

            event.setAndContinue(RawAnimation.begin().thenPlayAndHold("air"));
            return PlayState.STOP;
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
        if (player instanceof PlayerExpand p) {
            if (this.getServer() == null) {

                //   System.out.println("cilent   " + player + "    " + p.getAnimationEntity());
                System.out.println("cilent   " + tickCount + "    " + timeEnd + "    " + animation + "    " + snow);
            } else {

                //    System.out.println("server   " + player + "    " + p.getAnimationEntity());
                System.out.println("server   " + tickCount + "    " + timeEnd + "    " + animation + "    " + snow);
            }
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public AnimatableManager<PlayerAnimationEntity> getAnimatableManager() {

        return this.getAnimatableInstanceCache().getManagerForId(this.getId());
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {

    }


    //  public void setTypeName(String typeName,String nextType) {

    //      if (!Objects.equals(typeName, nextType))  nextStage=0;//更新动画时使阶段为0

    //
    //      setTypeName(typeName);
    //      //sendSyncData("PlayerAnimation_Name", tag -> tag.putString("name", typeName));

    //  }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {

    }

    public boolean isStageCool() {

        return this.stageCool < this.time - (this.getTimeEnd() - this.tickCount);
    }

    public void setStageCool(int value) {

        this.stageCool = value;


        sendSyncData("PlayerAnimation_StageCool", tag -> tag.putInt("stageCool", stageCool));


    }

    public void setTypeName(String typeName) {

        if (!Objects.equals(typeName, this.typeName)) {
            if (nextStage.containsKey(typeName)) {

                this.stage = nextStage.get(typeName);
            } else {
                this.stage = 0;
            }//更新动画时使阶段为0
            nextStage.remove(typeName);//设置下一阶段
        }


        this.typeName = typeName;
        sendSyncData("PlayerAnimation_Name", tag -> tag.putString("name", typeName));

    }

    public void setNextStage(String typeTarget, int nextStage, int time) {
        this.nextStage.put(typeTarget, nextStage);
        this.nextStageTime.put(typeTarget, time + player.tickCount);
        sendSyncData("PlayerAnimation_NextStageTime", tag -> {
            tag.putInt("time", time);
            tag.putString("typeTarget", typeTarget);
            tag.putInt("nextStage", nextStage);
        });//无效
    }


    public void setNextStage(String typeTarget, int nextStage) {
        this.nextStage.put(typeTarget, nextStage);
        sendSyncData("PlayerAnimation_NextStage", tag -> {
            tag.putString("typeTarget", typeTarget);
            tag.putInt("nextStage", nextStage);
        });//无效
    }


    public void setStage(int stage) {
        this.stage = stage;

        ;
        sendSyncData("PlayerAnimation_stage", tag -> tag.putInt("stage", stage));

    }

    public void setTraceColor(float[] color) {

        traceColor = color;


        sendSyncData("PlayerAnimation_TraceColor", tag -> {

            tag.putFloat("r", traceColor[0]);
            tag.putFloat("g", traceColor[1]);
            tag.putFloat("b", traceColor[2]);
            tag.putFloat("a", traceColor[3]);
        });

    }

    public void setTarget(Entity e) {
        this.target = e;

        if (e != null && !e.isRemoved()) {
            sendSyncData("PlayerAnimation_target", tag -> tag.putInt("target", target.getId()));
        }

    }

    public void setEndLink(boolean end) {

        endLink = end;
        //  System.out.println("ser    " + time);
        sendSyncData("PlayerAnimation_EndLink", tag -> tag.putBoolean("endLink", end));
    }

    public void setMainTraceResource(ResourceLocation resource) {

        mainTraceResource = resource;
        sendSyncData("PlayerAnimation_MainTraceResource", tag -> {
            tag.putString("Namespace", mainTraceResource.getNamespace());
            tag.putString("Path", mainTraceResource.getPath());
        });
    }

    public void setOffsetTraceResource(ResourceLocation resource) {

        offsetTraceResource = resource;
        sendSyncData("PlayerAnimation_OffsetTraceResource", tag -> {
            tag.putString("Namespace", offsetTraceResource.getNamespace());
            tag.putString("Path", offsetTraceResource.getPath());
        });

    }

    public void setAnimationResource(ResourceLocation resource) {

        animationResource = resource;
        sendSyncData("PlayerAnimation_AnimationResource", tag -> {
            tag.putString("Namespace", animationResource.getNamespace());
            tag.putString("Path", animationResource.getPath());
        });

    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;

        // System.out.println(this.texture);
        sendSyncData("PlayerAnimation_Texture", tag -> {
            tag.putString("Namespace", texture.getNamespace());
            tag.putString("Path", texture.getPath());
        });
    }

    public void setModel(ResourceLocation model) {
        this.model = model;


        sendSyncData("PlayerAnimation_Model", tag -> {
            tag.putString("Namespace", model.getNamespace());
            tag.putString("Path", model.getPath());
        });
    }

    // 获取restart属性
    public boolean getRestart() {
        return this.restart;
    }

    public void setRestart(boolean restart) {
        this.restart = restart;

        sendSyncData("PlayerAnimation_Restart", tag -> tag.putBoolean("restart", restart));
    }

    // 获取xRot属性
    public float getXRots() {
        return this.xRot;
    }

    // 同步属性：xRot
    public void setXRots(float xRot) {

        this.xRotO = this.xRot;
        this.xRot = xRot;


        sendSyncData("PlayerAnimation_XRot", tag -> tag.putFloat("xRot", xRot));
    }

    // 获取yRot属性
    public float getYRots() {
        return this.yRot;
    }

    // 同步属性：yRot
    public void setYRots(float yRot) {

        this.yRotO = this.yRot;
        this.yRot = yRot;

        ;
        sendSyncData("PlayerAnimation_YRot", tag -> tag.putFloat("yRot", yRot));
    }

    // 获取playType属性
    public int getPlayType() {
        return this.playType;
    }


    // 同步属性：time

    // 同步属性：playType
    public void setPlayType(int playType) {
        this.playType = playType;

        sendSyncData("PlayerAnimation_PlayType", tag -> tag.putInt("playType", playType));
    }


    public void setOTime(int oTime) {
        this.oTime = oTime;

        sendSyncData("PlayerAnimation_oTime", tag -> tag.putInt("oTime", oTime));
    }


    // 获取maxStage属性
    public int getMaxStage() {
        return this.maxStage;
    }

    // 同步属性：maxStage
    public void setMaxStage(int maxStage) {
        this.maxStage = maxStage;

        sendSyncData("PlayerAnimation_MaxStage", tag -> tag.putInt("maxStage", maxStage));
    }

    // 获取maxTrace属性
    public int getMaxTrace() {
        return this.maxTrace;
    }

    // 同步属性：maxTrace
    public void setMaxTrace(int maxTrace) {
        this.maxTrace = maxTrace;

        sendSyncData("PlayerAnimation_MaxTrace", tag -> tag.putInt("maxTrace", maxTrace));
    }

    // 获取mainTrace属性
    public boolean getMainTrace() {
        return this.mainTrace;
    }

    // 同步属性：mainTrace
    public void setMainTrace(boolean mainTrace) {
        this.mainTrace = mainTrace;

        sendSyncData("PlayerAnimation_MainTrace", tag -> tag.putBoolean("mainTrace", mainTrace));
    }

    // 获取offsetTrace属性
    public boolean getOffsetTrace() {
        return this.offsetTrace;
    }

    // 同步属性：offsetTrace
    public void setOffsetTrace(boolean offsetTrace) {
        this.offsetTrace = offsetTrace;

        sendSyncData("PlayerAnimation_OffsetTrace", tag -> tag.putBoolean("offsetTrace", offsetTrace));
    }

    public boolean getSnow() {
        return this.snow;
    }

    public void setSnow(boolean snow) {
        this.snow = snow;
        //   this.oTime = -1;
        ;
        sendSyncData("PlayerAnimation_snow", tag -> tag.putBoolean("snow", snow));

    }

    // 获取mTraceR1属性
    public float getMTraceR1() {
        return this.mTraceR1;
    }

    // 同步属性：mTraceR1
    public void setMTraceR1(float mTraceR1) {
        this.mTraceR1 = mTraceR1;

        sendSyncData("PlayerAnimation_MTraceR1", tag -> tag.putFloat("mTraceR1", mTraceR1));
    }

    // 获取mTraceR2属性
    public float getMTraceR2() {
        return this.mTraceR2;
    }

    // 同步属性：mTraceR2
    public void setMTraceR2(float mTraceR2) {
        this.mTraceR2 = mTraceR2;

        sendSyncData("PlayerAnimation_MTraceR2", tag -> tag.putFloat("mTraceR2", mTraceR2));
    }

    // 获取oTraceR1属性
    public float getOTraceR1() {
        return this.oTraceR1;
    }

    // 同步属性：oTraceR1
    public void setOTraceR1(float oTraceR1) {
        this.oTraceR1 = oTraceR1;

        sendSyncData("PlayerAnimation_OTraceR1", tag -> tag.putFloat("oTraceR1", oTraceR1));
    }

    // 获取oTraceR2属性
    public float getOTraceR2() {
        return this.oTraceR2;
    }

    // 同步属性：oTraceR2
    public void setOTraceR2(float oTraceR2) {
        this.oTraceR2 = oTraceR2;

        sendSyncData("PlayerAnimation_OTraceR2", tag -> tag.putFloat("oTraceR2", oTraceR2));
    }

    // 获取animation属性
    public String getAnimation() {
        return this.animation;
    }

    // 同步属性：animation（覆盖原setAnimation方法）
    public void setAnimation(String name) {//注意 在一列内修改多次当前动画会导致效果出错
//注意 需在每个动画文件里添加air(默认动作)  不然无法重置动画
        //注意 bb更新后会修改导入的动画文件导致错误
        //  if (!Objects.equals(this.animation, name)) {
        //      this.oTime = -1;
        //  }


        this.animation = name;
        // System.out.println(animation+"---a");
        //snowTick();
        //System.out.println(isEnd());
        if (name != null) {
            sendSyncData("AnimationSync", tag -> tag.putString("animation", name));
            //  System.out.println("animation");
        } else {
            sendSyncData("AnimationSync", tag -> tag.putString("animation", "air"));
        }

    }

    // 获取tickCount属性（使用父类的tickCount，添加getter）
    public int getTickCount() {
        return this.tickCount;
    }

    // 同步属性：tickCount（特殊处理，用于同步计时器）
    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;


        ;

        sendSyncData("PlayerAnimation_TickCount", tag -> tag.putInt("tickCount", tickCount));
    }

    public int getTime() {

        return this.time - (this.timeEnd - this.tickCount);
    }

    public void setTime(int time) {

        this.time = time;
        this.timeEnd = time + tickCount;
        //  System.out.println("setTime" + this.timeEnd);
        sendSyncData("PlayerAnimation_Time", tag -> tag.putInt("time", time));
    }

    public void setTime(int time, int timeend) {

        this.time = time;
        this.timeEnd = timeend;
        //  System.out.println("setTime" + this.timeEnd);
        sendSyncData("PlayerAnimation_Time", tag -> tag.putInt("time", time));
    }

    // 获取time属性
    public int getTimeEnd() {
        return this.timeEnd;
    }

    // 获取关联的玩家实体
    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {

        this.player = player;

        sendSyncData("PlayerAnimation_Player", tag -> tag.putInt("player", player.getId()));

    }

    // 获取左手矩阵

    // 获取右手矩阵

    // 获取主手矩阵（示例：补充其他矩阵属性的getter）
    public Matrix4f getMainHand() {
        return this.mainHand;
    }

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

    public void setSyncOtherPlayer() {
        this.syncOtherPlayer = true;
    }

    // 通用网络发送工具方法（服务器端向所有玩家发送同步数据）
    private void sendSyncData(String eventName, Consumer<CompoundTag> dataSetter) {
        // 仅在服务器端发送同步数据

        if (this.player != null) {
            CompoundTag data = new CompoundTag();

            if (this.getServer() == null) {


                data.putInt("entityId", player.getId()); // 携带实体ID，用于客户端定位实体
                data.putBoolean("SyncOtherPlayer", true);//排除发信的玩家
                dataSetter.accept(data); // 设置具体属性值
                NetworkManager.send(eventName, data);

            } else {

                data.putInt("entityId", player.getId()); // 携带实体ID，用于客户端定位实体
                dataSetter.accept(data); // 设置具体属性值

                if (syncOtherPlayer) {

                    //System.out.println("-------------------2-------------------");
                    this.level().players().forEach(player -> {

                        //  System.out.println("464"+player);
                        if (player != this.player) {
                            //    if(Objects.equals(eventName, "PlayerAnimation_Name")){


                            //    }

                            NetworkManager.send(eventName, data, (ServerPlayer) player);
                        }

                    });
                    //  System.out.println(data.getString("animation") + "----b");
                    syncOtherPlayer = false;
                } else {
                    //System.out.println("-------------------1-------------------");
                    NetworkManager.sendAll(eventName, data);
                    //    System.out.println(data.getString("animation")+"----b");
                }


            }

        }

    }
}
//     snowTick();
//     if (this.tickCount >= time) {     }

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
//  xRotO = xRot;
//  yRotO = yRot;
//  float[] hv = Maths.getHV(yRot, player.yRotO, xRot, 0);
//  xRot = player.xRotO;
//             e.setDataFreely("234", 234);
//             System.out.println(e1);
//         }
//     });
//    if (player instanceof PlayerExpand p) {
//        //   p.setAnimationEntity(null);
//    }
//   PlayerAnimationEntity.playerSync(this.player);//同步客户端玩家动画
//   if (Objects.equals(animation, "air")) {
//  syncTick();
//       this.setSnow(false);
//   }

// NetworkManager.send("AnimationSync", data, (ServerPlayer) e);
//       this.level().players().forEach(e -> {      });
//           CompoundTag data = new CompoundTag();
//           data.putInt("entity", this.getId()); // 携带实体ID，用于客户端定位实体

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
