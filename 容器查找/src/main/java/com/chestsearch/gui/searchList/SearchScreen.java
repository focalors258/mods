package com.chestsearch.gui.searchList;

import com.chestsearch.ChestSearch;
import com.chestsearch.tool.E;
import com.chestsearch.tool.M;
import com.chestsearch.tool.R;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import com.chestsearch.gui.roll;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.chestsearch.network.buttonNetwork;
import net.minecraftforge.client.event.ScreenEvent;

import java.util.ArrayList;

@OnlyIn(Dist.CLIENT)//注意screen为客户端
public class SearchScreen extends Screen implements MenuAccess<SearchMenu> {
//是否可使用物品组件 取决于screen继承AbstractContainerScreen

    public static SearchScreen of;

    public static ResourceLocation screenImage = new ResourceLocation(ChestSearch.MODID, "textures/gui/search_button.png");

    public SearchMenu menu;

    public boolean fullScreen = false;//是否平铺
   // public CompoundTag container;

    public ArrayList<CompoundTag> orderlyContainer = new ArrayList<>();//有序容器
    public CompoundTag disorderContainer;//无序容器
    public roll roll;//注意 有3排

    //  public static Minecraft minecraft;

    public roll getRoll() {

        return this.roll;
    }

    public boolean init;

    public float getHeight() {

        if (minecraft != null) {
            return minecraft.getWindow().getGuiScaledHeight();
        }
        return 0;
    }

    public float getWidth() {

        if (minecraft != null) {
            return minecraft.getWindow().getGuiScaledWidth();
        }
        return 0;
    }

    public SearchScreen(SearchMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pTitle);

        this.menu = pMenu;

        of = this;

    }

    public void initContainer() {//在界面发生变化时执行


        if (disorderContainer != null) {
            disorderContainer.getAllKeys().forEach(blockPos -> {

                orderlyContainer.add((CompoundTag) disorderContainer.get(blockPos));//有序化

                int line = 0;

                if (!fullScreen) {//是否平展
                    line = disorderContainer.size();
                } else {

                    line = (int) Math.ceil((double) disorderContainer.size() / 3);
                }
                roll = new roll((float) (getWidth() / 2 - 79), (float) (getHeight() / 2 - 91.5),
                        27, 158, line, 193, menu.containerId,
                        "searchRoll", 0, false);

                if (minecraft.player.getPersistentData().contains("searchRoll")) {

                    roll.slidePos = minecraft.player.getPersistentData().getFloat("searchRoll");

                }

            });
        }
        init = true;
    }
    //     disorderContainer = menu.container;

    //     if (disorderContainer != null) {
