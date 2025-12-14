package com.integration_package_core.tool;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.awt.geom.Point2D;

public class Maths {


    public static Minecraft mc = Minecraft.getInstance();

    public static float getCycle(float z1) {

        float z2;

        if (z1 < 0) {
            z2 = Math.abs(z1);
        } else {
            z2 = z1;
        }

        return z1 / z2 * ((z2 + 180) % 360 - 180);
    }

    public static float[] getMaxWindowAngles() {
        float fov = Minecraft.getInstance().options.fov().get() * 0.5f;
        float base = (float) 1 / mc.getWindow().getHeight() * mc.getWindow().getWidth();
        float y = (float) Math.atan(base * Math.tan(fov * Math.PI / 180));

        return new float[]{(float) (y * 180 / Math.PI), fov};

    }


    public static float getYClipAngles(Vector3f direction, Vector3f direction1) {//计算两方向向量夹角

        Vector3f direction3 = new Vector3f(direction1.x, 0, direction1.z);//注意 不统计三维y轴导致俯仰角超过+-90度后偏航角计算将错误

        Vector3f direction4 = new Vector3f(direction.x, 0, direction.z);


        //   Vector3f direction2 = new Vector3f();

        //   Quaternionf rotationQ = new Quaternionf().rotationTo(direction3, new Vector3f(0, 0, 1));

        //   direction4.rotate(rotationQ, direction2);
        //   // System.out.println(direction2.y);
        //   return getHorizontal(-direction2.x, -direction2.z);


        float counter = 1;
        float cross = direction3.x * direction4.z - direction3.z * direction4.x;

    //    System.out.println(cross);
        if (cross < 0) counter = -1;


        float legnht= (float) (Math.sqrt(direction3.x * direction3.x + direction3.z * direction3.z) * Math.sqrt(direction4.x * direction4.x + direction4.z * direction4.z));

        if(legnht==0)return 0;

        float a = (float) ((direction3.x * direction4.x + direction3.z * direction4.z) /legnht);

   //     System.out.println(a);


        return (float) (180 / Math.PI * Math.acos(a)) * counter;

    }







    //不会导致超过90度  但正负相反
    public static float[] getClipAngles(Vector3fc direction, Vector3fc direction1) {//计算两方向向量夹角
        Vector3f direction2 = new Vector3f();

        Quaternionf rotationQ = new Quaternionf().rotationTo(direction1, new Vector3f(0, 0, 1));

        direction.rotate(rotationQ, direction2);
        // System.out.println(direction2.y);
        return new float[]{getHorizontal(-direction2.x, -direction2.z), getVertical(direction2.x, direction2.y, direction2.z)};
    }


    //会导致超过90度
    public static float[] calculateRelativeAngles(Vector3fc originalDir, Vector3fc targetDir) {
        // 1. 归一化向量（角度计算依赖单位向量）
        Vector3f normOrig = new Vector3f(originalDir).normalize();
        Vector3f normTarget = new Vector3f(targetDir).normalize();
        final float EPS = 1e-6f;

        // 2. 校验非零向量
        if (normOrig.lengthSquared() < EPS || normTarget.lengthSquared() < EPS) {
            throw new IllegalArgumentException("方向向量不能为零");
        }

        // 3. 计算原向量的绝对偏航/俯仰
        float origYaw = (float) Math.toDegrees(Math.atan2(normOrig.x(), normOrig.z()));
        origYaw = origYaw < 0 ? origYaw + 360 : origYaw; // 转[0,360)
        float origPitch = (float) Math.toDegrees(Math.atan2(normOrig.y(), Math.hypot(normOrig.x(), normOrig.z())));

        // 4. 计算目标向量的绝对偏航/俯仰
        float targetYaw = (float) Math.toDegrees(Math.atan2(normTarget.x(), normTarget.z()));
        targetYaw = targetYaw < 0 ? targetYaw + 360 : targetYaw; // 转[0,360)
        float targetPitch = (float) Math.toDegrees(Math.atan2(normTarget.y(), Math.hypot(normTarget.x(), normTarget.z())));

        // 5. 计算相对角度（偏航归一化到[-180,180]，俯仰直接差值）
        float relYaw = targetYaw - origYaw;
        relYaw = relYaw > 180 ? relYaw - 360 : (relYaw < -180 ? relYaw + 360 : relYaw);
        float relPitch = targetPitch - origPitch;

        return new float[]{relYaw, relPitch};
    }

    public static Vector3f getXYVec3(float xr, float yr) {

        float o = (float) (yr * Math.PI / 180);

        float z = (float) Math.cos(o);
        //   System.out.println(z);
        float x = (float) Math.sin(o);

        float tan = (float) Math.tan(xr * Math.PI / 180);
        float y;//= //(float) (Math.sqrt (   x*x+z*z)/tan);
        y = (float) (Math.sqrt(x * x + z * z) * tan);
        //  if(tan==0){
        //      y= Float.MAX_VALUE;
        //  }else{


        return new Vector3f(x, y, z).normalize();
    }


