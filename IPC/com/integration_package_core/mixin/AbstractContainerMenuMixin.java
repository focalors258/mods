package com.integration_package_core.mixin;

import com.integration_package_core.event.Event.ItemTransferEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {


    @Shadow @Final public NonNullList<Slot> slots;

    @Inject(
            method = "doClick",
            at = @At(value = "TAIL")// 注入点：方法尾部

    )
    private  void a(int pSlotId, int pButton, ClickType pClickType, Player pPlayer, CallbackInfo ci){

        System.out.println(Minecraft.getInstance().screen);


if (pSlotId<this.slots.size()&&pSlotId>=0){
        ItemTransferEvent.onItemTransfer(pPlayer,this.slots.get(pSlotId).getItem(),pSlotId);
}

    }






















}
