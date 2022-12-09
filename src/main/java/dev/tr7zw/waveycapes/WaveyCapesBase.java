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
    private final File settingsFile = new File("config", "waveycapes.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private boolean optifinePresent = false;
    
    public void init() {
        INSTANCE = this;
        initSupportHooks();
    }
    
    public void writeConfig() {
        // moved to wavey cape module
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
