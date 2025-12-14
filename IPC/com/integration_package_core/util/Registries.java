package com.integration_package_core.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registries {


    public static String getId(Object o) {


        if (o instanceof Entity e) {

            return BuiltInRegistries.ENTITY_TYPE.getKey(e.getType()).toString();

        } else if (o instanceof ItemStack e) {

            return BuiltInRegistries.ITEM.getKey(e.getItem()).toString();

        } else if (o instanceof Block e) {

            return BuiltInRegistries.BLOCK.getKey(e).toString();


        }

        return "air";
    }



    public static ResourceLocation getResourceLocation(String id) {

        // 1. 定义正则表达式（匹配 xxx:yyy 格式）
        String regex = "^([a-z0-9_]+):([a-z0-9_]+)$";

        // 2. 编译正则表达式为 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 3. 创建 Matcher 对象，关联待匹配的字符串
        Matcher matcher = pattern.matcher(id);

        // 4. 检查是否匹配，并提取分组
        if (matcher.matches()) {
            // group(1) 对应第一个捕获组（xxx）
            String xxx = matcher.group(1);
            // group(2) 对应第二个捕获组（yyy）
            String yyy = matcher.group(2);

          return new ResourceLocation(xxx,yyy);
        } else {
            return null;
        }
    }



    public static Block getBlock(String id) {

        return BuiltInRegistries.BLOCK.get(getResourceLocation(id));

    }


    public static Item getItem(String id) {

        return BuiltInRegistries.ITEM.get(getResourceLocation(id));


    }

    public static ItemStack getItemStack(String id) {

        return new ItemStack(getItem(id));

    }

    public static ItemStack getItemStack(String id, CompoundTag nbt) {

        ItemStack i=  new ItemStack(getItem(id));

        i.setTag(nbt);
        return i;
    }





}
