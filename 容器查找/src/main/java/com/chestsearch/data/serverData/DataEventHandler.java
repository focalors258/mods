package com.chestsearch.data.serverData;

import com.chestsearch.ChestSearch;
import net.minecraft.client.telemetry.events.WorldLoadEvent;

import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

@Mod.EventBusSubscriber(modid = ChestSearch.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DataEventHandler {
    private static final String DATA_NAME = "yourmod_world_data"; // 数据文件名将是 "yourmod_world_data.dat"

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            // 只在服务器端加载数据（客户端不需要持久化存储）
            if (serverLevel.dimension() == Level.OVERWORLD) { // 如果你只需要在主世界存储数据
                serverLevel.getDataStorage().computeIfAbsent(
                        WorldSavedData::load,    // 加载方法
                        WorldSavedData::new,     // 创建新实例的方法
                        DATA_NAME              // 数据名称
                );
            }
        }
    }
}