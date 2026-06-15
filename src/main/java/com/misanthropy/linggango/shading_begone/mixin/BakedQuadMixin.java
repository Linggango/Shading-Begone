package com.misanthropy.linggango.shading_begone.mixin;

import com.misanthropy.linggango.shading_begone.ShadingConfig;
import net.minecraft.client.renderer.block.model.BakedQuad;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BakedQuad.class)
public class BakedQuadMixin {

    @Inject(method = "isShade", at = @At("RETURN"), cancellable = true)
    private void modifyShade(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            cir.setReturnValue(ShadingConfig.INSTANCE.blockShadingEffective);
        }
    }
}