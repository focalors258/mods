package com.integration_package_core.mixinTool;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface HumanoidArmorLayerMember<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {


    public List<RenderLayer<T, M>> getLayer();


    public void $renderArmorPiece(PoseStack pPoseStack, MultiBufferSource pBuffer, T pLivingEntity, EquipmentSlot pSlot, int pPackedLight, A pModel);


    public A $getArmorModel(EquipmentSlot pSlot);


 //   public RenderLayerParent<T, M> getRenderer();


}