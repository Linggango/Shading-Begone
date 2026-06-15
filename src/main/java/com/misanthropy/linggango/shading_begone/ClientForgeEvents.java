package com.misanthropy.linggango.shading_begone;

import com.misanthropy.linggango.shading_begone.mixin.GameRendererInvoker;
import com.misanthropy.linggango.shading_begone.mixin.LevelRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Shading_begone.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;
        while (ClientModEvents.RELOAD_CONFIG.consumeClick()) {
            ShadingConfig.load();
            mc.levelRenderer.allChanged();
            ((LevelRendererAccessor) mc.levelRenderer).setGenerateClouds(true);
            ((GameRendererInvoker) mc.gameRenderer).invokeReloadShaders(mc.getResourceManager());
        }

        boolean changedBlocks = false;
        boolean changedClouds = false;
        boolean changedEntities = false;

        while (ClientModEvents.TOGGLE_BLOCK_SHADING.consumeClick()) {
            ShadingConfig.INSTANCE.blockShadingEnabled = !ShadingConfig.INSTANCE.blockShadingEnabled;
            changedBlocks = true;
        }

        while (ClientModEvents.TOGGLE_CLOUD_SHADING.consumeClick()) {
            ShadingConfig.INSTANCE.cloudShadingEnabled = !ShadingConfig.INSTANCE.cloudShadingEnabled;
            changedClouds = true;
        }

        while (ClientModEvents.TOGGLE_ENTITY_SHADING.consumeClick()) {
            ShadingConfig.INSTANCE.entityShadingEnabled = !ShadingConfig.INSTANCE.entityShadingEnabled;
            changedEntities = true;
        }

        if (changedBlocks || changedClouds || changedEntities) {
            ShadingConfig.save();

            if (changedBlocks) {
                mc.levelRenderer.allChanged();
            }

            if (changedClouds) {
                ((LevelRendererAccessor) mc.levelRenderer).setGenerateClouds(true);
            }

            if (changedEntities) {
                ((GameRendererInvoker) mc.gameRenderer).invokeReloadShaders(mc.getResourceManager());
            }
        }
    }
}