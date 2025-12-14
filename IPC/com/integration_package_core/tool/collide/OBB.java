package com.integration_package_core.tool.collide;

import com.integration_package_core.tool.Render;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix3f;
import org.joml.Vector3f;

public class OBB {


    public static boolean snow = false;
    public Vector3f pose = new Vector3f(0, 0, 0);
    public Vector3f deflect = new Vector3f(0, 0, 0);
    public Vector3f pos = new Vector3f(0, 0, 0);
    public Matrix3f rotate = new Matrix3f();


    public OBB(float X1, float Y1, float Z1, float X2, float Y2, float Z2) {
        this(new AABB(X1, Y1, Z1, X2, Y2, Z2));
    }

    public OBB(Entity e, float partialTick) {
        pos = e.position().toVector3f();
        AABB aabb = e.getBoundingBox();
        pos.x = (float) Mth.lerp(partialTick, e.xOld, e.getX());
        pos.y = (float) Mth.lerp(partialTick, e.yOld, e.getY());
        pos.z = (float) Mth.lerp(partialTick, e.zOld, e.getZ());

        pose.x = (float) aabb.getXsize() / 2;
        pose.y = (float) aabb.getYsize() / 2;
        pose.z = (float) aabb.getZsize() / 2;

        pos.y += (pose.y);

        rotate.rotateY((float) (-e.yRotO / 180 * Math.PI));
    }

    public OBB(Entity e) {
        pos = e.position().toVector3f();

        AABB aabb = e.getBoundingBox();

        pose.x = (float) aabb.getXsize() / 2;
        pose.y = (float) aabb.getYsize() / 2;
        pose.z = (float) aabb.getZsize() / 2;

        pos.y += (pose.y);
        rotate.rotateY((float) (-e.yRotO / 180 * Math.PI));
    }

    public OBB() {
    }

    public OBB(AABB aabb) {

        pose.x = (float) aabb.getXsize() / 2;
        pose.y = (float) aabb.getYsize() / 2;
        pose.z = (float) aabb.getZsize() / 2;


        pos.x = (float) (aabb.minX + pose.x);
        pos.y = (float) (aabb.minY + pose.y);
        pos.z = (float) (aabb.minZ + pose.z);
    }

    public static OBB of() {
        return new OBB();
    }


    public static OBB of(float X1, float Y1, float Z1, float X2, float Y2, float Z2) {
        return new OBB(X1, Y1, Z1, X2, Y2, Z2);
    }

    public static OBB of(AABB aabb) {
        return new OBB(aabb);
    }

    public static OBB ofAABB(AABB aabb) {
        return new OBB(aabb);
    }

    public static OBB of(Entity aabb) {
        return new OBB(aabb);
    }

    public static OBB ofEntity(Entity aabb) {
        return new OBB(aabb);
    }

    public static OBB of(LivingEntity aabb) {
        return new OBB(aabb);
    }

    public static OBB of(Player aabb) {
        return new OBB(aabb);
    }

    public static OBB of(ServerPlayer aabb) {
        return new OBB(aabb);
    }

    public static OBB of(LocalPlayer aabb) {
        return new OBB(aabb);
    }

