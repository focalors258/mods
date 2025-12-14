package com.integration_package_core.tool.collide;

import com.integration_package_core.tool.DeBug;
import org.joml.Vector3f;

public class Line {


    public Vector3f point1;

    public Vector3f point2;


    public Line(Vector3f point1, Vector3f point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public static float dot(Vector3f a, Vector3f b) {
        //DeBug.tell(String.valueOf(a));
        double dotProduct = a.dot(b);

        // 2. 计算两个向量的模长
        double magA = a.length(); // |a|
        double magB = b.length(); // |b|

        // 3. 处理特殊情况：任一向量为零向量（模长为0），夹角无意义（返回0°或根据需求处理）
        if (magA < 1e-10 || magB < 1e-10) {
            // System.out.println("警告：存在零向量，夹角无意义！");
            return 0;
        }

        // 4. 计算余弦值（修正浮点数精度误差，确保在[-1.0, 1.0]范围内）
        double cos1 = dotProduct / (magA * magB);

     //  DeBug.tell(String.valueOf(cos1));

        cos1 = Math.max(-1.0, Math.min(1.0, cos1)); // 防止超出范围导致arccos报错

        // 5. 反余弦求弧度，再转换为角度
        float angleRadians = (float) Math.acos(cos1);
        float angleDegrees = (float) Math.toDegrees(angleRadians);

  //      DeBug.tell(String.valueOf(angleDegrees));
//   DeBug.tell(b.x +"    "+ b.y);
//
//        DeBug.tell(a.x +"    "+ a.y);
       // System.out.println();
        if (a.y > b.y) {
         //  DeBug.tell("a");
         //  angleDegrees = angleDegrees / Math.abs(angleDegrees) * 360 - angleDegrees;
        }
        //
        //

        return (float) angleDegrees;
    }


    public static Vector3f crossProduct(Vector3f a, Vector3f b) {
        float rx = a.y * b.z - a.z * b.y;
        float ry = a.z * b.x - a.x * b.z;
        float rz = a.x * b.y - a.y * b.x;
        return new Vector3f(rx, ry, rz);
    }


    public Vector3f verticalPoint(Vector3f P) {
        // 1. 计算向量 AB 和 AP
        double ABx = this.point2.x - this.point1.x;
        double ABy = this.point2.y - this.point1.y;
        double ABz = this.point2.z - this.point1.z;

        double APx = P.x - this.point1.x;
        double APy = P.y - this.point1.y;
        double APz = P.z - this.point1.z;

        // 2. 计算点积
        double dotProductAP_AB = APx * ABx + APy * ABy + APz * ABz;
        double dotProductAB_AB = ABx * ABx + ABy * ABy + ABz * ABz;

        // 3. 处理线段AB长度为0的特殊情况（A和B重合）
        if (dotProductAB_AB < 1e-10) { // 使用一个极小值来避免除以零
            return this.point1; // 或 B，因为它们是同一个点
        }

        // 4. 计算参数 t
        double t = dotProductAP_AB / dotProductAB_AB;

        // 5. 根据 t 的值确定最终的交点
        if (t <= 0.0) {
            return this.point1; // 投影点在A点或其延长线上，取A
        } else if (t >= 1.0) {
            return this.point2; // 投影点在B点或其延长线上，取B
        } else {
            // 投影点在线段AB内部，计算具体坐标
            double Qx = this.point1.x + t * ABx;
            double Qy = this.point1.y + t * ABy;
            double Qz = this.point1.z + t * ABz;
            return new Vector3f((float) Qx, (float) Qy, (float) Qz);
        }
    }


    public boolean passZ(float v) {
        return point1.z <= v && point2.z >= v || point1.z >= v && point2.z <= v;
    }

    public boolean passX(float v) {
        return point1.x <= v && point2.x >= v || point1.x >= v && point2.x <= v;
    }

    public boolean passY(float v) {

        return point1.y <= v && point2.y >= v || point1.y >= v && point2.y <= v;
    }

    public boolean passYFace(float faceY, float faceX1, float faceX2, float faceZ1, float faceZ2) {
        if (!passY(faceY)) {
            return false;
        }

        // 2. 计算线段的方向向量
        Vector3f direction = new Vector3f();
        point2.sub(point1, direction); // direction = point2 - point1

        // 3. 检查线段是否平行于Y平面（避免除以零）
        if (direction.y == 0.0f) {
            // 线段平行于平面，此时要么整个线段在平面上，要么不相交。
            // 这里简化处理，认为不相交。更精确的处理需要检查线段是否在矩形内。
            return false;
        }

        // 4. 使用参数方程计算交点
        float t = (faceY - point1.y) / direction.y;

        // 5. 检查 t 是否在线段范围内 [0, 1]
        if (t < 0.0f || t > 1.0f) {
            return false;
        }

        // 6. 计算交点的X和Z坐标
        float intersectX = point1.x + t * direction.x;
        float intersectZ = point1.z + t * direction.z;

        // 7. 检查交点是否在平面的矩形区域内
        // 注意：需要考虑 faceX1 可能大于 faceX2 的情况（例如参数传递顺序相反）
        float minX = Math.min(faceX1, faceX2);
        float maxX = Math.max(faceX1, faceX2);
        float minZ = Math.min(faceZ1, faceZ2);
        float maxZ = Math.max(faceZ1, faceZ2);

        return intersectX >= minX && intersectX <= maxX && intersectZ >= minZ && intersectZ <= maxZ;




        /*
        if (passY(faceY)) {
            Vector3f point12 = new Vector3f();

            point1.sub(point2, point12);//原点线

            float face2 = faceY - point2.y;//原点面

            float scale = 1 / point12.y * face2;//面坐标与x的比


            point1.mul(scale, point12);//缩放坐标至面

            return point12.x >= faceX1 && point12.x <= faceX2 &&
                    point12.z >= faceZ1 && point12.z <= faceZ2;

        }
        return false;
        */


    }

    public boolean passZFace(float faceZ, float faceX1, float faceX2, float faceY1, float faceY2) {

        if (!passZ(faceZ)) {
            return false;
        }

        // 2. 计算线段的方向向量
        Vector3f direction = new Vector3f();
        point2.sub(point1, direction); // direction = point2 - point1

        // 3. 检查线段是否平行于Y平面（避免除以零）
        if (direction.z == 0.0f) {
            // 线段平行于平面，此时要么整个线段在平面上，要么不相交。
            // 这里简化处理，认为不相交。更精确的处理需要检查线段是否在矩形内。
            return false;
        }

        // 4. 使用参数方程计算交点
        float t = (faceZ - point1.z) / direction.z;

        // 5. 检查 t 是否在线段范围内 [0, 1]
        if (t < 0.0f || t > 1.0f) {
            return false;
        }

        // 6. 计算交点的X和Z坐标
        float intersectX = point1.x + t * direction.x;
        float intersectY = point1.y + t * direction.y;

        // 7. 检查交点是否在平面的矩形区域内
        // 注意：需要考虑 faceX1 可能大于 faceX2 的情况（例如参数传递顺序相反）
        float minX = Math.min(faceX1, faceX2);
        float maxX = Math.max(faceX1, faceX2);
        float minZ = Math.min(faceY1, faceY2);
        float maxZ = Math.max(faceY1, faceY2);

        return intersectX >= minX && intersectX <= maxX && intersectY >= minZ && intersectY <= maxZ;







/*

        if (passZ(faceZ)) {
            Vector3f point12 = new Vector3f();
            point1.sub(point2, point12);//原点线
            float qm1 = faceZ - point2.z;//原点面
            float scale = 1 / point12.z * qm1;//面坐标与x的比
            point1.mul(scale, point12);//缩放坐标至面
            return point12.x >= faceX1 && point12.x <= faceX2 &&
                    point12.y >= faceY1 && point12.y <= faceY2;
        }
        return false;

 */
    }

    public boolean passXFace(float faceX, float faceZ1, float faceZ2, float faceY1, float faceY2) {


        if (!passX(faceX)) {
            return false;
        }

        // 2. 计算线段的方向向量
        Vector3f direction = new Vector3f();
        point2.sub(point1, direction); // direction = point2 - point1

        // 3. 检查线段是否平行于Y平面（避免除以零）
        if (direction.x == 0.0f) {
            // 线段平行于平面，此时要么整个线段在平面上，要么不相交。
            // 这里简化处理，认为不相交。更精确的处理需要检查线段是否在矩形内。
            return false;
        }

        // 4. 使用参数方程计算交点
        float t = (faceX - point1.x) / direction.x;

        // 5. 检查 t 是否在线段范围内 [0, 1]
        if (t < 0.0f || t > 1.0f) {
            return false;
        }

        // 6. 计算交点的X和Z坐标
        float intersectZ = point1.z + t * direction.z;
        float intersectY = point1.y + t * direction.y;

        // 7. 检查交点是否在平面的矩形区域内
        // 注意：需要考虑 faceX1 可能大于 faceX2 的情况（例如参数传递顺序相反）
        float minX = Math.min(faceZ1, faceZ2);
        float maxX = Math.max(faceZ1, faceZ2);
        float minZ = Math.min(faceY1, faceY2);
        float maxZ = Math.max(faceY1, faceY2);

        return intersectZ >= minX && intersectZ <= maxX && intersectY >= minZ && intersectY <= maxZ;









/*
        if (passX(faceX)) {
            Vector3f point12 = new Vector3f();
            point1.sub(point2, point12);//原点线
            float qm1 = faceX - point2.x;//原点面
            float scale = 1 / point12.x * qm1;//面坐标与x的比
            point1.mul(scale, point12);//缩放坐标至面
            return point12.z >= faceZ1 && point12.z <= faceZ2 &&
                    point12.y >= faceY1 && point12.y <= faceY2;
        }
        return false;

 */
    }


    public boolean passBlock(OBB o) {


        float zm = o.pos.z - o.pose.z + o.deflect.z;
        float ym = o.pos.z + o.pose.z + o.deflect.z;

        float sm = o.pos.y + o.pose.y + o.deflect.y;
        float xm = o.pos.y - o.pose.y + o.deflect.y;

        float qm = o.pos.x - o.pose.x + o.deflect.x;
        float hm = o.pos.x + o.pose.x + o.deflect.x;

        boolean a = passXFace(qm, zm, ym, xm, sm) ||
                passXFace(hm, zm, ym, xm, sm) ||
                passYFace(sm, qm, hm, zm, ym) ||
                passYFace(xm, qm, hm, zm, ym) ||
                passZFace(zm, qm, hm, xm, sm) ||
                passZFace(ym, qm, hm, xm, sm);
/*
        if(true){
            System.out.println("cnm");
        //    RenderSystem.setShaderColor(1f, 0.5f, 1f, 1);

            e.getPoseStack().pushPose();
            Render r = Render.of(e.getPoseStack());
            //    System.out.println(o.pose.x);
            Render.levelPoseStack(e.getPoseStack(), point2);
            Render.bindCamera(e.getPoseStack());
            e.getPoseStack().scale(0.05f, 0.05f, 0.05f);
            //    boolean a = o.collide(o1);
            //     System.out.println(a);
            RenderSystem.setShaderColor(0.2f, 0.2f, 1f, 1);
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

            e.getPoseStack().pushPose();

            //    System.out.println(o.pose.x);
            Render.levelPoseStack(e.getPoseStack(), point1);
            Render.bindCamera(e.getPoseStack());
            e.getPoseStack().scale(0.05f, 0.05f, 0.05f);
            //    boolean a = o.collide(o1);
            //     System.out.println(a);
            RenderSystem.setShaderColor(0.2f, 0.2f, 1f, 1);

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



        }
*/

        return a;


    }

    /**
     * 获取
     *
     * @return point1
     */
    public Vector3f getPoint1() {
        return point1;
    }

    /**
     * 设置
     *
     * @param point1
     */
    public void setPoint1(Vector3f point1) {
        this.point1 = point1;
    }

    /**
     * 获取
     *
     * @return point2
     */
    public Vector3f getPoint2() {
        return point2;
    }

    /**
     * 设置
     *
     * @param point2
     */
    public void setPoint2(Vector3f point2) {
        this.point2 = point2;
    }

    public String toString() {
        return "Line{point1 = " + point1 + ", point2 = " + point2 + "}";
    }
}