///意区分 container.getCount()  方块实体数量  roll.count 目录行数


    public SearchMenu getMenu() {


        //  if (this.menu == null){

        //      this.menu = (searchMenu) minecraft.player.containerMenu;//new roll((float) (getWidth() / 2 + 78), (float) (getHeight() / 2 + 91.5), 27, 158, (int) (menu.container.size() / 3), 194, menu.containerId, "searchRoll", 0, false);
        //  }
        return this.menu;

    }

    public static void chestRender(ScreenEvent.Render.Post event, PoseStack PoseStack) {

        if (event.getScreen() instanceof ContainerScreen) {

            R.renderTexture(event, ChestSearch.MODID, "textures/gui/search_button.png",//框
                    0, 0, 0, 0, 29, 27,
                    400, 400, PoseStack,
                    ((float) R.getWidth() / 2 + 87),
                    ((float) R.getHeight() / 2 - 35), 100);


            R.renderTexture(event, ChestSearch.MODID, "textures/gui/search_button.png",//图标
                    0, 0, 186, 37, 24, 24,
                    400, 400, PoseStack,
                    ((float) R.getWidth() / 2 + 90),
                    ((float) R.getHeight() / 2 - 34), 100);


        }

    }

    public void renderListFull(roll r, GuiGraphics pGuiGraphics, PoseStack PoseStack, int mouseValue) {

        for (int i = 0; i <= r.count; i++) {//竖

            if (r.slidePos + i * 27 < -26 || r.slidePos + i * 27 > 195) continue;

            for (int j = 0; j < 3 && i * 3 + j < orderlyContainer.size(); j++) {//横

                int index = i * 3 + j;

                CompoundTag entityTag = orderlyContainer.get(index);//所有数据
                //部分数据
                BlockEntity entity = minecraft.level.getBlockEntity(E.getPos(new int[]{entityTag.getInt("x"), entityTag.getInt("y"), entityTag.getInt("z")}));
//箱子对应的物品
                ItemStack chestOfItem = new ItemStack(entity.getBlockState().getBlock());
                //箱子的全部数据
                ListTag itemsData = entityTag.getList("Items", 10);//获取数组

                int count = itemsData.size();

                PoseStack.pushPose();
                PoseStack.translate(r.x + j * 53, r.y + r.slidePos + i * 27, 100);

                PoseStack.pushPose();
                PoseStack.translate(0, +0.5, 0);
                pGuiGraphics.renderItem(chestOfItem, +3, +4);//物品
                PoseStack.popPose();
                // System.out.println(count);

                RenderSystem.enableBlend();//透明

                pGuiGraphics.blit(screenImage, 0, 0, 75, 0, 52, 26, 400, 400);//栏位
                if (count > 0 && count < 20) {
                    pGuiGraphics.blit(screenImage, 0, 0, 193, 0, (int) ((double) count / 27 * 52), 26, 400, 400);
                } else if (count >= 20) {
                    pGuiGraphics.blit(screenImage, 0, 0, 252, 0, (int) ((double) count / 27 * 52), 26, 400, 400);
                }

                if (index == 3 * r.flag() + mouseValue && r.flag() >= 0) {
                    RenderSystem.enableBlend();
                    pGuiGraphics.blit(screenImage, 0, 0, 134, 0, 52, 26, 400, 400);//高亮
                }


                PoseStack.pushPose();
                PoseStack.scale(0.7F, 0.7F, 0);
                if (entityTag.contains("CustomName")) {//注意  '{\"text\":\"箱子2423421\"}' 并不是tag类型

                    String name = E.getChestName(entityTag);

                    if (minecraft.font.width(name) > 26) {

                        name = name.substring(0, 5) + "..";

                    }
                    pGuiGraphics.drawString(minecraft.font, name, 27, 6, M.getColor(205, 205, 205, 255));//容器名称

                } else {
                    pGuiGraphics.drawString(minecraft.font, chestOfItem.getHoverName(), 27, 6, M.getColor(205, 205, 205, 255));//容器名称
                }

                pGuiGraphics.drawString(minecraft.font,
                        (int)(minecraft.player.distanceToSqr(entityTag.getInt("x"),entityTag.getInt("y"),entityTag.getInt("z"))/4)+"米",
                        27, 20, M.getColor(205, 205, 205, 255));

                PoseStack.popPose();


                PoseStack.popPose();
            }
        }

    }

    public void renderList(roll r, GuiGraphics pGuiGraphics, PoseStack PoseStack, int mouseValue) {
//                             注意  这里不能使用<=
        for (int i = 0; i < r.count; i++) {//竖

            if (r.slidePos + i * 27 < -26 || r.slidePos + i * 27 > 195) {
                continue;
            }

            CompoundTag entityTag = orderlyContainer.get(i);//所有数据
            //部分数据
            BlockEntity entity = minecraft.level.getBlockEntity(E.getPos(new int[]{entityTag.getInt("x"), entityTag.getInt("y"), entityTag.getInt("z")}));

            ItemStack chestOfItem = new ItemStack(entity.getBlockState().getBlock());
            // System.out.println(  entityTag instanceof  );
            ListTag itemsData = entityTag.getList("Items", 10);//获取数组

            int count = itemsData.size();

            PoseStack.pushPose();
            PoseStack.translate(r.x, r.y + r.slidePos + i * 27, 100);

            PoseStack.pushPose();
            PoseStack.translate(0, +0.5, 0);
            pGuiGraphics.renderItem(chestOfItem, +3, +4);//物品
            PoseStack.popPose();
            // System.out.println(count);
            for (int j = 0; j < itemsData.size() && j < 12; j++) {//不能用itemsData.contains

                ItemStack item = E.getItemStack(((CompoundTag) itemsData.get(j)).getString("id"));
                //System.out.println(item);
                if (item != null) {//渲染容器内物品

                    PoseStack.pushPose();
                    PoseStack.scale(0.6F, 0.6F, 1);
                    pGuiGraphics.renderItem(item, j * 18 + 33, 22);//物品
                    PoseStack.popPose();
                }
            }


            RenderSystem.enableBlend();//透明

            pGuiGraphics.blit(screenImage, 0, 0, 229, 38, 158, 26, 400, 400);//栏位
            //System.out.println(itemsData.contains(0));

            if (count > 0 && count < 20) {//渲染数量条
                pGuiGraphics.blit(screenImage, 0, 0, 229, 100, (int) ((double) count / 27 * 158), 26, 400, 400);//绿
            } else if (count >= 20) {
                pGuiGraphics.blit(screenImage, 0, 0, 229, 131, (int) ((double) count / 27 * 158), 26, 400, 400);//红
            }
            if (i == r.flag() && r.flag() >= 0) {
                RenderSystem.enableBlend();
                pGuiGraphics.blit(screenImage, 0, 0, 229, 69, 158, 26, 400, 400);//高亮
            }

            PoseStack.pushPose();
            PoseStack.scale(0.7F, 0.7F, 0);
            if (entityTag.contains("CustomName")) {//注意  '{\"text\":\"箱子2423421\"}' 并不是tag类型

                String name = E.getChestName(entityTag);

                if (minecraft.font.width(name) > 70) {

                    name = name.substring(0, 15) + "..";

                }
                pGuiGraphics.drawString(minecraft.font, name, 29, 6, M.getColor(205, 205, 205, 255));

            } else {
                pGuiGraphics.drawString(minecraft.font, chestOfItem.getHoverName(), 29, 6, M.getColor(205, 205, 205, 255));
            }


            pGuiGraphics.drawString(minecraft.font, (int)(minecraft.player.distanceToSqr(entityTag.getInt("x"),entityTag.getInt("y"),entityTag.getInt("z"))/4)+"米", 180, 6, M.getColor(205, 205, 205, 255));


            PoseStack.popPose();//缩小比例

            PoseStack.popPose();

        }

    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {


        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        if (!init || roll == null) {//未知问题  roll已初始化后再次调用仍为null
            initContainer();
        }


        if (minecraft != null && orderlyContainer != null && disorderContainer != null && init) {

            minecraft.player.getPersistentData().putFloat("searchRoll", roll.slidePos);
            //   SearchMenu menu1 = menu;

            roll r = roll;


            if (r != null) {

                r.x = (float) (getWidth() / 2 - 79);
                r.y = (float) (getHeight() / 2 - 91.5);

                PoseStack PoseStack = pGuiGraphics.pose();


                pGuiGraphics.fill(0, 0, 3000, 3000, -500, M.getColor(10, 10, 10, 100));//填充

                PoseStack.pushPose();

                PoseStack.translate((float) minecraft.getWindow().getGuiScaledWidth() / 2, (float) minecraft.getWindow().getGuiScaledHeight() / 2 + 0.5, 100);//界面
                pGuiGraphics.blit(screenImage, -88, -112, 0, 34, 176, 222, 400, 400);
                PoseStack.translate(0, 0, 1000);

                pGuiGraphics.fill(-88, -140, 88, -94, M.getColor(0, 0, 0, 1));//上遮挡
                PoseStack.translate(0, 242, 1000);
                pGuiGraphics.fill(-88, -140, 88, -94, M.getColor(0, 0, 0, 1));//下遮挡

                PoseStack.popPose();


                // Map<BlockPos, BlockEntity> container =container.get();

                int mouseValue = (int) ((pMouseX - roll.x) / 52);//鼠标选择的值  横向
                if (roll.clickInside()) {//打开箱子  //未添加横向检测

                    if (!fullScreen) {

                        if (pMouseX - roll.x >= 0 && pMouseX - roll.x <= 158) {
                            openChest(r.rollMouse);
                        }

                    } else {

                        if (mouseValue <= 2 && mouseValue >= 0) {
                            int i = r.rollMouse * 3 + mouseValue;
                            openChest(i);

                        }
                    }

                }
                mouseValue = Math.max(0, mouseValue);
                mouseValue = Math.min(2, mouseValue);

                //按键1
                pGuiGraphics.blit(screenImage, (int) (R.getWidth() / 2 + 87), (int) (R.getHeight() / 2 - 35), 0, 0, 30, 27, 400, 400);
               //按键2
                pGuiGraphics.blit(screenImage, (int) (R.getWidth() / 2 + 87), (int) (R.getHeight() / 2 - 5), 0, 0, 30, 27, 400, 400);
                //回到最初的箱子
                pGuiGraphics.blit(screenImage, (int) (R.getWidth() / 2 + 90), (int) (R.getHeight() / 2 - 5), 186, 61, 24, 24, 400, 400);


                if (fullScreen) {//渲染平铺按钮  按键功能写在渲染事件
                    pGuiGraphics.blit(screenImage, (int) (R.getWidth() / 2 + 94), (int) (R.getHeight() / 2 - 29), 189, 89, 14, 14, 400, 400);

                    renderListFull(r, pGuiGraphics, PoseStack, mouseValue);
                } else {//渲染非平铺按钮
                    pGuiGraphics.blit(screenImage, (int) (R.getWidth() / 2 + 94), (int) (R.getHeight() / 2 - 29), 189, 109, 14, 14, 400, 400);

                    renderList(r, pGuiGraphics, PoseStack, mouseValue);
                }


                PoseStack.pushPose();
                PoseStack.translate(0, 0, 2000);
                pGuiGraphics.drawString(minecraft.font, "附近的箱子", (int) r.x, (int) r.y - 14, M.getColor(85, 85, 85, 255), false);
                PoseStack.popPose();
            }
        }

    }

    public void openChest(int i) {

        if (i >= 0 && i < orderlyContainer.size()) {
            int[] pos = new int[]{orderlyContainer.get(i).getInt("x"), orderlyContainer.get(i).getInt("y"), orderlyContainer.get(i).getInt("z")};

            CompoundTag bag = new CompoundTag();

            bag.putIntArray("chestPos", pos);

            buttonNetwork.send("openChest", bag);

            minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.get());
        }

    }

    public void openChest(int[] i) {


        CompoundTag bag = new CompoundTag();

        bag.putIntArray("chestPos", i);

        buttonNetwork.send("openChest", bag);

        minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.get());


    }


    //  int i = (this.width - this.imageWidth) / 2;
    //  int j = (this.height - this.imageHeight) / 2;

    // System.out.println(r.count); && orderlyContainer.contains(3 * i + j)
    // System.out.println(r.x+"   "+r.y+"   "+ r.slidePos);

    //  pGuiGraphics.blit(screenImage, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
    //      (ChestBlockEntity)entity.getBlockState().getc
    // pGuiGraphics.drawString(minecraft.font, Objects.requireNonNull(entity.saveWithFullMetadata().get("CustomName")).toString(),22,6,M.getColor(205,205,205,255));

    //   System.out.println();

    //  public searchScreen(searchMenu pMenu, ArrayList<slot> slotList) {