    public static boolean collide(OBB a, OBB b) {
        //a => +
        //b =>  -

        Vector3f[] v = a.relativeVertex(b);//a的叠加点

        Vector3f bbb = v[0];///---
        Vector3f aaa = v[6];///+++

        Vector3f abb = v[1];
        Vector3f baa = v[7];//点是倾斜的  不能直接加


        Vector3f aab = v[2];//++-
        Vector3f bab = v[3];//-+-
        Vector3f bba = v[4];//--+
        Vector3f aba = v[5];//+-+


        //  System.out.println(new Line(bbb, new Vector3f(bbb).add(b.pose.x, 0, 0)));
        return
                new Line(bbb, abb).passBlock(b) ||//推断线是否穿过面
                        new Line(bbb, bab).passBlock(b) ||
                        new Line(bbb, bba).passBlock(b) ||

                        new Line(aaa, baa).passBlock(b) ||
                        new Line(aaa, aba).passBlock(b) ||
                        new Line(aaa, aab).passBlock(b) ||

                        new Line(abb, bbb).passBlock(b) ||
                        new Line(abb, aab).passBlock(b) ||
                        new Line(abb, aba).passBlock(b) ||

                        new Line(baa, aaa).passBlock(b) ||
                        new Line(baa, bba).passBlock(b) ||
                        new Line(baa, bab).passBlock(b) ||
                        b.offsetInclude(bbb) ||//推断点是否在块内
                        b.offsetInclude(aaa) ;//!!!!!!!!!测试是否只需包括两点
                      //  b.offsetInclude(abb) ||
                      //  b.offsetInclude(baa) ||
                      //  b.offsetInclude(aab) ||
                      //  b.offsetInclude(bab) ||
                      //  b.offsetInclude(bba) ||
                      //  b.offsetInclude(aba);
    }

    public static void render(RenderLevelStageEvent e) {


        LocalPlayer p = Minecraft.getInstance().player;


        if (p != null && e.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES && snow) {

            OBB o1 = new OBB(p, Minecraft.getInstance().getPartialTick());

            o1.pose.z = 3;
            o1.pose.x = 3;
            o1.rotate.rotateX((float) (p.xRotO / 180 * Math.PI));
            o1.deflect.x -= 1;

            e.getPoseStack().pushPose();
            /// o1.renderPostVertex(e.getPoseStack());
            //  o1.renderLevelVertex(e.getPoseStack());
            e.getPoseStack().popPose();


            p.level().getEntities(null, AABB.ofSize(p.position(), 40, 40, 40)).forEach(en -> {

                OBB o = new OBB(en, Minecraft.getInstance().getPartialTick());
                o.rotate.rotateX((float) (en.xRotO / 180 * Math.PI));

                o.deflect.x -= 3;

                //   o.pose.x += 1;
                // o.pose = new Vector3f(3, 3, 3);

                if (en instanceof Player) {
                    return;
                }

                o.pose.z = 3;
                o.pose.x = 3;
                boolean a = o.collide(p.position().toVector3f());
                Vector3f v1 = o.aaaaa(p.position().toVector3f());
                for (Vector3f v : o.postVertex()) {

                    e.getPoseStack().pushPose();

                    e.getPoseStack().popPose();


                    e.getPoseStack().pushPose();
                    Render r = Render.of(e.getPoseStack());

                    Render.levelPoseStack(e.getPoseStack(), v);
                    Render.bindCamera(e.getPoseStack());
                    e.getPoseStack().scale(0.05f, 0.05f, 0.05f);
                    ;
                    if (a) RenderSystem.setShaderColor(10, 0.5f, 0.5f, 1);
                    r.guiGraphics.blit(
                            r.texture,
                            -1,  // X坐标
                            -1,                  // Y坐标
                            0,            // 纹理UV X
                            0,                // 纹理UV Y
                            2,             // 宽度
                            2,                  // 高度
                            2, 2);
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                    e.getPoseStack().popPose();
                }


                if (!(en instanceof Player)) {

                    e.getPoseStack().pushPose();
                    Render r = Render.of(e.getPoseStack());

                    Render.levelPoseStack(e.getPoseStack(), v1);
                    Render.bindCamera(e.getPoseStack());
                    e.getPoseStack().scale(0.05f, 0.05f, 0.05f);
                    ;
                    RenderSystem.setShaderColor(0.1f, 0.5f, 0.5f, 1);
                    r.guiGraphics.blit(
                            r.texture,
                            -1,  // X坐标
                            -1,                  // Y坐标
                            0,            // 纹理UV X
                            0,                // 纹理UV Y
                            2,             // 宽度
                            2,                  // 高度
                            2, 2);
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                    e.getPoseStack().popPose();
                }


                if (!(en instanceof Player) && false) {
                    for (Vector3f v : o.relativeVertex(o1)) {

                        e.getPoseStack().pushPose();
                        Render r = Render.of(e.getPoseStack());
                        //    System.out.println(o.pose.x);
                        Render.levelPoseStack(e.getPoseStack(), v);
                        Render.bindCamera(e.getPoseStack());
                        e.getPoseStack().scale(0.05f, 0.05f, 0.05f);
                        //    boolean a = o.collide(o1);
                        //     System.out.println(a);
                        RenderSystem.setShaderColor(0.5f, 1, 0.5f, 1);

                        r.guiGraphics.blit(
                                r.texture,
                                -1,  // X坐标
                                -1,                  // Y坐标
                                0,            // 纹理UV X
                                0,                // 纹理UV Y
                                2,             // 宽度
                                2,                  // 高度
                                2, 2);

                        e.getPoseStack().popPose();

                        RenderSystem.setShaderColor(1, 1, 1, 1);
                    }

                }


            });


        }//受伤边框


    }

