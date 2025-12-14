package com.integration_package_core.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;

public class Utlis {


    public static MinecraftServer server = ServerLifecycleHooks.getCurrentServer();


    public static int tickCount = 0;


    public static CompoundTag syncData = new CompoundTag();


}
