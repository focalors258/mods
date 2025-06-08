package com.chestsearch.event.eventHandler;

import com.chestsearch.ChestSearch;
import com.chestsearch.gui.roll;
import com.chestsearch.gui.searchList.SearchScreen;
import com.chestsearch.gui.tool;
import com.chestsearch.tool.R;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.chestsearch.network.buttonNetwork;

import java.awt.*;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = ChestSearch.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class renderEvent {

    public static Minecraft minecraft = Minecraft.getInstance();


    @SubscribeEvent
    public static void p(ScreenEvent.MouseScrolled event) {

        Map<String, roll> rolls = null;
        if (Minecraft.getInstance().player != null) {
            rolls = roll.rollList.get(Minecraft.getInstance().player.containerMenu.containerId);
        }
        //  System.out.println(roll.rollList.toString());

        //System.out.println(Minecraft.getInstance().player.containerMenu.containerId);
        if (rolls != null) {

            rolls.forEach((key, rollStack) -> {


                rollStack.sliceScrol((float) event.getScrollDelta());

            });

        }


    }

    @SubscribeEvent
    public static void a(ScreenEvent.MouseDragged.Pre event) {


        Map<String, roll> rolls = null;
        if (Minecraft.getInstance().player != null) {
            rolls = roll.rollList.get(Minecraft.getInstance().player.containerMenu.containerId);
        }
        if (rolls != null) {

            rolls.forEach((key, rollStack) -> {


                if (rollStack.direction == 0) {

                    rollStack.sliceMouse((float) event.getDragY());

                } else if (rollStack.direction == 1) {

                    rollStack.sliceMouse((float) event.getDragX());
                }


            });
            // for (int id : rolls) {}
            //let rool = rolls[id]
        }

    }


    @SubscribeEvent
    public static void c(ScreenEvent.Render.Pre event) {

        PoseStack PoseStack = event.getGuiGraphics().pose();

        //event.getGuiGraphics().


        //  if (event.screen instanceof $LecternClass && Client.player.containerMenu.containerId > 100) {}

        tool.mouseLeftOutside = false;

        Map<String, roll> rolls = null;
        if (Minecraft.getInstance().player != null) {
            rolls = roll.rollList.get(Minecraft.getInstance().player.containerMenu.containerId);
        }
        if (rolls != null) {

            rolls.forEach((key, rollStack) -> {

                rollStack.tick();

            });
        }


    }

    @SubscribeEvent
    public static void tyertye(ScreenEvent.Render.Post event) {

        PoseStack PoseStack = event.getGuiGraphics().pose();

        SearchScreen.chestRender(event, PoseStack);


    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void p(RenderGuiEvent.Post event) {

        PoseStack PoseStack = event.getGuiGraphics().pose();


        if (minecraft.screen instanceof AbstractContainerScreen<?> chestScreen) {

            if (tool.newVirtualButton(((float) minecraft.getWindow().getGuiScaledWidth() / 2 + 87), (int) ((float) minecraft.getWindow().getGuiScaledHeight() / 2 - 35), 27, 25)
            ) {//进入按键区域

                if (tool.mouseLeftOutside) {//按键

                    //  System.out.println("按下");

                    if (minecraft.player != null) {

                        minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.get());

                        // LevelChunk levelchunk = null;
                        if (minecraft.level != null) {

                            Player player = minecraft.player;

                            if (player != null) {

                                int[] pos = player.getPersistentData().getIntArray("chestPos");
                                //  System.out.println(pos);
                                if (pos.length == 3) {
                                    // LevelChunk levelchunk0 = E.getChunk(player, pos[0], pos[2]);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]), SectionPos.blockToSectionCoord(pos[2]));
                                    // LevelChunk levelchunk1 = E.getChunk(player, pos[0] - 16, pos[2] - 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]-16), SectionPos.blockToSectionCoord(pos[2]-16));
                                    // LevelChunk levelchunk2 = E.getChunk(player, pos[0] + 16, pos[2] + 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]+16), SectionPos.blockToSectionCoord(pos[2]+16));
                                    // LevelChunk levelchunk3 = E.getChunk(player, pos[0] - 16, pos[2] + 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]-16), SectionPos.blockToSectionCoord(pos[2]+16));
                                    // LevelChunk levelchunk4 = E.getChunk(player, pos[0] + 16, pos[2] - 16);//player.level().getChunk(SectionPos.blockToSectionCoord(pos[0]+16), SectionPos.blockToSectionCoord(pos[2]-16));

                                    // Map<BlockPos, BlockEntity> list = new HashMap<>();

                                    // list.putAll(levelchunk0.getBlockEntities());
                                    // list.putAll(levelchunk1.getBlockEntities());
                                    // list.putAll(levelchunk2.getBlockEntities());
                                    // list.putAll(levelchunk3.getBlockEntities());
                                    // list.putAll(levelchunk4.getBlockEntities());


                                    // // buttonNetwork.send(new CompoundTag());
                                    // System.out.println(SectionPos.blockToSectionCoord(500));
                                    ///  System.out.println(list);
                                    //   System.out.println(event.le);
                                    // System.out.println();


                                    //          if (!list.isEmpty()) {  }
                                    //   SearchMenu a = new SearchMenu(minecraft.player.containerMenu.containerId, new chestContainer(list));
                                    //  minecraft.setScreen(new SearchScreen(a, player.getInventory(), Component.literal("456")));
                                    //  player.openMenu(new SimpleMenuProvider((i, playerInventory, p) -> a, Component.literal("附近的箱子")));

                                    buttonNetwork.send("openSearch");//额外添加 待完善

                                }

                            }


                        }


                    }
                    //;
                }

            }

        }

        if (minecraft.screen instanceof SearchScreen searchScreen) {

            if (tool.newVirtualButton(((float) R.getWidth() / 2 + 87), (int) ((float) R.getHeight() / 2 - 35), 27, 25)
            ) {

                // System.out.println(tool.mouseLeftOutside);

                if (tool.mouseLeftOutside) {//按键

                    if (minecraft.player != null) {

                        minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.get());

                        if (searchScreen.fullScreen) {//关闭平铺

                            searchScreen.fullScreen = false;
                            searchScreen.roll.count = searchScreen.disorderContainer.size();

                            searchScreen.roll.slidePos *= 3;

                        } else {//打开平铺

                            searchScreen.fullScreen = true;
                            searchScreen.roll.count = (int) Math.ceil((double) searchScreen.disorderContainer.size() / 3);
                            searchScreen.roll.slidePos /= 3;

                        }


                    }

                }
            }

            if (tool.newVirtualButton(((float) R.getWidth() / 2 + 87), (int) ((float) R.getHeight() / 2 - 5), 27, 25)
            ) {

                if (tool.mouseLeftOutside) {//按键

                    if (minecraft.player != null) {

                        minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.get());


                        searchScreen.openChest( minecraft.player .getPersistentData().getIntArray("chestPos"));


                    }

                }

            }

        }

