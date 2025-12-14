package com.integration_package_core.tool;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.codehaus.plexus.util.dag.Vertex;
import org.joml.Matrix3f;
import org.joml.Vector3f;

public class OrientedBox {


    Vector3f pose = new Vector3f();
    Vector3f pos = new Vector3f();
    Matrix3f rotate = new Matrix3f();

    public OrientedBox(Entity e) {

        pos = e.position().toVector3f();

        AABB aabb = e.getBoundingBox();

        pose.x = (float) aabb.getXsize() / 2;

        pose.y = (float) aabb.getYsize() / 2;

        pose.z = (float) aabb.getZsize() / 2;

        rotate.rotateZ(e.yRotO);

    }

    public OrientedBox(AABB aabb) {

        pose.x = (float) aabb.getXsize() / 2;

        pose.y = (float) aabb.getYsize() / 2;

        pose.z = (float) aabb.getZsize() / 2;


        pos.x = (float) (aabb.minX + pose.x);

        pos.y = (float) (aabb.minY + pose.y);

        pos.z = (float) (aabb.minZ + pose.z);
    }

    public boolean collide(OrientedBox b) {


        // boolean collide = false;

        OrientedBox a = this;

        //以a为原点
        // Vector3f[] aa=a.vertex();//旋转前
        Vector3f[] pb = b.postVertex(a.rotate);//旋转后

        for (int i = 0; i < 8; i++) {

            if (include(pb[i])) {

                return true;
            }
        }
        Vector3f[] pa = a.postVertex(b.rotate);//旋转后

        for (int i = 0; i < 8; i++) {

            if (b.include(pa[i])) {

                return true;
            }
        }
        return false;
    }


    public boolean include(Vector3f v) {


        return v.x > pos.x - pose.x && v.x < pos.x + pose.x &&
                v.y > pos.y - pose.y && v.y < pos.y + pose.y &&
                v.z > pos.z - pose.z && v.z < pos.z + pose.z;


    }


    public Vector3f[] vertex() {


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

    public Vector3f[] postVertex(Matrix3f m) {
        //  rotate.

        Vector3f[] localVertices = new Vector3f[]{
                new Vector3f(-pose.x, -pose.y, -pose.z),
                new Vector3f(pose.x, -pose.y, -pose.z),
                new Vector3f(pose.x, pose.y, -pose.z),
                new Vector3f(-pose.x, pose.y, -pose.z),
                new Vector3f(-pose.x, -pose.y, pose.z),
                new Vector3f(pose.x, -pose.y, pose.z),
                new Vector3f(pose.x, pose.y, pose.z),
                new Vector3f(-pose.x, pose.y, pose.z)
        };

        Vector3f[] worldVertices = new Vector3f[8];
        Vector3f tempTransformed = new Vector3f();
        Matrix3f m2 = new Matrix3f(rotate);
        for (int i = 0; i < localVertices.length; i++) {
            // a. 使用 Matrix3f 变换局部顶点 (旋转和缩放)

            m2.mul(m.transpose());


            m2.transform(localVertices[i], tempTransformed);

            // b. 将变换后的局部顶点平移到世界空间中的位置
            worldVertices[i] = tempTransformed.add(pos);

        }

        return worldVertices;

    }

    public Vector3f[] postVertex() {
        //  rotate.

        Vector3f[] localVertices = new Vector3f[]{
                new Vector3f(-pose.x, -pose.y, -pose.z),
                new Vector3f(pose.x, -pose.y, -pose.z),
                new Vector3f(pose.x, pose.y, -pose.z),
                new Vector3f(-pose.x, pose.y, -pose.z),
                new Vector3f(-pose.x, -pose.y, pose.z),
                new Vector3f(pose.x, -pose.y, pose.z),
                new Vector3f(pose.x, pose.y, pose.z),
                new Vector3f(-pose.x, pose.y, pose.z)
        };

        Vector3f[] worldVertices = new Vector3f[8];
        Vector3f tempTransformed = new Vector3f();

        for (int i = 0; i < localVertices.length; i++) {
            // a. 使用 Matrix3f 变换局部顶点 (旋转和缩放)
            rotate.transform(localVertices[i], tempTransformed);

            // b. 将变换后的局部顶点平移到世界空间中的位置
            worldVertices[i] = tempTransformed.add(pos);

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

























