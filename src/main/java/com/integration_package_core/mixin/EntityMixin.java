package com.integration_package_core.mixin;

import com.integration_package_core.mixinTool.EntityExpand;
import com.integration_package_core.tool.LoadClass;
import com.integration_package_core.tool.TagUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.apache.commons.lang3.ObjectUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@Mixin(Entity.class)
public abstract class EntityMixin extends net.minecraftforge.common.capabilities.CapabilityProvider<Entity> implements EntityExpand, Nameable, EntityAccess, CommandSource, net.minecraftforge.common.extensions.IForgeEntity {


    @Shadow
    private Vec3 position;

    protected EntityMixin(Class<Entity> baseClass) {
        super(baseClass);
    }


    @Shadow
    public abstract CompoundTag getPersistentData();

    @Shadow
    public abstract CompoundTag saveWithoutId(CompoundTag pCompound);

    @Shadow
    private CompoundTag persistentData;

    @Shadow
    private EntityDimensions dimensions;

    public CompoundTag getKjsData() {

        var nbt = new CompoundTag();
        this.saveWithoutId(nbt);

        if (nbt.contains("KubeJSPersistentData")) {

            return (CompoundTag) nbt.get("KubeJSPersistentData");

        }

        return new CompoundTag();

    }


    public boolean setDataList(String key, Tag value) {

        if (value != null) {
            this.getPersistentData().put(key, value);
            return true;
        }
        return false;

    }

    public boolean setDataValue(String key, Object value) {

//js在使用方法的那一刻传入的值会根据形参类型自动转换一次
        //  if (Objects.equals(key, "678657433") && Minecraft.getInstance() != null && Minecraft.getInstance().player != null) {
        //      Minecraft.getInstance().player.sendSystemMessage(Component.literal("qjqs" + value.getClass().getName()));
        //  }

        if (value instanceof Integer v) {

            this.getPersistentData().putInt(key, v);

            return true;
        } else if (value instanceof Boolean v) {

            this.getPersistentData().putBoolean(key, v);

            return true;
        } else if (value instanceof Double v) {

            this.getPersistentData().putDouble(key, v);
            return true;

        } else if (value instanceof Float v) {

            this.getPersistentData().putFloat(key, v);
            return true;

        } else if (value instanceof String v) {

            this.getPersistentData().putString(key, v);

            return true;
        } else if (value instanceof Tag v) {


            this.getPersistentData().put(key, v);
            return true;
        }
        return false;
    }

    public int getDataFloat(String key) {

        if (this.getPersistentData().contains(key)) {

            return this.getPersistentData().getInt(key);

        } else if (getKjsData().contains(key)) {

            return getKjsData().getInt(key);

        }

        return 0;
    }

    public int getDataInt(String key) {

        if (this.getPersistentData().contains(key)) {

            return this.getPersistentData().getInt(key);

        } else if (getKjsData().contains(key)) {

            return getKjsData().getInt(key);

        }

        return 0;
    }

    ;
    @Nullable
    public Tag getDataList(String key) {


        if (this.getPersistentData().contains(key)) {

            return this.getPersistentData().get(key);

        } else if (getKjsData().contains(key)) {

            return  getKjsData().get(key);

        }

        return new CompoundTag();

    }

    ;


    public boolean getDataBoolean(String key) {


        if (this.getPersistentData().contains(key)) {

            return this.getPersistentData().getBoolean(key);

        } else if (getKjsData().contains(key)) {

            return getKjsData().getBoolean(key);

        }

        return false;

    }

    ;


    public double getDataDouble(String key) {


        if (this.getPersistentData().contains(key)) {

            return this.getPersistentData().getDouble(key);

        } else if (getKjsData().contains(key)) {

            return getKjsData().getDouble(key);

        }

        return 0;

    }

    ;

    public String getDataString(String key) {


        if (this.getPersistentData().contains(key)) {

            return this.getPersistentData().getString(key);

        } else if (getKjsData().contains(key)) {

            return getKjsData().getString(key);

        }

        return "";

    }

    ;

    public boolean areData(String key) {


        if (this.getPersistentData().contains(key)) {

            return true;

        } else {
            return getKjsData().contains(key);
        }

    }


    public void setDimensions(EntityDimensions e) {

        this.dimensions = e;

    }


    @Inject(
            method = "load",
            at = @At(
                    value = "TAIL"
                    //  shift = At.Shift.AFTER
            )
    )
    public void init(CallbackInfo ci) {


        //EntityData.getHealthMove((Entity) this)


        // this.position = new Vec3(0, 0, 0);

        //System.out.println("3453456347");


    }


}
