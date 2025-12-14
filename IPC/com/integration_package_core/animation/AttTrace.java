package com.integration_package_core.animation;

import com.integration_package_core.IPC;
import com.integration_package_core.tool.Render;
import com.integration_package_core.render.RenderTypes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import software.bernie.geckolib.core.object.Color;

import java.util.ArrayList;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class AttTrace {

    public static Vec3 getPos( Matrix4f matrix4f, float r) {


        Vector4f vec = new Vector4f(-0, -0, -r, 1);
        vec.mul(matrix4f);
        vec.rotateY((float) Math.PI);

        return new Vec3(vec.x(), vec.y(), vec.z());//Vec3(vec1.x(), vec1.y(), vec1.z());


    }

    public static void render(ArrayList<Matrix4f> TraceList, MultiBufferSource bufferSource, PoseStack poseStack, PlayerAnimationEntity entity, float r1, float r2,ResourceLocation resourceLocation) {

      //  System.out.println(TraceList.size());
       // System.out.println(TraceList);

        if (entity == null || TraceList.size() < 2) {
            return; // 轨迹点不足时直接返回
        }

        ResourceLocation trailTexture;
        // 1. 选择半透明渲染类型（适合轨迹效果，可替换为你的发光类型）


        trailTexture  = new ResourceLocation(IPC.MODID, "textures/animation_entity/trail.png");

        if (resourceLocation!=null){
            trailTexture=resourceLocation;
        }


       VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderTypes.Beacon(trailTexture));
      //  DeBug.tell(String.valueOf(TraceList));
        // 2. 遍历轨迹点，绘制连续四边形
        for (int i = 1; i <TraceList.size(); i++) {
            Matrix4f currMatrix = TraceList.get(i);       // 当前帧矩阵
            Matrix4f prevMatrix = TraceList.get(i - 1);   // 上一帧矩阵



            if (currMatrix == null || prevMatrix == null) {
                continue; // 跳过空矩阵
            }
           //System.out.println(r1);

           //System.out.println(r2);
            // 3. 从矩阵中获取轨迹的前后位置（左右边界，控制轨迹宽度）
            Vec3 prevLeft = getPos( prevMatrix, r1);  // 上一帧左边界
            Vec3 prevRight = getPos(prevMatrix, r2); // 上一帧右边界
            Vec3 currLeft = getPos( currMatrix, r1);  // 当前帧左边界
            Vec3 currRight = getPos( currMatrix, r2); // 当前帧右边界
          //  System.out.println(currRight);
     //      Objects.requireNonNull(Minecraft.getInstance().getConnection()).sendCommand("particle minecraft:end_rod "+prevLeft.x+" "+prevLeft.y+" "+prevLeft.z+" 0.1 0.1 0.1 0 1 normal");
     //      Objects.requireNonNull(Minecraft.getInstance().getConnection()).sendCommand("particle minecraft:end_rod "+prevRight.x+" "+prevRight.y+" "+prevRight.z+" 0.1 0.1 0.1 0 1 normal");
     //      Objects.requireNonNull(Minecraft.getInstance().getConnection()).sendCommand("particle minecraft:end_rod "+currLeft.x+" "+currLeft.y+" "+currLeft.z+" 0.1 0.1 0.1 0 1 normal");
     //      Objects.requireNonNull(Minecraft.getInstance().getConnection()).sendCommand("particle minecraft:end_rod "+currRight.x+" "+currRight.y+" "+currRight.z+" 0.1 0.1 0.1 0 1 normal");



            // 4. 获取PoseStack当前变换矩阵（用于坐标转换）
            PoseStack.Pose pose = poseStack.last();
            Matrix4f worldMatrix = pose.pose();       // 世界坐标变换矩阵
            Matrix3f normalMatrix = pose.normal();    // 法线矩阵（光照计算）

            // 5. 计算纹理坐标（让纹理沿轨迹连续分布）
            float totalSegments = TraceList.size() - 1; // 总段数
            float uStart = (i - 1) / totalSegments;    // 起始纹理U
            float uEnd = i / totalSegments;            // 结束纹理U
            float vTop = 0.0f;                         // 纹理上边界V
            float vBottom = 1.0f;                      // 纹理下边界V

            RenderSystem.enableBlend();

            // 6. 绘制四边形（按顺时针顺序添加顶点，避免背面剔除）

            //在此处使用RenderSystem修改渲染器颜色无效果


            // 顶点1：上一帧右边界
            {
                vertexConsumer.vertex(worldMatrix,
                                (float) prevRight.x, (float) prevRight.y, (float) prevRight.z)//<------------
                        .color(entity.traceColor[0],
                                entity.traceColor[1],
                                entity.traceColor[2],
                                entity.traceColor[3]) // 白色半透明
                        .uv(uStart, vBottom)      // 纹理坐标
                        .overlayCoords(NO_OVERLAY)
                        .uv2(15728880) // 最大亮度（不受环境光影响）
                        .normal(normalMatrix, 0.0f, 1.0f, 0.0f)//<---------
                        .endVertex();
            }

            // 顶点2：当前帧右边界
            {
                vertexConsumer.vertex(worldMatrix,
                                (float) currRight.x, (float) currRight.y, (float) currRight.z)
                        .color(entity.traceColor[0],
                                entity.traceColor[1],
                                entity.traceColor[2],
                                entity.traceColor[3])
                        .uv(uEnd, vBottom)
                        .overlayCoords(NO_OVERLAY)
                        .uv2(15728880)
                        .normal(normalMatrix, 0.0f, 1.0f, 0.0f)
                        .endVertex();
            }

            // 顶点3：当前帧左边界
            {
                vertexConsumer.vertex(worldMatrix,
                                (float) currLeft.x, (float) currLeft.y, (float) currLeft.z)
                        .color(entity.traceColor[0],
                                entity.traceColor[1],
                                entity.traceColor[2],
                                entity.traceColor[3])
                        .uv(uEnd, vTop)
                        .overlayCoords(NO_OVERLAY)
                        .uv2(15728880)
                        .normal(normalMatrix, 0.0f, 1.0f, 0.0f)
                        .endVertex();
            }

            // 顶点4：上一帧左边界
            {
                vertexConsumer.vertex(worldMatrix,
                                (float) prevLeft.x, (float) prevLeft.y, (float) prevLeft.z)
                        .color(entity.traceColor[0],
                                entity.traceColor[1],
                                entity.traceColor[2],
                                entity.traceColor[3])
                        .uv(uStart, vTop)
                        .overlayCoords(NO_OVERLAY)
                        .uv2(15728880)
                        .normal(normalMatrix, 0.0f, 1.0f, 0.0f)
                        .endVertex();
            }


        }
    }

    // 确保getPos方法正确从矩阵中提取位置（参考之前的矩阵平移分量提取逻辑）

    /*
    private static Vec3 getPos(PoseStack poseStack, PlayerAnimationEntity entity, Matrix4f matrix, float offset) {
        // 从矩阵中提取平移分量（X/Y/Z）
        float x = matrix.m30();
        float y = matrix.m31();
        float z = matrix.m32();

        // 应用额外偏移（根据r1/r2控制左右边界，这里假设offset是横向偏移）
        // 注意：需根据实体朝向计算偏移方向（示例为简化版）
        Vec3 entityDir = entity.getLookAngle().normalize(); // 实体朝向
        Vec3 rightDir = new Vec3(-entityDir.z, 0, entityDir.x).normalize(); // 右侧方向
        Vec3 offsetPos = rightDir.scale(offset); // 横向偏移

        // 转换为世界坐标（结合PoseStack的全局变换）
        return new Vec3(x + offsetPos.x, y + offsetPos.y, z + offsetPos.z);
    }

*/

    /*
    public static void render(MultiBufferSource bufferSource,PoseStack poseStack, PlayerAnimationEntity entity, float r1, float r2) {


        //  if (Minecraft.getInstance().player instanceof PlayerExpand p) {  }
        if (entity != null && entity.mainHand != null) {


            VertexConsumer vertexconsumer = bufferSource.getBuffer(
                    RenderType.entityTranslucent(new ResourceLocation(IntegrationPackageCore.MODID, "textures/animation_entity/trail.png")));

            for (int i = 1; i < entity.traceList.size(); i++) {
                Matrix4f matrix4f = entity.traceList.get(i);
                Matrix4f Omatrix4f = entity.traceList.get(i-1);

                if (matrix4f != null&&Omatrix4f!=null) {
                  // poseStack.pushPose();
                  // poseStack.popPose();
                    //  poseStack.last().pose().m30()


               Vec3 oldPos1 = getPos(poseStack, entity, Omatrix4f, r1);
               Vec3 newPos1 = getPos(poseStack, entity, matrix4f, r2);
               Vec3 oldPos2 = getPos(poseStack, entity, Omatrix4f, r1);
               Vec3 newPos2 = getPos(poseStack, entity, matrix4f, r2);




                    PoseStack.Pose posestack$pose = poseStack.last();
                    Matrix4f matrix4f1 = posestack$pose.pose();
                    Matrix3f matrix3f = posestack$pose.normal();
                //  vertexconsumer.vertex(matrix4f1, (float) oldPos1.x, (float) oldPos1.y, (float) oldPos1.z).color(1f, 1f, 1f, 0.5f).uv(0, 1F).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                //  vertexconsumer.vertex(matrix4f1, (float) newPos1.x, (float) newPos1.y, (float) newPos1.z).color(1f, 1f, 1f, 0.5f).uv(0, 1F).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                //  vertexconsumer.vertex(matrix4f1, (float) oldPos2.x, (float) oldPos2.y, (float) oldPos2.z).color(1f, 1f, 1f, 0.5f).uv(1, 0).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                //  vertexconsumer.vertex(matrix4f1, (float)newPos2 .x, (float) newPos2 .y, (float)newPos2 .z).color(1f, 1f, 1f, 0.5f).uv(1, 0).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                    float u1 = i / (float)  entity.traceList.size();    // 起始纹理坐标（0~1）
                    float u2 = u1 + 1 / (float)  entity.traceList.size();
                 //   System.out.println();
                    //Objects.requireNonNull(Minecraft.getInstance().getConnection()).sendCommand("particle minecraft:end_rod "+oldPos1.x+" "+oldPos1.y+" "+oldPos1.z+" 0.1 0.1 0.1 0 1 normal");


         // vertexconsumer.vertex(matrix4f1, 0, 0, 1).color(1f, 1f, 1f, 0.5f).uv(u1, 1F).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
         // vertexconsumer.vertex(matrix4f1, 0, 1, 0).color(1f, 1f, 1f, 0.5f).uv(u2, 1F).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
         // vertexconsumer.vertex(matrix4f1, 1, 1, 1).color(1f, 1f, 1f, 0.5f).uv(u2, 0).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
         // vertexconsumer.vertex(matrix4f1, 1, 0, 0).color(1f, 1f, 1f, 0.5f).uv(u1, 0).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();

                  vertexconsumer.vertex(matrix4f1, 0, 0, 1).color(1f, 1f, 1f, 0.5f).uv(u1, 1F).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                  vertexconsumer.vertex(matrix4f1, 0, 1, 0).color(1f, 1f, 1f, 0.5f).uv(u2, 1F).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                  vertexconsumer.vertex(matrix4f1, 1, 1, 1).color(1f, 1f, 1f, 0.5f).uv(u2, 0).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
                  vertexconsumer.vertex(matrix4f1, (float)-newPos1.x, (float)-newPos1.y, (float)-newPos1.z).color(1f, 1f, 1f, 0.5f).uv(u1, 0).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();

          //   vertexconsumer.vertex(matrix4f1, (float) newPos2.x, (float)newPos2.y, (float) newPos2.z).color(1f, 1f, 1f, 0.5f).uv(u1, 1F).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
          //   vertexconsumer.vertex(matrix4f1, (float)oldPos2.x, (float)oldPos2.y, (float)oldPos2.z).color(1f, 1f, 1f, 0.5f).uv(u2, 1F).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
          //   vertexconsumer.vertex(matrix4f1, (float)oldPos1.x, (float)oldPos1.y, (float)oldPos1.z).color(1f, 1f, 1f, 0.5f).uv(u2, 0).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
          //   vertexconsumer.vertex(matrix4f1, (float)newPos1.x, (float)newPos1.y, (float)newPos1.z).color(1f, 1f, 1f, 0.5f).uv(u1, 0).overlayCoords(NO_OVERLAY).uv2(150).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();



                    //   Render.bindCamera(poseStack);
                 //   poseStack.scale(0.2F, 0.2F, 0.2f);
                 //   Render.of(poseStack).guiGraphics.fill(1, 1, -1, -1, 0, Color.ofRGBA(255, 255, 255, 255).argbInt());


                }


            }

            entity.traceList.forEach(mainHand -> {


            });


        }


    }
*/
    public static void render(PoseStack poseStack, PlayerAnimationEntity entity) {


        //  if (Minecraft.getInstance().player instanceof PlayerExpand p) {  }
        if (entity != null && entity.mainHand != null) {

            PlayerAnimationEntity p1 = entity;

            poseStack.pushPose();

            //  Vector4f vec = new Vector4f(0, 0, 0, 1);
            //  vec.mul(matrix4f);

            //   poseStack.mulPose(Axis.YN.rotationDegrees(-p.getAnimationEntity().xRot));
            //  //   poseStack.mulPose(Axis.YN.rotationDegrees(p.getAnimationEntity().xRot - 180));
            //  poseStack.mulPose(Axis.YN.rotationDegrees(-180));
            //  poseStack.translate(
            //          vec.x(),
            //          vec.y(),
            //          vec.z());


            //  poseStack.mulPose(Axis.YN.rotationDegrees(-entity.xRot + 180));


            Render.bindCamera(poseStack);


            poseStack.scale(0.2F, 0.2F, 0.2f);
            Render.of(poseStack).guiGraphics.fill(1, 1, -1, -1, 0, Color.ofRGBA(255, 255, 255, 255).argbInt());
            poseStack.popPose();
        }


    }
}

