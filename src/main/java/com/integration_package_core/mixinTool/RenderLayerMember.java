package com.integration_package_core.mixinTool;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.Entity;

public interface RenderLayerMember<T extends Entity,M extends EntityModel<T>>{


    public RenderLayerParent<T, M> getRenderer();
}
