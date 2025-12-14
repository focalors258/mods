package com.integration_package_core.tool.collide;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix3f;
import org.joml.Vector3f;

public class OBB45 {


    public Vector3f pose = new Vector3f();
    public Vector3f pos = new Vector3f();
    public Matrix3f rotate = new Matrix3f();

    public OBB45(Entity e, float partialTick) {

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


    public OBB45(Entity e) {

        pos = e.position().toVector3f();

        AABB aabb = e.getBoundingBox();

        pose.x = (float) aabb.getXsize() / 2;
        pose.y = (float) aabb.getYsize() / 2;
        pose.z = (float) aabb.getZsize() / 2;

        pos.y += (pose.y);
        rotate.rotateY((float) (-e.yRotO / 180 * Math.PI));
    }

    public OBB45(AABB aabb) {

        pose.x = (float) aabb.getXsize() / 2;
        pose.y = (float) aabb.getYsize() / 2;
        pose.z = (float) aabb.getZsize() / 2;


        pos.x = (float) (aabb.minX + pose.x);
        pos.y = (float) (aabb.minY + pose.y);
        pos.z = (float) (aabb.minZ + pose.z);
    }

    public boolean collide(OBB45 b) {


        // boolean collide = false;

        OBB45 a = this;

        //以a为原点
        // Vector3f[] aa=a.vertex();//旋转前
        Vector3f[] pb = b.postVertex(a);//旋转后
        //  Vector3f v1 = a.rotate.transform(pos);
        for (int i = 0; i < 8; i++) {

            if (include(pb[i])) {

                return true;
            }
        }
        Vector3f[] pa = a.postVertex(b);//旋转后
        // Vector3f v2 = b.rotate.transform(b.pos);
        for (int i = 0; i < 8; i++) {

            if (b.include(pa[i])) {

                return true;
            }
        }
        return false;
    }

    //
    public boolean include(Vector3f v) {


        return v.x > pos.x - pose.x && v.x < pos.x + pose.x &&
                v.y > pos.y - pose.y && v.y < pos.y + pose.y &&
                v.z > pos.z - pose.z && v.z < pos.z + pose.z;


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


    public Vector3f[] levelVertex() {

        return new Vector3f[]{
                new Vector3f(-pose.x + pos.x, -pose.y + pos.y, -pose.z + pos.z),
                new Vector3f(pose.x + pos.x, -pose.y + pos.y, -pose.z + pos.z),
                new Vector3f(pose.x + pos.x, pose.y + pos.y, -pose.z + pos.z),
                new Vector3f(-pose.x + pos.x, pose.y + pos.y, -pose.z + pos.z),
                new Vector3f(-pose.x + pos.x, -pose.y + pos.y, pose.z + pos.z),
                new Vector3f(pose.x + pos.x, -pose.y + pos.y, pose.z + pos.z),
                new Vector3f(pose.x + pos.x, pose.y + pos.y, pose.z + pos.z),
                new Vector3f(-pose.x + pos.x, pose.y + pos.y, pose.z + pos.z)
        };
    }

    //将传入的立方体旋转角作为新坐标系
    public Vector3f[] postVertex(OBB45 thenM) {
        //  rotate.pos;//
        Vector3f v1 = pos;//thenM.rotate.transform(pos);//原点也需旋转

        Vector3f[] localVertices = poseVertex();
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
        Vector3f[] worldVertices = new Vector3f[8];
        // Vector3f tempTransformed = new Vector3f();

        Matrix3f thisM = new Matrix3f(rotate);//原本的角度

        for (int i = 0; i < localVertices.length; i++) {
            // a. 使用 Matrix3f 变换局部顶点 (旋转和缩放)
            thenM.rotate.transpose().mul(thisM);


            localVertices[i] = thisM.transform(localVertices[i]);//内旋转

            // b. 将变换后的局部顶点平移到世界空间中的位置
          //  localVertices[i] = thenM.rotate.transpose().transform(localVertices[i]);//外旋转


            worldVertices[i] = localVertices[i].add(v1);
            // thisM.mul(thenM.transpose());


        }

        return worldVertices;

    }

    public Vector3f[] postVertex() {
        //  rotate.

        Vector3f[] localVertices = poseVertex();

        Vector3f[] worldVertices = new Vector3f[8];
        Vector3f tempTransformed = new Vector3f();

        for (int i = 0; i < localVertices.length; i++) {
            // a. 使用 Matrix3f 变换局部顶点 (旋转和缩放)
            localVertices[i] = rotate.transform(localVertices[i]);

            // b. 将变换后的局部顶点平移到世界空间中的位置
            worldVertices[i] = localVertices[i].add(pos);

        }

        return worldVertices;

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

}

