//渲染鼠标坐标
   //     PoseStack.pushPose();
   //     PoseStack.scale(1, 1, 0);
   //     PoseStack.translate(64, 37, 2000);
   //     event.getGuiGraphics().drawString(minecraft.font, "x:" + tool.getMouseX() + "   y:" + tool.getMouseY(), 0, 0, Color.lightGray.getRGB(), false);//(,);
   //     PoseStack.popPose();
//

    }

    //    minecraft.setScreen(new searchScreen());
    //   @SubscribeEvent
    //   public static void c(ScreenEvent.Render.Pre event){}
//
//
//
//            // System.out.println(minecraft.getWindow().getGuiScale());
//            //  PoseStack.scale((float)minecraft.getWindow().getGuiScale()/6,(float) minecraft.getWindow().getGuiScale()/6,1);
//
//            //  event.getGuiGraphics().blit(new ResourceLocation(ChestSearch.MODID, "textures/gui/search_button.png"), 0, 0, 0, 0, 27, 25, 320, 320);
//
    //   @SubscribeEvent
    //   public static void c(ScreenEvent.Render.Pre event){}
//
//


    @SubscribeEvent
    public static void l(ScreenEvent.MouseButtonPressed.Post event) {

        if (event.getButton() == 0) {

            Map<String, roll> rolls = null;
            if (Minecraft.getInstance().player != null) {
                rolls = roll.rollList.get(Minecraft.getInstance().player.containerMenu.containerId);
            }
            //System.out.println(roll.rollList.toString());

//            System.out.println(Minecraft.getInstance().player.containerMenu.containerId);
            if (rolls != null) {

                rolls.forEach((key, rollStack) -> {

                    rollStack.oldMouse();
                });

            }

        }


    }


    @SubscribeEvent
    public static void c(ScreenEvent.MouseButtonReleased.Post event) {


        if (event.getButton() == 0) {

            tool.mouseLeftInside = true;

            tool.mouseLeftOutside = true;
        }


    }

    @SubscribeEvent
    public static void i(ScreenEvent.Render.Post event) {

        tool.mouseLeftInside = false;


    }


}
