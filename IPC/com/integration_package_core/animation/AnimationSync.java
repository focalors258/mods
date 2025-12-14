package com.integration_package_core.animation;

import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.util.Network.NetworkManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class AnimationSync {


//   public static void server() {


//       NetworkManager.NetworkEvent("PlayerAnimationToServer", e -> {//同步客户端玩家

//           if (e.player instanceof PlayerExpand p) {

//               p.initAnimation();
//           }
//       });
    //System.out.println( "4564359834534");
    //  p.setTime(e.data.getInt("time"));//updateTick
    // p.tickCount = e.data.getInt("tickCount");//updateTick

//   }











    public static void client() {

        // 接收：restart同步
        NetworkManager.NetworkEvent("PlayerAnimation_mainRender", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setMainRender(e.data.getBoolean("value"));
            }
        });
        // 接收：restart同步
        NetworkManager.NetworkEvent("PlayerAnimation_offsetRender", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setOffsetRender(e.data.getBoolean("value"));
            }
        });
        // 接收：restart同步
        NetworkManager.NetworkEvent("PlayerAnimation_headRender", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setHeadRender(e.data.getBoolean("value"));
            }
        });
        // 接收：restart同步
        NetworkManager.NetworkEvent("PlayerAnimation_chestRender", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setChestRender(e.data.getBoolean("value"));
            }
        });

        // 接收：restart同步
        NetworkManager.NetworkEvent("PlayerAnimation_legRender", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setLegRender(e.data.getBoolean("value"));
            }
        });
        // 接收：restart同步
        NetworkManager.NetworkEvent("PlayerAnimation_footRender", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setFootRender(e.data.getBoolean("value"));
            }
        });



        NetworkManager.NetworkEvent("PlayerAnimation_Player", e -> {//部分同步
            //System.out.println("<-");
            if (e.level.getEntity(e.data.getInt("entityId")) instanceof PlayerExpand p) {//进入结束阶段后不再同步

                //注意  客户端tick同步后 仍会自动增值

                if (e.data.contains("SyncOtherPlayer")) {
                       p.getAnimationEntity().setSyncOtherPlayer();;
                }

                p.getAnimationEntity().setPlayer((Player) e.level.getEntity(e.data.getInt("player")));

            }
        });




        // 动画同步
        NetworkManager.NetworkEvent("AnimationSync", e -> {//注意  客户端不会瞬间同步


            if (e.level.getEntity(e.data.getInt("entityId")) instanceof PlayerExpand p ) {

                if (e.data.contains("SyncOtherPlayer")) {
                       p.getAnimationEntity().setSyncOtherPlayer();;


                }
                p.getAnimationEntity().setAnimation(e.data.getString("animation"));
            }
        });

        NetworkManager.NetworkEvent("PlayerAnimation_TraceColor", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setTraceColor(new float[]{e.data.getFloat("r"), e.data.getFloat("g"), e.data.getFloat("b"), e.data.getFloat("a")});
            }
        });


        NetworkManager.NetworkEvent("PlayerAnimation_AnimationResource", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {
                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setAnimationResource(new ResourceLocation(e.data.getString("Namespace"),e.data.getString("Path")));
            }
        });



        NetworkManager.NetworkEvent("PlayerAnimation_OffsetTraceResource", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {
                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setOffsetTraceResource(new ResourceLocation(e.data.getString("Namespace"),e.data.getString("Path")));
            }
        });



        NetworkManager.NetworkEvent("PlayerAnimation_MainTraceResource", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setMainTraceResource(new ResourceLocation(e.data.getString("Namespace"),e.data.getString("Path")));
            }
        });
        NetworkManager.NetworkEvent("PlayerAnimation_NextStageTime", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {
                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setNextStage(e.data.getString("typeTarget"),e.data.getInt("nextStage"),e.data.getInt("time"));
            }
        });


        NetworkManager.NetworkEvent("PlayerAnimation_NextStage", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {
                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setNextStage(e.data.getString("typeTarget"),e.data.getInt("nextStage"));
            }
        });

        NetworkManager.NetworkEvent("PlayerAnimation_oTime", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {
                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setOTime(e.data.getInt("oTime"));
            }
        });



        NetworkManager.NetworkEvent("PlayerAnimation_stage", e -> {


            int entityId = e.data.getInt("entityId");


            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {


                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setStage(e.data.getInt("stage"));

            }
        });


        // 接收：target同步
        NetworkManager.NetworkEvent("PlayerAnimation_StageCool", e -> {

            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setStageCool(e.data.getInt("stageCool"));
            }
        });

        // 接收：target同步
        NetworkManager.NetworkEvent("PlayerAnimation_Name", e -> {

            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setTypeName(e.data.getString("name"));
            }
        });



        // 接收：target同步
        NetworkManager.NetworkEvent("PlayerAnimation_target", e -> {

            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setTarget(e.level.getEntity(e.data.getInt("target")));
            }
        });


        // 接收：model同步
        NetworkManager.NetworkEvent("PlayerAnimation_Model", e -> {

            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setModel(new ResourceLocation(e.data.getString("Namespace"), e.data.getString("Path")));

            }
        });
        // 接收：tetxtrue同步
        NetworkManager.NetworkEvent("PlayerAnimation_Texture", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {


                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setTexture(new ResourceLocation(e.data.getString("Namespace"), e.data.getString("Path")));
                // System.out.println(entity.texture);
            }
        });


        // 接收：restart同步
        NetworkManager.NetworkEvent("PlayerAnimation_Restart", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setRestart(e.data.getBoolean("restart"));
            }
        });

        // 接收：xRot同步
        NetworkManager.NetworkEvent("PlayerAnimation_XRot", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setXRots(e.data.getFloat("xRot"));
            }
        });
        // 接收：xRot同步
        NetworkManager.NetworkEvent("PlayerAnimation_snow", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setSnow(e.data.getBoolean("snow"));


              //  System.out.println(e.data.getBoolean("snow"));


          //      System.out.println(e.player);


            }
        });


        // 接收：yRot同步
        NetworkManager.NetworkEvent("PlayerAnimation_YRot", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setYRots(e.data.getFloat("yRot"));
            }
        });

        // 接收：playType同步
        NetworkManager.NetworkEvent("PlayerAnimation_PlayType", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setPlayType(e.data.getInt("playType"));
            }
        });

        // 接收：maxStage同步
        NetworkManager.NetworkEvent("PlayerAnimation_MaxStage", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setMaxStage(e.data.getInt("maxStage"));
            }
        });

        // 接收：maxTrace同步
        NetworkManager.NetworkEvent("PlayerAnimation_MaxTrace", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {
                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setMaxTrace(e.data.getInt("maxTrace"));
            }
        });

        // 接收：mainTrace同步
        NetworkManager.NetworkEvent("PlayerAnimation_MainTrace", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setMainTrace(e.data.getBoolean("mainTrace"));
            }
        });

        // 接收：offsetTrace同步
        NetworkManager.NetworkEvent("PlayerAnimation_OffsetTrace", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setOffsetTrace(e.data.getBoolean("offsetTrace"));
            }
        });

        // 接收：mTraceR1同步
        NetworkManager.NetworkEvent("PlayerAnimation_MTraceR1", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {
                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setMTraceR1(e.data.getFloat("mTraceR1"));
            }
        });

        // 接收：mTraceR2同步
        NetworkManager.NetworkEvent("PlayerAnimation_MTraceR2", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {
                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setMTraceR2(e.data.getFloat("mTraceR2"));
            }
        });

        // 接收：oTraceR1同步
        NetworkManager.NetworkEvent("PlayerAnimation_OTraceR1", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {
                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setOTraceR1(e.data.getFloat("oTraceR1"));
            }
        });

        // 接收：oTraceR2同步
        NetworkManager.NetworkEvent("PlayerAnimation_OTraceR2", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {
                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setOTraceR2(e.data.getFloat("oTraceR2"));
            }
        });

        // 接收：animation同步


        // 接收：tickCount同步
        NetworkManager.NetworkEvent("PlayerAnimation_TickCount", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().tickCount = e.data.getInt("tickCount");
            }
        });

        // 接收：tickCount同步
        NetworkManager.NetworkEvent("PlayerAnimation_EndLink", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {

                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }
                entity.getAnimationEntity().setEndLink(e.data.getBoolean("endLink"));
            }
        });


        // 接收：time同步
        NetworkManager.NetworkEvent("PlayerAnimation_Time", e -> {
            int entityId = e.data.getInt("entityId");
            if (e.level.getEntity(entityId) instanceof PlayerExpand entity) {
                if (e.data.contains("SyncOtherPlayer")) {
                    entity.getAnimationEntity().setSyncOtherPlayer();
                }

                entity.getAnimationEntity().setTime(e.data.getInt("time"));

                //  entity.se.data.getIntArray("time")[1];
                //  System.out.println(entity.timeEnd);


            }
        });

        //  NetworkManager.NetworkEvent("PlayerAnimation_TimeEnd", e -> {
        //      int entityId = e.data.getInt("entityId");
        //      if (e.level.getEntity(entityId) instanceof PlayerAnimationEntity entity) {
        //          if (e.data.contains("SyncOtherPlayer")){
        //              entity.setSyncOtherPlayer();
