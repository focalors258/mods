package com.integration_package_core.optimize;

import com.integration_package_core.tool.DeBug;
import com.integration_package_core.tool.Effect;
import com.integration_package_core.tool.Maths;
import com.integration_package_core.tool.Render;
import com.integration_package_core.tool.collide.OBB;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Vector3f;
import software.bernie.geckolib.core.object.Color;

public class LevelSubtitle {


    public static int n = 0;

    public static void texture(ResourceLocation text, int x1, int y1, int u0, int u1, int v0, int v1, Entity e, float r, float g, float b, int type) {

        Vector3f pos;
        pos = e.position().toVector3f();
        pos.y += (float) (e.getBoundingBox().getYsize() / 2);


        new Effect("level", 20, (render, e1) -> {

            if (render.stage == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
                //AFTER_ENTITIES
//


                Minecraft mc = Minecraft.getInstance();

                Camera camera = mc.gameRenderer.getMainCamera();


                //  OBB.render(e);
                net.minecraft.client.gui.Font font = mc.font;


                float tick = e1.getTime();

                // float tick = Mth.lerp(Minecraft.getInstance().getPartialTick(), t - 1, t);
                float base = 1;
                float move = 1;
                if (type == 1) {
                    base = 7;
                } else if (type == 2) {
                    move = -1;
                }

                float a = 0;
                if (tick < 0.4) {
                    a = tick * 25 * base;
                } else {
                    a = (float) (10 + (tick - 0.4) * 2) * base;
                }

                // a =  //Maths.bessel(tick, new Point2D.Float(0, 0), new Point2D.Float(0.125f, 10), new Point2D.Float(0.25f, 20)).y;

                //  pos.y+= (float) (tick*0.1);

                render.poseStack().pushPose();
                float y = 0;
                if (tick > 0.4) {
                    y = (float) (tick - 0.4);
                }
                Render.levelPoseStack(render.poseStack(), pos.x, (float) (pos.y + 0.1 * y), pos.z);
                Render.bindCamera(render.poseStack());

                //   System.out.println(a);
                //    Render.of(render.poseStack()).renderLaser(new Vector3f(0, 0, 0), new Vector3f(0, 5, 0), 0.8F, 0);

                //  font.drawInBatch(var10001, var10002, (float)(-9) / 2.0F, color + (alpha << 24), false, matrix.m_85850_().m_252922_(), bufferSource, Font.DisplayMode.SEE_THROUGH, 0, 15728880);

                render.poseStack().mulPose(Axis.ZP.rotation((float) Math.PI));
                //        ---------

                float i = (float) (0.002 * (20 + (base - 1) * 13 - move * a));

                System.out.println(i);
                float d = 0;
                if (mc.player != null) {

                    d = (float) Math.pow(new OBB(mc.player, mc.getFrameTime()).pos.distance(pos), 0.5);
                }
                d = Math.max(1.5f, Math.min(10, d / 2));


                render.poseStack().scale(i * d, i * d, 1F);//--------------

                render.guiGraphics.blit(text, x1, y1, u0, u1, v0, v1);

                render.poseStack().popPose();
            }

        });


    }

    public static void text(String text, Entity e, float r, float g, float b, int type) {

        Vector3f pos = e.position().toVector3f();
        pos.y += (float) (e.getBoundingBox().getYsize() / 2);
        text(text, pos, r, g, b, type);
    }

