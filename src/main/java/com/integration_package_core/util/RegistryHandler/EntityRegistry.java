package com.integration_package_core.util.RegistryHandler;

import com.integration_package_core.IntegrationPackageCore;
import com.integration_package_core.animation.PlayerAnimationEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib.GeckoLib;

public final class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
           IntegrationPackageCore. MODID);

//注意  实体注册需写在这之间------------------------------

    public static final RegistryObject<EntityType<PlayerAnimationEntity>> PlayerAnimationEntity = registerEffect("player_animation_entity", PlayerAnimationEntity::new,
            0f, 0f, 0x302219, 0xACACAC);



//注意  实体注册需写在这之间------------------------------

    public static <T extends Entity> RegistryObject<EntityType<T>> registerEffect(String name, EntityType.EntityFactory<T> entity,
                                                                              float width, float height, int primaryEggColor, int secondaryEggColor) {
    return ENTITIES.register(name,
            () -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).noSave().fireImmune().build(new ResourceLocation(IntegrationPackageCore.MODID, name).toString()));}





}






















// public static final RegistryObject<Item> aaavvvbgrh=ITEMS.register("fttft", ItemBase::new);

//public static int aaaa=EntityBar.MODID;
//   public static void init(){

// ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

//  }

//  public static <T extends Mob> EntityType<T> registerMob(String name, EntityType.Builder<T> entity,  float width, float height, int primaryEggColor, int secondaryEggColor) {

//   return    Registry.register(BuiltInRegistries.ENTITY_TYPE, name, entity.sized(width, height).build(name));

//   //  return ENTITIES.register(name,
//   //          () -> EntityType.Builder.of(entity, MobCategory.MISC).sized(width, height).build(name));
//  }
//    public static final DeferredRegister<Item> ITEMS=DeferredRegister.create(ForgeRegistries.ITEMS, IntegrationPackageCore.MODID);

