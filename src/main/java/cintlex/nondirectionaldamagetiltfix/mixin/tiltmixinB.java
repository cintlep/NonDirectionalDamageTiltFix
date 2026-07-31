package cintlex.nondirectionaldamagetiltfix.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Camera;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Camera.class)
public class tiltmixinB {

    @Unique
    private DamageSource ndtf$ds = null;
    @Unique
    private float ndtf$rd = 0.0f;

    @WrapOperation(
            method = "extractRenderState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getHurtDir()F"
            )
    )
    private float ndtf$nondirectionaltilt(LivingEntity le, Operation<Float> original) {
        if (le.hurtTime <= 0) {
            return original.call(le);
        }
        DamageSource ds = le.getLastDamageSource();
        if (ds == null || directionaltilt(ds)) {
            return original.call(le);
        }
        if (ndtf$ds != ds) {
            ndtf$rd = le.getRandom().nextBoolean() ? 0.0f : 180.0f;
            ndtf$ds = ds;
        }
        return ndtf$rd;
    }

    @Unique
    private static boolean directionaltilt(DamageSource ds) {
        return ds.getSourcePosition() != null
                || ds.getEntity() != null
                || ds.getDirectEntity() != null;
    }
}