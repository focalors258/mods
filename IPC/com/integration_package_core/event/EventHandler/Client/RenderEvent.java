package com.integration_package_core.event.EventHandler.Client;

import com.integration_package_core.IPC;
import com.integration_package_core.animation.AttTrace;
import com.integration_package_core.animation.PlayerAnimationEntity;
import com.integration_package_core.animation.PlayerAnimationEntityRender;
import com.integration_package_core.animation.Storyboard;
import com.integration_package_core.event.Event.KeyBindsEvent;
import com.integration_package_core.mixinTool.*;
import com.integration_package_core.tool.*;
import com.integration_package_core.optimize.EntityBar;
import com.integration_package_core.render.ShaderManager;
import com.integration_package_core.tool.collide.OBB;
import com.integration_package_core.util.Network.NetworkManager;
import com.integration_package_core.util.Utlis;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.*;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.event.GeoRenderEvent;

import java.lang.Math;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = IPC.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderEvent {
    public static HashMap<String, PostChain> cnm = new HashMap<>();
    public static Minecraft mc = Minecraft.getInstance();
    static EntityRenderDispatcher Dispatcher;

    //
//
//        Effect. renderUI.forEach(Effect::tick);
//
//
//
    private static boolean c;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void o(RenderLevelStageEvent e) {

        //  Camera a= (Camera) Minecraft.getInstance().gameRenderer.getMainCamera();
        //  e.getPoseStack().mulPose(Axis.YN.rotationDegrees(-a.getYRot()));


        if (Minecraft.getInstance().player != null) {
            if (e.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {

                OBB.render(e);

            }
            if (false && e.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {


                e.getPoseStack().pushPose();
                Render.levelPoseStack(e.getPoseStack(), 0, 0, 0);
                // Render.bindCamera(e.getPoseStack());
                Player p = Minecraft.getInstance().player;
                //      Render.of(e.getPoseStack()).renderLaser(p.position().toVector3f(), p.position().add(2, 2, 2).toVector3f(), 0.4F, 10);

                p.level().getEntities(null, AABB.of(new BoundingBox(0, -60, 0, 546, 45, 456))).forEach(e1 -> {

                    Render.of(e.getPoseStack()).renderLaser(new Vector3f(0, -10, 6), new OBB(e1).pos, 0.05F);//第二点高于第一点 无法旋转


                });

                e.getPoseStack().popPose();

                // System.out.println(    (  (PlayerExpand)  Minecraft.getInstance().player).getWeaponData());
            }
        }


        if (Minecraft.getInstance().player != null) {
            Effect.renderLevel.removeIf(e1 -> {

                return Effect.renderLevel.size() > 60 || (Minecraft.getInstance().player.tickCount > e1.end);
            });

            Effect.renderLevel.forEach(eff -> {

                //System.out.println("bbb");
                e.getPoseStack().pushPose();
                eff.render(Render.of(e.getPoseStack(), e.getStage()));
                e.getPoseStack().popPose();

            });
            Minecraft.getInstance().renderBuffers().bufferSource().endBatch();


        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void k(RenderGuiEvent.Pre e) {

        e.getGuiGraphics().pose().pushPose();

        //  e.getGuiGraphics().fill(0,0,400,400,Color.ofRGBA(255,255,255,255).getColor());
        if (Minecraft.getInstance().player != null && !EntityBar.gui.isEmpty()) {

            // System.out.println(((float) e.getWindow().getGuiScaledWidth()) / 854);
            //  EntityBar.renderBar(20, 390, 800, 20, 1, 80, 0, 50, e.getGuiGraphics());
            //  if (EntityBar.gui.get(0).isRemoved()) EntityBar.gui.remove(0);

            if ((EntityBar.gui.get(0).isRemoved() || EntityBar.gui.get(0).distanceTo(Minecraft.getInstance().player) >= 40))
                EntityBar.gui.remove(0);

            if (EntityBar.gui.size() == 1) {


                EntityBar.renderGuiEntityBar(e, (float) e.getWindow().getGuiScaledWidth() / 2, 400, EntityBar.gui.get(0));
            } else if (EntityBar.gui.size() == 2) {

                EntityBar.renderGuiEntityBar(e, (float) e.getWindow().getGuiScaledWidth() / 3, 300, EntityBar.gui.get(0));
                EntityBar.renderGuiEntityBar(e, (float) e.getWindow().getGuiScaledWidth() * 2 / 3, 300, EntityBar.gui.get(1));

                if ((EntityBar.gui.get(1).isRemoved() || EntityBar.gui.get(1).distanceTo(Minecraft.getInstance().player) >= 40)) {
                    EntityBar.gui.remove(1);
                }
            } else if (EntityBar.gui.size() > 2) {

                EntityBar.renderGuiEntityBar(e, (float) e.getWindow().getGuiScaledWidth() / 4, 200, EntityBar.gui.get(0));
                EntityBar.renderGuiEntityBar(e, (float) e.getWindow().getGuiScaledWidth() * 2 / 4, 200, EntityBar.gui.get(1));
                EntityBar.renderGuiEntityBar(e, (float) e.getWindow().getGuiScaledWidth() * 3 / 4, 200, EntityBar.gui.get(2));


                if ((EntityBar.gui.get(1).isRemoved() || EntityBar.gui.get(1).distanceTo(Minecraft.getInstance().player) >= 40)) {
                    EntityBar.gui.remove(1);
                } else if ((EntityBar.gui.get(2).isRemoved() || EntityBar.gui.get(2).distanceTo(Minecraft.getInstance().player) >= 40)) {
                    EntityBar.gui.remove(2);

                }
            }


        }
        e.getGuiGraphics().pose().popPose();


        if (Minecraft.getInstance().player != null) {

            Effect.renderGuI.removeIf(e1 -> {
                return Effect.renderGuI.size() > 60 || (Minecraft.getInstance().player.tickCount > e1.end);
            });

            Effect.renderGuI.forEach(eff -> {
                e.getGuiGraphics().pose().pushPose();
                eff.render(Render.of(e.getGuiGraphics().pose()));
                e.getGuiGraphics().pose().popPose();
            });
        }


    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void h(InputEvent.Key e) {

        // System.out.println(Minecraft.getInstance().screen);

        if (KeyBinds.skill_1.getKey().getValue() == e.getKey()) {

            NetworkManager.send("rdgdr");


        }


    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void a(ViewportEvent.ComputeFov e) {


    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void a(ViewportEvent.ComputeCameraAngles e) {
//fov纵向不随屏幕高度变化   横向随宽度变化

        PlayerAnimationEntityRender.battleShot(e);
        Player p1 = Minecraft.getInstance().player;

        PlayerExpand p2 = (PlayerExpand) p1;
        LivingEntity l = null;
        if (p2 != null && p2.getAnimationEntity() != null && p2.getAnimationEntity().getStoryboard() != null) {
            //      l = (LivingEntity) p2.getAnimationEntity().target;
            Storyboard s = p2.getAnimationEntity().getStoryboard();




//目标实体坐标 需加上总骨骼坐标偏移
      //      float x = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), s.target.xOld, s.target.getX());// (float) (( + l.getX()) / 2);
      //      float y = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), s.target.yOld + 1, s.target.getY() + 1);//(float) (( + l.getY()) / 2);
      //      float z = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), s.target.zOld, s.target.getZ());//(float) ((+ l.getZ()) / 2);
            PlayerAnimationEntity pa = p2.getAnimationEntity();

            CameraMember c = (CameraMember) e.getCamera();

            Vector3f storyboardPos = s.getMovePosition();

            e.setYaw(s.yRotTarget);//先设置yaw让偏移坐标随分镜目标实体旋转
            e.setPitch(0);
            c.$setXRot(0);
            c.$setYRot(s.yRotTarget);//设置3个旋转轴  (第三人称)偏移后的视角坐标不跟随设置事件前的旋转值

            float delta = Math.min(1 - ((pa.transitEnd - Mth.lerp(Minecraft.getInstance().getPartialTick(), p1.tickCount - 1, p1.tickCount)) / pa.transitSpeed), 1);
           // System.out.println(delta);
        //战斗镜头

            c.$setPosition(    pa.transitCamera.lerp(new Vec3(s.levelPos.x, s.levelPos.y, s.levelPos.z), delta));//原点坐标 纵轴不需要sui
            c.$move(storyboardPos.x, storyboardPos.y, storyboardPos.z);//旋转后坐标 坐标在此后固定  只修改yaw与pitch

         //   Vector3f camPos = e.getCamera().getPosition().toVector3f();

//摄像机与目标实体之间的方向

     // float[] maxXY  = Maths.getMaxWindowAngles();

     //    float[]XY=s.getClipAngles(new Vector3f(x,y,z));

     //    float X=XY[0];
     //    float Y=XY[1];

     //    //   System.out.println(maxX);
     //    if (X > maxXY[1]+s.reviseXRot) {//竖

     //
     //        s.setXRot(s.getXRot() - 0.01F);

     //
     //    } else if (X < -maxXY[1]-s.reviseXRot) {

     //        s.setXRot(s.getXRot() + 0.01F);
     //
     //    }

     //    if (Y > maxXY[0]+s.reviseXRot) {//横
     //        s.setYRot(s.getYRot() - 0.01F);
     //    } else if (Y < -maxXY[0]-s.reviseXRot) {
     //        s.setYRot(s.getYRot() + 0.01F);
     //    }


            // System.out.println(-dAngle[1]);
            // System.out.println(Maths.getVertical(direction2.x,direction2.y,direction2.z));

            //  System.out.println(direction);
            //  System.out.println(new Vector3f(lx, ly, lz).normalize());

            //   System.out.println(Arrays.toString()
            //   );

//   direction.rotateAxis((float) (-Maths.getVertical(lx,ly,lz)*Math.PI/180),direction.x,0,-direction.z);
            //   float xx = Maths.getHorizontal(x - lx, z - lz);

            //       System.out.println(Maths.getHorizontal(direction.x, direction.z)+"    "+   Maths.getVertical(direction.x, direction.y,direction.z));
            ;
//if(){}
            //    System.out.println(s.yRot);

            float x=Mth.lerp(mc.getPartialTick(),s.xRotO,s.getXRot());
            float y=Mth.lerp(mc.getPartialTick(),s.yRotO,s.getYRot());

            p1.setYRot(y);//玩家角度


            e.setYaw(y);//摄像机角度(事件)
            c.$setYRot(y);//摄像机角度(原对象)
            e.setPitch(x);
            c.$setXRot(x);
//e.setRoll();
//Maths

            pa.getStoryboard().tick();


            //camPos.rotationTo()
            //   e.setYaw();
        }else{









        }
















    }


    //  @OnlyIn(Dist.CLIENT)

    public static void o(RenderGuiEvent.Post e) {

        PoseStack stack = e.getGuiGraphics().pose();


        Render graphics = Render.of(stack);

        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();


        stack.pushPose();
//stack.translate(-100,-100,100);
        //  graphics.guiGraphics.fill(1,1,500,500,300,Color.ofRGBA(255,255,255,255).getColor());

        // stack.scale(width*0.001F, height*0.001F, 0.1F);

        graphics.guiGraphics.drawString(Minecraft.getInstance().font, "4654345346", 100, 114, 16777215);

        RenderSystem.clear(256, Minecraft.ON_OSX);
        RenderSystem.enableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder renderer = tessellator.getBuilder();

        RenderSystem.setShader(() -> ShaderManager.aaaaaa);
        RenderSystem.setShaderTexture(0, new ResourceLocation(IPC.MODID, "textures/tunnel0.png"));
        // System.out.println("------------------------------------------------"+RenderShader.aaaaaa+"------------------------------------------------");
        //  System.out.println("------------------------------------------------"+RenderSystem.getShader()+"------------------------------------------------");
//.tell(String.valueOf(RenderSystem.getShader()));
        //RenderSystem.setShader(GameRenderer::getPositionTexColorShader);GameRenderer::getPositionTexShader

        int red = (int) (0.5 * 255);
        int green = (int) (0.5 * 255);
        int blue = (int) (0.5 * 255);
        double z = -90;

        float opacity = 1F;

        RenderSystem.setShader(() -> ShaderManager.aaaaaa);

        renderer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        renderer.vertex(0.0D, height, z).uv(0.0F, 1.0F).color(red, green, blue, (int) (opacity * 255F)).endVertex();
        renderer.vertex(width, height, z).uv(1.0F, 1.0F).color(red, green, blue, (int) (opacity * 255F)).endVertex();
        renderer.vertex(width, 0.0D, z).uv(1.0F, 0.0F).color(red, green, blue, (int) (opacity * 255F)).endVertex();
        renderer.vertex(0.0D, 0.0D, z).uv(0.0F, 0.0F).color(red, green, blue, (int) (opacity * 255F)).endVertex();
        tessellator.end();
        stack.popPose();

        RenderSystem.applyModelViewMatrix();
        graphics.guiGraphics.flush();


    }


    //@OnlyIn(Dist.CLIENT)
    //@SubscribeEvent
    public static void o(KeyBindsEvent e) {


        //  System.out.println("==========================================================================");
//
        //   e.register("23424", keyBinds.skill_2);
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
    public static void j(GeoRenderEvent.Entity.Post e) {//轨迹渲染


        if (e.getEntity() instanceof PlayerAnimationEntity p && !p.isEnd()) {
            //     RenderSystem.setShaderColor(1,1,1,1);
            AttTrace.render(p.mainTraceList, e.getBufferSource(), e.getPoseStack(), p, (float) (p.getMTraceR1()), (float) (p.getMTraceR2()), p.mainTraceResource);//渲染轨迹
            AttTrace.render(p.offsetTraceList, e.getBufferSource(), e.getPoseStack(), p, (float) (p.getOTraceR1()), (float) (p.getOTraceR2()), p.offsetTraceResource);//渲染轨迹

        }

        //*/
    }

    @OnlyIn(Dist.CLIENT)
    //  @SubscribeEvent
    public static void j(RenderLevelStageEvent e) {//e.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL &&


        Minecraft mc = Minecraft.getInstance();
        if (e.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL && mc.player != null) {
            ShaderManager.renderEffect("cnmd", mc.getWindow().getWidth(), mc.getWindow().getHeight());


            ShaderManager.Effect eff = ShaderManager.getEffects("cnmd");

            if (eff != null) {
                int depthTexId = mc.getMainRenderTarget().getDepthTextureId();
                // System.out.println(depthTexId);

                //  eff.getGlValue("Depth").set(depthTexId); // vec2用两个float


                eff.getGlValue("Radius1").set(0f);
                eff.getGlValue("Radius2").set(0.2f);     // float用float值(float) mc.player.tickCount%30/30
                eff.getGlValue("Amount").set((float) 30);

            }
        }

        if (false) {
            if (e.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
                cnm.values().forEach((chain) -> {

                    chain.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());

                    chain.process(e.getPartialTick());

                    //System.out.println(chain);


                });
                mc.getMainRenderTarget().bindWrite(false);
            }


            // 2. 确保Map和PostChain存在
            if (cnm == null || !cnm.containsKey("nmb") || !(cnm.get("nmb") instanceof net.minecraft.client.renderer.PostChain)) {
                return;
            }

            //net.minecraft.client.renderer.PostChain postChain = (net.minecraft.client.renderer.PostChain) ;
            // 3. 遍历PostChain中的所有Pass，找到你的手电筒Shader

            if (cnm.get("nmb") instanceof PostChainMember ppp) {

                for (net.minecraft.client.renderer.PostPass pass : ppp.getPasses()) {
                    EffectInstance effect = pass.getEffect();
                    if (effect == null || !effect.getName().equals("integration_package_core:flashlight")) {
                        continue;
                    }

                    // 4. 安全设置Uniform（用EffectInstance的方法，类型严格匹配）
                    // 假设Offset是vec2（float, float），Radius和Intensity是float
                    effect.safeGetUniform("Offset").set(0.0F, 0.0F); // vec2用两个float
                    effect.safeGetUniform("Radius").set(1000.0F);     // float用float值
                    effect.safeGetUniform("IntensityAmount").set(100.0F);
                }

                //  ShaderManager.setEffect();


            }


        }


        if (false) {//未知的原因导致启用光影时无法在level渲染刀光
            //PlayerAnimationEntityRender.renderTrace(e);
        }


    }

    @SubscribeEvent
    public static void onRenderLevelAfter(RenderLevelStageEvent event) {
        // 1. 只在“世界渲染后”阶段执行（后处理安全时机）
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            //   return;
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


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void p(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> e) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        PlayerAnimationEntityRender.preventPlayerRender(e);


        EntityBar.renderLevelEntityBar(e);

        Entity entity = e.getEntity();

        float t = entity.invulnerableTime;

        float tick = Mth.lerp(e.getPartialTick(), Utlis.tickCount - 1, Utlis.tickCount );


        if (entity instanceof LivingEntityExpand e1 && !(entity instanceof Player)) {
            if (entity.invulnerableTime > 18 && !e1.isRigid() && !e1.isSmash()) {

                e.getPoseStack().translate(
                        (2 * Math.random() - 1) / 10,
                        0,
                        (2 * Math.random() - 1) / 10);

            }
            e.getPoseStack().mulPose(Axis.YN.rotationDegrees(-Mth.lerp(e.getPartialTick(), entity.yRotO, entity.getYRot())));
            if (e1.isSmash()) {//击溃

                float t1 = e1.getSmashTime() - (e1.getSmashEnd() - tick);
                float t2 = (float) (Math.pow(Math.max(0.1, t1 / 10), 6));//+Math.abs(t1/e1.getSmashTime()-0.9f);


                if (t1 < 10) {
                    e.getPoseStack().translate(0, e.getEntity().getBoundingBox().getXsize() * t2 / 2, 0);
                    e.getPoseStack().mulPose(Axis.XN.rotationDegrees(t2 * 90));

                } else {
                    e.getPoseStack().translate(0, e.getEntity().getBoundingBox().getXsize() / 2, 0);
                    e.getPoseStack().mulPose(Axis.XN.rotationDegrees(90));

                }


            } else if (e1.isRigid()) {//硬直

                float t1 = e1.getRigidTime() - (e1.getRigidEnd() - tick);

                float t2 = e1.getRigidTime() / 2;

                //System.out.println(e1.getRigidEnd() - tick);
                //System.out.println(t1);
                //System.out.println("tick"+tick);
                if (t1 < 3) {
                    e.getPoseStack().mulPose(Axis.XN.rotationDegrees((float) (30 * Math.pow(Math.max(0.1, Math.max(2, t2 / 20) * t1 / 3), 2))));
                } else if (t1 < t2) {

                    e.getPoseStack().mulPose(Axis.XN.rotationDegrees((float) (30 * Math.pow(Math.max(0.1, Math.max(2, t2 / 20) * (t2 - t1) / (t2 - 3)), 2))));
                }


            }
            e.getPoseStack().mulPose(Axis.YN.rotationDegrees(Mth.lerp(e.getPartialTick(), entity.yRotO, entity.getYRot())));


        }


        //  RenderSystem.setShaderColor(5,3,4,1);
        //   if (Dispatcher == null) {
        //       Dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();//(EntityRenderDispatcher)
        //   }
        //  if(e.getEntity() instanceof PlayerExpand p){
        //      r.fill(poseStack,a,new float[]{255,255,255,255});

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

        if (Minecraft.getInstance().player != null) {


            // System.out.println(    (  (PlayerExpand)  Minecraft.getInstance().player).getWeaponData());
        }

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

            if (!a1.isEmpty() && false) {
                poss1 = new float[]{(float) a1.get(0).getX(), (float) a1.get(0).getY(), (float) a1.get(0).getZ(),};

                poss2 = new float[]{(float) p2.getX(), (float) p2.getY(), (float) p2.getZ()};
                // Render.of(poseStack).renderLaserV(poss1, poss2, 0.2f);
                //  Render.of(poseStack).renderLaserH(poss1, poss2, 0.2f);
                Render r = Render.of(poseStack);

                r.setTexture(new ResourceLocation(IPC.MODID, "textures/animation_entity/trail1.png"));
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
  if (false) {
                    poseStack.pushPose();  // 保存当前矩阵状态


                    // 绘制血条外框
                    guiGraphics.blit(
                            EntityBar.EntityBar_TEXTURE,
                            (int) (-(length + 2) / 2),  // X坐标
                            0,                  // Y坐标
                            pos,                // 纹理UV X
                            208F,                // 纹理UV Y
                            (int) (length + 4),         // 宽度
                            5,                  // 高度
                            256,                // 纹理总宽度
                            256                 // 纹理总高度
                    );
                    // 绘制血条底部背景
                    guiGraphics.blit(
                            EntityBar.EntityBar_TEXTURE,
                            (int) (-(length - 2) / 2),  // X坐标
                            1,                  // Y坐标
                            pos + 2,            // 纹理UV X
                            229F,                // 纹理UV Y
                            (int) length,             // 宽度
                            3,                  // 高度
                            256, 256
                    );

                    // 根据新旧生命值关系绘制不同状态的血条
                    if (oldHealth >= health) {
                        // 绘制旧生命值（灰色部分）
                        poseStack.pushPose();
                        poseStack.scale(oldHealth, 1.0F, 1.0F);
                        poseStack.translate(-(length - 2) * 0.5 / oldHealth, 1, -0.01);
                        guiGraphics.blit(
                                EntityBar.EntityBar_TEXTURE,
                                0, 0,
                                pos + 2, 214F,  // 灰色纹理UV
                                (int) length, 3,
                                256, 256
                        );
                        poseStack.popPose();  // 恢复矩阵状态

                        // 绘制旧护盾（红色护盾）
                        // 绘制当前生命值（粉色部分）
                        poseStack.pushPose();
                        poseStack.scale(health, 1.0F, 1.0F);
                        poseStack.translate(-(length - 2) * 0.5 / health, 1, -0.02);  // 层级更靠上
                        guiGraphics.blit(
                                EntityBar.EntityBar_TEXTURE,
                                0, 0,
                                pos + 2, 224F,  // 粉色纹理UV
                                (int) length, 3,
                                256, 256
                        );

                        poseStack.popPose();

                    } else {
                        // 当当前生命值 > 旧生命值时的绘制逻辑（绿色+红色过渡）

                        // 绘制当前生命值（绿色部分）
                        poseStack.pushPose();
                        poseStack.scale(health, 1.0F, 1.0F);
                        poseStack.translate(-(length - 2) * 0.5 / health, 1, -0.02);
                        guiGraphics.blit(
                                EntityBar.EntityBar_TEXTURE,
                                0, 0,
                                pos + 2, 219F,  // 绿色纹理UV
                                (int) length, 3,
                                256, 256
                        );
                        poseStack.popPose();

                        // 绘制旧生命值（红色部分）
                        poseStack.pushPose();
                        poseStack.scale(oldHealth, 1.0F, 1.0F);
                        poseStack.translate(-(length - 2) * 0.5 / oldHealth, 1, -0.02);
                        guiGraphics.blit(
                                EntityBar.EntityBar_TEXTURE,
                                0, 0,
                                pos + 2, 224F,  // 红色纹理UV
                                (int) length, 3,
                                256, 256
                        );

                        poseStack.popPose();
                    }

                    // 结束矩阵变换
                    poseStack.popPose();
                }
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