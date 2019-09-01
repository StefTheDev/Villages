package com.stefthedev.villages.utilities.storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class YAML {

    private JavaPlugin javaPlugin;
    private String name;

    private FileConfiguration fileConfiguration;
    private File file;

    public YAML(JavaPlugin javaPlugin, String name) {
        this.javaPlugin = javaPlugin;
        this.name = name;
    }

    public void setup() {
        if(file == null) {
            file = new File(javaPlugin.getDataFolder(), name + ".yml");
        }

        if(!file.exists()) {
            file.getParentFile().mkdirs();
            javaPlugin.saveResource(name + ".yml", false);
        }

        reload();
        fileConfiguration.options().copyDefaults(true);
        save();
    }

    private void reload(){
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        InputStream inputStream = javaPlugin.getResource(name + ".yml");
        if(inputStream != null) fileConfiguration.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream)));
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }
}