    private static Vector3f GetTiltVector(Vector3f vec) {
        float horizMag = (float) Math.sqrt(vec.x * vec.x + vec.z * vec.z);

        if (horizMag < 1e-6f) {
            // 竖直向量（无水平投影），直接返回y轴方向
            return new Vector3f(0, vec.y, 0);
        }

        // 水平分量合并到x轴，z轴置0，保留竖直分量y
        return new Vector3f(horizMag, vec.y, 0);
    }

    // 直接用向量求竖直方向的夹角（无需显式算仰角）
//  public static float GetVerticalAngleDirectly(Vector3f vec1, Vector3f vec2) {
//      // 1. 预处理两个向量，得到倾斜向量
//      Vector3f tilt1 = GetTiltVector(vec1);
//      Vector3f tilt2 = GetTiltVector(vec2);

//      // 2. 计算两个倾斜向量的点积和模长
//      float dot = tilt1.dot(tilt2);
//      float mag1 = tilt1.magnitude;
//      float mag2 = tilt2.magnitude;

//      // 避免零向量除零
//      if (mag1 < 1e-6f || mag2 < 1e-6f) {
//          return 0f;
//      }

//      // 3. 计算夹角（点积公式），修正精度误差
//      float cosTheta = Math.clamp(dot / (mag1 * mag2), -1f, 1f);
//      float angleDegrees = Math.acos(cosTheta) * Math.Rad2Deg;

//      return angleDegrees;
//  }


    public static float getVertical(Vector3f v) {

        return getVertical(v.x, v.y, v.z);


    }

    public static float getVertical(float x, float y, float z) {

        float distance = (float) Math.hypot(Math.hypot(x, y), z);

        if (distance == 0) {

            return Math.abs(y) / y * 180;

        }

        return (float) (180 * (Math.asin(y / distance) / Math.PI));
    }

    public static float getHorizontal(Vector3f v) {

        float x = v.x;
        float z = v.z;

        float horizontal = (float) (180 * (Math.atan2(x, z) / Math.PI));//注意 vertical1 horizontal1  这里的单位为角度

        if (horizontal > 0) {
            horizontal = 180 - horizontal;//修复坐标系方向不同
        } else if (horizontal < 0) {
            horizontal = -horizontal - 180;
        }

        if ((x < 0.00000005 && z < 0.000000005) && (x > -0.00000005 && z > -0.00000005)) {//排除距离过小的情况
            horizontal = 0;

        }


        return horizontal;
    }

    public static float getHorizontal(float x, float z) {


        float horizontal = (float) (180 * (Math.atan2(x, z) / Math.PI));//注意 vertical1 horizontal1  这里的单位为角度

        if (horizontal > 0) {
            horizontal = 180 - horizontal;//修复坐标系方向不同
        } else if (horizontal < 0) {
            horizontal = -horizontal - 180;
        }

        if ((x < 0.00000005 && z < 0.000000005) && (x > -0.00000005 && z > -0.00000005)) {//排除距离过小的情况
            horizontal = 0;

        }


        return horizontal;
    }

    public static float[] getRadian(float xRot, float yRot) {

        return new float[]{(float) (Math.PI * xRot / 180), (float) (Math.PI * yRot / 180)};


    }

    public static Vec3 eulerGetVectors(float xRot, float yRot) {

        float[] rot = getRadian(xRot, yRot);

        return new Vec3(
                -Math.cos(rot[0]) * Math.sin(rot[1]) / 5,
                Math.cos(rot[0]) * Math.cos(rot[1]) / 5,//
                -Math.sin(rot[0]) / 5
        );


    }

    /**
     * 计算两个角度的最小差值（考虑角度循环性）
     *
     * @param currentAngle 当前角度
     * @param targetAngle  目标角度
     * @return 最小角度差（范围：-180 ~ 180）
     */
    public static float calculateMinAngleDiff(float currentAngle, float targetAngle) {
        float diff = targetAngle - currentAngle;
        // 处理360度循环，将差值限制在[-180, 180]区间
        if (Math.abs(diff) > 180) {
            diff = diff > 0 ? diff - 360 : diff + 360;
        }
        return diff;
    }


//Euler pt

