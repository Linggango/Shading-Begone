package com.misanthropy.linggango.shading_begone.mixin;

import com.misanthropy.linggango.shading_begone.ShadingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "me.jellysquid.mods.sodium.client.render.immediate.CloudRenderer", remap = false)
public class CloudRendererMixin {

    @ModifyArg(
            method = "rebuildGeometry",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/caffeinemc/mods/sodium/api/util/ColorMixer;mul(II)I",
                    remap = false
            ),
            index = 1,
            remap = false
    )
    private int redirectCloudColorMix(int originalShadingColor) {
        if (!ShadingConfig.INSTANCE.cloudShadingEnabled && !ShadingConfig.INSTANCE.shadersActive) {
            return -1;
        }
        return originalShadingColor;
    }
}