package com.integration_package_core.event.EventHandler;

import com.integration_package_core.animation.AttTrace;
import com.integration_package_core.animation.PlayerAnimationEntity;
import com.integration_package_core.mixinTool.HumanoidArmorLayerMember;
import com.integration_package_core.mixinTool.LivingEntityRenderMember;
import com.integration_package_core.IntegrationPackageCore;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.tool.Render;
import com.integration_package_core.util.Network.NetworkManager;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Quaternionf;
import org.joml.Vector4f;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.event.GeoRenderEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = IntegrationPackageCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderEvent {

    public static void o() {


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
    public static void j(GeoRenderEvent.Entity.Post e) {


        if (e.getEntity() instanceof PlayerAnimationEntity p) {

            AttTrace.render(e.getBufferSource(), e.getPoseStack(), p, p.traceR1, p.traceR2);//渲染轨迹
            //AttTrace.render(e.getPoseStack(),0,p);
            //
            //    AttTrace.render(e.getPoseStack(),1,p);
            //
        }


    }

    static EntityRenderDispatcher Dispatcher;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void l(TickEvent.RenderTickEvent e) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
       // System.out.println(  e.renderTickTime);





    };
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void p(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> e) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {


        //   if (Dispatcher == null) {
        //       Dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();//(EntityRenderDispatcher)
        //   }
        //  if(e.getEntity() instanceof PlayerExpand p){

        //    //  System.out.println(  p.getAnimationEntity());
        //  }
        //   if(e.getEntity() instanceof )
        //   Render r=Render.of();
        //   r.poseStack().mulPoseMatrix(p..head);
        //   r.guiGraphics.fill(1, -1, -1, 1, 0);

        //  System.out.println(e.getEntity().getType());


        if (e.getEntity() instanceof PlayerExpand p) {
            //   System.out.println("112542");
            //   System.out.println(p.getAnimationEntity());
            if (p.getAnimationEntity() != null) {
                if (Minecraft.getInstance().player != null) {
                    if (!Minecraft.getInstance().player.getPersistentData().contains("ModelLoad")) {

                        Minecraft.getInstance().player.getPersistentData().putBoolean("ModelLoad", true);//加载盔甲模型


                    } else {

                        e.setCanceled(true);


                    }


                }

            }

        }


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


        if (false && e.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES) {
            if (Minecraft.getInstance().player instanceof PlayerExpand p) {
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

                    e.getPoseStack().mulPose(Axis.YN.rotationDegrees(p.getAnimationEntity().xRot - 180));

                    e.getPoseStack().translate(
                            vec.x(),
                            vec.y(),
                            vec.z());


                    e.getPoseStack().mulPose(Axis.YN.rotationDegrees(-p.getAnimationEntity().xRot + 180));
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