//
        //              entity.setTime(e.data.getInt("time"));
        //          }
        //      }
        //  });


        //    // 保留原PlayerAnimationSync事件（实体关联同步）
        //    NetworkManager.NetworkEvent("PlayerAnimationSync", e -> {
        //        if (e.level.getEntity(e.data.getInt("player")) instanceof PlayerExpand p) {
        //            p.setAnimationEntity((PlayerAnimationEntity) e.level.getEntity(e.data.getInt("entity")));
        //        }
        //    });

    }

    public static void other() {
        NetworkManager.NetworkEvent("PlayerAnimationEntity", e -> {//全部同步

            //System.out.println("<-");
            if (e.level.getEntity(e.data.getInt("entity")) instanceof PlayerAnimationEntity p && !p.isEnd()) {//进入结束阶段后不再同步
                if (e.data.contains("SyncOtherPlayer")) {
                    //   p.getAnimationEntity().setSyncOtherPlayer();;
                }
                p.player = (Player) e.level.getEntity(e.data.getInt("player"));
                //    p.setTimeEnd(e.data.getInt("time"));//updateTick
                //    p.tickCount = e.data.getInt("tickCount");//updateTick
                //    p.playType = e.data.getInt("playType");
                //    p.restart = e.data.getBoolean("restart");
                //    p.setAnimation(e.data.getString("animation"));
                //    p.setXRots(e.data.getFloat("xRot"));
                //    p.setYRots(e.data.getFloat("yRot"));

                //  p.setMaxStage(e.data.getInt("maxStage"));
                //  p.maxTrace = e.data.getInt("maxTrace");
                //  p.setMainTrace(e.data.getBoolean("mainTrace"));
                //  p.setOffsetTrace(e.data.getBoolean("offsetTrace"));
                //  p.setMTraceR1(e.data.getFloat("mTraceR1"));
                //  p.setMTraceR2(e.data.getFloat("mTraceR2"));
                //  p.setOTraceR2(e.data.getFloat("oTraceR2"));
                //  p.setOTraceR1(e.data.getFloat("oTraceR1"));


// p.updateTick = e.data.getInt("updateTick");//updateTick
                //p.tickCount = e.data.getInt("tick");
                // System.out.println(e.data.getString("animation"));
            }


        });
        NetworkManager.NetworkEvent("PlayerAnimationToServer", e -> {//同步客户端玩家

            if (e.player instanceof PlayerExpand p) {

                p.initAnimation();
            }
        });


        NetworkManager.NetworkEvent("PlayerAnimationTick", e -> {//部分同步

            //System.out.println("<-");
            if (e.level.getEntity(e.data.getInt("entityId")) instanceof PlayerAnimationEntity p && !p.isEnd()) {//进入结束阶段后不再同步

                //注意  客户端tick同步后 仍会自动增值

                if (e.data.contains("SyncOtherPlayer")) {
                    //  p.getAnimationEntity().setSyncOtherPlayer();;
                }

                p.setAnimation(e.data.getString("animation"));
                //  p.setTime(e.data.getInt("time"));//updateTick
                // p.tickCount = e.data.getInt("tickCount");//updateTick
            }
        });


        NetworkManager.NetworkEvent("PlayerAnimationSync", e -> {//同步该玩家在所有客户端的动画实体

            if (e.level.getEntity(e.data.getInt("player")) instanceof PlayerExpand p) {


                p.setAnimationEntity((PlayerAnimationEntity) e.level.getEntity(e.data.getInt("entity")));

                //    System.out.println(p.getAnimationEntity());

            }
        });

    }
}
