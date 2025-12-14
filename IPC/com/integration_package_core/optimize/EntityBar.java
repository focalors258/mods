package com.integration_package_core.optimize;

import com.google.common.collect.Lists;
import com.integration_package_core.IPC;
import com.integration_package_core.mixinTool.LivingEntityExpand;
import com.integration_package_core.tool.Maths;
import com.integration_package_core.tool.Render;
//import com.integration_package_core.tool.RotationAxis;
import com.integration_package_core.util.Network.NetworkEvent;
import com.integration_package_core.util.Registries;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import software.bernie.geckolib.core.object.Color;

import java.util.Collections;
import java.util.List;

public class EntityBar {
//注意  gui和level的z轴渲染深度相反!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static final int BarLength = 1000;

    public static ResourceLocation EntityBar_TEXTURE = new ResourceLocation(IPC.MODID, "textures/entity_bar/bar.png");

    public static List<Entity> gui = Collections.synchronizedList(Lists.newArrayList());

    public static List<Entity> level = Collections.synchronizedList(Lists.newArrayList());

    public static float[] barUpdate(int tickCount, float end, float move, float value) {


        if (end <= tickCount) {
            if (move != value) {
                end = tickCount + 30;
            }
            if (end <= tickCount) {
                move = value;
            }
        } else {
            move = value;
        }

        return new float[]{move, value};
    }


    public static void snowEntityBar(NetworkEvent e) {//显示条
        LivingEntity e1 = (LivingEntity) e.level.getEntity(e.data.getInt("att"));
        LivingEntity e2 = (LivingEntity) e.level.getEntity(e.data.getInt("hurt"));

        if (e2 != null) {
    //  LevelSubtitle.texture(EntityBar.EntityBar_TEXTURE, 0,0,0,0,200,200,e2,255,255,255,0);
        }


        float v = e.data.getFloat("value");
        if (e2 instanceof LivingEntityExpand l) {
            e2.getPersistentData().putFloat("MaxHealth", e.data.getFloat("maxHealth"));
            //      if (l.getHealth_move() != e2.getHealth() / e2.getMaxHealth()) {    }
            if (l.getHealth_end() > e2.tickCount) {
                float time = Math.max(0, 1 - (l.getHealth_end() - Mth.lerp(Minecraft.getInstance().getPartialTick(), e2.tickCount - 1, e2.tickCount)) / 30);
                l.setOhealth_move(Mth.lerp(time, l.getOHealth_move(), e2.getHealth() /e.data.getFloat("maxHealth")));
            }
            l.setHealth_end(e2.tickCount + 30);
            if (l.getToughness_end() > e2.tickCount) {
                float time = Math.max(0, 1 - (l.getToughness_end() - Mth.lerp(Minecraft.getInstance().getPartialTick(), e2.tickCount - 1, e2.tickCount)) / 20);
                l.setOtoughness_move(Mth.lerp(time, l.getOtoughness_move(), l.getToughness() / l.getMaxToughness()));
            }
            l.setToughness_end(e2.tickCount + 20);
            if (l.getAbsorption_end() > e2.tickCount) {
                float time = Math.max(0, 1 - (l.getAbsorption_end() - Mth.lerp(Minecraft.getInstance().getPartialTick(), e2.tickCount - 1, e2.tickCount)) / 30);
                l.setOabsorption_move(Mth.lerp(time, l.getOabsorption_move(), e2.getAbsorptionAmount() / e.data.getFloat("maxHealth")));
            }
            l.setAbsorption_end(e2.tickCount + 30);
        }
        if (Minecraft.getInstance().player != null && e2 != null) {
            //  e1 != null && e1.getId() == Minecraft.getInstance().player.getId()
            if (!e2.getPersistentData().contains("boss")) {
                e2.getPersistentData().putBoolean("snowBossBar", true);
                //    e2.getPersistentData().put("snowBossBar", )
                if (!EntityBar.level.contains(e2))
                    EntityBar.level.add(e2);
                // if (EntityBar.level.size() > 50) {
                //     EntityBar.level.remove(0);
                // }
            } else {
                if (!EntityBar.gui.contains(e2))
                    EntityBar.gui.add(e2);
                //  if (EntityBar.gui.size() > 3) EntityBar.gui.remove(0);
            }

            //   LevelSubtitle.text("" + v, (Entity) e2, 1, 1, 1, Math.random() > 0.5 ? 0 : 2);
            //     new HurtSnow("" + v, (Entity) e2, 1, 1, 1, Math.random() > 0.5 ? 0 : 2);
        }


    }


