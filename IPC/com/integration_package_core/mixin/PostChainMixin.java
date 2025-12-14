package com.integration_package_core.mixin;

import com.integration_package_core.mixinTool.PostChainMember;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.*;

import java.util.List;






    @Mixin({PostChain.class})
    @Implements({@Interface(
            iface = AutoCloseable.class,
            prefix = "lazy$"
    )})
    public abstract class PostChainMixin implements AutoCloseable, PostChainMember {


        @Shadow @Final private List<PostPass> passes;


        public List<PostPass> getPasses() {
            return this.passes;
        }
    }







