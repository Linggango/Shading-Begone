package com.misanthropy.linggango.shading_begone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ShadingConfig {
    private static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("shading_begone.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public boolean blockShadingEnabled = false;
    public boolean cloudShadingEnabled = false;
    public boolean entityShadingEnabled = false;
    public transient boolean shadersActive = false;
    public transient boolean blockShadingEffective = false;
    public transient boolean cloudShadingEffective = false;
    public transient boolean entityShadingEffective = false;
    public static final ShadingConfig INSTANCE = new ShadingConfig();
    public void updateEffectiveStates() {
        this.blockShadingEffective = this.blockShadingEnabled && !this.shadersActive;
        this.cloudShadingEffective = this.cloudShadingEnabled && !this.shadersActive;
        this.entityShadingEffective = this.entityShadingEnabled && !this.shadersActive;
    }

    public static void load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                    ShadingConfig loaded = GSON.fromJson(reader, ShadingConfig.class);
                    if (loaded != null) {
                        INSTANCE.blockShadingEnabled = loaded.blockShadingEnabled;
                        INSTANCE.cloudShadingEnabled = loaded.cloudShadingEnabled;
                        INSTANCE.entityShadingEnabled = loaded.entityShadingEnabled;
                    }
                }
            } else {
                save();
            }
            INSTANCE.updateEffectiveStates();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        INSTANCE.updateEffectiveStates();

        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
            GSON.toJson(INSTANCE, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}