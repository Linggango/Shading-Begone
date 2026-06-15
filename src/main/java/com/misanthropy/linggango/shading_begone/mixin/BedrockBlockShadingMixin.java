package com.misanthropy.linggango.shading_begone.mixin;

import com.misanthropy.linggango.shading_begone.ShadingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Pseudo // bedrockify optional
@Mixin(targets = "me.juancarloscp52.bedrockify.client.features.bedrockShading.BedrockBlockShading", remap = false)
public class BedrockBlockShadingMixin {

    @Inject(method = { "getBlockShade", "getLiquidShade" }, at = @At("RETURN"), cancellable = true)
    private void modifyShade(CallbackInfoReturnable<Float> cir) {
        if (!ShadingConfig.INSTANCE.blockShadingEnabled && !ShadingConfig.INSTANCE.shadersActive) {
            cir.setReturnValue(Math.max(1.0F, cir.getReturnValueF()));
        }
    }
}