    public static void text(String text, Vector3f e, float r, float g, float b, int type) {
      //  n++;
     //   DeBug.tell(String.valueOf(n));
        Vector3f pos = new Vector3f(e);
        //   pos = e.position().toVector3f();
        //  pos.y += (float) (e.getBoundingBox().getYsize() / 2);

        //    pos = e.position().toVector3f();

        //    pos.y += (float) (e.getBoundingBox().getYsize() / 2);
        new Effect("gui", 20, (render, e1) -> {
            if (true) {//render.stage == RenderLevelStageEvent.Stage.AFTER_ENTITIES
                //AFTER_ENTITIES
//


                Minecraft mc = Minecraft.getInstance();

                Camera camera = mc.gameRenderer.getMainCamera();


                //  OBB.render(e);
                net.minecraft.client.gui.Font font = mc.font;


                float tick = e1.getTime();

                // float tick = Mth.lerp(Minecraft.getInstance().getPartialTick(), t - 1, t);
                float base = 2;
                float move = 1;
                if (type == 1) {
                    base = 12;
                } else if (type == 2) {
                    move = -1;
                }

                float a = 0;
                if (tick < 0.4) {
                    a = tick * 25 * base;
                } else {
                    a = (float) (10 + (tick - 0.4) * 3) * base;
                }

                // a =  //Maths.bessel(tick, new Point2D.Float(0, 0), new Point2D.Float(0.125f, 10), new Point2D.Float(0.25f, 20)).y;

                //  pos.y+= (float) (tick*0.1);

                render.poseStack().pushPose();
                float y = 0;
                float y1 = 0;

                if (tick > 0.4) {

                    y = (float) (tick - 0.4);

                    y1 = y;

                } else if (type == 1 && tick < 0.2) {

                    y = (1 - tick * 4);
                    //System.out.println("a");
                }
                //   System.out.println(y);
                //   System.out.println(tick);
                //   // DeBug.tell(String.valueOf(y));
                //        font.drawInBatch(text, 100,100, Color.ofRGBA(r, g, b, (1 - y)).getColor(), false, render.poseStack().last().pose(), mc.renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                //      font.drawInBatch(text, (float) (-font.width(text) * 0.5), type == 2 ? 0 : -30 * (y1), Color.ofRGBA(r, g, b, (1 - y)).getColor(), false, render.poseStack().last().pose(), mc.renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);
                if (mc.player != null) {
                    Vector3f v = camera.getPosition().toVector3f().sub(pos);
//DeBug.tell(String.valueOf(camera.getPosition().x));

//DeBug.tell(String.valueOf(v.x));

                    //    float planeD = v.x * v.x + v.z * v.z;
                    float xR1 = Maths.getCycle(-(camera.getYRot()) + (Maths.getHorizontal(v.x, v.z)));
                    float  yR2=camera.getXRot();

                    float yR1 = -yR2 + Maths.getVertical(v.x, v.y, v.z);
                    if(Math.abs(xR1) > 90 ){
                        //    xR1=0;
                        //  yR2=camera.getXRot()<0?-180-camera.getXRot():180-camera.getXRot();
                //     DeBg.tell(String.valueOf(yR2));
                      yR1=-yR1;
                    //  DeBug.tell(String.valueOf(yR2));

                    }
                //    DeBug.tell(String.valueOf(xR1));
              //   = Math.abs(xR1) > 90 ?  : yR1;
                    xR1 *= (90 - Math.abs(camera.getXRot())) / 90;
                    float w = mc.getWindow().getGuiScaledWidth();
                    float h = mc.getWindow().getGuiScaledHeight();
                    render.poseStack().translate((float) w / 2 + xR1 * w / 90, (float) h / 2 + yR1 * h / 90, 0);

                    //      DeBug.tell(Maths.getCycle(camera.getYRot()) + "     " + camera.getYRot());
                    //   DeBug.tell(xR1+"     "+yR1 );
                    //  System.out.println(Maths.getHorizontal(v.x,v.z)+"     "+ Maths.getVertical(v.x,v.y,v.z));
                }
//render.guiGraphics.fill(0,0,100,100, Color.WHITE.getColor());
                //  Render.levelPoseStack(render.poseStack(), pos.x, (float) (pos.y), pos.z);
                // Render.bindCamera(render.poseStack());
                //   System.out.println(a);
                //  font.drawInBatch(var10001, var10002, (float)(-9) / 2.0F, color + (alpha << 24), false, matrix.m_85850_().m_252922_(), bufferSource, Font.DisplayMode.SEE_THROUGH, 0, 15728880);
                //   render.poseStack().mulPose(Axis.ZP.rotation((float) Math.PI));
                //        ---------
                float i = (float) (0.1 * (20 + (base - 1) * 12 - move * a));
                // System.out.println(i);
                //  float d = 0;
                //  if (mc.player != null) {

                //      d = (float) Math.pow(new OBB(mc.player, mc.getFrameTime()).pos.distance(pos), 0.5);
                //  }
                //  d = Math.max(1.5f, Math.min(10, d / 2));
                //  d = 1;
                // System.out.println(i);
                render.poseStack().scale(i, i, 1F);//--------------
                //   System.out.println(5464);
                //  r.poseStack().translate(mc.getWindow().getWidth()/2,mc.getWindow().getHeight()/2,1000);

                if (tick > 0.04)
                    font.drawInBatch(text, (float) (-font.width(text) * 0.5), type == 2 ? 0 : -30 * (y1), Color.ofRGBA(r, g, b, (1 - y)).getColor(), false, render.poseStack().last().pose(), mc.renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, 15728880);

                //   if (1 - y > 0.9) DeBug.tell(String.valueOf(464));
                // System.out.println(1 - y);

                //   System.out.println(y);
                //     r.guiGraphics.drawString(Minecraft.getInstance().font, text, (int) (font.width(text)*0.5), 0, Color.ofRGBA(255, 255, 255, 255).getColor());


                render.poseStack().popPose();
            }

        });


    }


}

//     float scale = 0;
//     scale = (float)(double)1;
//     double x = Mth.lerp((double)mc.getFrameTime(), e.xOld, e.xOld);
//     double y = Mth.lerp((double)mc.getFrameTime(), e.yOld, e.yOld);
//     double z = Mth.lerp((double)mc.getFrameTime(), e.zOld, e.zOld);
//            Vec3 camPos = camera.getPosition();
//               double camX = camPos.x;
//               double camY = camPos.y;
//               double camZ = camPos.z;
//             PoseStack matrix = new PoseStack();
//           matrix.pushPose();
//        matrix.translate(pos.x - camX, pos.y- camY, pos.z- camZ);
//           matrix.mulPose(Axis.YP.rotation(-camera.getYRot()));
//           matrix.mulPose(Axis.XP.rotation(camera.getXRot()));
//   //      matrix.scale(-scale, -scale, scale);
//           RenderSystem.enableBlend();
//           RenderSystem.defaultBlendFunc();
//           RenderSystem.enableDepthTest();
//           RenderSystem.depthMask(false);
//           int alpha = 255;

//       r.guiGraphics.drawString(Minecraft.getInstance().font, text, 0, 0, Color.ofRGBA(255, 255, 255, 255).getColor());


//        font.drawInBatch("var10001", 0, 0, Color.ofRGBA(255, 255, 255, 255).getColor(), false, matrix.last().pose(), mc.renderBuffers().bufferSource(), Font.DisplayMode.SEE_THROUGH, 0, 15728880);
//           RenderSystem.disableBlend();
//           RenderSystem.disableDepthTest();
//           RenderSystem.depthMask(true);
//           matrix.popPose();
//     System.out.println( (0.2 * (1 - i)));

//      r.guiGraphics.blit(
//              r.texture,
//              -1,  // X坐标
//              -1,                  // Y坐标
//              0,            // 纹理UV X
//              0,                // 纹理UV Y
//              2,             // 宽度
//              2,                  // 高度
//              2, 2);