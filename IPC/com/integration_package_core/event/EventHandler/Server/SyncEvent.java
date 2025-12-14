package com.integration_package_core.event.EventHandler.Server;

import com.integration_package_core.IPC;
import com.integration_package_core.mixinTool.LivingEntityExpand;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.util.Network.NetworkEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = IPC.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SyncEvent {

    @SubscribeEvent
    public static void p(NetworkEvent e) {

        if (Objects.equals(e.key, "player_move_get")) {


            if (e.player instanceof PlayerExpand p) {

                CompoundTag v = e.data;
                if (v != null) {
                    p.setServerMoveCache(new Vec3(
                            v.getDouble("x"),
                            v.getDouble("y"),
                            v.getDouble("z")));

                }
            }


        }
        else if (Objects.equals(e.key, "attack")) {



            Player p =e.getPlayer();

            if (p != null) {


               // System.out.println(p.level().getServer()+"cnmcncmcncmcnm");
            }
            if (p instanceof PlayerExpand p1 && false) {
                p1.playAnimation(2, 99, true, "animation.animation6");
            }

            if (p instanceof PlayerExpand p1 && false) {

                p1.playStageAnimation(t -> {
                    t.setXRots(0);
                    t.setMainTrace(true);
                    t.setYRots(p.yRotO);
                    t.setTime(0);

                    t.setXRots(0);
                    //tell(t.model)

                    //  t.setTraceColor(new float[]{13.7f, 1.7f, 26.1f, 200});
                    t.setMaxTrace(60);
                    //System.out.println(t.stage);
                    t.setMaxStage(4);
                    //t.yRot=-40;
                    if (t.stage == 0) {
                        t.setAnimation("animation.animation6");
                        t.stageCool = 20;
                        t.setTime(200);
                    } else if (t.stage == 1) {

                        t.setAnimation("animation.animation3");
                        t.stageCool = 30;
                        t.setTime(155);
                    } else if (t.stage == 2) {
                        t.setAnimation("animation.animation5");
                        t.stageCool = 30;
                        t.setTime(140);
                    } else if (t.stage == 3) {
                        t.setAnimation("animation.animation4");
                        t.stageCool = 30;
                        t.setTime(160);
                    }
                });

                p1.pushTick(entity -> entity.level().getEntities(null, new AABB(
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

                }));
            }


        }
        else if(Objects.equals(e.key, "weaponSync")){

           ((PlayerExpand) e.player).pushWeapon((CompoundTag) e.data.get("nbt"),e.data.getString("id"));

        }
        else if(Objects.equals(e.key, "weaponDataSyncPlayer")){

            ((PlayerExpand) e.player).getWeaponStack((e.data.getString("itemId"))).data= (CompoundTag) e.data.get("data");

        }

    }


}