    public OBB setXRot(float v) {
        this.rotate.rotateX(v);
        return this;
    }

    public OBB setYRot(float v) {
        this.rotate.rotateY(v);
        return this;
    }

    public OBB setZRot(float v) {
        this.rotate.rotateZ(v);
        return this;
    }

    public OBB setDeflect(float x, float y, float z) {


        this.deflect.x = x;
        this.deflect.y = y;
        this.deflect.z = z;


        return this;
    }

    public boolean collide(OBB a) {

        return collide(this, a) || collide(a, this);

    }

    //+--
//-++

    public Vector3f[] levelVertex() {

        return new Vector3f[]{
                new Vector3f(-pose.x + pos.x, -pose.y + pos.y, -pose.z + pos.z),//---
                new Vector3f(pose.x + pos.x, -pose.y + pos.y, -pose.z + pos.z),//+--
                new Vector3f(pose.x + pos.x, pose.y + pos.y, -pose.z + pos.z),//++-
                new Vector3f(-pose.x + pos.x, pose.y + pos.y, -pose.z + pos.z),//-+-
                new Vector3f(-pose.x + pos.x, -pose.y + pos.y, pose.z + pos.z),//--+
                new Vector3f(pose.x + pos.x, -pose.y + pos.y, pose.z + pos.z),//+-+
                new Vector3f(pose.x + pos.x, pose.y + pos.y, pose.z + pos.z),//+++
                new Vector3f(-pose.x + pos.x, pose.y + pos.y, pose.z + pos.z)//-++
        };
    }

    public boolean offsetInclude(Vector3f v) {

        return v.x >= pos.x - pose.x+deflect.x && v.x <= pos.x + pose.x +deflect.x&&
                v.y >= pos.y - pose.y +deflect.y&& v.y <= pos.y + pose.y +deflect.y&&
                v.z >= pos.z - pose.z +deflect.z&& v.z <= pos.z + pose.z+deflect.z;






    }
    public boolean include(Vector3f v) {
       // float zm = o.pos.z - o.pose.z + this.deflect.z;
       // float ym = o.pos.z + o.pose.z + this.deflect.z;
//
       // float sm = o.pos.y + o.pose.y + this.deflect.y;
       // float xm = o.pos.y - o.pose.y + this.deflect.y;
//
       // float qm = o.pos.x - o.pose.x + o.deflect.x;
       // float hm = o.pos.x + o.pose.x + o.deflect.x;
        //    System.out.println((pos.x + pose.x)+"   "+v.x+"   "+(pos.x - pose.x));
        return v.x >= pos.x - pose.x && v.x <= pos.x + pose.x &&
                v.y >= pos.y - pose.y && v.y <= pos.y + pose.y &&
                v.z >= pos.z - pose.z && v.z <= pos.z + pose.z;


    }

