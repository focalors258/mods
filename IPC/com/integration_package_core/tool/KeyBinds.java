package com.integration_package_core.tool;

import com.integration_package_core.IPC;
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


@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)//<=====注意  为mod线事件
public class KeyBinds {


    public static KeyMapping angle_locking = new KeyMapping("key." + IPC.MODID + ".angle.locking", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, "key." + IPC.MODID + ".additional.content");

    public static KeyMapping trace_att_target_type = new KeyMapping("key." + IPC.MODID + "trace.att.target.type", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UP, "key." + IPC.MODID + ".additional.content");


    public static KeyMapping snow_obb = new KeyMapping("key." + IPC.MODID + ".snow.abb", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, "key." + IPC.MODID + ".additional.content");

    public static KeyMapping open_dan = new KeyMapping("key." + IPC.MODID + ".open.dan.ui", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_I, "key." + IPC.MODID + ".additional.content");

    public static KeyMapping skill_1 = new KeyMapping("key." + IPC.MODID + ".skill.1", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, "key." + IPC.MODID + ".additional.content");

    public static KeyMapping skill_2 = new KeyMapping("key." + IPC.MODID + ".skill.2", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, "key." + IPC.MODID + ".additional.content");

    public static KeyMapping skill_3 = new KeyMapping("key." + IPC.MODID + ".skill.3", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, "key." + IPC.MODID + ".additional.content");

    public static KeyMapping skill_4 = new KeyMapping("key." + IPC.MODID + ".skill.4", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_L, "key." + IPC.MODID + ".additional.content");

    public static KeyMapping skill_5 = new KeyMapping("key." + IPC.MODID + ".skill.5", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_L, "key." + IPC.MODID + ".additional.content");


    public static Map<String, KeyMapping> keyList = new HashMap<>();


    @SubscribeEvent
    public static void registerKeyBinds(RegisterKeyMappingsEvent ev) {
        //  ev.register(keyBinds.open_dan);

        //ModLoader.get().postEvent();
        // System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////////////////");
       //  MinecraftForge.EVENT_BUS.post();
   //     FMLJavaModLoadingContext.get().getModEventBus().post(new KeyBindsEvent(keyList));
        ev.register( trace_att_target_type);

        ev.register(angle_locking);
        ev.register(open_dan);

        ev.register(skill_1);

        ev.register(skill_2);

        ev.register(skill_3);

        ev.register(skill_4);
        ev.register(snow_obb);

        ev.register(skill_5);

    //   keyList.forEach((name, key) -> {

    //       ev.register(key);

    //   });


    }









}










