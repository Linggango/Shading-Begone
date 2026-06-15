package com.misanthropy.linggango.shading_begone.mixin;

import com.misanthropy.linggango.shading_begone.ShadingConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @ModifyVariable(method = "getShade(Lnet/minecraft/core/Direction;Z)F", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private boolean changeShade(boolean shade) {
        return shade && ShadingConfig.INSTANCE.blockShadingEffective;
    }
}