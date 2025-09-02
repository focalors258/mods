package com.integration_package_core.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraft.client.gui.screens.inventory

@OnlyIn(Dist.CLIENT)
public class CustomizeScreen  extends AbstractContainerScreen<CustomizeMenu> {
    public CustomizeScreen(CustomizeMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }


    protected void renderErrorIcon(GuiGraphics pGuiGraphics, int pX, int pY) {

    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

super.render(pGuiGraphics,pMouseX,pMouseY,pPartialTick);

        pGuiGraphics.fill(90, 90,  160,  160, -2130706433);

        renderBg(pGuiGraphics,pPartialTick,pMouseX,pMouseY);

    }



    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {



        pGuiGraphics.fill(0,0,0,0,0,0);









    }
}
