package com.integration_package_core.mixin;


import com.integration_package_core.mixinTool.HumanoidArmorLayerMember;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M>  implements HumanoidArmorLayerMember<T,M,A> {
    @Shadow protected abstract void renderArmorPiece(PoseStack pPoseStack, MultiBufferSource pBuffer, T pLivingEntity, EquipmentSlot pSlot, int pPackedLight, A pModel);

    @Shadow protected abstract A getArmorModel(EquipmentSlot pSlot);

    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    public A $getArmorModel(EquipmentSlot pSlot){


        return this.getArmorModel(pSlot);


    };


  //public RenderLayerParent<T, M> getRenderer(){
  //
  //    if (this instanceof RenderLayer a)
  //    return this.
  //
  //
  //
  //
  //
  //};


    public void $renderArmorPiece(PoseStack pPoseStack, MultiBufferSource pBuffer, T pLivingEntity, EquipmentSlot pSlot, int pPackedLight, A pModel){


        this.renderArmorPiece(pPoseStack,pBuffer,pLivingEntity,pSlot,pPackedLight,pModel);


    };

















}
