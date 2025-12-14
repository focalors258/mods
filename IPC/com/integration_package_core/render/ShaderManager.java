package com.integration_package_core.render;

import com.integration_package_core.IPC;
import com.integration_package_core.mixinTool.PostChainMember;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class ShaderManager {


    public static final Minecraft mc = Minecraft.getInstance();
    //   public static final RenderStateShard.ShaderStateShard AAA8AAA = new RenderStateShard.ShaderStateShard(() -> aaaaaa);
    public static ShaderInstance aaaaaa;
    public static HashMap<String, PostChain> EffectList = new HashMap<>();
    public static HashMap<String, ShaderInstance> ShaderList = new HashMap<>();

    public static ResourceProvider resourceProvider;
    public static Function<ResourceLocation, RenderType> line;

    public static void addShader(String name, ResourceLocation e, VertexFormat v) throws IOException {


        if (resourceProvider != null)
            ShaderList.put(name, new ShaderInstance(resourceProvider, e, v));//mc.getResourceManager()无法获取到渲染器资源
    }

    public static Function<ResourceLocation, RenderType> getRenderType(ResourceLocation name, VertexFormat v, VertexFormat.Mode vm)  {
        if (!ShaderList.containsKey(name.toString())) {

            try {
                addShader(name.toString(), name, v);

            } catch (IOException e) {
                System.out.println("cnm");
            }


        }
        return (re) -> RenderTypes.custom(name.toString(), re, new RenderStateShard.ShaderStateShard(() -> ShaderList.get(name.toString())), v, vm);
        //new RenderStateShard.ShaderStateShard(()-> ShaderList.get(name.toString()))

        //   RenderStateShard.TextureStateShard shard = new RenderStateShard.TextureStateShard(location, false, false);
        //   RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder().setTextureState(shard).setShaderState().setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setOverlayState(OVERLAY).setWriteMaskState(COLOR_WRITE).createCompositeState(false);
        //   return RenderType.create("aaaaa", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, rendertype$state);
    }

///    static {
///        try {
///            line = getRenderType(new ResourceLocation(IPC.MODID,"rendertype_lines"),DefaultVertexFormat.POSITION_TEX_COLOR,VertexFormat.Mode.LINES);
///        } catch (IOException e) {
///            throw new RuntimeException(e);
///        }
///    }
///

    public static void removeEffect(String name) {

        EffectList.remove(name);
    }

    public static void addEffect(String name, ResourceLocation path) throws IOException {

        if (!EffectList.containsKey(name) && EffectList.size() < 200) {
            RenderSystem.assertOnRenderThread();
            PostChain pp = new PostChain(
                    mc.textureManager,
                    mc.getResourceManager(),
                    mc.getMainRenderTarget(), path);

            EffectList.put(name, pp);
        }
    }

    public static void renderEffect(ResourceLocation path, int pWidth, int pHeight) throws IOException {
        if (EffectList.containsKey(path.toString())) {
            PostChain pc = EffectList.get(path.toString());
            pc.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
            pc.process(mc.getPartialTick());
            mc.getMainRenderTarget().bindWrite(false);

        } else {

            addEffect(path.toString(), path);

        }

    }

    public static void renderEffect(String name, int pWidth, int pHeight) {

        if (EffectList.containsKey(name)) {
            PostChain pc = EffectList.get(name);

            pc.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());

            pc.process(mc.getPartialTick());

            mc.getMainRenderTarget().bindWrite(false);

        }
    }

    public static void renderEffect(String name, int pWidth, int pHeight, Consumer<Effect> set) {

        if (EffectList.containsKey(name)) {
            PostChain pc = EffectList.get(name);
            pc.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
            pc.process(mc.getPartialTick());
            mc.getMainRenderTarget().bindWrite(false);
            set.accept(getEffects(name));
        }

    }

    public static void setEffect(String name, String effect, float... value) {

        if (EffectList.containsKey(name)) {
            PostChain pc = EffectList.get(name);

            if (pc instanceof PostChainMember p) {
                p.getPasses().forEach(pa -> {

                    EffectInstance effects = pa.getEffect();
                    if (effects.getName().equals(pc.getName())) {
                    }
                    if (value.length == 1) {
                        effects.safeGetUniform(effect).set(value[0]);
                    } else if (value.length == 2) {
                        effects.safeGetUniform(effect).set(value[0], value[1]);
                    } else if (value.length == 3) {
                        effects.safeGetUniform(effect).set(value[0], value[1], value[2]);
                    } else if (value.length == 4) {
                        effects.safeGetUniform(effect).set(value[0], value[1], value[2], value[3]);
                    }


                });

            }
        }
    }

    public static Effect getEffects(String name) {
        if (EffectList.containsKey(name)) {
            PostChain pc = EffectList.get(name);

            if (pc instanceof PostChainMember p) {

                for (PostPass pa : p.getPasses()) {

                    EffectInstance effects = pa.getEffect();

                    //   System.out.println(pc.getName());

                    if (effects.getName().equals(pc.getName())) {
                    }

                    return new Effect(effects);

                }

            }

        }

        return null;
    }


    public static class Effect {

        EffectInstance effect;

        public Effect(EffectInstance effect1) {

            effect = effect1;

        }

        public AbstractUniform getGlValue(String name) {
//注意类型严格与glsl一致
            return effect.safeGetUniform(name);

        }

    }

}