//
    //      //      this(pMenu, Component.literal("附近的容器"), slotList);
    //      this(pMenu, null, Component.literal("附近的容器"));
//
    //  }

    //       System.out.println(menu);
    //    //   if (minecraft != null && minecraft.player != null) {
    //    //       System.out.println(minecraft.player.containerMenu);
    //    //   }

    //       System.out.println(minecraft);
    //  private final ContainerListener listener = new ContainerListener() {
    //      /**
    //       * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
    //       * contents of that slot.
    //       */
    //      public void slotChanged(AbstractContainerMenu p_99054_, int p_99055_, ItemStack p_99056_) {
//
    //      }
//
    //      public void dataChanged(AbstractContainerMenu p_169772_, int p_169773_, int p_169774_) {
    //          if (p_169773_ == 0) {
//
    //          }
//
    //      }
    //  };
    // protected void init() {
    //     super.init();
    //     this.menu.addSlotListener(this.listener);
    // }

    // public void onClose() {
    //     this.minecraft.player.closeContainer();
    //     super.onClose();
    // }

    // public void removed() {
    //     super.removed();
    //     this.menu.removeSlotListener(this.listener);
    // }
    //   System.out.println(orderlyContainer.get(i));

    // int[] pos = new int[]{orderlyContainer.get(i).getInt("x"), orderlyContainer.get(i).getInt("y"), orderlyContainer.get(i).getInt("z")};

    // CompoundTag bag = new CompoundTag();

    // bag.putIntArray("chestPos", pos);

    // buttonNetwork.send("openChest", bag);

    // minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK.get());

