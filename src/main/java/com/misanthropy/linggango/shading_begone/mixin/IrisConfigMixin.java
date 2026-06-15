package com.misanthropy.linggango.shading_begone.mixin;

import com.misanthropy.linggango.shading_begone.ShadingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.irisshaders.iris.config.IrisConfig", remap = false)
public class IrisConfigMixin {

    @Shadow
    private boolean enableShaders;

    @Inject(method = "setShadersEnabled", at = @At("RETURN"))
    private void onSetShadersEnabled(boolean enabled, CallbackInfo ci) {
        ShadingConfig.INSTANCE.shadersActive = enabled;
        ShadingConfig.INSTANCE.updateEffectiveStates();
    }

    @Inject(method = "load", at = @At("RETURN"))
    private void onLoadShadersEnabled(CallbackInfo ci) {
        ShadingConfig.INSTANCE.shadersActive = this.enableShaders;
        ShadingConfig.INSTANCE.updateEffectiveStates();
    }
}