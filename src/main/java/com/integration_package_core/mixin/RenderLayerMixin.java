package com.integration_package_core.mixin;

import com.integration_package_core.mixinTool.RenderLayerMember;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(RenderLayer.class)
public abstract class RenderLayerMixin<T extends Entity, M extends EntityModel<T>>implements RenderLayerParent<T,M> , RenderLayerMember<T,M> {


    @Shadow @Final private RenderLayerParent<T, M> renderer;


    public RenderLayerParent<T, M> getRenderer(){

        return this.renderer;

    };





}
