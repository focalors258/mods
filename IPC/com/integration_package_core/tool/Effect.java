package com.integration_package_core.tool;

import com.google.common.collect.Lists;
import com.integration_package_core.util.Stage;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class Effect implements Stage {


    public static List<Effect> renderGuI = Collections.synchronizedList(Lists.newArrayList());

    public static List<Effect> renderLevel = Collections.synchronizedList(Lists.newArrayList());


    public BiConsumer<Render, Effect> render;
    public int tickCount = 0;
    public int time = 0;
    public int end = 0;


    public Effect(String type, int time, BiConsumer<Render, Effect> r) {


        this.render = r;
        this.time = time;
        if (Minecraft.getInstance().player != null) {
            this.end = Minecraft.getInstance().player.tickCount + time;
        }

        if (Objects.equals(type, "gui")) {

            renderGuI.add(this);

            // if (renderGuI.size() > 60) renderGuI.remove(0);

        } else if (Objects.equals(type, "level")) {

            renderLevel.add(this);

            //  if (renderGuI.size() > 60) renderGuI.remove(0);
        }


    }

    public void ticks() {

        //    tickCount++;
    }

    public float getTime() {

        if (Minecraft.getInstance().player != null) {

            int tick=(time - (end - Minecraft.getInstance().player.tickCount));

            return (Mth.lerp(Minecraft.getInstance().getPartialTick(), Math.max(0,tick - 1), tick) / time);
        }
        return 0;
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    public void render(Render r) {

        if (Minecraft.getInstance().player != null) {
            render.accept(r, this);
        }
        //  if (tickCount > time) {
        //      renderGuI.remove(this);
        //      renderLevel.remove(this);
        //  }

    }


}
