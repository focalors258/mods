package com.integration_package_core.tool.collide;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Vector3f;


public class OBB68 {
    public Vec3 pos;
    public Vec3 pose;
    public Vec3 axisX;
    public Vec3 axisY;
    public Vec3 axisZ;
    public Vec3 scaledAxisX;
    public Vec3 scaledAxisY;
    public Vec3 scaledAxisZ;
    public Matrix3f rotation = new Matrix3f();
    public Vec3 vertex1;
    public Vec3 vertex2;
    public Vec3 vertex3;
    public Vec3 vertex4;
    public Vec3 vertex5;
    public Vec3 vertex6;
    public Vec3 vertex7;
    public Vec3 vertex8;
    public Vec3[] vertices;
    private float yRot = 0;
    private float xRot = 0;


    public OBB68(Vec3 center, double width, double height, double depth, float yaw, float pitch) {

        this.pos = center;
        this.pose = new Vec3(width / 2.0, height / 2.0, depth / 2.0);
        this.axisZ = Vec3.directionFromRotation(yaw, pitch).normalize();
        this.axisY = Vec3.directionFromRotation(yaw + 90.0F, pitch).normalize().normalize();
        this.axisX = this.axisZ.cross(this.axisY);

        updateVertex();

    }

    public OBB68(Vec3 center, Vec3 size, float yaw, float pitch) {
        this(center, size.x, size.y, size.z, yaw, pitch);

    }

    public OBB68(Entity e) {

        this(e.getBoundingBox());

        setYRot(e.yRotO);
        updateVertex();
        //     rotation.rotateX((float) (-e.yRotO / 180 * Math.PI));
    }

    public OBB68(AABB box) {

        this.pos = new Vec3((box.maxX + box.minX) / 2.0, (box.maxY + box.minY) / 2.0, (box.maxZ + box.minZ) / 2.0);
        this.pose = new Vec3(Math.abs(box.maxX - box.minX) / 2.0, Math.abs(box.maxY - box.minY) / 2.0, Math.abs(box.maxZ - box.minZ) / 2.0);
        this.axisX = new Vec3(1.0, 0.0, 0.0);
        this.axisY = new Vec3(0.0, 1.0, 0.0);
        this.axisZ = new Vec3(0.0, 0.0, 1.0);
        updateVertex();
    }

    public OBB68(OBB68 obb) {
        rotation = obb.rotation;
        this.pos = obb.pos;
        this.pose = obb.pose;
        this.axisX = obb.axisX;
        this.axisY = obb.axisY;
        this.axisZ = obb.axisZ;
        updateVertex();
    }

    public static boolean collide(OBB68 a, OBB68 b) {


        //  System.out.println(Arrays.toString(b.vertices));

        if (a.vertices == null || b.vertices == null) return false;

        if (separated(a.vertices, b.vertices, a.scaledAxisX)) {
            return false;
        } else if (separated(a.vertices, b.vertices, a.scaledAxisY)) {
            return false;
        } else if (separated(a.vertices, b.vertices, a.scaledAxisZ)) {
            return false;
        } else if (separated(a.vertices, b.vertices, b.scaledAxisX)) {
            return false;
        } else if (separated(a.vertices, b.vertices, b.scaledAxisY)) {
            return false;
        } else if (separated(a.vertices, b.vertices, b.scaledAxisZ)) {
            return false;
        } else if (separated(a.vertices, b.vertices, a.scaledAxisX.cross(b.scaledAxisX))) {
            return false;
        } else if (separated(a.vertices, b.vertices, a.scaledAxisX.cross(b.scaledAxisY))) {
            return false;
        } else if (separated(a.vertices, b.vertices, a.scaledAxisX.cross(b.scaledAxisZ))) {
            return false;
        } else if (separated(a.vertices, b.vertices, a.scaledAxisY.cross(b.scaledAxisX))) {
            return false;
        } else if (separated(a.vertices, b.vertices, a.scaledAxisY.cross(b.scaledAxisY))) {
            return false;
        } else if (separated(a.vertices, b.vertices, a.scaledAxisY.cross(b.scaledAxisZ))) {
            return false;
        } else if (separated(a.vertices, b.vertices, a.scaledAxisZ.cross(b.scaledAxisX))) {
            return false;
        } else if (separated(a.vertices, b.vertices, a.scaledAxisZ.cross(b.scaledAxisY))) {
            return false;
        } else {
            return !separated(a.vertices, b.vertices, a.scaledAxisZ.cross(b.scaledAxisZ));
        }
    }