    public static void renderGuiEntityBar(RenderGuiEvent.Pre e, float x, float scale, Entity entity) {

        int level = entity.getPersistentData().getInt("level");
        e.getGuiGraphics().pose().pushPose();
        if (entity.invulnerableTime > 15)
            e.getGuiGraphics().pose().translate(entity.invulnerableTime % 5 - 2.5, entity.invulnerableTime % 3 - 1.5, 0);
        e.getGuiGraphics().pose().pushPose();
        //   e.getGuiGraphics().pose().translate(x, (float) e.getWindow().getGuiScaledHeight() * 0.03, 10);
        String text;
        if (level != 0) {
            text = "Lv." + level + entity.getDisplayName().getString();
        } else {
            text = entity.getDisplayName().getString();
        }
        e.getGuiGraphics().pose().translate(x, (float) e.getWindow().getGuiScaledHeight() * 0.03, -10);
        e.getGuiGraphics().drawString(Minecraft.getInstance().font, text, (int) (-0.5 * Minecraft.getInstance().font.width(text)), -10, Color.WHITE.getColor());
        e.getGuiGraphics().pose().popPose();


        e.getGuiGraphics().pose().pushPose();

        e.getGuiGraphics().pose().translate(x, (float) e.getWindow().getGuiScaledHeight() * 0.03, 10);
        e.getGuiGraphics().pose().mulPose(Maths.Rotation.ZP.deg(180));
        e.getGuiGraphics().pose().scale(40, 40, 50);//大小
        EntityBar.renderEntityBar(e.getGuiGraphics().pose(), (LivingEntity) entity, (int) (((float) e.getWindow().getGuiScaledWidth()) / 854 * scale), true, false, 1);
        e.getGuiGraphics().pose().popPose();
        e.getGuiGraphics().pose().popPose();

    }


    //new ResourceLocation(IPC.MODID, "textures/animation_entity/trail.png"); //
    public static void renderLevelEntityBar(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> e) {

        //if (true) return;
        //  System.out.println(level.contains(e.getEntity()));
        if (level.size() > 50) level.remove(0);


        if (!level.contains(e.getEntity()) ||
                !e.getEntity().getPersistentData().contains("MaxHealth") ||
                !e.getEntity().getPersistentData().contains("snowBossBar") ||
                (Minecraft.getInstance().player != null &&
                        ((e.getEntity() instanceof Player)))) return;

        float d = e.getEntity().distanceTo(Minecraft.getInstance().player);

        if (d > 20) return;
        //System.out.println( (1 - Math.pow(d / 20, 0.01)));
        if (d > 18) RenderSystem.setShaderColor(1F, 1F, 1F, (float) (0.5 * (20 - d)));

        PoseStack poseStack = e.getPoseStack();

        LivingEntity entity = e.getEntity();

        poseStack.pushPose();

        float height = (float) entity.getBoundingBox().getYsize();


        poseStack.translate(0, height + 1, 0);

        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation()); //方向(new Vec3f(0,1,0)).rotation(180))

        int leghnt = 80;

        leghnt += (int) Math.min(300, entity.getBoundingBox().getYsize() * 15);


        boolean elite = entity.getPersistentData().contains("elite");

