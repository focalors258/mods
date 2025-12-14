package com.integration_package_core.mixinTool;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityDimensions;

public interface EntityExpand {
    public void setDimensions(EntityDimensions e);
    public CompoundTag getKjsData();

    public int getDataInt(String key);

    public int getDataFloat(String key);

    public Tag getDataList(String key);


    public boolean getDataBoolean(String key);


    public double getDataDouble(String key);

    public String getDataString(String key);

  //  public boolean setDataFreely(String key, Object value);

    public boolean areData(String key);

    public boolean setDataList(String key, Tag value);
    public CompoundTag getRootData();

    public void setRootData(CompoundTag value);
    public boolean setDataValue(String key, Object value);
    public boolean setDataInt(String key, int value);
    public boolean setDataString(String key, String value);

    public boolean setDataBoolean(String key, boolean value);

    public boolean setDataFloat(String key, float value);


    public boolean setDataDouble(String key, double value);












}
