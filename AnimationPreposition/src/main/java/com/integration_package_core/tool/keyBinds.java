package com.integration_package_core.tool;

import com.integration_package_core.IntegrationPackageCore;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;


@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class keyBinds {


    public static KeyMapping open_dan = new KeyMapping("key."+ IntegrationPackageCore.MODID +".open.dan.ui", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_I, "key."+ IntegrationPackageCore.MODID +".additional.content");

    public static Map<String,KeyMapping> keyList=new HashMap<>();


    @SubscribeEvent
    public static void registerKeyBinds(RegisterKeyMappingsEvent ev)
    {
        ev.register(keyBinds.open_dan);

        keyList.forEach((name,key)->{


            ev.register(key);

        });




    }
}










