package xyz.vexsystems.RBW.Config;

import java.io.*;
import java.util.Properties;

public class ConfigManager {

    private static final String CONFIG_DIR_PATH = "config" + File.separator + "RBW";
    private static final String CONFIG_FILE_PATH = CONFIG_DIR_PATH + File.separator + "config.properties";

    private Properties properties;

    public ConfigManager() {
        properties = new Properties();
        loadConfig();
    }


    private void loadConfig() {
        File configFile = new File(CONFIG_FILE_PATH);
        if (configFile.exists()) {
            try (InputStream input = new FileInputStream(configFile)) {
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadDefaultConfig();
        }
    }



    private void loadDefaultConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("default_config.properties")) {
            if (input == null) {
                System.err.println("Default config file not found!");
                return;
            }
            properties.load(input);
            saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void saveConfig() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE_PATH)) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String moduleName, String key, String defaultValue) {
        return properties.getProperty(moduleName + "." + key, defaultValue);
    }

    public int getProperty(String moduleName, String key, int defaultValue) {
        String value = getProperty(moduleName, key, String.valueOf(defaultValue));
        return Integer.parseInt(value);
    }

    public boolean getModuleEnabled(String moduleName) {
        return Boolean.parseBoolean(getProperty(moduleName, "enabled", "false"));
    }

    public void setModuleEnabled(String moduleName, boolean enabled) {
        setProperty(moduleName, "enabled", String.valueOf(enabled));
    }

    public void setProperty(String moduleName, String key, String value) {
        properties.setProperty(moduleName + "." + key, value);
        saveConfig();
    }

    public void setProperty(String moduleName, String key, int value) {
        properties.setProperty(moduleName + "." + key, String.valueOf(value));
        saveConfig();
    }

    public void setProperty(String moduleName, String key, boolean value) {
        properties.setProperty(moduleName + "." + key, String.valueOf(value));
        saveConfig();
    }

}