//    protected void createMenuControls() {
//        if (this.minecraft.player.mayBuild()) {
//            this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (p_99033_) -> {
//                this.onClose();
//            }).bounds(this.width / 2 - 100, 196, 98, 20).build());
//            this.addRenderableWidget(Button.builder(Component.translatable("lectern.take_book"), (p_99024_) -> {
//                this.sendButtonClick(3);
//            }).bounds(this.width / 2 + 2, 196, 98, 20).build());
//        } else {
//            super.createMenuControls();
//        }
//
//    }
//

    //   PoseStack.pushPose();
    //   PoseStack.translate((int) ((float) minecraft.getWindow().getGuiScaledWidth() / 2 + 87), (int) ((float) minecraft.getWindow().getGuiScaledHeight() / 2 - 35), 1000);
    //    PoseStack.pushPose();
    //  PoseStack.translate((int) ((float) minecraft.getWindow().getGuiScaledWidth() / 2 + 87), (int) ((float) minecraft.getWindow().getGuiScaledHeight() / 2 - 35), 1000);
    // PoseStack.popPose();
    //   event.getGuiGraphics().blit(new ResourceLocation(ChestSearch.MODID, "textures/gui/search_button.png"), 0, 0, 0, 0, 27, 25, 400, 400);
    //   PoseStack.popPose();
    //     if (tool.newVirtualButton(((float) R.getWidth() / 2 + 87), (int) ((float) R.getHeight() / 2 - 35), 27, 25)
    //     ) {
    //         R.renderTexture(event, ChestSearch.MODID, "textures/gui/search_button.png",//放在
    //                 -3, 0, 35, 0, 27, 33,
    //                 400, 400, PoseStack,
    //                 ((float) R.getWidth() / 2 + 87),
    //                 ((float) R.getHeight() / 2 - 35F), 800);
    //     }
    //   BlockState state=minecraft.level.getBlockState(E.getPos(new int[]{orderlyContainer.get(i).getInt("x"), orderlyContainer.get(i).getInt("y"), orderlyContainer.get(i).getInt("z")}));
    // System.out.println(data);
    //  if (itemsData.contains("Items")) {
    //      count = Objects.requireNonNull(itemsData.getList("Items", 10)).size();
