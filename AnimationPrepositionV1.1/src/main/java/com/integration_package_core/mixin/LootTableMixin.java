package com.integration_package_core.mixin;

import com.integration_package_core.event.Event.LootDropEvent;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.NoSuchElementException;

@Mixin(LootTable.class)
public abstract class LootTableMixin {


    @Shadow @Final @Nullable private ResourceLocation randomSequence;

   // @Shadow protected abstract ObjectArrayList<ItemStack> getRandomItems(LootContext pContext);

    /**
     * @author 你的名字
     * @reason 注入战利品掉落事件
     */
    @Overwrite
    private ObjectArrayList<ItemStack> getRandomItems(LootContext pContext) {

        Entity entity=null;

        DamageSource source=null;

        try{
            source=pContext.getParam(LootContextParams.DAMAGE_SOURCE);
            entity=pContext.getParam(LootContextParams.THIS_ENTITY);

        }catch (NoSuchElementException ignored){}

        // 1. 原逻辑：生成初始战利品列表
        ObjectArrayList<ItemStack> originalLoots = new ObjectArrayList<>();
        // 调用原方法中的"填充战利品"逻辑（通过getRandomItemsRaw和stackSplitter）
        ((LootTable) (Object) this).getRandomItemsRaw(pContext, LootTable.createStackSplitter(pContext.getLevel(), originalLoots::add));

        // 2. 触发自定义事件
        LootDropEvent event = new LootDropEvent((LootTable) (Object) this, originalLoots, pContext, entity, source);
        MinecraftForge.EVENT_BUS.post(event);

        // 3. 根据事件结果处理战利品
        ObjectArrayList<ItemStack> finalLoots;
        if (event.isCanceled()) {
            // 若事件被取消，返回空列表（无掉落）
            finalLoots = new ObjectArrayList<>();
        } else {
            // 若未取消，使用事件中可能被修改后的战利品列表
            finalLoots = event.getLootItems();
        }

        // 4. 保留原逻辑：经过Forge全局 loot 修饰器处理
        finalLoots = net.minecraftforge.common.ForgeHooks.modifyLoot(((LootTable) (Object) this).getLootTableId(), finalLoots, pContext);

        return finalLoots;
    }
}
