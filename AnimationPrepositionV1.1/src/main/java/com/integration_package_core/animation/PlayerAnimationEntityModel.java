package com.integration_package_core.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import com.integration_package_core.IntegrationPackageCore;


public class PlayerAnimationEntityModel extends GeoModel<PlayerAnimationEntity> {


    // private static final ResourceLocation MODEL = new ResourceLocation(IntegrationPackageCore.MODID, "geo/patriot.geo.json");
    // public static final ResourceLocation TEXTURE = //new ResourceLocation(IntegrationPackageCore.MODID, "textures/entity/patriot/patriot.png");
    // private static final ResourceLocation ANIMATION = new ResourceLocation(IntegrationPackageCore.MODID, "animations/patriot.animation.json");
    public static int tick;

    public void setCustomAnimations(PlayerAnimationEntity entity, long instanceId, AnimationState<PlayerAnimationEntity> animationState) {


        super.setCustomAnimations(entity, instanceId, animationState);


        //  EntityModelData extraData = (EntityModelData) animationState.getExtraData().get(DataTickets.ENTITY_MODEL_DATA);


   entity.head = ((GeoBone) this.getAnimationProcessor().getBone("Head")).getModelSpaceMatrix();
   entity.chest = ((GeoBone) this.getAnimationProcessor().getBone("UpperBody")).getModelSpaceMatrix();
 //  entity.leftHand = ((GeoBone) this.getAnimationProcessor().getBone("LeftHand")).getModelSpaceMatrix();
  /// entity.rightHand = ((GeoBone) this.getAnimationProcessor().getBone("RightHand")).getModelSpaceMatrix();
   entity.buttock = ((GeoBone) this.getAnimationProcessor().getBone("UpBody")).getModelSpaceMatrix();
   entity.leftFoot = (((GeoBone) this.getAnimationProcessor().getBone("LeftLowerLeg"))).getModelSpaceMatrix();
   entity.rightFoot = ((GeoBone) this.getAnimationProcessor().getBone("RightLowerLeg")).getModelSpaceMatrix();
   entity.leftLeg = ((GeoBone) this.getAnimationProcessor().getBone("LeftLeg")).getModelSpaceMatrix();
   entity.rightLeg = ((GeoBone) this.getAnimationProcessor().getBone("RightLeg")).getModelSpaceMatrix();

        entity.mainItemScale =    ((GeoBone) this.getAnimationProcessor().getBone("RightHand")).getScaleVector();
        entity.offsetItemScale =    ((GeoBone) this.getAnimationProcessor().getBone("LeftHand")).getScaleVector();


        entity.mainHand = ((GeoBone) this.getAnimationProcessor().getBone("RightHand")).getModelRotationMatrix();
       entity.offsetHand = ((GeoBone) this.getAnimationProcessor().getBone("LeftHand")).getModelRotationMatrix();



      entity.rightArm = new Cube(((GeoBone) this.getAnimationProcessor().getBone("RightArm")));
      entity.leftArm = new Cube((GeoBone) this.getAnimationProcessor().getBone("LeftArm"));


        //  CoreGeoBone swordLoca(te9 = this.getAnimationProcessor().getBone("MAllBody"))

        //  CoreGeoBone swordLocate3 = this.getAnimationProcessor().getBone("Root");
        // System.out.println(a.x+"  "+a.y+"  "+a.z);
        //   PoseStack a=new PoseStack();
        //   Animation.matrixStackFromModel(a, (GeoBone) this.getAnimationProcessor().getBone("RightHand"));
        //  System.out.println(swordLocate1.getPivotY());//
        //  //    ((GeoBone)).getRotX();a.last().pose();//
        //       this.getAnimationProcessor().getBone()
//    Render r = Render.of();
//    r.poseStack().pushPose();
//  //  r.renderArmor((AbstractClientPlayer) entity.player, EquipmentSlot.CHEST, null);
//    r.poseStack().mulPoseMatrix(swordLocate1.getWorldSpaceMatrix());
//    r.renderArmor((AbstractClientPlayer) entity.player,  EquipmentSlot.HEAD, null);
//    r.poseStack().popPose();


        //     entity.head=swordLocate1.getModelSpaceMatrix();


//System.out.println("x "+180*swordLocate1.getRotX()/Math.PI);
//System.out.println(  "y "+180*swordLocate1.getRotY()/Math.PI);
//   Vec3 vec31 = MathUtils.getWorldPosFromModel(animatable, animatable.yBodyRot, (GeoBone) swordLocate1);
        //System.out.println( swordLocate1.getPosY());


    }



    @Override
    public RenderType getRenderType(PlayerAnimationEntity entity, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(entity));
    }

    @Override
    public ResourceLocation getModelResource(PlayerAnimationEntity entity) {

        return new ResourceLocation(IntegrationPackageCore.MODID, "geo/animation_entity/main.geo.json");

    }

    @Override
    public ResourceLocation getTextureResource(PlayerAnimationEntity entity) {

        if (entity.player instanceof LocalPlayer p) {

            // System.out.println(Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(p).getTextureLocation(p));

            return Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(p).getTextureLocation(p);

        }

        return new ResourceLocation(IntegrationPackageCore.MODID, "textures/entity/tartaric_acid.png");

    }

    @Override
    public ResourceLocation getAnimationResource(PlayerAnimationEntity entity) {

        return new ResourceLocation(IntegrationPackageCore.MODID, "animations/animation_entity/main.animation.json");
    }
}
