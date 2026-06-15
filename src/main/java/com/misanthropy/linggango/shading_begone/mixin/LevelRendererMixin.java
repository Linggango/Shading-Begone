package com.misanthropy.linggango.shading_begone.mixin;

import com.misanthropy.linggango.shading_begone.ShadingConfig;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @ModifyConstant(method = "buildClouds", constant = @Constant(floatValue = 0.9F))
    private float changeCloudBrightness9(float original) {
        return (ShadingConfig.INSTANCE.cloudShadingEnabled || ShadingConfig.INSTANCE.shadersActive) ? original : 1.0F;
    }

    @ModifyConstant(method = "buildClouds", constant = @Constant(floatValue = 0.7F))
    private float changeCloudBrightness7(float original) {
        return (ShadingConfig.INSTANCE.cloudShadingEnabled || ShadingConfig.INSTANCE.shadersActive) ? original : 1.0F;
    }

    @ModifyConstant(method = "buildClouds", constant = @Constant(floatValue = 0.8F))
    private float changeCloudBrightness8(float original) {
        return (ShadingConfig.INSTANCE.cloudShadingEnabled || ShadingConfig.INSTANCE.shadersActive) ? original : 1.0F;
    }
}