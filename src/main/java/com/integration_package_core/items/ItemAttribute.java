package com.integration_package_core.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import com.integration_package_core.tool.ReflectionUtils;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;

public class ItemAttribute {


    public static void addAttribute(Item item,Attribute attributes,AttributeModifier attributeModifier) throws ReflectiveOperationException {

   ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

   Multimap<Attribute, AttributeModifier> oldMap = ReflectionUtils.get(item, Multimap.class);

   oldMap.forEach(builder::put);

   builder.put(attributes, attributeModifier);

   ReflectionUtils.set(item, Multimap.class, builder.build());

    }

    public static void removeAttribute(Item item) throws ReflectiveOperationException {

      ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

      ReflectionUtils.set(item, Multimap.class, builder.build());

    }




}
