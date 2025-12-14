package com.integration_package_core.animation;

import com.integration_package_core.Config;
import com.integration_package_core.mixinTool.CameraMember;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.tool.Maths;
import com.integration_package_core.tool.Render;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ViewportEvent;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Arrays;

public class PlayerAnimationEntityRender extends GeoEntityRenderer<PlayerAnimationEntity> {


    public PlayerAnimationEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PlayerAnimationEntityModel());
    }


    public PlayerAnimationEntityRender(EntityRendererProvider.Context renderManager, GeoModel<PlayerAnimationEntity> model) {
        super(renderManager, model);
    }


    public static void battleShot(ViewportEvent.ComputeCameraAngles e) {
        Player p1 = Minecraft.getInstance().player;
        if (p1 instanceof PlayerExpand p &&
                p.getAnimationEntity() != null &&
                e.getCamera() instanceof CameraMember c && p.getAnimationEntity().getStoryboard() == null) {
            PlayerAnimationEntity a = p.getAnimationEntity();
            if (e.getCamera().isDetached()) {
                float d = 99999;//l.distanceToSqr(p1);
                if (a.target != null) d = (float) a.target.distanceToSqr(p1);
                //     System.out.println(p.getAnimationEntity().viewDistance);
                if (a.target instanceof LivingEntity l && !l.isRemoved() && (float) d <
                        (p.getAnimationEntity().viewDistance)) {

                    // System.out.println(l.distanceToSqr(p1));
                    // System.out.println(p.getAnimationEntity().viewDistance + 999999);
                    if (a.transitTarget != a.target) {
                        a.smoothCamera();
                        a.transitTarget = a.target;
                    }
                    //   float d = (float) l.distanceToSqr(p1);
                    if (true) {
                        float x = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), p1.xOld, p1.getX());// (float) (( + l.getX()) / 2);
                        float y = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), p1.yOld, p1.getY());//(float) (( + l.getY()) / 2);
                        float z = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), p1.zOld, p1.getZ());//(float) ((+ l.getZ()) / 2);
                        float lx = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), l.xOld, l.getX());// (float) ((p1.xOld + l.xOld) / 2);
                        float ly = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), l.yOld, l.getY());/// (float) ((p1.yOld + l.yOld) / 2);
                        float lz = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), l.zOld, l.getZ());// (float) ((p1.zOld + l.zOld) / 2);
                        double b = Config.COMBAT_VISUAL_ANGLE.get();

                        Vec3 end = new Vec3(
                                ((x + lx) / 2) + ((x - lx) * b / 2),
                                ((y + ly) / 2) + ((y - ly) * b / 2),
                                ((z + lz) / 2) + ((z - lz) * b / 2)
                        );
                        float delta = Math.min(1 - ((a.transitEnd - Mth.lerp(Minecraft.getInstance().getPartialTick(), p1.tickCount - 1, p1.tickCount)) / a.transitSpeed), 1);
                        //   System.out.println(e.getCamera().getYRot());
                        //     e.setYaw(180);
                        //   c.$setYRot(0);
                        // System.out.println(e.getCamera().getYRot());
                        //   e.setPitch(0);Maths.getHorizontal(x-lx,z-lz)
