package com.integration_package_core.event.Event;

import com.mojang.blaze3d.platform.InputConstants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.Cancellable;
import org.spongepowered.asm.mixin.injection.callback.CancellationException;

@Cancelable//可取消事件
public class KeyClickEvent extends InputEvent implements Cancellable {//可取消事件


    // 标注事件可取消

    private final int key;
    private final int scanCode;
    private final int action;
    private final int modifiers;
//当事件被取消前仍然有按键按下   Action可能会在取消后保持按下状态  可用此重置
    public static boolean resetAction = false;


    public KeyClickEvent(int key, int scanCode, int action, int modifiers) {

        this.key = key;
        this.scanCode = scanCode;
        this.action = action;
        this.modifiers = modifiers;

        resetAction = false;

    }

    // 获取战利品列表（可修改，用于调整掉落）
    public int getKey() {
        return this.key;
    }

    public void setResetAction(boolean value) {

        resetAction = value;

    }

    public int getScanCode() {
        return this.scanCode;
    }

    public int getAction() {
        return this.action;
    }


    public int getModifiers() {
        return this.modifiers;
    }


    public static boolean onKeyButtonPre(int key, int scanCode, int action, int modifiers) {
        return MinecraftForge.EVENT_BUS.post(new KeyClickEvent(key, scanCode, action, modifiers));
    }


    @Override
    public boolean isCancellable() {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void cancel() throws CancellationException {

    }
}