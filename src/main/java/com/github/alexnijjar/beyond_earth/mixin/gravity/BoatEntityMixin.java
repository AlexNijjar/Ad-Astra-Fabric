package com.github.alexnijjar.beyond_earth.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Vec3d;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {

    private static final double CONSTANT = -0.04;

    @Inject(method = "updateVelocity", at = @At("TAIL"), cancellable = true)
    public void updateVelocity(CallbackInfo ci) {
        if (BeyondEarth.CONFIG.general.doEntityGravity) {
            Entity entity = (Entity) (Object) this;
            if (!entity.hasNoGravity()) {
                Vec3d velocity = entity.getVelocity();
                double newGravity = ModUtils.getMixinGravity(CONSTANT, this);
                entity.setVelocity(velocity.getX(), velocity.getY() - CONSTANT + newGravity, velocity.getZ());
            }
        }
    }
}