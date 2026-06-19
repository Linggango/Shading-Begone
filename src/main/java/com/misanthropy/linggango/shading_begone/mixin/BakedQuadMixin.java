package com.misanthropy.linggango.shading_begone.mixin;

import com.misanthropy.linggango.shading_begone.ShadingConfig;
import net.minecraft.client.renderer.block.model.BakedQuad;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BakedQuad.class)
public class BakedQuadMixin {

    @Shadow @Final protected boolean shade;

    /**
     * @author Misanthropy
     * @reason For the sake of further optimization
     */
    @Overwrite
    public boolean isShade() {
        return this.shade && ShadingConfig.INSTANCE.blockShadingEffective;
    }
}