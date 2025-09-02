package com.integration_package_core.mixin;

import com.integration_package_core.mixinTool.PlayerItemInHandLayerMember;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerItemInHandLayer.class)
public abstract  class PlayerItemInHandLayerMixin<T extends Player, M extends EntityModel<T> & ArmedModel & HeadedModel> extends ItemInHandLayer<T, M> implements PlayerItemInHandLayerMember {
    @Shadow protected abstract void renderArmWithItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight);

    @Shadow @Final private ItemInHandRenderer itemInHandRenderer;

    public PlayerItemInHandLayerMixin(RenderLayerParent<T, M> pRenderer, ItemInHandRenderer pItemInHandRenderer) {
        super(pRenderer, pItemInHandRenderer);
    }



    public ItemInHandRenderer getItemInHandRenderer(){

        return  this.itemInHandRenderer;


    };





    @Override
    public void renderPlayerItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {

      //  this.renderArmWithItem();


        this.renderArmWithItem(pLivingEntity,pItemStack,pDisplayContext,pArm,pPoseStack,pBuffer,pPackedLight);



    }
}
