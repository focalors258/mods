package com.integration_package_core.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import software.bernie.geckolib.cache.object.GeoBone;

public class Animation {


    public static int LinkType_SwitchingEntity = 0;

    public static int LinkType_ForcedPlay = 1;

    public static int LinkType_Common = 2;



    public static int PlayType_Loop = 0;

    public static int PlayType_Play = 1;

    public static int PlayType_PlayAndHold= 2;


    public static void translateRotateGeckolib(GeoBone bone, PoseStack matrixStackIn) {
        GeoBone parent = bone.getParent();
        if(parent != null) {
            matrixStackIn.translate((double) ((bone.getPivotX() - parent.getPivotX() - bone.getPosX()) / 16.0F),
                    (double) ((bone.getPivotY() - parent.getPivotY() + bone.getPosY()) / 16.0F),
                    (double) ((bone.getPivotZ() - parent.getPivotZ() + bone.getPosZ()) / 16.0F));
        }
        else {
            matrixStackIn.translate((double) ((bone.getPivotX() - bone.getPosX()) / 16.0F),
                    (double) ((bone.getPivotY() + bone.getPosY()) / 16.0F),
                    (double) ((bone.getPivotZ() + bone.getPosZ()) / 16.0F));
        }

        if (bone.getRotZ() != 0.0F) {
            matrixStackIn.mulPose(Axis.ZP.rotation(bone.getRotZ()));
        }

        if (bone.getRotY() != 0.0F) {
            matrixStackIn.mulPose(Axis.YP.rotation(bone.getRotY()));
        }

        if (bone.getRotX() != 0.0F) {
            matrixStackIn.mulPose(Axis.XP.rotation(bone.getRotX()));
        }

        matrixStackIn.scale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
    }

    public static void matrixStackFromModel(PoseStack matrixStack, GeoBone geoBone) {
        GeoBone parent = geoBone.getParent();
        if (parent != null) matrixStackFromModel(matrixStack, parent);
        translateRotateGeckolib(geoBone, matrixStack);
    }







}
