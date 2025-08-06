package com.integration_package_core.animation;

import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.util.Network.NetworkManager;
import com.integration_package_core.util.RegistryHandler.EntityRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;

public class PlayerAnimationEntity extends Entity implements GeoEntity {




   // protected static final RawAnimation FLY_ANIM = RawAnimation.begin().thenLoop("move.fly");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public Player player;
    public int time = 100;

    public Matrix4f head;
    public Matrix4f chest;
    public Matrix4f leftHand;
    public Matrix4f rightHand;//arm
    public Matrix4f buttock;//双腿骨骼基点
    public int stage = 0;
    public ArrayList<Matrix4f> traceList=new ArrayList<>();

    public int maxStage = 5;
    public int stageCool = 80;//攻击冷却
    public Cube leftArm;
    public Cube rightArm;//arm
    public Matrix4f leftLeg;
    public Matrix4f rightLeg;//arm
    public Matrix4f leftFoot;
    public Matrix4f rightFoot;//arm

    public Matrix4f mainHand;

    //public Matrix4f mainHand1;

    public float[] traceColor=new float[]{1,1,1,1};


    public Matrix4f offsetHang;//arm


public float traceR1=0;

    public float traceR2=1;

    public float xRot = 0;
    public float yRot = 0;
    public String animation = "sit";
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

        this.time = time;
        //  System.out.println(player);

        sync();


    }

    public static void client() {
        NetworkManager.NetworkEvent("PlayerAnimationSync", e -> {


            // System.out.println("46456757545435548870784563243");

            //System.out.println("<-");
            if (e.level.getEntity(e.data.getInt("player")) instanceof PlayerExpand p) {


                p.setAnimationEntity((PlayerAnimationEntity) e.level.getEntity(e.data.getInt("entity")));

                // System.out.println(e.level.getEntity(e.data.getInt("entity")));
                //  System.out.println(p.getAnimationEntity());

            }

        });

        NetworkManager.NetworkEvent("PlayerAnimationEntity", e -> {

            //System.out.println("<-");
            if (e.level.getEntity(e.data.getInt("entity")) instanceof PlayerAnimationEntity p) {

                p.player = (Player) e.level.getEntity(e.data.getInt("player"));

                p.time = e.data.getInt("time");//updateTick

                p.tickCount = e.data.getInt("tickCount");//updateTick

                // p.updateTick = e.data.getInt("updateTick");//updateTick

                p.restart = e.data.getBoolean("restart");

                p.animation = e.data.getString("animation");

                p.xRot = e.data.getFloat("xRot");

                p.yRot = e.data.getFloat("yRot");
                //p.tickCount = e.data.getInt("tick");

                // System.out.println(e.data.getString("animation"));
            }


        });


    }

    public static void playerSync(LivingEntity p) {

        //   System.out.println("34634534535");
        //    if (instanceof Player p)
        if (p == null) return;
        //Player p = l.player;

        p.level().getEntities(null, new AABB(
                p.getX() - 48,
                p.getY() - 48,
                p.getZ() - 48,
                p.getX() + 48,
                p.getY() + 48,
                p.getZ() + 48)).forEach(e -> {

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

    public void recordTrace(){
        if (this.getServer()==null){
            if(traceList.size()>=maxTrace){

                traceList.remove(0);

            }

        traceList.add(mainHand);

    }

    }

public int maxTrace=80;




    public void tick() {


        sync();


     //   recordTrace();
        //   if (this.tickCount >= time - 6) {

        //       this.animation = null;
        //   }

        if (this.getServer() != null) {

            System.out.println(animation);
            System.out.println(stage );
            System.out.println(stageCool);
            //    if (tickCount==updateTick){
//
            //        animation=newAnimation;
//
            //    }

            PlayerAnimationEntity.playerSync(this.player);//同步客户端玩家动画

            if (this.tickCount >= time) {

                this.remove(RemovalReason.DISCARDED);

                if (player instanceof PlayerExpand p) {

                    p.setAnimationEntity(null);

                }

                PlayerAnimationEntity.playerSync(this.player);//同步客户端玩家动画

            }
        }


        // if (this.tickCount % 5 == 0) {      }


    }

    public void sync() {

        //    System.out.println("->");

        if (player != null) {

            this.level().getEntities(null, new AABB(
                    this.getX() - 48,
                    this.getY() - 48,
                    this.getZ() - 48,
                    this.getX() + 48,
                    this.getY() + 48,
                    this.getZ() + 48)).forEach(e -> {

                if (e instanceof Player && this.getServer() != null) {

                    CompoundTag data = new CompoundTag();

                    data.putInt("player", this.player.getId());

                    //  data.putInt("updateTick", this.updateTick);
//
                    data.putInt("tickCount", this.tickCount);

                    data.putInt("time", this.time);

                    data.putInt("entity", this.getId());

                    data.putBoolean("restart", this.restart);
                    //data.putInt("tick", this.tickCount);
                    //     data.putInt("time", this.time);


                    data.putFloat("xRot", this.xRot);

                    if (this.animation != null && this.tickCount < time - 6) {
                        data.putString("animation", this.animation);
                    } else {

                        data.putString("animation", "null");

                    }

                    data.putFloat("xRot", this.yRot);


                    NetworkManager.send("PlayerAnimationEntity", data, (ServerPlayer) e);

                }

            });


            this.setPos(player.position());
        }

    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Controller", 5, this::addAnimation));
    }

    protected <E extends PlayerAnimationEntity> PlayState addAnimation(final AnimationState<E> event) {

        //event.getController().hasAnimationFinished

        // System.out.println(event.getController(). getCurrentRawAnimation());

        //  if (event.isMoving()){    }
        /// event.getController().setAnimation("");
        //   if(event.getController()instanceof AnimationControllerMember a){
        //       a.setCurrentRawAnimation(null);
        //       //a.$stopTriggeredAnimation();
        //   }
        //   event.setAndContinue(RawAnimation.begin().thenLoop(null));
        //System.out.println(restart);

        //  System.out.println(tickCount);

        //  System.out.println(updateTick);
        //  if (restart) {
        //      restart = false;
        //
        //      return PlayState.CONTINUE;
        //  }

        //  event.setAndContinue(RawAnimation.begin().


        if (isEnd()) {

            return PlayState.STOP;

        }

        if (animation != null && !animation.equals("null")) {
            if (playType == 0) {
                return event.setAndContinue(RawAnimation.begin().thenLoop(animation));
            } else if (playType == 1) {
                return event.setAndContinue(RawAnimation.begin().thenPlay(animation));
            } else if (playType == 2) {
                return event.setAndContinue(RawAnimation.begin().thenPlayAndHold(animation));
            }

        }

        return PlayState.CONTINUE;


    }

    public void snowTick() {

        if (this.getServer() == null) {

            System.out.println("cilent   " + tickCount);

        } else {

            System.out.println("server   " + tickCount);

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
}