    /**
     * 平滑逼近目标角度（基础方法）
     *
     * @param currentAngle 当前角度
     * @param targetAngle  目标角度
     * @param step         每次逼近的步长
     * @return 处理后的角度
     */
    public static float smoothApproach(float currentAngle, float targetAngle, float step) {
        float diff = calculateMinAngleDiff(currentAngle, targetAngle);

        if (Math.abs(diff) > step) {
            // 差距大于步长时，按步长逼近
            float direction = (float) Math.signum(diff);
            return currentAngle + direction * step;
        } else {
            // 差距小于等于步长时，直接对齐目标
            return targetAngle;
        }
    }

    /**
     * 处理垂直角度（俯仰角）的平滑逼近
     *
     * @param currentVertical   当前俯仰角
     * @param targetVertical    目标俯仰角
     * @param currentHorizontal 当前水平角（用于极端情况翻转）
     * @return 处理后的角度数组 [新俯仰角, 新水平角]
     */
    public static float[] smoothVerticalAngle(float currentVertical, float targetVertical, float currentHorizontal) {
        float newVertical = currentVertical;
        float newHorizontal = currentHorizontal;
        float diff = calculateMinAngleDiff(currentVertical, targetVertical);

        // 处理极端情况：接近±90度且需要向相反方向移动时翻转水平角
        boolean isExtreme = (currentVertical <= -90 && diff > 0) ||
                (currentVertical >= 90 && diff < 0);

        if (isExtreme) {
            // 翻转水平角
            newHorizontal = currentHorizontal < 0 ? currentHorizontal + 180 : currentHorizontal - 180;
        } else {
            // 正常平滑逼近
            newVertical = smoothApproach(currentVertical, targetVertical, 20.0f);
        }

        return new float[]{newVertical, newHorizontal};
    }

    /**
     * 处理水平角度（偏航角）的平滑逼近
     *
     * @param currentHorizontal 当前偏航角
     * @param targetHorizontal  目标偏航角
     * @return 处理后的偏航角
     */
    public static float smoothHorizontalAngle(float currentHorizontal, float targetHorizontal) {
        float diff = calculateMinAngleDiff(currentHorizontal, targetHorizontal);

        // 处理极端情况：接近±180度且需要向相反方向移动时重置角度
        if ((currentHorizontal <= -180 && diff > 0)) {
            return 180.0f;
        } else if ((currentHorizontal >= 180 && diff < 0)) {
            return -180.0f;
        }

        // 正常平滑逼近
        return smoothApproach(currentHorizontal, targetHorizontal, 20.0f);
    }

    public static float[] getHV(float currentHorizontal, float targetHorizontal, float currentVertical, float targetVertical) {

        float h = smoothHorizontalAngle(currentHorizontal, targetHorizontal);

        return smoothVerticalAngle(currentVertical, targetVertical, h);


    }

    //获取最大的绝对值
    public static double mabs(double n1, double n2) {

        return Math.abs(n1) >= Math.abs(n2) ? n1 : n2;


    }

    public static Point2D.Float bessel(float t, Point2D.Float p0, Point2D.Float p1, Point2D.Float p2) {

        if (t < 0 || t > 1) {
            return new Point2D.Float(0, 0);   // throw new IllegalArgumentException("参数 t 必须在 [0, 1] 范围内。");
        }
        float mt = 1 - t;
        float x = mt * mt * p0.x + 2 * mt * t * p1.x + t * t * p2.x;
        float y = mt * mt * p0.y + 2 * mt * t * p1.y + t * t * p2.y;
        return new Point2D.Float(x, y);


        //  return (1 - t) * (1 - t) * P0 + 2 * (1 - t) * t * P1 + t * t * P2;

    }

    public enum Rotation {
        XN((f) -> {
            return (new Quaternionf()).rotationX(-f);
        }, new Vector3f(-1.0F, 0.0F, 0.0F)),
        XP((f) -> {
            return (new Quaternionf()).rotationX(f);
        }, new Vector3f(1.0F, 0.0F, 0.0F)),
        YN((f) -> {
            return (new Quaternionf()).rotationY(-f);
        }, new Vector3f(0.0F, -1.0F, 0.0F)),
        YP((f) -> {
            return (new Quaternionf()).rotationY(f);
        }, new Vector3f(0.0F, 1.0F, 0.0F)),
        ZN((f) -> {
            return (new Quaternionf()).rotationZ(-f);
        }, new Vector3f(0.0F, 0.0F, -1.0F)),
        ZP((f) -> {
            return (new Quaternionf()).rotationZ(f);
        }, new Vector3f(0.0F, 0.0F, 1.0F));

        public final Vector3f vec;
        private final Func func;

        private Rotation(Func func, Vector3f vec) {
            this.func = func;
            this.vec = vec;
        }

        public Quaternionf rad(float f) {
            return this.func.rotation(f);
        }

        public Quaternionf deg(float f) {
            return this.func.rotation(f * 0.017453292F);
        }

        private interface Func {
            Quaternionf rotation(float var1);
        }
    }


}
