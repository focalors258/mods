package com.chestsearch.mixin;

import net.minecraft.commands.CommandSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin extends net.minecraftforge.common.capabilities.CapabilityProvider<Entity> implements Nameable, EntityAccess, CommandSource, net.minecraftforge.common.extensions.IForgeEntity{


    @Shadow private Vec3 position;

    protected EntityMixin(Class<Entity> baseClass) {
        super(baseClass);
    }



    @Inject(
            method ="load",
            at = @At(
                    value = "TAIL"
                    //  shift = At.Shift.AFTER
            )
    )
    public void init(CallbackInfo ci) {


        //EntityData.getHealthMove((Entity) this)



   // this.position = new Vec3(0, 0, 0);

       //System.out.println("3453456347");


    }
















}
