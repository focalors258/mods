package com.integration_package_core.event.EventHandler.Init;

import com.integration_package_core.IPC;
import com.integration_package_core.util.RegistryHandler.AttributeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
//注意!!!!:@Mod(IPC.MODID)在此加上<-----会导致所有注册管线失败

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
//@Mod(AutoLevelingMod.MOD_ID)
public class EntityEvent {
//



    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void o(RegisterShadersEvent e) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.println(e.getResourceProvider());

        System.out.println(Minecraft.getInstance().getResourceManager());

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

    }












    @SubscribeEvent
    public static void p(EntityAttributeModificationEvent e) {
     //   System.out.println(AttributeRegistry.MaxToughness.isPresent());
        e.getTypes().forEach(entity -> {

            if (entity != EntityType.PLAYER) {
             e.add(entity, AttributeRegistry.MaxToughness.get(), 20);//用于怪物和武器
             e.add(entity, AttributeRegistry.SmashTime.get(), 200);
             e.add(entity, AttributeRegistry.Warn.get(), 100);
            }else{


                e.add(entity, AttributeRegistry.PlayerMaxToughness.get(), 10);//用于玩家

            }


        });

    }













}
