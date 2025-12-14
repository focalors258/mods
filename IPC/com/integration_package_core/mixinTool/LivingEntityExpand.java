package com.integration_package_core.mixinTool;

import com.integration_package_core.tool.collide.OBB;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

public interface LivingEntityExpand {

    //public float get
    public float getHealth_move();
    public void setToughness_move(float toughness_move);
    public void setHealth_move(float health_move);
    public float getAbsorption_move();

    public float getToughness_move();

    public void setAbsorption_move(float absorption_move);
    public void setToughness_end(float toughness_end);


    public void setHealth_end(float health_end);


    public void setAbsorption_end(float absorption_end);



    public float getOtoughness_move();


    public void setOtoughness_move(float otoughness_move);


    public float getOhealth_move();

    public void setOhealth_move(float ohealth_move);

    public float getOabsorption_move();

    public void setOabsorption_move(float oabsorption_move);



    public float getOHealth_move();


    public float getOAbsorption_move();

    public float getOToughness_move();

    public void setSmashTime(float value);

    public float getSmashTime();


    public void setToughness(float value);
    public void setMaxToughness(float value);

    public float getToughness();

    public void hurtToughness(LivingEntity source, float value);
    public void hurtToughness(float value);
    public float getMaxToughness();

public boolean isSmash();

    public float getHealth_end();


    public float getAbsorption_end();


    public float getToughness_end();

    public void setSmash(boolean value);


    public float getSmashEnd() ;


    public void setSmashEnd(float value);

    public void setRigid(boolean time);
    public void rangeAttackLiving(OBB o,  DamageSource ds, float v);
    public boolean isRigid();
    public void hit(OBB o,  DamageSource ds, float v);
    public float getRigidEnd();

    public float getRigidTime();

    public void setRigidEnd(float value);

    public void hit(Vector3f pose, Vector3f deflect, DamageSource ds, float v);
    public void setRigidTime(float value);

    public void addRigid(int time);



}