    private static boolean separated(Vec3[] vertsA, Vec3[] vertsB, Vec3 axis) {


        if (axis.lengthSqr() < 1e-9) { // 使用长度的平方来判断是否为零向量，避免浮点数精度问题
            return false;
        }

        if (axis.equals(Vec3.ZERO)) {//有bug
            return false;
        } else {
            double aMin = Double.POSITIVE_INFINITY;
            double aMax = Double.NEGATIVE_INFINITY;
            double bMin = Double.POSITIVE_INFINITY;
            double bMax = Double.NEGATIVE_INFINITY;

            for (int i = 0; i < 8; ++i) {
                double aDist = vertsA[i].dot(axis);
                aMin = aDist < aMin ? aDist : aMin;
                aMax = aDist > aMax ? aDist : aMax;
                double bDist = vertsB[i].dot(axis);
                bMin = bDist < bMin ? bDist : bMin;
                bMax = bDist > bMax ? bDist : bMax;
            }

            double longSpan = Math.max(aMax, bMax) - Math.min(aMin, bMin);
            double sumSpan = aMax - aMin + bMax - bMin;
            return longSpan >= sumSpan;
        }
    }

    public float getXRot() {
        return xRot;

    }

    public void setXRot(float x) {
        xRot = x;
        this.axisZ = Vec3.directionFromRotation(x, yRot).normalize();
        this.axisY = Vec3.directionFromRotation(x + 90.0F, yRot).normalize().normalize();
        this.axisX = this.axisZ.cross(this.axisY);
        updateVertex();
    }

    public float getYRot() {
        return yRot;

    }

    public void setYRot(float y) {
        this.yRot = y;
        this.axisZ = Vec3.directionFromRotation(xRot, y).normalize();
        this.axisY = Vec3.directionFromRotation(xRot + 90.0F, y).normalize().normalize();
        this.axisX = this.axisZ.cross(this.axisY);
        updateVertex();
    }

    public OBB68 copy() {
        return new OBB68(this);
    }

    public OBB68 offsetAlongAxisX(double offset) {
        this.pos = this.pos.add(this.axisX.scale(offset));
        return this;
    }

    public OBB68 offsetAlongAxisY(double offset) {
        this.pos = this.pos.add(this.axisY.scale(offset));
        return this;
    }

    public OBB68 offsetAlongAxisZ(double offset) {
        this.pos = this.pos.add(this.axisZ.scale(offset));
        return this;
    }

    public OBB68 offset(Vec3 offset) {
        this.pos = this.pos.add(offset);
        return this;
    }

    public OBB68 scale(double scale) {
        this.pose = this.pose.scale(scale);
        return this;
    }

    public Vec3[] vertex() {

        updateVertex();

        return vertices;
    }

    public OBB68 updateVertex() {

        //System.out.println(rotation);

        this.rotation.set(0, 0, (float) this.axisX.x);
        this.rotation.set(0, 1, (float) this.axisX.y);
        this.rotation.set(0, 2, (float) this.axisX.z);
        this.rotation.set(1, 0, (float) this.axisY.x);
        this.rotation.set(1, 1, (float) this.axisY.y);
        this.rotation.set(1, 2, (float) this.axisY.z);
        this.rotation.set(2, 0, (float) this.axisZ.x);
        this.rotation.set(2, 1, (float) this.axisZ.y);
        this.rotation.set(2, 2, (float) this.axisZ.z);
        this.scaledAxisX = this.axisX.scale(this.pose.x);
        this.scaledAxisY = this.axisY.scale(this.pose.y);
        this.scaledAxisZ = this.axisZ.scale(this.pose.z);
        this.vertex1 = this.pos.subtract(this.scaledAxisZ).subtract(this.scaledAxisX).subtract(this.scaledAxisY);
        this.vertex2 = this.pos.subtract(this.scaledAxisZ).add(this.scaledAxisX).subtract(this.scaledAxisY);
        this.vertex3 = this.pos.subtract(this.scaledAxisZ).add(this.scaledAxisX).add(this.scaledAxisY);
        this.vertex4 = this.pos.subtract(this.scaledAxisZ).subtract(this.scaledAxisX).add(this.scaledAxisY);
        this.vertex5 = this.pos.add(this.scaledAxisZ).subtract(this.scaledAxisX).subtract(this.scaledAxisY);
        this.vertex6 = this.pos.add(this.scaledAxisZ).add(this.scaledAxisX).subtract(this.scaledAxisY);
        this.vertex7 = this.pos.add(this.scaledAxisZ).add(this.scaledAxisX).add(this.scaledAxisY);
        this.vertex8 = this.pos.add(this.scaledAxisZ).subtract(this.scaledAxisX).add(this.scaledAxisY);
        this.vertices = new Vec3[]{this.vertex1, this.vertex2, this.vertex3, this.vertex4, this.vertex5, this.vertex6, this.vertex7, this.vertex8};
        return this;
    }

    public boolean contains(Vec3 point) {
        Vector3f distance = point.subtract(this.pos).toVector3f();
        distance.mulTranspose(this.rotation);
        return (double) Math.abs(distance.x()) < this.pose.x && (double) Math.abs(distance.y()) < this.pose.y && (double) Math.abs(distance.z()) < this.pose.z;
    }

    public boolean collide(AABB boundingBox) {
        OBB68 otherOBB = (new OBB68(boundingBox)).updateVertex();
        updateVertex();
        return collide(this, otherOBB);
    }

    public boolean collide(OBB68 otherOBB) {
        updateVertex();
        otherOBB.updateVertex();
        return collide(this, otherOBB);
    }
}








