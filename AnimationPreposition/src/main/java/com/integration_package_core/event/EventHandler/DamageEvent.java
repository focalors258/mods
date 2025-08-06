package com.integration_package_core.event.EventHandler;

import com.integration_package_core.IntegrationPackageCore;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.tool.EntityData;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.server.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IntegrationPackageCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DamageEvent {


    @SubscribeEvent
    public static void p(LivingDamageEvent e) throws ReflectiveOperationException {


        //  System.out.println("345346457");

        if (e.getSource().getEntity() instanceof Player p) {


            if (p instanceof PlayerExpand p1) {


                p1.playAnimation(2, 2, 80, "att", t -> {

                    t.maxStage = 3;

                    if (t.stage == 0) {
                        t.stageCool = 30;
                        t.time = 60;
                    } else if (t.stage == 1) {

                        t.animation = "att2";
                        t.stageCool = 30;
                        t.time = 60;
                    }
                    if (t.stage == 2) {
                        t.animation = "att3";
                        t.stageCool = 85;
                        t.time = 120;
                    }
                });

            }     }
    }
                //    if (!p.getPersistentData().contains("att")) {
//
                //        p1.playAnimation(2, "att", 55, false);
                //        p.getPersistentData().putInt("att", 1);
                //    } else {
//
                //       p1.playAnimation(2, "att2", 67, false);
//
                //        p.getPersistentData().remove("att");
//
                //    }





            //   CompoundTag A = new CompoundTag();

            //   A.putInt("5", 5);

            //entity.setPos(p.position());
            //  System.out.println("<-");

            //  NetworkManager.send("555", A, (ServerPlayer) p);
            //  NetworkManager.send("666", A, (ServerPlayer) p);


/*
        if (false) {

            if (e.getSource().getEntity() instanceof Player player) {

                //  CustomizeMenu a = new CustomizeMenu(100);


                if (e.getEntity() instanceof Creeper) {
                    // player.openMenu(new SimpleMenuProvider((i, playerInventory, p) -> a, Component.literal("456546")));
                }

                // Item item= player.getItemInHand(InteractionHand.MAIN_HAND).getItem();//
//


                //   System.out.println( ReflectionUtils.getFieldValueByType(item, Multimap.class));


                if (player instanceof EntityData invoker) {
//在java中获取通过mixin添加给类的成员
                }
                // Minecraft.getInstance().setScreen(new CustomizeScreen(new CustomizeMenu(100),player.getInventory(),Component.literal("456546")));

                //Component.translatable("ew");
                //  player.health_move=0;


            }
        }
*/



//   @SubscribeEvent
//   public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {

//       //MenuScreens.register(newMenu.Customize.get(), CustomizeScreen::new);//将menu和screen绑定
//       //  MenuScreens.register(MenuHandle.STEW_POT_MENU.get(), StewPotScreen::new);
//   }

    @SubscribeEvent
    public static void g(EntityEvent e) {


//e.getEntity().getPersistentData().


    }

    @SubscribeEvent
    public static void c(MobSpawnEvent e) {


        //  e.getEntity().getPersistentData().


    }

    @SubscribeEvent
    public static void d(ServerStartedEvent e) {


        //  e.getEntity().getPersistentData().

        e.getServer().getAllLevels().forEach(level -> {
            if (level == null) return;
            //   System.out.println(level.getEntity(0).getName()+"我操死你吗个比");

            level.getEntities().getAll().forEach(entity -> {
                if (entity == null) return;
                if (entity instanceof LivingEntity livingEntity) {

                    // livingEntity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(UUID.fromString("34546"),"56436",100.0,AttributeModifier.Operation.ADDITION));//长久保存
                    // System.out.println("我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈");


                }

            });

        });


    }


    @SubscribeEvent
    public static void d(ServerStoppingEvent e) {


        //  e.getEntity().getPersistentData().


        e.getServer().getAllLevels().forEach(level -> {
            if (level == null) return;

            level.getEntities().getAll().forEach(entity -> {

                if (entity == null) return;
                if (entity instanceof LivingEntity livingEntity) {

                    //      entity.getPersistentData().putInt("345345", 675);
                    //      System.out.println(entity);
                    //      System.out.println(entity.getPersistentData().getInt("345345"));

                    //      //  livingEntity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(UUID.fromString("3-4-5-4-6"), "56436", 100.0, AttributeModifier.Operation.ADDITION));//长久保存
                    //      //livingEntity.getPersistentData()


                    //      System.out.println("我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈我操你妈");


                }


            });

        });


    }


}
