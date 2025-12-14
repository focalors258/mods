package com.integration_package_core.event.Event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class LivingAttackEvent extends Event {



    private final DamageSource source;
    private  float amount;
    public LivingAttackEvent(LivingEntity entity, DamageSource source, float amount)
    {

        this.source = source;
        this.amount = amount;
    }

    public DamageSource getSource() { return source; }
    public float getAmount() { return amount; }


    public void setAmount(float amount) {
         this.amount=amount;
    }




    public static float onLivingAttack(LivingEntity entity, DamageSource source, float amount) {
        LivingAttackEvent e=new LivingAttackEvent(entity,source,amount);

        return MinecraftForge.EVENT_BUS.post(e)?0:e.getAmount();

    }






}