//
    //  }

    // if (index >= orderlyContainer.size()) {//越界
    //     break;
    // }
    //  System.out.println(index+"    "+"563465466465345635");Math.min(index, orderlyContainer.size() - 1)

    //  public searchScreen(searchMenu pMenu) {

    //      this(pMenu, null, Component.literal("345"));

//         disorderContainer.get().forEach((key, value) -> {

    //             orderlyContainer.add(value);

    //         });

    //         System.out.println( this.disorderContainer.get());
    //         System.out.println( this.orderlyContainer);
    //         //System.out.println(orderlyContainer);
    // }

    //System.out.println(container.get());
    //  }
    /**
     * Moves the display back one page
     */
    protected void pageBack() {
        this.sendButtonClick(1);
    }

    /**
     * Moves the display forward one page
     */
    protected void pageForward() {
        this.sendButtonClick(2);
    }

    /**
     * I'm not sure why this exists. The function it calls is public and does all the work.
     */
    //  protected boolean forcePage(int pPageNum) {
//
    //          return false;
//
    //  }
    private void sendButtonClick(int pPageData) {
        if (this.minecraft != null) {
            if (this.minecraft.gameMode != null) {
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, pPageData);
            }
        }
    }

    // public boolean isPauseScreen() {
    //     return false;
    // }


    //  protected void closeScreen() {
    //      this.minecraft.player.closeContainer();
    //  }
}


//    public searchScreen(searchMenu pMenu, Component pTitle, ArrayList<slot> slotList) {
//  super(pTitle);
// }

//  public searchScreen(searchMenu searchMenu, Inventory inventory, Component component) {
//      super(Component.literal("附近的容器"));
//  }
