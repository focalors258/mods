package com.integration_package_core.mixin;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyMapping.class)
public abstract  class KeyMappingMixin {


    @Shadow private boolean isDown;

    public  void setDown(boolean is){

        this.isDown=is;

    };















}
