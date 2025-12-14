package com.integration_package_core.mixin;


import net.minecraft.client.Minecraft;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import org.checkerframework.framework.qual.SubtypeOf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Mixin(CompoundTag.class)
public abstract class CompoundTagMixin implements Tag {


    @Shadow @Final private Map<String, Tag> tags;


// /**
//  * @author aaa
//  * @reason aada
//  */
// @Overwrite
// @Nullable
// public Tag put(String pKey, Tag pValue) {
//     if (pValue == null) throw new IllegalArgumentException("Invalid null NBT value with key " + pKey);


//     if (Objects.equals(pKey, "124576464") &&Minecraft.getInstance()!=null&&Minecraft.getInstance().player != null) {
//      //   Minecraft.getInstance().player.sendSystemMessage(Component.literal("cnmgb"+pValue.getClass().getName()));
//     }

//     return this.tags.put(pKey, pValue);
// }






}