        if (elite) {

            leghnt = (int) (leghnt * 1.8);

        }
        // entity.getPersistentData().contains("elite")
        renderEntityBar(poseStack, entity, leghnt, elite, true, -1);


        poseStack.popPose();


    }
//注意在gui 渲染字体后  如果是同stack里渲染的图片   后渲染的图片如果z轴小于先渲染的图片  则会被先渲染的遮挡  如不渲染图片则不会被先渲染且z轴优先于其的遮挡

    public static void renderEntityBar(PoseStack poseStack, LivingEntity entity, int scale, boolean elite, boolean renderText, int z) {
        if (!entity.getPersistentData().contains("MaxHealth")) return;
        scale = Math.max(scale, 70);

        poseStack.pushPose();
        poseStack.mulPose(Maths.Rotation.ZP.deg(180));

        poseStack.scale(0.02F, 0.02F, 0.02F);//大小

        GuiGraphics guiGraphics = Render.of(poseStack).guiGraphics;

        float pos = 0;

        float length = 68;

        float maxHealth = entity.getPersistentData().getFloat("MaxHealth");
        float health = entity.getHealth() / maxHealth;

        float absorption = entity.getAbsorptionAmount() / maxHealth;

        if (entity instanceof LivingEntityExpand entity1) {
            //    System.out.println("aaaa");
            float toughness = entity1.getToughness() / entity1.getMaxToughness();

            float oldToughness = EntityBar.renderBarMove(entity.tickCount, entity1.getToughness_end(), entity1.getOToughness_move(), toughness);


            float oldAbsorption = EntityBar.renderBarMove(entity.tickCount, entity1.getAbsorption_end(), entity1.getOAbsorption_move(), absorption);


            //Mth.lerp(Minecraft.getInstance().getPartialTick(), entity1.getOToughness_move(), entity1.getToughness_move());

            float oldHealth = EntityBar.renderBarMove(entity.tickCount, entity1.getHealth_end(), entity1.getOHealth_move(), health);


            if (renderText) {
                // 获取实体等级（假设getData方法用于从实体NBT获取数据）entity.getPersistentData().contains("level")
                int level = entity.getPersistentData().getInt("level");
                level = Math.max(level, 0);  // 确保等级不为负
                Font font = net.minecraft.client.Minecraft.getInstance().font;
                if (level > 0) {

                    // 绘制等级文本（参数：字体、文本、X坐标、Y坐标、颜色、是否阴影）
                    guiGraphics.drawString(
                            font,
                            "Lv." + level,
                            -(380 * scale / BarLength),  // X坐标
                            -10,        // Y坐标
                            Color.ofRGBA(200, 200, 200, 255).getColor(),  // 颜色（ARGB）
                            false     // 无阴影
                    );
                }
            }
            RenderSystem.enableBlend();

            poseStack.translate(0, 0, 3 * z);
            EntityBar.renderBarFrame(scale * 10, elite, guiGraphics);

            //     System.out.println(health);

            if (elite) {
                //  System.out.println(entity1.isSmash());
                if (!entity1.isSmash()) {
                    poseStack.translate(0, 0, 0.01 * z);
                    EntityBar.renderBar(20, 390, 800, 20, oldToughness, scale, 0, 50, guiGraphics);
                    poseStack.translate(0, 0, 0.01 * z);
                    EntityBar.renderBar(20, 340, 800, 20, toughness, scale, 0, 50, guiGraphics);
                } else {
                    poseStack.translate(0, 0, 0.01 * z);
                    EntityBar.renderBar(20, 390, 800, 20, 1 - Math.max(0, entity1.getSmashEnd() - entity.tickCount) / entity1.getSmashTime(), scale, 0, 50, guiGraphics);
                }
            }

            if (oldHealth >= health) {//恢复
                poseStack.translate(0, 0, 0.01 * z);
                EntityBar.renderBar(20, 260, 800, 40, oldHealth, scale, guiGraphics);
                poseStack.translate(0, 0, 0.01 * z);
                EntityBar.renderBar(20, 120, 800, 40, health, scale, guiGraphics);
            } else {//受伤
                poseStack.translate(0, 0, 0.01 * z);
                EntityBar.renderBar(20, 190, 800, 40, health, scale, guiGraphics);
                poseStack.translate(0, 0, 0.01 * z);
                EntityBar.renderBar(20, 120, 800, 40, oldHealth, scale, guiGraphics);
            }

            if (oldAbsorption >= absorption) {
                poseStack.translate(0, 0, 0.01 * z);
                EntityBar.renderBarFrame(582, oldAbsorption, scale * 10, elite, guiGraphics);
                poseStack.translate(0, 0, 0.01 * z);
                EntityBar.renderBarFrame(440, absorption, scale * 10, elite, guiGraphics);

                // EntityBar.renderBar(20, 120, 800, 40, health, leghnt, guiGraphics);
            } else {
                poseStack.translate(0, 0, 0.01 * z);
                EntityBar.renderBarFrame(440, oldAbsorption, scale * 10, elite, guiGraphics);

            }


            RenderSystem.setShaderColor(1F, 1, 1, 1);


            // 矩阵变换：开始绘制血条


        }

        poseStack.popPose();


    }


    public static float renderBarMove(float nowTime, float endTime, float old, float now) {

        float time;


        float oldHealth;

        if (endTime > nowTime) {

            time = Math.max(0, 1 - (endTime - Mth.lerp(Minecraft.getInstance().getPartialTick(), nowTime - 1, nowTime)) / 30);


            oldHealth = Mth.lerp(time, old, now);
        } else {

            oldHealth = now;
        }

        return oldHealth;

    }


    public static float getBarMove(float vary, float present, float speed) {

        // float  speed= ((LivingEntityMixin)pEntity).EntityBar_move_speed;


        if (vary - present == 0.00) {//当变化条少于生命条+0.00时  使两值相等  并重置bossbar1(变化条速度)


        } else if (present < vary) {//当变化条大于生命条足够多时 !entityBar_speed[index] |

            //  if ((vary - present) > speed) {
//
            //     // speed = (float) Math.min(vary - present, 0.3);//速度
            //  }

            //   if (time <= 0) {  }

            vary -= (float) Math.min(speed, +vary - present);


        } else if (present > vary) {

            //     if ((present - vary) > speed) {
//
            //        // speed = (float) Math.min(present - vary, 0.4);
            //     }

            vary += (float) Math.min(speed, (present - vary));

        }


        return vary;


    }

    public static void renderBarFrame(float scale, boolean isElite, GuiGraphics guiGraphics) {

        renderBarFrame(0, 1, scale, isElite, guiGraphics);
    }

    public static void renderBarFrame(float y, float value, float scale, boolean isElite, GuiGraphics guiGraphics) {
        //  System.out.println("bbbbbbbbb");
        scale = Math.max(scale, 70);

        int height = 60;

        if (isElite) height = 90;
        guiGraphics.pose().pushPose();

        guiGraphics.pose().scale(0.1F, 0.1f, 1);

        guiGraphics.blit(
                EntityBar.EntityBar_TEXTURE,
                (int) -(380 * scale / BarLength + 40),  // X坐标
                (int) (1),                  // Y坐标
                (float) 0,            // 纹理UV X
                (float) y,                // 纹理UV Y
                (int) Math.min(40, value * 840),             // 宽度
                height,                  // 高度
                2560, 2560);

        if (value * 840 > 40) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().scale(scale / BarLength, 1, 1);//中间段
            guiGraphics.blit(
                    EntityBar.EntityBar_TEXTURE,
                    (int) (-380),  // X坐标
                    (int) (1),                  // Y坐标
                    40,            // 纹理UV X
                    y,                // 纹理UV Y
                    (int) Math.min(760, value * 840),             // 宽度
                    height,                  // 高度
                    2560, 2560);
            guiGraphics.pose().popPose();
        }
        if (value * 840 > 760) {
            guiGraphics.blit(
                    EntityBar.EntityBar_TEXTURE,
                    (int) (380 * scale / BarLength),  // X坐标
                    (int) (1),                  // Y坐标
                    BarLength - 200,            // 纹理UV X
                    y,                // 纹理UV Y
                    (int) Math.min(40, value * 840),             // 宽度
                    height,                  // 高度
                    2560, 2560);

        }
        guiGraphics.pose().popPose();


    }


    public static void renderBar(int x1, int y1, int x2, int y2, float value, float scale, GuiGraphics guiGraphics) {

        renderBar(x1, y1, x2, y2, value, scale, 0, 0, guiGraphics);


    }


    public static void renderBar(int x1, int y1, int x2, int y2, float value, float scale, int x, int y, GuiGraphics guiGraphics) {

        scale = Math.max(scale, 70);

        value = Math.min(1, value);

        value = Math.max(0, value);
        //  System.out.println("aaaaaaaaaa");


        float scale1 = (x2 - 760 + (760 * scale / 100)) / x2;


        guiGraphics.pose().pushPose();

        guiGraphics.pose().scale(scale1 * 0.1f, 0.1F, 1.0F);

        //  guiGraphics.pose().scale(value*scale1,1, 1);//中间段-(1-value)*scale/BarLength

        //guiGraphics.pose().translate(-(scale1/2), 1, -0.01);


        guiGraphics.blit(
                EntityBar.EntityBar_TEXTURE,
                (int) (-x2 / 2) + x,  // X坐标
                11 + y,                  // Y坐标
                (float) x1,            // 纹理UV X
                (float) y1,                // 纹理UV Y
                (int) (x2 * value),             // 宽度
                y2,                  // 高度
                2560, 2560);

        guiGraphics.pose().popPose();


    }


    /*
    public static void tick(LivingEntity entity){


        if (
                Minecraft.getInstance().player != null &&
                        entity.level().getServer() == null &&
                        Minecraft.getInstance().player.distanceTo(entity) < 20) {

            LivingEntityExpand l=(LivingEntityExpand)entity;

            float toughness=l.getToughness();

            float maxToughness=l.getMaxToughness();


            float presentH = entity.getHealth() / entity.getMaxHealth();

            float presentT = toughness / maxToughness;

            float presentA = entity.getAbsorptionAmount() / entity.getMaxHealth();


            if (entity.tickCount <= 1) {

                toughness = maxToughness;
            }

            //  oabsorption_move = absorption_move;
            // // ohealth_move = health_move;
            //  otoughness_move = toughness_move;

            float speed = 0;

            if (entity.invulnerableTime == 0) {
                speed = 0.02F;
            } else {
                speed = 0.005F;
            }


            if (health_end <= tickCount) {
                if (health_move != presentH) {
                    health_end = tickCount + 30;
                }
                if (health_end <= tickCount) {
                    ohealth_move = presentH;
                }
            } else {
                health_move = presentH;
            }

            if (toughness_end <= tickCount) {
                if (toughness_move != presentT) {
                    toughness_end = tickCount + 30;
                }
                if (toughness_end <= tickCount) {
                    otoughness_move = presentT;
                }
            } else {
                toughness_move = presentT;
            }

            if (absorption_end <= tickCount) {
                if (absorption_move != presentA) {
                    absorption_end = tickCount + 30;
                }
                if (absorption_end <= tickCount) {
                    oabsorption_move = presentA;
                }
            } else {
                absorption_move = presentA;
            }


            //if (0) {}
            //EntityBar.getBarMove(health_move, presentH, speed);
            //    absorption_move = EntityBar.getBarMove(absorption_move, presentA, speed);
            //    toughness_move = EntityBar.getBarMove(toughness_move, presentT, speed);


        }








    }
    */


}
