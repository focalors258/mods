package com.integration_package_core.util.RegistryHandler;

import com.integration_package_core.IPC;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public  class AttributeRegistry {


    //注意 DeferredRegister类型需写在开头
    public static final DeferredRegister<Attribute> ATTRIBUTE = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, IPC.MODID);


//玩家使用
    public static final RegistryObject<Attribute> PlayerMaxToughness = registryAttribute("player_max_toughness", () -> new RangedAttribute("attribute.entity_bar.max_toughness", 0.0D, 0.0D, 2000D).setSyncable(true));

    //韧性值 怪物和武器使用
    public static final RegistryObject<Attribute> MaxToughness = registryAttribute("max_toughness", () -> new RangedAttribute("attribute.entity_bar.max_toughness", 0.0D, 0.0D, 2000D).setSyncable(true));

    //倒地时间
    public static final RegistryObject<Attribute> SmashTime = registryAttribute("smash_time", () -> new RangedAttribute("attribute.entity_bar.smash_time", 0.0D, 0.0D, 999999D).setSyncable(true));

    //警戒值
    public static final RegistryObject<Attribute> Warn = registryAttribute("warn", () -> new RangedAttribute("attribute.entity_bar.warn", 0.0D, 0.0D, 1000).setSyncable(true));


//   public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
//           IPC.MODID);


    //   public static final DeferredRegister<Attribute> KUBEJS_ATTRIBUTE= DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "kubejs");

    public static RegistryObject<Attribute> registryAttribute(String name, Supplier<? extends Attribute> sup) {


      //  System.out.println(47543);

        return ATTRIBUTE.register(name, sup);


    }


}
