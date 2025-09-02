package com.integration_package_core.util.RegistryHandler;

import com.integration_package_core.IntegrationPackageCore;
import com.integration_package_core.animation.PlayerAnimationEntityRender;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import software.bernie.example.GeckoLibMod;
import software.bernie.example.client.renderer.entity.BatRenderer;
import com.integration_package_core.util.RegistryHandler.EntityRegistry;
import software.bernie.geckolib.GeckoLib;

@Mod.EventBusSubscriber(modid = IntegrationPackageCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class RenderRegistry {


    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
if( !FMLEnvironment.production) {}//
    event.registerEntityRenderer(EntityRegistry.PlayerAnimationEntity.get(), PlayerAnimationEntityRender::new);

    }
}