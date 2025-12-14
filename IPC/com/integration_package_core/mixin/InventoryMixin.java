package com.integration_package_core.mixin;

import com.integration_package_core.event.Event.ItemTransferEvent;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class InventoryMixin implements Container {

    @ModifyVariable(//修改变量  包含目标方法形参中的变量和方法中的局部变量
            method = "hurtArmor",
            at = @At(
                    //  value = "STORE",//在局部变量被赋值时注入 需指定局部变量索引
                    value = "HEAD",//在形参被赋值时注入
                    ordinal = 0//在目标变量的第n次访问后注入(一般为 value = "STORE"时才有效)
                 //   target = "Lnet/minecraft/client/Minecraft;getWindow()Lcom/mojang/blaze3d/platform/Window;"
            ),
            ordinal = 0,//同类型(该mixin方法的类型)变量的排序索引
            argsOnly = true//排除非形参的变量
    )
    private float a(float value){//盔甲受到耐久伤害永远为1


        return 1;
    }


    @Inject(
            method = "add(ILnet/minecraft/world/item/ItemStack;)Z",  at = @At(value = "HEAD")// 注入点：方法开头

    )
    private  void b(int pSlot, ItemStack pStack, CallbackInfoReturnable<Boolean> cir){


    //    ItemTransferEvent.onItemTransfer(((Inventory)(Container)this).player,pStack);

     //   System.out.println(346352);


    }

    @Inject(
            method = "setPickedItem",  at = @At(value = "HEAD")// 注入点：方法开头

    )
    private  void c(ItemStack pStack, CallbackInfo ci){


   //    ItemCollectEvent.onItemTransfer(((Inventory)(Container)this).player,pStack);




    }









}
