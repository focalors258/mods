package com.integration_package_core.util.RegistryHandler;

import com.integration_package_core.IPC;
import com.integration_package_core.animation.PlayerAnimationEntity;
import com.integration_package_core.entity.BlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITY= DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,
            IPC.MODID);

    public static final RegistryObject<BlockEntityType<com.integration_package_core.entity.BlockEntity>> BlockEntity=BLOCKENTITY.register("blockentity",()->{

        Block[] blocks=new Block[BuiltInRegistries.BLOCK.size()];



        for (int i = 0; i < BuiltInRegistries.BLOCK.size(); i++) {
            blocks[i]=BuiltInRegistries.BLOCK.get((ResourceLocation) BuiltInRegistries.BLOCK.keySet().toArray()[i]);
        }


        return   BlockEntityType.Builder.of(com.integration_package_core.entity.BlockEntity::new,  blocks).build(null);
    });










}
