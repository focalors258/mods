package com.integration_package_core.animation;

import com.integration_package_core.tool.Render;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.joml.Vector4f;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.awt.*;
import java.util.Arrays;

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
                Cube.clear(model.rightArm);//注意物品也会跟随手臂倾斜
                Cube.clear(model.leftArm);//注意物品也会跟随手臂倾斜
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

            //poseStack.pushPose();

        //    poseStack.mulPoseMatrix(entity.mainHand);
        //    poseStack.translate(0.5, 0, 0);
//
//
        //    entity.mainHand1=poseStack.last().pose();
        //    poseStack.popPose();



            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees(180));
            poseStack.mulPoseMatrix(entity.mainHand);



            poseStack.mulPose(Axis.ZN.rotationDegrees(180));
            poseStack.mulPose(Axis.XN.rotationDegrees(10));




            poseStack.translate(0.34, -0.7, -0);
            r.renderPlayerItem((AbstractClientPlayer) p, poseStack, HumanoidArm.RIGHT);//注意物品也会跟随手臂倾斜
          // poseStack.translate(0, -0, -0.8);
         //   entity.mainHand1=poseStack;

         //  poseStack.pushPose();
         //  Render.bindCamera(poseStack);
         //  poseStack.scale(0.2F, 0.2F, 0.2f);
         //  Render.of(poseStack).guiGraphics.fill(1, 1, -1, -1, 0, Color.ofRGBA(255, 255, 255, 255).argbInt());
         //  poseStack.popPose();



          //  r.renderPlayerItem((AbstractClientPlayer) p, poseStack, HumanoidArm.RIGHT);





            poseStack.popPose();


            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees(180));
            poseStack.mulPoseMatrix(entity.offsetHang);
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));
            poseStack.translate(-0.34, -0.7, 0);
            r.renderPlayerItem((AbstractClientPlayer) p, poseStack, HumanoidArm.LEFT);
            poseStack.popPose();






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


        if (p != null) {


            double x = Mth.lerp(partialTick, entity.xOld, entity.getX());
            double y = Mth.lerp(partialTick, entity.yOld, entity.getY());
            double z = Mth.lerp(partialTick, entity.zOld, entity.getZ());

            double nx = Mth.lerp(partialTick, p.xOld, p.getX());
            double ny = Mth.lerp(partialTick, p.yOld, p.getY());
            double nz = Mth.lerp(partialTick, p.zOld, p.getZ());

            poseStack.pushPose();

            poseStack.translate(nx - x, ny - y, nz - z);

            poseStack.mulPose(Axis.YN.rotationDegrees(entity.xRot));




//注意 和骨骼同名的方块的枢轴点需在同一位置
            Render r = Render.of(poseStack);
            renderHead(entity, poseStack, p, r);
            renderChest(entity, poseStack, p, r);
            renderLeg(entity, poseStack, p, r);
            renderFoot(entity, poseStack, p, r);
            renderHand(entity, poseStack, p, r);

            //  RenderSystem.clearColor(0,0,0,0);
            poseStack.pushPose();
            ///    poseStack.scale(0.1F,0.1f,0.1f);
            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
            poseStack.popPose();
            // RenderSystem.clearColor(1,1,1,1);
            poseStack.popPose();

            //poseStack.mulPose(Axis.XN.rotationDegrees(entity.yRot));
            //    System.out.println(entity.head);

//  renderTrail(entity, partialTick, poseStack, bufferSource, 1F, packedLight);
            //    r.poseStack().pushPose();

            //  Vec3 position = r.minecraft.gameRenderer.getMainCamera().getPosition();
            //  poseStack.translate(position.x, position.y, position.z);

            //  //    poseStack.translate(-position.x, -position.y, -position.z);
            //  r.poseStack().translate(p.xOld,
            //          p.yOld,
            //          p.zOld);

            //  r.poseStack().mulPose(    r.minecraft.getEntityRenderDispatcher().cameraOrientation());

            //  r.poseStack().mulPoseMatrix(entity.head);


            //  r.poseStack().popPose();


        }
    }


}
/*
            if (entity.head != null) {
                Render r = Render.of();
                poseStack.pushPose();
              //
                poseStack.mulPose(Axis.YN.rotationDegrees(180));
                poseStack.mulPoseMatrix(entity.head);
                poseStack.mulPose(Axis.ZN.rotationDegrees(180));


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