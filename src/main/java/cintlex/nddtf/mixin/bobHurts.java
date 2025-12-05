package cintlex.nddtf.mixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class bobHurts {
    @Shadow @Final
    Minecraft minecraft;
    @Unique
    private DamageSource damagetiltfixes$ds = null;
    @Unique
    private float damagetiltfixes$rd = 0.0f;
    @WrapOperation(
            method = "bobHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getHurtDir()F"
            )
    )
    private float properndtilt(LivingEntity le, Operation<Float> og) {
        float vanilla = og.call(le);
        if (le != this.minecraft.player || le.hurtTime <= 0) {
            return vanilla;
        }
        DamageSource ds = le.getLastDamageSource();
        if (ds == null || directional(ds)) {
            return vanilla;
        }
        if (damagetiltfixes$ds != ds) {
            damagetiltfixes$rd = le.getRandom().nextBoolean() ? 0.0f : 180.0f;
            damagetiltfixes$ds = ds;
        }
        return damagetiltfixes$rd;
    }
    @Unique
    private static boolean directional(DamageSource source) {
        return source.getSourcePosition() != null
                || source.getEntity() != null
                || source.getDirectEntity() != null;
    }
}