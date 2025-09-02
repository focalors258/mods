package com.integration_package_core.event.Event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

// 标注事件可取消
@Cancelable
public class LootDropEvent extends Event {
    private final ObjectArrayList<ItemStack> lootItems; // 生成的战利品列表
    private final LootContext lootContext; // 战利品生成上下文（包含实体、世界等信息）
    private final LootTable lootTable; // 对应的战利品表

    public final Entity entity;

    public final DamageSource source;

    public LootDropEvent(LootTable lootTable, ObjectArrayList<ItemStack> lootItems, LootContext lootContext, Entity entity, DamageSource source) {
        this.lootTable = lootTable;



        this.lootItems = lootItems;
        this.lootContext = lootContext;
        this.entity = entity;
        this.source = source;
    }

    // 获取战利品列表（可修改，用于调整掉落）
    public ObjectArrayList<ItemStack> getLootItems() {
        return lootItems;
    }

    // 获取战利品生成上下文（用于获取额外信息，如击杀者、被击杀实体等）
    public LootContext getLootContext() {
        return lootContext;
    }

    // 获取对应的战利品表（用于判断是哪个表的掉落）
    public LootTable getLootTable() {
        return lootTable;
    }
}