package com.misanthropy.linggango.shading_begone;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Shading_begone.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    public static final KeyMapping RELOAD_CONFIG = new KeyMapping(
            "key.shading_begone.reload", InputConstants.UNKNOWN.getValue(), "category.shading_begone"
    );
    public static final KeyMapping TOGGLE_BLOCK_SHADING = new KeyMapping(
            "key.shading_begone.toggle_block", InputConstants.UNKNOWN.getValue(), "category.shading_begone"
    );
    public static final KeyMapping TOGGLE_CLOUD_SHADING = new KeyMapping(
            "key.shading_begone.toggle_cloud", InputConstants.UNKNOWN.getValue(), "category.shading_begone"
    );
    public static final KeyMapping TOGGLE_ENTITY_SHADING = new KeyMapping(
            "key.shading_begone.toggle_entity", InputConstants.UNKNOWN.getValue(), "category.shading_begone"
    );

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ShadingConfig::load);
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(RELOAD_CONFIG);
        event.register(TOGGLE_BLOCK_SHADING);
        event.register(TOGGLE_CLOUD_SHADING);
        event.register(TOGGLE_ENTITY_SHADING);
    }
}