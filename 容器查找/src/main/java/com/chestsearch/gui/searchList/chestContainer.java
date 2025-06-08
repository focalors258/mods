package com.chestsearch.gui.searchList;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class chestContainer implements ContainerData {


    public Map<BlockPos, BlockEntity> container;


    public chestContainer(Map<BlockPos, BlockEntity> container){

        this.container=container;
    }


    @Override
    public int get(int pIndex) {
        return 0;
    }

    public BlockEntity get(BlockPos pos) {
        return container.get(pos);
    }

    public  Map<BlockPos, BlockEntity> get() {
        return container;
    }


    @Override
    public void set(int pIndex, int pValue) {

    }

    @Override
    public int getCount() {
        return container.size();
    }



}
