package com.integration_package_core.mixin;

import com.integration_package_core.mixinTool.PlayerExpand;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@OnlyIn(Dist.CLIENT)
@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends Input {



//  @Inject(
//          method = "tick",
//          at = @At(
//                  value = "HEAD"

//          ),
//          cancellable = true//可取消事件(使用forge提供的效果)

//  )
//  public void a(boolean pIsSneaking, float pSneakingSpeedMultiplier, CallbackInfo ci){


//      if (Minecraft.getInstance().player != null && ((PlayerExpand) Minecraft.getInstance().player).isStopMove())
//        //  ci.cancel();
//  }







}
