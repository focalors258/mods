package com.integration_package_core.tool;

import com.integration_package_core.IntegrationPackageCore;
import com.integration_package_core.event.Event.KeyBindsEvent;
import com.integration_package_core.util.Network.NetworkManager;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;
import org.stringtemplate.v4.ST;

import java.util.HashMap;
import java.util.Map;


@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)//<=====注意  为mod线事件
public class keyBinds {



    public static KeyMapping open_dan = new KeyMapping("key." + IntegrationPackageCore.MODID + ".open.dan.ui", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_I, "key." + IntegrationPackageCore.MODID + ".additional.content");

    public static KeyMapping skill_1 = new KeyMapping("key." + IntegrationPackageCore.MODID + ".skill.1", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, "key." + IntegrationPackageCore.MODID + ".additional.content");

    public static KeyMapping skill_2 = new KeyMapping("key." + IntegrationPackageCore.MODID + ".skill.2", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, "key." + IntegrationPackageCore.MODID + ".additional.content");

    public static KeyMapping skill_3 = new KeyMapping("key." + IntegrationPackageCore.MODID + ".skill.3", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, "key." + IntegrationPackageCore.MODID + ".additional.content");

    public static KeyMapping skill_4 = new KeyMapping("key." + IntegrationPackageCore.MODID + ".skill.4", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_L, "key." + IntegrationPackageCore.MODID + ".additional.content");



    public static Map<String, KeyMapping> keyList = new HashMap<>();


    @SubscribeEvent
    public static void registerKeyBinds(RegisterKeyMappingsEvent ev) {
        //  ev.register(keyBinds.open_dan);

        //ModLoader.get().postEvent();
        // System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////////////");
       //  MinecraftForge.EVENT_BUS.post();
   //     FMLJavaModLoadingContext.get().getModEventBus().post(new KeyBindsEvent(keyList));

        ev.register(open_dan);

        ev.register(skill_1);

        ev.register(skill_2);

        ev.register(skill_3);

        ev.register(skill_4);

    //   keyList.forEach((name, key) -> {

    //       ev.register(key);

    //   });


    }









}










