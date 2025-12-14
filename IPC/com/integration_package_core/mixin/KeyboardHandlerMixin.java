package com.integration_package_core.mixin;

import com.integration_package_core.event.Event.KeyClickEvent;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {


//  @ModifyArg(/* ISTORE 12 */
//          method = "keyPress",
//             index =3,
//         // ordinal = 3,
//          at = @At(
//                  value = "INVOKE",
//                      target = "Lnet/minecraft/client/Minecraft;getWindow()Lcom/mojang/blaze3d/platform/Window;"
//          )
//       //   argsOnly = true
//  )

    @ModifyVariable(//修改变量  包含目标方法形参中的变量和方法中的局部变量
            method = "keyPress",
            at = @At(
                    //  value = "STORE",//在局部变量被赋值时注入 需指定局部变量索引
                    value = "HEAD",//在形参被赋值时注入
                    ordinal = 0,//在目标变量的第n次访问后注入(一般为 value = "STORE"时才有效)
                    target = "Lnet/minecraft/client/Minecraft;getWindow()Lcom/mojang/blaze3d/platform/Window;"
            ),
            ordinal = 2,//同类型(该mixin方法的类型)变量的排序索引
            argsOnly = true//排除非形参的变量
    )
    private int b(int pAction) {

        //     System.out.println('b' + "  " + pAction);
        if (KeyClickEvent.resetAction) {

            return 0; // 修改后的值
        }

        return pAction; // 不修改其他按键
    }


    @Inject(
            method = "keyPress",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;getWindow()Lcom/mojang/blaze3d/platform/Window;"
            ),
            cancellable = true//可取消事件(使用forge提供的效果)

    )
    private void a(long pWindowPointer, int pKey, int pScanCode, int pAction, int pModifiers, CallbackInfo ci) {            // 原方法的第一个参数：实体对象


        //   System.out.println('a' + "  " + pAction);

        if (KeyClickEvent.onKeyButtonPre(pKey, pScanCode, pAction, pModifiers)) ci.cancel();//可取消事件


        // System.out.println(pAction);


    }

}
