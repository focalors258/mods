package com.integration_package_core.tool;

import com.integration_package_core.IntegrationPackageCore;
import com.integration_package_core.mixinTool.HumanoidArmorLayerMember;
import com.integration_package_core.mixinTool.LivingEntityRenderMember;
import com.integration_package_core.mixinTool.PlayerItemInHandLayerMember;
import com.integration_package_core.mixinTool.RenderLayerMember;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.*;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class Render {

    public final Minecraft minecraft;

    public final MultiBufferSource.BufferSource bufferSource;

    public final GuiGraphics guiGraphics;

    public EntityRenderDispatcher Dispatcher;//(EntityRenderDispatcher)

    public static Render of() {

        return new Render(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
    }

    public static Render of(PoseStack poseStack) {

        return new Render(Minecraft.getInstance(), poseStack, Minecraft.getInstance().renderBuffers().bufferSource());
    }
    public  ResourceLocation texture = new ResourceLocation(IntegrationPackageCore.MODID, "textures/air.png");

    public float u1 = 0;

    public float u2 = 512;

    public float v1 = 0;

    public float v2 = 512;

    public float textureH = 512;


    public float textureW = 512;


    public void setTextureUV(
            float u1,
            float u2,
            float v1,
            float v2,
            float textureW,
            float textureH) {
        this.u1 = u1;
        this.u2 = u2;
        this.v1 = v1;
        this.v2 = v2;

        this.textureW = textureW;
        this.textureH = textureH;
    }


    public Render(Minecraft pMinecraft, PoseStack pose, MultiBufferSource.BufferSource pBufferSource) {

        // this(pMinecraft, pBufferSource);
        this.minecraft = pMinecraft;

        this.bufferSource = pBufferSource;

        GuiGraphics guiGraphics = new GuiGraphics(pMinecraft, pBufferSource);

        try {

            ReflectionUtils.set(guiGraphics, PoseStack.class, pose);
        } catch (ReflectiveOperationException ignored) {


        }


        this.guiGraphics = guiGraphics;

        this.Dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();

    }

    public Render(Minecraft pMinecraft, MultiBufferSource.BufferSource pBufferSource) {
        this.minecraft = pMinecraft;
        this.bufferSource = pBufferSource;
        this.guiGraphics = new GuiGraphics(pMinecraft, pBufferSource);

        this.Dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
    }

    public static void levelPoseStack(PoseStack poseStack, float x, float y, float z) {

        Vec3 position = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        poseStack.translate(x - position.x, y - position.y, z - position.z);


    }

    public static void bindCamera(PoseStack poseStack) {

        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());

    }


    public void fill(int pMinX, int pMinY, int pMaxX, int pMaxY, int pColor) {

        guiGraphics.fill(RenderTypes.Beacon(texture), pMinX, pMinY, pMaxX, pMaxY, 0, pColor);


    }

    public void renderTooltip(Font pFont, ItemStack pStack, int pMouseX, int pMouseY) {

        guiGraphics.renderTooltip(pFont, Screen.getTooltipFromItem(this.minecraft, pStack), pStack.getTooltipImage(), pMouseX, pMouseY);

    }


    public interface modelHandle {

        void InternalHandle(PlayerModel<AbstractClientPlayer> model);

    }


    public void renderArmor(AbstractClientPlayer player, PoseStack poseStack, EquipmentSlot slot, modelHandle modelHandlePre) {

        renderArmor(player, poseStack, slot, modelHandlePre, null);

    }


    public void renderArmor(AbstractClientPlayer player, EquipmentSlot slot, modelHandle modelHandlePre, modelHandle modelHandlePost) {

        renderArmor(player, poseStack(), slot, modelHandlePre, modelHandlePost);

    }

    public void renderPlayerItem(AbstractClientPlayer player, PoseStack poseStack, HumanoidArm hand) {


        //   ItemDisplayContext DisplayContext =( () ? : ItemDisplayContext.THIRD_PERSON_LEFT_HAND);
        if (hand == HumanoidArm.RIGHT) {
            minecraft.getItemRenderer().renderStatic(
                    player.getMainHandItem(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                    minecraft.getEntityRenderDispatcher().getPackedLightCoords(player, minecraft.getPartialTick()), 0,
                    poseStack, Minecraft.getInstance().renderBuffers().bufferSource(), player.level(), player.getId());
        } else if (hand == HumanoidArm.LEFT) {

            minecraft.getItemRenderer().renderStatic(
                    player.getMainHandItem(), ItemDisplayContext.THIRD_PERSON_LEFT_HAND,//无法使用左手
                    minecraft.getEntityRenderDispatcher().getPackedLightCoords(player, minecraft.getPartialTick()), 0,
                    poseStack, Minecraft.getInstance().renderBuffers().bufferSource(), player.level(), player.getId() + ItemDisplayContext.THIRD_PERSON_LEFT_HAND.ordinal());


        }

    }


    public void renderPlayerItem(AbstractClientPlayer player, PoseStack poseStack, HumanoidArm hand, modelHandle modelHandlePre, modelHandle modelHandlePost) {


        PlayerRenderer playerRenderer = (PlayerRenderer) Dispatcher.getRenderer(player);

        if (playerRenderer instanceof LivingEntityRenderMember c) {


            List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>> layers = c.getLayer();// (List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>>) ReflectionUtils.get("layers", playerRenderer);


            if (layers != null) {
                //System.out.println("7463464353");
                layers.forEach(item -> {

                    // System.out.println(armor);

                    if (item instanceof RenderLayerMember j) {
                        if (modelHandlePre != null) {

                            modelHandlePre.InternalHandle(item.getParentModel());

                        }
                        //  j.getRenderer() instanceof LivingEntityRenderMember r&&ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                        if (j instanceof PlayerItemInHandLayerMember h) {
                            if (hand == HumanoidArm.LEFT) {

                                h.renderPlayerItem(player, player.getOffhandItem(),
                                        ItemDisplayContext.THIRD_PERSON_LEFT_HAND, hand, poseStack, bufferSource,
                                        220 * player.level().getRawBrightness(BlockPos.containing(player.position()), 0) / 15);

                            } else if (hand == HumanoidArm.RIGHT) {

                                h.renderPlayerItem(player, player.getMainHandItem(),
                                        ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, hand, poseStack, bufferSource,
                                        minecraft.getEntityRenderDispatcher().getPackedLightCoords(player, minecraft.getPartialTick()));

                            }

                        }
                        if (modelHandlePre != null) {
                            modelHandlePre.InternalHandle(item.getParentModel());

                        }
                    }

                });


            }


        }


    }

    public void renderItem(AbstractClientPlayer player, PoseStack poseStack, HumanoidArm hand, modelHandle modelHandlePre, modelHandle modelHandlePost) {


        PlayerRenderer playerRenderer = (PlayerRenderer) Dispatcher.getRenderer(player);

        if (playerRenderer instanceof LivingEntityRenderMember c) {

            List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>> layers = c.getLayer();// (List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>>) ReflectionUtils.get("layers", playerRenderer);

            if (layers != null) {

                layers.forEach(item -> {

                    if (item instanceof RenderLayerMember j) {
                        if (modelHandlePre != null) {

                            modelHandlePre.InternalHandle(item.getParentModel());

                        }
                        if (j instanceof PlayerItemInHandLayerMember h) {
                            if (hand == HumanoidArm.LEFT) {


                                h.getItemInHandRenderer().renderItem(player, player.getOffhandItem(),
                                        ItemDisplayContext.THIRD_PERSON_LEFT_HAND, true, poseStack, bufferSource,
                                        minecraft.getEntityRenderDispatcher().getPackedLightCoords(player, minecraft.getPartialTick()));


                            } else if (hand == HumanoidArm.RIGHT) {

                                h.getItemInHandRenderer().renderItem(player, player.getMainHandItem(),
                                        ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, poseStack, bufferSource,
                                        minecraft.getEntityRenderDispatcher().getPackedLightCoords(player, minecraft.getPartialTick()));

                            }

                        }
                        if (modelHandlePre != null) {
                            modelHandlePre.InternalHandle(item.getParentModel());

                        }
                    }

                });


            }


        }


    }

    //model可修改单件盔甲动作
    public void renderArmor(AbstractClientPlayer player, PoseStack pStack, EquipmentSlot slot, modelHandle modelHandlePre, modelHandle modelHandlePost) {

        //    if (Dispatcher == null) {

        //        Dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();

        //    }
        //Player p = Minecraft.getInstance().player;

        PlayerRenderer playerRenderer = (PlayerRenderer) Dispatcher.getRenderer(player);

        if (playerRenderer instanceof LivingEntityRenderMember c) {


            List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>> layers = c.getLayer();// (List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>>) ReflectionUtils.get("layers", playerRenderer);


            if (layers != null) {
                //System.out.println("7463464353");
                layers.forEach(armor -> {

                    // System.out.println(armor);

                    if (armor instanceof RenderLayerMember j) {

                        //  j.getRenderer() instanceof LivingEntityRenderMember r&&
                        if (modelHandlePre != null) {

                            //r.setModel(model);
                            modelHandlePre.InternalHandle(armor.getParentModel());

                        }

                        if (j instanceof HumanoidArmorLayerMember h) {

                            HumanoidModel<?> m = h.$getArmorModel(slot);

                            // System.out.println("3464353");

                            //     m.body.xRot = 1.654F;
                            //     //   m.body.zRot = 0;
                            //     armor.getParentModel().body.xRot = 0.5F;//此值会覆盖其上的值^  (在model传入$renderArmorPiece后覆盖)
                            //  armor.getParentModel() = model;
                            //System.out.println(player.level().getRawBrightness(BlockPos.containing(player.position()), 0));


                            h.$renderArmorPiece(pStack,
                                    bufferSource,
                                    player,
                                    slot,
                                    minecraft.getEntityRenderDispatcher().getPackedLightCoords(player, minecraft.getPartialTick()),
                                    m);
//
                            //220 * player.level().getRawBrightness(BlockPos.containing(player.position()), 0) / 15
                        }
                        if (modelHandlePost != null) {

                            modelHandlePost.InternalHandle(armor.getParentModel());

                        }


                    }

                });


            }


        }


    }

    public PoseStack poseStack() {


        return guiGraphics.pose();
        //    guiGraphics.drawString(minecraft.font,text,);

    }


    public void fill(PoseStack poseStack, Vector3d[] pos, float[] color) {



        ResourceLocation trailTexture = texture;



        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderTypes.TranslucentEntity(trailTexture));
        PoseStack.Pose pose = poseStack.last();
        Matrix4f worldMatrix = pose.pose();       // 世界坐标变换矩阵
        Matrix3f normalMatrix = pose.normal();    // 法线矩阵（光照计算）

//顺时针

        addVertex(vertexConsumer, worldMatrix, normalMatrix, pos[0], color, u1 / textureW, v1 / textureH);//左上
        addVertex(vertexConsumer, worldMatrix, normalMatrix, pos[1], color, u1 / textureW, v2 / textureH);//右上
        addVertex(vertexConsumer, worldMatrix, normalMatrix, pos[2], color, u2 / textureW, v2 / textureH);
        addVertex(vertexConsumer, worldMatrix, normalMatrix, pos[3], color, u2 / textureW, v1 / textureH);//左下


    }


    public void renderTrace(ArrayList<Matrix4f> TraceList, MultiBufferSource bufferSource, PoseStack poseStack, float[] color, float r1, float r2) {


        //  System.out.println(TraceList.size());
        // System.out.println(TraceList);

        if (TraceList.size() < 2) {
            return; // 轨迹点不足时直接返回
        }


        // 1. 选择半透明渲染类型（适合轨迹效果，可替换为你的发光类型）
        ResourceLocation trailTexture = texture;
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderTypes.Beacon(trailTexture));

        // 2. 遍历轨迹点，绘制连续四边形
        for (int i = 1; i < TraceList.size(); i++) {
            Matrix4f currMatrix = TraceList.get(i);       // 当前帧矩阵
            Matrix4f prevMatrix = TraceList.get(i - 1);   // 上一帧矩阵

            if (currMatrix == null || prevMatrix == null) {
                continue; // 跳过空矩阵
            }
            //System.out.println(r1);

            //System.out.println(r2);
            // 3. 从矩阵中获取轨迹的前后位置（左右边界，控制轨迹宽度）
            Vec3 prevLeft = getPos(prevMatrix, r1);  // 上一帧左边界
            Vec3 prevRight = getPos(prevMatrix, r2); // 上一帧右边界
            Vec3 currLeft = getPos(currMatrix, r1);  // 当前帧左边界
            Vec3 currRight = getPos(currMatrix, r2); // 当前帧右边界


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
            // RenderSystem.setShaderColor(
            //         color[0],
            //         color[1],
            //         color[2],
            //         color[3]);
            // 6. 绘制四边形（按顺时针顺序添加顶点，避免背面剔除）


            // 顶点1：上一帧右边界
            {
                vertexConsumer.vertex(worldMatrix,
                                (float) prevRight.x, (float) prevRight.y, (float) prevRight.z)//<------------
                        .color(color[0],
                                color[1],
                                color[2],
                                color[3]) // 白色半透明
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
                        .color(color[0],
                                color[1],
                                color[2],
                                color[3])
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
                        .color(color[0],
                                color[1],
                                color[2],
                                color[3])
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
                        .color(color[0],
                                color[1],
                                color[2],
                                color[3])
                        .uv(uStart, vTop)
                        .overlayCoords(NO_OVERLAY)
                        .uv2(15728880)
                        .normal(normalMatrix, 0.0f, 1.0f, 0.0f)
                        .endVertex();
            }


            //  RenderSystem.setShaderColor(1, 1, 1, 1);
        }


    }

    public void renderTraceLine(PoseStack poseStack, List<Vector3d> tracePoints, float[] color,
                                double lineWidth, ResourceLocation r) {

        renderTraceLine(poseStack, bufferSource,
                tracePoints, color,
                lineWidth, RenderTypes.Beacon(r));
    }

    public void setTexture(ResourceLocation rl) {

        texture = rl;

    }


    public static final int NO_OVERLAY = OverlayTexture.NO_OVERLAY;
    public static final int FULL_BRIGHTNESS = 15728880; // 0xF000F0

    /**
     * 渲染轨迹线
     *
     * @param poseStack   矩阵堆栈（用于坐标变换）
     * @param buffer      顶点缓冲区
     * @param tracePoints 轨迹点列表（按时间顺序排列）
     * @param color       轨迹颜色（RGBA，0~1范围）
     * @param lineWidth   轨迹宽度（单位：方块）
     * @param renderType  渲染类型（指定纹理等）
     */
    public void renderTraceLine(PoseStack poseStack, MultiBufferSource buffer,
                                List<Vector3d> tracePoints, float[] color,
                                double lineWidth, RenderType renderType) {
        if (tracePoints.size() < 2) {
            return; // 至少需要2个点才能形成线段
        }

        // 获取当前矩阵状态（世界坐标矩阵和法线矩阵）
        PoseStack.Pose pose = poseStack.last();
        Matrix4f worldMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        // 获取顶点消费者（根据渲染类型获取对应的缓冲区）
        VertexConsumer vertexConsumer = buffer.getBuffer(renderType);

        // 启用混合（半透明效果）
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // 计算总段数（用于纹理坐标）
        int totalSegments = tracePoints.size() - 1;

        // 遍历轨迹点，绘制每一段四边形
        for (int i = 1; i < tracePoints.size(); i++) {
            Vector3d currentPoint = tracePoints.get(i);
            Vector3d prevPoint = tracePoints.get(i - 1);

            // 1. 计算线段方向向量
            Vector3d direction = new Vector3d(
                    currentPoint.x - prevPoint.x,
                    currentPoint.y - prevPoint.y,
                    currentPoint.z - prevPoint.z
            ).normalize();

            // 2. 计算垂直于轨迹的左右偏移方向（用于形成四边形宽度）
            // 这里使用向上向量(0,1,0)叉乘方向向量，得到横向偏移
            Vector3d right = new Vector3d(
                    -direction.z, 0, direction.x // 简化的横向垂直向量
            ).normalize().mul(lineWidth / 2); // 偏移距离为线宽的一半

            // 3. 计算当前段的四个顶点（上一帧左右 + 当前帧左右）
            Vector3d prevLeft = new Vector3d(prevPoint).sub(right);
            Vector3d prevRight = new Vector3d(prevPoint).add(right);
            Vector3d currentLeft = new Vector3d(currentPoint).sub(right);
            Vector3d currentRight = new Vector3d(currentPoint).add(right);

            // 4. 计算纹理坐标（沿轨迹分布）
            float uStart = (i - 1) / (float) totalSegments;
            float uEnd = i / (float) totalSegments;
            float vTop = 0.0f;
            float vBottom = 1.0f;

            // 5. 绘制四边形（按顺时针顺序添加4个顶点）
            // 顶点1：上一帧右边界
            addVertex(vertexConsumer, worldMatrix, normalMatrix,
                    prevRight, color, uStart, vBottom);

            // 顶点2：当前帧右边界
            addVertex(vertexConsumer, worldMatrix, normalMatrix,
                    currentRight, color, uEnd, vBottom);

            // 顶点3：当前帧左边界
            addVertex(vertexConsumer, worldMatrix, normalMatrix,
                    currentLeft, color, uEnd, vTop);

            // 顶点4：上一帧左边界
            addVertex(vertexConsumer, worldMatrix, normalMatrix,
                    prevLeft, color, uStart, vTop);
        }

        // 关闭混合（恢复默认状态）
        RenderSystem.disableBlend();
    }


    /**
     * 添加单个顶点到缓冲区
     */
    private void addVertex(VertexConsumer consumer, Matrix4f worldMatrix, Matrix3f normalMatrix,
                           Vector3d pos, float[] color, float u, float v) {
        consumer.vertex(worldMatrix,
                        (float) pos.x, (float) pos.y, (float) pos.z)
                .color(RenderSystem.getShaderColor()[0],
                        RenderSystem.getShaderColor()[1],
                        RenderSystem.getShaderColor()[2],
                        RenderSystem.getShaderColor()[3]) // RGBA颜色
                .uv(u, v) // 纹理坐标
                .overlayCoords(NO_OVERLAY)
                .uv2(FULL_BRIGHTNESS) // 最大亮度
                .normal(normalMatrix, 0.0f, 1.0f, 0.0f) // 法线方向（向上）
                .endVertex();
    }

    public static Vec3 getPos(Matrix4f matrix4f, float r) {


        Vector4f vec = new Vector4f(-0, -0, -r, 1);
        vec.mul(matrix4f);
        vec.rotateY((float) Math.PI);

        return new Vec3(vec.x(), vec.y(), vec.z());//Vec3(vec1.x(), vec1.y(), vec1.z());


    }

    public void renderLaserV(float[] poss1, float[] poss2, float r) {

        poseStack().pushPose();
        Vector3d npos1 = new Vector3d(poss1[0] - poss2[0], poss1[1] - poss2[1], poss1[2] - poss2[2]);

        Vector3d npos2 = new Vector3d(0, 0, 0);

        Vector3d ePos1;
        Vector3d ePos2;

        ePos1 = new Vector3d(0, -0.5, 0);
        ePos2 = new Vector3d(0, 0.5, 0);

        //   Vector3d ePos3 = new Vector3d(0.1, 0, 0);
//
        //   Vector3d ePos4 = new Vector3d(-0.1, 0, 0);

        float levelDistance = (float) Math.pow((npos1.x - npos2.x) * (npos1.x - npos2.x) + (npos1.y - npos2.y) * (npos1.y - npos2.y), 0.5);
        float distance = (float) Math.pow((npos1.x - npos2.x) * (npos1.x - npos2.x) + (npos1.z - npos2.z) * (npos1.z - npos2.z) + (npos1.y - npos2.y) * (npos1.y - npos2.y), 0.5);

        float horizontalAngle = (float) ((npos1.x - npos2.x) / levelDistance);

        //  System.out.println(horizontalAngle);

        if ((float) Math.min(npos2.z, npos1.z) < 0) {
            horizontalAngle = (float) (Math.PI - (Math.asin(horizontalAngle)));
        } else {
            horizontalAngle = (float) ((Math.asin(horizontalAngle)));
        }
        //  System.out.println(horizontalAngle / Math.PI + "pi");
        //
        //   ePos1.rotateX((npos1.z - npos2.z)/distance);
        //   ePos2.rotateX((npos1.z - npos2.z)/distance);
        ePos1.rotateX(horizontalAngle);
        ePos2.rotateX(horizontalAngle);


        float x = (poss1[0] - poss2[0]) / distance;
        float z = (poss1[2] - poss2[2]) / distance;
        float y = (poss1[1] - poss2[1]) / distance;

        //  System.out.println(x+"  "+y+"  "+z);
        // System.out.println((poss1[1] - poss2[1]));
        // System.out.println(levelDistance);
        // ============================ ePos1.rotateAxis(90, x, y, z);
        // ============================ ePos2.rotateAxis(90, x, y, z);


        Vector3d[] a = new Vector3d[]{

                new Vector3d(npos1.x + ePos1.x, npos1.y + ePos1.y, npos1.z + ePos1.z),
                new Vector3d(npos1.x + ePos2.x, npos1.y + ePos2.y, npos1.z + ePos2.z),
                new Vector3d(npos2.x + ePos2.x, npos2.y + ePos2.y, npos2.z + ePos2.z),
                new Vector3d(npos2.x + ePos1.x, npos2.y + ePos1.y, npos2.z + ePos1.z),

        };


        poseStack().translate(poss2[0], poss2[1], poss2[2]);

        fill(poseStack(), a, new float[]{1, 1, 1, 1});

        poseStack().popPose();
    }

    public void renderLaser(float[] poss1, float[] poss2, float r, float slanting) {





        poseStack().pushPose();
        Vector3d npos1 = new Vector3d(poss1[0] - poss2[0], poss1[1] - poss2[1], poss1[2] - poss2[2]);

        Vector3d npos2 = new Vector3d(0, 0, 0);

        Vector3d ePos1;
        Vector3d ePos2;

        float levelDistance = (float) Math.pow((npos1.x - npos2.x) * (npos1.x - npos2.x) + (npos1.z - npos2.z) * (npos1.z - npos2.z), 0.5);
        float distance = (float) Math.pow((npos1.x - npos2.x) * (npos1.x - npos2.x) + (npos1.z - npos2.z) * (npos1.z - npos2.z) + (npos1.y - npos2.y) * (npos1.y - npos2.y), 0.5);

        float horizontalAngle = (float) ((npos1.x - npos2.x) / levelDistance);


        if ((float) Math.min(npos2.x, npos1.x) < 0) {
            horizontalAngle = (float) (Math.PI - (Math.asin(horizontalAngle)));
        } else {
            horizontalAngle = (float) ((Math.asin(horizontalAngle)));
        }
        if ((npos1.x > 0 && npos1.z > 0) || (npos1.x < 0 && npos1.z < 0)) {

            ePos1 = new Vector3d(-r * Math.cos(Math.PI - horizontalAngle), 0, -r * Math.sin(Math.PI - horizontalAngle));
            ePos2 = new Vector3d(r * Math.cos(Math.PI - horizontalAngle), 0, r * Math.sin(Math.PI - horizontalAngle));
        } else {
            ePos1 = new Vector3d(-r * Math.cos(horizontalAngle), 0, -r * Math.sin(horizontalAngle));
            ePos2 = new Vector3d(r * Math.cos(horizontalAngle), 0, r * Math.sin(horizontalAngle));


        }

        Quaternionf q = Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation();


        float x = (poss1[0] - poss2[0]) / distance;
        float z = (poss1[2] - poss2[2]) / distance;
        float y = (poss1[1] - poss2[1]) / distance;


        ePos1.rotateAxis(slanting, x, y, z);

        ePos2.rotateAxis(slanting, x, y, z);

        Vector3d[] a = new Vector3d[]{

                new Vector3d(npos1.x + ePos1.x, npos1.y + ePos1.y, npos1.z + ePos1.z),
                new Vector3d(npos1.x + ePos2.x, npos1.y + ePos2.y, npos1.z + ePos2.z),
                new Vector3d(npos2.x + ePos2.x, npos2.y + ePos2.y, npos2.z + ePos2.z),
                new Vector3d(npos2.x + ePos1.x, npos2.y + ePos1.y, npos2.z + ePos1.z),

        };

        poseStack().translate(poss2[0], poss2[1], poss2[2]);
     //   System.out.println(texture+"4554");
        fill(poseStack(), a, new float[]{1, 1, 1, 1});

        poseStack().popPose();
    }

    public void renderLaser(float[] poss1, float[] poss2, float r) {

        poseStack().pushPose();
        Vector3d npos1 = new Vector3d(poss1[0] - poss2[0], poss1[1] - poss2[1], poss1[2] - poss2[2]);

        Vector3d npos2 = new Vector3d(0, 0, 0);


        //Vector3d.distance()

        Vector3d ePos1;
        Vector3d ePos2;
        ePos1 = new Vector3d(-0.5, 0, 0);
        ePos2 = new Vector3d(0.5, 0, 0);
        //   Vector3d ePos3 = new Vector3d(0.1, 0, 0);
        //   Vector3d ePos4 = new Vector3d(-0.1, 0, 0);
        float levelDistance = (float) Math.pow((npos1.x - npos2.x) * (npos1.x - npos2.x) + (npos1.z - npos2.z) * (npos1.z - npos2.z), 0.5);
        float distance = (float) Math.pow((npos1.x - npos2.x) * (npos1.x - npos2.x) + (npos1.z - npos2.z) * (npos1.z - npos2.z) + (npos1.y - npos2.y) * (npos1.y - npos2.y), 0.5);

        float horizontalAngle = (float) ((npos1.x - npos2.x) / levelDistance);

        //  System.out.println(horizontalAngle);

        if ((float) Math.min(npos2.x, npos1.x) < 0) {
            horizontalAngle = (float) (Math.PI - (Math.asin(horizontalAngle)));
        } else {
            horizontalAngle = (float) ((Math.asin(horizontalAngle)));
        }

        Quaternionf q = Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation();

        ePos1.rotateY(horizontalAngle);
        ePos2.rotateY(horizontalAngle);


        //  float x = (poss1[0] - poss2[0]) / distance;
        //  float z = (poss1[2] - poss2[2]) / distance;
        //  float y = (poss1[1] - poss2[1]) / distance;


        Vector3d[] a = new Vector3d[]{

                new Vector3d(npos1.x + ePos1.x, npos1.y + ePos1.y, npos1.z + ePos1.z),
                new Vector3d(npos1.x + ePos2.x, npos1.y + ePos2.y, npos1.z + ePos2.z),
                new Vector3d(npos2.x + ePos2.x, npos2.y + ePos2.y, npos2.z + ePos2.z),
                new Vector3d(npos2.x + ePos1.x, npos2.y + ePos1.y, npos2.z + ePos1.z),

        };


        poseStack().translate(poss2[0], poss2[1], poss2[2]);

     fill(poseStack(), a, new float[]{1, 1, 1, 1});

        poseStack().popPose();
    }
    /**
     * 激光渲染工具类
     * 特性：连接两点的长方体激光，自发光、半透明、全角度可见
     */

    // 常量：无叠加层、最大亮度（自发光）

}

