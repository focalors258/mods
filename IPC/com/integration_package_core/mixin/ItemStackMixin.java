package com.integration_package_core.mixin;

import com.google.common.collect.Multimap;
import com.integration_package_core.event.Event.ItemTransferEvent;
import com.integration_package_core.mixinTool.ItemStackExpand;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Collection;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin extends net.minecraftforge.common.capabilities.CapabilityProvider<ItemStack> implements ItemStackExpand {


    @Shadow
    @Final
    @Deprecated
    @Nullable
    private Item item;

    @Shadow
    public abstract Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot pSlot);

    protected ItemStackMixin(Class<ItemStack> baseClass) {
        super(baseClass);
    }


    @OnlyIn(Dist.CLIENT)
    @Inject(
            method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/nbt/CompoundTag;)V", at = @At(value = "TAIL")// 注入点：方法开头

    )
    private void b(ItemLike p_41604_, int p_41605_, CompoundTag p_41606_, CallbackInfo ci) {


        //   ItemTransferEvent.onItemTransfer(null,(ItemStack) (CapabilityProvider)this);



        //System.out.println(this);


        //   System.out.println(346352);
        //  System.out.println(346352);

    }


    public double getAttributeValue(EquipmentSlot e, Attribute a) {


        Collection<AttributeModifier> att = this.getAttributeModifiers(e).get(a);

        if (att != null) {
            return att.stream().mapToDouble(AttributeModifier::getAmount) .sum();
        }
        return 0;
    }


}
