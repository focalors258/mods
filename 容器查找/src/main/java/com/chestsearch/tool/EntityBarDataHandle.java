package com.chestsearch.tool;

public class EntityBarDataHandle {


    public static float[] getBarMove(float vary, float present, float speed) {

        // float  speed= ((LivingEntityMixin)pEntity).EntityBar_move_speed;

        float BASE = 1;


        if (vary - present == 0.00) {//当变化条少于生命条+0.00时  使两值相等  并重置bossbar1(变化条速度)

            speed = 0;

        } else if (present < vary) {//当变化条大于生命条足够多时 !entityBar_speed[index] |

            if ((vary - present) > speed) {

                speed = (float) Math.min(vary - present, 0.3);//速度
            }

            //   if (time <= 0) {  }

            vary -= (float) Math.min(BASE * 0.4 * speed, +vary - present);


        } else if (present > vary) {

            if ((present - vary) > speed) {

                speed = (float) Math.min(present - vary, 0.4);
            }

            vary += (float) Math.min(BASE * 0.4 * speed, (present - vary));

        }


        return new float[]{vary,speed};


    }


}
