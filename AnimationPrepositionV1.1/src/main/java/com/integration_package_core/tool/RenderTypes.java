package com.integration_package_core.tool;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RenderTypes extends RenderType{


    public RenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    public static RenderType Beacon(ResourceLocation location) {
        RenderStateShard.TextureStateShard shard = new RenderStateShard.TextureStateShard(location, false, false);
        RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder().setTextureState(shard).setShaderState(RENDERTYPE_BEACON_BEAM_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setOverlayState(OVERLAY).setWriteMaskState(COLOR_WRITE).createCompositeState(false);
        return RenderType.create("aaaaa", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, rendertype$state);
    }

    public static RenderType TranslucentEntity(ResourceLocation location) {
        RenderStateShard.TextureStateShard shard = new RenderStateShard.TextureStateShard(location, false, false);
        RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder().setTextureState(shard).setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setOverlayState(OVERLAY).setWriteMaskState(COLOR_WRITE).createCompositeState(false);
        return RenderType.create("aaaaa", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, rendertype$state);
    }







}
//