package com.integration_package_core.event.EventHandler.Client;


import com.integration_package_core.IPC;
import com.integration_package_core.mixinTool.LivingEntityExpand;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.optimize.EntityBar;
import com.integration_package_core.optimize.LevelSubtitle;
import com.integration_package_core.optimize.WeaponStack;
import com.integration_package_core.util.Network.NetworkEvent;
import com.integration_package_core.util.Utlis;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = IPC.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class SyncEvent {


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void p(NetworkEvent e) {

        if (Objects.equals(e.key, "player_move_set")) {

            CompoundTag v = e.data;

            e.player.setDeltaMovement(v.getDouble("x"), v.getDouble("y"), v.getDouble("z"));

        } else if (Objects.equals(e.key, "smashTimeSync")) {

            CompoundTag v = e.data;

            if (e.level.getEntity(v.getInt("id")) instanceof LivingEntityExpand e1) {

                e1.setSmashTime(v.getFloat("smashTime"));

            }


        } else if (Objects.equals(e.key, "maxToughnessSync")) {

            CompoundTag v = e.data;

            if (e.level.getEntity(v.getInt("id")) instanceof LivingEntityExpand e1) {

                e1.setMaxToughness(v.getFloat("maxToughness"));

            }


        } else if (Objects.equals(e.key, "toughnessSync")) {

            CompoundTag v = e.data;

            if (e.level.getEntity(v.getInt("id")) instanceof LivingEntityExpand e1) {

                e1.setToughness(v.getFloat("toughness"));

            }


        } else if (Objects.equals(e.key, "isSmash")) {

            CompoundTag v = e.data;

            if (e.level.getEntity(v.getInt("id")) instanceof LivingEntityExpand e1) {

                e1.setSmash(v.getBoolean("smash"));

            }

        } else if (Objects.equals(e.key, "smashEndSync")) {

            CompoundTag v = e.data;

            if (e.level.getEntity(v.getInt("id")) instanceof LivingEntityExpand e1) {

                e1.setSmashEnd(v.getFloat("smashEnd"));

            }

        } else if (Objects.equals(e.key, "AbsorptionAmount")) {

            CompoundTag v = e.data;

            if (e.level.getEntity(v.getInt("id")) instanceof LivingEntity e1) {

                e1.setAbsorptionAmount(v.getFloat("AbsorptionAmount"));
                //  System.out.println(e1.getAbsorptionAmount());
            }

        } else if (Objects.equals(e.key, "rigidEndSync")) {

            CompoundTag v = e.data;

            if (e.level.getEntity(v.getInt("id")) instanceof LivingEntityExpand e1) {

                e1.setRigidEnd(v.getFloat("rigidEnd"));
                //  System.out.println(e1.getAbsorptionAmount());
            }

        } else if (Objects.equals(e.key, "rigidTimeSync")) {

            CompoundTag v = e.data;

            if (e.level.getEntity(v.getInt("id")) instanceof LivingEntityExpand e1) {

                e1.setRigidTime(v.getFloat("rigidTime"));
                //  System.out.println(e1.getAbsorptionAmount());
            }

        } else if (Objects.equals(e.key, "isRigidSync")) {

            CompoundTag v = e.data;

            if (e.level.getEntity(v.getInt("id")) instanceof LivingEntityExpand e1) {

                e1.setRigid(v.getBoolean("isRigid"));
                //  System.out.println(e1.getAbsorptionAmount());
            }

        } else if (Objects.equals(e.key, "weaponSync")) {

            ((PlayerExpand) e.player).pushWeapon((CompoundTag) e.data.get("nbt"), e.data.getString("id"));

        } else if (Objects.equals(e.key, "toughnessSyncPlayer")) {


            if (Minecraft.getInstance().player instanceof PlayerExpand p) {

                if (p.getWeaponData().containsKey(e.data.getString("itemId"))) {
                    WeaponStack data = p.getWeaponData().get(e.data.getString("itemId"));
                    data.toughnessValue = e.data.getFloat("value");
                    data.toughnessRecover = e.data.getBoolean("recover");
                    //    data.toughnessTime = e.data.getInt("time");
                }


            }

        } else if (Objects.equals(e.key, "toughnessTimeSyncPlayer")) {


            if (Minecraft.getInstance().player instanceof PlayerExpand p) {

                if (p.getWeaponData().containsKey(e.data.getString("itemId"))) {
                    WeaponStack data = p.getWeaponData().get(e.data.getString("itemId"));
                    data.toughnessTime = e.data.getInt("time") + Minecraft.getInstance().player.tickCount;

                    //    data.toughnessTime = e.data.getInt("time");
                }


            }

        } else if (Objects.equals(e.key, "weaponListSync")) {


            //   e.data.getAllKeys().
//
            //   new ArrayList<>()


            // (  (PlayerExpand) Minecraft.getInstance().player).setWeaponList();


        } else if (Objects.equals(e.key, "weaponDataSyncPlayer")) {

            ((PlayerExpand) e.player).getWeaponStack((e.data.getString("itemId"))).data = (CompoundTag) e.data.get("data");

        }
        else if (Objects.equals(e.key, "damageEventBar")) {//显示条
//不要在客户端接收事件中获取实体属性(如最大生命值)!!!!!!!!!!!!!!!!!!!!!
           EntityBar.snowEntityBar(e);



        }   else if (Objects.equals(e.key, "tickCountSync")) {//显示条
//不要在客户端接收事件中获取实体属性(如最大生命值)!!!!!!!!!!!!!!!!!!!!!
            Utlis.tickCount=e.data.getInt("tick");



        }


    }


}