//e.setRoll(0);
                        float delta1 = Math.min(1 - ((a.angleTransitEnd - Mth.lerp(Minecraft.getInstance().getPartialTick(), p1.tickCount - 1, p1.tickCount)) / (a.transitSpeed )), 1);//独立时间轴

                        float ang = Maths.getHorizontal(x - lx, z - lz);
                   //     System.out.println(d >= 0.5);


                        if (d - (ly - y) * (ly - y) >= 1 && a.angleLock && (Math.abs(Math.abs(ang) - Math.abs(p1.getYRot())) < 90 || a.angleTransitEnd > p1.tickCount)) {//防止角度变换过大
                            //System.out.println(e.getYaw());
                            float yrot = Mth.lerp(delta * delta1, a.transitRotate.y, ang) % 360;
                            //System.out.println(delta1+"    "+yrot);
                            // System.out.println(a.transitRotate.y);
                            p1.setYRot(yrot);
                            e.setYaw(yrot);//
                            c.$setYRot(yrot);//设置3个旋转轴  (第三人称)偏移后的视角坐标不跟随设置事件前的旋转值
                            c.$setPosition(new Vec3(x, y, z));
                            c.$move(-3, 0, 0);

                        } else {
                         //   System.out.println(Math.abs(Math.abs(ang) - Math.abs(p1.getYRot())));
                            a.updateSmoothStart();
                            a.angleTransitEnd = p1.tickCount + a.transitSpeed ;
                            // a.transitRotate.y=e.getCamera().getYRot(); if (a.angleTransitEnd < p1.tickCount)
                        }

                        c.$setPosition(
                                a.transitCamera.lerp(end, delta)//战斗镜头(进入时)
                        );
                        c.$move(-(6.0D + Math.pow(d, 0.4)) * delta, 0.0D, 0.0D);//旋转后偏移

                        //  c.$setPosition(new Vec3(x,y+1,z));
                        if (delta >= 1 && delta1 >= 1) {
                            a.updateSmoothStart();
                        }else if(delta >= 1 && !a.angleLock ){
                            a.updateSmoothStart();

                        }
                    }
                } else {

                    if (a.transitTarget != null) {
                        a.transitEnd = p1.tickCount + a.transitSpeed;
                        a.transitTarget = null;
                    }
                    float delta = Math.min(1 - ((a.transitEnd - Mth.lerp(Minecraft.getInstance().getPartialTick(), p1.tickCount - 1, p1.tickCount)) / a.transitSpeed), 1);
                    System.out.println(delta);

                    if (delta < 1) {//动态镜头
                        c.$setPosition(
                                a.transitCamera.lerp(e.getCamera().getPosition(), delta)
                        );
                    } else {
                        a.updateSmoothStart();
                    }
                }
                //System.out.println(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition());
            } else {

                //System.out.println(e.getCamera().isDetached());
                a.updateSmoothStart();
            }
        }

    }


    public static void renderTrace(RenderLevelStageEvent e) {//废弃

//废弃
        if (e.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {

            e.getPoseStack().pushPose();

            if (Minecraft.getInstance().level != null) {
                Minecraft.getInstance().level.players().forEach(p1 -> {

                    if (p1 instanceof PlayerExpand p2) {
                        PlayerAnimationEntity p = p2.getAnimationEntity();
                        if (p != null && p.getSnow()) {
                            Render.levelPoseStack(e.getPoseStack(), p1);

                            e.getPoseStack().mulPose(Axis.YN.rotationDegrees(Mth.lerp(Minecraft.getInstance().getPartialTick(), p.yRotO, p.getYRots())));

                            e.getPoseStack().mulPose(Axis.XN.rotationDegrees(Mth.lerp(Minecraft.getInstance().getPartialTick(), p.xRotO, p.getXRots())));
                            RenderSystem.enableBlend();

                            //  System.out.println(Arrays.toString(p.traceColor));
                            RenderSystem.setShaderColor(
                                    p.traceColor[0],
                                    p.traceColor[1],
                                    p.traceColor[2],
                                    p.traceColor[3]);
                            //     RenderSystem.setShaderColor(1,1,1,1);
                            //  Render r = Render.of(e.getPoseStack());
                            //  r.renderLaser(new float[]{-1, -2, -3}, new float[]{1, 2, 3}, 5);
                            //       r.guiGraphics.blit(r.texture,0,0,0,0,20,20);
                            //       AttTrace.render(p.mainTraceList, Minecraft.getInstance().renderBuffers().bufferSource(), e.getPoseStack(), p, (float) (p.getMTraceR1()), (float) (p.getMTraceR2()), p.mainTraceResource);//渲染轨迹
                            //    AttTrace.render(p.offsetTraceList, Minecraft.getInstance().renderBuffers().bufferSource(), e.getPoseStack(), p, (float) (p.getOTraceR1()), (float) (p.getOTraceR2()), p.offsetTraceResource);//渲染轨迹
                            RenderSystem.setShaderColor(1, 1, 1, 1);

                        }
                    }


                });
            }
            e.getPoseStack().popPose();

        }


    }


    public static void renderHead(PlayerAnimationEntity entity, PoseStack poseStack, Player p, Render r) {

        if (entity.head != null) {

            poseStack.pushPose();

            poseStack.mulPose(Axis.YN.rotationDegrees(180));
            poseStack.mulPoseMatrix(entity.head);
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));

            r.renderArmor((AbstractClientPlayer) p, poseStack, EquipmentSlot.HEAD, model -> {
                model.head.xRot = 0;
                model.head.yRot = 0;
                model.head.zRot = 0;
            });

            poseStack.popPose();
        }

    }

    public static void renderChest(PlayerAnimationEntity entity, PoseStack poseStack, Player p, Render r) {

        if (entity.chest != null) {
            // Render r = Render.of();
            poseStack.pushPose();
            //
            poseStack.mulPose(Axis.YN.rotationDegrees(180));
            poseStack.mulPoseMatrix(entity.chest);
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));

            poseStack.translate(0, -0.35, 0);
            //  PlayerModel<AbstractClientPlayer> a=new PlayerModel();

            r.renderArmor((AbstractClientPlayer) p, poseStack, EquipmentSlot.CHEST, model -> {
                model.body.xRot = 0;
                model.body.yRot = 0;
                model.body.zRot = 0;
                //  System.out.println(model.leftArm.x);
                // model.leftArm.yRot  =   - entity.leftArm.yRot;//copy(model.leftArm);
                // model.leftArm.xRot  =   - entity.leftArm.xRot;
                // model.leftArm.zRot  =    entity.leftArm.zRot;
                entity.rightArm.copy(model.rightArm);
                entity.leftArm.copy(model.leftArm);
                //    System.out.println(model.leftArm.x);
            }, model -> {

            });

            poseStack.popPose();
        }

    }

    public static void renderLeg(PlayerAnimationEntity entity, PoseStack poseStack, Player p, Render r) {

        if (entity.buttock != null) {
            // Render r = Render.of();
            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees(180));
            poseStack.mulPoseMatrix(entity.buttock);
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));
            poseStack.translate(0, -0.75, 0);
            r.renderArmor((AbstractClientPlayer) p, poseStack, EquipmentSlot.LEGS, model -> {

                        model.leftLeg.xScale = 0F;
                        model.rightLeg.xScale = 0F;
                        model.leftLeg.yScale = 0F;
                        model.rightLeg.yScale = 0F;
                        //  model.leftLeg.xScale = 0.5F;
                    },
                    model -> {

                        model.leftLeg.yScale = 1F;
                        model.rightLeg.yScale = 1F;
                        model.leftLeg.xScale = 1F;
                        model.rightLeg.xScale = 1F;


                        //model.leftLeg.xScale = 1F;
                    }


            );
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees(180));
            poseStack.mulPoseMatrix(entity.leftLeg);
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));
            poseStack.translate(-0.1, -0.75, 0);
            r.renderArmor((AbstractClientPlayer) p, poseStack, EquipmentSlot.LEGS, model -> {


                        model.body.xScale = 0;
                        model.body.yScale = 0;

                        model.leftLeg.yScale = 0.5F;
                        model.rightLeg.yScale = 0F;
                        //  model.leftLeg.xScale = 0.5F;
                    },
                    model -> {
                        model.body.xScale = 1;
                        model.body.yScale = 1;
                        model.leftLeg.yScale = 1F;
                        model.rightLeg.yScale = 1F;
                        //model.leftLeg.xScale = 1F;
                    }
            );
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees(180));
            poseStack.mulPoseMatrix(entity.rightLeg);
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));
            poseStack.translate(0.1, -0.75, 0);
            r.renderArmor((AbstractClientPlayer) p, poseStack, EquipmentSlot.LEGS, model -> {

                        model.body.xScale = 0;
                        model.body.yScale = 0;
                        model.leftLeg.yScale = 0F;
                        model.rightLeg.yScale = 0.5F;
                        //  model.leftLeg.xScale = 0.5F;
                    },
                    model -> {
                        model.body.xScale = 1;
                        model.body.yScale = 1;
                        model.leftLeg.yScale = 1F;
                        model.rightLeg.yScale = 1F;
                        //model.leftLeg.xScale = 1F;
                    }
            );
            poseStack.popPose();
        }

    }


    public static void renderFoot(PlayerAnimationEntity entity, PoseStack poseStack, Player p, Render r) {

        if (entity.buttock != null) {
            // Render r = Render.of();
            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees(180));
            poseStack.mulPoseMatrix(entity.rightFoot);
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));
            poseStack.translate(0.1, -1.15, -0);
            r.renderArmor((AbstractClientPlayer) p, poseStack, EquipmentSlot.FEET, model -> {
                model.rightLeg.xRot = 0;
                model.rightLeg.yRot = 0;
                model.rightLeg.zRot = 0;

                model.leftLeg.xScale = 0;
                model.leftLeg.yScale = 0;
                model.leftLeg.zScale = 0;
            }, model1 -> {
                model1.leftLeg.xScale = 1;
                model1.leftLeg.yScale = 1;
                model1.leftLeg.zScale = 1;
            });
            poseStack.popPose();


            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees(180));
            poseStack.mulPoseMatrix(entity.leftFoot);
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));
            poseStack.translate(-0.1, -1.15, -0);
            r.renderArmor((AbstractClientPlayer) p, poseStack, EquipmentSlot.FEET, model -> {
                model.leftLeg.xRot = 0;
                model.leftLeg.yRot = 0;
                model.leftLeg.zRot = 0;

                model.rightLeg.xScale = 0;
                model.rightLeg.yScale = 0;
                model.rightLeg.zScale = 0;
            }, model1 -> {
                model1.rightLeg.xScale = 1;
                model1.rightLeg.yScale = 1;
                model1.rightLeg.zScale = 1;
            });
            poseStack.popPose();


        }

    }

    public static void renderHand(PlayerAnimationEntity entity, PoseStack poseStack, Player p, Render r) {


        if (entity.mainHand != null) {

            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees(180));
            poseStack.mulPoseMatrix(entity.mainHand);
            // poseStack.mulPose(Axis.ZN.rotationDegrees(180));
            poseStack.mulPose(Axis.XN.rotationDegrees(80));
            poseStack.translate(0, 0.15, 0);

            if (entity.mainRender) r.renderItem((AbstractClientPlayer) p, poseStack, HumanoidArm.RIGHT, model -> {

            }, null);
            poseStack.popPose();

            {
                poseStack.pushPose();
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
                poseStack.mulPoseMatrix(entity.offsetHand);

                poseStack.mulPose(Axis.XN.rotationDegrees(80));
                poseStack.translate(0, 0.15, 0);
                if (entity.offsetRender) r.renderItem((AbstractClientPlayer) p, poseStack, HumanoidArm.LEFT, model -> {

                }, null);
                poseStack.popPose();
            }

        }

    }

    public static void preventPlayerRender(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> e) {


        if (e.getEntity() instanceof PlayerExpand p1) {

            Player p = (Player) e.getEntity();

            PlayerAnimationEntity entity = p1.getAnimationEntity();
            //   System.out.println("112542");
            //   System.out.println(p.getAnimationEntity());
            if (entity != null) {
                if (Minecraft.getInstance().player != null) {
                    if (!Minecraft.getInstance().player.getPersistentData().contains("ModelLoad")) {

                        Minecraft.getInstance().player.getPersistentData().putBoolean("ModelLoad", true);//加载盔甲模型


                    } else {

                        double x = 0;//Mth.lerp(e.getPartialTick(), entity.xOld, entity.getX());
                        double y = 0;//Mth.lerp(e.getPartialTick(), entity.yOld, entity.getY());
                        double z = 0;//Mth.lerp(e.getPartialTick(), entity.zOld, entity.getZ());
                        //    double nx = Mth.lerp(e.getPartialTick(), p.xOld, p.getX());
                        //    double ny = Mth.lerp(e.getPartialTick(), p.yOld, p.getY());
                        //    double nz = Mth.lerp(e.getPartialTick(), p.zOld, p.getZ());

                        Minecraft.getInstance().getEntityRenderDispatcher().render(
                                entity, x, y, z, 0,
                                e.getPartialTick(), e.getPoseStack(), e.getMultiBufferSource(),
                                Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(p, Minecraft.getInstance().getPartialTick()));

                        if (entity.getSnow()) {//如果取消渲染动画实体  则其内部tick也会停止运作
                            e.setCanceled(true);
                        }

                    }
                }
            }

        }


    }

    @Override
    public void render(PlayerAnimationEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        //     System.out.println(entity.player);
//
        //  System.out.println(entity.time);
//
        //   if (entity.hasTrail()) {  }
        Player p = entity.player;


        if (p != null) {//将碰撞箱设置为0使正常无法渲染


            if (Minecraft.getInstance().getCameraEntity() != null && entity.target != null) {
                //  Minecraft.getInstance().setCameraEntity(target);

                if (Minecraft.getInstance().gameRenderer.getMainCamera() instanceof CameraMember c) {

                    // Minecraft.getInstance().cameraEntity.po
                    // System.out.println(entity.target);

                    //  System.out.println(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition());
                    //  c.$setPosition(new Vec3(0,5,0));
                    //  System.out.println(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition());

                }


                //.setPosition()=new Vec3(0 , 0, 0);


            }


            poseStack.pushPose();

            //poseStack.translate(nx - x, ny - y, nz - z);

            poseStack.scale(0.925f, 0.925f, 0.925f);

            poseStack.mulPose(Axis.YN.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRots())));

            poseStack.mulPose(Axis.XN.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRots())));

            //     System.out.println(entity.xRot+"   "+entity.yRot);
            //  System.out.println(entity.getSnow());
            if (!entity.getSnow()) {
                poseStack.scale(0, 0, 0);//隐藏
            } else {
//注意 和骨骼同名的方块的枢轴点需在同一位置


                Render r = Render.of(poseStack);
                if (entity.headRender) renderHead(entity, poseStack, p, r);
                if (entity.chestRender) renderChest(entity, poseStack, p, r);
                if (entity.legRender) renderLeg(entity, poseStack, p, r);
                if (entity.footRender) renderFoot(entity, poseStack, p, r);
                renderHand(entity, poseStack, p, r);
                entity.recordTrace();//加载轨迹


            }

            //  System.out.println(entity.player);

            //
            //

            //   a.add(new Vector3d(1,1,1));

            //   a.add(new Vector3d(-1,-1,-1));

            //   System.out.println("3453442");


