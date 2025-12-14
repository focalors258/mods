package com.integration_package_core.tool;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.nbt.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TagUtils {



    public static void put(TreeMap<Double,String> treemap,double k,String v){

        treemap.put(k,v);

    }


    public static String get(TreeMap<Double,String> treemap,double k){

      return   treemap.get(k);

    }

    public static CompoundTag getCompound(CompoundTag t,String k){

    return (CompoundTag) t.get(k);

    }

    public static CompoundTag get(Tag t,String k){

        return (CompoundTag) ((CompoundTag) t).get(k);

    }

    public static void putCompound(String key, CompoundTag t, CompoundTag c) {

        ((CompoundTag) t).put(key, c);

    }

    public static void put(String key, Tag t, CompoundTag c) {

        ((CompoundTag) t).put(key, c);

    }


    public static ListTag toTagList(@Nullable Object list) {
        return (ListTag) toTagCollection(list);
    }


    @Nullable
    public static CollectionTag<?> toTagCollection(@Nullable Object v) {
        if (v instanceof CollectionTag tag) {
            return tag;
        } else if (v instanceof CharSequence) {
            try {
                return (CollectionTag<?>) TagParser.parseTag("{a:" + v + "}").get("a");
            } catch (Exception ex) {
                return null;
            }
        } else if (v instanceof JsonArray array) {
            List<Tag> list = new ArrayList<>(array.size());

            for (JsonElement element : array) {
                list.add(toTag(element));
            }

            return toTagCollection(list);
        }

        return v == null ? null : toTagCollection((Collection<?>) v);
    }


    @Nullable
    public static Tag toTag(@Nullable Object v) {
        if (v == null || v instanceof EndTag) {
            return null;
        } else if (v instanceof Tag tag) {
            return tag;
        } else if (v instanceof ItemStack s) {
            return s.save(new CompoundTag());
        } else if (v instanceof CharSequence || v instanceof Character) {
            return StringTag.valueOf(v.toString());
        } else if (v instanceof Boolean b) {
            return ByteTag.valueOf(b);
        } else if (v instanceof Number number) {
            if (number instanceof Byte) {
                return ByteTag.valueOf(number.byteValue());
            } else if (number instanceof Short) {
                return ShortTag.valueOf(number.shortValue());
            } else if (number instanceof Integer) {
                return IntTag.valueOf(number.intValue());
            } else if (number instanceof Long) {
                return LongTag.valueOf(number.longValue());
            } else if (number instanceof Float) {
                return FloatTag.valueOf(number.floatValue());
            }

            return DoubleTag.valueOf(number.doubleValue());
        } else if (v instanceof JsonPrimitive json) {
            if (json.isNumber()) {
                return toTag(json.getAsNumber());
            } else if (json.isBoolean()) {
                return ByteTag.valueOf(json.getAsBoolean());
            } else {
                return StringTag.valueOf(json.getAsString());
            }
        } else if (v instanceof Map<?, ?> map) {
            CompoundTag tag = new CompoundTag();

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Tag nbt1 = toTag(entry.getValue());

                if (nbt1 != null) {
                    tag.put(String.valueOf(entry.getKey()), nbt1);
                }
            }

            return tag;
        } else if (v instanceof JsonObject json) {
            CompoundTag tag = new CompoundTag();

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                Tag nbt1 = toTag(entry.getValue());

                if (nbt1 != null) {
                    tag.put(entry.getKey(), nbt1);
                }
            }

            return tag;
        } else if (v instanceof Collection<?> c) {
            return toTagCollection(c);
        } else if (v instanceof JsonArray array) {
            List<Tag> list = new ArrayList<>(array.size());

            for (JsonElement element : array) {
                list.add(toTag(element));
            }

            return toTagCollection(list);
        }

        return null;
    }


}
