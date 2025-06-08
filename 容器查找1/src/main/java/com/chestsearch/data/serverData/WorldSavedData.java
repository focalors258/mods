package com.chestsearch.data.serverData;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class WorldSavedData extends SavedData {
    // 示例数据：全局计数器和自定义结构位置
    private int globalCounter = 0;
    private final Map<BlockPos, String> customStructures = new HashMap<>();

    // 静态工厂方法：从 NBT 加载数据
    public static WorldSavedData load(CompoundTag nbt) {
        WorldSavedData data = new WorldSavedData();
        data.globalCounter = nbt.getInt("GlobalCounter");

        // 加载自定义结构
        CompoundTag structuresNbt = nbt.getCompound("CustomStructures");
        for (String key : structuresNbt.getAllKeys()) {
            String[] posParts = key.split(",");
            BlockPos pos = new BlockPos(
                    Integer.parseInt(posParts[0]),
                    Integer.parseInt(posParts[1]),
                    Integer.parseInt(posParts[2])
            );
            data.customStructures.put(pos, structuresNbt.getString(key));
        }

        return data;
    }

    // 将数据保存到 NBT
    @Override
    public @NotNull CompoundTag save(CompoundTag nbt) {
        nbt.putInt("GlobalCounter", globalCounter);

        // 保存自定义结构
        CompoundTag structuresNbt = new CompoundTag();
        customStructures.forEach((pos, type) -> {
            String key = pos.getX() + "," + pos.getY() + "," + pos.getZ();
            structuresNbt.putString(key, type);
        });
        nbt.put("CustomStructures", structuresNbt);

        return nbt;
    }

    // 数据操作方法
    public void incrementCounter() {
        globalCounter++;
        setDirty(); // 标记数据已修改，触发保存
    }

    public int getCounter() {
        return globalCounter;
    }

    public void addStructure(BlockPos pos, String type) {
        customStructures.put(pos, type);
        setDirty();
    }

    public Map<BlockPos, String> getStructures() {
        return customStructures;
    }
}