// Field newPose = GuiGraphics.class.getDeclaredField("pose");

//newPose.setAccessible(true);

//  newPose.set(this.guiGraphics, pose);
// 使用反射获取私有字段
// 设置可访问性为true，绕过Java的访问控制
// 获取私有字段的值
// String value = (String) privateField.get(this.guiGraphics);
//  System.out.println("获取到的私有字段值: " + value);
// 修改私有字段的值
//    System.out.println("修改后的私有字段值: " + privateField.get(this.guiGraphics));
// p.sendSystemMessage(Component.literal(playerRenderer.toString()));
//  p.sendSystemMessage(Component.literal("a.toString()"));
//p.sendSy
//
//                            // m.leftArmPose
//
//
//                           // p.sendSystemMessage(Component.literal(m.body.xRot + "   " + a.getParentModel().body.xRot  + "   " + "aaa"));stemMessage(Component.literal(c.getLayer().toString()));
//  p.sendSystemMessage(Component.literal("aaaa"));
//   m.leftArm.zRot = 0;
//   m.leftArm.xRot = 0;
//   m.leftArm.yRot = 0;
//m  //   p.sendSystemMessage(Component.literal(a.toString()));
//   m.body.yRot = 0;
/*
            PlayerRenderer playerRenderer = (PlayerRenderer) Dispatcher.getRenderer(Minecraft.getInstance().player);


            List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>> layers = (List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>>) ReflectionUtils.get("layers", playerRenderer);

            if (layers != null) {


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


                System.out.println(getArmorModelMethod.invoke(a, EquipmentSlot.CHEST));
                // 4. 调用方法，传入对应参数
                renderArmorPieceMethod.invoke(
                        a,
                        pStack,
                        bufferSource,
                        Minecraft.getInstance().player,
                        EquipmentSlot.CHEST,
                        100,
                        (HumanoidModel<AbstractClientPlayer>)
                                getArmorModelMethod.invoke(a, slot)

                );




        }

*/
