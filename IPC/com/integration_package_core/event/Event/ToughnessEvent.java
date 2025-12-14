package com.integration_package_core.event.Event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Event;

public abstract class ToughnessEvent extends Event {


    public LivingEntity entity;

    public LivingEntity source;
    public float amount;


    public static class HurtEvent extends ToughnessEvent {


        public HurtEvent(float amount, LivingEntity entity) {
            this.amount = amount;

            this.entity = entity;

        }


        public HurtEvent(float amount, LivingEntity entity, LivingEntity source) {
            this.amount = amount;
            this.source = source;
            this.entity = entity;

        }


    }

    public static class SmashEvent extends ToughnessEvent {

        public SmashEvent(float amount, LivingEntity entity, LivingEntity source) {
            this.amount = amount;
            this.source = source;
            this.entity = entity;

        }
        public SmashEvent(float amount, LivingEntity entity) {
            this.amount = amount;

            this.entity = entity;

        }


    }


}
