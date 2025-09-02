package com.integration_package_core.tool;

import net.minecraft.world.phys.Vec3;

public class Maths {

    public static float[] getRadian(float xRot, float yRot) {

        return new float[]{(float) (Math.PI * xRot / 180), (float) (Math.PI * yRot / 180)};


    }


//Euler pt

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
public static double mabs(double n1,double n2){

    return Math.abs(n1) >= Math.abs(n2) ? n1 : n2;



}




}
