package com.integration_package_core.animation;

import com.integration_package_core.mixinTool.PlayerExpand;
import com.integration_package_core.tool.Maths;
import com.integration_package_core.tool.Render;
import com.integration_package_core.util.Stage;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.*;

import java.lang.Math;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class Storyboard implements Stage {

    public final Vector3f forwards = new Vector3f(0.0F, 0.0F, 1.0F);
    public final Vector3f up = new Vector3f(0.0F, 1.0F, 0.0F);
    public final Vector3f left = new Vector3f(1.0F, 0.0F, 0.0F);
    public Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
    public Vector3f startPos;//相对玩家坐标
    public Vector3f levelPos;
    public int time;
    public float reviseXRot = 0;
    public float reviseYRot = 0;
    public float reviseSpeed = 0.05f;

    public float reviseType = 0;

public boolean smooth=false;



    public Entity target;
    private Function<Double, Double> x = t ->  0.0;
    private Function<Double, Double> y = t ->  0.0;
    private Function<Double, Double> z = t ->  0.0;


    public void setXMove(Function<Double, Double> x){

        this.x=x;
    }
    public void setYMove(Function<Double, Double> y){

        this.y=y;
    }
    public void setZMove(Function<Double, Double> z){

        this.z=z;
    }









    public float yRotO;
    public float xRotO;
    private float yRot;
    private float xRot;
    private int timeEnd;

    public float xRotTarget;

    public float yRotTarget;
    public Storyboard(int time,boolean smooth) {
      this(time);
        this.smooth=smooth;
    }
    public Storyboard(int time) {
        this.time = time;
        this.target = Minecraft.getInstance().player;

        if (Minecraft.getInstance().player != null) {

            yRotTarget=   (    target).yRotO;
            xRotTarget=   (   target).xRotO;

            timeEnd = time + Minecraft.getInstance().player.tickCount;
            this.levelPos = Minecraft.getInstance().player.position().toVector3f();

        }

    }

    public void tick() {

        Storyboard s = this;
        s.xRotO = s.xRot;
        s.yRotO = s.yRot;

        float[] maxXY = Maths.getMaxWindowAngles();
        float x = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), s.target.xOld, s.target.getX());// (float) (( + l.getX()) / 2);
        float y = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), s.target.yOld + 1, s.target.getY() + 1);//(float) (( + l.getY()) / 2);
        float z = (float) Mth.lerp(Minecraft.getInstance().getPartialTick(), s.target.zOld, s.target.getZ());//(float) ((+ l.getZ()) / 2);

        float[] XY = s.getClipAngles(new Vector3f(x, y, z));


        float Y = getYClipAngles(new Vector3f(x, y, z));
        //   System.out.println(maxX);
        //     System.out.println(X - maxXY[1] );
        if (Y > maxXY[0] + s.reviseXRot) {//横//
           // System.out.println(1);
            if (s.reviseType == 0) {
                s.setYRot(s.getYRot() - reviseSpeed* Render.toTickFPS());
            } else if (s.reviseType == 1) {
                s.setYRot(s.getYRot() - Y * reviseSpeed* Render.toTickFPS());
            }
            // s.setYRot(s.getYRot() - reviseSpeed);
        } else if (Y < -maxXY[0] - s.reviseXRot) {
           // System.out.println(0);

            if (s.reviseType == 0) {
                s.setYRot(s.getYRot() + reviseSpeed* Render.toTickFPS());
            } else if (s.reviseType == 1) {

                s.setYRot(s.getYRot() - Y * reviseSpeed* Render.toTickFPS());
            }
            //    s.setYRot(s.getYRot() + reviseSpeed);
        }

        float X = getXClipAngles(new Vector3f(x, y, z));
      //  System.out.println(X);

        if (X > maxXY[1] + s.reviseXRot) {//竖
            if (s.reviseType == 0) {
                s.setXRot(s.getXRot() - reviseSpeed* Render.toTickFPS());
            } else if (s.reviseType == 1) {

                s.setXRot(s.getXRot() - X * reviseSpeed* Render.toTickFPS());
            }
        } else if (X < -maxXY[1] - s.reviseXRot) {

            if (s.reviseType == 0) {
                s.setXRot(s.getXRot() + reviseSpeed* Render.toTickFPS());
            } else if (s.reviseType == 1) {

                s.setXRot(s.getXRot() - X * reviseSpeed* Render.toTickFPS());
            }
            //      s.setXRot(s.getXRot() + reviseSpeed);
        }


    }

    public float[] getClipAngles(Vector3f v) {
//摄像机与目标实体之间的方向
        Vector3f camPos = camera.getPosition().toVector3f();
        Storyboard s = this;
        float lx = v.x - camPos.x;
        float ly = camPos.y - v.y;
        float lz = v.z - camPos.z;
        Vector3f direction = new Vector3f(-s.forwards.x, -s.forwards.y, s.forwards.z);//用向量计算偏移角  而不是xy

        Vector3f direction1 = new Vector3f(lx, ly, lz).normalize();

        double aaa = (Math.hypot(direction1.x, direction1.z) * Math.cos(Maths.getYClipAngles(
                new Vector3f(-direction.x, direction.y, direction.z),
                new Vector3f(direction1)) / 180 * Math.PI));
        //   System.out.println(direction1.y /aaa);
        float direction3 = (float) Math.atan(direction1.y / aaa);

        float X = (float) (Maths.getVertical(direction) - direction3 * 180 / Math.PI);

        float Y = Maths.getYClipAngles(
                new Vector3f(-direction.x, direction.y, direction.z).rotateAxis((float) (s.getXRot() / 180 * Math.PI), s.left.x, s.left.y, s.left.z),
                new Vector3f(direction1).rotateAxis((float) (s.getXRot() / 180 * Math.PI), s.left.x, s.left.y, s.left.z));//Maths.getHorizontal(direction) - Maths.getHorizontal(direction1);

        return new float[]{X, Y};

    }

    public float getXClipAngles(Vector3f v) {

        Vector3f camPos = camera.getPosition().toVector3f();
        Storyboard s = this;
        float lx = v.x - camPos.x;
        float ly = camPos.y - v.y;
        float lz = v.z - camPos.z;
        Vector3f direction = new Vector3f(-s.forwards.x, -s.forwards.y, s.forwards.z);//用向量计算偏移角  而不是xy
        Vector3f direction1 = new Vector3f(lx, ly, lz).normalize();
        double cos = Math.cos(Maths.getYClipAngles(
                new Vector3f(-direction.x, direction.y, direction.z),
                new Vector3f(direction1)) / 180 * Math.PI);//求平面偏移视角投影正视角的长度
        //   System.out.println("yRot:  "+getYClipAngles(v));
        double aaa = (Math.hypot(direction1.x, direction1.z) * cos);///(cos!=0?cos:1)
        //   System.out.println(direction1.y /aaa);
        aaa = aaa != 0 ? aaa : 1;

        float direction3 = (float) Math.atan(direction1.y / aaa);
        //   System.out.println("y:  "+direction1.y );
        //System.out.println(Maths.getVertical(direction)+"    "+direction3 * 180 / Math.PI);

        float X = (float) (Maths.getVertical(direction) - direction3 * 180 / Math.PI);

        return X;

    }


    public float getYClipAngles(Vector3f v) {
        Vector3f camPos = camera.getPosition().toVector3f();
        Storyboard s = this;
        float lx = v.x - camPos.x;
        float ly = camPos.y - v.y;
        float lz = v.z - camPos.z;
        Vector3f direction = new Vector3f(-s.forwards.x, -s.forwards.y, s.forwards.z);//用向量计算偏移角  而不是xy
        Vector3f direction1 = new Vector3f(lx, ly, lz).normalize();

        float Y = Maths.getYClipAngles(
                new Vector3f(-direction.x, direction.y, direction.z).rotateAxis((float) (s.getXRot() / 180 * Math.PI), s.left.x, s.left.y, s.left.z),
                new Vector3f(direction1).rotateAxis((float) (s.getXRot() / 180 * Math.PI), s.left.x, s.left.y, s.left.z));//Maths.getHorizontal(direction) - Maths.getHorizontal(direction1);

        return Y;
    }

    public float getXRot() {

        return xRot;
    }

    public void setXRot(float x) {

        xRot = x;
        Quaternionf rotation = new Quaternionf().rotationYXZ(-yRot * ((float) Math.PI / 180F), xRot * ((float) Math.PI / 180F), 0.0F);
        this.forwards.set(0.0F, 0.0F, 1.0F).rotate(rotation);
        this.up.set(0.0F, 1.0F, 0.0F).rotate(rotation);
        this.left.set(1.0F, 0.0F, 0.0F).rotate(rotation);

    }

    public float getYRot() {

        return yRot;
    }

    public void setYRot(float y) {

        yRot = y;
        Quaternionf rotation = new Quaternionf().rotationYXZ(-yRot * ((float) Math.PI / 180F), xRot * ((float) Math.PI / 180F), 0.0F);
        this.forwards.set(0.0F, 0.0F, 1.0F).rotate(rotation);
        this.up.set(0.0F, 1.0F, 0.0F).rotate(rotation);
        this.left.set(1.0F, 0.0F, 0.0F).rotate(rotation);

    }

    public void setRotate(float x, float y) {


    }

    public float getTime() {

        if (Minecraft.getInstance().player != null) {

            int tick = (time - (timeEnd - Minecraft.getInstance().player.tickCount));

            return (Mth.lerp(Minecraft.getInstance().getPartialTick(), Math.max(0, tick - 1), tick) / time);
        }
        return 0;
    }

    @Override
    public boolean isEnd() {

        if (Minecraft.getInstance().player != null) {

            //   int tick = (time - (timeEnd - Minecraft.getInstance().player.tickCount));

            return timeEnd - Minecraft.getInstance().player.tickCount < 0;
        }

        return false;
    }


    public Vector3f getLevelPosition() {

        return new Vector3f(levelPos);
    }

    public Vector3f getMovePosition() {
        float i = getTime();

      double x=this.x.apply((double) i);

        double y=this.y.apply((double) i);
        double z=this.z.apply((double) i);

        return new Vector3f(startPos).add(new Vector3f((float) x, (float) y, (float) z));
    }

    public float getX() {

        if(x==null)    return startPos.x;

        return (float) (startPos.x + x.apply((double)getTime()));
    }

    public float getY() {

        if(y==null)    return startPos.y;

        return (float) (startPos.y + y.apply((double)getTime()));
    }

    public float getZ() {

        if(z==null)    return startPos.z;

        return (float) (startPos.z + z.apply((double)getTime()));
    }


}
