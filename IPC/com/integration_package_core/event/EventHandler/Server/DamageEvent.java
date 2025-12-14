package com.integration_package_core.event.EventHandler.Server;

import com.integration_package_core.IPC;
import com.integration_package_core.event.Event.LootDropEvent;
import com.integration_package_core.mixinTool.ItemStackExpand;
import com.integration_package_core.mixinTool.LivingEntityExpand;
import com.integration_package_core.mixinTool.PlayerExpand;

import com.integration_package_core.tool.DeBug;
import com.integration_package_core.util.Network.NetworkManager;
import com.integration_package_core.util.RegistryHandler.AttributeRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.server.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = IPC.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DamageEvent {

    @SubscribeEvent
    public static void p(LivingHealEvent e) {

        Entity p = e.getEntity();

        LivingEntity e1 = e.getEntity();

        if (e.getEntity() != null) {
            e1.level().players().forEach(p1 -> {

                NetworkManager.send(p1, "damageEventBar", c -> {
                    //  if(e.getSource().getEntity()!=null){
                    //      c.putInt("att", e.getSource().getEntity().getId());
                    //  }
                    c.putInt("hurt", e1.getId());
                    c.putFloat("maxHealth", e.getEntity().getMaxHealth());
                    c.putFloat("value", e.getAmount());


                });


            });
        }
    }

    @SubscribeEvent
    public static void p(LootDropEvent e) {

        //   System.out.println(   e.getLootItems());

        //   System.out.println   (   e.source);

//e.getLootItems()
//e.getLootContext()
//e.setCanceled(true);

    }


    @SubscribeEvent
    public static void p(LivingDamageEvent e) throws ReflectiveOperationException {

        Entity p = e.getSource().getEntity();

        LivingEntity e1 = e.getEntity();


        if(p instanceof PlayerExpand p2){

      //    p2.getAnimationEntity().setTarget(e1);



        }

        //   System.out.println(FMLEnvironment.);
        //   try {
        //       ;
        //       System.out.println(Class.forName("net.minecraft.client.Minecraft"));


        //   }catch (ClassNotFoundException ignored1) {

        //       System.out.println("4654646");

        //   }


        if (e.getEntity() != null) {
            e1.level().players().forEach(p1 -> {//更新实体条

                NetworkManager.send(p1, "damageEventBar", c -> {
                    if (e.getSource().getEntity() != null) {
                        c.putInt("att", e.getSource().getEntity().getId());
                    }
                    c.putInt("hurt", e1.getId());
                    c.putFloat("maxHealth", e.getEntity().getMaxHealth());
                    c.putFloat("value", e.getAmount());


                });


            });
        }


        if (p != null && false) {

            //   (  (LivingEntityExpand) p).setMaxToughness((float) (100));

            ((LivingEntityExpand) p).addRigid((80));


            if (e.getSource().getEntity() instanceof Player p1) {

                p1.getItemBySlot(EquipmentSlot.MAINHAND).addAttributeModifier(AttributeRegistry.MaxToughness.get(),
                        new AttributeModifier(UUID.randomUUID(), "56", 65, AttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);

//   System.out.println(( (ItemStackExpand) (Object)  p1.getItemBySlot(EquipmentSlot.MAINHAND)).getAttributeValue(EquipmentSlot.MAINHAND,AttributeRegistry.MaxToughness.get()));

                ((PlayerExpand) p1).pushWeapon(p1.getItemBySlot(EquipmentSlot.MAINHAND));


                ((PlayerExpand) p1).getWeaponMaxToughness(p1.getItemBySlot(EquipmentSlot.MAINHAND));


                ((PlayerExpand) p1).getWeaponData().forEach((a, b) -> {


                    System.out.println(b);


                });

            }
//
            //   float i=((LivingEntityExpand) p).getToughness()-10;
            // //  System.out.println(((LivingEntityExpand) p).getToughness());
            // //  System.out.println(i);
//
            //   (  (LivingEntityExpand) p).setToughness(i);


        }


        if (p instanceof Player p1) {

            PlayerExpand p2 = (PlayerExpand) p1;

p2.getAnimationEntity().setTarget(e1);
            //  p1.setItemSlot(EquipmentSlot);


            //  p1.playStageAnimation(t -> {
            //      t.setXRots(0);
            //      t.setMainTrace(true);
            //      t.setYRots(p.yRotO);
            //      t.setTimeEnd(0);
            //      t.setTarget(e.getEntity());
            //      t.setXRots(0);
            //      //tell(t.model)

            //      //  t.setTraceColor(new float[]{13.7f, 1.7f, 26.1f, 200});
            //      t.setMaxTrace(60);
            //      //System.out.println(t.stage);
            //      t.setMaxStage(4);
            //      //t.yRot=-40;
            //      if (t.stage == 0) {
            //          t.setAnimation("animation.animation6");
            //          t.stageCool = 20;
            //          t.setTimeEnd(200);
            //      } else if (t.stage == 1) {

            //          t.setAnimation("animation.animation3");
            //          t.stageCool = 30;
            //          t.setTimeEnd(155);
            //      } else if (t.stage == 2) {
            //          t.setAnimation("animation.animation5");
            //          t.stageCool = 30;
            //          t.setTimeEnd(140);
            //      } else if (t.stage == 3) {
            //          t.setAnimation("animation.animation4");
            //          t.stageCool = 30;
            //          t.setTimeEnd(160);
            //      }
            //  });

        }


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


//  System.out.println("345346457");
//t.yRot=-40;     //   t.setTexture(new ResourceLocation("integration_package_core", "textures/animation_entity/trail.png"));
//                    //System.out.println(t.stage);
