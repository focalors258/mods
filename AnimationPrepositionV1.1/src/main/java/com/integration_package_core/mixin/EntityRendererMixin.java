package com.integration_package_core.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin <T extends Entity> {



    @Inject(
            method = "render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "HEAD")// 注入点：方法开头

    )
    private void injectRenderHead(
            T par1, float par2, float par3, PoseStack par4, MultiBufferSource par5, int par6, CallbackInfo ci  // 原方法的第一个参数：实体对象
            // 原方法的第二个参数：实体偏航角
            // 原方法的第三个参数：部分tick（渲染帧插值）
            // 原方法的第四个参数：渲染矩阵栈
            // 原方法的第五个参数：顶点缓冲源
            // 原方法的第六个参数：打包的光照信息
            // Mixin回调信息（用于控制原方法）
    ) {

        RenderSystem.setShaderColor(1,1,1,1);

    }























}
