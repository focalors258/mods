package com.chestsearch.tool;

import com.chestsearch.ChestSearch;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.Event;

@OnlyIn(Dist.CLIENT)
public class R {


    private final MultiBufferSource.BufferSource bufferSource;

    private final GuiGraphics guiGraphics;

    public PoseStack pose;
    private static final  Minecraft minecraft = Minecraft.getInstance();

    public R(PoseStack poseStack, MultiBufferSource.BufferSource pBufferSource) {


        this.bufferSource = pBufferSource;

        this.pose = poseStack;

        this.guiGraphics = new GuiGraphics(minecraft, pBufferSource);

    }


    public void renderTooltip(Font pFont, ItemStack pStack, int pMouseX, int pMouseY) {

        guiGraphics.renderTooltip(pFont, Screen.getTooltipFromItem(this.minecraft, pStack), pStack.getTooltipImage(), pMouseX, pMouseY);

    }

    public static void renderTexture(Event event, String mod, String location, int pX, int pY, float pUOffset, float pVOffset, int pWidth, int pHeight, int pTextureWidth, int pTextureHeight, PoseStack poseStack, float lX, float lY, float lZ) {

        poseStack.pushPose();


        poseStack.translate(lX, lY, lZ);
        if (event instanceof ScreenEvent.Render e) {
            e.getGuiGraphics().blit(new ResourceLocation(mod, location), pX, pY, pUOffset, pVOffset, pWidth, pHeight, pTextureWidth, pTextureHeight);
        } else if (event instanceof RenderGuiEvent e) {

            e.getGuiGraphics().blit(new ResourceLocation(mod, location), pX, pY, pUOffset, pVOffset, pWidth, pHeight, pTextureWidth, pTextureHeight);


        }
        poseStack.popPose();

    }

    public static float getWidth(){

       return minecraft.getWindow().getGuiScaledWidth();


    }

    public static float getHeight(){

        return minecraft.getWindow().getGuiScaledHeight();


    }




}
