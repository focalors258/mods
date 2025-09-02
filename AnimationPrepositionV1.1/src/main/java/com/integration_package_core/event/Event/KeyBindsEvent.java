package com.integration_package_core.event.Event;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;
@OnlyIn(Dist.CLIENT)
@Event.HasResult
public class KeyBindsEvent extends Event implements IModBusEvent {

    public Map<String, KeyMapping> keyList;

    @ApiStatus.Internal
    public KeyBindsEvent(Map<String, KeyMapping> keyList) {

        System.out.println("==========================================================================");
        System.out.println(keyList);



        this.keyList = keyList;
    }

    /**
     * Registers a new key mapping.
     */
    public void register(String name, KeyMapping key) {

        this.keyList.put(name, key);
        System.out.println("==========================================================================");
        System.out.println(key);

    }
}







