package com.integration_package_core.mixinTool;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;

import java.util.List;

public interface LivingEntityRenderMember<T extends Entity,M extends EntityModel<T>> {



    public List<RenderLayer<T, M>> getLayer();

    public void setModel(M model);










}
