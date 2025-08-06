package com.integration_package_core.tool;

import com.integration_package_core.mixin.RenderLayerMixin;
import com.integration_package_core.mixinTool.HumanoidArmorLayerMember;
import com.integration_package_core.mixinTool.LivingEntityRenderMember;
import com.integration_package_core.mixinTool.PlayerItemInHandLayerMember;
import com.integration_package_core.mixinTool.RenderLayerMember;
import com.integration_package_core.util.Network.NetworkEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.earlydisplay.RenderElement;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class Render {

    public final Minecraft minecraft;

    private final MultiBufferSource.BufferSource bufferSource;

    public final GuiGraphics guiGraphics;

    public EntityRenderDispatcher Dispatcher;//(EntityRenderDispatcher)

    public static Render of() {

        return new Render(Minecraft.getInstance(), Minecraft.getInstance().renderBuffers().bufferSource());
    }

    public static Render of(PoseStack poseStack) {

        return new Render(Minecraft.getInstance(),poseStack, Minecraft.getInstance().renderBuffers().bufferSource());
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

    public static void levelPoseStack(PoseStack poseStack,float x,float y,float z) {

        Vec3 position = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        poseStack.translate(x-position.x, y-position.y, z-position.z);


    }
    public static void bindCamera(PoseStack poseStack){

        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());

    }



    public void fill(int pMinX, int pMinY, int pMaxX, int pMaxY, int pColor) {

        guiGraphics.fill(pMinX, pMinY, pMaxX, pMaxY, 0, pColor);


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

    public void renderPlayerItem(AbstractClientPlayer player,PoseStack poseStack ,HumanoidArm hand) {


        PlayerRenderer playerRenderer = (PlayerRenderer) Dispatcher.getRenderer(player);

        if (playerRenderer instanceof LivingEntityRenderMember c) {


            List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>> layers = c.getLayer();// (List<RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>>) ReflectionUtils.get("layers", playerRenderer);


            if (layers != null) {
                //System.out.println("7463464353");
                layers.forEach(item -> {

                    // System.out.println(armor);

                    if (item instanceof RenderLayerMember j) {

                        //  j.getRenderer() instanceof LivingEntityRenderMember r&&ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                        if (j instanceof PlayerItemInHandLayerMember h) {
                            if (hand == HumanoidArm.LEFT) {

                                h.renderPlayerItem(player, player.getOffhandItem(),
                                        ItemDisplayContext.THIRD_PERSON_LEFT_HAND, hand, poseStack, bufferSource,
                                        220*player.level().getRawBrightness(BlockPos.containing(player.position()), 0)/15);

                            } else if (hand == HumanoidArm.RIGHT) {

                               h.renderPlayerItem(player, player.getMainHandItem(),
                                       ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, hand, poseStack, bufferSource,
                                       220*player.level().getRawBrightness(BlockPos.containing(player.position()), 0)/15);

                            }

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
                                    220 * player.level().getRawBrightness(BlockPos.containing(player.position()), 0)/15,
                                    m);

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
