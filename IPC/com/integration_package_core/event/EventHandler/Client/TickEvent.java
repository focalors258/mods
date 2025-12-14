package com.integration_package_core.event.EventHandler.Client;

import com.integration_package_core.Config;
import com.integration_package_core.IPC;
import com.integration_package_core.entity.PlayerOptimize;
import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.mixinTool.PostChainMember;
import com.integration_package_core.render.ShaderManager;
import com.mojang.blaze3d.shaders.AbstractUniform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = IPC.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TickEvent {


    @SubscribeEvent
    public static void j(net.minecraftforge.event.TickEvent.ClientTickEvent e) throws IOException {


        PlayerOptimize.updateMove();

        LocalPlayer p = Minecraft.getInstance().player;

        Minecraft mc = Minecraft.getInstance();


    //    Effect. renderLevel.forEach(Effect::tick);

       // Effect.renderGuI.forEach(Effect::tick);

        if (p != null&&((PlayerExpand)p).getAnimationEntity()!=null) {
            //System.out.println(((PlayerExpand)p).getAnimationEntity().target);
        }


        if (p != null) {


        // Minecraft.getInstance().getTutorial().stop();

        // p.input.left=false;
        }


        ShaderManager.Effect eff = ShaderManager.getEffects("cnmd");

        if (eff != null) {
        //    System.out.println();
        // eff.getGlValue("Offset").set(0.0F, 0.0F); // vec2用两个float
        // eff.getGlValue("Radius").set(1000.0F);     // float用float值
        // eff.getGlValue("IntensityAmount").set(100.0F);

        }


        if (p != null && p.tickCount >= 10) {


            ShaderManager.addEffect("cnmd", new ResourceLocation(IPC.MODID,"shaders/post/giddiness.json"));

          //  ShaderManager.setEffect("cnmd");



        //   if (!RenderEvent.cnm.containsKey("nmb")) {
        //       RenderSystem.assertOnRenderThread();
        //       PostChain pp = new PostChain(mc.textureManager, mc.getResourceManager(), mc.getMainRenderTarget(), new ResourceLocation(IPC.MODID, "shaders/post/flashlight.json"));

        //       RenderEvent.cnm.put("nmb", pp);
        //   }
            if (false&&RenderEvent.cnm.containsKey("nmb") && RenderEvent.cnm.get("nmb") instanceof PostChainMember ppp) {
                ppp.getPasses().forEach((postPass) -> {
                    EffectInstance effect = postPass.getEffect();

                    if (effect.getName().equals(IPC.MODID + ":flashlight")) {

                        AbstractUniform a = effect.safeGetUniform("Offset");

                        if (a != null) {
                            a.set(0, -0);
                        }
                        //.set(0, -0);
                        effect.safeGetUniform("Radius");//.set(100);
                        //    effect.safeGetUniform("IntensityAmount").set(100);
                    }

                });
            }


        }


    }


}
