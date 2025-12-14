package com.integration_package_core.entity;

import com.integration_package_core.util.RegistryHandler.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntity extends net.minecraft.world.level.block.entity.BlockEntity {
    public BlockEntity( BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegistry.BlockEntity.get(), pPos, pBlockState);
    }


    public static BlockEntity of( BlockPos pPos,BlockState pBlockState){

        return   new BlockEntity(  pPos,  pBlockState);
    }








}
