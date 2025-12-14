package com.integration_package_core.mixinTool;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;

public interface ItemStackExpand {

    public double getAttributeValue(EquipmentSlot e, Attribute a);

}
