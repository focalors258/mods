package com.integration_package_core.event.Event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class WeaponInitEvent extends Event {


    String item;
    CompoundTag nbt;

    CompoundTag data;


    public static boolean onWeaponInit(String item, CompoundTag nbt, CompoundTag data) {
        return MinecraftForge.EVENT_BUS.post(new WeaponInitEvent( item,  nbt,data) );
    }


    public WeaponInitEvent(String item, CompoundTag nbt, CompoundTag data) {
        this.item = item;
        this.nbt = nbt;
        this.data = data;
    }

    /**
     * 获取
     * @return item
     */
    public String getItem() {
        return item;
    }

    /**
     * 设置
     * @param item
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * 获取
     * @return nbt
     */
    public CompoundTag getNbt() {
        return nbt;
    }

    /**
     * 设置
     * @param nbt
     */
    public void setNbt(CompoundTag nbt) {
        this.nbt = nbt;
    }

    /**
     * 获取
     * @return data
     */
    public CompoundTag getData() {
        return data;
    }

    /**
     * 设置
     * @param data
     */
    public void setData(CompoundTag data) {
        this.data = data;
    }

    public String toString() {
        return "WeaponInitEvent{item = " + item + ", nbt = " + nbt + ", data = " + data + "}";
    }
}
