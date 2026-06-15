package com.misanthropy.linggango.shading_begone;

import com.misanthropy.linggango.shading_begone.gui.ShadingConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(Shading_begone.MODID)
public class Shading_begone {

    public static final String MODID = "shading_begone";

    public Shading_begone() {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (minecraft, lastScreen) -> new ShadingConfigScreen(lastScreen)
                )
        );
    }
}