//   poseStack.mulPose(Axis.YN.rotationDegrees(-p.getAnimationEntity().xRot));
//   poseStack.mulPose(Axis.YN.rotationDegrees(p.getAnimationEntity().xRot - 180));






/*
*
*  //   poseStack.mulPose(Axis.YN.rotationDegrees(-180));
  //   poseStack.translate(
  //           entity.getX()+vec.x(),
  //           entity.getY()+vec.y(),
  //           entity.getZ()+vec.z());
  //   poseStack.mulPose(Axis.YN.rotationDegrees(-entity.xRot + 180));


  //   Vector4f vec1 = new Vector4f(0, 0, 0, 1);
  //   vec.mul(matrix4f);


     //   System.out.println(vec1.x()+"   "+ vec1.y()+"   "+vec1.z());



        //System.out.println(entity.getX()+vec.x()+"   "+ entity.getY()+vec.y()+"   "+ entity.getZ()+vec.z());

*
*    entity.traceList.forEach(mainHand->{
         if(mainHand!=null){
             poseStack.pushPose();
             Vector4f vec = new Vector4f(0, 0, -r, 1);
             vec.mul(mainHand);
             poseStack.mulPose(Axis.YN.rotationDegrees(-180));
             poseStack.translate(
                     vec.x(),
                     vec.y(),
                     vec.z());

             poseStack.mulPose(Axis.YN.rotationDegrees(-entity.xRot + 180));


             Render.bindCamera(poseStack);
             poseStack.scale(0.2F, 0.2F, 0.2f);
             Render.of(poseStack).guiGraphics.fill(1, 1, -1, -1, 0, Color.ofRGBA(255, 255, 255, 255).argbInt());

             poseStack.popPose();
         }

     });
*
*
*
*
*
*
*
*
*
*
*
*
*
* */











