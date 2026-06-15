package com.misanthropy.linggango.shading_begone.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.misanthropy.linggango.shading_begone.ShadingConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GlslPreprocessor.class)
public class GlslPreprocessorMixin {

    @WrapOperation(
            method = "processImports",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/preprocessor/GlslPreprocessor;applyImport(ZLjava/lang/String;)Ljava/lang/String;"
            )
    )
    private String shading_begone$modifyLightingCalculations(
            GlslPreprocessor instance,
            boolean quotesUsed,
            String file,
            Operation<String> original
    ) {
        String source = original.call(instance, quotesUsed, file);

        if (source != null && !ShadingConfig.INSTANCE.entityShadingEnabled && !quotesUsed && "light.glsl".equals(file)) {

            String search = "vec4 minecraft_mix_light(vec3 lightDir0, vec3 lightDir1, vec3 normal, vec4 color) {";

            String replacement = """
                #define minecraft_mix_light(lightDir0, lightDir1, normal, color) (ProjMat[3].x != -1.0 ? color : minecraft_mix_light_helper(lightDir0, lightDir1, normal, color))
                vec4 minecraft_mix_light_helper(vec3 lightDir0, vec3 lightDir1, vec3 normal, vec4 color) {
                """;
            source = source.replace(search, replacement);
        }

        return source;
    }
}