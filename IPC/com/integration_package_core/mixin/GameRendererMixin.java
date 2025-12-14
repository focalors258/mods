package com.integration_package_core.mixin;

import com.integration_package_core.IPC;
import com.integration_package_core.render.ShaderManager;
import com.mojang.blaze3d.shaders.Program;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    public abstract void setRenderBlockOutline(boolean pRenderBlockOutline);

    @Inject(method = "reloadShaders", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GameRenderer;shutdownShaders()V",
            shift = At.Shift.AFTER
    ), locals = LocalCapture.CAPTURE_FAILHARD, require = 1)
    private void reloadShaders(ResourceProvider provier, CallbackInfo info, List<Program> programs, List<Pair<ShaderInstance, Consumer<ShaderInstance>>> shaders) throws IOException {




        ShaderManager.resourceProvider=provier;









        try {

            System.out.println("wcnmwcnmwcnm_________________________________________________________________________________________________________________________");

            shaders.add(Pair.of(
                    new ShaderInstance(
                            provier,
                            new ResourceLocation(
                                    IPC.MODID, "position_tex_col_smooth"),
                            DefaultVertexFormat.POSITION_TEX_COLOR),
                    x -> {


                        ShaderManager.aaaaaa = x;



                    }));


        } catch (IOException ignored) {
            System.out.println("nmslnmslnmsl_________________________________________________________________________________________________________________________");


        }

    }

}
