package com.misanthropy.linggango.shading_begone.gui;

import com.misanthropy.linggango.shading_begone.ShadingConfig;
import com.misanthropy.linggango.shading_begone.mixin.GameRendererInvoker;
import com.misanthropy.linggango.shading_begone.mixin.LevelRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ShadingConfigScreen extends OptionsSubScreen {
    private static final Component TITLE = Component.translatable("shading_begone.config.title");
    private OptionsList list;

    private final boolean initialBlock;
    private final boolean initialCloud;
    private final boolean initialEntity;

    public ShadingConfigScreen(Screen lastScreen) {
        super(lastScreen, Minecraft.getInstance().options, TITLE);
        this.initialBlock = ShadingConfig.INSTANCE.blockShadingEnabled;
        this.initialCloud = ShadingConfig.INSTANCE.cloudShadingEnabled;
        this.initialEntity = ShadingConfig.INSTANCE.entityShadingEnabled;
    }

    @Override
    protected void init() {
        this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);

        OptionInstance<Boolean> blockOption = OptionInstance.createBoolean(
                "shading_begone.config.option.blockShading",
                enabled -> Tooltip.create(Component.translatable("shading_begone.config.option.blockShading.tooltip")),
                ShadingConfig.INSTANCE.blockShadingEnabled,
                enabled -> ShadingConfig.INSTANCE.blockShadingEnabled = enabled
        );

        OptionInstance<Boolean> cloudOption = OptionInstance.createBoolean(
                "shading_begone.config.option.cloudShading",
                enabled -> Tooltip.create(Component.translatable("shading_begone.config.option.cloudShading.tooltip")),
                ShadingConfig.INSTANCE.cloudShadingEnabled,
                enabled -> ShadingConfig.INSTANCE.cloudShadingEnabled = enabled
        );

        OptionInstance<Boolean> entityOption = OptionInstance.createBoolean(
                "shading_begone.config.option.entityShading",
                enabled -> Tooltip.create(Component.translatable("shading_begone.config.option.entityShading.tooltip")),
                ShadingConfig.INSTANCE.entityShadingEnabled,
                enabled -> ShadingConfig.INSTANCE.entityShadingEnabled = enabled
        );

        this.list.addSmall(blockOption, cloudOption);
        this.list.addSmall(entityOption, null);

        this.addWidget(this.list);

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> {
            this.minecraft.setScreen(this.lastScreen);
        }).bounds(this.width / 2 - 100, this.height - 27, 200, 20).build());
    }

    @Override
    public void removed() {
        ShadingConfig.save();

        if (this.minecraft == null || this.minecraft.player == null) return;

        boolean changedBlocks = this.initialBlock != ShadingConfig.INSTANCE.blockShadingEnabled;
        boolean changedClouds = this.initialCloud != ShadingConfig.INSTANCE.cloudShadingEnabled;
        boolean changedEntities = this.initialEntity != ShadingConfig.INSTANCE.entityShadingEnabled;

        if (changedBlocks || changedClouds || changedEntities) {
            if (changedBlocks) {
                this.minecraft.levelRenderer.allChanged();
            }
            if (changedClouds) {
                ((LevelRendererAccessor) this.minecraft.levelRenderer).setGenerateClouds(true);
            }
            if (changedEntities) {
                ((GameRendererInvoker) this.minecraft.gameRenderer).invokeReloadShaders(this.minecraft.getResourceManager());
            }
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.basicListRender(guiGraphics, this.list, mouseX, mouseY, partialTick);
    }
}