    public Vector3f[] poseVertex() {

        return new Vector3f[]{
                new Vector3f(-pose.x, -pose.y, -pose.z),
                new Vector3f(pose.x, -pose.y, -pose.z),
                new Vector3f(pose.x, pose.y, -pose.z),
                new Vector3f(-pose.x, pose.y, -pose.z),
                new Vector3f(-pose.x, -pose.y, pose.z),
                new Vector3f(pose.x, -pose.y, pose.z),
                new Vector3f(pose.x, pose.y, pose.z),
                new Vector3f(-pose.x, pose.y, pose.z)
        };

    }

    private Vector3f aaaaa(Vector3f p) {


        Vector3f p1 = new Vector3f(p);

        p1 = p1.add(deflect);//偏移坐标

        p1 = p1.sub(this.pos);//

        Matrix3f thenM = new Matrix3f(this.rotate).transpose();//相对旋转

        thenM.transform(p1);

        p1 = p1.add(this.pos);//相对旋转

        return p1;

    }

    public boolean collide(Vector3f p) {

        Vector3f p1 = new Vector3f(p);


        p1 = p1.sub(this.pos);//

        Matrix3f thenM = new Matrix3f(this.rotate).transpose();//相对旋转
        thenM.transform(p1);

        p1 = p1.sub(deflect);//偏移坐标

        p1 = p1.add(this.pos);//相对旋转

        return include(p1);
    }

    //将传入的立方体旋转角作为新坐标系
    public Vector3f[] relativeVertex(OBB obb1) {
        Vector3f[] localVertices = poseVertex();
        Matrix3f thisM = new Matrix3f(rotate);//原本的角度

        for (int i = 0; i < localVertices.length; i++) {

            localVertices[i] = localVertices[i].add(deflect);//偏移坐标
            localVertices[i] = thisM.transform(localVertices[i]);//内旋转
            localVertices[i] = localVertices[i].add(pos);//世界坐标
            localVertices[i] = localVertices[i].sub(obb1.pos);//
            // localVertices[i] = localVertices[i].sub(deflect);//偏移坐标
            //    System.out.println(obb1.pos);
            Matrix3f thenM = new Matrix3f(obb1.rotate).transpose();//相对旋转
            thenM.transform(localVertices[i]);
            //        localVertices[i] = localVertices[i].add(deflect);//偏移坐标
            localVertices[i] = localVertices[i].add(obb1.pos);//相对旋转
        }
        return localVertices;
    }

    public AABB postAABB() {

        Matrix3f rotate1 = new Matrix3f(rotate);
        Vector3f pos = new Vector3f(deflect);

        pos = rotate1.transform(pos);
        pos = pos.add(this.pos);//偏移坐标

        float r = Math.max(Math.abs(pose.z), Math.max(Math.abs(pose.z), Math.abs(pose.x)));

        return new AABB(pos.x - r, pos.y - r, pos.z - r, pos.x + r, pos.y + r, pos.z + r);
    }

    public Vector3f[] postVertex() {
        //  rotate.

        Vector3f[] localVertices = poseVertex();

        Vector3f[] worldVertices = new Vector3f[8];
        //Vector3f tempTransformed = new Vector3f();

        for (int i = 0; i < localVertices.length; i++) {

            Matrix3f rotate1 = new Matrix3f(rotate);

            localVertices[i] = localVertices[i].add(deflect);//偏移坐标

            localVertices[i] = rotate1.transform(localVertices[i]);


            worldVertices[i] = localVertices[i].add(pos);
        }

        return worldVertices;

    }

    public void renderPostVertex(PoseStack poseStack) {

        OBB o = this;

        for (Vector3f v : o.postVertex()) {

            Render r = Render.of(poseStack);
            Render e = r;
            e.getPoseStack().pushPose();
            RenderSystem.setShaderColor(1f, 1, 1f, 1);
            Render.levelPoseStack(e.getPoseStack(), v);
            Render.bindCamera(e.getPoseStack());
            e.getPoseStack().scale(0.05f, 0.05f, 0.05f);
            r.guiGraphics.blit(
                    r.texture,
                    -1,  // X坐标
                    -1,                  // Y坐标
                    0,            // 纹理UV X
                    0,                // 纹理UV Y
                    2,             // 宽度
                    2,                  // 高度
                    2, 2);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            e.getPoseStack().popPose();
        }
    }

