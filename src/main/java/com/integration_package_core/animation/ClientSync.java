package com.integration_package_core.animation;

import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.util.Network.NetworkManager;
import net.minecraft.world.entity.player.Player;

public class ClientSync {


    public static void client() {
        NetworkManager.NetworkEvent("PlayerAnimationSync", e -> {//同步客户端玩家

            if (e.level.getEntity(e.data.getInt("player")) instanceof PlayerExpand p) {

                p.setAnimationEntity((PlayerAnimationEntity) e.level.getEntity(e.data.getInt("entity")));
            }
        });


        NetworkManager.NetworkEvent("PlayerAnimationTick", e -> {//部分同步

            //System.out.println("<-");
            if (e.level.getEntity(e.data.getInt("entity")) instanceof PlayerAnimationEntity p && !p.isEnd()) {//进入结束阶段后不再同步

//注意  客户端tick同步后 仍会自动增值
                p.setAnimation(e.data.getString("animation"));
                //  p.setTime(e.data.getInt("time"));//updateTick
                // p.tickCount = e.data.getInt("tickCount");//updateTick
            }

        });

        NetworkManager.NetworkEvent("PlayerAnimationEntity", e -> {//全部同步

            //System.out.println("<-");
            if (e.level.getEntity(e.data.getInt("entity")) instanceof PlayerAnimationEntity p && !p.isEnd()) {//进入结束阶段后不再同步

                p.player = (Player) e.level.getEntity(e.data.getInt("player"));

                p.setTime(e.data.getInt("time"));//updateTick
                p.tickCount = e.data.getInt("tickCount");//updateTick
                p.playType = e.data.getInt("playType");
                p.restart = e.data.getBoolean("restart");
                p.setAnimation(e.data.getString("animation"));
                p.setXRots(e.data.getFloat("xRot"));
                p.setYRots(e.data.getFloat("yRot"));

                p.setMaxStage(e.data.getInt("maxStage"));
                p.maxTrace = e.data.getInt("maxTrace");
                p.setMainTrace(e.data.getBoolean("mainTrace"));
                p.setOffsetTrace(e.data.getBoolean("offsetTrace"));
                p.setMTraceR1(e.data.getFloat("mTraceR1"));
                p.setMTraceR2(e.data.getFloat("mTraceR2"));
                p.setOTraceR2(e.data.getFloat("oTraceR2"));
                p.setOTraceR1(e.data.getFloat("oTraceR1"));


// p.updateTick = e.data.getInt("updateTick");//updateTick
                //p.tickCount = e.data.getInt("tick");
                // System.out.println(e.data.getString("animation"));
            }


        });

        // 动画同步
        NetworkManager.NetworkEvent("AnimationSync", e -> {//注意  客户端不会瞬间同步


            if (e.level.getEntity(e.data.getInt("entity")) instanceof PlayerAnimationEntity p) {

                //System.out.println(e.data.getString("animation"));

                p.setAnimation(e.data.getString("animation"));
            }
        });


        // 接收：restart同步
        NetworkManager.NetworkEvent("PlayerAnimation_Restart", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.restart = e.data.getBoolean("restart");
            }
        });

        // 接收：xRot同步
        NetworkManager.NetworkEvent("PlayerAnimation_XRot", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.setXRots(e.data.getFloat("xRot"));
            }
        });
        // 接收：xRot同步
        NetworkManager.NetworkEvent("PlayerAnimation_snow", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.setSnow(e.data.getBoolean("snow"));
            }
        });


        // 接收：yRot同步
        NetworkManager.NetworkEvent("PlayerAnimation_YRot", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.setYRots(e.data.getFloat("yRot"));
            }
        });

        // 接收：playType同步
        NetworkManager.NetworkEvent("PlayerAnimation_PlayType", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.playType = e.data.getInt("playType");
            }
        });

        // 接收：maxStage同步
        NetworkManager.NetworkEvent("PlayerAnimation_MaxStage", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.setMaxStage(e.data.getInt("maxStage"));
            }
        });

        // 接收：maxTrace同步
        NetworkManager.NetworkEvent("PlayerAnimation_MaxTrace", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.maxTrace = e.data.getInt("maxTrace");
            }
        });

        // 接收：mainTrace同步
        NetworkManager.NetworkEvent("PlayerAnimation_MainTrace", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.setMainTrace(e.data.getBoolean("mainTrace"));
            }
        });

        // 接收：offsetTrace同步
        NetworkManager.NetworkEvent("PlayerAnimation_OffsetTrace", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.setOffsetTrace(e.data.getBoolean("offsetTrace"));
            }
        });

        // 接收：mTraceR1同步
        NetworkManager.NetworkEvent("PlayerAnimation_MTraceR1", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.setMTraceR1(e.data.getFloat("mTraceR1"));
            }
        });

        // 接收：mTraceR2同步
        NetworkManager.NetworkEvent("PlayerAnimation_MTraceR2", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.setMTraceR2(e.data.getFloat("mTraceR2"));
            }
        });

        // 接收：oTraceR1同步
        NetworkManager.NetworkEvent("PlayerAnimation_OTraceR1", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.setOTraceR1(e.data.getFloat("oTraceR1"));
            }
        });

        // 接收：oTraceR2同步
        NetworkManager.NetworkEvent("PlayerAnimation_OTraceR2", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.setOTraceR2(e.data.getFloat("oTraceR2"));
            }
        });

        // 接收：animation同步


        // 接收：tickCount同步
        NetworkManager.NetworkEvent("PlayerAnimation_TickCount", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
                entity.tickCount = e.data.getInt("tickCount");
            }
        });

        // 接收：time同步
        NetworkManager.NetworkEvent("PlayerAnimation_Time", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {

                entity.setTime(e.data.getInt("time"));

                //    System.out.println("cli    "+ e.data.getInt("time"));


            }
        });

        //    // 保留原PlayerAnimationSync事件（实体关联同步）
        //    NetworkManager.NetworkEvent("PlayerAnimationSync", e -> {
        //        if (e.level.getEntity(e.data.getInt("player")) instanceof PlayerExpand p) {
        //            p.setAnimationEntity((PlayerAnimationEntity) e.level.getEntity(e.data.getInt("entity")));
        //        }
        //    });

    }


}
