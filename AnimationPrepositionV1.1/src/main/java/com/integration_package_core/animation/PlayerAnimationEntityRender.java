package com.integration_package_core.animation;

import com.integration_package_core.mixinTool.PlayerExpand;
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
import net.minecraftforge.client.event.RenderLivingEvent;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PlayerAnimationEntityRender extends GeoEntityRenderer<PlayerAnimationEntity> {


    public PlayerAnimationEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PlayerAnimationEntityModel());
    }


    public PlayerAnimationEntityRender(EntityRendererProvider.Context renderManager, GeoModel<PlayerAnimationEntity> model) {
        super(renderManager, model);
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

            r.renderItem((AbstractClientPlayer) p, poseStack, HumanoidArm.RIGHT, model -> {

            }, null);
            poseStack.popPose();

            {
                poseStack.pushPose();
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
                poseStack.mulPoseMatrix(entity.offsetHand);

                poseStack.mulPose(Axis.XN.rotationDegrees(80));
                poseStack.translate(0, 0.15, 0);
                r.renderItem((AbstractClientPlayer) p, poseStack, HumanoidArm.LEFT, model -> {

                }, null);
                poseStack.popPose();
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
                renderHead(entity, poseStack, p, r);
                renderChest(entity, poseStack, p, r);
                renderLeg(entity, poseStack, p, r);
                renderFoot(entity, poseStack, p, r);
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

    public static void preventPlayerRender(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> e) {


        if (e.getEntity() instanceof PlayerExpand p1) {

            Player p= (Player) e.getEntity();

            PlayerAnimationEntity entity=p1.getAnimationEntity();
            //   System.out.println("112542");
            //   System.out.println(p.getAnimationEntity());
            if (entity != null&&entity.getSnow()) {
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
                                entity, x,  y,  z,0,
                                e.getPartialTick(),e.getPoseStack(),e.getMultiBufferSource(),
                                Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(p, Minecraft.getInstance().getPartialTick()));



                        e.setCanceled(true);


                    }


                }

            }

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