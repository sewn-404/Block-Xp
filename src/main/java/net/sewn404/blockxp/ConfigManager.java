package net.sewn404.blockxp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting() // Makes the JSON file human-readable
            .excludeFieldsWithoutExposeAnnotation() // Only fields with @Expose will be included
            .create();

    private static Path configPath; // Path to the config file
    public static ModConfig config; // The loaded configuration instance

    public static void initialize(Path gameConfigDirectory) {
        // Define the full path to your mod's config file
        configPath = gameConfigDirectory.resolve(Blockxp.MOD_ID + ".json");
        loadConfig(); // Attempt to load the config
    }

    private static void loadConfig() {
        if (Files.exists(configPath)) {
            try (FileReader reader = new FileReader(configPath.toFile())) {
                config = GSON.fromJson(reader, ModConfig.class);
                Blockxp.LOGGER.info("Loaded config from {}", configPath);
            } catch (IOException e) {
                Blockxp.LOGGER.error("Failed to load config from {}", configPath, e);
                config = new ModConfig(); // Fallback to default config on error
                saveConfig(); // Attempt to save the default config
            }
        } else {
            // If config file doesn't exist, create a new one with defaults and save it
            config = new ModConfig();
            Blockxp.LOGGER.info("No config file found, creating default at {}", configPath);
            saveConfig();
        }
    }

    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(configPath.toFile())) {
            GSON.toJson(config, writer);
            Blockxp.LOGGER.info("Saved config to {}", configPath);
        } catch (IOException e) {
            Blockxp.LOGGER.error("Failed to save config to {}", configPath, e);
        }
    }
}