    public void renderLevelVertex(PoseStack poseStack) {

        OBB o = this;
        //System.out.println(456);
        for (Vector3f v : o.levelVertex()) {
            //   System.out.println(v);
            Render e = Render.of(poseStack);
            Render r = e;
            e.getPoseStack().pushPose();
            RenderSystem.setShaderColor(0.5f, 1, 1f, 1);
            Render.levelPoseStack(e.getPoseStack(), v);
            Render.bindCamera(e.getPoseStack());
            e.getPoseStack().scale(0.1f, 0.1f, 0.1f);
            //     System.out.println(a);
            // RenderSystem.setShaderColor(10, 0.5f, 0.5f, 1);
            r.guiGraphics.blit(
                    r.texture,
                    -1,  // X坐标
                    -1,                  // Y坐标
                    0,            // 纹理UV X
                    0,                // 纹理UV Y
                    2,             // 宽度
                    2,                  // 高度
                    2, 2);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            e.getPoseStack().popPose();
        }
    }


//return  new Vector3d[]{
//
//            new Vector3d(change.m30()- shape.x, change.m30()- shape.y, change.m30()- shape.z),
//            new Vector3d(change.m30()+ shape.x, change.m30()+ shape.y, change.m30()+ shape.z),
//            new Vector3d(change.m30()+ shape.x, change.m30()- shape.y, change.m30()- shape.z),
//            new Vector3d(change.m30()- shape.x, change.m30()+ shape.y, change.m30()- shape.z),
//            new Vector3d(change.m30()- shape.x, change.m30()- shape.y, change.m30()+ shape.z),
//            new Vector3d(change.m30()+ shape.x, change.m30()+ shape.y, change.m30()- shape.z),
//            new Vector3d(change.m30()- shape.x, change.m30()+ shape.y, change.m30()+ shape.z),
//            new Vector3d(change.m30()+ shape.x, change.m30()- shape.y, change.m30()+ shape.z)};

//  float l1=      bbb.x+2*b.pose.x;
//        float l2=      bbb.y+2*b.pose.y;
//        float l3=      bbb.z+2*b.pose.z;
//
//        float l4=      bbb.y+2*b.pose.x;
//        float l5=      bbb.y+2*b.pose.y;
//        float l6=      bbb.y+2*b.pose.z;
//
//        float l7=      bbb.y+2*b.pose.x;
//        float l8=      bbb.y+2*b.pose.y;
//        float l9=      bbb.y+2*b.pose.z;
//
//        float l11=      bbb.y+2*b.pose.x;
//        float l12=      bbb.y+2*b.pose.y;
//        float l13=      bbb.y+2*b.pose.z;
    // thenM;
//
    // thisM.mul(thenM);
    // thisM.mul(thenM.transpose());
           /*
                new Vector3f[]{
                new Vector3f(-pose.x + v1.x, -pose.y + v1.y, -pose.z + v1.z),
                new Vector3f(pose.x + v1.x, -pose.y + v1.y, -pose.z + v1.z),
                new Vector3f(pose.x + v1.x, pose.y + v1.y, -pose.z + v1.z),
                new Vector3f(-pose.x + v1.x, pose.y + v1.y, -pose.z + v1.z),
                new Vector3f(-pose.x + v1.x, -pose.y + v1.y, pose.z + v1.z),
                new Vector3f(pose.x + v1.x, -pose.y + v1.y, pose.z + v1.z),
                new Vector3f(pose.x + v1.x, pose.y + v1.y, pose.z + v1.z),
                new Vector3f(-pose.x + v1.x, pose.y + v1.y, pose.z + v1.z)
        };
*/
    // Vector3f[] worldVertices = new Vector3f[8];
    // Vector3f tempTransformed = new Vector3f();
}