//r.renderTraceLine(poseStack,b, new float[]{255,255,255,255},2.6 ,new ResourceLocation(IntegrationPackageCore.MODID, "textures/animation_entity/trail.png"));


            //  RenderSystem.clearColor(0,0,0,0);
            poseStack.pushPose();

            RenderSystem.enableBlend();

            if (Minecraft.getInstance().cameraEntity != null) {//透明度

                if (Minecraft.getInstance().options.getCameraType().isFirstPerson() && entity.player == Minecraft.getInstance().player) {

                    poseStack.scale(0, 0, 0);
                    //RenderSystem.setShaderColor(1F, 1F, 1F, 0);

                } else {


                    RenderSystem.setShaderColor(1F, 1F, 1F,
                            (float) Math.min(1,
                                    Math.pow(Minecraft.getInstance().cameraEntity.distanceToSqr(
                                            Minecraft.getInstance().gameRenderer.getMainCamera().getPosition()), 0.5)));


                    //  System.out.println( );
                    //   p.sendSystemMessage(Component.literal(String.valueOf(Math.pow(Minecraft.getInstance().cameraEntity.distanceToSqr(
                    //           Minecraft.getInstance().gameRenderer.getMainCamera().getPosition()), 0.5))));
                }


            }


            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
            RenderSystem.setShaderColor(1, 1, 1, 1f);
            poseStack.popPose();
            // RenderSystem.clearColor(1,1,1,1);
            poseStack.popPose();


        }
    }

}
/*
            if (entity.head != null) {
                Render r = Render.of();
                poseStack.pushPose();
              //       //poseStack.mulPose(Axis.XN.rotationDegrees(entity.yRot));
              //            //    System.out.println(entity.head);
              //
              ////  renderTrail(entity, partialTick, poseStack, bufferSource, 1F, packedLight);
              //            //    r.poseStack().pushPose();
              //
              //            //  Vec3 position = r.minecraft.gameRenderer.getMainCamera().getPosition();
              //            //  poseStack.translate(position.x, position.y, position.z);
              //
              //            //  //    poseStack.translate(-position.x, -position.y, -position.z);
              //            //  r.poseStack().translate(p.xOld,
              //            //          p.yOld,
              //            //          p.zOld);
              //
              //            //  r.poseStack().mulPose(    r.minecraft.getEntityRenderDispatcher().cameraOrientation());
              //
              //            //  r.poseStack().mulPoseMatrix(entity.head);
              //
              //
              //            //  r.poseStack().popPose();
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
                poseStack.mulPoseMatrix(entity.head);
                poseStack.mulPose(Axis.ZN.rotationDegrees(180));
  // poseStack.translate(0, -0, -0.8);
            //   entity.mainHand1=poseStack;

            //  poseStack.pushPose();
            //  Render.bindCamera(poseStack);
            //  poseStack.scale(0.2F, 0.2F, 0.2f);
            //  Render.of(poseStack).guiGraphics.fill(1, 1, -1, -1, 0, Color.ofRGBA(255, 255, 255, 255).argbInt());
            //  poseStack.popPose();


            //  r.renderPlayerItem((AbstractClientPlayer) p, poseStack, HumanoidArm.RIGHT);




              //  PlayerModel<AbstractClientPlayer> a=new PlayerModel();
  //entity.rightLeg.copy(model.rightLeg);
                //    System.out.println(model.leftArm.x);
                r.renderArmor((AbstractClientPlayer) p, poseStack, EquipmentSlot.HEAD, model -> {
                    model.head.xRot=0;
                    model.head.yRot=0;
                    model.head.zRot=0;
                });
                r.renderArmor((AbstractClientPlayer) p, poseStack, EquipmentSlot.CHEST, model -> {
                    model.head.xRot=0;
                    model.head.yRot=0;
                    model.head.zRot=0;
                });

                r.renderArmor((AbstractClientPlayer) p, poseStack, EquipmentSlot.LEGS, model -> {
                    model.head.xRot=0;
                    model.head.yRot=0;
                    model.head.zRot=0;
                });
                r.renderArmor((AbstractClientPlayer) p, poseStack, EquipmentSlot.FEET, model -> {
                    model.head.xRot=0;
                    model.head.yRot=0;
                    model.head.zRot=0;
                });

                poseStack.popPose();

  //model.body.xRot=0;
                //model.body.yRot=0;
                //model.body.zRot=0;
                //  System.out.println(model.leftArm.x);
                // model.leftArm.yRot  =   - entity.leftArm.yRot;//copy(model.leftArm);
                // model.leftArm.xRot  =   - entity.leftArm.xRot;
                // model.leftArm.zRot  =    entity.leftArm.zRot;
               // model.leftLeg.yRot= (float) Math.PI;
             //  entity.leftFoot.copy(model.leftLeg);

              // model.leftLeg.y=-8;
              // model.rightLeg.y=-8;
            }
                //       r1.poseStack().pushPose();
    //       r1.poseStack().mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
    //     //  entity.mainHand.translate()
    //     // Vector4f vec = new Vector4f(0, 0, 0, 1);
    //     // vec.mul(matrix4f);
    //     // return new Vec3(vec.x(), vec.y()+1.5, vec.z());
    //       r1.guiGraphics.fill(10, 10, -10, -10, Color.ofRGBA(255, 255, 255, 255).argbInt());
    //       r1.poseStack().popPose();

        //    Vector4f vec = new Vector4f(0, 0, 0, 1);
        //    vec.mul( );vec.x;
       //  Minecraft.getInstance().getItemRenderer().renderStatic(
         //          entity.player.getMainHandItem(),
         //          ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
         //          100, 0,
         //          poseStack, Minecraft.getInstance().renderBuffers().bufferSource(), entity.level(), 0);

            //   float scaleX =poseStack.last().pose().get(0, 0);  // X 轴（宽度）拉伸比例
            //   float scaleY = poseStack.last().pose().get(1, 1);  // Y 轴（高度）拉伸比例
            //   float scaleZ = poseStack.last().pose().get(2, 2);
// （转换代码同上）

            //    Vector3f realScale = new Vector3f();
            //    entity.mainHand.deco(new Vector3f(), new Quaternionf(), realScale);

            //      System.out.println(scaleX+"  "+scaleY+"  "+scaleZ);
//          r.renderPlayerItem((AbstractClientPlayer) p, poseStack, HumanoidArm.RIGHT, model -> {

//              Cube.clear(model.rightArm);//注意物品也会跟随手臂倾斜
//              Cube.clear(model.leftArm);//注意物品也会跟随手臂倾斜
//              Cube.clear(model.leftSleeve);//注意物品也会跟随手臂倾斜
//              Cube.clear(model.rightSleeve);//注意物品也会跟随手臂倾斜
//entity.mainItemScale.
//              model.rightArm.xScale = (float) entity.mainItemScale.x;//厚
//              model.rightArm.yScale = (float) entity.mainItemScale.z;//宽
//              model.rightArm.zScale = (float) entity.mainItemScale.y;//长

//              //   model.

//          }, null);//注意物品也会跟随手臂倾斜

       //   Vector4f localPos = new Vector4f(poseStack.last().pose().m30(), poseStack.last().pose().m30(), poseStack.last().pose().m30(), 1.0f);
       //   localPos.mul(poseStack.last().pose());  // 应用矩阵变换
       //    entity.pos[0]= localPos.x();  // 变换后的X坐标
       //    entity.pos[1]= localPos.y();  // 变换后的Y坐标
       //    entity.pos[2]= localPos.z();
         // poseStack.last().pose().m30();
         // poseStack.last().pose().m31();
         // poseStack.last().pose().m32();


          //  System.out.println(Arrays.toString(entity.pos));
         //  Render.bindCamera(poseStack);
         //  poseStack.scale(0.1F, 0.1F, 0.1f);
         //  r .guiGraphics.fill(1, 1, -1, -1, 0, Color.ofRGBA(255, 255, 255, 255).argbInt());


            */