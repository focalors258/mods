package com.chestsearch.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

import static com.chestsearch.gui.tool.*;

@OnlyIn(Dist.CLIENT)
public class roll {

    public static Map<Integer, Map<String, roll>> rollList = new HashMap<>();


    public static Minecraft minecraft = Minecraft.getInstance();

    public roll(float x, float y, float height, float width, int count, float maxLength, int containerId, String rollId, int direction, boolean isChoose) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.count = count;
        this.maxLength = maxLength;
        this.containerId = containerId;
        this.rollId = rollId;
        this.direction = direction;//0 竖 1 横

        if (!isChoose) {
            this.rollBefore = Integer.MIN_VALUE;
        }

        if (rollList.containsKey(containerId)) {

            rollList.get(containerId).put(rollId, this);

        } else {

            Map<String, roll> a = new HashMap<>();

            a.put(rollId, this);

            rollList.put(containerId, a);

        }


    }//function类中不能再用function定义函数

    public float x;

    public float y;

    public float height;//单个高度

    public float width;//单个宽度

    public float count;//目录数量

    public int rollBefore;//之前的选项

    public float oldMouseX = 0;

    public float oldMouseY = 0;

    public float direction;//横向 竖向


    public String rollId;


    public int containerId;

    public int rollMouse = 0;//鼠标现在的选项
    //Math.floor((MouseY -    public float slidePos - 20 + 12.5) /    public float height) //
    public float maxLength;//窗口呈现最大长度

    public float slidePos;//滑动坐标

    //tell(    public intslidePos)


    public int flag() {//放在界面上时

        // tell(this.rollMouse)

        // tell(this.direction )
        float rollBefore;
        if (this.rollBefore != Integer.MIN_VALUE) {
            rollBefore = this.rollBefore;
        } else {
            rollBefore = -9999999;
        }//是否可选择

        if (this.rollMouse != rollBefore
                && this.rollMouse >= 0
                // && this.rollMouse >= 0//选项为0默认解锁
                && this.buttonZone()

                && this.rollMouse <= count - 1
        ) {
            return this.rollMouse;
        } else {


            return -1;

        }
    }

    ;

    public void sliceScrol(float MouseScroll) {//滚轮滑动


        //  let MouseScroll = //getMouseScroll()//滚轮

        //   tell(MouseScroll)

        if (newVirtualButton(x, y, width, height * (1 + count))) {

            this.slidePos += (float) (MouseScroll * 7.5);

        }

    }

    ;

    public void sliceMouse(float MouseMove) {//鼠标滑动

        if (this.buttonZone()) {

            this.slidePos += (MouseMove);

        }

    }

    ;

    public boolean clickOutside() {//点击
        return minecraft.mouseHandler.xpos() == this.oldMouseX && minecraft.mouseHandler.ypos() == this.oldMouseY && mouseLeftOutside;

    }

    ;

    public boolean clickInside() {//点击
        return minecraft.mouseHandler.xpos() == this.oldMouseX && minecraft.mouseHandler.ypos() == this.oldMouseY && mouseLeftInside;

    }

    ;

    public void oldMouse() {//缓存

        this.oldMouseX = (float) minecraft.mouseHandler.xpos();

        this.oldMouseY = (float) minecraft.mouseHandler.ypos();
    }

    ;


    public boolean buttonZone() {//按键区域

        if (this.direction == 0) {

            return newVirtualButton(x, y, +width, height * (1 + count));

        } else if (this.direction == 1) {

            return newVirtualButton(x, y, width * (1 + count), height);

        }
        return false;
    }

    public void tick() {

        float length = this.maxLength;//最大呈现长度缓存

        float singleLength = 0;

        if (this.direction == 0) {

            this.rollMouse = (int) Math.floor((minecraft.mouseHandler.ypos()/minecraft.getWindow().getGuiScale() - this.slidePos - y ) / this.height); //鼠标的选项

            singleLength = height;

            if (this.maxLength == 0) {

                length = minecraft.getWindow().getGuiScaledHeight();

            }

        } else if (this.direction == 1) {

            this.rollMouse = (int) Math.floor((minecraft.mouseHandler.xpos()/minecraft.getWindow().getGuiScale()  - this.slidePos - x) / this.width);//鼠标的选项

            singleLength = width;

            if (this.maxLength == 0) {

                length = minecraft.getWindow().getGuiScaledWidth();

            }

        }

        // tell(this.slidePos)
        //  this.slice()

        //目录边界
        float exceed_roll = 0;

        float windowDiffer = count * singleLength - length;//可移动的窗口长度

        if (windowDiffer < 0) {

            exceed_roll = this.slidePos;// + ((副本_选项数量 * 50 - height) +副本_选项数量*50)//1420
            ///tell(exceed_roll)
        } else {

            exceed_roll = this.slidePos + ((count * singleLength - length));

        }

        // tell(rool.slidePos)

        if (this.slidePos > 0) {

            //   tell(this.slidePos)

            this.slidePos *= 0.95F;

            if (this.slidePos < 0.05) {

                this.slidePos *= 0;


            }//this.slidePos = 10//减少过小计算


        } else if (this.slidePos != 0 && exceed_roll < 0) {

            this.slidePos -= (float) ((exceed_roll) * 0.05);

            if (exceed_roll > -0.05) this.slidePos = -(exceed_roll - this.slidePos);//减少过小计算

        }
    }

    // RoolList[containerId] = {}
    //tell(rollId)
    //if(RollList[containerId]==undefined)RollList[containerId]=

    //初始化

    //  RollList[containerId][rollId]=this


    //priority:1080


}
