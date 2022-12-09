package dev.tr7zw.waveycapes;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public abstract class WaveyCapesBase {

    public static WaveyCapesBase INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger("WaveyCapes");
    public static Config config;
    private final File settingsFile = new File("config", "waveycapes.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private boolean optifinePresent = false;
    
    public void init() {
        INSTANCE = this;
        if (settingsFile.exists()) {
            try {
                config = gson.fromJson(new String(Files.readAllBytes(settingsFile.toPath()), StandardCharsets.UTF_8),
                        Config.class);
            } catch (Exception ex) {
                System.out.println("Error while loading config! Creating a new one!");
                ex.printStackTrace();
            }
        }
        if (config == null) {
            config = new Config();
            writeConfig();
        } else {
            if(ConfigUpgrader.upgradeConfig(config)) {
                writeConfig();
            }
        }
        initSupportHooks();
    }
    
    public void writeConfig() {
        if (settingsFile.exists())
            settingsFile.delete();
        try {
            Files.write(settingsFile.toPath(), gson.toJson(config).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    public GuiScreen createConfigScreen(GuiScreen parent) {
        return new ConfigScreen(parent);
    }
    
    public static class ConfigScreen extends CustomConfigScreen {

        public ConfigScreen(GuiScreen lastScreen) {
            super(lastScreen, "text.wc.title");
        }
        
        @Override
        public void initialize() {
            List<GuiButton> options = new ArrayList<>();
            options.add(getEnumOption("text.wc.setting.capestyle", CapeStyle.class, () -> config.capeStyle, (v) -> config.capeStyle = v));
            options.add(getEnumOption("text.wc.setting.windmode", WindMode.class, () -> config.windMode, (v) -> config.windMode = v));
            options.add(getEnumOption("text.wc.setting.capemovement", CapeMovement.class, () -> config.capeMovement, (v) -> config.capeMovement = v));
            //options.add(getIntOption("text.wc.setting.capeparts", 16, 64, () -> config.capeParts, (v) -> config.capeParts = v));
            options.add(getIntOption("text.wc.setting.gravity", 5, 32, () -> config.gravity, (v) -> config.gravity = v));
            options.add(getIntOption("text.wc.setting.heightMultiplier", 4, 16, () -> config.heightMultiplier, (v) -> config.heightMultiplier = v));
            //options.add(getIntOption("text.wc.setting.maxBend", 1, 20, () -> config.maxBend, (v) -> config.maxBend = v));

            addOptionsList(options);

        }

        @Override
        public void save() {
            INSTANCE.writeConfig();
        }
        
    }
    
    public abstract void initSupportHooks();
    
    /**
     * Checks if a class exists or not
     * @param name
     * @return
     */
    protected static boolean doesClassExist(String name) {
        try {
            if(Class.forName(name) != null) {
                return true;
            }
        } catch (ClassNotFoundException e) {}
        return false;
    }
    
}
