package com.integration_package_core.optimize;

import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.util.Network.NetworkManager;
import com.integration_package_core.util.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class WeaponStack {

    public Player player;
    //   public int slotId;
    public String itemId;
    public float toughnessValue;
    public boolean toughnessRecover = true;
    public int toughnessTime = 0;
    public CompoundTag data = new CompoundTag();
   public CompoundTag nbt;
  public   ItemStack item;

    public WeaponStack() {
    }

    public WeaponStack(float toughnessValue) {
        this.toughnessValue = toughnessValue;

    }


    public WeaponStack(String itemId, CompoundTag nbt, CompoundTag data, Player player) {

        this.player = player;
        this.nbt = nbt;
        this.itemId = itemId;

        this.data = data;
        this.toughnessValue = (float) ((PlayerExpand) player).getWeaponMaxToughness(Registries.getItemStack(itemId, nbt));
        this.item = Registries.getItemStack(itemId);
        //System.out.println(toughnessValue);
        this.item.setTag(nbt);
    }

    public WeaponStack(String itemId, CompoundTag nbt, Player player) {

        this.player = player;
        this.nbt = nbt;
        this.itemId = itemId;
        this.toughnessValue = (float) ((PlayerExpand) player).getWeaponMaxToughness(Registries.getItemStack(itemId, nbt));
        this.item = Registries.getItemStack(itemId);
        //System.out.println(toughnessValue);
        this.item.setTag(nbt);
    }


    /**
     * 获取
     *
     * @return toughnessValue
     */
    public float getToughnessValue() {
        return toughnessValue;
    }

    /**
     * 设置
     *
     * @param toughnessValue
     */
    public void setToughnessValue(float toughnessValue) {
        this.toughnessValue = toughnessValue;
    }

    /**
     * 获取
     *
     * @return toughnessRecover
     */
    public boolean getToughnessRecover() {
        return toughnessRecover;
    }

    /**
     * 获取
     *
     * @return toughnessTime
     */
    public int getToughnessTime() {


        return toughnessTime;
    }

    /**
     * 设置
     *
     * @param toughnessTime
     */
    public void setToughnessTime(int toughnessTime) {

        if (player.getServer() != null) {
            NetworkManager.send(player, "toughnessTimeSyncPlayer", e -> {
                e.putString("itemId", itemId);
                e.putInt("time", toughnessTime);
            });
        }

        this.toughnessTime = toughnessTime + player.tickCount;
    }

    public String toString() {
        return "WeaponStack{toughnessValue = " + toughnessValue + ", toughnessRecover = " + toughnessRecover + ", toughnessTime = " + toughnessTime + "}";
    }

    /**
     * 获取
     *
     * @return item
     */
    public ItemStack getItem() {

        return item;
    }

    /**
     * 获取
     *
     * @return nbt
     */
    public CompoundTag getData() {

     //  NetworkManager.send(player, "weaponDataSyncPlayer", e -> {
     //      e.putString("itemId", itemId);
     //      e.put("data", data);
     //  });

        return data;
    }




    public void updateData(){
        NetworkManager.send(player, "weaponDataSyncPlayer", e -> {
            e.putString("itemId", itemId);
            e.put("data", data);
        });
    }


    /**
     * 设置
     *
     * @param data
     */
    public void setData(CompoundTag data) {

        this.data = data;

        NetworkManager.send(player, "weaponDataSyncPlayer", e -> {
            e.putString("itemId", itemId);
            e.put("data", data);
        });



    }

    /**
     * 获取
     *
     * @return toughnessRecover
     */
    public boolean isToughnessRecover() {
        return toughnessRecover;
    }

    /**
     * 设置
     *
     * @param toughnessRecover
     */
    public void setToughnessRecover(boolean toughnessRecover) {
        this.toughnessRecover = toughnessRecover;
    }

    /**
     * 获取
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * 设置
     *
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * 获取
     *
     * @return slotId
     */
    //  public int getSlotId() {
    //   return slotId;
    // }

    /**
     * 设置
     *
     * @param slotId
     */
    //   public void setSlotId(int slotId) {
    // this.slotId = slotId;
    // }
}
