package com.integration_package_core.event.EventHandler.Client;

import com.integration_package_core.Config;
import com.integration_package_core.IPC;
import com.integration_package_core.animation.Storyboard;
import com.integration_package_core.event.Event.KeyClickEvent;
import com.integration_package_core.mixinTool.LivingEntityExpand;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.tool.KeyBinds;
import com.integration_package_core.tool.Maths;
import com.integration_package_core.tool.collide.OBB;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = IPC.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class ButtonEvent {
    public static int a = 0;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void p1(KeyClickEvent e) {

        //  System.out.println(7854767);
        //  e.setCanceled(true);
        //  DeBug.tell(String.valueOf(e.getKey() == KeyBinds.snow_obb.getKey().getValue()));


        if (e.getKey() == KeyBinds.snow_obb.getKey().getValue()) {
            //   System.out.println("cnmcnm");
            OBB.snow = !OBB.snow;
            //      if (OBB.snow)
        }
        if (e.getKey() == KeyBinds.trace_att_target_type.getKey().getValue() && Minecraft.getInstance().player instanceof PlayerExpand p) {

            //if (p.getAnimationEntity() != null) {  }

                Config.TRACE_ATT_TARGET_TYPE.set(!Config.TRACE_ATT_TARGET_TYPE.get());

        }
        if (e.getKey() == KeyBinds.angle_locking.getKey().getValue() && Minecraft.getInstance().player instanceof PlayerExpand p) {

//
            Config.ANGLE_LOCKING.set(!Config.ANGLE_LOCKING.get());

            //    System.out.println("cnm");
            //     System.out.println(Config.ANGLE_LOCKING.get());
            if (p.getAnimationEntity() != null) {
                p.getAnimationEntity().angleLock = !p.getAnimationEntity().angleLock;
                p.getAnimationEntity().angleLock = Config.ANGLE_LOCKING.get();
            }


            if (true) return;
            Storyboard storyboard = new Storyboard(200, true);


            storyboard.startPos = new Vector3f(-5, 0, -2);

            storyboard.setXRot(Minecraft.getInstance().player.xRotO); //= 0;
            // storyboard.setYRot(Maths.getHorizontal(storyboard.startPos.sub(p.getAnimationEntity().position().toVector3f())));// = 0;
            storyboard.reviseSpeed = 0.1F;
            storyboard.reviseType = 1;
            // p.getAnimationEntity().smoothCamera();
            storyboard.smooth = true;
            p.getAnimationEntity().addStoryboard(storyboard);


            p.playAnimation(true, e2 -> {

                e2.setTime(99999);
                e2.setAnimation("walk");
                e2.setPlayType(0);


                return true;
            });


            //   storyboard.yRot= Maths.;
            //      if (OBB.snow)
        }


    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void p(InputEvent.MouseButton.Pre e) {

        //  a++;
        //    System.out.println(a);


        if (e.getButton() == Minecraft.getInstance().options.keyAttack.getKey().getValue()) {

            if (Minecraft.getInstance() != null &&
                    Minecraft.getInstance().player != null &&
                    Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SwordItem s) {
                //   NetworkManager.send("attack");


                Player p = Minecraft.getInstance().player;

                if (p instanceof PlayerExpand p1 && true) {
                    //System.out.println(p1.getAnimationEntity());


                    if (false) {
                        p1.playAnimations(2, 0, t -> {
                            t.setXRots(0);
                            t.setMainTrace(true);
                            t.setYRots(p.yRotO);
                            t.setTime(0);
                            t.setStageCool(0);
                            t.setXRots(0);
                            //tell(t.model)
                            t.setTypeName("3453");
                            //  t.setTraceColor(new float[]{13.7f, 1.7f, 26.1f, 200});
                            t.setMaxTrace(60);
                            //System.out.println(t.stage);
                            t.setMaxStage(4);
                            //t.yRot=-40;
                            if (t.stage == 0) {
                                t.setAnimation("animation.animation3");
                                // = 20;
                                t.setTime(200);

                                //System.out.println(t.getTimeEnd());
                            } else if (t.stage == 1) {

                                t.setAnimation("animation.animation3");
                                //  t.stageCool = 30;
                                t.setTime(155);
                            } else if (t.stage == 2) {
                                t.setAnimation("animation.animation4");
                                //   t.stageCool = 30;
                                t.setTime(140);
                            } else if (t.stage == 3) {
                                t.setAnimation("animation.animation4");
                                //  t.stageCool = 30;
                                t.setTime(160);

                                // return false;
                            }

                            return true;
                        });

                        System.out.println(Minecraft.getInstance().player.level().getServer());


                        p1.pushTick(entity -> {

                            //  System.out.println(entity.getAnimatableManager().isFirstTick());

                            entity.level().getEntities(null, new AABB(
                                    p.getX() - 6,
                                    p.getY() - 6,
                                    p.getZ() - 6,
                                    p.getX() + 6,
                                    p.getY() + 6,
                                    p.getZ() + 6)).forEach(entity1 -> {
                                if (!(entity1 instanceof Player)) {

                                    if ((entity.stage == 1 || entity.stage == 0) && (entity.tickCount % 10 == 0 && entity.tickCount > 5 && entity.tickCount < 20)) {


                                        entity.setTarget(entity1);

                                        if (entity1 instanceof LivingEntityExpand e1) {

                                            e1.setToughness(e1.getToughness() - 1.3f);

                                            e1.addRigid(15);
                                        }

                                        entity1.hurt(entity1.damageSources().magic(), 1);

                                        entity1.invulnerableTime = 0;

                                    }

                                    if ((entity.stage == 3 || entity.stage == 2) && (entity.tickCount % 3 == 0 && entity.tickCount > 5 && entity.tickCount < 20)) {
                                        if (entity1 instanceof LivingEntityExpand e1) {
                                            //  System.out.println(e1.getMaxToughness()-0.3f);

                                            e1.setToughness(e1.getToughness() - 1.3f);
                                        }
                                        entity1.hurt(entity1.damageSources().magic(), 1);

                                        entity1.invulnerableTime = 0;
                                    }
                                }

                            });
                        });

                    }


                }


                if (Minecraft.getInstance().level != null) {
                    //   System.out.println(Minecraft.getInstance().level.getServer());
                }


            }


        }


    }


}
