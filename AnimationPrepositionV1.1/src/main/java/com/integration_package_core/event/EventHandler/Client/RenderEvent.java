package com.integration_package_core.event.EventHandler.Client;

import com.integration_package_core.animation.AttTrace;
import com.integration_package_core.animation.PlayerAnimationEntity;
import com.integration_package_core.animation.PlayerAnimationEntityRender;
import com.integration_package_core.event.Event.KeyBindsEvent;
import com.integration_package_core.mixinTool.CameraMember;
import com.integration_package_core.mixinTool.HumanoidArmorLayerMember;
import com.integration_package_core.mixinTool.LivingEntityRenderMember;
import com.integration_package_core.IntegrationPackageCore;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.tool.Render;
import com.integration_package_core.tool.keyBinds;
import com.integration_package_core.util.Network.NetworkManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.*;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.event.GeoRenderEvent;

import java.lang.Math;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = IntegrationPackageCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderEvent {
    static EntityRenderDispatcher Dispatcher;

    @SubscribeEvent
    public static void h(InputEvent.Key e) {


        if (keyBinds.skill_1.getKey().getValue() == e.getKey()) {

            NetworkManager.send("rdgdr");


        }


    }


    //@OnlyIn(Dist.CLIENT)
    //@SubscribeEvent
    public static void o(KeyBindsEvent e) {


        //  System.out.println("==========================================================================");
//
        e.register("23424", keyBinds.skill_2);
        //  NetworkManager.NetworkEvent("666", e -> {

        //      //System.out.println(e.data);


        //  });
    }

    public static void q(GeoRenderEvent.Entity.Pre e) {

        if (e.getEntity() instanceof PlayerAnimationEntity p) {


        }


    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void j(ViewportEvent.ComputeCameraAngles e) {

        Player p = Minecraft.getInstance().player;

        if (e.getCamera() instanceof CameraMember c && p instanceof PlayerExpand p1 && p1.getAnimationEntity() != null) {


//Vec3 vec= Maths.eulerGetVectors(p.xRotO,p.yRotO);


            //c.$setPosition(new Vec3(p.getX()-10*vec.x, p.getY()+10*vec.y, p.getZ()-10*vec.z));


        }


    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void j(GeoRenderEvent.Entity.Post e) {


        if (e.getEntity() instanceof PlayerAnimationEntity p) {
            //     RenderSystem.setShaderColor(1,1,1,1);
            AttTrace.render(p.mainTraceList, e.getBufferSource(), e.getPoseStack(), p, (float) (p.getMTraceR1()), (float) (p.getMTraceR2()));//渲染轨迹
            AttTrace.render(p.offsetTraceList, e.getBufferSource(), e.getPoseStack(), p, (float) (p.getOTraceR1()), (float) (p.getOTraceR2()));//渲染轨迹

        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void j(GeoRenderEvent.Entity.Pre e) {


        if (e.getEntity() instanceof PlayerAnimationEntity p) {
            //RenderSystem.enableBlend();
            //   AttTrace.render(e.getBufferSource(), e.getPoseStack(), p, p.traceR1, p.traceR2);//渲染轨迹
            // RenderSystem.setShaderColor(1,1,1,0.1f);

        }
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void l(TickEvent.RenderTickEvent e) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // System.out.println(  e.renderTickTime);


    }

    ;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void p(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> e) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        //  RenderSystem.setShaderColor(5,3,4,1);
        //   if (Dispatcher == null) {
        //       Dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();//(EntityRenderDispatcher)
        //   }
        //  if(e.getEntity() instanceof PlayerExpand p){
        //      r.fill(poseStack,a,new float[]{255,255,255,255});
        PlayerAnimationEntityRender.preventPlayerRender(e);
//  Render r=Render.of();
//  ArrayList<Vector3d> b=new ArrayList<>() ;

//  b.add(new Vector3d(10,10,10));

//  b.add(new Vector3d(-10,-10,-10));
//Render.of(e.getPoseStack()).renderLaser(new float[] {0,0,0},new float[] {5,5,5},0.2f,0);

//  r.renderTraceLine(e.getPoseStack(),b, new float[]{1,1,1,1},2.6 ,new ResourceLocation(IntegrationPackageCore.MODID, "textures/animation_entity/trail.png"));

        //    //  System.out.println(  p.getAnimationEntity());
        //  }
        //   if(e.getEntity() instanceof )
        //   Render r=Render.of();
        //   r.poseStack().mulPoseMatrix(p..head);
        //   r.guiGraphics.fill(1, -1, -1, 1, 0);

        //  System.out.println(e.getEntity().getType());




        AbstractClientPlayer p = Minecraft.getInstance().player;

        // EntityRenderer<?> playerRenderer=Dispatcher != null
        if (e.getEntity() instanceof LocalPlayer c) {


            //  Render.of().renderArmor(p, e.getPoseStack(), EquipmentSlot.CHEST, model -> {
            //  // System.out.println(  model.leftArm.xRot);
            //  //         System.out.println(  model.leftArm.yRot);
            //  //         System.out.println(  model.leftArm.zRot);
            //  // model.leftArm.xRot= (float) Math.PI;
//
            //  });
            //  Render r = new Render(Minecraft.getInstance(), e.getPoseStack(), Minecraft.getInstance().renderBuffers().bufferSource());

            // // //  p.sendSystemMessage(Component.literal("playerRcnmcnmcnmenderer.toString()"));


            //  r.renderArmor(c, EquipmentSlot.CHEST, null);

        }

        //      p.sendSystemMessage(Component.literal("playerRenderer.toString()"));


        if (false) {//注意  玩家渲染和实体渲染为分开的
            PlayerRenderer playerRenderer = (PlayerRenderer) Dispatcher.getRenderer(Minecraft.getInstance().player);


            if (playerRenderer instanceof LivingEntityRenderMember c) {


                //   p.sendSystemMessage(Component.literal(c.getLayer().toString()));


                List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>> layers = c.getLayer();// (List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>>) ReflectionUtils.get("layers", playerRenderer);


                if (layers != null) {
                    //    p.sendSystemMessage(Component.literal(layers.toString()));

                    if (layers.get(0) instanceof HumanoidArmorLayerMember h) {


                        h.$renderArmorPiece(e.getPoseStack(),
                                e.getMultiBufferSource(),
                                Minecraft.getInstance().player,
                                EquipmentSlot.CHEST,
                                100, h.$getArmorModel(EquipmentSlot.CHEST));

                    }


                }
                //     p.sendSystemMessage(Component.literal(  getArmorModelMethod.invoke(a, EquipmentSlot.CHEST).toString()));
                //renderArmorPieceMethod.invoke(a,)


                //       try {

                //   } catch (Exception eee) {

                //       System.out.println("cnm");
                //       // 捕获反射过程中可能出现的异常，如方法不存在、调用出错等
                //       //e.printStackTrace();
                //   }
            }


        }


    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void p(RenderLevelStageEvent e) {

        PoseStack poseStack = e.getPoseStack();


        if (e.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {


            //      poseStack.pushPose();
            //      Render.levelPoseStack(poseStack, 0, 0, 0);
            //      e.getPoseStack().mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
//
            //      //  Render.of(poseStack).guiGraphics.blit( new ResourceLocation(IntegrationPackageCore.MODID, "textures/animation_entity/trail.png"), -9, -9, 0, 0, 512, 512, 512, 512);
//
            //      poseStack.popPose();


            poseStack.pushPose();

            Render.levelPoseStack(poseStack, 0, 0, 0);
            //    e.getPoseStack().mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            Player p2 = Minecraft.getInstance().player;

            List<Entity> a1 = new ArrayList<>(p2.level().getEntities(null, new AABB(
                    p2.getX() - 6,
                    p2.getY() - 6,
                    p2.getZ() - 6,
                    p2.getX() + 6,
                    p2.getY() + 6,
                    p2.getZ() + 6)));


            //    Render.of(poseStack).renderLaser(poseStack,new Vector3d(p2.getX(),
            //            p2.getY(),
            //            p2.getZ()), new Vector3d(0,0,0),0.5f,new float[]{1,1,1,1}, RenderTypes.getRenderType(Render.trailTexture));

            float[] poss1;
            float[] poss2;

            if (!a1.isEmpty()&&false) {
                poss1 = new float[]{(float) a1.get(0).getX(), (float) a1.get(0).getY(), (float) a1.get(0).getZ(),};

                poss2 = new float[]{(float) p2.getX(), (float) p2.getY(), (float) p2.getZ()};
                // Render.of(poseStack).renderLaserV(poss1, poss2, 0.2f);
                //  Render.of(poseStack).renderLaserH(poss1, poss2, 0.2f);
                Render r = Render.of(poseStack);

                r.setTexture(new ResourceLocation(IntegrationPackageCore.MODID, "textures/animation_entity/trail1.png"));
               r.setTextureUV(10, 300, 10, 200, 512, 512);
              //
                r.renderLaser(poss1, poss2, 0.1f, (float) (Math.PI / 2));

                r.renderLaser(poss1, poss2, 0.1f, 0);
               //  Render.of(poseStack).renderLaser(poss1, poss2, 0.2f,0);
            }

            if (!a1.isEmpty() && false) {


                Vector3d pos1 = new Vector3d(poss1[0] - poss2[0], poss1[1] - poss2[1], poss1[2] - poss2[2]);

                Vector3d pos2 = new Vector3d(0, 0, 0);

                Vector3d ePos1 = new Vector3d(-0.5, 0, 0);
                Vector3d ePos2 = new Vector3d(0.5, 0, 0);


                //   Vector3d ePos3 = new Vector3d(0.1, 0, 0);
//
                //   Vector3d ePos4 = new Vector3d(-0.1, 0, 0);
                float distance = (float) Math.pow((pos1.x - pos2.x) * (pos1.x - pos2.x) + (pos1.z - pos2.z) * (pos1.z - pos2.z) + (pos1.y - pos2.y) * (pos1.y - pos2.y), 0.5);
                float levelDistance = (float) Math.pow((pos1.x - pos2.x) * (pos1.x - pos2.x) + (pos1.z - pos2.z) * (pos1.z - pos2.z), 0.5);

                float horizontalAngle = (float) ((pos1.x - pos2.x) / levelDistance);

                //  System.out.println(horizontalAngle);

                if ((float) Math.min(pos2.z, pos1.z) < 0) {
                    horizontalAngle = (float) (Math.PI - (Math.asin(horizontalAngle)));
                } else {
                    horizontalAngle = (float) ((Math.asin(horizontalAngle)));
                }


                //  System.out.println(horizontalAngle / Math.PI + "pi");
                //  ePos1.rotate()
                //
                Quaternionf q = Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation();

                //  ePos1.rotateY(horizontalAngle);
                //  ePos2.rotateY(horizontalAngle);

                ePos1.rotate(new Quaterniond(q.x, q.y, q.z, q.w));

                ePos2.rotate(new Quaterniond(q.x, q.y, q.z, q.w));

                //    ePos2.rotate((Quaterniondc) Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());


                float x = (poss1[0] - poss2[0]) / distance;
                float z = (poss1[2] - poss2[2]) / distance;
                float y = (poss1[1] - poss2[1]) / distance;

                //  System.out.println(x+"  "+y+"  "+z);
                // System.out.println((poss1[1] - poss2[1]));
                // System.out.println(levelDistance);
                ePos1.rotateAxis(90, x, y, z);
                ePos2.rotateAxis(90, x, y, z);


                // System.out.println(ePos1.x+"    " +ePos1.z);
                //  System.out.println(horizontalAngle);

                //  ePos3.rotateZ(180*horizontalAngle/Math.PI);
                //  ePos4.rotateZ(180*horizontalAngle/Math.PI);


                Vector3d[] a = new Vector3d[]{
                        // new Vector3d(-entity.getX(),-entity.getY()-0.1,-entity.getZ()),
                        // new Vector3d(-entity.getX(),-entity.getY(),-entity.getZ()),
                        //   new Vector3d(0,0,0),
                        //  new Vector3d(0,0,+6),

                        new Vector3d(pos1.x + ePos1.x, pos1.y, pos1.z + ePos1.z),
                        new Vector3d(pos1.x + ePos2.x, pos1.y, pos1.z + ePos2.z),
                        new Vector3d(pos2.x + ePos2.x, pos2.y, pos2.z + ePos2.z),
                        new Vector3d(pos2.x + ePos1.x, pos2.y, pos2.z + ePos1.z),


                };


                poseStack.translate(poss2[0], poss2[1], poss2[2]);

                Render.of().fill(poseStack, a, new float[]{1, 1, 1, 1});


            }

            poseStack.popPose();
            // e.getPoseStack().translate(
            //         5,
            //         5,
            //         5);

            // if (Minecraft.getInstance().level != null) {
            //     if (Minecraft.getInstance().player != null) {


            //       //  poseStack.mulPose(Axis.ZN.rotationDegrees( Minecraft.getInstance().player.tickCount));
            //     }
            // }
            //   e.getPoseStack().translate(
            //         - 5,
            //         - 5,
            //         - 5);


            if (Minecraft.getInstance().player instanceof PlayerExpand p && false) {
                if (p.getAnimationEntity() != null && p.getAnimationEntity().mainHand != null) {

                    PlayerAnimationEntity p1 = p.getAnimationEntity();

                    e.getPoseStack().pushPose();

                    Vector4f vec = new Vector4f(0, 0, 0, 1);
                    vec.mul(p.getAnimationEntity().mainHand);

                    double x = Mth.lerp(Minecraft.getInstance().getFrameTime(), p1.xOld, p1.getX());
                    double y = Mth.lerp(Minecraft.getInstance().getFrameTime(), p1.yOld, p1.getY());
                    double z = Mth.lerp(Minecraft.getInstance().getFrameTime(), p1.zOld, p1.getZ());

                    Render.levelPoseStack(e.getPoseStack(), 0, 0, 0);
                    e.getPoseStack().translate(
                            x,
                            y,
                            z);

                    e.getPoseStack().mulPose(Axis.YN.rotationDegrees(p.getAnimationEntity().getXRots() - 180));

                    e.getPoseStack().translate(
                            vec.x(),
                            vec.y(),
                            vec.z());


                    e.getPoseStack().mulPose(Axis.YN.rotationDegrees(-p.getAnimationEntity().getYRots() + 180));
                    Render.bindCamera(e.getPoseStack());


                    e.getPoseStack().scale(0.2F, 0.2F, 0.2f);
                    Render.of(e.getPoseStack()).guiGraphics.fill(1, 1, -1, -1, 0, Color.ofRGBA(255, 255, 255, 255).argbInt());
                    e.getPoseStack().popPose();
                }
            }
        }

    }

}
//               renderers.forEach((a,b)->{


//           System.out.println(  a);
//   p.sendSystemMessage(Component.literal("34542342"));
//(List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>>) ReflectionUtils.get("layers", playerRenderer;
//   Render r = new Render(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());

// //  r.poseStack().mulPose(Axis.YN.rotation(180));

//        r.renderArmor(Minecraft.getInstance().player,e.getPoseStack(), EquipmentSlot.CHEST);
//          if (e.getEntity() instanceof Player p) {


//      }
//   System.out.println(layers);
//    ReflectionUtils.get(" renderArmorPiece", layers.get(0));

//       });

// playerRenderer.
// List<RenderLayer<Player, PlayerModel<Player>>> layers = (List<RenderLayer<Player, PlayerModel<Player>>>) modifyPrivate.get("layers",playerRenderer);

//    Field wayController = LivingEntityRenzss.getDeclaredFields();
//                                         z
//    wayController.setAccessible(true);   z
//                                         z
//return   wayController.get(target);  zt.println (
                                                   
                           /*
    //     double x        //  if (e.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) = Mth.lerp(Minecraft.getInstance().getFrameTime(), p1.xOld, p1.getX());
    //     double y         = Mth.lerp(Minecraft.getInstance().getFrameTime(), p1.yOld, p1.getY());
    //     double z        if (false&&  Minecraft.getInstance().player instanceof PlayerExpand p) { = Mth.lerp(Minecraft.getInstance().getFrameTime(), p1.zOld, p1.getZ());
                               if (p.getAnimationEntity() != null && p.getAnimationEntity().mainHand != null) {
    //     Render.levelPoseStack(e.getPoseStack(), 0, 0, 0);
    //  e.getPoseSt                PlayerAnimationEntity p1 = p.getAnimationEntity();ack().translate(
    //          x,
    //          y,                 e.getPoseStack().pushPose();
    //          z);
    //   Class<?> a                Vector4f vec = new Vector4f(0, 0, 0, 1);rmorLayerClass = HumanoidArmorLayer.class;
    // System.out.p                vec.mul(p.getAnimationEntity().mainHand);rintln( ));
    // System.out.println(   Dispatcher.renderers. get(EntityType.PLAYER));
    //  modifyPriva            //   e.getPoseStack().mulPose(Axis.YN.rotationDegrees(-p.getAnimationEntity().xRot));te.get("entityRenderDispatcher",Minecraft.getInstance());
    // 2. 获取私有方法 re            //   e.getPoseStack().mulPose(Axis.YN.rotationDegrees(p.getAnimationEntity().xRot - 180));nderArmorPiece，参数需与方法定义匹配
    //      Method                 e.getPoseStack().mulPose(Axis.YN.rotationDegrees( - 180));privateMethod = MyClass.class.getDeclaredMethod("privateMethod",);
    //  System.out.                e.getPoseStack().translate(println(Dispatcher);
    // System.out.p                        vec.x(),rintln("aaaaa");
                                           vec.y(),
    //    e.getRend                        vec.z());erer(EntityType.PLAYER);

    // Field newPose = GuiGraphics.class.getDeclaredField()
    // newPose.setA                e.getPoseStack().mulPose(Axis.YN.rotationDegrees(-p.getAnimationEntity().xRot + 180));ccessible(true);
    // newPose.set(this.guiGraphics, pose);

    //  e.getPoseSt                Render.bindCamera(e.getPoseStack());ack().translate(0.34, -0.7, 0);
    //  e.getPoseStack().mulPose((new Quaternionf()).rotationXYZ((float) (p.getAnimationEntity().xRot*Math.PI/180f),0,0));
    //   e.getPoseStack().translate(-0.34, -0.7, 0);
    //     e.getPos                e.getPoseStack().scale(0.2F, 0.2F, 0.2f);eStack().translate(
    //                             Render.of(e.getPoseStack()).guiGraphics.fill(1, 1, -1, -1, 0, Color.ofRGBA(255, 255, 255, 255).argbInt());p.getAnimationEntity().getX(),(float) (p.getAnimationEntity().getX()-vec.x())
    //                             e.getPoseStack().popPose();p.getAnimationEntity().getY(),(float) (p.getAnimationEntity().getY()-vec.y())
    //                         }p.getAnimationEntity().getZ());(float) (p.getAnimationEntity().getZ()-vec.z())
    // e.getPoseSta        }ck().translate(vec.x(), vec.y(), vec.z());
}                                  */
/*
                    if (false) {
                        Method renderArmorPieceMethod = HumanoidArmorLayer.class.getDeclaredMethod(
                                "renderArmorPiece",
                                PoseStack.class,
                                MultiBufferSource.class,
                                LivingEntity.class,
                                EquipmentSlot.class,
                                int.class,
                                HumanoidModel.class
                        );

                        Method getArmorModelMethod = HumanoidArmorLayer.class.getDeclaredMethod(
                                "getArmorModel",
                                EquipmentSlot.class
                        );

                        // 3. 设置私有方法可访问
                        renderArmorPieceMethod.setAccessible(true);
                        getArmorModelMethod.setAccessible(true);


                        HumanoidArmorLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>, ? extends HumanoidModel<AbstractClientPlayer>> a
                                = (HumanoidArmorLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>, ? extends HumanoidModel<AbstractClientPlayer>>) layers.get(0);//ReflectionUtils.get(" renderArmorPiece", layers.get(0));(HumanoidArmorLayer<Player, ? extends HumanoidModel<Player>, ? extends HumanoidModel<Player>>)
                        //    HumanoidArmorLayer<Player,>

                        //   System.out.println(a);
                        p.sendSystemMessage(Component.literal(a.toString()));
                        //System.out.println(ReflectionUtils.get(" renderArmorPiece", layers.get(0)));

                        //  System.out.println(getArmorModelMethod.invoke(a, EquipmentSlot.CHEST));
                        // 4. 调用方法，传入对应参数
                        renderArmorPieceMethod.invoke(
                                a,
                                e.getPoseStack(),
                                e.getMultiBufferSource(),
                                Minecraft.getInstance().player,
                                EquipmentSlot.CHEST,
                                100,
                                (HumanoidModel<AbstractClientPlayer>)
                                        getArmorModelMethod.invoke(a, EquipmentSlot.CHEST)
                                // a.getArmorModel(EquipmentSlot.CHEST)
                        );
                    }
*/